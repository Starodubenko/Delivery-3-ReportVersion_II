package com.epam.star.action.Client;

import com.epam.star.action.Action;
import com.epam.star.action.ActionException;
import com.epam.star.action.ActionResult;
import com.epam.star.action.MappedAction;
import com.epam.star.dao.H2dao.DaoFactory;
import com.epam.star.dao.H2dao.DaoManager;
import com.epam.star.dao.OrderDao;
import com.epam.star.entity.Client;
import com.epam.star.entity.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@MappedAction("GET/browseOrders")
public class AjaxBrowseOrdersAction implements Action {
    private static final Logger LOGGER = LoggerFactory.getLogger(AjaxBrowseOrdersAction.class);
    ActionResult clientOrders = new ActionResult("ajaxOrdersForClientTable");

    @Override
    public ActionResult execute(HttpServletRequest request) throws ActionException {
        DaoManager daoManager = DaoFactory.getInstance().getDaoManager();

        OrderDao orderDao = daoManager.getOrderDao();
        Client user = (Client) request.getSession().getAttribute("user");

        List<Order> todayOrders = orderDao.findAllByClientIdToday(user.getId());
        List<Order> pastOrders = orderDao.findAllByClientIdLastDays(user.getId());
        List<Order> allOrders = orderDao.findAllByClientId(user.getId());

        request.setAttribute("todayOrders", todayOrders);
        request.setAttribute("pastOrders", pastOrders);
        request.setAttribute("allOrders", allOrders);

        return clientOrders;
    }
}
