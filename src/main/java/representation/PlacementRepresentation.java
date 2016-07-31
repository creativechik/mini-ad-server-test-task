package representation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.Set;

/**
 * Created by mikhail on 01.08.16.
 */
@JacksonXmlRootElement(localName = "placement")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlacementRepresentation {
    private int id;

    private String name;

    private Set<Integer> campaignIds;

    private String self;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Integer> getCampaignIds() {
        return campaignIds;
    }

    public String getSelf() {
        return self;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCampaignIds(Set<Integer> campaignIds) {
        this.campaignIds = campaignIds;
    }

    public void setSelf(String self) {
        this.self = self;
    }
}
