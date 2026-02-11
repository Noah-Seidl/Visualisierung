package Shelly;

import Shelly.Shelly2PMJSON.Shelly2PMSTATUSJSON;
import Shelly.ShellyEM3STATUSJSON.ShellyEM3STATUSJSON;

import java.net.http.HttpResponse;

public class ShellyEM3 extends ShellyBase{
    protected ShellyEM3(String ip, String channel) {
        super(ip, channel);
    }

    @Override
    protected String getStatusUrl() {
        return "http://" + ip + "/rpc/EM.GetStatus?id=0";
    }


    @Override
    protected Boolean queryStatus(String response) {
        return null;
    }

    @Override
    public Boolean queryToggle(String response) {
        return null;
    }

    private Double queryPower(String response) {
        try {
            ShellyEM3STATUSJSON shellyJson = mapper.readValue(response, ShellyEM3STATUSJSON.class);
            return shellyJson.totalActPower;
        } catch (Exception ignored) {
        }
        return null;
    }

    protected Boolean fetchStatus(){
        try {
            String response = client.send(requestStatus, HttpResponse.BodyHandlers.ofString()).body();
            power = queryPower(response);
            return null;
        } catch (Exception e) {
            System.out.println("Error in Shelly toggle: " + e.getMessage());
        }
        return null;
    }

    protected Boolean fetchToggle() {
        return null;
    }

}
