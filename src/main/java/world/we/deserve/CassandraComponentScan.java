/**
 * 
 */
package world.we.deserve;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.config.CassandraSessionFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.convert.CassandraConverter;
import org.springframework.data.cassandra.convert.MappingCassandraConverter;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.mapping.BasicCassandraMappingContext;
import org.springframework.data.cassandra.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

/**
 * @author Miguel Ángel Dev (miguelangelprogramacion@gmail.com)
 *
 */
@Configuration
@ComponentScan(basePackages = { "world.we.deserve" })
@EnableCassandraRepositories(basePackages = { "world.we.deserve.dao" })
public class CassandraComponentScan {
	/**************************************************************
	**************************************************************
						Cassandra - Config
	**************************************************************
	**************************************************************/
	
	 @Bean
	  public CassandraClusterFactoryBean clusterCassandra() {

	    CassandraClusterFactoryBean cluster = new CassandraClusterFactoryBean();
	    cluster.setContactPoints("localhost");
	    cluster.setPort(9042);

	    return cluster;
	  }

	  @Bean
	  public CassandraMappingContext mappingContext() {
	    return new BasicCassandraMappingContext();
	  }

	  @Bean
	  public CassandraConverter converter() {
	    return new MappingCassandraConverter(mappingContext());
	  }

	  @Bean
	  public CassandraSessionFactoryBean session() throws Exception {

	    CassandraSessionFactoryBean session = new CassandraSessionFactoryBean();
	    //TODO: DI?
	    session.setCluster(clusterCassandra().getObject());
	    session.setKeyspaceName("demo");
	    session.setConverter(converter());
	    session.setSchemaAction(SchemaAction.NONE);

	    return session;
	  }

	  @Bean
	  public CassandraOperations cassandraTemplate() throws Exception {
	    return new CassandraTemplate(session().getObject());
	  }
}
