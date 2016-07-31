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
    public CampaignRepresentation add(CampaignRepresentation campaignRepresentation)
            throws IllegalArgumentException;

    @Get
    public CampaignListRepresentation getCampaigns();
}
