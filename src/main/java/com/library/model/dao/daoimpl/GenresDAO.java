package com.library.model.dao.daoimpl;

import com.library.model.dao.abstractdao.AbstractJDBCDao;
import com.library.model.entities.Genre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Genres dao.
 */
public class GenresDAO extends AbstractJDBCDao<Genre> {


    /**
     * Instantiates a new Genres dao.
     *
     * @param connection the connection
     */
    public GenresDAO(Connection connection) {
        super(connection);
    }

    @Override
    public String getSelectQuery() {
        return "select * from genre ";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO genre (name) " +
                " VALUES (?) ";
    }

    @Override
    public String getUpdateQuery() {
        return "";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE from genre where id = ? ";
    }


    @Override
    protected List<Genre> parseResultSet(ResultSet rs) throws SQLException {

        List<Genre> result = new ArrayList<>();
        try {
            while (rs.next()) {
                Genre genre = new Genre();
                genre.setName(rs.getString("name"));
                genre.setId(rs.getInt("id"));
                result.add(genre);
            }
        } catch (Exception e) {
            daoLogger.error("GenreDAO parseResultSet error",e);
            throw new SQLException(e);
        }

        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Genre object) throws SQLException {
        try {
             statement.setString(1,object.getName());
        } catch (Exception e) {
            daoLogger.error("GenreDAO prepareStatementForInsert error",e);
            throw new SQLException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Genre object) throws SQLException {
        throw new UnsupportedOperationException();
    }

}
