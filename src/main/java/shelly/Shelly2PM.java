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
    public Boolean status() {
        try {
            HttpResponse<String> response = client.send(requestStatus, HttpResponse.BodyHandlers.ofString());
            status = response.body().contains("\"output\":true");
            return response.body().contains("\"output\":true");
        }catch (Exception e){
            return status;
        }
    }

}
