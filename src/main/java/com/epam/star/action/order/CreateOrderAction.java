package com.epam.star.action.order;

import com.epam.star.action.Action;
import com.epam.star.action.ActionException;
import com.epam.star.action.ActionResult;
import com.epam.star.dao.GoodsDao;
import com.epam.star.dao.H2dao.DaoFactory;
import com.epam.star.dao.H2dao.DaoManager;
import com.epam.star.dao.OrderDao;
import com.epam.star.dao.PeriodDao;
import com.epam.star.dao.StatusDao;
import com.epam.star.entity.Client;
import com.epam.star.entity.Order;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//@MappedAction("GET/fastCreateOrder")
public class CreateOrderAction implements Action {
    private static final Logger LOGGER = LoggerFactory.getLogger(CreateOrderAction.class);

    ActionResult message = new ActionResult("message");
    ActionResult personalCabinet = new ActionResult("personal-cabinet");
    ActionResult jsonPage = new ActionResult("json");

    @Override
    public ActionResult execute(HttpServletRequest request) throws ActionException {
        DaoManager daoManager = DaoFactory.getInstance().getDaoManager();

        Client user = (Client) request.getSession().getAttribute("user");


        Order order = null;
        boolean haveMoney = false;
        try {
            OrderDao orderDao = daoManager.getOrderDao();
            order = createOrder(request, daoManager);

            haveMoney = checkBalance(request, user, order);
            if (haveMoney)
            orderDao.insert(order);
            else request.setAttribute("message", "order.not.enough.money");

            daoManager.commit();
        } catch (Exception e) {
            daoManager.rollback();
        } finally {
            daoManager.closeConnection();
        }

        JSONObject json = new JSONObject();
        if (order != null) {
            if (haveMoney)
            request.setAttribute("message", "order.created.successful");
            json.put("personalCabinet","personal-cabinet");
            request.setAttribute("json", json);
            return jsonPage;
        } else request.setAttribute("message", "during.creating.error.occurred");

        return message;
    }

    private boolean checkBalance (HttpServletRequest request, Client client, Order order){
        String paymentType = request.getParameter("paymentType");
        int count = Integer.valueOf(request.getParameter("goodscount"));

        if (client.getVirtualBalance().intValue() < order.getOrderCost().intValue()
                && paymentType.equals("online")) return false;
        else return true;
    }

    private Order createOrder(HttpServletRequest request, DaoManager daoManager) throws ActionException {

        Order order = null;
        String idString = request.getParameter("id");
        int index = -1;
        if (idString != null)
            index = Integer.parseInt(request.getParameter("id"));

        Client user = daoManager.getClientDao().findById(index);
        if (user == null) daoManager.getEmployeeDao().findById(index);
        if (user == null) user = (Client) request.getSession().getAttribute("user");

        try {
            PeriodDao periodDao = daoManager.getPeriodDao();
            GoodsDao goodsDao = daoManager.getGoodsDao();
            StatusDao statusDao = daoManager.getStatusDao();

            if (user == null) user = (Client) request.getSession().getAttribute("user");
            BigDecimal goodsPricee = goodsDao.findByGoodsName(request.getParameter("goodsname")).getPrice();
            BigDecimal orderCost = goodsPricee.multiply(new BigDecimal(request.getParameter("goodscount")));

            order = new Order();
            order.setUser(user);
            int count = Integer.parseInt(request.getParameter("goodscount"));
            order.setGoods(goodsDao.findByGoodsName(request.getParameter("goodsname")));
            order.setCount(count);
            order.setPeriod(periodDao.findByPeriod(Time.valueOf(request.getParameter("deliverytime"))));
            boolean isOnline = debitFunds(request, daoManager, user);
            if (isOnline) {
                if (!user.getRole().getPositionName().equals("Client"))
                    orderCost = orderCost.divide(new BigDecimal(2));
                order.setPaid(orderCost);
                order.setOrderCost(orderCost);
            }
            else {
                if (!user.getRole().getPositionName().equals("Client"))
                    orderCost = orderCost.divide(new BigDecimal(2));
                order.setPaid(new BigDecimal(0));
                order.setOrderCost(orderCost);
            }

            try {
                order.setDeliveryDate(new SimpleDateFormat("dd.MM.yyyy").parse(request.getParameter("deliverydate")));
            } catch (ParseException e) {
                throw new ActionException(e);
            }
            order.setAdditionalInfo(new String(request.getParameter("additionalinformation").getBytes("ISO-8859-1"),"UTF-8"));
            order.setStatus(statusDao.findByStatusName("waiting"));
            order.setOrderDate(new Date());

        } catch (Exception e) {
            request.setAttribute("CreateOrderError", "You made a mistake, check all fields");
            throw new ActionException(e);
        }
        return order;
    }

    private boolean debitFunds(HttpServletRequest request, DaoManager daoManager, Client user) {

        String paymentType = request.getParameter("paymentType");
        if (paymentType != null) {
            boolean online = paymentType.equals("online");
            return online;
        }else return false;
    }
}
