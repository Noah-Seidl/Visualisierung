import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import shelly.ShellyEm3;
import shelly.ShellyException;
import shelly.ShellyTemp;


public class StatusVBox extends VBox {

    Label powerLabel = new Label("0");
    Label outTmp = new Label("0");
    Label radiatorTmp = new Label("0");
    Label boilerTmp = new Label("0");
    static ShellyEm3 shellyEm3;
    static ShellyTemp shellyTemp;
    String[] temp = {"TEMP", "TEMP", "TEMP"};
    String power = "0";
    int counter = 10;
    static int restartCounter = 0;

    public static int initShelly()
    {
        if(restartCounter++ >10)
            return -1;
        try {
            shellyEm3 = new ShellyEm3("192.168.2.40", 0);
            shellyTemp = new ShellyTemp("192.168.2.41", 0);
        } catch (ShellyException e) {
            return 0;
        }
        return 1;
    }

    public StatusVBox(double v)
    {
        super(v);

        this.setAlignment(Pos.TOP_CENTER);
        this.setLayoutX(1030);
        this.setLayoutY(20);

        //Status Labels:
        Label l1 = new Label("Gesamtleistung: ");
        l1.setFont(new Font(35));

        Label l2 = new Label("Außentemperatur:");
        l2.setFont(new Font(35));

        Label l3 = new Label("Vorlauftemperatur:");
        l3.setFont(new Font(35));

        Label l4 = new Label("Boilertemperatur:");
        l4.setFont(new Font(35));

        powerLabel.setFont(new Font(25));
        outTmp.setFont(new Font(25));
        radiatorTmp.setFont(new Font(25));
        boilerTmp.setFont(new Font(25));

        this.getChildren().addAll(l1,powerLabel,l2,outTmp,l3,radiatorTmp,l4,boilerTmp);
    }


    public void update()
    {
        if(counter++ < 5)
            return;

        outTmp.setTextFill(Color.BLACK);
        radiatorTmp.setTextFill(Color.BLACK);
        boilerTmp.setTextFill(Color.BLACK);
        powerLabel.setTextFill(Color.BLACK);

        try {
            power = shellyEm3.getPower();
        } catch (Exception e) {
            powerLabel.setTextFill(Color.RED);
        }

        try {
            temp = shellyTemp.getTemp();
        } catch (Exception e) {
            outTmp.setTextFill(Color.RED);
            radiatorTmp.setTextFill(Color.RED);
            boilerTmp.setTextFill(Color.RED);
        }

        powerLabel.setText(power + "W");
        outTmp.setText(temp[0] + "°C");
        radiatorTmp.setText(temp[1] + "°C");
        boilerTmp.setText(temp[2] + "°C");
        counter = 0;
    }


}
