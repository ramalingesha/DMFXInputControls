/**
 * 
 */
package com.bt.dm.fx.controls.main;

import java.time.LocalDate;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import com.bt.dm.fx.controls.DMControlBuilder;
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
import com.bt.dm.fx.controls.toolbar.DMFormPanelToolBar;
import com.bt.dm.fx.controls.toolbar.DMFormPanelToolBar.DMFormPanelToolBarBuilder;
import com.bt.dm.fx.controls.utils.AppLocale;
import com.bt.dm.fx.controls.utils.Fonts;
import com.bt.dmhelper.utils.model.ReferenceModel;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;

/**
 * @author Ramalingesha ML
 *
 *         Created on Sep 22, 2020 7:46:35 PM
 */
public class Main extends Application {

	public static void main(String[] args) {
		Fonts.getInstance().setLocaleFonts(AppLocale.ENGLISH);
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		// Create the VBox
		VBox root = new VBox();
		root.setStyle("-fx-background-color:lightblue;");
		// Set the vertical spacing between children to 20px
		root.setSpacing(20);
		// Set the padding of the VBox
		root.setPadding(new Insets(10, 10, 10, 10));
		// Add the Labels and the HBox to the VBox
		root.getChildren().addAll(this.getTextFieldControls(),
				this.getDualTextFieldControls(), this.getChoiceBoxControls(),
				this.getFontAwesomeIcons(), this.getDatePickerControls(),
				this.getFormPanelToolBar());

		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setStyle("-fx-background-color:transparent;");
		scrollPane.setContent(root);
		scrollPane.setFitToWidth(true);

		// Creating a scene object
		Scene scene = new Scene(scrollPane);
		// Add the StyleSheet to the Scene
		scene.getStylesheets().add(
				getClass().getResource("app.css").toExternalForm());

		// Setting title to the Stage
		stage.setTitle("DoodMapan FXControls");

		// Adding scene to the stage
		stage.setScene(scene);
		stage.setMaximized(true);
		// stage.setFullScreen(true);

		// Displaying the contents of the stage
		stage.show();
	}

	private HBox getTextFieldControls() {
		HBox hbox = new HBox();
		hbox.setSpacing(10);
		hbox.setAlignment(Pos.CENTER_LEFT);
		hbox.getChildren().addAll(
				new FXTextInputCmp(
						new FXTextInputCmpBuilder().label("Text Field")),
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
				new DMFormPanelToolBar(new DMFormPanelToolBarBuilder()
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
}
