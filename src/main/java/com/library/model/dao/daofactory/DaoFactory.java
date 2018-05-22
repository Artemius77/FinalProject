package com.library.model.dao.daofactory;

import com.library.model.dao.abstractdao.GenericDao;

/**
 * The interface Dao factory.
 *
 * @param <Context> the type parameter
 */
public interface DaoFactory<Context> {

    /**
     * Create GenericDao via context and dao Class.
     *
     * @param context  context for creation needed dao
     * @param dtoClass Entity class, which dao should be returned
     * @return the dao
     */
    GenericDao   getDao(Context context, Class dtoClass);

}
