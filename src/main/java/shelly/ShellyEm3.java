package shelly;

import java.net.http.HttpResponse;

public class ShellyEm3 extends ShellyBase{
    private static ShellyEm3 instance = null;
    private String power = "";


    private ShellyEm3(String ip, int channel) throws ShellyException {
        super(ip, channel);
    }

    @Override
    protected String getStatusUrl() {
        return "http://" + ip + "/rpc/EM.GetStatus?id=0";
    }

    public void fetchPower() throws ShellyException {
        try {
            HttpResponse<String> response = client.send(requestStatus, HttpResponse.BodyHandlers.ofString());
            int index1 = response.body().indexOf("total_act_power\":") + 17;
            int index2 = response.body().indexOf(',', index1);
            power = response.body().substring(index1,index2 - 1);
        }catch (Exception e){
            throw new ShellyException("Em3 error");
        }
    }

    public static ShellyEm3 getInstance() throws ShellyException {
        if(instance == null)
            instance = new ShellyEm3("192.168.2.40", 0);

        return instance;
    }


    public String getPower(){return power;}



    @Override
    public Boolean fetchStatus() {
        return null;
    }
}
