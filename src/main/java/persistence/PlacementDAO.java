package persistence;

import com.datastax.driver.core.*;
import persistence.entity.Placement;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mikhail on 29.07.16.
 */
public class PlacementDAO extends AbstractDAO<Placement> {
    private static class Holder {
        private static final PlacementDAO instance = new PlacementDAO();
    }

    public static PlacementDAO getInstance() {
        return Holder.instance;
    }

    public Placement add(Placement toAdd) throws SQLException {
        CassandraClient cassandraClient = getCassandraClient();
        Session session = getSession(cassandraClient);
        PreparedStatement preparedStatement = session.prepare("INSERT INTO test_space.placement " +
                "(id, name) " +
                "VALUES (?,?);"
        );
        BoundStatement boundStatement = preparedStatement.bind(
                toAdd.getId(),
                toAdd.getName()
        );
        ResultSet resultSet;
        try {
            resultSet = session.execute(boundStatement);
        } finally {
            cassandraClient.close();
        }

        if (resultSet != null) {
            return toAdd;
        }
        return null;
    }

    public Boolean delete(String id) throws SQLException {
        CassandraClient cassandraClient = getCassandraClient();
        Session session = getSession(cassandraClient);
        PreparedStatement preparedStatement = session.prepare("DELETE " +
                "FROM test_space.placement " +
                "WHERE id =  ?;"
        );
        BoundStatement boundStatement = preparedStatement.bind(Integer.valueOf(id));
        ResultSet resultSet;
        try {
            resultSet = session.execute(boundStatement);
        } finally {
            cassandraClient.close();
        }

        return resultSet != null;
    }

    public List<Placement> findAll() throws SQLException {
        CassandraClient cassandraClient = getCassandraClient();
        Session session = getSession(cassandraClient);
        ResultSet resultSet;
        try {
            resultSet = session.execute("SELECT id, name " +
                    "FROM test_space.placement;"
            );
        } finally {
            cassandraClient.close();
        }

        if (resultSet == null) {
            return null;
        }

        List<Placement> allPlacements = new ArrayList<Placement>();
        for (Row row : resultSet.all()) {
            Placement placement = new Placement(
                    row.getInt(0),
                    row.getString(1)
            );
            allPlacements.add(placement);
        }
        return allPlacements;
    }

    public Placement findById(String id) throws SQLException {
        CassandraClient cassandraClient = getCassandraClient();
        Session session = getSession(cassandraClient);
        PreparedStatement preparedStatement = session.prepare("SELECT id, name " +
                "FROM test_space.placement " +
                "WHERE id = ?"
        );
        BoundStatement boundStatement = preparedStatement.bind(Integer.valueOf(id));
        ResultSet rs;
        try {
            rs = session.execute(boundStatement);
        } finally {
            cassandraClient.close();
        }
        Row row = rs.one();

        if (row != null) {
            return new Placement(
                    row.getInt(0),
                    row.getString(1)
            );
        }
        return null;
    }

    public Placement update(Placement toUpdate, String id) throws SQLException {
        CassandraClient cassandraClient = getCassandraClient();
        Session session = getSession(cassandraClient);
        PreparedStatement preparedStatement = session.prepare("UPDATE test_space.placement " +
                "SET name = ?" +
                "WHERE id = ?"
        );
        BoundStatement boundStatement = preparedStatement.bind(
                toUpdate.getName(), toUpdate.getId()
        );
        ResultSet resultSet;
        try {
            resultSet = session.execute(boundStatement);
        } finally {
            cassandraClient.close();
        }

        if (resultSet != null) {
            return toUpdate;
        }
        return null;
    }
}
