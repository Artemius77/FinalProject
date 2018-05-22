package com.library.model.dbutils;

import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *  Database connection via DataSource.
 */
public class DatabaseConnection {

    private final static String JNDI_LOOKUP = "java:/comp/env/jdbc/DBPoolConnect";
    private static Connection conn;
    private static DataSource dataSource;
    private static InitialContext ic;


    /**
     * Gets connection.
     *
     * @return the connection
     */
    public static Connection getConnection() {

        try {
            ic = new InitialContext();
            dataSource = (DataSource) ic.lookup(JNDI_LOOKUP); // !!!
            conn = dataSource.getConnection();
        } catch (NamingException | SQLException e) {
            LoggerFactory.getLogger(DatabaseConnection.class).error("getConnection method failed!", e);
        }

        return conn;
    }

}
