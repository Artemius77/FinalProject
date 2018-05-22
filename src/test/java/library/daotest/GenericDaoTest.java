package library.daotest;

import com.library.model.dao.abstractdao.GenericDao;
import com.library.model.entities.Identified;
import com.library.model.entities.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;


import java.lang.reflect.Constructor;
import java.util.List;

@RunWith(Parameterized.class)
public abstract class GenericDaoTest<Context> {
    /**
     * Класс тестируемого дао объекта
     */
    protected Class daoClass;

    /**
     * Экземпляр доменного объекта, которому не соответствует запись в системе хранения
     */
    protected Identified notPersistedDto;

    /**
     * Экземпляр тестируемого дао объекта
     */
    public abstract GenericDao dao();

    /**
     * Контекст взаимодействия с системой хранения данных
     */
    public abstract Context context();



    @Test
    public void testPersist() throws Exception {

        List<Identified> list = dao().getAll();
         dao().persist(notPersistedDto);

        List<Identified> list2 = dao().getAll();

        Assert.assertEquals("After persist list size didnt change.", 1,list2.size()-list.size());
    }

    @Test
    public void testGetByPK()  {

        List<Identified> list = dao().getAll();
        Object id =  list.get(list.size()-1).getId();
        Assert.assertNotNull(id);

        Identified dto = (Identified) dao().getByPK(id);

        Assert.assertEquals(id,dto.getId());
    }

    @Test
    public void testDelete() throws Exception {
        Constructor cs;
        Identified persistObject;
        if (((daoClass).equals(User.class))) {
            cs = daoClass.getConstructor(String.class, String.class);
            persistObject = (Identified) cs.newInstance("яяяяя","zzzzzz");
        } else {
            cs = daoClass.getConstructor(String.class);
            persistObject = (Identified) cs.newInstance("яяяяя");
        }

        dao().persist(persistObject);
        List list = dao().getAll();
        int oldSize = list.size();
        Assert.assertTrue(oldSize > 0);

        dao().delete(list.get(list.size()-1));
        list = dao().getAll();
        Assert.assertNotNull(list);
        int newSize = list.size();

        Assert.assertEquals(1, oldSize - newSize);
    }

    @Test
    public void testGetAll()  {
        List list = dao().getAll();
        Assert.assertNotNull(list);
        Assert.assertTrue(list.size() > 0);
    }

    public GenericDaoTest(Class clazz, Identified<Integer> notPersistedDto) {
        this.daoClass = clazz;
        this.notPersistedDto = notPersistedDto;
    }
}
