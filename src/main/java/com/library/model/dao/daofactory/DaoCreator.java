package com.library.model.dao.daofactory;

import com.library.model.dao.abstractdao.GenericDao;

/**
 * The interface Dao creator.
 *
 * @param <Context> context for creation
 */
public interface DaoCreator<Context> {
      /**
       * Instantiate  dao with connection
       *
       * @param context the context
       * @return the generic dao
       */
      GenericDao create(Context context);
}
