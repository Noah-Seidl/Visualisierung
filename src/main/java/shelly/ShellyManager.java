package shelly;

import javafx.application.Platform;

import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ShellyManager extends Subject{
    List<ShellyDevice> shellys = new LinkedList<>();
    private Timer statusTimer;

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
        },0,10000);
    }

    public void stopStatusCheck(){
        statusTimer.cancel();
    }


    public void updateStatus()
    {

        for(ShellyDevice shelly: shellys)
        {
            System.out.println("Status: " + shelly.getStatus());
            //System.out.println("Status Check: " + shelly.getStatus() + " new status: " + shelly.status());
            if(shelly.getStatus() != shelly.status())
               Platform.runLater(this::notifyO);
        }
    }

    public List<ShellyDevice> getList()
    {
        return shellys;
    }


}
