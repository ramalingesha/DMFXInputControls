/**
 * 
 */
package com.bt.dm.fx.controls.dialog;

import java.util.Optional;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.text.TextAlignment;
import javafx.stage.StageStyle;

import com.bt.dm.core.i18n.DMMessageLocalizer;
import com.bt.dm.core.pubsub.PubSubEvent;
import com.bt.dm.core.pubsub.PubSubEventDataModel;
import com.bt.dm.core.pubsub.PubSubEventHandler;
import com.bt.dm.fx.controls.events.IconClickEvent;
import com.bt.dm.fx.controls.labels.FXLabelCmp;
import com.bt.dm.fx.controls.labels.FXLabelCmp.FXLabelCmpBuilder;
import com.bt.dm.fx.controls.labels.FXMaterialDesignIcon;
import com.bt.dm.fx.controls.labels.FXMaterialDesignIcon.FXMaterialDesignIconBuilder;
import com.bt.dm.fx.controls.theme.ControlsTheme;
import com.bt.dm.fx.controls.utils.DMAlertIconConstants;
import com.bt.dm.fx.controls.utils.Fonts;
import com.bt.dm.fx.controls.utils.SizeHelper;
import com.bt.dm.fx.controls.utils.UIHelper;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;

/**
 * @author Ramalingesha ML
 *
 *         Created on Oct 19, 2020 11:25:37 AM
 */
public class FXMessageBox {
	public static class FXMessageBoxBuilder {
		private AlertType alertType = AlertType.INFORMATION;
		private String title;
		private String message;
		private boolean titleI18N = true;
		private boolean messageI18N = true;
		private boolean messageEnglishFont;
		private Double messagePanelHeight;

		public FXMessageBoxBuilder(String message) {
			this.message = message;
		}

		public FXMessageBoxBuilder(String message, boolean i18n) {
			this.message = message;
			this.messageI18N = i18n;
		}

		public FXMessageBoxBuilder alertType(AlertType alertType) {
			this.alertType = alertType;
			return this;
		}

		public FXMessageBoxBuilder title(String title) {
			this.title = title;
			return this;
		}
		
		public FXMessageBoxBuilder messagePanelHeight(Double messagePanelHeight) {
			this.messagePanelHeight = messagePanelHeight;
			return this;
		}

		public FXMessageBoxBuilder title(String title, boolean i18n) {
			this.title = title;
			this.titleI18N = i18n;
			return this;
		}

		public FXMessageBoxBuilder messageEnglishFont(boolean messageEnglishFont) {
			this.messageEnglishFont = messageEnglishFont;
			return this;
		}
	}

	private FXMessageDialog dialog;
	private static FXMessageBox instance;

	private FXMessageBox() {
		this.dialog = FXMessageDialog.getInstance();
	}

	public static FXMessageBox getInstance() {
		if (instance == null) {
			instance = new FXMessageBox();
		}

		return instance;
	}

	public Optional<ButtonType> show(FXMessageBoxBuilder builder) {
		this.dialog.setMessage(builder.message, builder.messageI18N);
		this.dialog.setMessageBoxTitle(builder.title, builder.titleI18N);
		this.dialog.changeAlertIcon(builder.alertType);
		this.dialog.setEnglishFont(builder.messageEnglishFont);
		this.dialog.setMessagePanelHeight(builder.messagePanelHeight);

		return FXMessageDialog.getInstance().showAndWait();
	}

	public void hide() {
		FXMessageDialog.getInstance().hide();
	}
}

class FXMessageDialog extends Dialog<ButtonType> {
	private static FXMessageDialog instance;
	private StringProperty message = new SimpleStringProperty();
	private StringProperty messageBoxTitle = new SimpleStringProperty();
	private FXMaterialDesignIcon alertIcon;
	private PubSubEventHandler handler;
	private boolean messageEnglishFont;
	private FXLabelCmp messageCmp;

	public void setMessage(String message, boolean i18n) {
		this.message.setValue(i18n ? DMMessageLocalizer.getLabel(message)
				: message);
	}
	
	public void setMessagePanelHeight(Double height) {
		if (height != null) {
			this.messageCmp.setPrefHeight(height);
		} else {
			this.messageCmp.setPrefHeight(40);
		}
	}

	public void setMessageBoxTitle(String messageBoxTitle, boolean i18n) {
		this.messageBoxTitle.setValue(i18n ? DMMessageLocalizer
				.getLabel(messageBoxTitle) : messageBoxTitle);
	}

	public void setEnglishFont(boolean messageEnglishFont) {
		this.messageEnglishFont = messageEnglishFont;
	}

	private FXMessageDialog() {
		super();
	}

	public static FXMessageDialog getInstance() {
		if (instance == null) {
			instance = new FXMessageDialog();
			instance.setAlertIcon(AlertType.INFORMATION);
			instance.initComponent();
		}

		instance.toggleEnglishFont();

		return instance;
	}

	private void toggleEnglishFont() {
		if (this.messageEnglishFont) {
			messageCmp.getStyleClass().add("english-font");
		} else {
			messageCmp.getStyleClass().remove("english-font");
		}
	}

	private void initComponent() {
		DialogPane dialogPane = this.getDialogPane();
		dialogPane.getStyleClass().add("dm-message-box");
		this.addRootThemeStyleSheet(dialogPane);

		// remove dialog title bar
		this.initStyle(StageStyle.UNDECORATED);
		dialogPane.setMinWidth(SizeHelper.SCREEN_SIZE.getWidth() * 0.3);
		dialogPane.setMaxWidth(SizeHelper.SCREEN_SIZE.getWidth() * 0.4);

		FXMaterialDesignIconBuilder closeIconBuilder = new FXMaterialDesignIconBuilder(
				MaterialDesignIcon.CLOSE);
		closeIconBuilder.iconClickEvent(new IconClickEvent() {

			@Override
			public void onClick(Event event) {
				hide();
			}
		});

		FXMaterialDesignIcon closeIcon = new FXMaterialDesignIcon(
				closeIconBuilder);
		closeIcon.getStyleClass().add("close-icon");

		FXLabelCmp titleCmp = new FXLabelCmp(new FXLabelCmpBuilder().font(Fonts
				.getInstance().getDefaultHeaderFont()));
		titleCmp.textProperty().bind(this.messageBoxTitle);
		Region spaceRegion = new Region();
		HBox.setHgrow(spaceRegion, Priority.ALWAYS);

		HBox titleBox = new HBox(titleCmp, spaceRegion, closeIcon);
		titleBox.getStyleClass().add("title-bar");
		titleBox.setPadding(new Insets(10));

		messageCmp = new FXLabelCmp(new FXLabelCmpBuilder());
		messageCmp.textProperty().bind(this.message);
		messageCmp.setWrapText(true);
		messageCmp.setTextAlignment(TextAlignment.LEFT);
		messageCmp.setPadding(new Insets(10, 10, 0, 15));

		BorderPane messagePanel = new BorderPane();
		messagePanel.getStyleClass().add("message-box");
		messagePanel.setPadding(new Insets(10));

		messagePanel.setLeft(this.alertIcon);
		messagePanel.setCenter(messageCmp);

		BorderPane root = new BorderPane();
		root.setPadding(new Insets(0));
		root.setTop(titleBox);
		root.setCenter(messagePanel);

		dialogPane.setContent(root);

		dialogPane.getButtonTypes().add(UIHelper.ALERT_OK_BUTTON);

		this.subscribeOnThemeChange();
	}

	private void subscribeOnThemeChange() {
		handler = new PubSubEventHandler() {

			@Override
			public void onPubSubData(PubSubEventDataModel data) {
				addRootThemeStyleSheet(getDialogPane());
			}
		};
		PubSubEvent.getInstance().subscribe("THEME", handler);
	}

	private void addRootThemeStyleSheet(Pane root) {
		root.getStylesheets().clear();
		root.getStylesheets().add(
				getClass().getResource("MessageBox.css").toExternalForm());
		root.getStylesheets().add(
				getClass().getResource(
						ControlsTheme.getThemeCssFileName("MessageBox"))
						.toExternalForm());
		root.getStylesheets().add(
				getClass().getResource(
						DMMessageLocalizer
								.getAppLocaleCssFileName("MessageBox"))
						.toExternalForm());
	}

	class AlertIconDetails {
		String iconStyle;
		MaterialDesignIcon icon;

		public AlertIconDetails(String iconStyle, MaterialDesignIcon icon) {
			this.iconStyle = iconStyle;
			this.icon = icon;
		}
	}

	private AlertIconDetails getIconStyles(AlertType alertType) {
		MaterialDesignIcon icon;
		String iconStyle = String.format("-fx-font-size: %s;",
				DMAlertIconConstants.ALERT_POPUP_ICON_SIZE);
		switch (alertType) {
		case ERROR:
			icon = MaterialDesignIcon.CLOSE_BOX;
			iconStyle += String.format("-fx-fill: %s;",
					DMAlertIconConstants.ERROR_ICON_COLOR);
			break;
		case WARNING:
			icon = MaterialDesignIcon.ALERT;
			iconStyle += String.format("-fx-fill: %s;",
					DMAlertIconConstants.WARNING_ICON_COLOR);
			break;
		case CONFIRMATION:
			icon = MaterialDesignIcon.HELP_CIRCLE;
			iconStyle += String.format("-fx-fill: %s;",
					DMAlertIconConstants.CONFIRM_ICON_COLOR);
			break;

		default:
			icon = MaterialDesignIcon.INFORMATION;
			iconStyle += String.format("-fx-fill: %s;",
					DMAlertIconConstants.INFO_ICON_COLOR);
			break;
		}

		return new AlertIconDetails(iconStyle, icon);
	}

	void changeAlertIcon(AlertType alertType) {
		AlertIconDetails alertIconDetails = getIconStyles(alertType);

		this.alertIcon.changeIcon(alertIconDetails.icon);
		this.alertIcon.setStyle(alertIconDetails.iconStyle);

		// Remove cancel button if exists
		this.getDialogPane().getButtonTypes()
				.removeAll(UIHelper.ALERT_CANCEL_BUTTON);
		if (alertType == AlertType.CONFIRMATION) {
			// Add cancel button for confirmation dialog
			this.getDialogPane().getButtonTypes()
					.add(UIHelper.ALERT_CANCEL_BUTTON);
		}
	}

	private void setAlertIcon(AlertType alertType) {
		AlertIconDetails alertIconDetails = getIconStyles(alertType);

		FXMaterialDesignIconBuilder alertIconBuilder = new FXMaterialDesignIconBuilder(
				alertIconDetails.icon);

		this.alertIcon = new FXMaterialDesignIcon(alertIconBuilder);
		this.alertIcon.setStyle(alertIconDetails.iconStyle);
	}
}
