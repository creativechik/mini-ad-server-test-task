package persistence;

import com.datastax.driver.core.*;
import persistence.entity.Placement;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mikhail on 29.07.16.
 */
public class PlacementPersistenceService extends AbstractPersistenceService<Placement> {
    private static volatile PlacementPersistenceService placementPersistenceService;

    private PlacementPersistenceService() {
    }

    public static PlacementPersistenceService getInstance() {
        if (placementPersistenceService == null) {
            synchronized (PlacementPersistenceService.class) {
                placementPersistenceService = new PlacementPersistenceService();
            }
        }
        return placementPersistenceService;
    }

    public Placement add(Placement toAdd) throws SQLException {
        CassandraClient cassandraClient = getCassandraClient();
        Session session = getSession(cassandraClient);
        PreparedStatement preparedStatement = session.prepare("INSERT INTO test_space.placement" +
                "(id, name, campaign_id)" +
                "VALUES (?,?,?);"
        );
        BoundStatement boundStatement = preparedStatement.bind(
                toAdd.getId(),
                toAdd.getName(),
                toAdd.getCampaignId()
        );
        ResultSet resultSet = session.execute(boundStatement);
        cassandraClient.close();
        if (resultSet != null) {
            return toAdd;
        }
        return null;
    }

    public Boolean delete(String id) throws SQLException {
        CassandraClient cassandraClient = getCassandraClient();
        Session session = getSession(cassandraClient);
        PreparedStatement preparedStatement = session.prepare("DELETE id, name, campaign_id" +
                "FROM test_space.placement" +
                "WHERE id =  ?;"
        );
        BoundStatement boundStatement = preparedStatement.bind(Integer.valueOf(id));
        ResultSet resultSet = session.execute(boundStatement);
        cassandraClient.close();
        return resultSet != null;
    }

    public List<Placement> findAll() throws SQLException {
        CassandraClient cassandraClient = getCassandraClient();
        Session session = getSession(cassandraClient);
        ResultSet resultSet = session.execute("SELECT *" +
                "FROM test_space.placement;"
        );
        cassandraClient.close();
        if (resultSet == null) {
            return null;
        }

        List<Row> all = resultSet.all();
        List<Placement> allPlacements = new ArrayList<Placement>();
        for (Row row : all) {
            Placement placement = new Placement();
            placement.setId(row.getInt(0));
            placement.setName(row.getString(1));
            placement.setCampaignId(row.getInt(2));
        }
        return allPlacements;
    }

    public Placement findById(String id) throws SQLException {
        CassandraClient cassandraClient = getCassandraClient();
        Session session = getSession(cassandraClient);
        PreparedStatement preparedStatement = session.prepare("SELECT *" +
                "FROM test_space.placement" +
                "WHERE id = ?"
        );
        BoundStatement boundStatement = preparedStatement.bind(id);
        ResultSet rs = session.execute(boundStatement);
        Row row = rs.one();
        cassandraClient.close();
        if (row != null) {
            Placement placement = new Placement();
            placement.setId(row.getInt(0));
            placement.setName(row.getString(1));
            placement.setCampaignId(row.getInt(2));
            return placement;
        }
        return null;
    }

    public Placement update(Placement toUpdate, String id) throws SQLException {
        CassandraClient cassandraClient = getCassandraClient();
        Session session = getSession(cassandraClient);
        PreparedStatement preparedStatement = session.prepare("UPDATE test_space.placement" +
                "SET name = ?, campaign_id = ?" +
                "WHERE id = ?"
        );
        BoundStatement boundStatement = preparedStatement.bind(
                toUpdate.getName(), toUpdate.getCampaignId(), toUpdate.getId()
        );
        ResultSet resultSet = session.execute(boundStatement);
        cassandraClient.close();
        if (resultSet != null) {
            return toUpdate;
        }
        return null;
    }
}
