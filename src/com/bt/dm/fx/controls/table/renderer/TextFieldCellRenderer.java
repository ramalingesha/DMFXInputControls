/**
 * 
 */
package com.bt.dm.fx.controls.table.renderer;

import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;

import com.bt.dm.core.utils.DMNumberUtils;
import com.bt.dm.fx.controls.table.event.TableCellValueUpdateEvent;

/**
 * 
 * @author Ramalingesha ML
 *
 *         Created on Aug 21, 2022 7:42:18 PM
 */
public class TextFieldCellRenderer<S, V extends Object> extends TableCell<S, V> {
	private TextField textField;

	public TextFieldCellRenderer(
			TableCellValueUpdateEvent tableCellValueUpdateEvent) {
		if (tableCellValueUpdateEvent != null) {
			textField = new TextField();
			textField.setTextFormatter(new TextFormatter<>(
					new StringConverter<String>() {
						@Override
						public String fromString(String value) {
							return DMNumberUtils.getAmountString(value);
						}

						@Override
						public String toString(String value) {
							return DMNumberUtils.getAmountString(value);
						}
					}));

			textField.focusedProperty().addListener(
					(observable, oldValue, newValue) -> {
						if (!newValue) {
							tableCellValueUpdateEvent.onCellValueUpdate(0,
									textField.getText());
						}
					});

			this.setGraphic(textField);
			this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
			this.setEditable(true);
		}
	}

	@Override
	protected void updateItem(V value, boolean empty) {
		super.updateItem(value, empty);

		if (!empty) {
			if (textField != null) {
				textField
						.setText(DMNumberUtils
								.getAmountString(value instanceof String ? DMNumberUtils
										.parseDouble(value, true) : value));
				this.setGraphic(textField);
			} else {
				this.setText(DMNumberUtils.getAmountString(value));
			}
		} else {
			this.setGraphic(null);
		}
	}
}
