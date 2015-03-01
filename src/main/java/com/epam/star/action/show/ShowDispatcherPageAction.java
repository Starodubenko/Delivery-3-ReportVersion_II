package com.epam.star.action.show;

import com.epam.star.action.Action;
import com.epam.star.action.ActionException;
import com.epam.star.action.ActionResult;
import com.epam.star.action.MappedAction;
import com.epam.star.dao.H2dao.*;
import com.epam.star.dao.util.PaginatedList;
import com.epam.star.dao.util.Pagination;
import com.epam.star.entity.Goods;
import com.epam.star.entity.Period;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

@MappedAction("GET/dispatcher")
public class ShowDispatcherPageAction implements Action {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShowDispatcherPageAction.class);

    private ActionResult dispatcher = new ActionResult("dispatcher");

    @Override
    public ActionResult execute(HttpServletRequest request) throws ActionException, SQLException {
        DaoManager daoManager = DaoFactory.getInstance().getDaoManager();

        H2ClientDao clientDao = daoManager.getClientDao();
        H2OrderDao orderDao = daoManager.getOrderDao();

        List<Period> periods = daoManager.getPeriodDao().getAllPeriods();
        List<Goods> goods = daoManager.getGoodsDao().getAllGoods();

        request.getSession().setAttribute("entityName", "Client");

        Pagination pagination = new Pagination();
        PaginatedList clients = pagination.paginationEntity(request, (AbstractH2Dao) clientDao, "clients");
        PaginatedList orders = pagination.paginationEntity(request, (AbstractH2Dao) orderDao, "orders");

//        H2OrderDao2 orderDao2 = daoManager.getOrderDao2();
//        PaginatedList<Order2> orders = pagination.paginationEntity(request, orderDao2, "orders");

        request.setAttribute("clientsPaginatedList",clients);
        request.setAttribute("ordersPaginatedList",orders);

        HttpSession session = request.getSession();
        session.setAttribute("periods", periods);
        session.setAttribute("goods", goods);

        daoManager.closeConnection();

        return dispatcher;
    }
}