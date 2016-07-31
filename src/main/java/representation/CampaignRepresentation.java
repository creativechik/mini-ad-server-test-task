package representation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.Set;

/**
 * Created by mikhail on 31.07.16.
 */
@JacksonXmlRootElement(localName = "campaign")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CampaignRepresentation {
    private int id;

    private String name;

    private int weight;

    private String adPhrase;

    private Set<Integer> placementIds;

    private String self;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    public String getAdPhrase() {
        return adPhrase;
    }

    public Set<Integer> getPlacementIds() {
        return placementIds;
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

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setAdPhrase(String adPhrase) {
        this.adPhrase = adPhrase;
    }

    public void setPlacementIds(Set<Integer> placementIds) {
        this.placementIds = placementIds;
    }

    public void setSelf(String self) {
        this.self = self;
    }
}
