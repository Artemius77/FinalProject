package library.utilstest;

import com.library.controller.utils.classes.FormBean;
import org.junit.Assert;
import org.junit.Test;

public class FormBeanTest {

    @Test
    public void testFormBean() throws Exception {
        FormBean formBean = new FormBean("","fdsa","","","","","");
        formBean.validate();

        Assert.assertEquals("FormBean don't find all errors",7,formBean.getErrors().size());

        formBean = new FormBean("fds","23","123","fds","fsa","dsa","fds");
        Assert.assertTrue("FormBean didnt work",formBean.getErrors().isEmpty());
    }

}
