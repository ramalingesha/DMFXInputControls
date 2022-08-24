/**
 * 
 */
package com.bt.dm.fx.controls.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;

import com.bt.dm.fx.controls.table.DMTableActions;
import com.bt.dm.fx.controls.table.FXTableColumnModel;
import com.bt.dm.fx.controls.table.FXTableView;
import com.bt.dm.fx.controls.table.FXTableView.FXTableViewBuilder;
import com.bt.dm.fx.controls.table.FxTableColumnSizeMapper;

/**
 * @author Ramalingesha ML
 *
 *         Created on Dec 30, 2020 12:39:02 PM
 */
public class TableDemoView extends Pane {
	private FXTableView<TableDemoModel> table;

	public TableDemoView() {
		this.createComponent();
	}

	private void createComponent() {
		ObservableList<TableDemoModel> societyDetails = FXCollections
				.observableArrayList();
		societyDetails.add(new TableDemoModel(1, "Rama", "9/9/1999",
				"Bangalore"));
		societyDetails.add(new TableDemoModel(1, "Rama", "9/9/1999",
				"Bangalore"));
		societyDetails.add(new TableDemoModel(1, "Rama", "9/9/1999",
				"Bangalore"));

		FXTableViewBuilder<TableDemoModel> builder = new FXTableViewBuilder<TableDemoModel>(
				this.getTableColumns()).data(societyDetails).height(200)
				.width(600).header("Demo Table")
				.tableActions(new DMTableActions<TableDemoModel>() {
					@Override
					public void onEditClick(TableDemoModel selectedTableModel,
							int selectedRowIndex, int selectedColumnIndex) {
						System.out.println("Editing " + selectedRowIndex
								+ selectedColumnIndex);
						System.out.println(selectedTableModel);
					}

					@Override
					public void onDeleteClick(
							TableDemoModel selectedTableModel,
							int selectedRowIndex, int selectedColumnIndex) {
						System.out.println("Delete " + selectedRowIndex
								+ selectedColumnIndex);
						System.out.println(selectedTableModel);
					}
				});
		this.table = new FXTableView<TableDemoModel>(builder);

		this.getChildren().add(table);
	}

	private ObservableList<FxTableColumnSizeMapper<TableDemoModel>> getTableColumns() {
		FxTableColumnSizeMapper<TableDemoModel> slNoColumn = new FxTableColumnSizeMapper<TableDemoModel>(
				new FXTableColumnModel<TableDemoModel, Integer>("SlNo", "slNo"),
				0.1);
		FxTableColumnSizeMapper<TableDemoModel> nameColumn = new FxTableColumnSizeMapper<TableDemoModel>(
				new FXTableColumnModel<TableDemoModel, String>("Name", "name"),
				0.32);
		FxTableColumnSizeMapper<TableDemoModel> dateOfBirthColumn = new FxTableColumnSizeMapper<TableDemoModel>(
				new FXTableColumnModel<TableDemoModel, String>("Date Of Birth",
						"dateOfBirth"), 0.2);
		FxTableColumnSizeMapper<TableDemoModel> addressColumn = new FxTableColumnSizeMapper<TableDemoModel>(
				new FXTableColumnModel<TableDemoModel, String>("Address",
						"address"), 0.27);

		@SuppressWarnings("unchecked")
		ObservableList<FxTableColumnSizeMapper<TableDemoModel>> mappers = FXCollections
				.observableArrayList(slNoColumn, nameColumn, dateOfBirthColumn,
						addressColumn);

		return mappers;
	}
}
