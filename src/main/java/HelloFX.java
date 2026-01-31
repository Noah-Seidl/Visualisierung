import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class HelloFX extends Application {

    @Override
    public void start(Stage stage) {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        Label l = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        Button button = new Button("Drück mich");
        button.isDefaultButton();
        button.setOnAction((a)->System.out.println("pressed"));

        Image testpng = new Image("/bulbOff.png", true);
        Image testpng1 = new Image("/bulbOn.png", true);
        ImageView iView = new ImageView();
        iView.setOnMouseClicked((a)->{
            System.out.println("Bild gedrückt");
            if(testpng.equals(iView.getImage())) {
                iView.setImage(testpng1);
            }else{
                iView.setImage(testpng);
            }
        });
        iView.setImage(testpng);
        iView.setPickOnBounds(true);

        VBox stack = new VBox(10.0);
        stack.setAlignment(Pos.CENTER);
        stack.getChildren().addAll(button, l, iView);


        Scene scene = new Scene(stack, 640, 480);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}