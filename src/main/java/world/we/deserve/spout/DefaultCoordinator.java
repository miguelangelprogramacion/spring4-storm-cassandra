package world.we.deserve.spout;

import java.io.Serializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import storm.trident.spout.ITridentSpout.BatchCoordinator;

/*
*@author Miguel Ángel Fernández Fernández
*
* taken from storm-cassandra-cql: https://github.com/hmsonline/storm-cassandra-cql
*
*/
@SuppressWarnings("rawtypes")
public class DefaultCoordinator implements BatchCoordinator<Long>, Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory
            .getLogger(DefaultCoordinator.class);

    public boolean isReady(long txid) {
        return true;
    }

    public void close() {
    }

    public Long initializeTransaction(long txid, Long prevMetadata,
                                      Long currMetadata) {
        LOG.info("Initializing Transaction [" + txid + "]");
        return null;
    }


    public void success(long txid) {
        LOG.info("Successful Transaction [" + txid + "]");
    }
}
