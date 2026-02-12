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

        Map<Integer, ShellyBase> shellys = model.getShellyMap();

        try {
            Thread.sleep(30 * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        shellys.forEach((key,shelly) ->System.out.println(shelly.toString()));

    }

}
