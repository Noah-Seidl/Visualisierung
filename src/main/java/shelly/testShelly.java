package shelly;

import java.util.Arrays;

public class testShelly {
    public static void main(String[] args) {
        try {
            ShellyTemp shelly = new ShellyTemp("192.168.2.41", 0);
            String[] tmp = shelly.getTemp();
            System.out.println("Power: " + Arrays.toString(tmp));


        }catch (Exception ignored){}




    }
}
