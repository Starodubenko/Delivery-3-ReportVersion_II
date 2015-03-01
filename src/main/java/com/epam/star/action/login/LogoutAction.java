package com.epam.star.action.login;

import com.epam.star.action.Action;
import com.epam.star.action.ActionException;
import com.epam.star.action.ActionResult;
import com.epam.star.action.MappedAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

@MappedAction("GET/logout")
public class LogoutAction implements Action {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogoutAction.class);
    ActionResult welcome = new ActionResult("welcome", true);

    @Override
    public ActionResult execute(HttpServletRequest request) throws ActionException, SQLException {
        request.getSession().invalidate();
        return welcome;
    }
}
