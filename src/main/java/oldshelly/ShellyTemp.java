package oldshelly;

import java.net.http.HttpResponse;

public class ShellyTemp extends ShellyBase{
    private String[] temp = {"0", "0", "0"};
    private static ShellyTemp instance = null;

    private ShellyTemp(String ip, int channel) throws ShellyException {
        super(ip, channel);
    }

    @Override
    protected String getStatusUrl() {
        return "http://" + ip + "/status";
    }

    public void fetchTmp() throws ShellyException
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
            temp = new String[]{helper1, helper2, helper3};
        }catch (Exception e){
            throw new ShellyException("TMP Sensor error");
        }
    }

    public static ShellyTemp getInstance() throws ShellyException {
        if(instance == null)
            instance = new ShellyTemp("192.168.2.41", 0);

        return instance;
    }


    public String[] getTemp() {return temp;}

    @Override
    public Boolean fetchStatus() {
        return null;
    }
}
