package persistence.entity;

import representation.PlacementRepresentation;

/**
 * Created by mikhail on 29.07.16.
 */
public class Placement {
    private int id;

    private String name;

    public Placement(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public PlacementRepresentation asPlacementRepresentation() {
        return new PlacementRepresentation(id, name);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

}
