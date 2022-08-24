/**
 * 
 */
package com.bt.dm.fx.controls.labels;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import com.bt.dm.core.i18n.DMMessageLocalizer;
import com.bt.dm.core.pubsub.PubSubEvent;
import com.bt.dm.core.utils.DMDateUtils;
import com.bt.dm.core.utils.DMStringUtils;
import com.bt.dm.fx.controls.DMLabelBuilder;
import com.bt.dm.fx.controls.utils.ControlSize;

/**
 * @author Ramalingesha ML
 *
 *         Created on Dec 1, 2021 9:03:51 AM
 */
public class FXDateTimeLabelCmp extends Pane {
	public static class FXDateTimeLabelCmpBuilder extends DMLabelBuilder {
		private boolean showTime;
		private boolean verticalAlign;

		public FXDateTimeLabelCmpBuilder showTime(boolean showTime) {
			this.showTime = showTime;
			return this;
		}

		public FXDateTimeLabelCmpBuilder verticalAlign(boolean verticalAlign) {
			this.verticalAlign = verticalAlign;
			return this;
		}
	}

	private FXLabelCmp dayLabel;
	private FXLabelCmp dateLabel;
	private FXLabelCmp timeLabel;
	FXDateTimeLabelCmpBuilder builder;
	private Date date;
	private Timeline timeline;
	private Map<String, String> daysLocaleKeyMap;

	public FXDateTimeLabelCmp(FXDateTimeLabelCmpBuilder builder) {
		this.builder = builder;
		this.createComponent();
		this.createTimeline();
		this.updaeDaysLocaleKeyMap();
		this.addListeners();
	}

	private void createComponent() {
		this.date = new Date();

		String[] classeNames = new String[] { "english-font" };
		ControlSize size = new ControlSize(100, 20);

		dayLabel = new FXLabelCmp(new DMLabelBuilder().align(Pos.CENTER).size(
				size));
		dayLabel.setStyle("-fx-text-fill: #F4FF81; -fx-font-size: 19px;");

		dateLabel = new FXLabelCmp(new DMLabelBuilder().align(Pos.CENTER)
				.classNames(classeNames).size(size));
		timeLabel = new FXLabelCmp(new DMLabelBuilder().align(Pos.CENTER)
				.classNames(classeNames).size(size));

		Pane box;
		if (this.builder.verticalAlign) {
			box = new VBox(5);
			((VBox) box).setAlignment(Pos.CENTER);
		} else {
			box = new HBox(10);
			((HBox) box).setAlignment(Pos.CENTER);
		}

		box.getChildren().add(dayLabel);

		if (this.builder.showTime) {
			box.getChildren().add(timeLabel);
		}

		box.getChildren().add(dateLabel);

		this.getChildren().add(box);
	}

	private void createTimeline() {
		timeline = new Timeline(new KeyFrame(Duration.minutes(1), event -> {
			this.updateTime();
		}));

		timeline.setCycleCount(Animation.INDEFINITE);
	}

	private void startTimer() {
		this.updateTime();
		timeline.play();
	}

	private void updateTime() {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				date.setTime(System.currentTimeMillis());

				String dayName = DMDateUtils.formatDate(date, "EEEE")
						.toLowerCase();

				dayLabel.setText(DMStringUtils
						.getNutralValue(DMMessageLocalizer
								.getLabel(daysLocaleKeyMap.get(dayName))));
				dateLabel.setText(DMDateUtils.formatDate(date));
				timeLabel.setText(DMDateUtils.formatDate(date, "hh:mm aa"));
			}
		});
	}

	private void stopTimer() {
		this.timeline.stop();
	}

	private void addListeners() {
		PubSubEvent.getInstance().subscribe("startClock", (data) -> {
			this.startTimer();
		});

		PubSubEvent.getInstance().subscribe("stopClock", (data) -> {
			this.stopTimer();
		});
	}

	private void updaeDaysLocaleKeyMap() {
		this.daysLocaleKeyMap = new HashMap<String, String>();

		daysLocaleKeyMap.put("sunday", "dayName.sunday");
		daysLocaleKeyMap.put("monday", "dayName.monday");
		daysLocaleKeyMap.put("tuesday", "dayName.tuesday");
		daysLocaleKeyMap.put("wednesday", "dayName.wednesday");
		daysLocaleKeyMap.put("thursday", "dayName.thursday");
		daysLocaleKeyMap.put("friday", "dayName.friday");
		daysLocaleKeyMap.put("saturday", "dayName.saturday");
	}
}
