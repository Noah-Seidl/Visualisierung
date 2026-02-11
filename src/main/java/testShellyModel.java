import Shelly.ShellyBase;

import java.util.Map;

public class testShellyModel {
    public static void main(String[] args) {
        ShellyModel model = new ShellyModel();

        try {
            model.makeShellyMaps();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        model.startPolling();

        Map<Integer, ShellyBase> shellys = model.getLightMap();
        Map<Integer, ShellyBase> em3s = model.getEm3Map();
        Map<Integer, ShellyBase> temps = model.getTempMap();

        try {
            Thread.sleep(30 * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        shellys.forEach((key,shelly) ->System.out.println(shelly.toString()));

        em3s.forEach((key,shelly) ->System.out.println(shelly.toString()));

        temps.forEach((key,shelly) ->System.out.println(shelly.toString()));

    }
}
