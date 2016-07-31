package resource;

import org.restlet.data.Status;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import persistence.PlacementPersistenceService;
import persistence.entity.Placement;
import representation.PlacementListRepresentation;
import representation.PlacementRepresentation;
import util.PlacementUtils;
import util.ResourceUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * Created by mikhail on 01.08.16.
 */
public class PlacementListServerResource extends ServerResource implements PlacementListResource {
    private PlacementPersistenceService placementPersistenceService;

    @Override
    protected void doInit() {
        placementPersistenceService = PlacementPersistenceService.getInstance();
    }

    public PlacementRepresentation add(PlacementRepresentation placementRepresentation) throws IllegalArgumentException {
        try {
            Placement placementIn = PlacementUtils.toPlacement(placementRepresentation);
            Placement placementOut = placementPersistenceService.add(placementIn);
            if (placementOut == null) {
                throw new IllegalArgumentException(String.format("Placement '%s' can not be added", placementRepresentation.getName()));
            }

            PlacementRepresentation result = PlacementUtils.toPlacementRepresentation(placementOut);

            getResponse().setLocationRef(
                    ResourceUtils.getPlacementUrl(String.valueOf(placementOut.getId())));
            getResponse().setStatus(Status.SUCCESS_CREATED);
            return result;
        } catch (SQLException ex) {
            getLogger().log(Level.WARNING, "Error when adding a placement", ex);
            throw new ResourceException(ex);
        }
    }

    public PlacementListRepresentation getPlacements() {
        try {
            List<Placement> placements = placementPersistenceService.findAll();
            if (placements == null) {
                return null;
            }

            List<PlacementRepresentation> placementReprs = new ArrayList<PlacementRepresentation>();
            for (Placement placement : placements) {
                PlacementRepresentation placementRepr = PlacementUtils
                        .toPlacementRepresentation(placement);
                placementRepr
                        .setSelf(ResourceUtils.getPlacementUrl(String.valueOf(placement.getId())));
                placementReprs.add(placementRepr);
            }
            PlacementListRepresentation result = new PlacementListRepresentation();
            result.setList(placementReprs);
            return result;
        } catch (SQLException ex) {
            getLogger().log(Level.WARNING, "Error when listing placements", ex);
            throw new ResourceException(ex);
        }
    }
}
