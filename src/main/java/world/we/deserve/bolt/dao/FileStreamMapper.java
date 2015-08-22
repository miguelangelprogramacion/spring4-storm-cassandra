/**
 * 
 */
package world.we.deserve.bolt.dao;

import static com.datastax.driver.core.querybuilder.QueryBuilder.insertInto;

import java.io.Serializable;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.hmsonline.trident.cql.mappers.CqlRowMapper;

import storm.trident.tuple.TridentTuple;

/**
 * @author Miguel Ángel Dev (miguelangelprogramacion@gmail.com)
 *
 */
@Component
public class FileStreamMapper implements CqlRowMapper<Object, Object>, Serializable {

	public FileStreamMapper() {
		super();
	}

	private static final long serialVersionUID = 1L;
    private static int cont = 0;

    public Statement map(TridentTuple tuple) {
    	System.err.println(tuple.get(0).toString());
//        long t = System.currentTimeMillis() % 10;
        long t = cont;
        cont++;
//        Update statement = update("demo", "usuario");
//        statement.with(set("email", String.valueOf(t)))
//        .and(set("nombre", tuple.getString(0))).where(eq("id", UUID.randomUUID()));
        Insert statement = insertInto( "usuario").
        		value("id", UUID.randomUUID())
                .value("nombre", String.valueOf(t))
                .value("email", "Doe");
        return statement;       
    }

    public Statement map(TridentTuple tuple, Object value) {        
        return null;
    }

    @Override
    public Statement retrieve(Object key) {
        return null;
    }

    @Override
    public Statement map(Object key, Object value) {
        return null;
    }

    @Override
    public Object getValue(Row row) {
        return null;
    }
}
