package persistence;

import com.datastax.driver.core.Session;
import com.datastax.driver.core.exceptions.ConnectionException;

import java.net.InetSocketAddress;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by mikhail on 29.07.16.
 */
public abstract class AbstractDAO<T> {

    private static final String CASSANDRA_NODE = "127.0.0.1";
    private static final int CASSANDRA_PORT = 9042;

    public static void init() throws ConnectionException {
        CassandraClient cassandraClient = getCassandraClient();
        Session session = getSession(cassandraClient);

        try {
            session.execute("CREATE KEYSPACE IF NOT EXISTS test_space WITH replication = {" +
                    "  'class': 'SimpleStrategy'," +
                    "  'replication_factor': '3'" +
                    "};"
            );

            session.execute("CREATE TABLE IF NOT EXISTS test_space.placement (" +
                    "id int PRIMARY KEY," +
                    "name text" +
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
        } finally {
            cassandraClient.close();
        }
    }

    public static CassandraClient getCassandraClient() {
        CassandraClient cassandraClient = new CassandraClient();
        cassandraClient.connect(CASSANDRA_NODE, CASSANDRA_PORT);
        return cassandraClient;
    }

    public static Session getSession(CassandraClient cassandraClient) {
        Session session = cassandraClient.getSession();
        if (session == null) {
            throw new ConnectionException(new InetSocketAddress(CASSANDRA_PORT),
                    String.format("Can not connect to localhost/%s:%d", CASSANDRA_NODE, CASSANDRA_PORT)
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
