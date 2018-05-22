package com.library.servicelayer.serviceinterface;

import com.library.model.entities.*;
import com.library.controller.utils.enums.Role;

/**
 * The interface Manage service.
 */
public interface ManageService {

    /**
     * Add book.
     *
     * @param book the book
     */
    void addBook(Book book);

    /**
     * Add author.
     *
     * @param author the author
     */
    void addAuthor(Author author);

    /**
     * Add genre.
     *
     * @param genre the genre
     */
    void addGenre(Genre genre);

    /**
     * Add publisher.
     *
     * @param publisher the publisher
     */
    void addPublisher(Publisher publisher);


    /**
     * Add user with default role.
     *
     * @param user the user
     */
    void addNewUser(User user);

    /**
     * Update book.
     *
     * @param book the book
     */
    void updateBook(Book book);

    /**
     * Release book.
     *
     * @param bookId the book id
     */
    void releaseBook(int bookId);
  //  void updateUser(User user);

    /**
     * Delete book.
     *
     * @param book the book
     */
    void deleteBook(Book book);

    /**
     * Order book.
     *
     * @param bookId the book id
     * @param userId the user id
     */
    void takeBook(int bookId, String userId);

    /**
     * Change user.
     *
     * @param role the role
     * @param user the user
     */
    void changeUser(Role role, User user);
}
