package com.library.model.entities;

/**
 * The interface Identified.
 * Connect all DAO Entities
 *
 * @param <T> the type parameter
 */
public interface Identified<T> {
    /**
     * Gets id.
     *
     * @return the id
     */
    T getId();
}
