/**
 * 
 */
package com.bt.dm.fx.controls.demo;

import java.util.HashMap;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;

import com.bt.dm.core.type.TextFormatTypeEnum;
import com.bt.dm.fx.controls.crosstable.DMCrossTable;
import com.bt.dm.fx.controls.crosstable.DMCrossTable.FXDMCrossTableBuilder;
import com.bt.dm.fx.controls.crosstable.DMCrossTableColumnModel;
import com.bt.dm.fx.controls.crosstable.DMCrossTableColumnModel.DMCrossTableColumnModelBuilder;
import com.bt.dm.fx.controls.crosstable.DMCrossTableModel;
import com.bt.dm.fx.controls.crosstable.DMCrossTableRowDataMap;
import com.bt.dm.fx.controls.table.DMTableModel;
import com.bt.dm.fx.controls.table.FxTableColumnSizeMapper;
import com.bt.dm.fx.controls.utils.SizeHelper;

/**
 * @author Ramalingesha ML
 *
 *         Created on Feb 16, 2021 8:45:20 PM
 */
public class CrossTableDemoView extends Pane {

	public CrossTableDemoView() {
		this.createComponent();
	}

	private void createComponent() {
		DMCrossTableModel<DMTableModel> crossTableModel = new DMCrossTableModel<DMTableModel>(
				this.getRowHeaderColumn(), this.getTableColumns());

		FXDMCrossTableBuilder<DMTableModel> builder = (FXDMCrossTableBuilder<DMTableModel>) new FXDMCrossTableBuilder<DMTableModel>(
				crossTableModel).height(200).header("Cross Table Demo")
				.equalColumnWidth(true);
		DMCrossTable<DMTableModel> crossTable = new DMCrossTable<DMTableModel>(
				builder);
		crossTable.setPrefWidth(SizeHelper.SCREEN_SIZE.getWidth() - 200);

		Map<String, Object> rowDataMap = new HashMap<String, Object>();
		rowDataMap.put("name", "Ravi");
		rowDataMap.put("test1", 67.3);
		rowDataMap.put("test3", 61);
		DMCrossTableRowDataMap crossTableRowDataMap = new DMCrossTableRowDataMap(
				rowDataMap);

		rowDataMap = new HashMap<String, Object>();
		rowDataMap.put("name", "Rani");
		rowDataMap.put("test1", 62.3);
		rowDataMap.put("test2", 45);
		rowDataMap.put("test3", 67.3);
		DMCrossTableRowDataMap crossTableRowDataMap2 = new DMCrossTableRowDataMap(
				rowDataMap);

		rowDataMap = new HashMap<String, Object>();
		rowDataMap.put("name", "Rani");
		rowDataMap.put("test1", 62.3);
		rowDataMap.put("test2", 45);
		rowDataMap.put("test3", 67.3);
		DMCrossTableRowDataMap crossTableRowDataMap3 = new DMCrossTableRowDataMap(
				rowDataMap);

		rowDataMap = new HashMap<String, Object>();
		rowDataMap.put("name", "Rani");
		rowDataMap.put("test1", 62.3);
		rowDataMap.put("test2", 45);
		rowDataMap.put("test3", 67.3);
		DMCrossTableRowDataMap crossTableRowDataMap4 = new DMCrossTableRowDataMap(
				rowDataMap);

		rowDataMap = new HashMap<String, Object>();
		rowDataMap.put("name", "Rani");
		rowDataMap.put("test1", 62.3);
		rowDataMap.put("test2", 45);
		rowDataMap.put("test3", 67.3);
		DMCrossTableRowDataMap crossTableRowDataMap5 = new DMCrossTableRowDataMap(
				rowDataMap);

		crossTable.addRecords(FXCollections.observableArrayList(
				crossTableRowDataMap, crossTableRowDataMap2,
				crossTableRowDataMap3, crossTableRowDataMap4,
				crossTableRowDataMap5));

		this.getChildren().add(crossTable);
	}

	private FxTableColumnSizeMapper<DMTableModel> getRowHeaderColumn() {
		return new FxTableColumnSizeMapper<DMTableModel>(
				new DMCrossTableColumnModel<DMTableModel>(
						new DMCrossTableColumnModelBuilder("", "name")));
	}

	private ObservableList<FxTableColumnSizeMapper<DMTableModel>> getTableColumns() {
		ObservableList<FxTableColumnSizeMapper<DMTableModel>> mappers = FXCollections
				.observableArrayList();
		for (int i = 0; i < 28; i++) {
			FxTableColumnSizeMapper<DMTableModel> column = new FxTableColumnSizeMapper<DMTableModel>(
					new DMCrossTableColumnModel<DMTableModel>(
							new DMCrossTableColumnModelBuilder(String.format(
									"Test %s", i + 1), String.format("test%s",
									i + 1)).textFormatType(
									TextFormatTypeEnum.QUANTITY)
									.readFromLocale(false)));
			mappers.add(column);
		}

		return mappers;
	}
}
