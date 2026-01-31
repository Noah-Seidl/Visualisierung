package shelly;

import java.net.http.HttpResponse;

public class ShellyRelay extends ShellyBase{
    protected ShellyRelay(String ip, int channel) throws ShellyException {
        super(ip, channel);
    }

    @Override
    protected String getStatusUrl() {
        return "http://" + ip + "/relay/0";
    }



    @Override
    public Boolean status() {
        try {
            HttpResponse<String> response = client.send(requestStatus, HttpResponse.BodyHandlers.ofString());
            status = response.body().contains("\"ison\":true");
            return response.body().contains("\"ison\":true");
        }catch (Exception e){
            return status;
        }
    }

}
