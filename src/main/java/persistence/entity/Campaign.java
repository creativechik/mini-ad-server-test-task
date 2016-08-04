package persistence.entity;

import representation.CampaignRepresentation;

import java.util.Set;

/**
 * Created by mikhail on 29.07.16.
 */
public class Campaign {
    private int id;

    private String name;

    private int weight;

    private String adPhrase;

    private Set<Integer> placementIds;

    public Campaign(int id, String name, int weight, String adPhrase, Set<Integer> placementIds) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.adPhrase = adPhrase;
        this.placementIds = placementIds;
    }

    public CampaignRepresentation asCampaignRepresentation() {
        return new CampaignRepresentation(
                name, weight, adPhrase, placementIds
        );
    }

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

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Campaign campaign = (Campaign) o;

        if (weight != campaign.weight) return false;
        if (!name.equals(campaign.name)) return false;
        if (!adPhrase.equals(campaign.adPhrase)) return false;
        return placementIds.equals(campaign.placementIds);

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
