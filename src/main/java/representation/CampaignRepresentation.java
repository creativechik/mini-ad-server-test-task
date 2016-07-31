package representation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import persistence.entity.Placement;

import java.util.Set;

/**
 * Created by mikhail on 31.07.16.
 */
@JacksonXmlRootElement(localName = "campaign")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CampaignRepresentation {
    private String name;

    private int weight;

    private String adPhrase;

    private Set<Placement> placements;

    private String self;

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    public String getAdPhrase() {
        return adPhrase;
    }

    public Set<Placement> getPlacements() {
        return placements;
    }

    public String getSelf() {
        return self;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setAdPhrase(String adPhrase) {
        this.adPhrase = adPhrase;
    }

    public void setPlacements(Set<Placement> placements) {
        this.placements = placements;
    }

    public void setSelf(String self) {
        this.self = self;
    }
}
