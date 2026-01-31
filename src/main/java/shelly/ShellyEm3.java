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

    public String getPower(){

        try {
            HttpResponse<String> response = client.send(requestStatus, HttpResponse.BodyHandlers.ofString());
            int index1 = response.body().indexOf("total_act_power\":") + 17;
            int index2 = response.body().indexOf(',', index1);
            String helper = response.body().substring(index1,index2 - 1);
            return helper;
        }catch (Exception e){
            System.out.println("Fehler Response" + e.getMessage());
            return "Error Em3";
        }
    }


    @Override
    public Boolean status() {
        return null;
    }
}
