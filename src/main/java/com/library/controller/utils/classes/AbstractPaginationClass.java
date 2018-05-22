package com.library.controller.utils.classes;

import com.library.servicelayer.serviceimpl.OutputServiceImpl;
import com.library.servicelayer.serviceinterface.OutputService;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Abstract pagination class.
 */
public class AbstractPaginationClass {

    /**
     * Output service
     */
    protected OutputService bookOutput = new OutputServiceImpl();

    /**
     * Records per page to show
     */
    protected int recordsPerPage = 3;
    /**
     * Summary founded books
     */
    private int rows;
    /**
     * Number of pages
     */
    private int nOfPages;

    /**
     * Current page.
     */
    protected int currentPage;


    /**
     * Pre action to determine where request come from
     *
     * @param request request object
     */
    protected void preAction(HttpServletRequest request) {
        if (request.getParameter("currentPage") == null) {

            currentPage = 1;
            if (request.getParameter("booksPerPage") != null &&
                    StringUtils.isNumeric(request.getParameter("booksPerPage"))) {

                recordsPerPage = Integer.parseInt(request.getParameter("booksPerPage"));
                if (recordsPerPage < 1) return;
                request.setAttribute("books", bookOutput.currentRequest(currentPage, recordsPerPage));

            }
        } else {
            if (!StringUtils.isNumeric(request.getParameter("currentPage"))) return;

            currentPage = Integer.valueOf(request.getParameter("currentPage"));
            if (currentPage < 1) return;
            request.setAttribute("books", bookOutput.currentRequest(currentPage, recordsPerPage));

        }
    }




    /**
     * Post action sending needed objects and calculate number of pages
     *
     * @param request the request
     */
    protected void postAction(HttpServletRequest request) {

        if (request.getParameter("currentPage") == null) {
            rows = bookOutput.getBooksCount();
            nOfPages = rows / recordsPerPage;

            if (rows % recordsPerPage != 0) {
                nOfPages++;
            }
        }

        request.setAttribute("noOfPages", nOfPages);
        request.setAttribute("size", rows);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("recordsPerPage", recordsPerPage);
    }
}
