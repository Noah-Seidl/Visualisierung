package shelly;

public class ShellyFactory {

    public static ShellyDevice autoDetect(String ip, int channel)
    {
        ShellyDevice shelly;
        try {
            shelly = new Shelly2PM(ip, channel);
            return shelly;
        } catch (Exception ignored) {}

        try {
            shelly = new ShellyRelay(ip, channel);
            return shelly;
        } catch (Exception ignored) {}

        try {
            shelly = new ShellyLight(ip, channel);
            return shelly;
        } catch (Exception ignored) {}

        return null;
    }

}
