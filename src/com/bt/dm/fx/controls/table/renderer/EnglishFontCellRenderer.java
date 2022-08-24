/**
 * 
 */
package com.bt.dm.fx.controls.table.renderer;

import javafx.scene.control.TableCell;

import com.bt.dm.core.utils.DMStringUtils;
import com.bt.dm.fx.controls.utils.Fonts;

/**
 * 
 * @author Ramalingesha ML
 *
 *         Created on Dec 22, 2021 8:39:59 AM
 */
public class EnglishFontCellRenderer<T, V extends Object> extends
		TableCell<T, V> {

	@Override
	protected void updateItem(V value, boolean empty) {
		super.updateItem(value, empty);

		// Clear previous text and graphic if any
		this.setText(null);
		this.setGraphic(null);
		this.setFont(Fonts.getInstance().getEnglishFont());

		if (!empty) {
			this.setText(DMStringUtils.getNutralValue(value));
		}
	}
}
