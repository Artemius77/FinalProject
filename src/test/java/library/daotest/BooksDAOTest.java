package library.daotest;

import com.library.model.dao.daofactory.DaoFactory;
import com.library.model.dao.daofactory.MySqlDaoFactory;
import com.library.model.dao.daoimpl.BooksDAO;

import com.library.model.entities.*;
import com.library.controller.utils.enums.SearchType;
import library.utils.AbstractConnection;
import org.apache.commons.dbcp.BasicDataSource;
import org.junit.*;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class BooksDAOTest extends AbstractConnection {

    private  BooksDAO booksDAO;


    @Before
    public  void setUpClass() throws Exception
    {
        connection = dataSource.getConnection();
        connection.setAutoCommit(false);
        booksDAO = (BooksDAO) factory.getDao(connection,Book.class);
    }

    @After
    public void tearDown() throws SQLException {
        connection.rollback();
        connection.close();
    }

    @Test
    public void testBooksCount()  {

        int count = 0;
        int i = 1;
        int records = 100;

        do {
            booksDAO.setPages(i,records);
            count += booksDAO.getAll().size();
            i++;
        } while (booksDAO.getAll().size() != 0);

        int bookCount = booksDAO.getBooksCount();
        Assert.assertEquals("booksCount didnt work",count,bookCount);
    }

    @Test
    public void testPersist() throws Exception {

        booksDAO.setPages(1,1);
        List<Book> list = booksDAO.getAll();
        int booksCount = booksDAO.getBooksCount();
        System.out.println(booksCount);
         Book utilBook = list.get(0);

        Assert.assertNotNull("utilBook is null", utilBook);

        Book book = new Book();
        book.setName("test");
        book.setPageCount(34);
        book.setIsbn("test");
        book.setPublishDate(1998);
        book.setAuthor(utilBook.getAuthor());
        book.setGenre(utilBook.getGenre());
        book.setPublisher(utilBook.getPublisher());

        booksDAO.persist(book);
        int booksCount2 = booksDAO.getBooksCount();
        Assert.assertEquals("Persist book didnt happen.",1,booksCount2-booksCount);
    }

    @Test
    public void testGetByPK()  {

        booksDAO.setPages(1,1);
        List<Book> list = booksDAO.getAll();
        Integer id =  list.get(0).getId();

        Identified dto = booksDAO.getByPK(id);

        Assert.assertEquals("Id is different.",id,dto.getId());
    }

    @Test
    public void testDelete() throws Exception {
        booksDAO.setPages(1,1);
        List<Book> list = booksDAO.getAll();

        Book utilBook = list.get(0);
        utilBook.setIsbn("test");

        booksDAO.persist(utilBook);
        int booksCount = booksDAO.getBooksCount();

        booksDAO.delete(utilBook);
        int booksCount2 = booksDAO.getBooksCount();

        Assert.assertEquals(1, booksCount - booksCount2);
    }

    @Test
    public void testGetAll()  {
        List<Book> list = booksDAO.getAll();
        int bookCount = booksDAO.getBooksCount();

        booksDAO.setPages(1,bookCount);
        list = booksDAO.getAll();

        Assert.assertEquals("getAll didnt work.",bookCount,list.size());
    }

    @Test
    public void testGetByGenre()  {
        booksDAO.getAll();
        booksDAO.setPages(1,booksDAO.getBooksCount());

        Genre searchGenre = booksDAO.getAll().get(0).getGenre();
        List<Book> bookByGenre = booksDAO.getByGenre(searchGenre.getId());

        for (Book book :
                bookByGenre) {
            Assert.assertEquals("search by genre failed",searchGenre.getId(), book.getGenre().getId());
        }

    }

    @Test
    public void testGetByLetter()  {
        booksDAO.getAll();
        booksDAO.setPages(1,booksDAO.getBooksCount());

        Book utilBook = booksDAO.getAll().get(0);
        String letter = String.valueOf(utilBook.getName().charAt(0));
        List<Book> booksByLetter = booksDAO.getBooksByLetter(letter);


        for (Book book :
                booksByLetter) {
            Assert.assertEquals("search by letter failed",letter, String.valueOf(book.getName().charAt(0)));
        }

    }

    @Test
    public void testGetBySearch()  {
        booksDAO.getAll();
        booksDAO.setPages(1,booksDAO.getBooksCount());

        Book utilBook = booksDAO.getAll().get(0);
        String author = (utilBook.getAuthor().getName());
        List<Book> booksByAuthor = booksDAO.getBooksBySearch(author,SearchType.AUTHOR);

        Assert.assertNotNull(booksByAuthor);

        for (Book book :
                booksByAuthor) {
            Assert.assertEquals("search by author failed",author, book.getAuthor().getName());
        }


        String bookName = (utilBook.getName().substring(0,2));
        List<Book> booksByName = booksDAO.getBooksBySearch(bookName,SearchType.TITLE);

        for (Book book :
                booksByName) {
            Assert.assertEquals("search by name failed",bookName, book.getName().substring(0,2));
        }
    }

    @Test
    public void testGetByUser()  {
        booksDAO.getAll();
        booksDAO.setPages(1,booksDAO.getBooksCount());

        Book utilBook = booksDAO.getAll().stream().filter(book -> book.getUser() != null).findFirst().get();
        List<Book> userBooks = booksDAO.getBooksByUser(utilBook.getUser().getId());
        System.out.println(utilBook.getUser().getId() + " " + userBooks);

        for (Book book :
                userBooks) {
            Assert.assertEquals("search by user failed",utilBook.getUser().getId(), book.getUser().getId());
        }

    }

    @Test
    public void testGetByAdmin()  {
        booksDAO.getAll();
        booksDAO.setPages(1,booksDAO.getBooksCount());

        List<Book> adminBooks = booksDAO.getBooksByAdmin();

        for (Book book :
                adminBooks) {
            Assert.assertNotNull("search by admin failed",book.getUser());
        }

    }

}
