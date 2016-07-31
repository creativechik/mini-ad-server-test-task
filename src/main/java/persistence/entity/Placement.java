package persistence.entity;

/**
 * Created by mikhail on 29.07.16.
 */
public class Placement {
    private int id;

    private String name;

    private int campaignId;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCampaignId() {
        return campaignId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCampaignId(int campaignId) {
        this.campaignId = campaignId;
    }
}
