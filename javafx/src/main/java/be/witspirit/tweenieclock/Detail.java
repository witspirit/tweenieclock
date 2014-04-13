package be.witspirit.tweenieclock;

import javafx.animation.*;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.RadialGradientBuilder;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Ellipse;
import javafx.util.Duration;

/**
 * Detail part of the image
 */
public class Detail {

    private ImageView detailView;
    private Ellipse overlay;
    private Point2D topLeftAnchor;
    private final Group detailNode;

    private Timeline pulseTimeline;
    private final DoubleProperty scaleProperty;

    public Detail(final String baseName, int left, int top) {
        Image detail = new Image("detail_"+baseName+".png");
        detailView = new ImageView(detail);
        overlay = createOverlay(detailView);

        // Not entirely sure why this is required...
        // But to get some alignment in the group between the overlay and the detailView
        // And onto the global image, this proved to be more or less ok
        detailView.setX(-10 - (detail.getWidth() / 2) );
        detailView.setY(-10 - (detail.getHeight() / 2));

        detailNode = new Group(detailView, overlay);

        topLeftAnchor = new Point2D((double) left, (double) top);

        scaleProperty = new SimpleDoubleProperty(1.0);
        detailNode.scaleXProperty().bind(scaleProperty);
        detailNode.scaleYProperty().bind(scaleProperty);

        pulseTimeline = TimelineBuilder.create().cycleCount(Timeline.INDEFINITE).autoReverse(true).keyFrames(
                new KeyFrame(Duration.ZERO, new KeyValue(scaleProperty, 1.0)),
                new KeyFrame(Duration.seconds(0.2), new KeyValue(scaleProperty, 1.1, Interpolator.EASE_BOTH))
        ).build();

        setHighlight(false);

    }

    public Node getNode() {
        return detailNode;
    }

    public Point2D getTopLeftAnchor() {
        return topLeftAnchor;
    }

    public Detail setHighlight(boolean enabled) {
        overlay.setOpacity(enabled ? 0.9 : 0.0);
        return this;
    }

    public Detail setPulse(boolean enabled) {
        pulseTimeline.stop();
        scaleProperty.setValue(1.0);
        if (enabled) {
            pulseTimeline.playFromStart();
        }
        return this;
    }

    public DoubleProperty opacityProperty() {
        return overlay.opacityProperty();
    }

    public DoubleProperty scaleProperty() {
        return scaleProperty;
    }

    private Ellipse createOverlay(ImageView detail) {
        Ellipse overlay = new Ellipse();
        overlay.setRadiusX(detail.getImage().getWidth() / 2);
        overlay.setRadiusY(detail.getImage().getHeight() / 2);
        overlay.setBlendMode(BlendMode.OVERLAY);

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
        return overlay;
    }
}
