package resource;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

/**
 * Created by mikhail on 29.07.16.
 */
public class PingServerResource extends ServerResource {
    @Get("txt")
    public String ping() {
        return "I'm running";
    }
}
