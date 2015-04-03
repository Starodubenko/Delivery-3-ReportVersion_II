package com.epam.star.action.order;

import com.epam.star.action.Action;
import com.epam.star.action.ActionException;
import com.epam.star.action.ActionResult;
import com.epam.star.action.MappedAction;
import com.epam.star.dao.H2dao.DaoFactory;
import com.epam.star.dao.H2dao.DaoManager;
import com.epam.star.dao.H2dao.H2OrderDao2;
import com.epam.star.dao.H2dao.H2OrderedGoodsDao;
import com.epam.star.entity.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

@MappedAction("GET/saveEditedOrder")
public class SaveEditedOrderAction implements Action {
    private static final Logger LOGGER = LoggerFactory.getLogger(SaveEditedOrderAction.class);
    private static Random rnd = new Random();

    ActionResult client = new ActionResult("client", true);

    @Override
    public ActionResult execute(HttpServletRequest request) throws ActionException {
        DaoManager daoManager = DaoFactory.getInstance().getDaoManager();

        Order order = (Order) request.getSession().getAttribute("editOrder");

        H2OrderedGoodsDao orderedGoodsDao = daoManager.getOrderedGoodsDao();
        H2OrderDao2 orderDao2 = daoManager.getOrderDao2();

//        Map<Goods, Integer> goods = order.getOrderedGoods(); TODO
//        List<OrderedGoods> byOrderNumber = orderedGoodsDao.findByOrderNumber(order.getNumber());
//        for (OrderedGoods orderedGoods : byOrderNumber) {
//            Integer integer = goods.get(orderedGoods.getGoods());
//            orderedGoods.setGoodsCount(integer);
//            orderedGoodsDao.updateEntity(orderedGoods);
//        }

        daoManager.closeConnection();
//        request.getSession().setAttribute("editOrder", null);
        return client;
    }
}
