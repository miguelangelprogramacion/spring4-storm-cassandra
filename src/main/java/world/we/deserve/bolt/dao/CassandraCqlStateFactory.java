/**
 * 
 */
package world.we.deserve.bolt.dao;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.stereotype.Component;

import com.datastax.driver.core.ConsistencyLevel;

import backtype.storm.task.IMetricsContext;
import storm.trident.state.State;
import storm.trident.state.StateFactory;
import world.we.deserve.CassandraComponentScan;

/**
 * @author Miguel Ángel Dev (miguelangelprogramacion@gmail.com)
 *
 */
@Component
public class CassandraCqlStateFactory implements StateFactory {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(CassandraCqlStateFactory.class);
    public static final String TRIDENT_CASSANDRA_MAX_BATCH_SIZE = "trident.cassandra.maxbatchsize";

    public static final int DEFAULT_MAX_BATCH_SIZE = 100;
//    private static CqlClientFactory clientFactory;
    private ConsistencyLevel batchConsistencyLevel;

    
    
    public CassandraCqlStateFactory(ConsistencyLevel batchConsistencyLevel){
        this.batchConsistencyLevel = batchConsistencyLevel;
    }
    
    public CassandraCqlStateFactory(){
    	this.batchConsistencyLevel = ConsistencyLevel.ONE;
    }
    
    
    SpringCassandraCqlStateFactory springCassandraCqlStateFactory;
   
    
    @SuppressWarnings("unchecked")
    @Override
    public State makeState(Map configuration, IMetricsContext metrics, int partitionIndex, int numPartitions) {
//        // worth synchronizing here?
//        if (clientFactory == null) {
//            clientFactory = new MapConfiguredCqlClientFactory(configuration);
//        }
//        final String maxBatchSizeString = (String) configuration.get(CassandraCqlStateFactory.TRIDENT_CASSANDRA_MAX_BATCH_SIZE);
//        final int maxBatchSize = (maxBatchSizeString == null) ? DEFAULT_MAX_BATCH_SIZE : Integer.parseInt((String) maxBatchSizeString);
//        LOG.debug("Creating State for partition [{}] of [{}]", new Object[]{partitionIndex, numPartitions});
//        return new CassandraCqlState(clientFactory, maxBatchSize, batchConsistencyLevel);
    	
    	
    	CassandraCqlState cassandraCqlState = null;
    	
		if(springCassandraCqlStateFactory==null)
    	{	
			ApplicationContext context = new AnnotationConfigApplicationContext(CassandraComponentScan.class);
			this.springCassandraCqlStateFactory = context.getBean(SpringCassandraCqlStateFactory.class);    		
    	}
		try {
			cassandraCqlState = springCassandraCqlStateFactory.getObject();
		} catch (Exception e) {			
			e.printStackTrace();
		}
    	return cassandraCqlState;
    }
}