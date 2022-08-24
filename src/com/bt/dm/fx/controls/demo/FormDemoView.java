package com.bt.dm.fx.controls.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;

import com.bt.dm.core.model.ReferenceModel;
import com.bt.dm.fx.controls.DMLabelBuilder;
import com.bt.dm.fx.controls.events.DMFormPanelToolBarEvent;
import com.bt.dm.fx.controls.form.DMForm;
import com.bt.dm.fx.controls.form.DMForm.DMFormBuilder;
import com.bt.dm.fx.controls.form.InputValidator;
import com.bt.dm.fx.controls.form.ValidationType;
import com.bt.dm.fx.controls.input.FXChoiceBoxCmp;
import com.bt.dm.fx.controls.input.FXChoiceBoxCmp.FXChoiceBoxCmpBuilder;
import com.bt.dm.fx.controls.input.FXDatePickerCmp;
import com.bt.dm.fx.controls.input.FXDatePickerCmp.FXDatePickerCmpBuilder;
import com.bt.dm.fx.controls.input.FXDualTextInputCmp;
import com.bt.dm.fx.controls.input.FXDualTextInputCmp.FXDualTextInputCmpBuilder;
import com.bt.dm.fx.controls.input.FXTextInputCmp;
import com.bt.dm.fx.controls.input.FXTextInputCmp.FXTextInputCmpBuilder;
import com.bt.dm.fx.controls.labels.FXCheckBoxCmp;
import com.bt.dm.fx.controls.labels.FXLabelCmp;
import com.bt.dm.fx.controls.toolbar.DMToolBar.DMToolBarBuilder;
import com.bt.dm.fx.controls.utils.UIHelper;

/**
 * @author Ramalingesha ML
 *
 *         Created on Nov 21, 2020 6:29:35 PM
 */
public class FormDemoView extends Pane {
	private FXDualTextInputCmp dualTextInputCmp;
	private FXTextInputCmp textFieldCmp;
	private FXDatePickerCmp datePickerCmp;
	private FXChoiceBoxCmp choiceBoxCmp;
	private FXCheckBoxCmp memberCheckCmp;
	private FXCheckBoxCmp nonMemberCheckCmp;
	private DMForm form;

	public FormDemoView() {
		this.createComponent();
	}

	private void createComponent() {
		DMFormBuilder formBuilder = new DMFormBuilder(
				this.getFormInputControls()).formHeader("Demo Form")
				.toolBarBuilder(
						new DMToolBarBuilder()
								.toolBarEvent(new DMFormPanelToolBarEvent() {

									@Override
									public void onSaveBtnClick() {
										if (form.isValidForm()) {
											System.out
													.println("Saved successfully...");
										}
									}

									@Override
									public void onCancelBtnClick() {
										form.clearFormInputs();
									}
								}));

		form = new DMForm(formBuilder);
		form.addValidation(textFieldCmp, new InputValidator(
				ValidationType.REQUIRED, "This field is required"));
		form.addValidation(dualTextInputCmp, new InputValidator(
				ValidationType.REQUIRED, "This field is required"));
		form.addValidation(choiceBoxCmp, new InputValidator(
				ValidationType.REQUIRED, "This field is required"));
		form.addValidation(datePickerCmp, new InputValidator(
				ValidationType.REQUIRED, "This field is required"));

		this.getChildren().add(form);
	}

	private Pane getFormInputControls() {
		dualTextInputCmp = new FXDualTextInputCmp(
				new FXDualTextInputCmpBuilder().label("Text Field"));
		textFieldCmp = new FXTextInputCmp(
				new FXTextInputCmpBuilder().label("Text Field"));
		datePickerCmp = new FXDatePickerCmp(
				((FXDatePickerCmpBuilder) new FXDatePickerCmpBuilder()
						.label("Date Picker")).setCurrentDate(false));

		choiceBoxCmp = new FXChoiceBoxCmp(
				((FXChoiceBoxCmpBuilder) new FXChoiceBoxCmpBuilder()
						.label("Choice Box")).items(this.getChoiceBoxItems())
						.showEmptyOption(true));

		memberCheckCmp = new FXCheckBoxCmp(new DMLabelBuilder().label("Member"));
		nonMemberCheckCmp = new FXCheckBoxCmp(
				new DMLabelBuilder().label("Non Member"));
		HBox memberChoiceBox = new HBox(new FXLabelCmp(new DMLabelBuilder()
				.label("Membership").fixedLabelSize(true)
				.align(Pos.CENTER_RIGHT)), memberCheckCmp, nonMemberCheckCmp);
		memberChoiceBox.setSpacing(10);

		TilePane pane = new TilePane();
		pane.setPrefColumns(2);
		pane.setHgap(10);
		pane.setVgap(15);

		pane.getChildren().addAll(dualTextInputCmp, textFieldCmp,
				datePickerCmp, choiceBoxCmp, memberChoiceBox);

		return pane;
	}

	private ObservableList<ReferenceModel> getChoiceBoxItems() {
		ObservableList<ReferenceModel> items = FXCollections
				.observableArrayList();
		items.add(new ReferenceModel(1, "One", "One"));
		items.add(new ReferenceModel(2, "Two", "Two"));
		items.add(new ReferenceModel(3, "Three", "Three"));
		items.add(new ReferenceModel(4, "Four", "Four"));

		return items;
	}

	public void setFocus() {
		UIHelper.setFocus(this.dualTextInputCmp.getControl());
	}
}
