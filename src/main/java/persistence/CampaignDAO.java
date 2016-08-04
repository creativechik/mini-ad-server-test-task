package persistence;

import com.datastax.driver.core.*;
import persistence.entity.Campaign;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mikhail on 29.07.16.
 */
public class CampaignDAO extends AbstractDAO<Campaign> {
    private static class Holder {
        private static final CampaignDAO instance = new CampaignDAO();
    }

    public static CampaignDAO getInstance() {
        return Holder.instance;
    }

    public Campaign add(Campaign toAdd) throws SQLException {
        CassandraClient cassandraClient = getCassandraClient();
        Session session = getSession(cassandraClient);
        PreparedStatement preparedStatement = session.prepare("INSERT INTO test_space.campaign " +
                "(id, name, weight, ad_phrase, placement_ids) " +
                "VALUES (?,?,?,?,?);"
        );
        BoundStatement boundStatement = preparedStatement.bind(
                toAdd.hashCode(),
                toAdd.getName(),
                toAdd.getWeight(),
                toAdd.getAdPhrase(),
                toAdd.getPlacementIds()
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
                "FROM test_space.campaign " +
                "WHERE id =  ?;"
        );
        BoundStatement boundStatement = preparedStatement.bind(id);
        ResultSet resultSet;
        try {
            resultSet = session.execute(boundStatement);
        } finally {
            cassandraClient.close();
        }

        return resultSet != null;
    }

    public List<Campaign> findAll() throws SQLException {
        CassandraClient cassandraClient = getCassandraClient();
        Session session = getSession(cassandraClient);
        ResultSet resultSet;
        try {
            resultSet = session.execute("SELECT id, name, weight, ad_phrase, placement_ids " +
                    "FROM test_space.campaign;"
            );
        } finally {
            cassandraClient.close();
        }

        if (resultSet == null) {
            return null;
        }

        List<Campaign> allCampaigns = new ArrayList<Campaign>();
        for (Row row : resultSet.all()) {
            Campaign campaign = new Campaign(
                    row.getInt(0),
                    row.getString(1),
                    row.getInt(2),
                    row.getString(3),
                    row.getSet(4, Integer.class)
            );
            allCampaigns.add(campaign);
        }
        return allCampaigns;
    }

    public Campaign findById(String id) throws SQLException {
        CassandraClient cassandraClient = getCassandraClient();
        Session session = getSession(cassandraClient);
        PreparedStatement preparedStatement = session.prepare("SELECT id, name, weight, ad_phrase, placement_ids " +
                "FROM test_space.campaign " +
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
            return new Campaign(
                    row.getInt(0),
                    row.getString(1),
                    row.getInt(2),
                    row.getString(3),
                    row.getSet(4, Integer.class)
            );
        }
        return null;
    }

    public Campaign update(Campaign toUpdate, String id) throws SQLException {
        CassandraClient cassandraClient = getCassandraClient();
        Session session = getSession(cassandraClient);
        PreparedStatement preparedStatement = session.prepare("UPDATE test_space.campaign " +
                "SET name = ?, weight = ?, ad_phrase = ?, placement_ids = ? " +
                "WHERE id = ?"
        );
        BoundStatement boundStatement = preparedStatement.bind(toUpdate.getName(), toUpdate.getWeight(), toUpdate.getAdPhrase(),
                toUpdate.getPlacementIds(), toUpdate.getId()
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
