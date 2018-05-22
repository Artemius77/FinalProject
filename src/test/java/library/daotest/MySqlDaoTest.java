package library.daotest;


import com.library.model.dao.daofactory.DaoFactory;
import com.library.model.dao.daofactory.MySqlDaoFactory;
import com.library.model.dao.abstractdao.GenericDao;
import com.library.model.entities.*;
import org.apache.commons.dbcp.BasicDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runners.Parameterized;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;

public class MySqlDaoTest extends GenericDaoTest<Connection> {

    private Connection connection;
    private static DataSource dataSource;
    private GenericDao dao;
    private static final DaoFactory<Connection> factory = MySqlDaoFactory.getInstance();

    @Parameterized.Parameters
    public static Collection getParameters() {
        return Arrays.asList(new Object[][]{
                {Author.class, new Author("test")},
                {Genre.class, new Genre("test")},
                {Publisher.class, new Publisher("test")}
        });
    }

    @BeforeClass
     public static void setUpClass() throws Exception
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


    @Before
    public void setUp() throws  SQLException {
        connection = dataSource.getConnection();
        connection.setAutoCommit(false);
        dao = factory.getDao(connection, daoClass);
    }

    @After
    public void tearDown() throws SQLException {
        context().rollback();
        context().close();
    }

    @Override
    public GenericDao dao() {
        return dao;
    }

    @Override
    public Connection context() {
        return connection;
    }

    public MySqlDaoTest(Class clazz, Identified<Integer> notPersistedDto) {
        super(clazz, notPersistedDto);
    }
}
