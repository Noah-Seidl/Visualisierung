package Shelly;

import Shelly.ShellyLIGHTJSON.ShellyLIGHTSTATUSJSON;
import Shelly.ShellyLIGHTJSON.ShellyLIGHTTOGGLEJSON;
import Shelly.ShellyRELAYJSON.ShellyRELAYSTATUSJSON;

import java.net.http.HttpResponse;

public class ShellyLight extends ShellyBase{
    ShellyLIGHTTOGGLEJSON shellyToggleJson;
    ShellyLIGHTSTATUSJSON shellyStatusJson;

    protected ShellyLight(String ip, int channel) {
        super(ip, channel);
    }

    @Override
    public boolean tryShelly() {
        try{
            HttpResponse<String> response = this.client.send(requestStatus, HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() != 200)
                return false;

            shellyStatusJson = mapper.readValue(response.body(), ShellyLIGHTSTATUSJSON.class);

            if(shellyStatusJson.lights.get(channel).ison == null)
                return false;

        }catch (Exception e){
            //System.out.println("Error in Shelly init: " + e.getMessage());
            return false;
        }

        return true;
    }



    @Override
    protected String getStatusUrl() {
        return "http://" + ip + "/status";
    }

    @Override
    protected String getToggleUrl()
    {
        return "http://" + ip + "/light/0?turn=toggle";
    }

    @Override
    protected Boolean queryStatus(String response) {
        try {
            shellyStatusJson = mapper.readValue(response, ShellyLIGHTSTATUSJSON.class);
            return shellyStatusJson.lights.get(channel).ison;
        } catch (Exception ignored) {
        }
        return null;
    }

    @Override
    public Boolean queryToggle(String response) {
        try {
            shellyToggleJson = mapper.readValue(response, ShellyLIGHTTOGGLEJSON.class);
            return shellyToggleJson.ison;
        } catch (Exception ignored) {
        }
        return null;
    }
}
