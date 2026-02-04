package shelly;

import netscape.javascript.JSObject;

import java.io.IOException;
import java.net.http.HttpResponse;

public class Shelly2PM extends ShellyBase{

    protected Shelly2PM(String ip, int channel) throws Exception {
        super(ip, channel);
    }


    @Override
    protected String getStatusUrl() {
        return "http://" + ip +"/rpc/Switch.GetStatus?id=" + channel;
    }


    @Override
    public Boolean fetchStatus() {
        try {
            HttpResponse<String> response = client.send(requestStatus, HttpResponse.BodyHandlers.ofString());
            status = response.body().contains("\"output\":true");
            return status;
        }catch (Exception e){
            System.out.println("Error in fetchStatus Shelly2PM: " + e.getMessage());
        }

        return status;
    }

}
