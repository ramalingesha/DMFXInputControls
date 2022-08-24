/**
 * 
 */
package com.bt.dm.fx.controls.labels;

import com.bt.dm.core.i18n.DMMessageLocalizer;
import com.bt.dm.core.pubsub.PubSubEvent;
import com.bt.dm.core.pubsub.PubSubEventDataModel;
import com.bt.dm.core.pubsub.PubSubEventHandler;
import com.bt.dm.fx.controls.theme.ControlsTheme;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.Hyperlink;

/**
 * @author Ramalingesha ML
 *
 *         Created on Jun 26, 2022 9:48:57 AM
 */
public class DMHyperLinkCmp extends Hyperlink {
	public static class DMHyperLinkCmpBuilder {
		private String linkText;
		private boolean i18n = true;
		private String className;
		private EventHandler<ActionEvent> eventHandler;

		public DMHyperLinkCmpBuilder(String linkText) {
			this.linkText = linkText;
		}

		public DMHyperLinkCmpBuilder i18n(boolean i18n) {
			this.i18n = i18n;
			return this;
		}

		public DMHyperLinkCmpBuilder className(String className) {
			this.className = className;
			return this;
		}

		public DMHyperLinkCmpBuilder eventHandler(
				EventHandler<ActionEvent> eventHandler) {
			this.eventHandler = eventHandler;
			return this;
		}
	}

	private DMHyperLinkCmpBuilder builder;

	public DMHyperLinkCmp(DMHyperLinkCmpBuilder builder) {
		this.builder = builder;
		this.createComponent();
	}

	private void createComponent() {
		this.setText(this.builder.i18n ? DMMessageLocalizer
				.getLabel(this.builder.linkText) : this.builder.linkText);

		if (this.builder.eventHandler != null) {
			this.setOnAction(this.builder.eventHandler);
		}

		if (this.builder.className != null) {
			this.getStyleClass().addAll(this.builder.className.split(" "));
		}

		this.setCursor(Cursor.HAND);
		this.addRootThemeStyleSheet();
		this.subscribeOnThemeChange();
	}

	public void setLinkText(String linkText) {
		this.setText(linkText);
	}

	private void subscribeOnThemeChange() {
		PubSubEventHandler handler = new PubSubEventHandler() {

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
