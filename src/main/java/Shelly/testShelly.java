package Shelly;

public class testShelly {
    static ShellyBase shelly;

    public static void main(String[] args) {
        try {
            shelly = new ShellyEM3("192.168.2.41",0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        System.out.println("Status: " + shelly.fetchStatus());
        System.out.println("Power: " + shelly.getPower());

    }
}



