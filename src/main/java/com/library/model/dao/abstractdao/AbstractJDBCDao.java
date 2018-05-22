package com.library.model.dao.abstractdao;

import com.library.model.dao.daofactory.DaoFactory;
import com.library.model.entities.Identified;
import org.apache.commons.dbutils.DbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * The type Abstract jdbc dao.
 *
 * @param <T> the type parameter
 */
public abstract class AbstractJDBCDao<T extends Identified>  implements GenericDao<T>{

    /**
     *  Connection.
     */
    protected Connection connection;
    /**
     * The Parent factory.
     */
    protected DaoFactory<Connection> parentFactory;
    /**
     * The constant daoLogger.
     */
    protected final static Logger daoLogger = LoggerFactory.getLogger(AbstractJDBCDao.class);

    /**
     * All records
     * <p/>
     * SELECT * FROM [Table]
     *
     * @return the select query
     */
    public abstract String getSelectQuery();


    /**
     * Insert record
     * <p/>
     * INSERT INTO [Table] ([column, column, ...]) VALUES (?, ?, ...);
     *
     * @return the create query
     */
    public abstract String getCreateQuery();

    /**
     * Update recorde
     * <p/>
     * UPDATE [Table] SET [column = ?, column = ?, ...] WHERE id = ?;
     *
     * @return the update query
     */
    public abstract String getUpdateQuery();

    /**
     * Delete record.
     * <p/>
     * DELETE FROM [Table] WHERE id= ?;
     *
     * @return the delete query
     */
    public abstract String getDeleteQuery();

    /**
     * Parse ResultSet and return object's list of appropriate ResultSet.
     *
     * @param rs the result set
     * @return the list
     * @throws SQLException the sql exception
     */
    protected abstract List<T> parseResultSet(ResultSet rs) throws SQLException;

    /**
     * Set argument for insert query.
     *
     * @param statement the statement
     * @param object    the object
     * @throws SQLException the sql exception
     */
    protected abstract void prepareStatementForInsert(PreparedStatement statement, T object) throws SQLException;

    /**
     * Set argument for update query.
     *
     * @param statement the statement
     * @param object    the object
     * @throws SQLException the sql exception
     */
    protected abstract void prepareStatementForUpdate(PreparedStatement statement, T object) throws SQLException;

    @Override
    public <K> T getByPK(K key) {
        List<T> list = null;
        String sql = getSelectQuery();
        sql += " WHERE id = ?";
        PreparedStatement statement = null;
        ResultSet rs = null;
        try  {
             statement = connection.prepareStatement(sql);

             if (key instanceof Integer)
                statement.setInt(1, (Integer) key);
             else if (key instanceof String)
                 statement.setString(1, (String) key);
             else {
                 daoLogger.warn("Wrong key value!");
                 return null;
             }

             rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (SQLException e) {
            daoLogger.error("Error in getByPK method !", e);
        } finally {
            DbUtils.closeQuietly(rs);
            DbUtils.closeQuietly(statement);
        }

        if (list == null || list.size() == 0) {
            daoLogger.warn("getByPK method found nothing!");
            return null;
        }

        if (list.size() > 1) {
            daoLogger.error("getByPK method received more than one object! {}");
        }

        return list.get(0);
    }



    @Override
    public  List<T> getAll() {
        List<T> list = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet rs = null;
        try  {
             statement = connection.prepareStatement(getSelectQuery());
             rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (SQLException e) {
            daoLogger.error("Error in getAll method!",e);
        } finally {
            DbUtils.closeQuietly(rs);
            DbUtils.closeQuietly(statement);

        }
        return list;

    }

    @Override
    public void persist(T object) throws SQLException {
        String sql = getCreateQuery();
        PreparedStatement statement = null;
        try  {

            statement = connection.prepareStatement(sql);
            prepareStatementForInsert(statement, object);
            int count = statement.executeUpdate();

            if (count != 1) {
                daoLogger.error("On persist modify more then 1 record: {}",count);
            }

        } catch (SQLException e) {
            daoLogger.error("Error in persist method",e);
            throw new SQLException();
        } finally {
            DbUtils.closeQuietly(statement);
        }
    }

    @Override
    public void update(T object) throws SQLException {
        String sql = getUpdateQuery();
        PreparedStatement statement = null;
        try  {
            statement = connection.prepareStatement(sql);
            prepareStatementForUpdate(statement, object);
            int count = statement.executeUpdate();
            if (count != 1) {
                daoLogger.error("On update modify more then 1 record: {}",count);
            }
        } catch (SQLException e) {
            daoLogger.error("Error in update method",e);
            throw new SQLException();
        } finally {
            DbUtils.closeQuietly(statement);
        }

    }

    @Override
    public void delete(T object)  {
        String sql = getDeleteQuery();
        PreparedStatement statement = null;
        try  {
            statement = connection.prepareStatement(sql);
            statement.setObject(1, object.getId());
            int count = statement.executeUpdate();
            if (count != 1) {
                daoLogger.error("On delete modify more then 1 record: {}", count);
            }
        } catch (SQLException e) {
            daoLogger.error("Error in delete method", e);
        } finally {
            DbUtils.closeQuietly(statement);
        }
    }



  /* protected <K> Identified getDependence(Class<? extends Identified> dtoClass, K pk) throws SQLException {
        return (Identified) parentFactory.getDao(connection,dtoClass).getByPK(pk);
    }*/

    /**
     * Instantiates a new Abstract jdbc dao.
     *
     * @param connection the connection
     */
    public AbstractJDBCDao(Connection connection) {
        this.connection = connection;
    }

    /**
     * Instantiates a new Abstract jdbc dao.
     *
     * @param parentFactory the parent factory
     * @param connection    the connection
     */
    public AbstractJDBCDao(DaoFactory<Connection> parentFactory, Connection connection) {
        this.parentFactory = parentFactory;
        this.connection = connection;
    }

}



