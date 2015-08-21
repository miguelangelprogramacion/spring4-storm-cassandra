package world.we.deserve;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.AuthorizationException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.testing.TestWordSpout;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;

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
    	app.greeting(args);
    }

	private void greeting(String[] args) {		
		LOG.info("Greeting");
		
		TopologyBuilder builder = new TopologyBuilder();

		builder.setSpout("word", new TestWordSpout(), 10);
		builder.setBolt("exclaim1", new ExclamationBolt(), 3).shuffleGrouping("word");
		builder.setBolt("exclaim2", new ExclamationBolt(), 2).shuffleGrouping("exclaim1");

		Config conf = new Config();
		conf.setDebug(true);

		if (args != null && args.length > 0) {
			conf.setNumWorkers(3);

			try {
				StormSubmitter.submitTopologyWithProgressBar(args[0], conf, builder.createTopology());
			} catch (AlreadyAliveException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidTopologyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (AuthorizationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {

			LocalCluster cluster = new LocalCluster();
			cluster.submitTopology("test", conf, builder.createTopology());
			Utils.sleep(10000);
			cluster.killTopology("test");
			cluster.shutdown();
		}
	}
	
	public static class ExclamationBolt extends BaseRichBolt {
		OutputCollector _collector;

		public void prepare(Map conf, TopologyContext context, OutputCollector collector) {
			_collector = collector;
		}

		public void execute(Tuple tuple) {
			_collector.emit(tuple, new Values(tuple.getString(0) + "!!!"));
			_collector.ack(tuple);
		}

		public void declareOutputFields(OutputFieldsDeclarer declarer) {
			declarer.declare(new Fields("word"));
		}

	}
}
