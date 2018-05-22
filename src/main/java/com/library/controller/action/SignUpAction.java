package com.library.controller.action;

import com.library.servicelayer.serviceimpl.ManageServiceImpl;
import com.library.servicelayer.serviceimpl.OutputServiceImpl;
import com.library.servicelayer.serviceinterface.ManageService;
import com.library.servicelayer.serviceinterface.OutputService;
import com.library.model.entities.User;
import com.library.controller.utils.classes.LanguageManager;
import org.apache.catalina.realm.RealmBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *  Sign up action.
 */
public class SignUpAction implements Action {

    private ManageService manageService = new ManageServiceImpl();
    private OutputService outputService = new OutputServiceImpl();
    private LanguageManager lang = LanguageManager.INSTANCE;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        /*
          Logout feature
         */
        if (request.getParameter("logout") != null) {
            HttpSession session = request.getSession();
            session.invalidate();
            return "index";
        }
        /*
        Change language feature
         */
        if (request.getParameter("language") != null) {
            HttpSession session = request.getSession();
            session.setAttribute("language",request.getParameter("language"));
            return "index";
        }

        String name = request.getParameter("username");
        /*
        Check where we come from
         */
        if (name == null) return "404";
        /*
        Check user on uniqueness
         */
        if (outputService.getUserByPk(name) != null) {
            request.setAttribute("error",lang.translate(request, "index.wronguser"));
            return "index";
        }

        String password = request.getParameter("password");
        String secondPassword = request.getParameter("confirm_password");
        /*
        Validate errors on server side
         */
        if (!validateParams(name, password, secondPassword)) {
            request.setAttribute("error",lang.translate(request,"index.wrong"));
            return "index";
        }
        /*
        Digesting password
         */
        password =  RealmBase.Digest(password,"MD5","ASCII");

        /*
        Add new user
         */
        manageService.addNewUser(new User(name,password));
        request.setAttribute("success",lang.translate(request,"index.success"));

        return "index";
    }



    private boolean validateParams(String username, String password, String secondPassword) {
        if (username.isEmpty()) return false;
        return password.length() >= 6 && password.equals(secondPassword);
    }
}
