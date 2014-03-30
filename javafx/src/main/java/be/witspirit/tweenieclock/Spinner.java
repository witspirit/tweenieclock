package be.witspirit.tweenieclock;


import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TimelineBuilder;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Spinner {
    private Detail[] details;
    private List<Timeline> timelines;
    private boolean spinning = false;
    private Random random = new Random();

    public Spinner(Detail... details) {
        this.details = details;

        setupTimelines();
    }

    public boolean isSpinning() {
        return spinning;
    }

    public void reset() {
        for (Detail detail : details) {
            detail.setHighlight(false);
        }
    }

    public void start() {
        if (!spinning) {
            reset();
            spinning = true;
            timelines.get(random.nextInt(details.length)).playFromStart();
        }
    }

    public void stop() {
        spinning = false;
    }

    private void setupTimelines() {
        timelines = new ArrayList<>();
        for (int i=0; i < details.length; i++) {
            DoubleProperty opacity = details[i].opacityProperty();
            Timeline timeline = TimelineBuilder.create().cycleCount(2).autoReverse(true).keyFrames(
                    new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                    new KeyFrame(Duration.seconds(0.1), new KeyValue(opacity, 0.9)),
                    new KeyFrame(Duration.seconds(0.15), new KeyValue(opacity, 0.9))
            ).build();
            final int nextTimelineIndex = i == details.length-1 ? 0 : i+1;
            timeline.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    Timeline nextTimeline = timelines.get(nextTimelineIndex);
                    if (spinning) {
                        nextTimeline.playFromStart();
                    } else {
                        details[nextTimelineIndex].setHighlight(true);
                    }
                }
            });
            timelines.add(timeline);
        }
    }

}
