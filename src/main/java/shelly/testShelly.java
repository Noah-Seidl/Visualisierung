package shelly;

public class testShelly {
    public static void main(String[] args) {
        ShellyDevice shelly = ShellyFactory.autoDetect("192.168.2.11", 0);

        if(shelly != null)
            System.out.println(shelly.status());

    }
}
