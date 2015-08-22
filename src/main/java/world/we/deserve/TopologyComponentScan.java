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

import backtype.storm.Config;
import storm.trident.TridentTopology;

/**
 * @author Miguel Ángel Dev (miguelangelprogramacion@gmail.com)
 *
 */
@Configuration
@ComponentScan(basePackages = { "world.we.deserve" })
public class TopologyComponentScan {

	/**************************************************************
	**************************************************************
						Storm trident config
	**************************************************************
	**************************************************************/

	@Bean
	public TridentTopology cluster() {

		TridentTopology topology = new TridentTopology();
		return topology;
	}

	@Bean
	public Config tridentConfig() {

		// Remenber (limited config)
		Config tridentConfig = new Config();
		tridentConfig.setMessageTimeoutSecs(60);
		tridentConfig.setNumAckers(1);
		tridentConfig.setNumWorkers(1);
		tridentConfig.setMaxSpoutPending(1);
		tridentConfig.setMaxTaskParallelism(1);

		tridentConfig.put(Config.TOPOLOGY_TRIDENT_BATCH_EMIT_INTERVAL_MILLIS, 1000);
		tridentConfig.put(Config.TOPOLOGY_EXECUTOR_RECEIVE_BUFFER_SIZE, 16384);
		tridentConfig.put(Config.TOPOLOGY_EXECUTOR_SEND_BUFFER_SIZE, 16384);
		tridentConfig.put(Config.TOPOLOGY_TRANSFER_BUFFER_SIZE, 32);
		tridentConfig.put(Config.TOPOLOGY_STATS_SAMPLE_RATE, 0.05);

		// tridentConfig.put(MapConfiguredCqlClientFactory.TRIDENT_CASSANDRA_CQL_HOSTS,
		// "localhost");

		tridentConfig.setDebug(false);

		return tridentConfig;
	}
}
