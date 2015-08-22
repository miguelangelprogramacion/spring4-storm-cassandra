/**
 * 
 */
package world.we.deserve.spout;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.tuple.Fields;
import storm.trident.spout.ITridentSpout;

/**
 * @author Miguel Ángel Dev (miguelangelprogramacion@gmail.com)
 *
 */
@SuppressWarnings("rawtypes")
@Component
@PropertySource("classpath:/world/we/deserve/app.properties")
public class LoadFileData implements ITridentSpout<Long> {
    private static final long serialVersionUID = 1L;
    SpoutOutputCollector collector;
    BatchCoordinator<Long> coordinator = new DefaultCoordinator();
    Emitter<Long> emitter = null;
    
    private String filePath;
 
    @Autowired
    public LoadFileData(Environment env) {
		super();		
		filePath = env.getProperty("file.path");
		emitter = new LoadFileDataEmitter(filePath);	
	}

	public BatchCoordinator<Long> getCoordinator(String txStateId, Map conf, TopologyContext context) {
        return coordinator;
    }

    public Emitter<Long> getEmitter(String txStateId, Map conf, TopologyContext context) {
        return emitter;
    }

    public Map getComponentConfiguration() {
        return null;
    }

    public Fields getOutputFields() {
        return new Fields("test");
    }
}


