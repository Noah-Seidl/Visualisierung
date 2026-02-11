package Shelly;

import Shelly.ShellyEM3STATUSJSON.ShellyEM3STATUSJSON;
import Shelly.ShellyTEMPSTATUSJSON.ShellyTEMPSTATUSJSON;

import java.net.http.HttpResponse;

public class ShellyTemp extends ShellyBase{
    protected ShellyTemp(String ip, int channel) {
        super(ip, channel);
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

    public Double queryTemp(String response)
    {
        try {
            ShellyTEMPSTATUSJSON shellyJson = mapper.readValue(response, ShellyTEMPSTATUSJSON.class);
            return shellyJson.extTemperature.one.tC;
        } catch (Exception ignored) {
        }
        return null;
    }

    protected Boolean fetchStatus(){
        try {
            String response = client.send(requestStatus, HttpResponse.BodyHandlers.ofString()).body();
            status = queryStatus(response);
            return status;
        } catch (Exception e) {
            System.out.println("Error in Shelly toggle: " + e.getMessage());
        }
        return null;
    }
}
