import Shelly.ShellyBase;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;


public class StatusVBox extends VBox {

    Label powerLabel = new Label("0");
    Label outTmp = new Label("0");
    Label radiatorTmp = new Label("0");
    Label boilerTmp = new Label("0");
    ShellyBase shellyEm3;
    ShellyBase shellyTemp;
    Double[] temp = {0.0,0.0,0.0};
    Double power = 0.0;
    int counter = 10;
    static int restartCounter = 0;

    public StatusVBox(double v, ShellyBase shellyEm3, ShellyBase shellyTemp)
    {
        super(v);

        this.shellyEm3 = shellyEm3;
        this.shellyTemp =  shellyTemp;

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
        startUpdater();
    }


    public void update()
    {
        outTmp.setTextFill(Color.BLACK);
        radiatorTmp.setTextFill(Color.BLACK);
        boilerTmp.setTextFill(Color.BLACK);
        powerLabel.setTextFill(Color.BLACK);

        power = shellyEm3.getPower();

        temp = shellyTemp.getTemp();

        String powerString = String.format("%.2f",power);
        powerLabel.setText(powerString + "W");

        if(power != null) {
            outTmp.setText(temp[0] + "°C");
            radiatorTmp.setText(temp[1] + "°C");
            boilerTmp.setText(temp[2] + "°C");
        }
    }

    public void startUpdater()
    {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(5), (actionEvent) -> {
                    update();
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

}
