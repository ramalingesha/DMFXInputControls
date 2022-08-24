/**
 * 
 */
package com.bt.dm.fx.controls.table;

import java.util.List;

import javafx.event.Event;

import com.bt.dm.fx.controls.dialog.FXMessageBox;
import com.bt.dm.fx.controls.dialog.FXMessageBox.FXMessageBoxBuilder;

/**
 * @author Ramalingesha ML
 *
 *         Created on Jan 1, 2021 8:03:08 AM
 */
public class DMTableActions<S extends DMTableModel> {
	private FXMessageBox messageBox;

	public DMTableActions() {
		this.messageBox = FXMessageBox.getInstance();
	}

	public void generateReport() {
		this.messageBox.show(new FXMessageBoxBuilder("Work in progress..."));
	}

	public void exportToExcel() {
		this.messageBox.show(new FXMessageBoxBuilder("Work in progress..."));
	}

	public void exportToPdf() {
		this.messageBox.show(new FXMessageBoxBuilder("Work in progress..."));
	}

	public void onEditClick(S selectedTableModel, int selectedRowIndex,
			int selectedColumnIndex) {

	}

	public void onDeleteClick(S selectedTableModel, int selectedRowIndex,
			int selectedColumnIndex) {

	}

	public void onRowCheckBoxSelected(boolean selected, S selectedTableModel) {

	}

	public void onAllRowsCheckBoxClicked(boolean selected, List<S> selectedRows) {

	}

	public void onExpandClick(Event event) {

	}
}
