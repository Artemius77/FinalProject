package com.library.model.entities;

/**
 * The type Publisher.
 */
public class Publisher implements Identified<Integer>{

    private String name;
    private Integer id;

    /**
     * Instantiates a new Publisher.
     *
     * @param name the name
     */
    public Publisher(String name) {
        this.name = name;
    }

    /**
     * Instantiates a new Publisher.
     */
    public Publisher() {
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
}
