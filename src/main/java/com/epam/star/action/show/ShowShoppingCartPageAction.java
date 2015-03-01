package com.epam.star.action.show;

import com.epam.star.action.Action;
import com.epam.star.action.ActionException;
import com.epam.star.action.ActionResult;
import com.epam.star.action.MappedAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

@MappedAction("GET/shoppingCart")
public class ShowShoppingCartPageAction implements Action {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShowShoppingCartPageAction.class);

    private ActionResult shoppingCart = new ActionResult("viewShoppingCart");

    @Override
    public ActionResult execute(HttpServletRequest request) throws ActionException, SQLException {
        return shoppingCart;
    }
}

