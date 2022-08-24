/**
 * 
 */
package com.bt.dm.fx.controls.table.renderer;

import java.text.NumberFormat;

import javafx.scene.control.TableCell;

/**
 * 
 * @author Ramalingesha ML
 *
 *         Created on Jul 10, 2022 9:00:01 AM
 */
public class NumberCellRenderer<T, V extends Object> extends TableCell<T, V> {
	private NumberFormat numberFormat;

	public NumberCellRenderer(NumberFormat numberFormat) {
		this.numberFormat = numberFormat;
	}

	@Override
	protected void updateItem(V value, boolean empty) {
		super.updateItem(value, empty);

		// Clear previous text and graphic if any
		this.setText(null);
		this.setGraphic(null);

		if (!empty) {
			this.setText(this.numberFormat.format(value == null ? 0 : value));
		}
	}
}
