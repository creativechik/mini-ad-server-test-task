import jetty.JettyHandler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;
import persistence.AbstractDAO;
import resource.*;

import java.util.logging.Logger;



/**
 * Created by mikhail on 29.07.16.
 */
public class MiniAdServerAPI extends Application {
    private static final Logger LOGGER = Logger.getLogger("InfoLogging");

    public static void main(String[] args) throws Exception {
        AbstractDAO.init();

        // -------------------Rest-API----------------------
        // Attach application to http://localhost:9090/v1
        Component c = new Component();
        c.getServers().add(Protocol.HTTP, 9090);

        // Declare client connector based on the classloader
        c.getClients().add(Protocol.CLAP);

        c.getDefaultHost().attach("/v1", new MiniAdServerAPI());

        c.start();

        LOGGER.info("Rest API started");
        LOGGER.info("URL: http://localhost:9090/v1");
        // -------------------------------------------------

        // ================== Jetty ========================
        Server jettyServer = new Server();
        ServerConnector http = new ServerConnector(jettyServer);
        http.setHost("localhost");
        http.setPort(8080);
        http.setIdleTimeout(30000);

        jettyServer.addConnector(http);
        jettyServer.setHandler(new JettyHandler());

        jettyServer.start();
        LOGGER.info("Jetty started");
        LOGGER.info("URL: http://localhost:8080");
        jettyServer.join();
        // =================================================
    }

    @Override
    public Restlet createInboundRoot() {
        Router publicRouter = publicResources();

        Router apiRouter = createApiRouter();
        publicRouter.attachDefault(apiRouter);
        return publicRouter;
    }

    private Router createApiRouter() {

        // Attach server resources to the given URL template.
        // For instance, CampaignListServerResource is attached
        // to http://localhost:9090/v1/campaigns
        // and to http://localhost:9090/v1/campaigns/
        Router router = new Router(getContext());

        router.attach("/campaigns", CampaignListServerResource.class);
        router.attach("/campaigns/", CampaignListServerResource.class);
        router.attach("/campaigns/{id}", CampaignServerResource.class);
        router.attach("/campaigns/{id}/", CampaignServerResource.class);
        router.attach("/campaigns/{id}/placements", PlacementListServerResource.class);
        router.attach("/campaigns/{id}/placements/", PlacementListServerResource.class);

        router.attach("/placements", PlacementListServerResource.class);
        router.attach("/placements/", PlacementListServerResource.class);
        router.attach("/placements/{id}", PlacementServerResource.class);
        router.attach("/placements/{id}/", PlacementServerResource.class);
        return router;
    }


    public Router publicResources() {
        Router router = new Router();

        router.attach("/ping", PingServerResource.class);
        return router;
    }
}
