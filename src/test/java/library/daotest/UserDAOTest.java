package library.daotest;

import com.library.model.dao.daofactory.DaoFactory;
import com.library.model.dao.daofactory.MySqlDaoFactory;
import com.library.model.dao.daoimpl.BooksDAO;
import com.library.model.dao.daoimpl.UserDAO;
import com.library.model.entities.Book;
import com.library.model.entities.User;
import com.library.controller.utils.enums.Role;
import library.utils.AbstractConnection;
import org.apache.commons.dbcp.BasicDataSource;
import org.junit.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserDAOTest extends AbstractConnection {
    private UserDAO userDAO;


    @Before
    public  void setUpClass() throws Exception
    {
        connection = dataSource.getConnection();
        connection.setAutoCommit(false);
        userDAO = (UserDAO) factory.getDao(connection,User.class);
    }

    @After
    public void tearDown() throws SQLException {
        connection.rollback();
        connection.close();
    }

    @Test
    public void testChangeRole() throws Exception {
        User testUser = userDAO.getAll().get(0);
        Assert.assertNotNull(testUser);

        userDAO.changeRole(testUser.getId(), Role.USER);
        Assert.assertEquals(userDAO.getAll().get(0).getRole(),Role.USER);

        userDAO.changeRole(testUser.getId(), Role.READER);
        Assert.assertEquals(userDAO.getAll().get(0).getRole(),Role.READER);
    }

    @Test
    public void testTakeBook() throws Exception {
        BooksDAO booksDAO = (BooksDAO) factory.getDao(connection,Book.class);
        booksDAO.getAll();
        booksDAO.setPages(1,booksDAO.getBooksCount());
        int bookWithNoUserId = booksDAO.getAll().stream().filter(book -> book.getUser()==null).findFirst().get().getId();

        Assert.assertNull(booksDAO.getByPK(bookWithNoUserId).getUser());

        userDAO.takeBook(bookWithNoUserId,userDAO.getAll().get(0).getId());
        Assert.assertNotNull(booksDAO.getByPK(bookWithNoUserId).getUser());
    }

    @Test
    public void testPersistUser() throws Exception {
        List<User> list = userDAO.getAll();

        User user = new User("test","test");
        userDAO.persist(user);
        userDAO.insertRole(user.getId(),Role.USER);

        List<User> list2 = userDAO.getAll();

        Assert.assertEquals("After persist list size didnt change.", 1,list2.size()-list.size());
    }

    @Test
    public void testReleaseBook() throws Exception {
        BooksDAO booksDAO = (BooksDAO) factory.getDao(connection,Book.class);
        booksDAO.getAll();
        booksDAO.setPages(1,booksDAO.getBooksCount());
        int bookWithUserId = booksDAO.getAll().stream().filter(book -> book.getUser()!=null).findFirst().get().getId();

        Assert.assertNotNull(booksDAO.getByPK(bookWithUserId).getUser());

        userDAO.releaseBook(bookWithUserId);
        Assert.assertNull(booksDAO.getByPK(bookWithUserId).getUser());
    }

    @Test
    public void testGetByPK()  {
        String userId = userDAO.getAll().get(0).getId();
        Assert.assertNotNull(userId);

        User user = userDAO.getByPK(userId);
        Assert.assertNotNull(user);

        Assert.assertEquals(userId,user.getId());
    }
}
