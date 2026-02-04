package shelly;

import java.net.http.HttpResponse;

public class ShellyEm3 extends ShellyBase{


    public ShellyEm3(String ip, int channel) throws ShellyException {
        super(ip, channel);
    }

    @Override
    protected String getStatusUrl() {
        return "http://" + ip + "/rpc/EM.GetStatus?id=0";
    }

    public String getPower() throws ShellyException {

        try {
            HttpResponse<String> response = client.send(requestStatus, HttpResponse.BodyHandlers.ofString());
            int index1 = response.body().indexOf("total_act_power\":") + 17;
            int index2 = response.body().indexOf(',', index1);
            return response.body().substring(index1,index2 - 1);
        }catch (Exception e){
            throw new ShellyException("TMP Sensor error");
        }
    }


    @Override
    public Boolean fetchStatus() {
        return null;
    }
}
