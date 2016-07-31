package util;

import persistence.entity.Placement;
import representation.PlacementRepresentation;

/**
 * Created by mikhail on 01.08.16.
 */
public class PlacementUtils {
    public static PlacementRepresentation toPlacementRepresentation(Placement placement) {
        PlacementRepresentation placementRepresentation = new PlacementRepresentation();
        placementRepresentation.setId(placement.getId());
        placementRepresentation.setName(placement.getName());
        placementRepresentation.setCampaignIds(placement.getCampaignIds());
        return placementRepresentation;
    }

    public static Placement toPlacement(PlacementRepresentation placementRepresentation) {
        Placement placement = new Placement();
        placement.setId(placementRepresentation.getId());
        placement.setName(placementRepresentation.getName());
        placement.setCampaignIds(placementRepresentation.getCampaignIds());
        return placement;
    }
}
