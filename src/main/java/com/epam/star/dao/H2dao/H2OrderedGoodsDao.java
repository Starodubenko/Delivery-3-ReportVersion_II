package com.epam.star.dao.H2dao;

import com.epam.star.dao.MappedDao;
import com.epam.star.dao.OrderedGoodsDao;
import com.epam.star.entity.OrderedGoods;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@MappedDao("OrderedGoods")
public class H2OrderedGoodsDao extends AbstractH2Dao implements OrderedGoodsDao{
    private static final Logger LOGGER = LoggerFactory.getLogger(H2ClientDao.class);
    private static final String ADD_GOODS = "INSERT INTO ORDERED_GOODS VALUES (?, ?, ?, ?, ?)";
    private static final String FULL_DELETE_GOODS = "DELETE FROM ORDERED_GOODS WHERE ID = ?";
    private static final String DELETE_GOODS = "UPDATE ORDERED_GOODS SET DELETED = ? WHERE ID = ?";
    private static final String UPDATE_GOODS = "UPDATE ORDERED_GOODS SET " +
            "ID = ?, ORDER_NUMBER = ?, GOODS_ID = ?, GOODS_COUNT = ?, DELETED = ? WHERE ID = ?";

    private static final String NECESSARY_COLUMNS =
            " ORDERED_GOODS.ID, ORDERED_GOODS.ORDER_NUMBER, ORDERED_GOODS.GOODS_ID, ORDERED_GOODS.GOODS_COUNT";

    private static final String ADDITIONAL_COLUMNS =
            "";

    private static final String FIND_BY_PARAMETERS_WITHOUT_COLUMNS =
            " SELECT %s FROM ORDERED_GOODS";

    private static final String ID_FIELD = " ORDERED_GOODS.ID, ";

    private static final String ORDER_BY = " ORDER BY GOODS_COUNT ";
    private static final String TABLE = "ORDERED_GOODS";
    private static final String IMPORTANT_FILTER = "";


    protected H2OrderedGoodsDao() {
        super(null, null);
    }

    protected H2OrderedGoodsDao(Connection conn, DaoManager daoManager) {
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


    public List<OrderedGoods> findByOrderNumber(int orderNumber) throws DaoException {
        String sql = "SELECT * FROM ORDERED_GOODS WHERE ORDER_NUMBER = " + orderNumber;
        List<OrderedGoods> goods = new ArrayList<>();
        PreparedStatement prstm = null;
        ResultSet resultSet = null;
        try {
            prstm = conn.prepareStatement(sql);
            resultSet = prstm.executeQuery();

            while (resultSet.next()) {
                goods.add(getEntityFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeStatement(prstm, resultSet);
        }
        return goods;
    }

    @Override
    public OrderedGoods findById(int ID) throws DaoException {
        String sql = "SELECT * FROM ORDERED_GOODS WHERE id = " + ID;
        OrderedGoods goods = null;
        PreparedStatement prstm = null;
        ResultSet resultSet = null;
        try {
            prstm = conn.prepareStatement(sql);
            resultSet = prstm.executeQuery();

            if (resultSet.next())
                goods = getEntityFromResultSet(resultSet);
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeStatement(prstm, resultSet);
        }
        return goods;
    }


    public String insert(OrderedGoods goods) throws DaoException {
        String status = "Goods do not added";

        try (PreparedStatement prstm = conn.prepareStatement(ADD_GOODS)){
            prstm.setString(1, null);
            prstm.setInt(2, goods.getOrderNumber());
            prstm.setInt(3, goods.getGoods().getId());
            prstm.setInt(4, goods.getGoodsCount());
            prstm.setBoolean(5, goods.isDeleted());
            prstm.execute();
            status = "Goods added successfully";
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return status;
    }


    public String deleteEntity(int ID) throws DaoException {
        String status = "Ordered goods do not deleted";

        try (PreparedStatement prstm = conn.prepareStatement(DELETE_GOODS)){
            prstm.setBoolean(1, true);
            prstm.setInt(2, ID);
            prstm.executeUpdate();
            status = "Ordered goods deleted successfully ";
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return status;
    }


    public String updateEntity(OrderedGoods goods) throws DaoException {
        String status = "Goods do not updated";

        try (PreparedStatement prstm = conn.prepareStatement(UPDATE_GOODS)){
            prstm.setInt(1, goods.getId());
            prstm.setInt(2, goods.getOrderNumber());
            prstm.setInt(3, goods.getGoods().getId());
            prstm.setInt(4, goods.getGoodsCount());
            prstm.setBoolean(5, goods.isDeleted());
            prstm.setInt(6, goods.getId());
            prstm.executeUpdate();
            status = "Goods updated successfully";
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return status;
    }

    @Override
    public OrderedGoods getEntityFromResultSet(ResultSet resultSet) throws DaoException {

        H2GoodsDao goodsDao = daoManager.getGoodsDao();

        OrderedGoods goods = new OrderedGoods();
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

    @Override
    public List<OrderedGoods> findAll() {
        String sql = "SELECT * FROM ORDERED_GOODS";
        List<OrderedGoods> orderedGoods = new ArrayList<>();
        try (PreparedStatement prstm = conn.prepareStatement(sql)){
            try(ResultSet resultSet = prstm.executeQuery()){
                while (resultSet.next())
                    orderedGoods.add(getEntityFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return orderedGoods;
    }

    @Override
    public String getFindByParameters(Boolean needAditionalColumns) {

        String columns = NECESSARY_COLUMNS;

        if (needAditionalColumns == true){
            columns = columns + ADDITIONAL_COLUMNS;
        }

        String result = String.format(FIND_BY_PARAMETERS_WITHOUT_COLUMNS,columns);

        result = String.format(result+"%s", ORDER_BY);
        result = String.format(result+"%s", LIMIT_OFFSET);

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
}
