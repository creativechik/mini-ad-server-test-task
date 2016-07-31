package representation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * Created by mikhail on 01.08.16.
 */
@JacksonXmlRootElement(localName = "placement")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlacementRepresentation {
    private String name;

    private int campaignId;

    private String self;

    public String getName() {
        return name;
    }

    public int getCampaignId() {
        return campaignId;
    }

    public String getSelf() {
        return self;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCampaignId(int campaignId) {
        this.campaignId = campaignId;
    }

    public void setSelf(String self) {
        this.self = self;
    }
}
