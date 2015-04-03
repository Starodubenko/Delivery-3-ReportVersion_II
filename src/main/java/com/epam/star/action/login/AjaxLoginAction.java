package com.epam.star.action.login;

import com.epam.star.action.Action;
import com.epam.star.action.ActionException;
import com.epam.star.action.ActionResult;
import com.epam.star.action.MappedAction;
import com.epam.star.dao.ClientDao;
import com.epam.star.dao.EmployeeDao;
import com.epam.star.dao.H2dao.DaoFactory;
import com.epam.star.dao.H2dao.DaoManager;
import com.epam.star.dao.H2dao.H2OrderDao2;
import com.epam.star.dao.PositionDao;
import com.epam.star.entity.Client;
import com.epam.star.entity.Order;
import com.epam.star.entity.OrderedGoods;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

@MappedAction("POST/ajaxLogin")
public class AjaxLoginAction implements Action {
    private static final Logger LOGGER = LoggerFactory.getLogger(AjaxLoginAction.class);

    private ActionResult result = new ActionResult("json");

    @Override
    public ActionResult execute(HttpServletRequest request) throws ActionException, SQLException {
        DaoManager daoManager = DaoFactory.getInstance().getDaoManager();

        EmployeeDao employeeDao = daoManager.getEmployeeDao();
        ClientDao clientDao = daoManager.getClientDao();
        PositionDao positionDao = daoManager.getPositionDao();
        H2OrderDao2 orderDao2 = daoManager.getOrderDao2();

        String login = request.getParameter("authenticationLogin");
        String password = request.getParameter("authenticationPassword");
        Client user = clientDao.findByCredentials(login, password);
        if (user == null)
            user = employeeDao.findByCredentials(login, password);

        JSONObject json = new JSONObject();
        if (user != null) {

            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            LOGGER.debug("Authentication is successful : {}", user);
            if (user.isDeleted()) json.put("loginError", "account.banned");

            String page = request.getHeader("referer");
            page = page.substring(page.indexOf("/do/"));

            if (page.equals("/do/completion-order")) json.put("roleView", "completion-order");
            else {
                if (user.getPosition().equals(positionDao.findByPositionName("Client"))) json.put("roleView", "client");
                if (user.getPosition().equals(positionDao.findByPositionName("Dispatcher"))) json.put("roleView", "dispatcher");
                if (user.getPosition().equals(positionDao.findByPositionName("Admin"))) json.put("roleView", "admin");
            }

            Order cart = (Order) request.getSession().getAttribute("shoppingCart");
            if (cart != null) {
                Order userCart = orderDao2.findCart(user);
                for (OrderedGoods goods : cart.getOrderedGoods()) {
                    userCart.addGoods(goods.getGoods());
                    userCart.setGoodsCount(goods.getGoods(), goods.getGoodsCount());
                }
                request.getSession().setAttribute("shoppingCart", userCart);
            }
        } else {
            json.put("loginError", "login.error");
        }

        request.setAttribute("json", json);
        daoManager.closeConnection();
        return result;
    }
}
