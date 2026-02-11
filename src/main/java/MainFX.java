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
import oldshelly.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class MainFX extends Application implements Observer {
    List<ShellyDevice> shellys;
    Map<Integer, ShellyViewWrapper> ShellyViewMap;


    Image bulbOff;
    Image bulbOn;

    ShellyManager manager;

    Label clock = new Label("0");
    StatusVBox statusVBox;

    @Override
    @SuppressWarnings("BusyWait")
    public void init() throws Exception {
        super.init();

        manager = new ShellyManager();

        int status;
        while ((status = manager.createShellyList()) == 0){Thread.sleep(500);}

        if(status == -1)
            throw new Exception("Manager Error");

        while((status = StatusVBox.initShelly()) == 0){Thread.sleep(500);}

        if(status == -1)
            throw new Exception("Em3 Error");

        manager.registerObserver(this);
        manager.startStatusCheck();
        shellys = manager.getList();
        ShellyViewMap = new HashMap<>();
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



        //Draw all ShellyDevices
        for (int i = 0; i < shellys.size(); i++) {
            ShellyDevice shelly = shellys.get(i);
            ImageView iView = getImageView(shelly, i);

            ShellyViewWrapper wrapper = new ShellyViewWrapper(shelly, iView);
            ShellyViewMap.put(shelly.getId(), wrapper);
        }


        //Labels
        clock.setFont(new Font(35));
        clock.setLayoutX(350);
        clock.setLayoutY(-4);

        statusVBox = new StatusVBox(5);

        //Update every second
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), (actionEvent) -> {
                    setClockLabel();
                    statusVBox.update();
                })
        );

        Pane pane = new Pane();
        pane.getChildren().add(backgroundView);



        pane.getChildren().addAll(ShellyViewMap.values()
                .stream()
                .map(ShellyViewWrapper::getView)
                .collect(Collectors.toList()));

        pane.getChildren().addAll(clock, statusVBox);

        Scene scene = new Scene(pane, 1366, 768);

        stage.setScene(scene);

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        stage.setTitle("Visualisierung");
        //stage.setAlwaysOnTop(true);
        stage.setResizable(false);
        stage.show();
        stage.setFullScreen(true);
    }

    private ImageView getImageView(ShellyDevice shelly, int i) {
        ImageView iView = new ImageView();
        iView.setX(shelly.getX());
        iView.setY(shelly.getY());
        iView.setImage(bulbOff);
        iView.setPreserveRatio(true);
        iView.setFitHeight(60);
        iView.setFitWidth(60);

        iView.setOnMouseClicked((a)->{
            System.out.println("Toggle" + i);
            new Thread(()->{
                boolean status = shelly.fetchToggle();
                Platform.runLater(()->updateSingle(i, status));
            }).start();
        });
        iView.setPickOnBounds(true);
        return iView;
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
        String formatted = time.format(formatter);
        clock.setText(formatted);
    }


    @Override
    public void update(List<Integer> index) {
        System.out.println("An Shelly has changed it status " + index);
        for (int i  : index)
        {
            ShellyViewWrapper ShellyView= ShellyViewMap.get(i);

            if(ShellyView.getShelly().getStatus())
                ShellyView.getView().setImage(bulbOn);
            else
                ShellyView.getView().setImage(bulbOff);
        }
    }

    @Override
    public void updateSingle(int index, boolean status) {
        System.out.println("Index: " + index + " Status: " + status);

        ShellyViewWrapper ShellyView= ShellyViewMap.get(index);

        if(status)
            ShellyView.getView().setImage(bulbOn);
        else
            ShellyView.getView().setImage(bulbOff);
    }


}