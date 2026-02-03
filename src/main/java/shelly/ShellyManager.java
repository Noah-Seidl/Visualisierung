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
    List<ShellyDevice> shellys = new LinkedList<>();
    private Timeline timeline;
    private static int restartCounter;

    public int createShellyList()
    {
        if(restartCounter++ > 10)
            return -1;

        List<String> shellyList;
        try {
            shellyList = getShellyInfoCSV();
        } catch (IOException e) {
            return -1;
        }

        for(String shelly : shellyList)
        {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("Error Sleep" + e.getMessage());
            }

            String[] helper = shelly.split(",");
            ShellyDevice newShelly = ShellyFactory.autoDetect(helper[0],Integer.parseInt(helper[1]));
            if(newShelly == null)
                return 0;

            newShelly.addCoords(Double.parseDouble(helper[2]), Double.parseDouble(helper[3]));
            shellys.add(newShelly);
        }

        return 1;
    }

    private List<String> getShellyInfoCSV() throws IOException {
        List<String> shellyList = new LinkedList<>();
        InputStream is = getClass().getResourceAsStream("/shellys.csv");
        if(is == null)
            throw new IOException("InputStream null");
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));

        String line;
        while((line = reader.readLine()) != null){
            shellyList.add(line);
        }

        reader.close();

        return shellyList;
    }

    public void startStatusCheck(){
        timeline = new Timeline(
                new KeyFrame(Duration.seconds(shellys.size()), actionEvent -> new Thread(this::updateStatus).start())
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

    }
    public void stopStatusCheck(){
        timeline.stop();
    }

    @SuppressWarnings("BusyWait")
    public void updateStatus() {
        List<Integer> indexList = new LinkedList<>();
        for (int index = 0; index < shellys.size() - 1; index++) {


            ShellyDevice shelly = shellys.get(index);
            boolean oldStatus = shelly.getStatus();
            boolean newStatus = shelly.status();

            System.out.println("Shelly Status: " + oldStatus);

            if (oldStatus != newStatus)
                indexList.add(index);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Sleep fehler");
            }

        }
        Platform.runLater(() -> notifyO(indexList));
    }

    public List<ShellyDevice> getList()
    {
        return shellys;
    }


}
