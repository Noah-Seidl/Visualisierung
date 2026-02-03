import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import shelly.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;


public class HelloFX extends Application implements Observer {
    List<ShellyDevice> shellys;
    List<ImageView> imageViews;

    Image bulbOff;
    Image bulbOn;

    ShellyManager manager;

    Label clock = new Label("0");
    StatusVBox statusVBox;

    @Override
    public void start(Stage stage) {
        manager = new ShellyManager();
        manager.registerObserver(this);
        manager.startStatusCheck();
        shellys = manager.getList();



        //
        bulbOff = new Image("/bulbOff.png", true);
        bulbOn = new Image("/bulbOn.png", true);
        Image backgroundImage = new Image("/HausVisEnhanced.jpg", true);
        ImageView backgroundView = new ImageView(backgroundImage);
        backgroundView.setPreserveRatio(true);
        backgroundView.setFitHeight(730);
        backgroundView.setFitWidth(1300);
        backgroundView.setY(40);

        imageViews = new LinkedList<>();
        //Alle ShellyDevices Zeichnen
        for (int i = 0; i < shellys.size(); i++) {
            ShellyDevice shelly = shellys.get(i);

            ImageView iView = new ImageView();
            iView.setX(shelly.getX());
            iView.setY(shelly.getY());
            iView.setImage(bulbOff);
            int finalI = i;

            iView.setOnMouseClicked((a)->{
                System.out.println("Toggle" + finalI);
                new Thread(()->{
                    boolean status = shelly.toggle();
                    Platform.runLater(()->updateSingle(finalI, status));
                }).start();
            });

            iView.setPickOnBounds(true);
            imageViews.add(iView);
        }


        //Labels
        clock.setFont(new Font(35));
        clock.setLayoutX(350);
        clock.setLayoutY(-8);

        statusVBox = new StatusVBox(5);

        //jede Sekunde Update
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), (actionEvent) -> {
                    setClockLabel(); //Uhrzeit aktualisierung
                    statusVBox.update();
                })
        );



        Pane pane = new Pane();
        pane.getChildren().add(backgroundView);
        pane.getChildren().addAll(imageViews);
        pane.getChildren().addAll(clock, statusVBox);

        Scene scene = new Scene(pane, 1366, 768);

        stage.setScene(scene);

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        stage.show();
        stage.setFullScreen(true);
    }
    @Override
    public void stop() throws Exception{
        manager.stopStatusCheck();
        super.stop();
    }


    public static void main(String[] args) {
        launch();
    }


    private void setClockLabel()
    {
        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd. MMM yyyy HH:mm:ss");
        String timestr = time.format(formatter);
        clock.setText(timestr);
    }







    @Override
    public void update(List<Integer> index) {
        System.out.println("Ein shelly status hat sich geändert Shelly geändert: " + index);
        for (int i  : index)
        {
            if(shellys.get(i).getStatus())
                imageViews.get(i).setImage(bulbOn);
            else
                imageViews.get(i).setImage(bulbOff);
        }
    }

    @Override
    public void updateSingle(int index, boolean status) {
        System.out.println("Index: " + index + " Status: " + status);

        if(status)
            imageViews.get(index).setImage(bulbOn);
        else
            imageViews.get(index).setImage(bulbOff);
    }


}