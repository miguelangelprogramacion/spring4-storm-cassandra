/**
 * 
 */
package world.we.deserve.bolt.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.stereotype.Component;

import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.BatchStatement.Type;
import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.core.Statement;

import storm.trident.state.State;
import world.we.deserve.CassandraComponentScan;

/**
 * @author Miguel Ángel Dev (miguelangelprogramacion@gmail.com)
 *
 */
@Component
public class CassandraCqlState  implements State, Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(CassandraCqlState.class);
//    private CqlClientFactory clientFactory;
    private int maxBatchSize;
    private ConsistencyLevel batchConsistencyLevel;
    private Type batchType = Type.LOGGED;
    
    
	CassandraOperations cassandraOperations;
    
    /**
	 * @param cassandraOperations
	 */
	@Autowired
	public CassandraCqlState(CassandraOperations cassandraOperations) {
		super();
//		this.cassandraOperations = cassandraOperations;
		
//		Insert insert = QueryBuilder.insertInto("usuario");
//		insert.setConsistencyLevel(ConsistencyLevel.ONE);
//		insert.value("id", UUID.randomUUID());
//		insert.value("nombre", "Alison");
//		insert.value("email", "gmailalgo");
//		cassandraOperations.execute(insert);

	}

	List<Statement> statements = new ArrayList<Statement>();
    
    @Override
    public void beginCommit(Long txid) {
    	if(cassandraOperations==null)
    	{	ApplicationContext context = new AnnotationConfigApplicationContext(CassandraComponentScan.class);
    		DAO dao = context.getBean(DAO.class);
    		this.cassandraOperations = dao.getCassandraOperations();
    	}
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
}
