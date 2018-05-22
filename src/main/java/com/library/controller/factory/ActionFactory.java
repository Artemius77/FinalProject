package com.library.controller.factory;

import com.library.controller.action.*;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Action factory.
 */
public class ActionFactory {
    /*
    Action map
     */
    private static Map<String, Action> actions = new HashMap<>();

    static {
        BooksManageAction addAction = new BooksManageAction();
        SignUpAction signUpAction = new SignUpAction();
        actions.put("POST/main", new UserAction());
        actions.put("GET/main", new BooksOutputAction());
        actions.put("POST/add", addAction);
        actions.put("GET/add", addAction);
        actions.put("POST/manage", new ManageAction());
        actions.put("POST/take", new UserBookAction());
        actions.put("GET/info", new InfoPageAction());
        actions.put("GET/pdf", new PdfDisplayAction());
        actions.put("POST/index", signUpAction);
        actions.put("GET/index", signUpAction);
    }


    /**
     * Gets action throw request object
     *
     * @param request  request object
     * @return  Action
     */
    public static Action getAction(HttpServletRequest request) {
        return actions.get(request.getMethod() + request.getPathInfo());
    }

}
