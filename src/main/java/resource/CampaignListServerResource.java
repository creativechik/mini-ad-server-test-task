package resource;

import org.restlet.data.Status;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import persistence.CampaignDAO;
import persistence.entity.Campaign;
import representation.CampaignListRepresentation;
import representation.CampaignRepresentation;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * Created by mikhail on 01.08.16.
 */
public class CampaignListServerResource extends ServerResource implements CampaignListResource {
    private CampaignDAO campaignDAO;

    @Override
    protected void doInit() {
        campaignDAO = CampaignDAO.getInstance();
    }

    public CampaignRepresentation add(CampaignRepresentation campaignRepresentation) throws IllegalArgumentException {
        try {
            Campaign campaignIn = campaignRepresentation.asCampaign();
            Campaign campaignOut = campaignDAO.add(campaignIn);
            if (campaignOut == null) {
                throw new IllegalArgumentException(
                        String.format("Campaign '%s' can not be added",
                                campaignRepresentation.getName()
                        )
                );
            }

            CampaignRepresentation result = campaignOut.asCampaignRepresentation();
            getResponse().setLocationRef("/campaigns/" + campaignOut.getId());
            getResponse().setStatus(Status.SUCCESS_CREATED);
            return result;
        } catch (SQLException ex) {
            getLogger().log(Level.WARNING, "Error when adding a campaign", ex);
            throw new ResourceException(ex);
        }
    }

    public CampaignListRepresentation getCampaigns() {
        try {
            List<Campaign> campaigns = campaignDAO.findAll();
            if (campaigns == null) {
                getLogger().log(Level.WARNING, "There are no campaigns in db yet");
                return null;
            }

            List<CampaignRepresentation> campaignReprs = new ArrayList<CampaignRepresentation>();
            for (Campaign campaign : campaigns) {
                CampaignRepresentation campaignRepr = campaign.asCampaignRepresentation();
                campaignReprs.add(campaignRepr);
            }
            CampaignListRepresentation result = new CampaignListRepresentation();
            result.setList(campaignReprs);
            return result;
        } catch (SQLException ex) {
            getLogger().log(Level.WARNING, "Error when listing campaigns", ex);
            throw new ResourceException(ex);
        }
    }
}
