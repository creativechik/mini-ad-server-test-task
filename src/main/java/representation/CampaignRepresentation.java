package representation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import persistence.entity.Campaign;

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

    private Set<Integer> placementIds;

    public CampaignRepresentation() {
    }

    public CampaignRepresentation(String name, int weight, String adPhrase, Set<Integer> placementIds) {
        this.name = name;
        this.weight = weight;
        this.adPhrase = adPhrase;
        this.placementIds = placementIds;
    }

    public Campaign asCampaign() {
        return new Campaign(
                hashCode(), name, weight, adPhrase, placementIds
        );
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CampaignRepresentation that = (CampaignRepresentation) o;

        if (weight != that.weight) return false;
        if (!name.equals(that.name)) return false;
        if (!adPhrase.equals(that.adPhrase)) return false;
        return placementIds.equals(that.placementIds);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + weight;
        result = 31 * result + adPhrase.hashCode();
        result = 31 * result + placementIds.hashCode();
        return result;
    }
}
