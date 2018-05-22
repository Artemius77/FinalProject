package com.library.controller.action;

import com.library.controller.utils.enums.Role;
import com.library.controller.utils.enums.SearchType;
import com.library.controller.utils.classes.AbstractPaginationClass;
import com.library.model.entities.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Admin/Reader Info page action.
 */
public class InfoPageAction extends AbstractPaginationClass implements Action {


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        preAction(request);

        /*
        Display user's books
         */
        if (request.getParameter("user") != null && ((User)request.getSession().getAttribute("user"))
               .getId().equals(request.getParameter("user"))) {
            String user = request.getParameter("user");
            request.setAttribute("books", bookOutput.getBooksByCriteria(
                    SearchType.USER, user, currentPage, recordsPerPage));
        }
        /*
        Display all owned books for admin
         */
        else if (request.getParameter("currentPage") == null && request.getParameter("booksPerPage") == null
                  && request.isUserInRole(Role.ADMIN.toString().toLowerCase())) {
            request.setAttribute("books", bookOutput.getBooksByCriteria(
                    SearchType.ADMIN,"",currentPage,recordsPerPage));
        }
        /*
        Reader cant look up not owned books
         */
        else if (request.getParameter("currentPage") == null && request.getParameter("booksPerPage") == null
                && !request.isUserInRole(Role.ADMIN.toString().toLowerCase())) {
            return "404";
        }

        postAction(request);

        return "info";
    }








}
