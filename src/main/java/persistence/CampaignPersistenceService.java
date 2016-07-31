package persistence;

import com.datastax.driver.core.*;
import persistence.entity.Campaign;
import persistence.entity.Placement;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mikhail on 29.07.16.
 */
public class CampaignPersistenceService extends AbstractPersistenceService <Campaign> {
    private static volatile CampaignPersistenceService campaignPersistenceService;

    private CampaignPersistenceService() {
    }

    public static CampaignPersistenceService getInstance() {
        if (campaignPersistenceService == null ) {
            synchronized (CampaignPersistenceService.class) {
                campaignPersistenceService = new CampaignPersistenceService();
            }
        }
        return campaignPersistenceService;
    }

    public Campaign add(Campaign toAdd) throws SQLException {
        CassandraClient cassandraClient = getCassandraClient();
        Session session = getSession(cassandraClient);
        PreparedStatement preparedStatement = session.prepare("INSERT INTO test_space.campaign" +
                "(id, name, weight, ad_phrase, placement_ids)" +
                "VALUES (?,?,?,?,?);"
        );
        BoundStatement boundStatement = preparedStatement.bind(
                toAdd.getId(),
                toAdd.getName(),
                toAdd.getWeight(),
                toAdd.getAdPhrase(),
                toAdd.getPlacements()
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
        PreparedStatement preparedStatement = session.prepare("DELETE id, name, weight, ad_phrase, placement_ids" +
                "FROM test_space.campaign" +
                "WHERE id =  ?;"
        );
        BoundStatement boundStatement = preparedStatement.bind(Integer.valueOf(id));
        ResultSet resultSet = session.execute(boundStatement);
        cassandraClient.close();
        return resultSet != null;
    }

    public List<Campaign> findAll() throws SQLException {
        CassandraClient cassandraClient = getCassandraClient();
        Session session = getSession(cassandraClient);
        ResultSet resultSet = session.execute("SELECT *" +
                "FROM test_space.campaign;"
        );
        cassandraClient.close();
        if (resultSet == null) {
            return null;
        }

        List<Row> all = resultSet.all();

        List<Campaign> allCampaigns = new ArrayList<Campaign>();
        for (Row row : all) {
            Campaign campaign = new Campaign();
            campaign.setId(row.getInt(0));
            campaign.setName(row.getString(1));
            campaign.setWeight(row.getInt(2));
            campaign.setAdPhrase(row.getString(3));
            campaign.setPlacements(row.getSet(4, Placement.class));
        }
        return allCampaigns;
    }

    public Campaign findById(String id) throws SQLException {
        CassandraClient cassandraClient = getCassandraClient();
        Session session = getSession(cassandraClient);
        PreparedStatement preparedStatement = session.prepare("SELECT *" +
                "FROM test_space.campaign" +
                "WHERE id = ?"
        );
        BoundStatement boundStatement = preparedStatement.bind(id);
        ResultSet rs = session.execute(boundStatement);
        Row row = rs.one();
        cassandraClient.close();
        if (row != null) {
            Campaign campaign = new Campaign();
            campaign.setId(row.getInt(0));
            campaign.setName(row.getString(1));
            campaign.setWeight(row.getInt(2));
            campaign.setAdPhrase(row.getString(3));
            campaign.setPlacements(row.getSet(4, Placement.class));
            return campaign;
        }
        return null;
    }

    public Campaign update(Campaign toUpdate, String id) throws SQLException {
        CassandraClient cassandraClient = getCassandraClient();
        Session session = getSession(cassandraClient);
        PreparedStatement preparedStatement = session.prepare("UPDATE test_space.campaign" +
                "SET name = ?, weight = ?, ad_phrase = ?, placement_ids = ?" +
                "WHERE id = ?"
        );
        BoundStatement boundStatement = preparedStatement.bind(toUpdate.getName(), toUpdate.getWeight(), toUpdate.getAdPhrase(),
                toUpdate.getPlacements(), toUpdate.getId()
        );
        ResultSet resultSet = session.execute(boundStatement);
        cassandraClient.close();
        if (resultSet != null) {
            return toUpdate;
        }
        return null;
    }
}
