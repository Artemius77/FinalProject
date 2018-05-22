package library.utils;

import com.library.model.dao.daofactory.DaoFactory;
import com.library.model.dao.daofactory.MySqlDaoFactory;
import org.apache.commons.dbcp.BasicDataSource;
import org.junit.BeforeClass;

import javax.sql.DataSource;
import java.sql.Connection;

public abstract class AbstractConnection {

    protected static DataSource dataSource;
    protected static final DaoFactory<Connection> factory = MySqlDaoFactory.getInstance();
    protected   Connection connection;

    @BeforeClass
    public static void setUpConnect() throws Exception
    {
        Class.forName("com.mysql.jdbc.Driver");

        BasicDataSource ds = new BasicDataSource();
        ds.setUrl("jdbc:mysql://localhost:3306/library");
        ds.setUsername("root");
        ds.setPassword("root");
        ds.setMinIdle(5);
        ds.setMaxIdle(10);
        ds.setMaxOpenPreparedStatements(100);

        dataSource = ds;
    }



}
