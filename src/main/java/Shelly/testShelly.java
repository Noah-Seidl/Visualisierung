package Shelly;

import java.util.Arrays;

public class testShelly {
    static ShellyBase shelly;

    public static void main(String[] args) {
        ShellyBase shelly;

        shelly = ShellyBase.autodetectShelly("192.168.2.29", "0");
        assert shelly != null;
        System.out.println(shelly.tryShelly());
        System.out.println(shelly.getClass().getName());
        shelly.fetchStatus();
        System.out.println("Status: " + shelly.getStatus());
        shelly.fetchToggle();
    }
}



