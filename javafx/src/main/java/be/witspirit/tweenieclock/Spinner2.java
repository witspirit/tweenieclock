package be.witspirit.tweenieclock;


import javafx.animation.*;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;

public class Spinner2 {
    private Detail[] details;
    private boolean spinning = false;
    private Random random = new Random();

    private Timeline timeline;
    private int currentIndex = 0;
    private DelegatedDoubleValue currentOpacity;


    public Spinner2(Detail... details) {
        this.details = details;
        currentOpacity = new DelegatedDoubleValue(details[currentIndex].opacityProperty());

        setupTimeline();
    }

    public boolean isSpinning() {
        return spinning;
    }

    public void reset() {
        for (Detail detail : details) {
            detail.setHighlight(false);
            detail.setPulse(false);
        }
    }

    public void start() {
        if (!spinning) {
            reset();
            spinning = true;

            currentIndex = random.nextInt(details.length);
            currentOpacity.setDelegate(details[currentIndex].opacityProperty());

            timeline.playFromStart();
        }
    }

    public void stop() {
        spinning = false;
        timeline.stop();
        details[currentIndex].setHighlight(true);
        details[currentIndex].setPulse(true);
    }

    private void setupTimeline() {
        timeline = TimelineBuilder.create().cycleCount(Timeline.INDEFINITE).autoReverse(false).keyFrames(
            new KeyFrame(Duration.ZERO, new KeyValue(currentOpacity, 0.0)),
            new KeyFrame(Duration.seconds(0.1), new KeyValue(currentOpacity, 0.9, Interpolator.EASE_BOTH)),
            new KeyFrame(Duration.seconds(0.2), new KeyValue(currentOpacity, 0.9)),
            new KeyFrame(Duration.seconds(0.3), new SetNextHandler(), new KeyValue(currentOpacity, 0.0, Interpolator.EASE_BOTH))
        ).build();
    }

    private class SetNextHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent actionEvent) {
            if (spinning) {
                currentIndex++;
                if (currentIndex >= details.length) {
                    currentIndex = 0;
                }
                currentOpacity.setDelegate(details[currentIndex].opacityProperty());
            }
        }
    }



}
