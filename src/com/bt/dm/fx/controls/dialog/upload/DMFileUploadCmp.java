/**
 * 
 */
package com.bt.dm.fx.controls.dialog.upload;

import java.io.File;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import com.bt.dm.core.i18n.DMMessageLocalizer;
import com.bt.dm.core.pubsub.PubSubEvent;
import com.bt.dm.core.pubsub.PubSubEventDataModel;
import com.bt.dm.core.pubsub.PubSubEventHandler;
import com.bt.dm.fx.controls.DMLabelBuilder;
import com.bt.dm.fx.controls.button.FXButtonCmp;
import com.bt.dm.fx.controls.button.FXButtonCmp.FXButtonCmpBuilder;
import com.bt.dm.fx.controls.dialog.FXMessageBox;
import com.bt.dm.fx.controls.dialog.FXMessageBox.FXMessageBoxBuilder;
import com.bt.dm.fx.controls.events.DMFileUploadEvent;
import com.bt.dm.fx.controls.events.IconClickEvent;
import com.bt.dm.fx.controls.labels.FXFontAwesomeIcon;
import com.bt.dm.fx.controls.labels.FXFontAwesomeIcon.FXFontAwesomeIconBuilder;
import com.bt.dm.fx.controls.labels.FXLabelCmp;
import com.bt.dm.fx.controls.labels.FXMaterialDesignIcon;
import com.bt.dm.fx.controls.labels.FXMaterialDesignIcon.FXMaterialDesignIconBuilder;
import com.bt.dm.fx.controls.theme.ControlsTheme;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;

/**
 * @author Ramalingesha ML
 *
 *         Created on Jan 19, 2021 11:18:06 AM
 */
public class DMFileUploadCmp extends Pane {
	public static class DMFileUploadCmpBuilder {
		private String title;
		private ExtensionFilter extensionFilters[];
		private String uploadBtnText;
		private DMFileUploadEvent fileUploadEvent;
		private boolean verticalAlign = true;
		private boolean showfilePath = false;

		public DMFileUploadCmpBuilder title(String title) {
			this.title = title;
			return this;
		}

		public DMFileUploadCmpBuilder uploadBtnText(String uploadBtnText) {
			this.uploadBtnText = uploadBtnText;
			return this;
		}

		public DMFileUploadCmpBuilder extensionFilters(
				ExtensionFilter extensionFilters[]) {
			this.extensionFilters = extensionFilters;
			return this;
		}

		public DMFileUploadCmpBuilder fileUploadEvent(
				DMFileUploadEvent fileUploadEvent) {
			this.fileUploadEvent = fileUploadEvent;
			return this;
		}

		public DMFileUploadCmpBuilder verticalAlign(boolean verticalAlign) {
			this.verticalAlign = verticalAlign;
			return this;
		}

		public DMFileUploadCmpBuilder showfilePath(boolean showfilePath) {
			this.showfilePath = showfilePath;
			return this;
		}
	}

	private DMFileUploadCmpBuilder builder;
	private FileChooser fileChooser;
	private File selectedFile;
	private PubSubEventHandler handler;
	private FXLabelCmp filePathCmp;

	public DMFileUploadCmp(DMFileUploadCmpBuilder builder) {
		this.builder = builder;

		this.createComponent();
		this.addRootThemeStyleSheet();
		this.subscribeOnThemeChange();
	}

	private void createComponent() {
		this.createFileChooser();

		if (this.builder.showfilePath) {
			this.getChildren().add(this.getUploadPanel());
		} else {
			this.getChildren().add(this.getImportButton());
		}
	}

	private Pane getUploadPanel() {
		filePathCmp = new FXLabelCmp(new DMLabelBuilder().label(
				"fileUploadCmp.helperText").align(Pos.CENTER));
		HBox wrapperPane1 = new HBox(filePathCmp);
		wrapperPane1.setAlignment(Pos.CENTER_LEFT);
		wrapperPane1.getStyleClass().add("file-path");

		FXFontAwesomeIcon openFolderIcon = new FXFontAwesomeIcon(
				new FXFontAwesomeIconBuilder(FontAwesomeIcon.FOLDER_OPEN_ALT)
						.size("30px").faIconClickEvent(new IconClickEvent() {

							@Override
							public void onClick(Event event) {
								selectedFile = fileChooser
										.showOpenDialog(((Node) event
												.getSource()).getScene()
												.getWindow());

								if (selectedFile != null) {
									filePathCmp.setText(selectedFile.getPath());
								}
							}
						}));

		HBox wrapperPane2 = new HBox(openFolderIcon);
		wrapperPane2.setAlignment(Pos.CENTER_LEFT);
		wrapperPane2.getStyleClass().add("file-path");

		HBox selectFilePane = new HBox(wrapperPane2, wrapperPane1);
		selectFilePane.setAlignment(Pos.CENTER_LEFT);
		selectFilePane.getStyleClass().add("select-box");

		FXLabelCmp titleCmp = new FXLabelCmp(
				new DMLabelBuilder().label(this.builder.title));

		FXButtonCmp uploadBtn = new FXButtonCmp(
				new FXButtonCmpBuilder()
						.text(this.builder.uploadBtnText)
						.materialIcon(
								new FXMaterialDesignIcon(
										new FXMaterialDesignIconBuilder(
												MaterialDesignIcon.UPLOAD)))
						.eventHandler(
								event -> {
									filePathCmp.setText(DMMessageLocalizer
											.getLabel("fileUploadCmp.helperText"));
									if (builder.fileUploadEvent != null) {
										if (selectedFile != null) {
											builder.fileUploadEvent
													.onFileUpload(this.selectedFile);
										} else {
											FXMessageBox
													.getInstance()
													.show(new FXMessageBoxBuilder(
															"fileUploadCmp.selectFile")
															.alertType(AlertType.ERROR));
										}
									}
								}));

		VBox pane = new VBox(titleCmp);
		pane.setSpacing(10);

		if (this.builder.verticalAlign) {
			pane.getChildren().addAll(selectFilePane, uploadBtn);
		} else {
			HBox box = new HBox(selectFilePane, uploadBtn);
			box.setSpacing(10);

			pane.getChildren().add(box);
		}

		return pane;
	}

	private Node getImportButton() {
		FXButtonCmp importButton = new FXButtonCmp(new FXButtonCmpBuilder()
				.text(this.builder.uploadBtnText != null ? this.builder.uploadBtnText : "milkProducer.form.input.import")
				.className("styled-accent-button english-font")
				.materialIcon(
						new FXMaterialDesignIcon(
								new FXMaterialDesignIconBuilder(
										MaterialDesignIcon.IMPORT)))
				.eventHandler(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						selectedFile = fileChooser.showOpenDialog(((Node) event
								.getSource()).getScene().getWindow());
						if (selectedFile != null
								&& builder.fileUploadEvent != null) {
							builder.fileUploadEvent.onFileUpload(selectedFile);
						}
					}
				}));

		return importButton;
	}

	private void createFileChooser() {
		fileChooser = new FileChooser();

		if (this.builder.title != null) {
			fileChooser.setTitle(this.builder.title);
		}

		if (this.builder.extensionFilters != null) {
			fileChooser
					.getExtensionFilters()
					.addAll(FXCollections
							.observableArrayList(this.builder.extensionFilters));
		}
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
				getClass().getResource("FileUpload.css").toExternalForm());
		this.getStylesheets().add(
				getClass().getResource(
						ControlsTheme.getThemeCssFileName("FileUpload"))
						.toExternalForm());
	}
}
