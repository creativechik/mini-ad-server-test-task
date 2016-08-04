package resource;

import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import persistence.PlacementDAO;
import persistence.entity.Placement;
import representation.PlacementRepresentation;

import java.sql.SQLException;

/**
 * Created by mikhail on 01.08.16.
 */
public class PlacementServerResource extends ServerResource implements PlacementResource {
    private PlacementDAO placementDAO;
    private Placement placement;
    private String id;
    private String nonExistentPlacementReason;

    /**
     * Method called at the creation of the Resource
     */
    @Override
    protected void doInit() {
        id = getAttribute("id");
        nonExistentPlacementReason = String.format("Placement with id = %s does not exist", id);
        placementDAO = PlacementDAO.getInstance();
    }

    public PlacementRepresentation getPlacement() throws IllegalArgumentException {
        Placement placement;
        try {
            placement = placementDAO.findById(id);
            setExisting(placement != null);
            if (!isExisting()) {
                getLogger().config(nonExistentPlacementReason);
                setExisting(false);
            }
        } catch (SQLException e) {
            throw new ResourceException(e);
        }
        if (placement == null) {
            throw new IllegalArgumentException(nonExistentPlacementReason);
        }
        return placement.asPlacementRepresentation();
    }

    public void remove() throws IllegalArgumentException {
        try {
            Boolean isDeleted = placementDAO.delete(String.valueOf(id));
            if (!isDeleted) {
                throw new IllegalArgumentException(nonExistentPlacementReason);
            }
        } catch (SQLException ex) {
            throw new ResourceException(ex);
        }
    }

    public PlacementRepresentation store(PlacementRepresentation placementRepresentation) throws IllegalArgumentException {
        if (placementRepresentation == null) {
            throw new IllegalArgumentException("Can not store null");
        }
        Placement placementIn = placementRepresentation.asPlacement();
        placementIn.setId(Integer.valueOf(id));

        Placement placementOut = null;
        if (isExisting()) {
            try {
                placementOut = placementDAO.update(placementIn, id);
            } catch (SQLException e) {
                throw new ResourceException(e);
            }
        }
        if (placementOut == null) {
            throw new IllegalArgumentException(nonExistentPlacementReason);
        }
        return placementOut.asPlacementRepresentation();
    }
}
