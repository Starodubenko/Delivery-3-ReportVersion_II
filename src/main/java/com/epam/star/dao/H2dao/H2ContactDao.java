package com.epam.star.dao.H2dao;

import com.epam.star.dao.ContactDao;
import com.epam.star.dao.MappedDao;
import com.epam.star.entity.Contact;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@MappedDao("Contact")
public class H2ContactDao extends AbstractH2Dao implements ContactDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(H2ClientDao.class);
    private static final String ADD_CONTACT = "INSERT INTO CONTACTS VALUES (?, ?, ?, ?, ?)";
    private static final String FULL_DELETE_CONTACT = "DELETE FROM CONTACTS WHERE ID = ?";
    private static final String DELETE_CONTACT = "UPDATE CONTACTS SET DELETED = ? WHERE ID = ?";
    private static final String UPDATE_CONTACT = "UPDATE CONTACTS SET ID = ?, TELEPHONE = ?, OWNER = ?, PART = ?, DELETED = ? WHERE ID = ?";

    private static final String NECESSARY_COLUMNS =
            " CONTACTS.ID, CONTACTS.TELEPHONE, CONTACTS.OWNER, CONTACTS.PART";

    private static final String ADDITIONAL_COLUMNS =
            " ,CONTACTS.DELETED";

    private static final String FIND_BY_PARAMETERS_WITHOUT_COLUMNS =
            " SELECT %s FROM CONTACTS";

    private static final String ID_FIELD = " CONTACTS.ID, ";

    private static final String ORDER_BY = " ORDER BY PART ";
    private static final String TABLE = "CONTACTS";
    private static final String IMPORTANT_FILTER = "";


    protected H2ContactDao() {
        super(null, null);
    }

    protected H2ContactDao(Connection conn, DaoManager daoManager) {
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
    public List<Contact> getContacts() throws DaoException {
        List<Contact> result = new ArrayList<>();

        PreparedStatement prstm = null;
        ResultSet resultSet = null;
        try {
            prstm = conn.prepareStatement("SELECT * FROM CONTACTS");
            resultSet = prstm.executeQuery();
            while (resultSet.next()) {
                result.add(getEntityFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeStatement(prstm, resultSet);
        }
        return result;
    }

    @Override
    public Contact findById(int ID) throws DaoException {
        String sql = "select * from contacts where id = " + "'" + ID + "'";
        PreparedStatement prstm = null;
        ResultSet resultSet = null;
        try {
            prstm = conn.prepareStatement(sql);
            resultSet = prstm.executeQuery();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeStatement(prstm, resultSet);
        }
        return getEntityFromResultSet(resultSet);
    }

    @Override
    public String insert(Contact contact) {
        String status = "Contact do not added";

        try (PreparedStatement prstm = conn.prepareStatement(ADD_CONTACT)){
            prstm.setString(1, null);
            prstm.setString(2, contact.getTelephone());
            prstm.setString(3, contact.getOwner());
            prstm.setString(4, contact.getPart());
            prstm.setBoolean(5, contact.isDeleted());
            prstm.execute();
            status = "Contact added successfully";
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return status;
    }

    @Override
    public String deleteEntity(int ID) throws DaoException {
        String status = "Contact do not deleted";

        try (PreparedStatement prstm = conn.prepareStatement(DELETE_CONTACT)){
            prstm.setBoolean(1, true);
            prstm.setInt(2, ID);
            prstm.execute();
            status = "Contact deleted successfully ";
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return status;
    }

    @Override
    public String updateEntity(Contact contact) {
        String status = "Contact do not updated";

        try (PreparedStatement prstm = conn.prepareStatement(UPDATE_CONTACT)){
            prstm.setInt(1, contact.getId());
            prstm.setString(2, contact.getTelephone());
            prstm.setString(3, contact.getOwner());
            prstm.setString(4, contact.getPart());
            prstm.setBoolean(5, contact.isDeleted());
            prstm.setInt(6, contact.getId());
            prstm.executeUpdate();
            status = "Contact updated successfully";
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return status;
    }

    @Override
    public Contact getEntityFromResultSet(ResultSet resultSet) throws DaoException {
        Contact contact = new Contact();
        try {
            contact.setId(resultSet.getInt("id"));
            contact.setTelephone(resultSet.getString("telephone"));
            contact.setOwner(resultSet.getString("owner"));
            contact.setPart(resultSet.getString("part"));
            contact.setDeleted(resultSet.getBoolean("deleted"));
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return contact;
    }

    @Override
    public List<Contact> findAll() {
        String sql = "SELECT * FROM CONTACTS";
        List<Contact> contacts = new ArrayList<>();
        try (PreparedStatement prstm = conn.prepareStatement(sql)){
            try(ResultSet resultSet = prstm.executeQuery()){
                while (resultSet.next())
                    contacts.add(getEntityFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return contacts;
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
