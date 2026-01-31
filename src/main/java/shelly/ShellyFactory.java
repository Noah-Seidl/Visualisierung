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
            if(shelly != null)
                return shelly;
        } catch (Exception e) {

        }

        try {
            shelly = new ShellyRelay(ip, channel);
            if(shelly != null)
                return shelly;
        } catch (Exception e) {
        }

        try {
            shelly = new ShellyLight(ip, channel);
            if(shelly != null)
                return shelly;
        } catch (Exception e) {
        }

        return null;
    }




}
