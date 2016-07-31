package persistence;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

/**
 * Created by mikhail on 29.07.16.
 */
public class CassandraClient {
    private Cluster cluster;

    private Session session;

    void connect(String node, int port) {
        cluster = Cluster.builder()
                .addContactPoint(node)
                .withPort(port)
                .build();
        session = cluster.connect();
    }

    Session getSession() {
        return session;
    }

    public void close() {
        session.close();
        cluster.close();
    }
}
