/**
 * 
 */
package world.we.deserve.bolt.dao;

import com.datastax.driver.core.Statement;
import com.hmsonline.trident.cql.mappers.CqlTupleMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import storm.trident.operation.TridentCollector;
import storm.trident.operation.TridentOperationContext;
import storm.trident.state.StateUpdater;
import storm.trident.tuple.TridentTuple;

import java.util.List;
import java.util.Map;
/**
 * @author Miguel Ángel Dev (miguelangelprogramacion@gmail.com)
 *
 */
@Component
public class CassandraCqlStateUpdater <K,V> implements StateUpdater<CassandraCqlState> {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(CassandraCqlStateUpdater.class);
    private CqlTupleMapper<K,V> mapper = null;
	private boolean propagateTuples;

	@Autowired
    public CassandraCqlStateUpdater(CqlTupleMapper<K,V> mapper) {
        this(mapper, false);
    }

	public CassandraCqlStateUpdater(CqlTupleMapper<K,V> mapper, boolean propagateTuples) {
        this.mapper = mapper;
		this.propagateTuples = propagateTuples;
	}

    @SuppressWarnings("rawtypes")
    @Override
    public void prepare(Map configuration, TridentOperationContext context) {
        LOG.debug("Preparing updater with [{}]", configuration);
    }

    @Override
    public void cleanup() {
    }

    @Override
    public void updateState(CassandraCqlState state, List<TridentTuple> tuples, TridentCollector collector) {
        for (TridentTuple tuple : tuples) {
            Statement statement = this.mapper.map(tuple);
            state.addStatement(statement);
			if (propagateTuples) {
				collector.emit(tuple);
			}
        }
    }
}
