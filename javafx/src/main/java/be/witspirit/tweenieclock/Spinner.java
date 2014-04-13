package be.witspirit.tweenieclock;


import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

import java.util.Random;

public class Spinner {
    private static final double TRANSITION_TIME = 0.08; // Seconds
    private static final double STABLE_TIME = 0.04; // Seconds


    private Detail[] details;
    private boolean spinning = false;
    private Random random = new Random();

    private Timeline timeline;
    private int currentIndex = 0;
    private DelegatedDoubleValue currentOpacity;
    private DelegatedDoubleValue currentScale;


    public Spinner(Detail... details) {
        this.details = details;
        currentOpacity = new DelegatedDoubleValue(details[currentIndex].opacityProperty());
        currentScale = new DelegatedDoubleValue(details[currentIndex].scaleProperty());

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
            updateDelegates();

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
            new KeyFrame(Duration.seconds(TRANSITION_TIME), new KeyValue(currentOpacity, 0.9, Interpolator.EASE_BOTH), new KeyValue(currentScale, 1.05, Interpolator.EASE_BOTH)),
            new KeyFrame(Duration.seconds(TRANSITION_TIME+STABLE_TIME), new KeyValue(currentOpacity, 0.9), new KeyValue(currentScale, 1.05)),
            new KeyFrame(Duration.seconds(TRANSITION_TIME*2 + STABLE_TIME), new SetNextHandler(), new KeyValue(currentOpacity, 0.0, Interpolator.EASE_BOTH), new KeyValue(currentScale, 1.0, Interpolator.EASE_BOTH))
        ).build();
    }

    private class SetNextHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent actionEvent) {
            if (spinning) {
                incrementIndex();
                updateDelegates();
            }
        }
    }

    private void incrementIndex() {
        currentIndex++;
        if (currentIndex >= details.length) {
            currentIndex = 0;
        }
    }

    private void updateDelegates() {
        currentOpacity.setDelegate(details[currentIndex].opacityProperty());
        currentScale.setDelegate(details[currentIndex].scaleProperty());
    }


}
