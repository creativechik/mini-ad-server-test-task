package persistence;

import com.datastax.driver.core.Session;
import com.datastax.driver.core.exceptions.ConnectionException;

import java.net.InetSocketAddress;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by mikhail on 29.07.16.
 */
public abstract class AbstractPersistenceService<T> {

    public static void init() throws ConnectionException {
        CassandraClient cassandraClient = getCassandraClient();
        Session session = getSession(cassandraClient);

        session.execute("CREATE KEYSPACE IF NOT EXISTS test_space WITH replication = {" +
                "  'class': 'SimpleStrategy'," +
                "  'replication_factor': '3'" +
            "};"
        );

        session.execute("CREATE TABLE IF NOT EXISTS test_space.placement (" +
                "id int PRIMARY KEY," +
                "name text," +
                "campaign_id int" +
            ");"
        );

        session.execute("CREATE TABLE IF NOT EXISTS test_space.campaign (" +
                "id int PRIMARY KEY," +
                "name text," +
                "weight int," +
                "ad_phrase text," +
                "placement_ids set<int>" +
            ");"
        );

        cassandraClient.close();
    }

    protected static CassandraClient getCassandraClient() {
        CassandraClient cassandraClient = new CassandraClient();
        cassandraClient.connect("127.0.0.1", 9042);
        return cassandraClient;
    }

    protected static Session getSession(CassandraClient cassandraClient) {
        Session session = cassandraClient.getSession();
        if (session == null) {
            throw new ConnectionException(new InetSocketAddress(9042),
                    "Can not connect to localhost/127.0.0.1:9042"
            );
        }
        return session;
    }

    public abstract T add(T toAdd) throws SQLException;

    public abstract Boolean delete(String id) throws SQLException;

    public abstract List<T> findAll() throws SQLException;

    public abstract T findById(String id) throws SQLException;

    public abstract T update(T toUpdate, String id) throws SQLException;

}
