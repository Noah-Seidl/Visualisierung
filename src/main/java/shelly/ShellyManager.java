package shelly;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.util.Duration;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

public class ShellyManager extends Subject{
    List<ShellyDevice> shellys = new LinkedList<>();
    private Timeline timeline;

    public ShellyManager(){
        createShellyList();
    }

    public void createShellyList()
    {
        //Alle lichter Shellys werden initialisiert
        try{
            InputStream is = getClass().getResourceAsStream("/shellys.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String line;
            while((line = reader.readLine()) != null){
                System.out.println("Line: " + line);
                String[] helper = line.split(",");
                ShellyDevice shelly =  ShellyFactory.autoDetect(helper[0],Integer.parseInt(helper[1]));
                if(shelly != null) {
                    shelly.addCoords(Double.parseDouble(helper[2]), Double.parseDouble(helper[3]));
                    shellys.add(shelly);
                }

                Thread.sleep(100);
            }
            reader.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void addShelly(ShellyDevice shelly) {shellys.add(shelly);}

    public void startStatusCheck(){
        timeline = new Timeline(
                new KeyFrame(Duration.seconds(shellys.size()), actionEvent -> {
                    new Thread(this::updateStatus).start();
                }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

    }
    public void stopStatusCheck(){
        timeline.stop();
    }


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
