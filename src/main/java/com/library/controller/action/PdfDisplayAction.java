package com.library.controller.action;

import com.library.servicelayer.serviceimpl.OutputServiceImpl;
import com.library.servicelayer.serviceinterface.OutputService;
import com.library.model.entities.Book;
import com.library.model.entities.User;
import com.library.controller.utils.enums.Role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * The type Pdf display action.
 */
public class PdfDisplayAction implements Action {
    /*
    Output service
     */
    private OutputService outputService = new OutputServiceImpl();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/pdf");
        try {

            if (request.getParameter("id") == null) return "404";

            OutputStream out = response.getOutputStream();
            int id = Integer.parseInt(request.getParameter("id"));
            /*
            Finding user from session and book using given id
             */
            User currentUser = (User) request.getSession().getAttribute("user");
            Book currentBook = outputService.getBookByPK(id);
            if (currentBook == null) return "404";
            /*
            Check if user is really want read exactly his book
             */
            if ((currentBook.getUser() != null && currentBook.getUser().getId().equals(currentUser.getId())) ||
                    currentUser.getRole().equals(Role.ADMIN)) {
                    byte[] content = currentBook.getContent();
                    response.setContentLength(content.length);
                    out.write(content);
            } else return "404";

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "none";
    }
}
