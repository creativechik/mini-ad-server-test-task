package resource;

import org.restlet.resource.Get;
import org.restlet.resource.Post;
import representation.PlacementListRepresentation;
import representation.PlacementRepresentation;

/**
 * Created by mikhail on 01.08.16.
 */
public interface PlacementListResource {
    @Post
    public PlacementRepresentation add(PlacementRepresentation placementRepresentation)
            throws IllegalArgumentException;

    @Get
    public PlacementListRepresentation getPlacements();
}
