package be.witspirit.tweenieclock;

import javafx.event.EventHandler;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.RadialGradientBuilder;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Ellipse;

/**
 * Detail part of the image
 */
public class Detail {

    private ImageView detailView;
    private Ellipse overlay;

    public Detail(final String baseName, int left, int top) {
        Image detail = new Image("detail_"+baseName+".png");
        detailView = new ImageView(detail);
        AnchorPane.setLeftAnchor(detailView, (double) left); // FIXME Assume AnchorPane
        AnchorPane.setTopAnchor(detailView, (double) top);

        overlay = createOverlay(detailView);
        AnchorPane.setLeftAnchor(overlay, (double) left);
        AnchorPane.setTopAnchor(overlay, (double) top);

        overlay.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println(baseName+" clicked");

                if (overlay.getOpacity() > 0) {
                    overlay.setOpacity(0.0);
                } else {
                    overlay.setOpacity(0.8);
                }
            }
        });

    }

    public void addTo(Pane pane) {
        pane.getChildren().add(detailView);
        pane.getChildren().add(overlay);
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