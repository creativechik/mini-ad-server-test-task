package resource;

import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import persistence.CampaignPersistenceService;
import persistence.entity.Campaign;
import representation.CampaignRepresentation;
import util.CampaignUtils;
import util.ResourceUtils;

import java.sql.SQLException;

/**
 * Created by mikhail on 31.07.16.
 */
public class CampaignServerResource extends ServerResource
        implements CampaignResource {

    private CampaignPersistenceService campaignPersistenceService;

    private Campaign campaign;

    private String id;

    /**
     * Method called at the creation of the Resource (ie : each time the
     * resource is called).
     */
    @Override
    protected void doInit() {
        id = getAttribute("id");

        campaignPersistenceService = CampaignPersistenceService.getInstance();

        try {
            campaign = campaignPersistenceService.findById(id);

            setExisting(campaign != null);
            if (!isExisting()) {
                getLogger().config(String.format("Campaign with id = %s does not exist", id));
                setExisting(false);
            }
        } catch (SQLException e) {
            throw new ResourceException(e);
        }
    }

    public CampaignRepresentation getCampaign() throws IllegalArgumentException {
        if (campaign == null) {
            throw new IllegalArgumentException(String.format("Campaign with id = %s does not exist", id));
        }
        CampaignRepresentation res = CampaignUtils.toCampaignRepresentation(campaign);
        res.setSelf(ResourceUtils.getCampaignUrl(String.valueOf(campaign.getId())));
        return res;
    }

    public void remove() throws IllegalArgumentException {
        try {
            Boolean isDeleted = campaignPersistenceService.delete(String.valueOf(campaign.getId()));
            if (!isDeleted) {
                throw new IllegalArgumentException(String.format("Campaign with id = %s does not exist", id));
            }
        } catch (SQLException ex) {
            throw new ResourceException(ex);
        }
    }

    public CampaignRepresentation store(CampaignRepresentation campaignRepresentation) throws IllegalArgumentException {
        if (campaignRepresentation == null) {
            throw new IllegalArgumentException("Can not store null");
        }
        Campaign campaignIn = CampaignUtils.toCampaign(campaignRepresentation);
        campaignIn.setId(Integer.valueOf(id));

        Campaign campaignOut = null;
        if (isExisting()) {
            try {
                campaignOut = campaignPersistenceService.update(campaignIn, id);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (campaignOut == null) {
            throw new IllegalArgumentException(String.format("Campaign with id = %s does not exist", id));
        }
        return CampaignUtils.toCampaignRepresentation(campaignOut);
    }
}
