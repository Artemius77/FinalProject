package com.library.servicelayer.serviceinterface;

import com.library.model.entities.*;
import com.library.controller.utils.enums.SearchType;

import java.util.List;

/**
 * The interface Output service.
 */
public interface OutputService {

    /**
     * All genres list.
     *
     * @return the list
     */
    List allGenres();

    /**
     * All authors list.
     *
     * @return the list
     */
    List allAuthors();

    /**
     * All publishers list.
     *
     * @return the list
     */
    List allPublishers();

    /**
     * Gets user by primary key.
     *
     * @param userId the user id
     * @return the user by pk
     */
    User getUserByPk(String userId);

    /**
     * Gets books by given criteria.
     *
     * @param type        the type
     * @param criteria    the criteria
     * @param currentPage the current page
     * @param bookPerPage the book per page
     * @return the books by criteria
     */
    List<Book> getBooksByCriteria(SearchType type, String criteria, int currentPage, int bookPerPage);

    /**
     * Current request list.
     *
     * @param currentPage the current page
     * @param bookPerPage the book per page
     * @return the list
     */
    List<Book> currentRequest(int currentPage, int bookPerPage);

    /**
     * Gets book by pk.
     *
     * @param id the id
     * @return the book by pk
     */
    Book getBookByPK(int id);

    /**
     * Gets author by pk.
     *
     * @param id the id
     * @return the author by pk
     */
    Author getAuthorByPK(int id);

    /**
     * Gets genre by pk.
     *
     * @param id the id
     * @return the genre by pk
     */
    Genre getGenreByPK(int id);

    /**
     * Gets publisher by pk.
     *
     * @param id the id
     * @return the publisher by pk
     */
    Publisher getPublisherByPK(int id);

    /**
     * Gets books count.
     *
     * @return the books count
     */
    int getBooksCount();
}
