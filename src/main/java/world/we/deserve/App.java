package world.we.deserve;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Launcher
 *
 */
@Component
public class App 
{
	private static final Logger LOG = LoggerFactory.getLogger(App.class);
	
    public static void main( String[] args )
    {
    	ApplicationContext context = new AnnotationConfigApplicationContext(StormCassandraComponentScan.class);    	
    	App app = context.getBean(App.class);
    	app.greeting();
    }

	private void greeting() {		
		LOG.info("Greeting");
	}
}
