import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.AbstractPersistenceService;
import resource.*;


/**
 * Created by mikhail on 29.07.16.
 */
public class MiniAdServerAPI extends Application {
    private static final Logger LOGGER = LoggerFactory.getLogger(MiniAdServerAPI.class);

    public static void main(String[] args) throws Exception {
        LOGGER.info("Application is starting...");

        AbstractPersistenceService.init();

        // Attach application to http://localhost:9090/v1
        Component c = new Component();
        c.getServers().add(Protocol.HTTP, 9090);

        // Declare client connector based on the classloader
        c.getClients().add(Protocol.CLAP);

        c.getDefaultHost().attach("/v1", new MiniAdServerAPI());

        c.start();

//        {
//            "id" : 1,
//                "name" : "testCampaign1",
//                "weight" : 10,
//                "adPhrase" : "Best ad ever",
//                "placements" : [{"id" : 1, "name" : "pl1"}]
//        }

//        {
//            "id" : 1,
//                "name" : "testPlacement1",
//                "campaign_id" : 1
//        }

        LOGGER.info("Web API started");
        LOGGER.info("URL: http://localhost:9090/v1");
    }

    @Override
    public Restlet createInboundRoot() {

        Router publicRouter = publicResources();

        // Create the api router, protected by a guard
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
