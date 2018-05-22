package com.library.model.dao.daoimpl;

import com.library.model.dao.daofactory.DaoFactory;
import com.library.model.dao.abstractdao.AbstractJDBCDao;
import com.library.model.dao.abstractdao.GenericDao;
import com.library.model.entities.*;
import com.library.controller.utils.enums.SearchType;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.dbutils.DbUtils;
//import org.apache.commons.codec.binary.Base64;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Books dao.
 */
public class BooksDAO extends AbstractJDBCDao<Book> {


    private static String currentSQL;
    private int currentPage;
    private  int recordsPerPage;


    /**
     * Set pages for pagination
     *
     * @param currentPage    the current page
     * @param recordsPerPage the records per page
     */
    public void setPages(int currentPage,int recordsPerPage){
        this.currentPage = currentPage;
        this.recordsPerPage = recordsPerPage;
    }


    /**
     * Instantiates a new Books dao.
     *
     * @param parentFactory the parent factory
     * @param connection    the connection
     */
    public BooksDAO(DaoFactory<Connection> parentFactory, Connection connection) {
        super(parentFactory, connection);
    }

    @Override
    public String getSelectQuery() {
        return "select b.id, b.name, content, page_count, isbn, genre_id, author_id, " +
                "publish_year, publisher_id, image, user_name, take_date, expire_date from book b ";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO book (name, content, page_count, isbn, genre_id, author_id, " +
                "publish_year, publisher_id, image) " +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) ";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE book SET name = ?, content = ?, page_count = ?, isbn = ?, " +
                "genre_id = ?, author_id = ?, publish_year = ?, publisher_id = ?, " +
                " image = ? WHERE id = ? ";
    }


    @Override
    public String getDeleteQuery() {
        return "DELETE from book where id = ?";
    }

    /**
     *
     * @return previous requested list
     */
    public List<Book> currentRequest() {
         return getAll(currentSQL);
    }

    @Override
    protected List<Book> parseResultSet(ResultSet rs) throws SQLException {
        List<Book> result = new ArrayList<>();
        try {

            GenericDao<Genre> genreGenericDao = parentFactory.getDao(connection,Genre.class);
            GenericDao<Author> authorGenericDao = parentFactory.getDao(connection,Author.class);
            GenericDao<Publisher> publisherGenericDao = parentFactory.getDao(connection,Publisher.class);
            GenericDao<User> userGenericDao = parentFactory.getDao(connection,User.class);

            while (rs.next()) {

                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setName(rs.getString("name"));
                book.setGenre(genreGenericDao.getByPK(rs.getInt("genre_id")));
                book.setIsbn(rs.getString("isbn"));
                book.setAuthor(authorGenericDao.getByPK(rs.getInt("author_id")));
                book.setPageCount(rs.getInt("page_count"));
                book.setPublishDate(rs.getLong("publish_year"));
                book.setPublisher(publisherGenericDao.getByPK(rs.getInt("publisher_id")));
                book.setImage(Base64.encodeBase64String(rs.getBytes("image")));
                book.setContent(rs.getBytes("content"));
                if (rs.getString("user_name") != null)
                    book.setUser(userGenericDao.getByPK(rs.getString("user_name")));
                if (rs.getDate("take_date") != null)
                    book.setTakeDate((rs.getDate("take_date")));
                if (rs.getDate("expire_date") != null)
                    book.setExpireDate((rs.getDate("expire_date")));


                result.add(book);

            }

        } catch (Exception e) {
            daoLogger.error("Error in BooksDAO parseResultSet",e);
            throw new SQLException(e);
        }

        return result;

    }

    /**
     * Gets by genre.
     *
     * @param id genre id
     * @return List of books by specific genre
     */
    public List<Book> getByGenre(int id) {
        currentSQL = getSelectQuery() + "where genre_id = " + id + " order by b.name";
        return getAll(currentSQL);
    }

    /**
     * Gets books by letter.
     *
     * @param letter the letter
     * @return  books by letter
     */
    public List<Book> getBooksByLetter(String letter) {
        currentSQL = getSelectQuery() + "where substr(b.name,1,1)='" + letter + "' order by b.name ";
        return getAll(currentSQL);
    }


    @Override
    public List<Book> getAll(){
        currentSQL = getSelectQuery();
        return getAll(currentSQL);
    }


    /**
     * Gets books by search.
     *
     * @param searchStr  search string
     * @param type  Search type
     * @return  books by search
     */
    public List<Book> getBooksBySearch(String searchStr, SearchType type) {
        StringBuilder sql = new StringBuilder(getSelectQuery() + " inner join author a on b.author_id = a.id ");

        if (type == SearchType.AUTHOR) {
            sql.append("where lower(a.name) like '%").append(searchStr.toLowerCase()).append("%' order by b.name ");

        } else if (type == SearchType.TITLE) {
            sql.append("where lower(b.name) like '%").append(searchStr.toLowerCase()).append("%' order by b.name ");
        }

        currentSQL = sql.toString();
        return getAll(currentSQL);
    }

    /**
     *
     * Parse given sqlQuery with pagination
     *
     * @param sqlQuery to parse
     * @return  list of books
     */
    private List<Book> getAll(String sqlQuery) {
        List<Book> list = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet rs = null;

        int start = currentPage * recordsPerPage - recordsPerPage;

        try  {

            statement = connection.prepareStatement(sqlQuery + " limit ?, ?");
            statement.setInt(1,start);
            statement.setInt(2,recordsPerPage);
            rs = statement.executeQuery();
            list = parseResultSet(rs);

        } catch (SQLException e) {
           daoLogger.error("private getAll BooksDAO method failed", e);
        } finally {
            DbUtils.closeQuietly(rs);
            DbUtils.closeQuietly(statement);
        }
        return list;
    }



    /**
     * Gets books by user.
     *
     * @param userId the user id
     * @return  books by user
     */
    public List<Book> getBooksByUser(String userId) {
        currentSQL = getSelectQuery() + " where user_name = '" + userId + "' order by name ";
        return getAll(currentSQL);
    }

    /**
     * Gets books by admin.
     *
     * @return  books by admin
     */
    public List<Book> getBooksByAdmin() {
        currentSQL = getSelectQuery() + " where user_name is not null ";
        return getAll(currentSQL);
    }


    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Book object) throws SQLException {
        try {
            statement.setString(1, object.getName());
            statement.setBytes(2, object.getContent());
            statement.setInt(3, object.getPageCount());
            statement.setString(4, object.getIsbn());
            statement.setLong(5, object.getGenre().getId());
            statement.setLong(6, object.getAuthor().getId());
            statement.setLong(7, object.getPublishDate());
            statement.setLong(8, object.getPublisher().getId());
            statement.setBytes(9, Base64.decodeBase64(object.getImage()));
        } catch (Exception e) {
            daoLogger.error("BooksDAO prepareStatementForInsert error",e);
            throw new SQLException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Book object) throws SQLException {
        try {
            statement.setString(1, object.getName());
            statement.setBytes(2, object.getContent());
            statement.setInt(3, object.getPageCount());
            statement.setString(4, object.getIsbn());
            statement.setLong(5, object.getGenre().getId());
            statement.setLong(6, object.getAuthor().getId());
            statement.setLong(7, object.getPublishDate());
            statement.setLong(8, object.getPublisher().getId());
            statement.setBytes(9, Base64.decodeBase64(object.getImage()));
            statement.setLong(10, object.getId());
        } catch (Exception e) {
            daoLogger.error("BooksDAO prepareStatementForUpdate error",e);
            throw new SQLException(e);
        }

    }

    /**
     * Gets books count.
     *
     * @return  books count
     */
    public int getBooksCount() {
        PreparedStatement statement = null;
        ResultSet rs = null;
        int count = 0;
        try  {

            statement = connection.prepareStatement(currentSQL);
            rs = statement.executeQuery();
            rs.last();
            count = rs.getRow();

        } catch (SQLException e) {
            daoLogger.error("BooksCount method error",e);
        } finally {
            DbUtils.closeQuietly(rs);
            DbUtils.closeQuietly(statement);
        }

        return count;
    }
}

