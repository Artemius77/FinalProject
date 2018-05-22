package com.library.controller;

import com.library.controller.action.Action;
import com.library.controller.factory.ActionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *  Front controller servlet.
 */
@WebServlet(name = "FrontControllerServlet", urlPatterns = "/action/*")
public class FrontControllerServlet extends HttpServlet {

    private Logger logger = LoggerFactory.getLogger(FrontControllerServlet.class);

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            /*
                Getting needed Action object depending on request
             */
            Action action = ActionFactory.getAction(request);
            /*
            Page not found if there is no action on this URL
             */
            if (action == null) {
                response.setStatus(404);
                response.sendRedirect("/pages/error/pageNotFound.html");
                return;
            }
            /*
            Execute given action and getting view
             */
            String view = action.execute(request, response);
            /*
            Do nothing in case view isn't needed
             */
            if (view.equals("none")) return;
            /*
            Forward or redirect to our page, depends on URL and return view
             */
            if (view.equals(request.getPathInfo().substring(1))) {
                request.getRequestDispatcher("/pages/" + view + ".jsp").forward(request, response);
            } else {
                response.sendRedirect(view); // (PRG pattern).
            }

        } catch (IOException e) {
            logger.error("FrontControllerServlet IOException occurred", e);
        } catch (Exception e) {
            logger.error("Error in FrontControllerServlet", e);
            throw new ServletException(e);
        }

    }
}


