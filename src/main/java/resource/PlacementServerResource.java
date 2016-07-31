package resource;

import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import persistence.PlacementPersistenceService;
import persistence.entity.Placement;
import representation.PlacementRepresentation;
import util.PlacementUtils;
import util.ResourceUtils;

import java.sql.SQLException;

/**
 * Created by mikhail on 01.08.16.
 */
public class PlacementServerResource extends ServerResource implements PlacementResource {
    private PlacementPersistenceService placementPersistenceService;

    private Placement placement;

    private String id;

    /**
     * Method called at the creation of the Resource (ie : each time the
     * resource is called).
     */
    @Override
    protected void doInit() {
        id = getAttribute("id");
        placementPersistenceService = PlacementPersistenceService.getInstance();

        try {
            placement = placementPersistenceService.findById(id);
            setExisting(placement != null);
            if (!isExisting()) {
                getLogger().config(String.format("Placement with id = %s does not exist", id));
                setExisting(false);
            }
        } catch (SQLException e) {
            throw new ResourceException(e);
        }
    }

    public PlacementRepresentation getPlacement() throws IllegalArgumentException {
        if (placement == null) {
            throw new IllegalArgumentException(String.format("Placement with id = %s does not exist", id));
        }
        PlacementRepresentation res = PlacementUtils.toPlacementRepresentation(placement);
        res.setSelf(ResourceUtils.getCampaignUrl(String.valueOf(placement.getId())));
        return res;
    }

    public void remove() throws IllegalArgumentException {
        try {
            Boolean isDeleted = placementPersistenceService.delete(String.valueOf(placement.getId()));
            if (!isDeleted) {
                throw new IllegalArgumentException(String.format("Placement with id = %s does not exist", id));
            }
        } catch (SQLException ex) {
            throw new ResourceException(ex);
        }
    }

    public PlacementRepresentation store(PlacementRepresentation placementRepresentation) throws IllegalArgumentException {
        if (placementRepresentation == null) {
            throw new IllegalArgumentException("Can not store null");
        }
        Placement placementIn = PlacementUtils.toPlacement(placementRepresentation);
        placementIn.setId(Integer.valueOf(id));

        Placement placementOut = null;
        if (isExisting()) {
            try {
                placementOut = placementPersistenceService.update(placementIn, id);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (placementOut == null) {
            throw new IllegalArgumentException(String.format("Placement with id = %s does not exist", id));
        }
        return PlacementUtils.toPlacementRepresentation(placementOut);
    }
}
