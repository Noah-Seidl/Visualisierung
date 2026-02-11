package Shelly;

import Shelly.Shelly2PMJSON.Shelly2PMSTATUSJSON;
import Shelly.Shelly2PMJSON.Shelly2PMTOGGLEJSON;

public class Shelly2PM extends ShellyBase {


    protected Shelly2PM(String ip, int channel) {
        super(ip, channel);
    }

    @Override
    protected String getStatusUrl() {
        return "http://" + ip +"/rpc/Switch.GetStatus?id=" + channel;
    }

    @Override
    protected Boolean queryStatus(String response) {
        Shelly2PMSTATUSJSON shellyJson;
        try {
            shellyJson = mapper.readValue(response, Shelly2PMSTATUSJSON.class);
            return shellyJson.output;
        } catch (Exception ignored) {
        }
        return null;
    }

    @Override
    public Boolean queryToggle(String response) {
        Shelly2PMTOGGLEJSON shellyJson;
        try {
            shellyJson = mapper.readValue(response, Shelly2PMTOGGLEJSON.class);
            return shellyJson.ison;
        } catch (Exception ignored) {
        }
        return null;
    }

}
