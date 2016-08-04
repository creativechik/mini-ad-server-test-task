package resource;

import org.restlet.resource.Get;
import org.restlet.resource.Post;
import representation.CampaignListRepresentation;
import representation.CampaignRepresentation;

/**
 * Created by mikhail on 01.08.16.
 */
public interface CampaignListResource {
    @Post
    CampaignRepresentation add(CampaignRepresentation campaignRepresentation)
            throws IllegalArgumentException;

    @Get
    CampaignListRepresentation getCampaigns();
}
