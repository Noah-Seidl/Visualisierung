package shelly;

import javafx.application.Platform;
import javafx.scene.input.InputMethodRequests;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ShellyManager extends Subject{
    List<ShellyDevice> shellys = new LinkedList<>();
    private Timer statusTimer;
    private int i = 0;

    public ShellyManager(){
        createShellyList();
    }

    public void createShellyList()
    {
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

                Thread.sleep(50);
            }
            reader.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    public void addShelly(ShellyDevice shelly)
    {
        shellys.add(shelly);
    }

    public void startStatusCheck(){
        statusTimer = new Timer();
        statusTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                updateStatus();
            }
        },0,500);
    }

    public void stopStatusCheck(){
        statusTimer.cancel();
    }


    public void updateStatus()
    {

            ShellyDevice shelly = shellys.get(i++);
            System.out.println("Status: " + shelly.getStatus());
            //System.out.println("Status Check: " + shelly.getStatus() + " new status: " + shelly.status());
            if(shelly.getStatus() != shelly.status())
               Platform.runLater(this::notifyO);

            if(i>=shellys.size())
                i = 0;

    }

    public List<ShellyDevice> getList()
    {
        return shellys;
    }


}
