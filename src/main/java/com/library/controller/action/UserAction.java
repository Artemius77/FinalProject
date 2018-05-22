package com.library.controller.action;

import com.library.model.entities.User;
import com.library.controller.utils.enums.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.Principal;

/**
 * The type User action.
 */
public class UserAction implements Action {

    private final static Logger logger = LoggerFactory.getLogger(UserAction.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        String username = null;
        Principal principal = request.getUserPrincipal();
        Role role = null;

        /*
        Match roles from db with enum Roles
         */
        for (Role roles : Role.values()) {
            if (request.isUserInRole(roles.toString().toLowerCase())) role = roles;
        }
        if (role == null) logger.warn("Roles didn't match!");


        if (principal != null) {
            username = principal.getName();// Find User by j_username.
        } else
            logger.warn("Principal is null!");

        /*
        Create user and add him to session
         */
        User user = new User(username, role);

        HttpSession session = request.getSession();
        session.setAttribute("user", user);


        return "main";
    }
}
