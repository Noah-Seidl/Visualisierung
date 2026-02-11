package Shelly;

import java.util.Arrays;

public class testShelly {
    static ShellyBase shelly;

    public static void main(String[] args) {
        ShellyBase shelly;

        shelly = ShellyBase.autodetectShelly("192.168.2.40", "0");

        assert shelly != null;
        shelly.startPoller(5.0);
        shelly.fetchStatus();


        System.out.println("Power: " + shelly.getPower());

        try {
            Thread.sleep((long) 6000.0);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Power: " + shelly.getPower());

        try {
            Thread.sleep((long) 6000.0);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Power: " + shelly.getPower());


        shelly.stopPoller();
    }
}



