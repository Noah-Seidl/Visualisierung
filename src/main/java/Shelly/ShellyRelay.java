package Shelly;


import Shelly.ShellyRELAYJSON.ShellyRELAYSTATUSJSON;
import Shelly.ShellyRELAYJSON.ShellyRELAYTOGGLEJSON;

public class ShellyRelay extends ShellyBase{
    protected ShellyRelay(String ip, String channel) {
        super(ip, channel);
    }

    @Override
    protected String getStatusUrl() {
        return "http://" + ip + "/relay/0";
    }


    @Override
    protected Boolean queryStatus(String response) {
        ShellyRELAYSTATUSJSON shellyJson;
        try {
            shellyJson = mapper.readValue(response, ShellyRELAYSTATUSJSON.class);
            return shellyJson.ison;
        } catch (Exception ignored) {
        }
        return null;
    }

    @Override
    public Boolean queryToggle(String response) {
        ShellyRELAYTOGGLEJSON shellyJson;
        try {
            shellyJson = mapper.readValue(response, ShellyRELAYTOGGLEJSON.class);
            return shellyJson.ison;
        } catch (Exception ignored) {}

        return null;
    }
}
