package shelly;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.util.Duration;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

public class ShellyManager extends Subject{
    List<ShellyDevice> shellyDeviceList = new LinkedList<>();
    private Timeline timeline;
    private static int restartCounter;
    private ShellyEm3 shellyEm3;
    private ShellyTemp shellyTemp;

    public int createShellyList()
    {
        restartCounter++;

        List<String[]> shellyList;
        try {
            shellyList = getShellyInfoCSV();
        } catch (IOException e) {
            return -1;
        }

        for(String[] shellyInfo : shellyList)
        {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("Error Sleep" + e.getMessage());
            }

            ShellyDevice newShelly = ShellyFactory.autoDetect(shellyInfo[0],Integer.parseInt(shellyInfo[1]));

            if(newShelly == null && restartCounter < 10)
                return 0;

            if(newShelly != null) {
                newShelly.addCords(Double.parseDouble(shellyInfo[2]), Double.parseDouble(shellyInfo[3]));
                shellyDeviceList.add(newShelly);
            }
        }

        try {
            shellyEm3 = ShellyEm3.getInstance();
        } catch (ShellyException e) {
            System.out.println("Shelly3 failed");
            return 0;
        }

        try {
            shellyTemp = ShellyTemp.getInstance();
        } catch (ShellyException e) {
            System.out.println("Shelly temp failed");
            return 0;
        }

        return 1;
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


    public void startStatusCheck(){
        timeline = new Timeline(
                new KeyFrame(Duration.seconds(shellyDeviceList.size()), actionEvent -> new Thread(this::updateStatus).start())
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

    }


    public void stopStatusCheck(){
        timeline.stop();
    }


    public void updateStatus() {
        List<Integer> indexList = new LinkedList<>();

        for (ShellyDevice shelly : shellyDeviceList) {
            boolean oldStatus = shelly.getStatus();
            boolean newStatus = shelly.fetchStatus();

            if (oldStatus != newStatus)
                indexList.add(shelly.getId());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Sleep Error");
            }
        }

        try {
            shellyEm3.fetchPower();
        } catch (ShellyException e) {
            throw new RuntimeException(e);
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("Sleep Error");
        }

        try {
            shellyTemp.fetchTmp();
        } catch (ShellyException e) {
            throw new RuntimeException(e);
        }

        if(!indexList.isEmpty())
            Platform.runLater(() -> notifyO(indexList));
    }


    public List<ShellyDevice> getList()
    {
        return shellyDeviceList;
    }
}
