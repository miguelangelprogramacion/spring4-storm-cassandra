/**
 * 
 */
package world.we.deserve.bolt.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.cassandra.core.CassandraOperations;

import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.BatchStatement.Type;
import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.core.Statement;

import storm.trident.state.State;

/**
 * @author Miguel Ángel Dev (miguelangelprogramacion@gmail.com)
 *
 */
public class CassandraCqlState  implements State, Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(CassandraCqlState.class);
//    private CqlClientFactory clientFactory;
    private int maxBatchSize;
    private ConsistencyLevel batchConsistencyLevel;
    private Type batchType = Type.LOGGED;
   
	public CassandraCqlState() {
		super();
	}

	CassandraOperations cassandraOperations;

	List<Statement> statements = new ArrayList<Statement>();
    
    @Override
    public void beginCommit(Long txid) {
//    	if(cassandraOperations==null)
//    	{	ApplicationContext context = new AnnotationConfigApplicationContext(CassandraComponentScan.class);
//    		DAO dao = context.getBean(DAO.class);
//    		this.cassandraOperations = dao.getCassandraOperations();
//    	}
    }

    @Override
    public void commit(Long txid) {
        LOG.debug("Commiting [{}]", txid);
        BatchStatement batch = new BatchStatement(batchType);
        batch.setConsistencyLevel(batchConsistencyLevel);
        int i = 0;
        for(Statement statement : this.statements) {
            batch.add(statement);
            i++;
            if(i >= this.maxBatchSize) {
            	cassandraOperations.execute(batch);
                batch = new BatchStatement(batchType);
                i = 0;
            }
        }
        if(i > 0) {
        	cassandraOperations.execute(batch);
        }
        this.statements.clear();
    }

    public void addStatement(Statement statement) {
        this.statements.add(statement);
    }
    
    /**
	 * @param cassandraOperations the cassandraOperations to set
	 */
	public void setCassandraOperations(CassandraOperations cassandraOperations) {
		this.cassandraOperations = cassandraOperations;
	}
}
