package representation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import persistence.entity.Placement;

/**
 * Created by mikhail on 01.08.16.
 */
@JacksonXmlRootElement(localName = "placement")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlacementRepresentation {
    private int id;

    private String name;

    public PlacementRepresentation() {
    }

    public PlacementRepresentation(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Placement asPlacement() {
        return new Placement(id, name);
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
