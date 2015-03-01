package com.epam.star.action.show;

import com.epam.star.action.Action;
import com.epam.star.action.ActionException;
import com.epam.star.action.ActionResult;
import com.epam.star.action.MappedAction;
import com.epam.star.dao.H2dao.DaoFactory;
import com.epam.star.dao.H2dao.DaoManager;
import com.epam.star.dao.H2dao.H2OrderDao2;
import com.epam.star.dao.util.PaginatedList;
import com.epam.star.dao.util.Pagination;
import com.epam.star.entity.Cart;
import com.epam.star.entity.Order2;
import com.epam.star.entity.Period;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

@MappedAction("GET/completion-order")
public class ShowCompletionOrderPageAction implements Action {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShowCompletionOrderPageAction.class);

    private ActionResult completion = new ActionResult("completionOrder");
    private ActionResult shoppingCart = new ActionResult("shoppingCart",true);

    @Override
    public ActionResult execute(HttpServletRequest request) throws ActionException, SQLException {

        Cart cart = (Cart)request.getSession().getAttribute("shoppingCart");
        if (cart.getGoodsCount() < 1) return shoppingCart;

        DaoManager daoManager = DaoFactory.getInstance().getDaoManager();

        H2OrderDao2 orderDao2 = daoManager.getOrderDao2();

        Pagination pagination = new Pagination();
        PaginatedList<Order2> orders = pagination.paginationEntity(request, orderDao2, "orders");

        List<Period> periods = daoManager.getPeriodDao().getAllPeriods();
        request.setAttribute("periods", periods);
        request.setAttribute("ordersPaginatedList",orders);

        daoManager.closeConnection();

        return completion;
    }
}

