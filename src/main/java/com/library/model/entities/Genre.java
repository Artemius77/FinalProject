package com.library.model.entities;

/**
 * The type Genre.
 */
public class Genre implements Identified<Integer> {
    
    private String name;
    private Integer id;

    /**
     * Instantiates a new Genre.
     */
    public Genre() {
    }

    /**
     * Instantiates a new Genre.
     *
     * @param name the name
     */
    public Genre(String name) {
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
    public void setId(int id) {
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

    public void setName(String name) {
        this.name = name;
    }

}
