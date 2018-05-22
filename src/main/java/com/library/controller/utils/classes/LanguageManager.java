package com.library.controller.utils.classes;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * The enum Language manager.
 */
public enum LanguageManager {

    /**
     * Instance language manager.
     */
    INSTANCE;

    private ResourceBundle resourceBundle;
    private final String resourseName = "language.text";

     LanguageManager() {
        resourceBundle = ResourceBundle.getBundle(
                resourseName, Locale.getDefault());
    }

    /**
     * Change Locale
     *
     * @param value the value
     */
    public void changeResource(String value) {
         resourceBundle = ResourceBundle.getBundle(resourseName, new Locale(value));
    }

    /**
     * Gets string.
     *
     * @param key the key
     * @return the string
     */
    public String getString(String key) {
        return resourceBundle.getString(key);
    }

    /**
     * Translate string.
     *
     * @param request HttpServletRequest Request object
     * @param key     indicate translated word
     * @return translated word
     */
    public String translate(HttpServletRequest request, String key) {
        String lang = (String) request.getSession().getAttribute("language");
        if (lang == null) return LanguageManager.INSTANCE.getString(key);
        else {
            LanguageManager.INSTANCE.changeResource(lang);
            return LanguageManager.INSTANCE.getString(key);
        }
    }
}
