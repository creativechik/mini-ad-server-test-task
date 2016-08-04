package resource;

import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import persistence.CampaignDAO;
import persistence.entity.Campaign;
import representation.CampaignRepresentation;

import java.sql.SQLException;

/**
 * Created by mikhail on 31.07.16.
 */
public class CampaignServerResource extends ServerResource implements CampaignResource {

    private CampaignDAO campaignDAO;
    private String id;
    private String nonExistentCampaignReason;

    /**
     * Method called at the creation of the Resource
     */
    @Override
    protected void doInit() {
        id = getAttribute("id");
        nonExistentCampaignReason = String.format("Campaign with id = %s does not exist", id);
        campaignDAO = CampaignDAO.getInstance();
    }

    public CampaignRepresentation getCampaign() throws IllegalArgumentException {
        Campaign campaign;
        try {
            campaign = campaignDAO.findById(id);

            setExisting(campaign != null);
            if (!isExisting()) {
                getLogger().config(nonExistentCampaignReason);
                setExisting(false);
            }
        } catch (SQLException e) {
            throw new ResourceException(e);
        }

        if (campaign == null) {
            throw new IllegalArgumentException(nonExistentCampaignReason);
        }
        return campaign.asCampaignRepresentation();
    }

    public void remove() throws IllegalArgumentException {
        try {
            Boolean isDeleted = campaignDAO.delete(id);
            if (!isDeleted) {
                throw new IllegalArgumentException(nonExistentCampaignReason);
            }
        } catch (SQLException ex) {
            throw new ResourceException(ex);
        }
    }

    public CampaignRepresentation store(CampaignRepresentation campaignRepresentation) throws IllegalArgumentException {
        if (campaignRepresentation == null) {
            throw new IllegalArgumentException("Can not store null");
        }
        Campaign campaignIn = campaignRepresentation.asCampaign();
        campaignIn.setId(Integer.valueOf(id));

        Campaign campaignOut = null;
        if (isExisting()) {
            try {
                campaignOut = campaignDAO.update(campaignIn, id);
            } catch (SQLException e) {
                throw new ResourceException(e);
            }
        }
        if (campaignOut == null) {
            throw new IllegalArgumentException(nonExistentCampaignReason);
        }
        return campaignOut.asCampaignRepresentation();
    }
}
