package com.epam.star.action.registration;

import com.epam.star.action.Action;
import com.epam.star.action.ActionException;
import com.epam.star.action.ActionResult;
import com.epam.star.action.MappedAction;
import com.epam.star.dao.ClientDao;
import com.epam.star.dao.DiscountDao;
import com.epam.star.dao.HibernateDao.HibernateClientDao;
import com.epam.star.dao.PositionDao;
import com.epam.star.entity.Client;
import com.epam.star.util.Validator;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Map;

@MappedAction("POST/ajaxClientRegistration")
@SessionScoped
public class AjaxClientRegistration implements Action, Serializable{
    private static final Logger LOGGER = LoggerFactory.getLogger(AjaxClientRegistration.class);
    private ActionResult result = new ActionResult("json");
    private ActionResult error = new ActionResult("message");

    @Inject
    private ClientDao clientDao;

    @Inject
    private PositionDao positionDao;

    @Inject
    private DiscountDao discountDao;

    @Override
    public ActionResult execute(HttpServletRequest request) throws ActionException, SQLException {
//        DaoManager daoManager = DaoFactory.getInstance().getDaoManager();

//        ClientDao clientDao = daoManager.getClientDao();
        Validator validator = new Validator();
        JSONObject jsonObject = new JSONObject();

        Client client = createClient(request, validator, jsonObject);

        if (client != null) {
            try {
//                PositionDao positionDao = daoManager.getPositionDao();
//                H2DiscountDao discountDao = daoManager.getDiscountDao();

                client.setPosition(positionDao.findByPositionName("Client"));
                client.setVirtualBalance(new BigDecimal(0));
//                client.setAvatar(0); TODO
                client.setDiscount(discountDao.findById(4));
                clientDao.insert(client);
//                client = clientDao.findByLogin(client.getLogin());

                LOGGER.info("Client created successful, {}", client);
                request.getSession().setAttribute("user", client);
                jsonObject.put("goToPC", "client");

            } catch (Exception e) {
//                daoManager.rollback();
                request.setAttribute("message", "client.creation.error");
                return error;
            }
        } else LOGGER.info("Creation of a client failed, {}", client);
        return result;
    }

    private Client createClient(HttpServletRequest request, Validator validator, JSONObject jsonObject) {
        Client client = new Client();

        boolean login = validator.checkUserName(request.getParameter("login"));
        boolean password = validator.checkUserPassword(request.getParameter("password"),null);
        boolean confirmpassword = validator.checkConfirmUserPassword(request.getParameter("password"), request.getParameter("confirmpassword"));
        boolean firstname = validator.checkUserFirstName(request.getParameter("firstname"));
        boolean lastname = validator.checkUserLastName(request.getParameter("lastname"));
        boolean middlename = validator.checkUserMiddleName(request.getParameter("middlename"));
        boolean address = validator.checkUserAddress(request.getParameter("address"));
        boolean telephone = validator.checkUserTelephone(request.getParameter("telephone"));
        boolean mobilephone = validator.checkUserMobilephone(request.getParameter("mobilephone"));
        boolean email = validator.checkEmail(request.getParameter("email"));
        try {
            if (validator.isValide()) {
                client.setLogin(request.getParameter("login"));
                client.setPassword(request.getParameter("password"));
                client.setFirstName(request.getParameter("firstname"));
                client.setLastName(request.getParameter("lastname"));
                client.setMiddleName(request.getParameter("middlename"));
                client.setAddress(request.getParameter("address"));
                client.setTelephone(request.getParameter("telephone"));
                client.setMobilephone(request.getParameter("mobilephone"));
                client.setEmail(request.getParameter("email"));
                return client;
            } else {
                Map<String, String> invalidFields = validator.getInvalidFields();
                for (Map.Entry<String, String> field : invalidFields.entrySet()) {
                    jsonObject.put(field.getKey(), field.getValue());
                }
            }
        } finally {
            request.setAttribute("json", jsonObject);
        }
        return null;
    }
}
