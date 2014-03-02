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

        ImageView center = createDetail("purple_center", 1089, 919);
        ImageView exclamation = createDetail("yellow_exclamation", 957, 462);
        ImageView mobile = createDetail("blue_mobile", 1338, 467);
        ImageView music = createDetail("green_music", 1519, 842);
        ImageView kid = createDetail("red_kid", 1431, 1254);
        ImageView playground = createDetail("pink_playground", 1134, 1503);
        ImageView train = createDetail("yellow_train", 864, 1590);
        ImageView puppetry = createDetail("purple_puppetry", 688, 1217);
        ImageView tinker = createDetail("brown_tinker", 644, 716);

        Ellipse overlay = new Ellipse();
        overlay.setRadiusX(center.getImage().getWidth()/2);
        overlay.setRadiusY(center.getImage().getHeight()/2);
        overlay.setBlendMode(BlendMode.OVERLAY);
        AnchorPane.setLeftAnchor(overlay, 1089.0);
        AnchorPane.setTopAnchor(overlay, 919.0);

//        AnchorPane.setLeftAnchor(overlay, 300.0);
//        AnchorPane.setTopAnchor(overlay, 300.0);

        RadialGradient gradient = RadialGradientBuilder.create()
                .stops(new Stop(0.0, Color.WHITE),
                       new Stop(1.0, Color.BLACK))
                .radius(0.6)
                .focusDistance(0.0)
                .centerX(0.5)
                .centerY(0.5)
                .build();
        overlay.setFill(gradient);

        BoxBlur bb = new BoxBlur();
        bb.setWidth(10);
        bb.setHeight(10);
        bb.setIterations(3);
        overlay.setEffect(bb);

        Timeline timeline = TimelineBuilder.create().cycleCount(Timeline.INDEFINITE).autoReverse(true)
                .keyFrames(new KeyFrame(Duration.millis(500), new KeyValue(overlay.opacityProperty(), 0.0)),
                           new KeyFrame(Duration.millis(500), new KeyValue(overlay.opacityProperty(), 0.8)))
                .build();
        timeline.play();


        root.getChildren().add(center);
        root.getChildren().add(overlay);
        root.getChildren().add(exclamation);
        root.getChildren().add(mobile);
        root.getChildren().add(music);
        root.getChildren().add(kid);
        root.getChildren().add(playground);
        root.getChildren().add(train);
        root.getChildren().add(puppetry);
        root.getChildren().add(tinker);

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

    private ImageView createDetail(final String baseName, int left, int top) {
        Image detail = new Image("detail_"+baseName+".png");
        ImageView detailView = new ImageView(detail);
        AnchorPane.setLeftAnchor(detailView, (double) left);
        AnchorPane.setTopAnchor(detailView, (double) top);
        detailView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println(baseName+" clicked");
            }
        });
        return detailView;
    }
}
