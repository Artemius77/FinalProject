package com.library.model.dao.daoimpl;

import com.library.model.dao.abstractdao.AbstractJDBCDao;
import com.library.model.entities.Author;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * The type Author dao.
 */
public class AuthorDAO extends AbstractJDBCDao<Author> {


    /**
     * Instantiates a new Author dao.
     *
     * @param connection the connection
     */
    public AuthorDAO(Connection connection) {
        super(connection);
    }

    @Override
    public String getSelectQuery() {
        return "select * from author ";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO author (name) " +
                " VALUES (?) ";
    }

    @Override
    public String getUpdateQuery() {
        return "";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE from author where id = ? ";
    }


    @Override
    protected List<Author> parseResultSet(ResultSet rs) throws SQLException {
        List<Author> result = new ArrayList<>();
        try {
            while (rs.next()) {
                Author genre = new Author();
                genre.setName(rs.getString("name"));
                genre.setId(rs.getInt("id"));
                result.add(genre);
            }
        } catch (Exception e) {
            daoLogger.error("AuthorDAO parseResultSet error",e);
            throw new SQLException(e);
        }


        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Author object) throws SQLException {
        try {
            statement.setString(1,object.getName());
        } catch (Exception e) {
            daoLogger.error("AuthorDAO prepareStatementForInsert error",e);
            throw new SQLException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Author object) {
        throw new UnsupportedOperationException();
    }

}
