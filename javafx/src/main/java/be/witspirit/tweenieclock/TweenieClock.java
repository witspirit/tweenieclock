package be.witspirit.tweenieclock;

import javafx.animation.KeyFrame;
import javafx.animation.TimelineBuilder;
import javafx.application.Application;
import javafx.beans.binding.DoubleBinding;
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

import java.util.Random;

public class TweenieClock extends Application {

    private Detail center;
    private Spinner spinner;

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


        center = addTo(root, new Detail("purple_center", 1089, 919));

        Detail tv = addTo(root, new Detail("yellow_tv", 957, 462));
        Detail puppetry = addTo(root, new Detail("blue_puppetry", 1338, 467));
        Detail music = addTo(root, new Detail("green_music", 1519, 842));
        Detail wii = addTo(root, new Detail("red_wii", 1431, 1254));
        Detail playground = addTo(root, new Detail("pink_playground", 1134, 1503));
        Detail train = addTo(root, new Detail("yellow_train", 864, 1590));
        Detail show = addTo(root, new Detail("purple_show", 688, 1217));
        Detail tinker = addTo(root, new Detail("brown_tinker", 644, 716));

        bindImageSizeToScene(root, scene, clock);

        primaryStage.show();

        spinner = new Spinner(tv, puppetry, music, wii, playground, train, show, tinker);

        final Random random = new Random();

        reset();

        center.getNode().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (!spinner.isSpinning()) {
                    center.setHighlight(false).setPulse(false);
                    spinner.start();

                    int spinningTime = 4 * 1000 + random.nextInt(4500); // 4 seconds spinning + an arbitrary extra up to 4 seconds

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

        root.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (!spinner.isSpinning()) {
                    reset();
                }
            }
        });

    }

    private void reset() {
        spinner.reset();
        center.setHighlight(true).setPulse(true);
    }

    private Detail addTo(AnchorPane pane, Detail detail) {
        AnchorPane.setLeftAnchor(detail.getNode(), detail.getTopLeftAnchor().getX());
        AnchorPane.setTopAnchor(detail.getNode(), detail.getTopLeftAnchor().getY());
        pane.getChildren().add(detail.getNode());


        return detail;
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
