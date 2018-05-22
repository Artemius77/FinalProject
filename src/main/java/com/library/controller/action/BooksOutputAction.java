package com.library.controller.action;

import com.library.controller.utils.enums.SearchType;
import com.library.controller.utils.classes.AbstractPaginationClass;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Output and search books Action
 */
public class BooksOutputAction extends AbstractPaginationClass implements Action {



    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        preAction(request);

        /*
        Search by genre if id is not null
         */
        if (request.getParameter("id") != null) {
            if (StringUtils.isNumeric(request.getParameter("id"))) {
                String id = request.getParameter("id");
                if (id.equals("0")){
                    request.setAttribute("books", bookOutput.getBooksByCriteria(SearchType.ALL,
                            "", currentPage, recordsPerPage));
                    request.setAttribute("selected", "All");
                } else {
                    request.setAttribute("books", bookOutput.getBooksByCriteria(SearchType.GENRE,
                            id, currentPage, recordsPerPage));
                    String genre = request.getParameter("name");
                    request.setAttribute("selected", genre);
                }
            } else {
                return "main";
            }
        }
        /*
        Search by letter if parameter letter is not null
         */
        else if (request.getParameter("letter") != null) {
            request.setAttribute("books", bookOutput.getBooksByCriteria(SearchType.LETTER,
                    request.getParameter("letter"), currentPage,recordsPerPage));
            request.setAttribute("selectedLetter",request.getParameter("letter").charAt(0));
        }
        /*
        Search by searching bar
        You can search by author or by title
         */
        else if (request.getParameter("search_String") != null) {

            String search_string = request.getParameter("search_String");
            SearchType type = SearchType.TITLE;

            if (request.getParameter("search_option").equals("Author")) {
                type = SearchType.AUTHOR;
            }

            request.setAttribute("option",type.toString().toLowerCase());

            if (search_string != null && !search_string.trim().equals("")) {
                request.setAttribute("books", bookOutput.getBooksByCriteria(
                        type, search_string, currentPage,recordsPerPage));
                request.setAttribute("search",search_string);
            } else {
                request.setAttribute("books",bookOutput.getBooksByCriteria(SearchType.ALL,"",currentPage,
                        recordsPerPage));
            }
            /*
            In other case we'd like to find all books
             */
        } else if (request.getParameter("currentPage") == null && request.getParameter("booksPerPage") == null) {
            request.setAttribute("books", bookOutput.getBooksByCriteria
                    (SearchType.ALL,"",currentPage,recordsPerPage));
        }


        postAction(request);

        return "main";
    }





}
