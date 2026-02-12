import Shelly.ShellyBase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class ShellyModel {
    private Map<Integer, ShellyBase> shellyMap = new HashMap<>();
    private List<Integer> em3IDs = new LinkedList<>();
    private List<Integer> tempIDs = new LinkedList<>();



    public boolean addShelly(ShellyBase shelly)
    {
        if(shelly == null)
            return false;

        int id = shelly.getId();

        if (shelly.isEm3())
            em3IDs.add(id);

        if (shelly.isTemp())
            tempIDs.add(id);

        shellyMap.put(id, shelly);
        return true;
    }


    public void startPolling()
    {
        int size = shellyMap.size();
        loopStarter(shellyMap.values(), size);
    }


    private void loopStarter(Collection<ShellyBase> values, int size)
    {
        for (ShellyBase shelly : values) {
            new Thread(() -> {

                shelly.startPoller(size);
                System.out.println("Started Poller");


            }).start();

            try {
                Thread.sleep(500);
            } catch (InterruptedException ignored) {}

        }
    }


    public void stopPolling()
    {
        loopStoper(shellyMap.values());
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

    public void makeShellyMaps() {
        List<String[]> csvList = null;
        try {
            csvList = getShellyInfoCSV();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ShellyBase shelly;
        int counterRetries = 0;

        for (String[] csvShelly : csvList) {

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            counterRetries = 0;
            shelly = null;
            while (shelly == null && counterRetries < 5) {
                shelly = ShellyBase.autodetectShelly(csvShelly[0], csvShelly[1]);
                counterRetries++;
            }

            if(shelly == null) {
                System.out.println("Error with shelly autodetect " + csvShelly[0] + " retries; " + counterRetries);
                continue;
            }

            shelly.addCords(Double.parseDouble(csvShelly[2]),Double.parseDouble(csvShelly[3]));

            addShelly(shelly);
        }
    }


    public Map<Integer, ShellyBase> getShellyMap()
    {
        return shellyMap;
    }

    public List<Integer> getEm3IDs()
    {
        return em3IDs;
    }

    public List<Integer> getTempIDs()
    {
        return tempIDs;
    }
}
