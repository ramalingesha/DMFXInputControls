package com.bt.dm.fx.controls.demo;

import java.io.File;
import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser.ExtensionFilter;

import com.bt.dm.core.model.ReferenceModel;
import com.bt.dm.fx.controls.DMControlBuilder;
import com.bt.dm.fx.controls.button.DMIconButton;
import com.bt.dm.fx.controls.button.DMIconButton.DMIconButtonBuilder;
import com.bt.dm.fx.controls.button.FXButtonCmp;
import com.bt.dm.fx.controls.button.FXButtonCmp.FXButtonCmpBuilder;
import com.bt.dm.fx.controls.dialog.FXMessageBox;
import com.bt.dm.fx.controls.dialog.FXMessageBox.FXMessageBoxBuilder;
import com.bt.dm.fx.controls.dialog.upload.DMFileUploadCmp;
import com.bt.dm.fx.controls.dialog.upload.DMFileUploadCmp.DMFileUploadCmpBuilder;
import com.bt.dm.fx.controls.events.DMFileUploadEvent;
import com.bt.dm.fx.controls.events.DMFormPanelToolBarEvent;
import com.bt.dm.fx.controls.events.DropDownChangeEvent;
import com.bt.dm.fx.controls.events.IconClickEvent;
import com.bt.dm.fx.controls.image.FXImage;
import com.bt.dm.fx.controls.image.FXImage.FXImageBuilder;
import com.bt.dm.fx.controls.input.FXChoiceBoxCmp;
import com.bt.dm.fx.controls.input.FXChoiceBoxCmp.FXChoiceBoxCmpBuilder;
import com.bt.dm.fx.controls.input.FXDatePickerCmp;
import com.bt.dm.fx.controls.input.FXDatePickerCmp.FXDatePickerCmpBuilder;
import com.bt.dm.fx.controls.input.FXDualTextInputCmp;
import com.bt.dm.fx.controls.input.FXDualTextInputCmp.FXDualTextInputCmpBuilder;
import com.bt.dm.fx.controls.input.FXTextInputCmp;
import com.bt.dm.fx.controls.input.FXTextInputCmp.FXTextInputCmpBuilder;
import com.bt.dm.fx.controls.labels.FXFontAwesomeIcon;
import com.bt.dm.fx.controls.labels.FXFontAwesomeIcon.FXFontAwesomeIconBuilder;
import com.bt.dm.fx.controls.labels.FXMaterialDesignIcon;
import com.bt.dm.fx.controls.labels.FXMaterialDesignIcon.FXMaterialDesignIconBuilder;
import com.bt.dm.fx.controls.toolbar.DMToolBar;
import com.bt.dm.fx.controls.toolbar.DMToolBar.DMToolBarBuilder;
import com.bt.dm.fx.controls.utils.UIHelper;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;

/**
 * @author Ramalingesha ML
 *
 *         Created on Nov 21, 2020 6:29:12 PM
 */
public class ComponentsDemoView extends Pane {
	private FXTextInputCmp textField;

	public ComponentsDemoView() {
		this.createComponent();
	}

	private void createComponent() {
		// Create the VBox
		VBox root = new VBox();
		// Set the vertical spacing between children to 20px
		root.setSpacing(20);
		// Set the padding of the VBox
		// root.setPadding(new Insets(10, 10, 10, 10));
		// Add the Labels and the HBox to the VBox
		root.getChildren().addAll(this.getTextFieldControls(),
		 this.getDualTextFieldControls(), this.getChoiceBoxControls(),
		 this.getFontAwesomeIcons(), this.getDatePickerControls(),
		 this.getFormPanelToolBar(), this.getAlertPanel(),
				this.getFileUploadCmp(), this.getIconButtonCmp());
		this.getChildren().add(root);

		UIHelper.setFocus(this.textField.getControl());
	}

	private HBox getTextFieldControls() {
		HBox hbox = new HBox();
		hbox.setSpacing(10);
		hbox.setAlignment(Pos.CENTER_LEFT);
		textField = new FXTextInputCmp(
				new FXTextInputCmpBuilder().label("Text Field"));
		hbox.getChildren().addAll(
				textField,
				new FXTextInputCmp(new FXTextInputCmpBuilder().label(
						"Text Field Top").verticalAlign(true)),
				new FXTextInputCmp(new FXTextInputCmpBuilder().value(
						"Without Label").hideLabel(true)),
				new FXTextInputCmp(new FXTextInputCmpBuilder().placeHolder(
						"Prompt Message").hideLabel(true)));

		return hbox;
	}

	private HBox getDatePickerControls() {
		HBox hbox = new HBox();
		hbox.setSpacing(10);
		hbox.setAlignment(Pos.CENTER_LEFT);
		hbox.getChildren()
				.addAll(new FXDatePickerCmp(new FXDatePickerCmpBuilder()
						.label("Date Picker")),
						new FXDatePickerCmp(
								((FXDatePickerCmpBuilder) new FXDatePickerCmpBuilder()
										.placeHolder("Without Label")
										.hideLabel(true)).setCurrentDate(false)),
						new FXDatePickerCmp(
								((FXDatePickerCmpBuilder) new FXDatePickerCmpBuilder()
										.label("Initial Date"))
										.initialDate(LocalDate.of(2019, 5, 26))),
						new FXDatePickerCmp(
								((FXDatePickerCmpBuilder) new FXDatePickerCmpBuilder()
										.label("Date Format").verticalAlign(
												true)).setCurrentDate(false)
										.dateFormat("yyyy-MM-dd")));

		return hbox;
	}

	private HBox getDualTextFieldControls() {
		HBox hbox = new HBox();
		hbox.setSpacing(10);
		hbox.setAlignment(Pos.CENTER_LEFT);
		hbox.getChildren()
				.addAll(new FXDualTextInputCmp(
						new FXDualTextInputCmpBuilder().label("Text Field")),
						new FXDualTextInputCmp(new FXDualTextInputCmpBuilder()
								.label("Text Field Top").verticalAlign(true)),
						new FXDualTextInputCmp(
								((FXDualTextInputCmpBuilder) new FXDualTextInputCmpBuilder()
										.value("Without Translator").hideLabel(
												true))
										.hideTranslatorField(true)),
						new FXDualTextInputCmp(new FXDualTextInputCmpBuilder()
								.placeHolder("Prompt Message").hideLabel(true)));

		return hbox;
	}

	private HBox getChoiceBoxControls() {
		ObservableList<ReferenceModel> items = FXCollections
				.observableArrayList();
		items.add(new ReferenceModel(1, "One", "One"));
		items.add(new ReferenceModel(2, "Two", "Two"));
		items.add(new ReferenceModel(3, "Three", "Three"));
		items.add(new ReferenceModel(4, "Four", "Four"));

		DMControlBuilder builder1 = ((FXChoiceBoxCmpBuilder) new FXChoiceBoxCmpBuilder()
				.label("Choice Box")).items(items).onChange(
				new DropDownChangeEvent() {

					@Override
					public void onOptionSelect(ReferenceModel model) {
						System.out.println("Selected id: " + model.getId());
					}
				});
		DMControlBuilder builder2 = ((FXChoiceBoxCmpBuilder) ((FXChoiceBoxCmpBuilder) new FXChoiceBoxCmpBuilder()
				.label("Choice Box Top")).items(
				FXCollections.observableArrayList(items)).verticalAlign(true))
				.showEmptyOption(true).onChange(new DropDownChangeEvent() {

					@Override
					public void onOptionSelect(ReferenceModel model) {
						System.out.println("Selected Name: " + model.getValue());
					}
				});

		FXChoiceBoxCmp choiceBoxCmp = new FXChoiceBoxCmp(builder1);
		HBox hbox = new HBox();
		hbox.setSpacing(10);
		hbox.getChildren().addAll(choiceBoxCmp, new FXChoiceBoxCmp(builder2));

		return hbox;
	}

	private HBox getFontAwesomeIcons() {
		HBox box = new HBox();
		box.setSpacing(10);
		box.getChildren()
				.addAll(new FXMaterialDesignIcon(
						new FXMaterialDesignIconBuilder(
								MaterialDesignIcon.THUMB_UP)
								.iconClickEvent(new IconClickEvent() {

									@Override
									public void onClick(Event event) {
										System.out.println("I am clicked...");
									}
								})),
						new FXMaterialDesignIcon(
								new FXMaterialDesignIconBuilder(
										MaterialDesignIcon.CLOSE).size("4em")),
						new FXFontAwesomeIcon(new FXFontAwesomeIconBuilder(
								FontAwesomeIcon.THUMBS_UP)),
						new FXFontAwesomeIcon(new FXFontAwesomeIconBuilder(
								FontAwesomeIcon.CLOSE).size("4em")
								.faIconClickEvent(new IconClickEvent() {

									@Override
									public void onClick(Event event) {
										System.out.println("I am clicked...");
									}
								})),
						new FXImage(
								new FXImageBuilder(
										"com/bt/dm/fx/controls/resources/images/DoodMapanLogo.png")
										.width(200)),
						new FXImage(
								new FXImageBuilder(
										"com/bt/dm/fx/controls/resources/images/DoodMapanLogo.png")
										.width(200).height(150)));

		return box;
	}

	private VBox getFormPanelToolBar() {
		VBox box = new VBox();
		box.getChildren().add(
				new DMToolBar(new DMToolBarBuilder()
						.toolBarEvent(new DMFormPanelToolBarEvent() {

							@Override
							public void onSaveBtnClick() {
								System.out.println("Save button clicked...");
							}

							@Override
							public void onCancelBtnClick() {
								System.out.println("Cancel button clicked...");
							}
						})));

		return box;
	}

	private HBox getAlertPanel() {
		FXMessageBox messageBox = FXMessageBox.getInstance();

		FXButtonCmp warningAlert = new FXButtonCmp(new FXButtonCmpBuilder()
				.text("Warning Alert").eventHandler(
						new EventHandler<ActionEvent>() {

							@Override
							public void handle(ActionEvent arg0) {
								messageBox
										.show(new FXMessageBoxBuilder(
												"This is to inform that you are seeing DoodMapan's custom warning box.")
												.title("Warning").alertType(
														AlertType.WARNING));
							}
						}));

		FXButtonCmp infoAlert = new FXButtonCmp(new FXButtonCmpBuilder().text(
				"Info Alert").eventHandler(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				messageBox
						.show(new FXMessageBoxBuilder(
								"This is to inform that you are seeing DoodMapan's custom message box.")
								.title("Information").alertType(
										AlertType.INFORMATION));
			}
		}));

		FXButtonCmp errorAlert = new FXButtonCmp(new FXButtonCmpBuilder().text(
				"Error Alert").eventHandler(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				messageBox
						.show(new FXMessageBoxBuilder(
								"This is to inform that you are seeing DoodMapan's custom error box.")
								.title("Error").alertType(AlertType.ERROR));
			}
		}));

		FXButtonCmp confirmationAlert = new FXButtonCmp(
				new FXButtonCmpBuilder().text("Confirm Alert").eventHandler(
						new EventHandler<ActionEvent>() {

							@Override
							public void handle(ActionEvent arg0) {
								messageBox
										.show(new FXMessageBoxBuilder(
												"DO you want to delete?")
												.title("Confirmation")
												.alertType(
														AlertType.CONFIRMATION))
										.filter(response -> response == UIHelper.ALERT_OK_BUTTON)
										.ifPresent(
												response -> System.out
														.println("Confirmed..."));
							}
						}));

		HBox box = new HBox(infoAlert, warningAlert, errorAlert,
				confirmationAlert);
		box.setSpacing(10);

		return box;
	}

	private Pane getFileUploadCmp() {
		DMFileUploadCmp fileUploadCmp = new DMFileUploadCmp(
				new DMFileUploadCmpBuilder()
						.title("Select image file")
						.extensionFilters(
								new ExtensionFilter[] { new ExtensionFilter(
										"Image Files", "*.png", "*.jpg",
										"*.gif") })
						.uploadBtnText("Upload Image")
						.fileUploadEvent(new DMFileUploadEvent() {

							@Override
							public void onFileUpload(File file) {
								FXMessageBox
										.getInstance()
										.show(new FXMessageBoxBuilder(
												String.format(
														"Selected image %s uploaded successfully.",
														file.getName())));
							}
						}));
		return fileUploadCmp;
	}

	private Pane getIconButtonCmp() {
		return new DMIconButton(
				new DMIconButtonBuilder("Icon Button")
						.materialIcon(MaterialDesignIcon.HOME).buttonClickEvent(new IconClickEvent() {
							
							@Override
							public void onClick(Event event) {
								System.out.println("Icon button clicked...");
							}
						}));
	}
}
