/**
 * 
 */
package world.we.deserve.bolt.function;

import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import storm.trident.operation.BaseFunction;
import storm.trident.operation.TridentCollector;
import storm.trident.operation.TridentOperationContext;
import storm.trident.tuple.TridentTuple;

/**
 * @author Miguel Ángel Dev (miguelangelprogramacion@gmail.com)
 *
 */
@Component
public class PrintTuple extends BaseFunction {

	private static final Logger LOG = LoggerFactory.getLogger(PrintTuple.class);
	
	static int i = 0;

	@Override
	public void prepare(Map map, TridentOperationContext tridentOperationContext) {
		LOG.debug("UP");
	}

	@Override
	public void execute(TridentTuple tuple, TridentCollector collector) {		
		i++;
		String commaSeparatedObjects = tuple.stream().map(i -> i.toString()).collect(Collectors.joining(", "));		
		LOG.debug(i + " " + commaSeparatedObjects);
//		System.err.println(i + " " + commaSeparatedObjects);
	}

	public String toString() {
		return "PrintTuple";
	}
}