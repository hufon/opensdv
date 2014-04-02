package fr.esdeve;

import org.restlet.Component;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws BeansException, Exception
    {
    	 ApplicationContext springContext = new ClassPathXmlApplicationContext(
    		        new String[] { "/META-INF/spring/applicationContext.xml", "/spring/applicationContext-server.xml" });
    	    // obtain the Restlet component from the Spring context and start it
    	    ((Component) springContext.getBean("top")).start();
    }
}
