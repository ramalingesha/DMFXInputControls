/**
 * 
 */
package com.bt.dm.fx.controls.table;

import java.io.Serializable;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * @author Ramalingesha ML
 *
 *         Created on Oct 10, 2020 7:41:59 PM
 */
public class DMTableModel implements Serializable {

	private static final long serialVersionUID = 1L;
	private final BooleanProperty checked;

	public DMTableModel() {
		this.checked = new SimpleBooleanProperty(false);
	}

	public DMTableModel(Boolean checked) {
		this.checked = new SimpleBooleanProperty(checked == null ? false
				: checked);
	}

	public BooleanProperty checkedProperty() {
		return checked;
	}

	public boolean isSelected() {
		return this.checked.getValue();
	}
}
