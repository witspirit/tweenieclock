package be.witspirit.tweenieclock;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TimelineBuilder;
import javafx.application.Application;
import javafx.beans.binding.DoubleBinding;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.RadialGradientBuilder;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.util.Duration;

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
