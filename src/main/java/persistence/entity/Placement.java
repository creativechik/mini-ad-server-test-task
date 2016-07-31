package persistence.entity;

import java.util.Set;

/**
 * Created by mikhail on 29.07.16.
 */
public class Placement {
    private int id;

    private String name;

    private Set<Integer> campaignIds;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Integer> getCampaignIds() {
        return campaignIds;
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
}
