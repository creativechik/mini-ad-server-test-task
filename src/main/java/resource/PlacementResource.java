package resource;

import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import representation.PlacementRepresentation;

/**
 * Created by mikhail on 01.08.16.
 */
public interface PlacementResource {
    @Get
    PlacementRepresentation getPlacement() throws IllegalArgumentException;

    @Delete
    void remove() throws IllegalArgumentException;

    @Put
    PlacementRepresentation store(PlacementRepresentation placementRepresentation) throws IllegalArgumentException;
}
