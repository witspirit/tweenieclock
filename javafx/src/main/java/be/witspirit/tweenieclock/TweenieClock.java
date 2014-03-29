package be.witspirit.tweenieclock;

import javafx.animation.*;
import javafx.application.Application;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.*;

public class TweenieClock extends Application {

    public static void main(String... args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Tweenie Clock");

        AnchorPane root = new AnchorPane();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        Image clock = new Image("TweenieClock.jpg");
        ImageView clockView = new ImageView(clock);
        clockView.setPreserveRatio(true);
        AnchorPane.setTopAnchor(clockView, 0.0);
        AnchorPane.setLeftAnchor(clockView, 0.0);
        AnchorPane.setBottomAnchor(clockView, 0.0);
        AnchorPane.setRightAnchor(clockView, 0.0);
        root.getChildren().add(clockView);

        Detail center = new Detail("purple_center", 1089, 919);

        Detail exclamation = new Detail("yellow_exclamation", 957, 462);
        Detail mobile = new Detail("blue_mobile", 1338, 467);
        Detail music = new Detail("green_music", 1519, 842);
        Detail kid = new Detail("red_kid", 1431, 1254);
        Detail playground = new Detail("pink_playground", 1134, 1503);
        Detail train = new Detail("yellow_train", 864, 1590);
        Detail puppetry = new Detail("purple_puppetry", 688, 1217);
        Detail tinker = new Detail("brown_tinker", 644, 716);

        center.addTo(root);
        exclamation.addTo(root);
        mobile.addTo(root);
        music.addTo(root);
        kid.addTo(root);
        playground.addTo(root);
        train.addTo(root);
        puppetry.addTo(root);
        tinker.addTo(root);

        bindImageSizeToScene(root, scene, clock);

        primaryStage.show();

        final Spinner spinner = new Spinner(exclamation, mobile, music, kid, playground, train, puppetry, tinker);

        final Random random = new Random();

        center.getOverlay().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (!spinner.isSpinning()) {
                    spinner.start();

                    int spinningTime = 5 * 1000 + random.nextInt(3000); // 5 seconds spinning + an arbitrary extra up to 3 seconds

                    // A Timer could also have worked, but caused application to not shutdown... So used this instead
                    TimelineBuilder.create().keyFrames(new KeyFrame(Duration.millis(spinningTime), new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            spinner.stop();
                        }
                    })).cycleCount(1).build().playFromStart();
                }

            }
        });

    }

    private void bindImageSizeToScene(AnchorPane root, Scene scene, Image clock) {
        DoubleBinding heightScale = scene.heightProperty().divide(clock.getHeight());
        DoubleBinding widthScale = scene.widthProperty().divide(clock.getWidth());

        Scale scale = new Scale();
        scale.yProperty().bind(heightScale);
        scale.xProperty().bind(widthScale);
        root.getTransforms().add(scale);
    }

}
