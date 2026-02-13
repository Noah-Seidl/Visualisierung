import Shelly.ShellyBase;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class testShellyModel {
    public static void main(String[] args) {


        List<Integer> list = new LinkedList<>();

        list.add(2);
        list.add(3);

        Iterator<Integer> it = list.iterator();



        while (it.hasNext())
        {
            System.out.println("it: " + it.next());
        }




/*
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
*/
    }

}
