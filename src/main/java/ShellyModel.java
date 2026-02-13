import Shelly.ShellyBase;
import javafx.application.Platform;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ShellyModel {
    private Map<Integer, ShellyBase> shellyMap = new HashMap<>();
    private List<Integer> normalShellys = new LinkedList<>();
    private List<Integer> em3IDs = new LinkedList<>();
    private List<Integer> tempIDs = new LinkedList<>();

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private Runnable onFinishedCallback;

    public void addShelly(ShellyBase shelly)
    {
        if(shelly == null)
            return;

        int id = shelly.getId();

        if (shelly.isEm3())
            em3IDs.add(id);

        if (shelly.isTemp())
            tempIDs.add(id);

        if(!shelly.isEm3() && !shelly.isTemp())
            normalShellys.add(id);

        shellyMap.put(id, shelly);
    }


    public void startPolling()
    {
        int size = shellyMap.size();
        new Thread(()->loopStarter(shellyMap.values(), size)).start();
    }


    private void loopStarter(Collection<ShellyBase> values, int size)
    {
        for (ShellyBase shelly : values) {
            new Thread(() -> {

                shelly.startPoller(size);
                System.out.println("Started Poller");


            }).start();

            try {
                Thread.sleep(200);
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
        final List<String[]> csvList;
        try {
            csvList = getShellyInfoCSV();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }


        AtomicInteger index = new AtomicInteger();
        AtomicInteger counterRetries = new AtomicInteger();

        scheduler.scheduleAtFixedRate(()->{
            if(index.get() >= csvList.size()) {
                if(onFinishedCallback != null) {
                    onFinishedCallback.run();
                }
                this.startPolling();
                scheduler.shutdown();
                return;
            }

            if(counterRetries.get() > 5)
            {
                counterRetries.set(0);
                index.getAndIncrement();
                return;
            }

            String[] csvShelly = csvList.get(index.get());
            ShellyBase shelly = ShellyBase.autodetectShelly(csvShelly[0],csvShelly[1]);
            if (shelly == null)
            {
                counterRetries.getAndIncrement();
                return;
            }

            shelly.addCords(Double.parseDouble(csvShelly[2]),Double.parseDouble(csvShelly[3]));
            System.out.println(shelly);
            addShelly(shelly);
            counterRetries.set(0);
            index.getAndIncrement();
            if(onFinishedCallback != null) {
                onFinishedCallback.run();
            }
        },0,200, TimeUnit.MILLISECONDS);

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

    public List<Integer> getNormalShellys()
    {
        return normalShellys;
    }

    public void setOnFinishedCallback(Runnable callback) {
        this.onFinishedCallback = callback;
    }
}

