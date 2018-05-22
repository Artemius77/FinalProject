package com.library.servicelayer.serviceimpl;

import com.library.model.dao.daofactory.DaoFactory;
import com.library.model.dao.daofactory.MySqlDaoFactory;
import com.library.servicelayer.serviceinterface.ManageService;
import com.library.model.dao.abstractdao.GenericDao;
import com.library.model.dao.daoimpl.UserDAO;
import com.library.model.dbutils.DatabaseConnection;
import com.library.model.entities.*;
import com.library.controller.utils.enums.Role;
import org.apache.commons.dbutils.DbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;


public class ManageServiceImpl implements ManageService {

    private DaoFactory<Connection> sqlFactory = MySqlDaoFactory.getInstance();
    private Logger logger = LoggerFactory.getLogger(ManageServiceImpl.class);

    @Override
    public void addBook(Book book) {
        addEntity(Book.class,book);
    }

    @Override
    public void addAuthor(Author author) {
        addEntity(Author.class,author);
    }

    @Override
    public void addGenre(Genre genre) {
        addEntity(Genre.class,genre);
    }

    @Override
    public void addPublisher(Publisher publisher) {
        addEntity(Publisher.class,publisher);
    }

    @Override
    public void addNewUser(User user) { //test
        Connection connection = DatabaseConnection.getConnection();
        UserDAO userDao = (UserDAO) sqlFactory.getDao(connection,User.class);
        try {
            connection.setAutoCommit(false);
            userDao.persist(user);
            userDao.insertRole(user.getId(),Role.USER);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {

            try {
                connection.rollback();
            } catch (SQLException e1) {
                logger.error("Error in addNewUser, transaction rollback",e1);
            }

        } finally {
            DbUtils.closeQuietly(connection);
        }
    }

    @Override
    public void takeBook(int bookId, String userId) {
        Connection connection = DatabaseConnection.getConnection();
        UserDAO userDao = (UserDAO) sqlFactory.getDao(connection,User.class);
        try {
            userDao.takeBook(bookId,userId);
        }  finally {
            DbUtils.closeQuietly(connection);
        }
    }

    @Override
    public void changeUser(Role role, User user) {
        Connection connection = DatabaseConnection.getConnection();
        UserDAO userDao = (UserDAO) sqlFactory.getDao(connection,User.class);
        try {
            connection.setAutoCommit(false);
            userDao.changeRole(user.getId(),role);
            userDao.update(user);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                logger.error("Error in changeUser, transaction rollback",e1);
            }
        } finally {
            DbUtils.closeQuietly(connection);
        }
    }


    @Override
    public void updateBook(Book book) {
        Connection connection = DatabaseConnection.getConnection();
        GenericDao<Book> genericDao = sqlFactory.getDao(connection,Book.class); //!!!!!!!!!!
        try {
            genericDao.update(book);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(connection);
        }
    }

    @Override
    public void releaseBook(int bookId) {
        Connection connection = DatabaseConnection.getConnection();
        UserDAO userDao = (UserDAO) sqlFactory.getDao(connection,User.class);
        try {
            userDao.releaseBook(bookId);
        }  finally {
            DbUtils.closeQuietly(connection);
        }
    }


    @Override
    public void deleteBook(Book book) {
        Connection connection = DatabaseConnection.getConnection();
        GenericDao<Book> genericDao = sqlFactory.getDao(connection,Book.class);
        try {
            genericDao.delete(book);
        }  finally {
            DbUtils.closeQuietly(connection);
        }
    }

    /**
     * @param dtoClass proper entity
     *              add entity to DB
     */
    private void addEntity(Class<? extends Identified> dtoClass, Identified identified) {
        Connection connection = DatabaseConnection.getConnection();
        GenericDao genericDao = sqlFactory.getDao(connection,dtoClass);
        try {
            genericDao.persist(identified);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(connection);
        }
    }
}
