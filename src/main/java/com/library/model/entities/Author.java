package com.library.model.entities;

/**
 * The type Author.
 */
public class Author implements Identified<Integer> {

    private String name;
    private Integer id;

    /**
     * Instantiates a new Author.
     */
    public Author() {
    }

    /**
     * Instantiates a new Author.
     *
     * @param name the name
     */
    public Author(String name) {
        this.name = name;
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

}
