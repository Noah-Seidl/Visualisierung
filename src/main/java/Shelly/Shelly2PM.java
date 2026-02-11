package Shelly;

import Shelly.ShellyJSONs.Shelly2PMJSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import shelly.ShellyBase;


import java.net.MalformedURLException;
import java.net.URL;
import java.net.http.HttpResponse;

public class Shelly2PM extends ShellyBase {

    protected Shelly2PM(String ip, int channel) throws Exception {
        super(ip, channel);
    }


    @Override
    protected String getStatusUrl() {
        return "http://" + ip +"/rpc/Switch.GetStatus?id=" + channel;
    }


    @Override
    public Boolean fetchStatus() {

        ObjectMapper mapper = new ObjectMapper();
        Shelly2PMJSON shellyJson;
        try {
            shellyJson = mapper.readValue(new URL(getStatusUrl()), Shelly2PMJSON.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        System.out.println("Status: " + shellyJson.output + " Power: " + shellyJson.apower +  " Temp: " + shellyJson.temperature.tC);


        return true;
    }

}
