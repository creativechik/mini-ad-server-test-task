package resource;

import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import representation.CampaignRepresentation;

/**
 * Created by mikhail on 31.07.16.
 */
public interface CampaignResource {
    @Get
    CampaignRepresentation getCampaign() throws IllegalArgumentException;

    @Delete
    void remove() throws IllegalArgumentException;

    @Put
    CampaignRepresentation store(CampaignRepresentation campaignRepresentation) throws IllegalArgumentException;
}
