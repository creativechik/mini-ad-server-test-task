package resource;

import org.restlet.data.Status;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import persistence.PlacementDAO;
import persistence.entity.Placement;
import representation.PlacementListRepresentation;
import representation.PlacementRepresentation;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * Created by mikhail on 01.08.16.
 */
public class PlacementListServerResource extends ServerResource implements PlacementListResource {
    private PlacementDAO placementDAO;

    @Override
    protected void doInit() {
        placementDAO = PlacementDAO.getInstance();
    }

    public PlacementRepresentation add(PlacementRepresentation placementRepresentation) throws IllegalArgumentException {
        try {
            Placement placementIn = placementRepresentation.asPlacement();
            Placement placementOut = placementDAO.add(placementIn);
            if (placementOut == null) {
                throw new IllegalArgumentException(String.format("Placement '%s' can not be added", placementRepresentation.getName()));
            }

            getResponse().setLocationRef("/placements/" + placementOut.getId());
            getResponse().setStatus(Status.SUCCESS_CREATED);
            return placementOut.asPlacementRepresentation();
        } catch (SQLException ex) {
            getLogger().log(Level.WARNING, "Error when adding a placement", ex);
            throw new ResourceException(ex);
        }
    }

    public PlacementListRepresentation getPlacements() {
        try {
            List<Placement> placements = placementDAO.findAll();
            if (placements == null) {
                getLogger().log(Level.WARNING, "There are no placements in db yet");
                return null;
            }

            List<PlacementRepresentation> placementReprs = new ArrayList<PlacementRepresentation>();
            for (Placement placement : placements) {
                PlacementRepresentation placementRepr = placement.asPlacementRepresentation();
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
