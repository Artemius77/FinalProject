package com.library.model.dao.abstractdao;

import java.sql.SQLException;
import java.util.List;

/**
 * unified interface management of persistent objects
 *
 * @param <T> type of persist object
 */
public interface GenericDao<T> {


    /**
     *  Add record
     *  @param object the object
     * @throws SQLException the sql exception
     */
    void persist(T object) throws SQLException;

    /**
     * return object of corresponding record with primary key equals key or null
     * Key may be int value or String value
     * @param <K>  the type parameter
     *
     * @param key the key
     * @return the by pk
     */
    <K> T getByPK(K key) ;

    /**
     * update record
     * @param object the object
     *
     * @throws SQLException the sql exception
     */
    void update(T object) throws SQLException;

    /**
     * delete record
     * @param object the object
     */
    void delete(T object);

    /**
     * return all records of corresponding type
     * @return the all
     */
    List<T>  getAll();

}
