package com.library.model.dao.daoimpl;

import com.library.model.dao.abstractdao.AbstractJDBCDao;
import com.library.model.entities.User;
import com.library.controller.utils.enums.Role;
import org.apache.commons.dbutils.DbUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The type User dao.
 */
public class UserDAO extends AbstractJDBCDao<User> {


    @Override
    public String getSelectQuery() {
        return "SELECT id, email, phone_number, role_name FROM users inner join user_roles " +
                "using (id) ";
    }


    /**
     * Instantiates a new User dao.
     *
     * @param connection the connection
     */
    public UserDAO(Connection connection) {
        super(connection);
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO users (id, user_pass) " +
                " VALUES (?, ?) ";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE users SET email = ?, phone_number = ? where id = ? ";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE from users where id = ? ";
    } //!!

    /**
     * Change user role by name.
     *
     * @param name user name
     * @param role result role
     * @throws SQLException  sql exception
     */
    public void changeRole(String name, Role role) throws  SQLException{

        PreparedStatement statement = null;

        try  {

            statement = connection.prepareStatement("update user_roles set role_name = ? where id = ? ");
            statement.setString(1,role.toString().toLowerCase());
            statement.setString(2,name);
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new SQLException("On update modify more then 1 record: " + count);
            }

        } catch (SQLException e) {
            daoLogger.error("UserDAO changeRole method error", e);
            throw new SQLException();
        } finally {
            DbUtils.closeQuietly(statement);
        }

    }

    /**
     * Order book by id.
     *
     * @param bookId the book id
     * @param userId the user id
     */
    public void takeBook(int bookId, String userId) {
        PreparedStatement statement = null;
        try  {

            statement = connection.prepareStatement("update book set user_name = ?, take_date = now(), " +
                    " expire_date = DATE_ADD(now(), INTERVAL 30 DAY)  where id = ? ");

            statement.setString(1,userId);
            statement.setInt(2,bookId);
            int count = statement.executeUpdate();

            if (count != 1) {
                throw new SQLException("On update modify more then 1 record: " + count);
            }

        } catch (SQLException e) {
            daoLogger.error("UserDAO takeBook method error", e);
        } finally {
            DbUtils.closeQuietly(statement);
        }
    }

    /**
     * Insert role.
     *
     * @param userName the user name
     * @param role     the role
     * @throws SQLException the sql exception
     */
    public void insertRole(String userName, Role role) throws SQLException {
        PreparedStatement statement = null;
        try  {

            statement = connection.prepareStatement("insert into user_roles(id, role_name) values(?, ?) ");
            statement.setString(1,userName);
            statement.setString(2,role.toString().toLowerCase());
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new SQLException("On update modify more then 1 record: " + count);
            }

        } catch (SQLException e) {
            daoLogger.error("UserDAO insertRole method error", e);
            throw new SQLException();
        } finally {
            DbUtils.closeQuietly(statement);
        }
    }

    /**
     * Make book free to order again.
     *
     * @param bookId the book id
     */
    public void releaseBook(int bookId) {
        PreparedStatement statement = null;
        try  {
            statement = connection.prepareStatement("update book set user_name = null, take_date = null, " +
                    " expire_date=null where id = ? ");
            statement.setInt(1,bookId);
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new SQLException("On update modify more then 1 record: " + count);
            }
        } catch (SQLException e) {
            daoLogger.error("UserDAO releaseBook method error", e);
        } finally {
            DbUtils.closeQuietly(statement);
        }
    }

    @Override
    protected List<User> parseResultSet(ResultSet rs) throws SQLException {
        List<User> result = new ArrayList<>();
        try {
            while (rs.next()) {

                User user = new User();
                user.setName(rs.getString("id"));
                user.setEmail(rs.getString("email"));
                user.setPhonenumber(rs.getString("phone_number"));
                user.setRole(Role.valueOf(rs.getString("role_name").toUpperCase()));
                result.add(user);

            }

        } catch (Exception e) {
            daoLogger.error("UserDAO parseResultSet method error", e);
            throw new SQLException(e);
        }


        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, User object) throws SQLException {
        try {
            statement.setString(1,object.getId());
            statement.setString(2,object.getPassword());
        }
        catch (Exception e) {
            daoLogger.error("UserDAO prepareStatementForInsert method error", e);
            throw new SQLException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, User object) throws SQLException {
        try {

            statement.setString(3, object.getId());
            statement.setString(1, object.getEmail());
            statement.setString(2, object.getPhonenumber());

        } catch (Exception e) {
            daoLogger.error("UserDAO prepareStatementForUpdate method error", e);
            throw new SQLException(e);
        }
    }
}
