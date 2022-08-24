/**
 * 
 */
package com.bt.dm.fx.controls.table.renderer;

import javafx.scene.control.TableCell;

import com.bt.dm.core.utils.DMNumberUtils;

/**
 * 
 * @author Ramalingesha ML
 *
 *         Created on Feb 13, 2021 5:51:25 PM
 */
public class QuantityCellRenderer<T, V extends Object> extends TableCell<T, V> {

	@Override
	protected void updateItem(V value, boolean empty) {
		super.updateItem(value, empty);

		// Clear previous text and graphic if any
		this.setText(null);
		this.setGraphic(null);

		if (!empty) {
			this.setText(DMNumberUtils.getQuantityString(value));
		}
	}
}
