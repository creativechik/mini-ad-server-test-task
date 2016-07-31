package util;

import persistence.entity.Campaign;
import representation.CampaignRepresentation;

/**
 * Created by mikhail on 01.08.16.
 */
public class CampaignUtils {
    public static CampaignRepresentation toCampaignRepresentation(Campaign campaign) {
        CampaignRepresentation campaignRepresentation = new CampaignRepresentation();
        campaignRepresentation.setId(campaign.getId());
        campaignRepresentation.setName(campaign.getName());
        campaignRepresentation.setWeight(campaign.getWeight());
        campaignRepresentation.setAdPhrase(campaign.getAdPhrase());
        campaignRepresentation.setPlacementIds(campaign.getPlacement_ids());
        return campaignRepresentation;
    }

    public static Campaign toCampaign(CampaignRepresentation campaignRepresentation) {
        Campaign campaign = new Campaign();
        campaign.setId(campaignRepresentation.getId());
        campaign.setName(campaignRepresentation.getName());
        campaign.setWeight(campaignRepresentation.getWeight());
        campaign.setAdPhrase(campaignRepresentation.getAdPhrase());
        campaign.setPlacement_ids(campaignRepresentation.getPlacementIds());
        return campaign;
    }
}
