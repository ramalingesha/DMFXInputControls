/**
 * 
 */
package com.bt.dm.fx.controls.dialog.search;

import java.util.stream.Collectors;

import com.bt.dm.core.model.DMSearchInputModel;
import com.bt.dm.core.model.ReferenceModel;
import com.bt.dm.core.service.data.CattleTypeConstants;
import com.bt.dm.core.service.data.CattleTypeMasterService;
import com.bt.dm.core.service.data.ShiftConstants;
import com.bt.dm.core.service.data.ShiftMasterService;
import com.bt.dm.fx.controls.input.FXChoiceBoxCmp;
import com.bt.dm.fx.controls.input.FXChoiceBoxCmp.FXChoiceBoxCmpBuilder;
import com.bt.dm.fx.controls.input.FXDatePickerCmp;
import com.bt.dm.fx.controls.input.FXDatePickerCmp.FXDatePickerCmpBuilder;
import com.bt.dm.fx.controls.utils.ControlSize;
import com.bt.dm.fx.controls.utils.SizeHelper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * @author Ramalingesha ML
 *
 *         Created on Sep 26, 2022 07:17:03 PM
 */
public class DMSearchInputPanel extends VBox {
	public static class DMSearchInputPanelBuilder {
		private boolean showDateRangeSearch = true;
		private boolean showDateSearch;
		private boolean showShiftSearch = true;
		private boolean showCattleTypeSearch = true;
		
		public DMSearchInputPanelBuilder showDateRangeSearch(boolean showDateRangeSearch) {
			this.showDateRangeSearch = showDateRangeSearch;
			return this;
		}
		
		public DMSearchInputPanelBuilder showDateSearch(boolean showDateSearch) {
			this.showDateSearch = showDateSearch;
			return this;
		}
		
		public DMSearchInputPanelBuilder showShiftSearch(boolean showShiftSearch) {
			this.showShiftSearch = showShiftSearch;
			return this;
		}
		
		public DMSearchInputPanelBuilder showCattleTypeSearch(boolean showCattleTypeSearch) {
			this.showCattleTypeSearch = showCattleTypeSearch;
			return this;
		}
	}
	
	private DMSearchInputPanelBuilder builder;
	private FXDatePickerCmp fromDateSearchCmp;
	private FXDatePickerCmp toDateSearchCmp;
	private FXChoiceBoxCmp shiftSearchCmp;
	private FXChoiceBoxCmp cattleTypeSearchCmp;
	
	public DMSearchInputPanel(DMSearchInputPanelBuilder builder) {
		this.builder = builder;
		this.createAndShowGUI();
		this.loadMasterData();
	}
	
	private void createAndShowGUI() {
		fromDateSearchCmp = new FXDatePickerCmp(
				new FXDatePickerCmpBuilder().setCurrentDate(true).label("app.fromDate"));
		toDateSearchCmp = new FXDatePickerCmp(
				new FXDatePickerCmpBuilder().setCurrentDate(true).label("app.toDate"));
		shiftSearchCmp = new FXChoiceBoxCmp(
				new FXChoiceBoxCmpBuilder().showEmptyOption(true).label("app.shift"));
		cattleTypeSearchCmp = new FXChoiceBoxCmp(
				new FXChoiceBoxCmpBuilder().showEmptyOption(true).label("app.cattleType"));
		
		int row = 0, column = 0;
		
		GridPane gridPane = new GridPane();
		gridPane.setHgap(10);
		gridPane.setVgap(20);
		
		if(builder.showDateRangeSearch) {
			gridPane.add(fromDateSearchCmp, column++, row);
			gridPane.add(toDateSearchCmp, column, row++);
		} else if(builder.showDateSearch) {
			gridPane.add(fromDateSearchCmp, column, row++);
		}
		
		column = 0;
		if(builder.showShiftSearch) {
			gridPane.add(shiftSearchCmp, column++, row);
		}
		
		if(builder.showCattleTypeSearch) {
			gridPane.add(cattleTypeSearchCmp, column++, row);
		}
		
		this.setSpacing(20);
		this.getChildren().add(gridPane);
	}
	
	private void loadMasterData() {
		ObservableList<ReferenceModel> shifts = ShiftMasterService.getInstance()
				.getModelsExceptSpecified(new int[] { ShiftConstants.ALL_SHIFT }).stream()
				.collect(Collectors.toCollection(FXCollections::observableArrayList));

		ObservableList<ReferenceModel> cattleTypes = CattleTypeMasterService.getInstance()
				.getModelsExceptSpecified(new int[] { CattleTypeConstants.ALL }).stream()
				.collect(Collectors.toCollection(FXCollections::observableArrayList));

		this.shiftSearchCmp.addItems(shifts);
		this.cattleTypeSearchCmp.addItems(cattleTypes);
	}
	
	public DMSearchInputModel getSearchInputModel() {
		DMSearchInputModel inputModel = new DMSearchInputModel();
		
		inputModel.setFromDate(fromDateSearchCmp.getValue());
		inputModel.setToDate(toDateSearchCmp.getValue());
		inputModel.setShift(shiftSearchCmp.isEmpty() ? null : shiftSearchCmp.getValue().getId());
		inputModel.setCattleType(cattleTypeSearchCmp.isEmpty() ? null : cattleTypeSearchCmp.getValue().getId());
		
		return inputModel;
	}
	
	public ControlSize getPanelSize() {
		return new ControlSize(SizeHelper.INPUT_CONTROL_SIZE.getWidth() * 3.9, 250);
	}
}
