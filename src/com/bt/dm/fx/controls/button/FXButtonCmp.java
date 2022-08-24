/**
 * 
 */
package com.bt.dm.fx.controls.button;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.Button;

import com.bt.dm.core.i18n.DMMessageLocalizer;
import com.bt.dm.core.pubsub.PubSubEvent;
import com.bt.dm.core.pubsub.PubSubEventDataModel;
import com.bt.dm.core.pubsub.PubSubEventHandler;
import com.bt.dm.fx.controls.events.DMFocusListener;
import com.bt.dm.fx.controls.labels.FXFontAwesomeIcon;
import com.bt.dm.fx.controls.labels.FXMaterialDesignIcon;
import com.bt.dm.fx.controls.theme.ControlsTheme;

/**
 * @author Ramalingesha ML
 *
 *         Created on Oct 8, 2020 1:53:02 PM
 */
public class FXButtonCmp extends Button {
	public static class FXButtonCmpBuilder {
		private String text;
		private FXFontAwesomeIcon faIcon;
		private FXMaterialDesignIcon materialIcon;
		private EventHandler<ActionEvent> eventHandler;
		private String className;
		private boolean i18n = true;
		private DMFocusListener focusListener;

		public FXButtonCmpBuilder text(String text) {
			this.text = text;
			return this;
		}

		public FXButtonCmpBuilder i18n(boolean i18n) {
			this.i18n = i18n;
			return this;
		}

		public FXButtonCmpBuilder faIcon(FXFontAwesomeIcon faIcon) {
			this.faIcon = faIcon;
			return this;
		}

		public FXButtonCmpBuilder materialIcon(FXMaterialDesignIcon materialIcon) {
			this.materialIcon = materialIcon;
			return this;
		}

		public FXButtonCmpBuilder eventHandler(
				EventHandler<ActionEvent> eventHandler) {
			this.eventHandler = eventHandler;
			return this;
		}

		public FXButtonCmpBuilder className(String className) {
			this.className = className;
			return this;
		}

		public FXButtonCmpBuilder focusListener(DMFocusListener focusListener) {
			this.focusListener = focusListener;
			return this;
		}
	}

	private FXButtonCmpBuilder builder;
	private PubSubEventHandler handler;

	public FXButtonCmp(FXButtonCmpBuilder builder) {
		this.builder = builder;
		this.createComponent();
	}

	private void createComponent() {
		this.setText(this.builder.i18n ? DMMessageLocalizer
				.getLabel(this.builder.text) : this.builder.text);

		if (this.builder.materialIcon != null) {
			this.setGraphic(this.builder.materialIcon);
		} else if (this.builder.faIcon != null) {
			this.setGraphic(this.builder.faIcon);
		}

		if (this.builder.eventHandler != null) {
			this.setOnAction(this.builder.eventHandler);
		}

		this.getStyleClass().add("button");

		if (this.builder.className != null) {
			this.getStyleClass().addAll(this.builder.className.split(" "));
		}

		this.setCursor(Cursor.HAND);
		this.addRootThemeStyleSheet();
		this.subscribeOnThemeChange();

		if (builder.focusListener != null) {
			this.focusedProperty().addListener(
					(Observable, oldValue, newValue) -> {
						if (newValue) {
							builder.focusListener.onFocus();
						} else {
							builder.focusListener.onBlur();
						}
					});
		}
	}

	public void setEnable(boolean enable) {
		this.setDisable(!enable);
	}

	private void subscribeOnThemeChange() {
		handler = new PubSubEventHandler() {

			@Override
			public void onPubSubData(PubSubEventDataModel data) {
				addRootThemeStyleSheet();
			}
		};
		PubSubEvent.getInstance().subscribe("THEME", handler);
	}

	private void addRootThemeStyleSheet() {
		this.getStylesheets().clear();
		this.getStylesheets().add(
				getClass().getClassLoader()
						.getResource("com/bt/dm/fx/controls/DMControl.css")
						.toExternalForm());
		this.getStylesheets()
				.add(getClass()
						.getClassLoader()
						.getResource(
								"com/bt/dm/fx/controls/"
										+ ControlsTheme
												.getThemeCssFileName("DMControl"))
						.toExternalForm());
	}
}
