package com.library.controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The interface Action.
 */
public interface Action {

    /**
     * Execute proper Action
     *
     * @param request  object
     * @param response object
     * @return the string representation of view
     */
    String execute(HttpServletRequest request, HttpServletResponse response);//!!! throws Exception;

}
