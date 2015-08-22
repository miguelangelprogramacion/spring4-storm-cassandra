/**
 * 
 */
package world.we.deserve.bolt.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.stereotype.Component;

/**
 * @author Miguel Ángel Dev (miguelangelprogramacion@gmail.com)
 *
 */
@Component
public class DAO {

	CassandraOperations cassandraOperations;

	/**
	 * @param cassandraOperations
	 */
	@Autowired
	public DAO(CassandraOperations cassandraOperations) {
		super();
		this.cassandraOperations = cassandraOperations;
	}

	/**
	 * @return the cassandraOperations
	 */
	public CassandraOperations getCassandraOperations() {
		return cassandraOperations;
	}

	/**
	 * @param cassandraOperations the cassandraOperations to set
	 */
	public void setCassandraOperations(CassandraOperations cassandraOperations) {
		this.cassandraOperations = cassandraOperations;
	}
}
