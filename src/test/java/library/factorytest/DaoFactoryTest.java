package library.factorytest;

import com.library.model.dao.abstractdao.GenericDao;
import com.library.model.entities.Book;
import com.library.model.entities.Genre;
import com.library.model.entities.Publisher;
import library.utils.AbstractConnection;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

public class DaoFactoryTest extends AbstractConnection {

    @Before
    public void setUp() throws SQLException {
        connection = dataSource.getConnection();
        connection.setAutoCommit(false);

    }

    @After
    public void tearDown() throws SQLException {
        connection.close();
    }

    @Test
    public void testDaoFactory()  {
        GenericDao dao = factory.getDao(connection,Book.class);
        Assert.assertNotNull(dao);

        GenericDao<Publisher> publisherDao = factory.getDao(connection,Publisher.class);
        Assert.assertEquals("Wrong dao return",publisherDao.getAll().get(0).getClass(),Publisher.class);

        GenericDao<Genre> genreDao = factory.getDao(connection,Genre.class);
        Assert.assertEquals("Wrong dao return",genreDao.getAll().get(0).getClass(),Genre.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDaoFactoryFail()  {
        factory.getDao(connection,String.class);
    }
}
