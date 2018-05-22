package com.library.controller.action;

import com.library.servicelayer.serviceimpl.ManageServiceImpl;
import com.library.servicelayer.serviceimpl.OutputServiceImpl;
import com.library.servicelayer.serviceinterface.ManageService;
import com.library.servicelayer.serviceinterface.OutputService;
import com.library.model.entities.Book;
import com.library.controller.utils.classes.FormBean;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;

/**
 * Books manipulating action
 */
@MultipartConfig
public class BooksManageAction implements Action {

    private ManageService manageService = new ManageServiceImpl();
    private OutputService outputService = new OutputServiceImpl();
    /*
       default book's Image in case of update
     */
    private  String defaultImage;
    /*
       default book's Content in case of update
     */
    private  byte[] defaultContent;


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        try {
            request.setCharacterEncoding("UTF-8");

            /*
            In case of update pass the book object on update page
             */
            if (request.getParameter("id") != null) {

                return sendBook(request);

            } else if (request.getParameter("deleteId") != null) { //Delete book
                manageService.deleteBook(outputService.getBookByPK(Integer.parseInt(
                        request.getParameter("deleteId"))));
                return "main";
            }

            if (request.getParameter("hiddenID") == null) return "main"; // in case we come not from add.jsp

            /*
             Determine where we come from(Update/Create)
             */
            boolean addMode = false;
            int idParam = -1;
            if (request.getParameter("hiddenID").isEmpty()) addMode = true;
            else {
               idParam = Integer.parseInt(request.getParameter("hiddenID"));
            }

            /*
            Reading form parameters
             */
            String name = request.getParameter("name");
            String pageCount = request.getParameter("pageCount");
            String isbn = request.getParameter("isbn");
            String publishYear = request.getParameter("publishDate");
            String author = (request.getParameter("author"));
            String genre = (request.getParameter("genre"));
            String publisher = (request.getParameter("publisher"));

            /*
            Validate form parameters and forward back if something wrong
             */
            FormBean formBean = new FormBean(name,pageCount,publishYear,isbn,author,genre,publisher);
            if (!formBean.validate()){
                if (addMode) {
                    request.setAttribute("formBean", formBean);
                } else {
                    request.setAttribute("book",outputService.getBookByPK(idParam));
                }
                request.setAttribute("error", formBean.getErrors());
                return "add";
            }



            Part filePart = request.getPart("content");
            Part imagePart = request.getPart("image");

            InputStream fileContent = filePart.getInputStream();
            InputStream imageContent = imagePart.getInputStream();

            /*
            Check image and content
             */
            byte[] content = IOUtils.toByteArray(fileContent);
            if (content.length == 0) {
                if (!addMode)
                    content = defaultContent;
                else {
                    request.setAttribute("formBean",formBean);
                    return sendMsg(request, "contentError", "fill in content pls!");
                }
            }

            String image = Base64.encodeBase64String(IOUtils.toByteArray(imageContent));
            if (image.isEmpty()) {
                if (!addMode)
                    image = defaultImage;
                else {
                    request.setAttribute("formBean",formBean);
                    return sendMsg(request, "imageError", "choose image pls!");
                }
            }



            //Create Book object
            Book book = new Book(name, content, Integer.parseInt(pageCount), isbn,
                    outputService.getGenreByPK(Integer.parseInt(genre)), outputService.getAuthorByPK(Integer.parseInt(author)),
                    Long.parseLong(publishYear), outputService.getPublisherByPK(Integer.parseInt(publisher)), image);

            /*
            Update or create depending on mode
             */
            if (!addMode) {
                book.setId(idParam);
                manageService.updateBook(book);
            } else {
                manageService.addBook(book);
            }

        } catch (IOException | ServletException e) {
            LoggerFactory.getLogger(BooksManageAction.class).error("Books Add error",e);
        }

        return "main";

    }

    /**
     *
     * @param request object
     * @param name key in request object
     * @param msg message to user
     * @return view
     */
    private String sendMsg(HttpServletRequest request, String name, String msg) {
        request.setAttribute(name, msg);
        return "add";
    }

    /**
     * Sending book for update
     *
     * @param request object
     * @return view
     */
    private String sendBook(HttpServletRequest request) {
        if (!StringUtils.isNumeric(request.getParameter("id"))) return "main";
        int id = Integer.parseInt(request.getParameter("id"));
        Book book = outputService.getBookByPK(id);

        if (book == null) return "main";

        defaultImage = book.getImage();
        defaultContent = book.getContent();
        request.setAttribute("book", book);
        return "add";
    }
}
