package com.epam.star.dao.H2dao;

import com.epam.star.dao.MappedDao;
import com.epam.star.dao.PayCardDao;
import com.epam.star.dao.PayCardStatusDao;
import com.epam.star.entity.PayCard;
import com.epam.star.entity.StatusPayCard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@MappedDao("PayCard")
public class H2PayCardDao extends AbstractH2Dao implements PayCardDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(H2ClientDao.class);
    private static final String ADD_PAYCARD = "INSERT INTO pay_card VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_PAYCARD = "UPDATE pay_card SET id = ?, serial_number = ?, secret_number = ?, balance = ?, id_status_pay_card = ?, DELETED = ?  WHERE id = ?";
    private static final String FULL_DELETE_PAYCARD = "DELETE FROM pay_card WHERE ID = ?";
    private static final String DELETE_PAYCARD = "UPDATE pay_card SET DELETED = ?  WHERE id = ?";

    private static final String NECESSARY_COLUMNS =
            " PAY_CARD.ID, PAY_CARD.SERIAL_NUMBER, PAY_CARD.SECRET_NUMBER, PAY_CARD.BALANCE, " +
                    "STATUS_CARD.STATUS_NAME ";

    private static final String ADDITIONAL_COLUMNS =
            " ,PAY_CARD.DELETED";

    private static final String FIND_BY_PARAMETERS_WITHOUT_COLUMNS =
            " SELECT %s" +
                    " FROM PAY_CARD" +
                    " INNER JOIN STATUS_CARD" +
                    " ON PAY_CARD.ID_STATUS_PAY_CARD = STATUS_CARD.ID";

    private static final String ID_FIELD = " PAY_CARD.ID, ";

    private static final String ORDER_BY = " ORDER BY SERIAL_NUMBER ";
    private static final String TABLE = "PAY_CARD";
    private static final String IMPORTANT_FILTER = "";


    protected H2PayCardDao() {
        super(null, null);
    }

    protected H2PayCardDao(Connection conn, DaoManager daoManager) {
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

    @Override
    public PayCard findBySerialNumber(String serNum) throws DaoException {
        String sql = "SELECT * FROM pay_card WHERE serial_number = " + "'" + serNum + "'";
        PayCard payCard = null;
        PreparedStatement prstm = null;
        ResultSet resultSet = null;
        try {
            prstm = conn.prepareStatement(sql);
            resultSet = prstm.executeQuery();

            payCard = getEntityFromResultSet(resultSet);
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeStatement(prstm, resultSet);
        }
        return payCard;
    }

    @Override
    public PayCard findBySecretNumber(String secNum) throws DaoException {
        String sql = "SELECT * FROM pay_card WHERE secret_number = " + "'" + secNum + "'";
        PayCard payCard = null;
        PreparedStatement prstm = null;
        ResultSet resultSet = null;

        try {
            prstm = conn.prepareStatement(sql);
            resultSet = prstm.executeQuery();

            if (resultSet.next())
                payCard = getEntityFromResultSet(resultSet);
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeStatement(prstm, resultSet);
        }
        return payCard;
    }

    @Override
    public PayCard findByStatus(String status) throws DaoException {

        H2PayCardStatusDao h2PayCardStatusDao = daoManager.getPayCardStatusDao();

        StatusPayCard statusPayCard = h2PayCardStatusDao.findByStatusName(status);
        String sql = "SELECT * FROM pay_card WHERE ID_STATUS = " + statusPayCard.getId();
        PayCard payCard = null;
        PreparedStatement prstm = null;
        ResultSet resultSet = null;
        try {
            prstm = conn.prepareStatement(sql);
            resultSet = prstm.executeQuery();

            payCard = getEntityFromResultSet(resultSet);
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeStatement(prstm, resultSet);
        }
        return payCard;
    }

    @Override
    public PayCard findById(int ID) throws DaoException {
        String sql = "SELECT * FROM pay_card WHERE Id = " + ID;
        PayCard payCard = null;
        PreparedStatement prstm = null;
        ResultSet resultSet = null;

        try {
            prstm = conn.prepareStatement(sql);
            resultSet = prstm.executeQuery();

            if (resultSet.next())
            payCard = getEntityFromResultSet(resultSet);
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeStatement(prstm, resultSet);
        }
        return payCard;
    }

    @Override
    public String insert(PayCard payCard) throws DaoException {
        String status = "PayCard do not added";

        PreparedStatement prstm = null;
        try {
            prstm = conn.prepareStatement(ADD_PAYCARD);
            prstm.setString(1, null);
            prstm.setString(2, payCard.getSerialNumber());
            prstm.setString(3, payCard.getSecretNumber());
            prstm.setBigDecimal(4, payCard.getBalance());
            prstm.setInt(5, payCard.getStatusPayCard().getId());
            prstm.setBoolean(6, payCard.isDeleted());
            prstm.execute();
            status = "PayCard added successfully";
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeStatement(prstm, null);
        }
        return status;
    }

    @Override
    public String deleteEntity(int ID) throws DaoException {
        String status = "Pay card not marked as deleted";

        try (PreparedStatement prstm = conn.prepareStatement(DELETE_PAYCARD)){
            prstm.setBoolean(1, true);
            prstm.setInt(2, ID);
            prstm.executeUpdate();
            status = "Pay card marked as deleted";
        } catch (Exception e) {
            throw new DaoException(e);
        }

        return status;
    }

    @Override
    public String updateEntity(PayCard payCard) throws DaoException {
        PreparedStatement prstm = null;
        try {
            prstm = conn.prepareStatement(UPDATE_PAYCARD);
            prstm.setInt(1, payCard.getId());
            prstm.setString(2, payCard.getSerialNumber());
            prstm.setString(3, payCard.getSecretNumber());
            prstm.setBigDecimal(4, payCard.getBalance());
            prstm.setInt(5, payCard.getStatusPayCard().getId());
            prstm.setBoolean(6, payCard.isDeleted());
            prstm.setInt(7, payCard.getId());
            prstm.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeStatement(prstm, null);
        }
        return null;
    }

    @Override
    public PayCard getEntityFromResultSet(ResultSet resultSet) throws DaoException {
        PayCardStatusDao payCardStatusDao = daoManager.getPayCardStatusDao();
        PayCard payCard = new PayCard();
        try {
            payCard.setId(resultSet.getInt("id"));
            payCard.setSerialNumber(resultSet.getString("serial_number"));
            payCard.setSecretNumber(resultSet.getString("secret_number"));
            payCard.setBalance(new BigDecimal(resultSet.getInt("balance")));
            payCard.setStatusPayCard(payCardStatusDao.findById(resultSet.getInt("id_status_pay_card")));
            payCard.setDeleted(resultSet.getBoolean("deleted"));
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return payCard;
    }

    @Override
    public List<PayCard> findAll() {
        String sql = "SELECT * FROM PAY_CARD";
        List<PayCard> payCards = new ArrayList<>();
        try (PreparedStatement prstm = conn.prepareStatement(sql)){
            try(ResultSet resultSet = prstm.executeQuery()){
                while (resultSet.next())
                    payCards.add(getEntityFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return payCards;
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
