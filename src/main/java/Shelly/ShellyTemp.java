package Shelly;

import Shelly.ShellyEM3STATUSJSON.ShellyEM3STATUSJSON;
import Shelly.ShellyLIGHTJSON.ShellyLIGHTSTATUSJSON;
import Shelly.ShellyTEMPSTATUSJSON.ShellyTEMPSTATUSJSON;

import java.net.http.HttpResponse;

public class ShellyTemp extends ShellyBase{
    ShellyTEMPSTATUSJSON shellyJson;

    protected ShellyTemp(String ip, String channel) {
        super(ip, channel);
    }

    @Override
    public boolean tryShelly() {
        try{
            HttpResponse<String> response = this.client.send(requestStatus, HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() != 200)
                return false;

            shellyJson = mapper.readValue(response.body(), ShellyTEMPSTATUSJSON.class);

            if(shellyJson.extTemperature.one.tC == null)
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
    protected Boolean queryStatus(String response) {
        return null;
    }

    @Override
    public Boolean queryToggle(String response) {
        return null;
    }

    public Double[] queryTemp(String response)
    {
        try {
            shellyJson = mapper.readValue(response, ShellyTEMPSTATUSJSON.class);
            return new Double[]{shellyJson.extTemperature.zero.tC, shellyJson.extTemperature.one.tC, shellyJson.extTemperature.two.tC};
        } catch (Exception ignored) {
        }
        return null;
    }

    protected Boolean fetchStatus(){
        try {
            String response = client.send(requestStatus, HttpResponse.BodyHandlers.ofString()).body();
            temp = queryTemp(response);
            return null;
        } catch (Exception e) {
            System.out.println("Error in Shelly toggle: " + e.getMessage());
        }
        return null;
    }
}
