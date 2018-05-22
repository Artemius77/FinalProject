package com.library.controller.utils.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * The type Application data.
 */
@WebListener()
public class ApplicationData implements ServletContextListener {

    private static final char[] ALPHABET = "АБГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЬЭЮЯ".toCharArray();

    /**
     * Instantiates a new Application data.
     */
// Public constructor is required by servlet spec
    public ApplicationData() {
    }

    // -------------------------------------------------------
    // ServletContextListener implementation
    // -------------------------------------------------------
        @Override
        public void contextInitialized(ServletContextEvent src) {
            src.getServletContext().setAttribute("alphabet", ALPHABET);
        }

        @Override
        public void contextDestroyed(ServletContextEvent event) {
        // NOOP.
        }

}
