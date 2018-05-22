package com.library.controller.action;

import com.library.servicelayer.serviceimpl.ManageServiceImpl;
import com.library.servicelayer.serviceinterface.ManageService;
import com.library.model.entities.User;
import com.library.controller.utils.enums.Role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *  User book action.
 */
public class UserBookAction implements Action {

    private ManageService manageService = new ManageServiceImpl();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        /*
        Retrieve user from session
         */
        HttpSession session = request.getSession();
        User sessionUser = (User) session.getAttribute("user");
        String userId = sessionUser.getId();
        Role userRole = sessionUser.getRole();

        /*
        Upgrade USER to READER
         */
        if (userRole.toString().equals("USER")) {

            String email = request.getParameter("email");
            String phone = request.getParameter("phone");

            if (email.isEmpty() || !email.contains("@")  ||
                    phone.isEmpty() || !phone.matches("[0-9]+") || phone.length() < 4) {
                return "main";
            }

            sessionUser.setEmail(email);
            sessionUser.setPhonenumber(phone);
            sessionUser.setRole(Role.READER);

            manageService.changeUser(Role.READER,sessionUser);
            return "main";
        }

        /*
        Order book if customer is READER
         */
        manageService.takeBook(Integer.parseInt(request.getParameter("bookId")), userId);

        return "main";
    }


}
