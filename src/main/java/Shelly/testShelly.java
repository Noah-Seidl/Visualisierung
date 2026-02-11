package Shelly;

import java.util.Arrays;

public class testShelly {
    static ShellyBase shelly;

    public static void main(String[] args) {
        ShellyBase shelly;

        shelly = ShellyBase.autodetectShelly("192.168.2.41", "0");

        System.out.println("Shelly class: " + shelly.getClass().getName());

    }
}



