package com.epam.star.action.order;

import com.epam.star.action.Action;
import com.epam.star.action.ActionException;
import com.epam.star.action.ActionResult;
import com.epam.star.action.MappedAction;
import com.epam.star.dao.ClientDao;
import com.epam.star.dao.H2dao.DaoException;
import com.epam.star.dao.H2dao.DaoFactory;
import com.epam.star.dao.H2dao.DaoManager;
import com.epam.star.dao.OrderDao;
import com.epam.star.dao.StatusDao;
import com.epam.star.entity.Client;
import com.epam.star.entity.Order;
import com.epam.star.entity.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.sql.SQLException;

@MappedAction("GET/orderOperation")
public class OrderOperationsAction implements Action {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderOperationsAction.class);

    ActionResult client = new ActionResult("client", true);
    ActionResult message = new ActionResult("message");

    @Override
    public ActionResult execute(HttpServletRequest request) throws ActionException, SQLException {
        DaoManager daoManager = DaoFactory.getInstance().getDaoManager();
        String[] idCheckedOrders = request.getParameterValues("IdOrder");
        String operation = request.getParameter("operation");
        Boolean ajax = request.getParameter("ajax").equals("ajax");

        Client user = (Client) request.getSession().getAttribute("user");
        boolean isClient = user.getRole().getPositionName().equals("Client");

        try {
            if (idCheckedOrders != null && idCheckedOrders.length > 0) {
                daoManager.beginTransaction();

                StatusDao statusDao = daoManager.getStatusDao();
                OrderDao orderDao = daoManager.getOrderDao();

                for (String id : idCheckedOrders) {
                    int index = Integer.parseInt(id);
                    Order order = (Order) orderDao.findById(index);

                    if (operation.equals("cancel") && !isClient) {
                        if (order.getStatus().getStatusName().equals("active") || order.getStatus().getStatusName().equals("waiting")) {
                            Status status = statusDao.findByStatusName("canceled");
                            order.setStatus(status);
                            LOGGER.info("The order was canceled {}", order);
                            request.setAttribute("message", "order.canceled");
                        }
                    } else if (operation.equals("cancel") && isClient && order.getStatus().getStatusName().equals("waiting")) {
                        Status status = statusDao.findByStatusName("canceled");
                        order.setStatus(status);
                        LOGGER.info("The order was canceled {}", order);
                        request.setAttribute("message", "order.canceled");
                    }

                    if (operation.equals("accept") && order.getStatus().getStatusName().equals("waiting")) {
                        Status status = statusDao.findByStatusName("active");
                        order.setStatus(status);
                        LOGGER.info("The order was activated {}", order);
                        request.setAttribute("message", "order.activated");
                    }
                    if (operation.equals("restore") && order.getStatus().getStatusName().equals("canceled")) {
                        Status status = statusDao.findByStatusName("active");
                        order.setStatus(status);
                        LOGGER.error("The order was restored {}", order);
                        request.setAttribute("message", "order.restored");
                    }
                    orderDao.changeStatus(order);

                    fundsOperation(order, daoManager, operation);

                }
            } else {
                LOGGER.error("The order was not selected {}");
                request.setAttribute("message", "orders.not.selected");
            }
            daoManager.commit();
        } catch (Exception e) {
            daoManager.rollback();
            throw new DaoException(e);
        } finally {
            daoManager.closeConnection();
        }

        if (ajax) return message;
        return client;
    }

    private void fundsOperation(Order order, DaoManager daoManager, String operation) {

        if (!order.getPaid().equals(new BigDecimal(0))) {
            BigDecimal goodsCost = order.getPaid();
            ClientDao clientDao = daoManager.getClientDao();
            Client client = order.getUser();

            if (operation.equals("cancel")) {
                BigDecimal clientVBalance = client.getVirtualBalance();
                BigDecimal summ = clientVBalance.add(goodsCost);

                client.setVirtualBalance(summ);
                try {
                    clientDao.updateEntity(client);
                }catch (Exception e){
                    throw new DaoException(e);
                }
            } else
            if (operation.equals("accept") || operation.equals("restore")) {
                BigDecimal clientVBalance = client.getVirtualBalance();
                BigDecimal summ = clientVBalance.subtract(goodsCost);

                client.setVirtualBalance(summ);
                clientDao.updateEntity(client);
            }
        }
    }
}
