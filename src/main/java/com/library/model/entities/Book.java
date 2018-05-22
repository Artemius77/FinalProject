package com.library.model.entities;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

/**
 * The type Book.
 */
public class Book implements Serializable,Identified<Integer>{

    private Integer id;
    private String name;
    private byte[] content;
    private int pageCount;
    private String isbn;
    private Genre genre;
    private Author author;
    private long publishDate;
    private Publisher publisher;
    private String image;
    private User user;
    private Date takeDate;
    private Date expireDate;

    /**
     * Instantiates a new Book.
     *
     * @param name        the name
     * @param content     the content
     * @param pageCount   the page count
     * @param isbn        the isbn
     * @param genre       the genre
     * @param author      the author
     * @param publishDate the publish date
     * @param publisher   the publisher
     * @param image       the image
     */
    public Book(String name, byte[] content, int pageCount, String isbn, Genre genre, Author author, long publishDate, Publisher publisher, String image) {
        this.name = name;
        this.content = content;
        this.pageCount = pageCount;
        this.isbn = isbn;
        this.genre = genre;
        this.author = author;
        this.publishDate = publishDate;
        this.publisher = publisher;
        this.image = image;
    }


    /**
     * Instantiates a new Book.
     */
    public Book() {
    }

    public Integer getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Integer id) {
        this.id = id;
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
     * Get content byte [ ].
     *
     * @return the byte [ ]
     */
    public byte[] getContent() {
        return content;
    }

    /**
     * Sets content.
     *
     * @param content the content
     */
    public void setContent(byte[] content) {
        this.content = content;
    }

    /**
     * Gets page count.
     *
     * @return the page count
     */
    public int getPageCount() {
        return pageCount;
    }

    /**
     * Sets page count.
     *
     * @param pageCount the page count
     */
    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
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
     * Gets genre.
     *
     * @return the genre
     */
    public Genre getGenre() {
        return genre;
    }

    /**
     * Sets genre.
     *
     * @param genre the genre
     */
    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    /**
     * Gets author.
     *
     * @return the author
     */
    public Author getAuthor() {
        return author;
    }

    /**
     * Sets author.
     *
     * @param author the author
     */
    public void setAuthor(Author author) {
        this.author = author;
    }

    /**
     * Gets publish date.
     *
     * @return the publish date
     */
    public long getPublishDate() {
        return publishDate;
    }

    /**
     * Sets publish date.
     *
     * @param publishDate the publish date
     */
    public void setPublishDate(long publishDate) {
        this.publishDate = publishDate;
    }

    /**
     * Gets publisher.
     *
     * @return the publisher
     */
    public Publisher getPublisher() {
        return publisher;
    }

    /**
     * Sets publisher.
     *
     * @param publisher the publisher
     */
    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    /**
     * Gets image.
     *
     * @return the image
     */
    public String getImage() {
        return image;
    }

    /**
     * Sets image.
     *
     * @param image the image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets user.
     *
     * @param user the user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets expire date.
     *
     * @return the expire date
     */
    public Date getExpireDate() {
        return expireDate;
    }

    /**
     * Sets expire date.
     *
     * @param expireDate the expire date
     */
    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    /**
     * Gets take date.
     *
     * @return the take date
     */
    public Date getTakeDate() {
        return takeDate;
    }

    /**
     * Sets take date.
     *
     * @param takeDate the take date
     */
    public void setTakeDate(Date takeDate) {
        this.takeDate = takeDate;
    }



}
