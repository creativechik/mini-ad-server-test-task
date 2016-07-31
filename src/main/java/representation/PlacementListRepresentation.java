package representation;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mikhail on 01.08.16.
 */
@JacksonXmlRootElement(localName = "placements")
public class PlacementListRepresentation {
    private List<PlacementRepresentation> list;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "placement")
    public List<PlacementRepresentation> getList() {
        if (list == null) {
            list = new ArrayList<PlacementRepresentation>();
        }
        return list;
    }

    public void setList(List<PlacementRepresentation> list) {
        this.list = list;
    }
}
