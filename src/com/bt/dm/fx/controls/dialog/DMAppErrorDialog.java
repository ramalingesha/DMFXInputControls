package com.bt.dm.fx.controls.dialog;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;

import com.bt.dm.core.i18n.DMMessageLocalizer;
import com.bt.dm.core.pubsub.PubSubEvent;
import com.bt.dm.core.pubsub.PubSubEventDataModel;
import com.bt.dm.core.pubsub.PubSubEventHandler;
import com.bt.dm.fx.controls.labels.FXLabelCmp;
import com.bt.dm.fx.controls.labels.FXLabelCmp.FXLabelCmpBuilder;
import com.bt.dm.fx.controls.labels.FXMaterialDesignIcon;
import com.bt.dm.fx.controls.labels.FXMaterialDesignIcon.FXMaterialDesignIconBuilder;
import com.bt.dm.fx.controls.text.FXTextCmp;
import com.bt.dm.fx.controls.text.FXTextCmp.FXTextCmpBuilder;
import com.bt.dm.fx.controls.theme.ControlsTheme;
import com.bt.dm.fx.controls.utils.Fonts;
import com.bt.dm.fx.controls.utils.SizeHelper;
import com.bt.dm.fx.controls.utils.UIHelper;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.StageStyle;

/**
 * @author Ramalingesha ML
 *
 *         Created on Feb 05, 2023 12:31:00 PM
 */
public class DMAppErrorDialog {
	private FXTextCmp errorMessageTextCmp;
	private FXTextCmp errorDetailsTextCmp;
	private Dialog<ButtonType> dialog;

	public DMAppErrorDialog() {
		this.createDialogWindow();
	}

	public Optional<ButtonType> showError(Throwable throwable) {
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		throwable.printStackTrace(printWriter);

		return this.showErrorDialog(stringWriter.toString());
	}

	public Optional<ButtonType> showError(Exception exception) {
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		exception.printStackTrace(printWriter);

		return this.showErrorDialog(stringWriter.toString());
	}

	private void createDialogWindow() {
		dialog = new Dialog<>();

		errorMessageTextCmp = new FXTextCmp(new FXTextCmpBuilder().classNames("kannada-error-red-font")
				.wrappingWidth(SizeHelper.ERROR_DIALOG_WIDTH));
		errorDetailsTextCmp = new FXTextCmp(
				new FXTextCmpBuilder().englishFont(true).wrappingWidth(SizeHelper.ERROR_DIALOG_WIDTH));

		ScrollPane scrollPane = new ScrollPane(errorDetailsTextCmp);
		scrollPane.setPrefWidth(SizeHelper.ERROR_DIALOG_WIDTH + 100);
		scrollPane.setPrefHeight(SizeHelper.ERROR_DIALOG_HEIGHT);
		scrollPane.setFitToHeight(true);

		VBox contentPanel = new VBox(10);
		contentPanel.setPadding(new Insets(10));
		contentPanel.getChildren().addAll(errorMessageTextCmp, scrollPane);

		VBox container = new VBox(10);
		container.setPadding(new Insets(0));
		container.getChildren().addAll(this.getTitleBar(), contentPanel);

		DialogPane dialogPane = dialog.getDialogPane();
		dialogPane.getStyleClass().add("dm-message-box");

		dialogPane.setContent(container);
		dialogPane.getButtonTypes().add(UIHelper.ALERT_OK_BUTTON);

		this.dialog.initStyle(StageStyle.UNDECORATED);

		this.addRootThemeStyleSheet(dialogPane);
		this.subscribeOnThemeChange();
	}

	private Optional<ButtonType> showErrorDialog(String errorDetails) {
		this.errorMessageTextCmp.setText(DMMessageLocalizer.getLabel("app.codeError.description"));
		this.errorDetailsTextCmp.setText(errorDetails);

		return this.dialog.showAndWait();
	}

	private Pane getTitleBar() {
		FXMaterialDesignIconBuilder closeIconBuilder = new FXMaterialDesignIconBuilder(MaterialDesignIcon.CLOSE)
				.iconClickEvent(event -> {
					this.dialog.hide();
				});

		FXMaterialDesignIcon closeIcon = new FXMaterialDesignIcon(closeIconBuilder);
		closeIcon.getStyleClass().add("close-icon");

		FXLabelCmp titleCmp = new FXLabelCmp(
				new FXLabelCmpBuilder().label("app.error").font(Fonts.getInstance().getDefaultHeaderFont()));

		Region spaceRegion = new Region();
		HBox.setHgrow(spaceRegion, Priority.ALWAYS);

		HBox titleBox = new HBox(titleCmp, spaceRegion, closeIcon);
		titleBox.getStyleClass().add("title-bar");
		titleBox.setPadding(new Insets(10));

		return titleBox;
	}

	private void subscribeOnThemeChange() {
		PubSubEventHandler handler = new PubSubEventHandler() {

			@Override
			public void onPubSubData(PubSubEventDataModel data) {
				addRootThemeStyleSheet(dialog.getDialogPane());
			}
		};
		PubSubEvent.getInstance().subscribe("THEME", handler);
	}

	private void addRootThemeStyleSheet(Pane root) {
		root.getStylesheets().clear();
		root.getStylesheets().add(getClass().getResource("MessageBox.css").toExternalForm());
		root.getStylesheets()
				.add(getClass().getResource(ControlsTheme.getThemeCssFileName("MessageBox")).toExternalForm());
		root.getStylesheets()
				.add(getClass().getResource(DMMessageLocalizer.getAppLocaleCssFileName("MessageBox")).toExternalForm());
	}
}
