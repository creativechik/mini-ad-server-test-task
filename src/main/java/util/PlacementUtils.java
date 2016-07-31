package util;

import persistence.entity.Placement;
import representation.PlacementRepresentation;

/**
 * Created by mikhail on 01.08.16.
 */
public class PlacementUtils {
    public static PlacementRepresentation toPlacementRepresentation(Placement placement) {
        PlacementRepresentation placementRepresentation = new PlacementRepresentation();
        placementRepresentation.setName(placement.getName());
        placementRepresentation.setCampaignId(placement.getCampaignId());
        return placementRepresentation;
    }

    public static Placement toPlacement(PlacementRepresentation placementRepresentation) {
        Placement placement = new Placement();
        placement.setName(placementRepresentation.getName());
        placement.setCampaignId(placementRepresentation.getCampaignId());
        return placement;
    }
}
