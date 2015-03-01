package com.epam.star.dao.H2dao;

import com.epam.star.dao.GoodsDao;
import com.epam.star.dao.MappedDao;
import com.epam.star.dao.Order2Dao;
import com.epam.star.entity.Goods;
import com.epam.star.entity.Order2;
import com.epam.star.entity.OrderedGoods;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@MappedDao("Order2")
public class H2OrderDao2 extends AbstractH2Dao implements Order2Dao {
    private static final Logger LOGGER = LoggerFactory.getLogger(H2OrderDao2.class);
    private static final String INSERT_ORDER = "INSERT INTO  USER_ORDER VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String RANGE_ORDERS = "SELECT * FROM ORDERS LIMIT ? OFFSET ?";
    private static final String FULL_DELETE_ORDER = "DELETE FROM USER_ORDER WHERE ID = ?";
    private static final String DELETE_ORDER = " UPDATE USER_ORDER SET DELETED = ? where ID = ?";
    private static final String UPDATE_ORDER = " UPDATE USER_ORDER SET ID = ?, USER_ID = ?, NUMBER = ?, PERIOD_ID = ?," +
            " DELIVERY_DATE = ?, ORDER_DATE = ?, PAID = ?, DISCOUNT_ID = ?, ADDITIONAL_INFO = ?, STATUS_ID = ?, DELETED = ? where ID = ?";

    private static final String NECESSARY_COLUMNS =
            " USER_ORDER.ID," +
                    "  USER_ORDER.NUMBER," +
                    "  USERS.FIRSTNAME," +
                    "  USERS.LASTNAME," +
                    "  USERS.MIDDLENAME," +
                    "  USERS.ADDRESS," +
                    "  DISCOUNT.DISCOUNT_PERCENTAGE," +
                    "  USER_ORDER.PAID," +
                    "  USER_ORDER.ORDER_DATE," +
                    "  USER_ORDER.DELIVERY_DATE," +
                    "  PERIOD.PERIOD," +
                    "  USER_ORDER.ADDITIONAL_INFO," +
                    "  STATUS.STATUS_NAME";

    private static final String ADDITIONAL_COLUMNS = " ,USER_ORDER.DELETED";

    private static final String FIND_BY_PARAMETERS_WITHOUT_COLUMNS =
            " SELECT %s" +
                    " FROM USER_ORDER" +
                    "  INNER JOIN USERS ON USER_ORDER.USER_ID = USERS.ID" +
                    "  INNER JOIN PERIOD ON USER_ORDER.PERIOD_ID = PERIOD.ID" +
                    "  INNER JOIN STATUS ON USER_ORDER.STATUS_ID = STATUS.ID" +
                    "  INNER JOIN DISCOUNT ON USER_ORDER.DISCOUNT_ID = DISCOUNT.ID";

    private static final String ID_FIELD = " USER_ORDER.ID, ";

    private static final String ORDER_BY = " ORDER BY ORDER_DATE DESC ";
    private static final String TABLE = "USER_ORDER";
    private static final String IMPORTANT_FILTER = "";


    protected H2OrderDao2() {
        super(null, null);
    }

    protected H2OrderDao2(Connection conn, DaoManager daoManager) {
        super(conn, daoManager);
    }

    @Override
    protected String getImportantFilter() {
        return IMPORTANT_FILTER;
    }

    @Override
    public String getTableName() {
        return TABLE;
    }

    public List<Order2> findAllByClientIdToday(int id) {
        String sql = "SELECT *" +
                " FROM USER_ORDER" +
                " where " +
                "user_id = " + id + " and " +
                "order_date = CAST(GETDATE() AS DATE) AND not DELETED " +
                "ORDER BY USER_ORDER.ORDER_DATE DESC, USER_ORDER.ID DESC";
        List<Order2> orders = new ArrayList<>();
        PreparedStatement prstm = null;
        ResultSet resultSet = null;

        try {
            prstm = conn.prepareStatement(sql);
            resultSet = prstm.executeQuery();

            while (resultSet.next()) {
                orders.add(getEntityFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeStatement(prstm, resultSet);
        }
        return orders;
    }

    public List<Order2> findAllByClientIdLastDays(int id) {
        String sql = "SELECT *" +
                " FROM USER_ORDER" +
                " where " +
                "user_id = " + id + " and " +
                "order_date != CAST(GETDATE() AS DATE) AND not DELETED " +
                "ORDER BY USER_ORDER.ORDER_DATE DESC, USER_ORDER.ID DESC";
        List<Order2> orders = new ArrayList<>();
        PreparedStatement prstm = null;
        ResultSet resultSet = null;

        try {
            prstm = conn.prepareStatement(sql);
            resultSet = prstm.executeQuery();

            while (resultSet.next()) {
                orders.add(getEntityFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeStatement(prstm, resultSet);
        }
        return orders;
    }

    @Override
    public List<Order2> findAllByClientId(int id) {
        String sql = "SELECT *" +
                " FROM USER_ORDER" +
                " where " +
                "user_id = " + id + " AND not DELETED " +
                "ORDER BY USER_ORDER.ORDER_DATE DESC, USER_ORDER.ID DESC";
        List<Order2> orders = new ArrayList<>();
        PreparedStatement prstm = null;
        ResultSet resultSet = null;
        try {
            prstm = conn.prepareStatement(sql);
            resultSet = prstm.executeQuery();

            while (resultSet.next()) {
                Order2 order = getEntityFromResultSet(resultSet);
                orders.add(order);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeStatement(prstm, resultSet);
        }
        return orders;
    }


    public int getClientOrdersCount(int id) {

        int result = 0;
        PreparedStatement prstm = null;
        ResultSet resultSet = null;
        try {
            prstm = conn.prepareStatement("SELECT COUNT(*) FROM USER_ORDER where user_id = " + id);
            resultSet = prstm.executeQuery();
            while (resultSet.next())
                result = resultSet.getInt("count(*)");
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeStatement(prstm, resultSet);
        }
        return result;
    }


    public Order2 findById(int ID) throws DaoException {
        String sqlOrder = "SELECT * FROM USER_ORDER WHERE id = " + ID;
        Order2 order = null;
        PreparedStatement prstm = null;
        ResultSet resultSet = null;
        try {
            prstm = conn.prepareStatement(sqlOrder);
            resultSet = prstm.executeQuery();

            if (resultSet.next())
                order = getEntityFromResultSet(resultSet);

        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeStatement(prstm, resultSet);
        }
        return order;
    }

    private Map<Goods, Integer> getGoodsForOrder(Connection conn, int number){
        Map<Goods, Integer> goods = new HashMap<>();
        String sqlGoods = "SELECT * FROM ORDERED_GOODS WHERE ORDER_NUMBER = ?";

            try(PreparedStatement prstm = conn.prepareStatement(sqlGoods)){
                prstm.setInt(1, number);

                try(ResultSet resultSet = prstm.executeQuery()){
                    while (resultSet.next()) {
                        OrderedGoods orderedGoods = getOrderedGoodsFromResultSet(resultSet);
                        goods.put(orderedGoods.getGoods(), orderedGoods.getGoodsCount());
                    }
                }

            }catch (Exception e){
                throw new DaoException(e);
            }

        return goods;
    }


    public String insert(Order2 order) {
        String status = "Order do not added";

        try (PreparedStatement prstm = conn.prepareStatement(INSERT_ORDER)){
            prstm.setString(1, null);
            prstm.setInt(2, order.getUser().getId());
            prstm.setInt(3, order.getNumber());
            prstm.setInt(4, order.getPeriod().getId());
            prstm.setDate(5, order.getDeliveryDate());
            prstm.setDate(6, order.getOrderDate());
            prstm.setBoolean(7, order.isPaid());
            prstm.setInt(8, order.getDiscount().getId());
            prstm.setString(9, order.getAdditionalInfo());
            prstm.setInt(10, order.getStatus().getId());
            prstm.setBoolean(11, order.isDeleted());
            prstm.execute();
            status = "Order added successfully";
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return status;
    }

    public String deleteEntity(int ID) throws DaoException {
        String status = "Order do not deleted";

        try (PreparedStatement prstm = conn.prepareStatement(DELETE_ORDER)){
            prstm.setBoolean(1, true);
            prstm.setInt(2, ID);
            prstm.executeUpdate();
            status = "Order deleted successfully ";
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return status;
    }


    public String updateEntity(Order2 order) {

        try (PreparedStatement prstm = conn.prepareStatement(UPDATE_ORDER)){
            prstm.setInt(1, order.getId());
            prstm.setInt(2, order.getUser().getId());
            prstm.setInt(3, order.getNumber());
            prstm.setInt(4, order.getPeriod().getId());
            prstm.setDate(5, order.getDeliveryDate());
            prstm.setDate(6, order.getOrderDate());
            prstm.setBoolean(7, order.isPaid());
            prstm.setInt(8, order.getDiscount().getId());
            prstm.setString(9, order.getAdditionalInfo());
            prstm.setInt(10, order.getStatus().getId());
            prstm.setBoolean(11, order.isDeleted());
            prstm.setInt(12, order.getId());
            prstm.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return null;
    }


    @Override
    public String getFindByParameters(Boolean needAditionalColumns) {

        String columns = NECESSARY_COLUMNS;

        if (needAditionalColumns == true) {
            columns = columns + ADDITIONAL_COLUMNS;
        }

        String result = String.format(FIND_BY_PARAMETERS_WITHOUT_COLUMNS, columns);

        result = String.format(result + "%s", ORDER_BY);
        result = String.format(result + "%s", LIMIT_OFFSET);

        return result;
    }

    @Override
    public String getFindByParametersWithoutColumns() {
        return FIND_BY_PARAMETERS_WITHOUT_COLUMNS;
    }

    @Override
    public String getNecessaryColumns() {
        return NECESSARY_COLUMNS;
    }

    @Override
    public String getAdditionalColumns() {
        return ADDITIONAL_COLUMNS;
    }

    @Override
    public String getIdField() {
        return ID_FIELD;
    }

    @Override
    public String getOrderBy() {
        return ORDER_BY;
    }


    public OrderedGoods getOrderedGoodsFromResultSet(ResultSet resultSet) throws DaoException {
        OrderedGoods goods = new OrderedGoods();
        GoodsDao goodsDao = daoManager.getGoodsDao();

        try {
            goods.setId(resultSet.getInt("id"));
            goods.setOrderNumber(resultSet.getInt("order_number"));
            goods.setGoods(goodsDao.findById(resultSet.getInt("goods_id")));
            goods.setGoodsCount(resultSet.getInt("goods_count"));
            goods.setDeleted(resultSet.getBoolean("deleted"));
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return goods;
    }

    public Order2 getEntityFromResultSet(ResultSet resultSet) throws DaoException{
        Order2 order = new Order2();
        H2PeriodDao periodDao = daoManager.getPeriodDao();
        H2StatusDao statusDao = daoManager.getStatusDao();
        H2ClientDao clientDao = daoManager.getClientDao();
        H2DiscountDao discountDao = daoManager.getDiscountDao();

        try {
            order.setId(resultSet.getInt("id"));
            order.setUser(clientDao.findById(resultSet.getInt("user_id")));
            order.setNumber(resultSet.getInt("number"));
            order.setPeriod(periodDao.findById(resultSet.getInt("period_id")));
            order.setDeliveryDate(resultSet.getDate("delivery_date"));
            order.setOrderDate(resultSet.getDate("order_date"));
            order.setPaid(resultSet.getBoolean("paid"));
            order.setDiscount(discountDao.findById(resultSet.getInt("discount_id")));
            order.setAdditionalInfo(resultSet.getString("additional_info"));
            order.setStatus(statusDao.findById(resultSet.getInt("status_id")));
            order.setDeleted(resultSet.getBoolean("deleted"));

            order.setGoods(getGoodsForOrder(conn,order.getNumber()));
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return order;
    }

    @Override
    public List<Order2> findAll() {
        String sql = "SELECT * FROM USER_ORDER";
        List<Order2> orders = new ArrayList<>();
        try (PreparedStatement prstm = conn.prepareStatement(sql)){
            try(ResultSet resultSet = prstm.executeQuery()){
                while (resultSet.next())
                    orders.add(getEntityFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return orders;
    }
}
