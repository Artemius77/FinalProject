package library.factorytest;


import com.library.controller.action.BooksManageAction;
import com.library.controller.action.BooksOutputAction;
import com.library.controller.factory.ActionFactory;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ActionFactoryTest {


    @Test
    public void testActionFactory()  {
        HttpServletRequest mockRequest = Mockito.mock(HttpServletRequest.class);
        //setup the behaviour here
        Mockito.when(mockRequest.getMethod()).thenReturn("GET");
        Mockito.when(mockRequest.getPathInfo()).thenReturn("/main");

        Assert.assertEquals("Action factory failed",
               BooksOutputAction.class, ActionFactory.getAction(mockRequest).getClass());

        Mockito.when(mockRequest.getPathInfo()).thenReturn("/notexist");
        Assert.assertNull(ActionFactory.getAction(mockRequest));

        Mockito.when(mockRequest.getPathInfo()).thenReturn("/add");
        Assert.assertEquals("Action factory failed",
                BooksManageAction.class, ActionFactory.getAction(mockRequest).getClass());
    }

    @Test
    public void testAction()  {
        HttpServletRequest mockRequest = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = Mockito.mock(HttpServletResponse.class);
        //setup the behaviour here
        Mockito.when(mockRequest.getMethod()).thenReturn("GET");
        Mockito.when(mockRequest.getPathInfo()).thenReturn("/index");

        String view = ActionFactory.getAction(mockRequest).execute(mockRequest,mockResponse);
        Assert.assertEquals("Action return wrong view","404",view);

        Mockito.when(mockRequest.getMethod()).thenReturn("GET");
        Mockito.when(mockRequest.getPathInfo()).thenReturn("/add");

        view = ActionFactory.getAction(mockRequest).execute(mockRequest,mockResponse);
        Assert.assertEquals("Action return wrong view","main",view);

    }
}
