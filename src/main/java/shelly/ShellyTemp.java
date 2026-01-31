package shelly;

import java.net.http.HttpResponse;

public class ShellyTemp extends ShellyBase{

    public ShellyTemp(String ip, int channel) throws ShellyException {
        super(ip, channel);
    }

    @Override
    protected String getStatusUrl() {
        return "http://" + ip + "/status";
    }

    public String getTemp()
    {
        try {
            HttpResponse<String> response = client.send(requestStatus, HttpResponse.BodyHandlers.ofString());
            int index1 = response.body().indexOf("\"tC\":") + 5;
            int index2 = response.body().indexOf(',', index1);
            String helper1 = response.body().substring(index1,index2);
            index1 = response.body().indexOf("\"tC\":", index2) + 5;
            index2 = response.body().indexOf(',', index1);
            String helper2 = response.body().substring(index1,index2);
            index1 = response.body().indexOf("\"tC\":", index2) + 5;
            index2 = response.body().indexOf(',', index1);
            String helper3 = response.body().substring(index1,index2);
            return helper1 + "T" + helper2 + "T" + helper3;
        }catch (Exception e){
            System.out.println("Fehler Response" + e.getMessage());
            return "Error Tmp+Error Tmp+Error TmpTTT";
        }
    }



    @Override
    public Boolean status() {
        return null;
    }
}
