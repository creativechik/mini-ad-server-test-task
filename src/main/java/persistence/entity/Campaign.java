package persistence.entity;

import java.util.Set;

/**
 * Created by mikhail on 29.07.16.
 */
public class Campaign {
    private int id;

    private String name;

    private int weight;

    private String adPhrase;

    private Set<Integer> placement_ids;

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

    public Set<Integer> getPlacement_ids() {
        return placement_ids;
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

    public void setPlacement_ids(Set<Integer> placement_ids) {
        this.placement_ids = placement_ids;
    }
}
