/**
 * 
 */
package com.bt.dm.fx.controls.table.renderer;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;

import com.bt.dm.fx.controls.table.DMTableModel;
import com.bt.dm.fx.controls.table.event.TableRowSelectionEvent;

/**
 * 
 * @author Ramalingesha ML
 *
 *         Created on Dec 31, 2021 7:00:50 PM
 */
public class CheckBoxCellRenderer<S extends DMTableModel> extends
		TableCell<S, Boolean> {
	private final CheckBox checkBox;

	public CheckBoxCellRenderer(TableRowSelectionEvent rowSelectionEvent) {
		checkBox = new CheckBox();

		checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> observable,
					Boolean oldValue, Boolean newValue) {

				rowSelectionEvent.onCellChecked(getIndex(), newValue);
			}
		});

		this.setGraphic(checkBox);
		this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		this.setEditable(true);
	}

	@Override
	protected void updateItem(Boolean value, boolean empty) {
		super.updateItem(value, empty);

		if (!empty) {
			checkBox.setSelected(value);
		}
	}
}
