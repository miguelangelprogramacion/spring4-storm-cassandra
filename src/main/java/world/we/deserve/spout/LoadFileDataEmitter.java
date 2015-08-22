/**
 * 
 */
package world.we.deserve.spout;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import storm.trident.operation.TridentCollector;
import storm.trident.spout.ITridentSpout.Emitter;
import storm.trident.topology.TransactionAttempt;

/**
 * @author Miguel Ángel Dev (miguelangelprogramacion@gmail.com)
 *
 */
public class LoadFileDataEmitter implements Emitter<Long>, Serializable {
	private static final long serialVersionUID = 1L;
	public static AtomicInteger successfulTransactions = new AtomicInteger(0);
	public static AtomicInteger uids = new AtomicInteger(0);

	String syntheticDataPath;
	
	public LoadFileDataEmitter(String syntheticDataPath) {
		this.syntheticDataPath = syntheticDataPath;
	}


	
	public void emitBatch(TransactionAttempt tx, Long coordinatorMeta, TridentCollector collector) {
		System.out.println("emit " + tx.getTransactionId() +"  "+tx.getAttemptId());

		Path path = Paths.get(syntheticDataPath);
		try {
			List<String> lines = Files.readAllLines(path);
			lines.forEach(line -> inject(line,collector));			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	private void inject(String line, TridentCollector collector) {
		List<Object> message = new ArrayList<Object>();
		
		message.add(line);
		collector.emit(message);
	}


	public void success(TransactionAttempt tx) {
		successfulTransactions.incrementAndGet();
	}

	public void close() {
	}
}
