import Shelly.ShellyBase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class ShellyModel {
    private Map<Integer, ShellyBase> lightMap = new HashMap<>();
    private Map<Integer, ShellyBase> em3Map = new HashMap<>();
    private Map<Integer, ShellyBase> tempMap = new HashMap<>();


    public boolean addShelly(ShellyBase shelly)
    {
        if(shelly == null)
            return false;

        int id = shelly.getId();
        if (shelly.isEm3())
            em3Map.put(id, shelly);

        if (shelly.isTemp())
            tempMap.put(id, shelly);

        lightMap.put(id, shelly);
        return true;
    }


    public void startPolling()
    {
        int size = lightMap.size() + em3Map.size() + tempMap.size();

        loopStarter(lightMap.values(), size);
        loopStarter(em3Map.values(),size);
        loopStarter(tempMap.values(), size);
    }


    private void loopStarter(Collection<ShellyBase> values, int size)
    {
        for (ShellyBase shelly : values) {
            new Thread(() -> {

                shelly.startPoller(size);
                System.out.println("Started Poller");


            }).start();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {}

        }
    }


    public void stopPolling()
    {
        loopStoper(lightMap.values());
        loopStoper(em3Map.values());
        loopStoper(tempMap.values());
    }


    private void loopStoper(Collection<ShellyBase> values)
    {
        for (ShellyBase shelly : values) {
            new Thread(shelly::stopPoller).start();
        }
    }


    private List<String[]> getShellyInfoCSV() throws IOException {
        List<String[]> shellyList = new LinkedList<>();

        InputStream is = getClass().getResourceAsStream("/shellys.csv");

        if(is == null)
            throw new IOException("InputStream null");

        BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));

        String line;
        while((line = reader.readLine()) != null){
            shellyList.add(line.split(","));
        }

        reader.close();
        return shellyList;
    }

    public void makeShellyMaps() throws Exception {
        List<String[]> csvList = getShellyInfoCSV();

        for (String[] csvShelly : csvList) {
            ShellyBase shelly = ShellyBase.autodetectShelly(csvShelly[0], csvShelly[1]);

            if(shelly == null)
                throw new Exception("Error with shelly autodetect");

            shelly.addCords(Double.parseDouble(csvShelly[2]),Double.parseDouble(csvShelly[3]));

            addShelly(shelly);
        }
    }


    public Map<Integer, ShellyBase> getLightMap()
    {
        return lightMap;
    }

    public Map<Integer, ShellyBase> getEm3Map()
    {
        return em3Map;
    }

    public Map<Integer, ShellyBase> getTempMap()
    {
        return tempMap;
    }
}
