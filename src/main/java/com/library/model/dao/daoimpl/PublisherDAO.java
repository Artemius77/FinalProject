package com.library.model.dao.daoimpl;

import com.library.model.dao.abstractdao.AbstractJDBCDao;
import com.library.model.entities.Publisher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Publisher dao.
 */
public class PublisherDAO  extends AbstractJDBCDao<Publisher> {


    /**
     * Instantiates a new Publisher dao.
     *
     * @param connection the connection
     */
    public PublisherDAO(Connection connection) {
        super(connection);
    }

    @Override
    public String getSelectQuery() {
        return "select * from publisher ";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO publisher (name) " +
                " VALUES (?) ";
    }

    @Override
    public String getUpdateQuery() {
        return "";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE from publisher where id = ? ";
    }



    @Override
    protected List<Publisher> parseResultSet(ResultSet rs) throws SQLException {
        List<Publisher> result = new ArrayList<>();
        try {
            while (rs.next()) {
                Publisher publisher = new Publisher();
                publisher.setName(rs.getString("name"));
                publisher.setId(rs.getInt("id"));
                result.add(publisher);
            }
        } catch (Exception e) {
            daoLogger.error("PublisherDAO parseResultSet error",e);
            throw new SQLException(e);
        }


        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Publisher object) throws SQLException {
        try {
            statement.setString(1,object.getName());
        } catch (Exception e) {
            daoLogger.error("PublisherDAO prepareStatementForInsert error",e);
            throw new SQLException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Publisher object) throws SQLException {
        throw new UnsupportedOperationException();
    }
}
