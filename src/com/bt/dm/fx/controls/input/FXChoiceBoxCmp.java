/**
 * 
 */
package com.bt.dm.fx.controls.input;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Control;

import com.bt.dm.core.model.ReferenceModel;
import com.bt.dm.core.pubsub.PubSubEvent;
import com.bt.dm.core.pubsub.PubSubEventDataModel;
import com.bt.dm.core.pubsub.PubSubEventHandler;
import com.bt.dm.core.utils.ConstantUtils;
import com.bt.dm.core.utils.DMCollectionUtils;
import com.bt.dm.core.utils.RandomNumberGenerator;
import com.bt.dm.fx.controls.DMControl;
import com.bt.dm.fx.controls.DMControlBuilder;
import com.bt.dm.fx.controls.events.DropDownChangeEvent;
import com.bt.dm.fx.controls.theme.ControlsTheme;

/**
 * @author Ramalingesha ML
 *
 *         Created on Sep 23, 2020 2:01:34 PM
 */
public class FXChoiceBoxCmp extends DMControl<ReferenceModel> {
	public static class FXChoiceBoxCmpBuilder extends DMControlBuilder {
		private List<ReferenceModel> items;
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
	private List<ReferenceModel> itemsList;

	public FXChoiceBoxCmp(DMControlBuilder builder) {
		super(builder);
		this.createComponent();
	}

	@Override
	public void createComponent() {
		FXChoiceBoxCmpBuilder choiceBoxCmpBuilder = (FXChoiceBoxCmpBuilder) this.builder;
		choiceBox = new ChoiceBox<String>();
		this.itemsList = new ArrayList<ReferenceModel>();

		// Add empty option
		if (choiceBoxCmpBuilder.items == null) {
			choiceBoxCmpBuilder.items = FXCollections.emptyObservableList();
		}
		if (choiceBoxCmpBuilder.showEmptyOption) {
			this.addEmptyOption();
		}

		// Add items
		this.addItems(choiceBoxCmpBuilder.items);

		// Select first element
		if (this.itemsList.size() > 0 || choiceBoxCmpBuilder.showEmptyOption) {
			this.choiceBox.getSelectionModel().selectFirst();
			this.selectedIndex = 0;
		}
		
		choiceBox.getStyleClass().add("english-font");
		
		// Add change listener
		choiceBox.getSelectionModel().selectedIndexProperty()
				.addListener(new ChangeListener<Number>() {

					@Override
					public void changed(
							ObservableValue<? extends Number> observableValue,
							Number value, Number newValue) {
						selectedIndex = newValue.intValue();
						if (selectedIndex != -1
								&& choiceBoxCmpBuilder.dropDownChangeEvent != null) {
							choiceBoxCmpBuilder.dropDownChangeEvent
									.onOptionSelect(itemsList
											.get(selectedIndex));
						}
					}
				});

		this.addRootThemeStyleSheet();
		this.subscribeOnThemeChange();
		this.getChildren().add(this.constructControlWithDefaults(choiceBox));
	}

	private void addEmptyOption() {
		this.addItems(FXCollections
				.observableArrayList(ConstantUtils.COMBO_SELECT_REF_MODEL));
	}

	@Override
	public ReferenceModel getValue() {
		if (this.itemsList != null && selectedIndex != -1
				&& selectedIndex < this.itemsList.size()) {
			return this.itemsList.get(selectedIndex);
		}
		return null;
	}

	@Override
	public void setValue(ReferenceModel value) {
		if (value != null) {
			this.choiceBox.setValue(value.getValue());
		} else {
			this.choiceBox.getSelectionModel().selectFirst();
		}
	}
	
	public void setValue(int id) {
		if (DMCollectionUtils.notEmptyOrNull(itemsList)) {
			int index = 0;
			for (ReferenceModel refModel : itemsList) {
				if (refModel.getId().compareTo(id) == 0) {
					this.choiceBox.getSelectionModel().select(index);
					break;
				}

				index++;
			}
		}
	}

	private ObservableList<String> getItemsDisplayNames(
			List<ReferenceModel> items) {
		ObservableList<String> options = FXCollections.observableArrayList();
		if (items != null) {
			items.stream().map(item -> item.getValue())
					.forEach(option -> options.add(option));
		}

		return options;
	}

	public void addItems(List<ReferenceModel> items) {
		this.addItems(items, true);
	}

	public void addItems(List<ReferenceModel> items, boolean selectFirst) {
		if (items != null) {
			this.itemsList.addAll(items);
			this.choiceBox.getItems().addAll(this.getItemsDisplayNames(items));

			if (selectFirst) {
				this.choiceBox.getSelectionModel().selectFirst();
			}
		}
	}

	@Override
	public String generateUniqueId() {
		return "choiceInputCmp_"
				+ RandomNumberGenerator.getInstance().getRandomNumber(8);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public Control getControl() {
		return this.choiceBox;
	}

	@Override
	public void clear() {
		this.choiceBox.getSelectionModel().selectFirst();
	}

	public void clearItems() {
		this.choiceBox.getItems().clear();
		this.itemsList.clear();

		if (((FXChoiceBoxCmpBuilder) this.builder).showEmptyOption) {
			this.addEmptyOption();
		}
	}

	public boolean isEmpty() {
		return this.getValue() == null
				|| this.getValue() == ConstantUtils.COMBO_SELECT_REF_MODEL;
	}

	public List<ReferenceModel> getItemRefModels() {
		return this.itemsList;
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
	
	private void subscribeOnThemeChange() {
		PubSubEventHandler handler = new PubSubEventHandler() {

			@Override
			public void onPubSubData(PubSubEventDataModel data) {
				addRootThemeStyleSheet();
			}
		};
		PubSubEvent.getInstance().subscribe("THEME", handler);
	}
}
