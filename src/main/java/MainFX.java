import Shelly.ObserverShelly;
import Shelly.ShellyBase;
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;


public class MainFX extends Application implements ObserverShelly {
    Map<Integer, ImageView> viewMap;
    Map<Integer, ShellyBase> shellyBaseMap;

    Image bulbOff;
    Image bulbOn;

    ShellyModel model;

    Pane pane = new Pane();

    Label clock = new Label("0");
    StatusVBox statusVBox;

    @Override
    public void init() throws Exception {
        super.init();

        viewMap = new HashMap<>();

        model = new ShellyModel();

        model.setOnFinishedCallback(() -> {
            Platform.runLater(this::updateUI);
        });

        new Thread(() -> {
            model.makeShellyMaps();
        }).start();
    }

    @Override
    public void start(Stage stage) {
        //Light icons
        bulbOff = new Image("/bulbOff.png", true);
        bulbOn = new Image("/bulbOn.png", true);

        //Set Background
        Image backgroundImage = new Image("/HausVisEnhanced.jpg", true);
        ImageView backgroundView = new ImageView(backgroundImage);
        backgroundView.setPreserveRatio(true);
        backgroundView.setFitHeight(730);
        backgroundView.setFitWidth(1300);
        backgroundView.setY(40);





        //Labels
        clock.setFont(new Font(35));
        clock.setLayoutX(350);
        clock.setLayoutY(-4);

        //Update every second
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), (actionEvent) -> {
                    setClockLabel();
                })
        );

        pane.getChildren().add(backgroundView);


        pane.getChildren().addAll(clock);

        Scene scene = new Scene(pane, 1366, 768);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        stage.setScene(scene);
        stage.setTitle("Visualisation");

        //stage.setAlwaysOnTop(true);
        stage.setResizable(false);
        stage.show();
        stage.setFullScreen(true);
    }

    private void createImageView(ShellyBase shelly) {
        ImageView iView = new ImageView();
        iView.setX(shelly.getX());
        iView.setY(shelly.getY());
        iView.setImage(bulbOff);
        iView.setPreserveRatio(true);
        iView.setFitHeight(60);
        iView.setFitWidth(60);

        iView.setOnMouseClicked((a)->{
            System.out.println("Toggle" + shelly.getId());
            new Thread(()->{
                boolean status = shelly.fetchToggle();
                Platform.runLater(()->updateSingle(shelly.getId(), status));
            }).start();
        });
        iView.setPickOnBounds(true);
        viewMap.put(shelly.getId(), iView);
    }

    @Override
    public void stop() throws Exception{
        model.stopPolling();
        super.stop();
    }


    public static void main(String[] args) {
        launch();
    }


    private void setClockLabel()
    {
        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd. MMM yyyy HH:mm:ss");
        String formatted = time.format(formatter);
        clock.setText(formatted);
    }


    @Override
    public void update(int index) {
        ImageView iView =  viewMap.get(index);
        if(shellyBaseMap.get(index).getStatus())
            iView.setImage(bulbOn);
        else
            iView.setImage(bulbOff);
    }

    @Override
    public void updateSingle(int index, boolean status) {
        viewMap.get(index).setImage(status ? bulbOn : bulbOff);
    }

    public void updateUI(){

        shellyBaseMap = model.getShellyMap();

        if(!model.getEm3IDs().isEmpty() && !model.getTempIDs().isEmpty() && statusVBox == null) {
            statusVBox = new StatusVBox(5, shellyBaseMap.get(model.getEm3IDs().getFirst()), shellyBaseMap.get(model.getTempIDs().getFirst()));
            pane.getChildren().add(statusVBox);
        }
        shellyBaseMap.values().forEach((shellyBase)->shellyBase.registerObserver(this));
        //Draw all ShellyDevices
        shellyBaseMap.values().stream()
                .filter(s -> !s.isEm3() && !s.isTemp())
                .filter(s -> !viewMap.containsKey(s.getId()))
                .forEach(s -> {
                    createImageView(s);
                    pane.getChildren().add(viewMap.get(s.getId()));
                });
    }

}