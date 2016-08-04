package resource;

import org.restlet.resource.Get;
import org.restlet.resource.Put;
import representation.PlacementListRepresentation;
import representation.PlacementRepresentation;

/**
 * Created by mikhail on 01.08.16.
 */
public interface PlacementListResource {
    @Put
    PlacementRepresentation add(PlacementRepresentation placementRepresentation)
            throws IllegalArgumentException;

    @Get
    PlacementListRepresentation getPlacements();
}
