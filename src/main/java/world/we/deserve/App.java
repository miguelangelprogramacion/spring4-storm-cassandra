package world.we.deserve;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import backtype.storm.Config;
import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.AuthorizationException;
import backtype.storm.generated.AuthorizationException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.testing.TestWordSpout;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;
import backtype.storm.utils.Utils;
import storm.trident.Stream;
import storm.trident.Stream;
import storm.trident.TridentTopology;
import storm.trident.TridentTopology;
import world.we.deserve.bolt.function.PrintTuple;
import world.we.deserve.spout.LoadFileData;
/**
 * Launcher
 *
 */
@Component
public class App {
	
	@Autowired
	Config tridentConfig;
	
	@Autowired
	TridentTopology topology;

	@Autowired
	LoadFileData loadFileDataSpout;
	
	@Autowired
	PrintTuple printBolt;

	private static final Logger LOG = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(StormCassandraComponentScan.class);
		App app = context.getBean(App.class);
//		app.greeting(args);

		String host = null;
		if (args != null && args.length > 0) {
			host = args[0];
		}
		app.tridentLaunch(host);

	}

	private void tridentLaunch(String host) {

		Stream fileStream = topology.newStream("test", loadFileDataSpout);		
		fileStream.each(new Fields("test"), printBolt, new Fields());

		if (host != null) {

			try {
				// Remember (limited config)
				StormSubmitter.submitTopology(host, tridentConfig, topology.build());
			} catch (AlreadyAliveException e) {
				e.printStackTrace();
			} catch (InvalidTopologyException e) {
				e.printStackTrace();
			} catch (AuthorizationException e) {
				e.printStackTrace();
			}
		} else {
			LocalCluster cluster = new LocalCluster();
			cluster.submitTopology("topology", tridentConfig, topology.build());
			Utils.sleep(1000 * 60 * 300);
			cluster.killTopology("topology");
			cluster.shutdown();
		}
	}
}
