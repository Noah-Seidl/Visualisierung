package Shelly;

public class testShelly {
    static Shelly2PM shelly;

    public static void main(String[] args) {
        try {
            shelly = new Shelly2PM("192.168.2.10",0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        shelly.fetchStatus();

    }
}



