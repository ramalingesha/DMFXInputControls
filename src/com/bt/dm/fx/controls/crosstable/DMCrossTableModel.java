/**
 * 
 */
package com.bt.dm.fx.controls.crosstable;

import javafx.collections.ObservableList;

import com.bt.dm.fx.controls.table.FxTableColumnSizeMapper;
import com.bt.dm.fx.controls.table.model.DMTableModel;

/**
 * @author Ramalingesha ML
 *
 *         Created on Feb 16, 2021 10:36:34 AM
 */
public class DMCrossTableModel<S extends DMTableModel> extends DMTableModel {

	private static final long serialVersionUID = 1L;
	private FxTableColumnSizeMapper<S> rowHeaderColumn;
	private ObservableList<FxTableColumnSizeMapper<S>> columns;
	private ObservableList<DMTableModel> records;

	public DMCrossTableModel(FxTableColumnSizeMapper<S> rowHeaderColumn,
			ObservableList<FxTableColumnSizeMapper<S>> columns) {
		this.rowHeaderColumn = rowHeaderColumn;
		this.columns = columns;
	}

	public FxTableColumnSizeMapper<S> getRowHeaderColumn() {
		return rowHeaderColumn;
	}

	public ObservableList<FxTableColumnSizeMapper<S>> getColumns() {
		return columns;
	}

	public ObservableList<DMTableModel> getRecords() {
		return records;
	}

	public void setRecords(ObservableList<DMTableModel> records) {
		this.records = records;
	}
}
