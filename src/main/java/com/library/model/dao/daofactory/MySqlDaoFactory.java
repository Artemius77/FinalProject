package com.library.model.dao.daofactory;

import com.library.model.dao.abstractdao.GenericDao;
import com.library.model.dao.daoimpl.*;
import com.library.model.entities.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;


/**
 *  MySQL dao factory.
 */
public class MySqlDaoFactory implements DaoFactory<Connection> {

    private final static Logger factoryLogger = LoggerFactory.getLogger(MySqlDaoFactory.class);
    /*
    DAO map
     */
    private static Map<Class, DaoCreator<Connection>> creators = new HashMap<>();

    /*
    SingletonHelper nested class
     */
    private static class SingletonHelper{
        private static final MySqlDaoFactory INSTANCE = new MySqlDaoFactory();
    }

    /**
     * Get instance of  DaoFactory.
     *
     * @return the my sql dao factory
     */
    public static MySqlDaoFactory getInstance(){
        return SingletonHelper.INSTANCE;
    }

    @Override
    public  GenericDao getDao(Connection connection, Class dtoClass) {
        DaoCreator<Connection> dao = creators.get(dtoClass);
        if (dao == null) {
          factoryLogger.error("DAO for current class don't exist {}", dtoClass);
          throw new IllegalArgumentException();
        }
        return dao.create(connection);
    }
    /*
    static map initialize
     */
    static {
        creators.put(Genre.class, GenresDAO::new);
        creators.put(Book.class, connection -> new BooksDAO(SingletonHelper.INSTANCE, connection));
        creators.put(Author.class, AuthorDAO::new);
        creators.put(Publisher.class, PublisherDAO::new);
        creators.put(User.class, UserDAO::new);//check instance
    }

}


