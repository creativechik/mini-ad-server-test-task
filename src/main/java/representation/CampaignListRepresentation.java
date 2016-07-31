package representation;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mikhail on 01.08.16.
 */
@JacksonXmlRootElement(localName = "campaigns")
public class CampaignListRepresentation {
    private List<CampaignRepresentation> list;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "campaign")
    public List<CampaignRepresentation> getList() {
        if (list == null) {
            list = new ArrayList<CampaignRepresentation>();
        }
        return list;
    }

    public void setList(List<CampaignRepresentation> list) {
        this.list = list;
    }
}
