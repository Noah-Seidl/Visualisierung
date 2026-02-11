package oldshelly;

import java.net.http.HttpResponse;

public class ShellyLight extends ShellyBase{
    protected ShellyLight(String ip, int channel) throws ShellyException {
        super(ip, channel);
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
    public Boolean fetchStatus() {
        try {
            HttpResponse<String> response = client.send(requestStatus, HttpResponse.BodyHandlers.ofString());
            status = response.body().contains("\"ison\":true");
            return status;
        }catch (Exception e){
            return status;
        }
    }

}
