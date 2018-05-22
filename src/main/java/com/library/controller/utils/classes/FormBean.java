package com.library.controller.utils.classes;

import java.util.*;

/**
 * The type Form bean.
 */
public class FormBean {

    private String name;
    private String pageCount;
    private String publishDate;
    private String isbn;
    private String author;
    private String genre;
    private String publisher;
    private HashMap<String, String> errors = new HashMap<>();


    /**
     * Validate given fields
     *
     * @return the boolean
     */
    public boolean validate() {

        boolean allOk=true;

        if (name.equals("")) {
            errors.put("bookName","Please enter book name");
            name="";
            allOk=false;
        }
        if (pageCount.equals("")) {
            errors.put("pageCount","Please enter page count");
            pageCount ="";
            allOk=false;
        } else {
            try {
                int x = Integer.parseInt(pageCount);
            } catch (NumberFormatException e) {
                errors.put("pageCount","Please enter a valid page count code");
                pageCount ="";
                allOk=false;
            }
        }
        if (publishDate.equals("")) {
            errors.put("publishYear","Please enter publish year");
            publishDate ="";
            allOk=false;
        } else {
            try {
                int x = Integer.parseInt(publishDate);
            } catch (NumberFormatException e) {
                errors.put("publishYear","Please enter a valid publish year code");
                publishDate ="";
                allOk=false;
            }
        }
        if (isbn.equals("")) {
            errors.put("isbn","Please enter isbn ");
            isbn="";
            allOk=false;
        }
        if (author.equals("")) {
            errors.put("author","Please enter author ");
            author="";
            allOk=false;
        }
        if (genre.equals("")) {
            errors.put("genre","Please enter genre ");
            genre="";
            allOk=false;
        }
        if (publisher.equals("")) {
            errors.put("publisher","Please enter publisher ");
            publisher="";
            allOk=false;
        }
        return allOk;
    }

    /**
     * Gets error msg.
     *
     * @param s the s
     * @return the error msg
     */
    public String getErrorMsg(String s) {
        String errorMsg = errors.get(s.trim());
        return (errorMsg == null) ? "" : errorMsg;
    }

    /**
     * Instantiates a new Form bean.
     */
    public FormBean() {
        name="";
        pageCount ="";
        publishDate ="";
        isbn="";
    }

    /**
     * Instantiates a new Form bean.
     *
     * @param name         the name
     * @param page_count   the page count
     * @param publish_year the publish year
     * @param isbn         the isbn
     * @param author       the author
     * @param genre        the genre
     * @param publisher    the publisher
     */
    public FormBean(String name, String page_count, String publish_year, String isbn, String author, String genre, String publisher) {
        this.name = name;
        this.pageCount = page_count;
        this.publishDate = publish_year;
        this.isbn = isbn;
        this.author = author;
        this.genre = genre;
        this.publisher = publisher;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * Gets isbn.
     *
     * @return the isbn
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Sets isbn.
     *
     * @param isbn the isbn
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Gets page count.
     *
     * @return the page count
     */
    public String getPageCount() {
        return pageCount;
    }

    /**
     * Sets page count.
     *
     * @param pageCount the page count
     */
    public void setPageCount(String pageCount) {
        this.pageCount = pageCount;
    }

    /**
     * Gets publish date.
     *
     * @return the publish date
     */
    public String getPublishDate() {
        return publishDate;
    }

    /**
     * Sets publish date.
     *
     * @param publishDate the publish date
     */
    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    /**
     * Gets errors.
     *
     * @return the errors
     */
    public HashMap<String, String> getErrors() {
        return errors;
    }

    /**
     * Gets author.
     *
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets author.
     *
     * @param author the author
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Gets genre.
     *
     * @return the genre
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Sets genre.
     *
     * @param genre the genre
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    /**
     * Gets publisher.
     *
     * @return the publisher
     */
    public String getPublisher() {
        return publisher;
    }

    /**
     * Sets publisher.
     *
     * @param publisher the publisher
     */
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

}
