/**
 * 
 */
package world.we.deserve.bolt.dao;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.stereotype.Component;

import world.we.deserve.CassandraComponentScan;

/**
 * @author Miguel Ángel Dev (miguelangelprogramacion@gmail.com)
 *
 */
@Component
public class SpringCassandraCqlStateFactory implements FactoryBean<CassandraCqlState>{
	
	@Autowired
	CassandraOperations cassandraOperations;
	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.FactoryBean#getObject()
	 */
	@Override
	public CassandraCqlState getObject() throws Exception {
					
		CassandraCqlState state = new CassandraCqlState();
		state.setCassandraOperations(cassandraOperations);
		return state;
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.FactoryBean#getObjectType()
	 */
	@Override
	public Class<?> getObjectType() {
		return CassandraCqlState.class;
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.FactoryBean#isSingleton()
	 */
	@Override
	public boolean isSingleton() {
		return false;
	}

}
