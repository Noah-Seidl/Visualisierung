import javafx.application.Application;
import javafx.beans.Observable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import shelly.Observer;
import shelly.ShellyDevice;
import shelly.ShellyFactory;
import shelly.ShellyManager;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;


public class HelloFX extends Application implements Observer {
    List<ShellyDevice> shellys;
    List<ImageView> imageViews;
    Image bulbOff;
    Image bulbOn;
    ShellyManager manager;

    @Override
    public void start(Stage stage) {
        manager = new ShellyManager();
        manager.registerObserver(this);
        manager.addShelly(Objects.requireNonNull(ShellyFactory.autoDetect("192.168.2.10", 0)).addCoords(100, 200));
        manager.addShelly(Objects.requireNonNull(ShellyFactory.autoDetect("192.168.2.11", 0)).addCoords(50, 50));

        manager.startStatusCheck();
        shellys = manager.getList();

        bulbOff = new Image("/bulbOff.png", true);
        bulbOn = new Image("/bulbOn.png", true);
        Image backgroundImage = new Image("/HausVisEnhanced.jpg", true);
        ImageView backgroundView = new ImageView(backgroundImage);
        backgroundView.setPreserveRatio(true);
        backgroundView.setFitHeight(730);
        backgroundView.setFitWidth(1300);
        backgroundView.setY(30);

        imageViews = new LinkedList<>();

        for(ShellyDevice shelly:shellys)
        {
            ImageView iView = new ImageView();
            iView.setX(shelly.getX());
            iView.setY(shelly.getY());
            iView.setImage(bulbOff);
            iView.setOnMouseClicked((a)->{
                System.out.println("Toggle");
                shelly.toggle();
                update();
            });
            iView.setPickOnBounds(true);
            imageViews.add(iView);
        }



        Pane pane = new Pane();
        pane.getChildren().add(backgroundView);
        pane.getChildren().addAll(imageViews);

        Scene scene = new Scene(pane, 1366, 768);

        stage.setScene(scene);
        stage.show();
    }
    @Override
    public void stop() throws Exception{
        manager.stopStatusCheck();
        super.stop();
    }


    public static void main(String[] args) {
        launch();
    }

    @Override
    public void update() {
        System.out.println("Ein shelly status hat sich geändert");
        for (int i = 0; i < shellys.size(); i++) {
            if (shellys.get(i).status()) {
                imageViews.get(i).setImage(bulbOn);
            } else {
                imageViews.get(i).setImage(bulbOff);
            }
        }
        
        
        
    }
}