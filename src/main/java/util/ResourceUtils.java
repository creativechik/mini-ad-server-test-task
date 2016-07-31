package util;

/**
 * Created by mikhail on 01.08.16.
 */
public class ResourceUtils {
    public static String getCampaignUrl(String id) {
        return "/campaigns/" + id;
    }

    public static String getPlacementUrl(String id) {
        return "/placements/" + id;
    }
}
