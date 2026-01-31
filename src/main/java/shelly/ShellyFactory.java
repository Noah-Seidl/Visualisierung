package shelly;

public class ShellyFactory {
    public enum ShellyType{
        PM2,LIGHT,RELAY
    }

    public static ShellyDevice autoDetect(String ip, int channel)
    {
        ShellyDevice shelly = null;
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
