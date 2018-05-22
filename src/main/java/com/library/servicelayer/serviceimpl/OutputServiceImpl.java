package com.library.servicelayer.serviceimpl;

import com.library.model.dao.daofactory.DaoFactory;
import com.library.model.dao.daofactory.MySqlDaoFactory;
import com.library.servicelayer.serviceinterface.OutputService;
import com.library.model.dao.abstractdao.GenericDao;
import com.library.model.dao.daoimpl.BooksDAO;
import com.library.model.dbutils.DatabaseConnection;
import com.library.model.entities.*;
import com.library.controller.utils.enums.SearchType;
import org.apache.commons.dbutils.DbUtils;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.List;

public class OutputServiceImpl implements OutputService {

    private DaoFactory<Connection> sqlFactory = MySqlDaoFactory.getInstance();
    private int booksCount;


    @Override
    public List allGenres() {
       return getAll(Genre.class);
    }

    @Override
    public List allAuthors() {
        return getAll(Author.class);
    }

    @Override
    public List allPublishers() {
        return getAll(Publisher.class);
    }



    @Override
    public List<Book> getBooksByCriteria(SearchType type, String criteria, int currentPage, int booksPerPage) {
        Connection connection = DatabaseConnection.getConnection();
        BooksDAO booksDAO = (BooksDAO) sqlFactory.getDao(connection,Book.class);
        booksDAO.setPages(currentPage,booksPerPage);
        List<Book> list;
        try {
            switch (type){
                case GENRE: list = booksDAO.getByGenre(Integer.parseInt(criteria));break;
                case LETTER: list = booksDAO.getBooksByLetter(criteria); break;
                case TITLE: list = booksDAO.getBooksBySearch(criteria, SearchType.TITLE); break;
                case AUTHOR: list = booksDAO.getBooksBySearch(criteria, SearchType.AUTHOR); break;
                case ADMIN: list = booksDAO.getBooksByAdmin(); break;
                case USER: list = booksDAO.getBooksByUser(criteria); break;
                case ALL:  list = booksDAO.getAll(); break;
                default:{
                    LoggerFactory.getLogger(OutputServiceImpl.class).warn("There is no such case for that type!");
                    list = null;
                }
            }
            booksCount = booksDAO.getBooksCount();
        } finally {
            DbUtils.closeQuietly(connection);
        }
        return list;
    }

    @Override
    public List<Book> currentRequest(int currentPage, int bookPerPage) {
        Connection connection = DatabaseConnection.getConnection();
        BooksDAO booksDAO = (BooksDAO) sqlFactory.getDao(connection,Book.class);
        booksDAO.setPages(currentPage,bookPerPage);
        List<Book> list;
        try {
          list = booksDAO.currentRequest();
        } finally {
            DbUtils.closeQuietly(connection);
        }
        return list;
    }

    @Override
    public int getBooksCount() {
        return booksCount;
    }

    @Override
    public Book getBookByPK(int id) {
        return getByPK(Book.class,id);
    }

    @Override
    public Author getAuthorByPK(int id) {
        return getByPK(Author.class,id);
    }

    @Override
    public Genre getGenreByPK(int id) {
        return getByPK(Genre.class,id);
    }

    @Override
    public User getUserByPk(String userId) {
        return getByPK(User.class,userId);
    }

    @Override
    public Publisher getPublisherByPK(int id) {
        return getByPK(Publisher.class,id);
    }



    /**
     * @param dtoClass proper entity
     * @return all objects of given entity in db
     */
    private List getAll(Class<? extends Identified> dtoClass) {
        Connection connection = DatabaseConnection.getConnection();
        GenericDao genericDao = sqlFactory.getDao(connection,dtoClass);
        List list;
        try {
            list = genericDao.getAll();
        } finally {
            DbUtils.closeQuietly(connection);
        }
        return list;
    }

    private <T,K> T getByPK(Class<T> dtoClass, K id) {
        Connection connection = DatabaseConnection.getConnection();
        GenericDao<T> genericDao =  sqlFactory.getDao(connection,dtoClass);
        T book;
        try {
            book = genericDao.getByPK(id);
        } finally {
            DbUtils.closeQuietly(connection);
        }
        return book;
    }


}
