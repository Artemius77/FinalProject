package com.library.controller.action;

import com.library.servicelayer.serviceimpl.ManageServiceImpl;
import com.library.servicelayer.serviceinterface.ManageService;
import com.library.model.entities.Author;
import com.library.model.entities.Genre;
import com.library.model.entities.Publisher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 *  Manage action.
 */
public class ManageAction implements Action{

    private ManageService manageService = new ManageServiceImpl();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try {

        request.setCharacterEncoding("UTF-8");

        /*
        Book release action(only by admin)
         */
        if (request.getParameter("releaseId") != null) {
            manageService.releaseBook(Integer.parseInt(request.getParameter("releaseId")));
            return "info";
        }

        /*
        Author persist if Author parameter is not null
         */
        if (request.getParameter("Author") != null && !request.getParameter("Author").isEmpty()) {
            String authorName = request.getParameter("Author");
            Author author = new Author(authorName);
            manageService.addAuthor(author);
        }
        /*
        Genre persist
         */
        else if (request.getParameter("Genre") != null && !request.getParameter("Genre").isEmpty()) {
            String genreName = request.getParameter("Genre");
            Genre genre = new Genre(genreName);
            manageService.addGenre(genre);
        }
        /*
        Publisher persist
         */
        else if (request.getParameter("Publisher") != null && !request.getParameter("Publisher").isEmpty()) {
            String publisherName = request.getParameter("Publisher");
            Publisher publisher = new Publisher(publisherName);
            manageService.addPublisher(publisher);
        }


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return "/pages/add.jsp";

    }
}
