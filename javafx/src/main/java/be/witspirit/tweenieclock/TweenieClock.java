package be.witspirit.tweenieclock;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TweenieClock extends Application {

    public static void main(String... args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Tweenie Clock");

        StackPane root = new StackPane();
        Scene scene = new Scene(root, 300, 250);

        Image clock = new Image("TweenieClock.jpg");
        ImageView clockView = new ImageView(clock);
        clockView.setPreserveRatio(true);
        clockView.fitWidthProperty().bind(scene.widthProperty());
        clockView.fitHeightProperty().bind(scene.heightProperty());
        root.getChildren().add(clockView);

        Image center = new Image("detail_purple_center.png");
        ImageView centerHighlight = new ImageView(center);
        StackPane.setAlignment(centerHighlight, Pos.TOP_LEFT);



        root.getChildren().add(centerHighlight);



        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
