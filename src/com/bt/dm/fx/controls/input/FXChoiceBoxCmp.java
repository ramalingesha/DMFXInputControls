/**
 * 
 */
package com.bt.dm.fx.controls.input;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;

import com.bt.dm.fx.controls.DMControl;
import com.bt.dm.fx.controls.DMControlBuilder;
import com.bt.dm.fx.controls.events.DropDownChangeEvent;
import com.bt.dmhelper.utils.ConstantUtils;
import com.bt.dmhelper.utils.model.ReferenceModel;

/**
 * @author Ramalingesha ML
 *
 *         Created on Sep 23, 2020 2:01:34 PM
 */
public class FXChoiceBoxCmp extends DMControl<ReferenceModel> {
	public static class FXChoiceBoxCmpBuilder extends DMControlBuilder {
		private ObservableList<ReferenceModel> items;
		private DropDownChangeEvent dropDownChangeEvent;
		private boolean showEmptyOption;

		public FXChoiceBoxCmpBuilder items(ObservableList<ReferenceModel> items) {
			this.items = items;
			return this;
		}

		public FXChoiceBoxCmpBuilder onChange(
				DropDownChangeEvent dropDownChangeEvent) {
			this.dropDownChangeEvent = dropDownChangeEvent;
			return this;
		}

		public FXChoiceBoxCmpBuilder showEmptyOption(boolean showEmptyOption) {
			this.showEmptyOption = showEmptyOption;
			return this;
		}

		public DropDownChangeEvent onChange() {
			return this.dropDownChangeEvent;
		}
	}

	private ChoiceBox<String> choiceBox;
	private int selectedIndex = -1;

	public FXChoiceBoxCmp(DMControlBuilder builder) {
		super(builder);
		this.createComponent();
	}

	@Override
	public void createComponent() {
		FXChoiceBoxCmpBuilder choiceBoxCmpBuilder = (FXChoiceBoxCmpBuilder) this.builder;
		choiceBox = new ChoiceBox<String>();

		// Add empty option
		if (choiceBoxCmpBuilder.items == null) {
			choiceBoxCmpBuilder.items = FXCollections.emptyObservableList();
		}
		if (choiceBoxCmpBuilder.showEmptyOption) {
			choiceBoxCmpBuilder.items.add(0,
					ConstantUtils.COMBO_SELECT_REF_MODEL);
		}

		// Add items
		this.addItems(choiceBoxCmpBuilder.items);
		// Select first element
		if (choiceBoxCmpBuilder.items.size() > 0) {
			this.choiceBox.getSelectionModel().selectFirst();
			this.selectedIndex = 0;
		}

		// Add change listener
		choiceBox.getSelectionModel().selectedIndexProperty()
				.addListener(new ChangeListener<Number>() {

					@Override
					public void changed(
							ObservableValue<? extends Number> observableValue,
							Number value, Number newValue) {
						selectedIndex = newValue.intValue();
						if (choiceBoxCmpBuilder.dropDownChangeEvent != null) {
							choiceBoxCmpBuilder.dropDownChangeEvent
									.onOptionSelect(choiceBoxCmpBuilder.items
											.get(newValue.intValue()));
						}
					}
				});

		this.getChildren().add(this.constructControlWithDefaults(choiceBox));
	}

	@Override
	public ReferenceModel getValue() {
		ObservableList<ReferenceModel> items = ((FXChoiceBoxCmpBuilder) builder).items;
		if (items != null && selectedIndex != -1
				&& selectedIndex < items.size()) {
			return items.get(selectedIndex);
		}
		return null;
	}

	@Override
	public void setValue(ReferenceModel value) {
		if (value != null) {
			this.choiceBox.setValue(value.getValue());
		}
	}

	private ObservableList<String> getItemsDisplayNames(
			ObservableList<ReferenceModel> items) {
		ObservableList<String> options = FXCollections.observableArrayList();
		if (items != null) {
			items.stream().map(item -> item.getValue())
					.forEach(option -> options.add(option));
		}

		return options;
	}

	public void addItems(ObservableList<ReferenceModel> items) {
		if (items != null) {
			this.choiceBox.getItems().addAll(this.getItemsDisplayNames(items));
		}
	}
}
