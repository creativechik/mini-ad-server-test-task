package jetty;

import com.datastax.driver.core.*;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import persistence.AbstractPersistenceService;
import persistence.CassandraClient;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

/**
 * Created by mikhail on 01.08.16.
 */
public class JettyHandler extends AbstractHandler {

    private static final String GET_PL = "pl";

    /**
     * Accepting queries like: pl=PlacementId
     */
    public void handle(String target,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response) throws IOException, ServletException {
        String query = ((Request) request).getMetaData().getURI().getQuery();
        if (query != null) {
            String name = "";
            String value = null;
            String[] param2Value = query.split("=");
            if (param2Value.length == 2) {
                name = param2Value[0];
                value = param2Value[1];
            }

            if (name.equals(GET_PL) && value != null) {
                Integer placementId = Integer.valueOf(value);
                CassandraClient cassandraClient = AbstractPersistenceService.getCassandraClient();
                Session session = AbstractPersistenceService.getSession(cassandraClient);
                PreparedStatement preparedPlacement = session.prepare("SELECT campaign_ids " +
                        "FROM test_space.placement " +
                        "WHERE id = ?;"
                );
                BoundStatement boundPlacement = preparedPlacement.bind(placementId);
                ResultSet rs = session.execute(boundPlacement);
                Set<Integer> campaignIds = rs.one().getSet(0, Integer.class);
                ResultSet allCampaigns = session.execute("SELECT id, weight, ad_phrase " +
                        "FROM test_space.campaign;"
                );
                cassandraClient.close();

                int winnerWeight = Integer.MIN_VALUE;
                String winnerAd = null;
                for (Row row : allCampaigns.all()) {
                    int id = row.getInt(0);
                    if (campaignIds.contains(id)) {
                        int weight = row.getInt(1);
                        if (weight > winnerWeight) {
                            winnerWeight = weight;
                            winnerAd = row.getString(2);
                        }
                    }
                }
                response.setContentType("text; charset=utf-8");
                response.setStatus(HttpServletResponse.SC_OK);

                PrintWriter out = response.getWriter();
                out.println(winnerAd);
                baseRequest.setHandled(true);
                return;
            }
        }
        baseRequest.setHandled(false);
    }
}
