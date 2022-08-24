/**
 * 
 */
package com.bt.dm.fx.controls.alert;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import com.bt.dm.fx.controls.labels.DMHyperLinkCmp;
import com.bt.dm.fx.controls.labels.DMHyperLinkCmp.DMHyperLinkCmpBuilder;
import com.bt.dm.fx.controls.labels.FXLabelCmp;
import com.bt.dm.fx.controls.labels.FXLabelCmp.FXLabelCmpBuilder;
import com.bt.dm.fx.controls.utils.DMAlertIconConstants;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;

/**
 * @author Ramalingesha ML
 *
 *         Created on Jul 7, 2022 12:17:59 PM
 */
public class DMAlertMessage extends Pane {
	public static class DMAlertMessageBuilder {
		private AlertType alertType = AlertType.INFORMATION;
		private String buttonText;
		private String message;
		private boolean buttonTextI18N = true;
		private boolean messageI18N = true;
		private boolean messageEnglishFont;
		private boolean buttonTextEnglishFont;
		private double width = 300;
		private double height = 40;
		private EventHandler<ActionEvent> buttonClickHandler;

		public DMAlertMessageBuilder(String message) {
			this.message = message;
		}

		public DMAlertMessageBuilder alertType(AlertType alertType) {
			this.alertType = alertType;
			return this;
		}

		public DMAlertMessageBuilder buttonText(String buttonText) {
			this.buttonText = buttonText;
			return this;
		}

		public DMAlertMessageBuilder buttonTextI18N(boolean buttonTextI18N) {
			this.buttonTextI18N = buttonTextI18N;
			return this;
		}

		public DMAlertMessageBuilder messageI18N(boolean messageI18N) {
			this.messageI18N = messageI18N;
			return this;
		}

		public DMAlertMessageBuilder messageEnglishFont(
				boolean messageEnglishFont) {
			this.messageEnglishFont = messageEnglishFont;
			return this;
		}

		public DMAlertMessageBuilder buttonTextEnglishFont(
				boolean buttonTextEnglishFont) {
			this.buttonTextEnglishFont = buttonTextEnglishFont;
			return this;
		}

		public DMAlertMessageBuilder buttonClickEvent(
				EventHandler<ActionEvent> buttonClickHandler) {
			this.buttonClickHandler = buttonClickHandler;
			return this;
		}

		public DMAlertMessageBuilder width(double width) {
			this.width = width;
			return this;
		}

		public DMAlertMessageBuilder height(double height) {
			this.height = height;
			return this;
		}
	}

	private DMAlertMessageBuilder builder;
	private FXLabelCmp messageCmp;

	public DMAlertMessage(DMAlertMessageBuilder builder) {
		this.builder = builder;
		this.createAndShowAlert();
	}

	private void createAndShowAlert() {
		messageCmp = new FXLabelCmp(new FXLabelCmpBuilder()
				.label(builder.message)
				.i18n(builder.messageI18N)
				.classNames(
						new String[] { "heading2", "alert-message-heading2" }));
		if (builder.messageEnglishFont) {
			messageCmp.getStyleClass().add("english-font");
		}

		HBox box = new HBox(10);
		box.setAlignment(Pos.CENTER_LEFT);
		box.setPrefSize(builder.width, builder.height);

		box.getChildren().addAll(this.getAlertMessageIcon(builder.alertType),
				messageCmp);

		if (builder.buttonText != null) {
			DMHyperLinkCmpBuilder hyperLinkCmpBuilder = new DMHyperLinkCmpBuilder(
					builder.buttonText).i18n(builder.buttonTextI18N).className(
					"hyperlink-button-heading2");

			if (builder.buttonClickHandler != null) {
				hyperLinkCmpBuilder.eventHandler(builder.buttonClickHandler);
			}

			if (builder.buttonTextEnglishFont) {
				// hyperLinkCmpBuilder.className("english-font");
			}

			DMHyperLinkCmp buttonLinkCmp = new DMHyperLinkCmp(
					hyperLinkCmpBuilder);

			box.getChildren().add(buttonLinkCmp);
		}

		this.getChildren().add(box);
	}

	private MaterialDesignIconView getAlertMessageIcon(AlertType alertType) {
		MaterialDesignIcon alertIcon;
		String alertIconColor;

		switch (alertType) {
		case ERROR:
			alertIcon = MaterialDesignIcon.CLOSE_BOX;
			alertIconColor = DMAlertIconConstants.ERROR_ICON_COLOR;
			break;
		case WARNING:
			alertIcon = MaterialDesignIcon.ALERT;
			alertIconColor = DMAlertIconConstants.WARNING_ICON_COLOR;
			break;

		default:
			alertIcon = MaterialDesignIcon.INFORMATION;
			alertIconColor = DMAlertIconConstants.INFO_ICON_COLOR;
			break;
		}

		MaterialDesignIconView alertIconView = new MaterialDesignIconView(
				alertIcon);
		alertIconView.setStyle(String.format("-fx-font-size: %s;-fx-fill: %s;",
				DMAlertIconConstants.ALERT_POPUP_ICON_SIZE, alertIconColor));

		return alertIconView;
	}
	
	public void changeAlertMessage(String message) {
		this.messageCmp.setText(message);
	}
	
	public void showAlert() {
		this.setVisible(true);
	}
	
	public void hideAlert() {
		this.setVisible(false);
	}
}
