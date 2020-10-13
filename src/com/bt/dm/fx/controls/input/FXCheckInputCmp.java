/**
 * 
 */
package com.bt.dm.fx.controls.input;

import javafx.scene.control.CheckBox;

import com.bt.dm.fx.controls.DMControl;
import com.bt.dm.fx.controls.DMControlBuilder;

/**
 * @author Ramalingesha ML
 *
 *         Created on Oct 9, 2020 8:55:23 AM
 */
public class FXCheckInputCmp extends DMControl<Boolean> {
	public static class FXCheckInputCmpBuilder extends DMControlBuilder {
	}

	private CheckBox checkBox;

	public FXCheckInputCmp(DMControlBuilder builder) {
		super(builder);
		this.createComponent();
	}

	@Override
	public void createComponent() {
		this.checkBox = new CheckBox();

		this.getChildren()
				.add(this.constructControlWithDefaults(this.checkBox));
	}

	@Override
	public Boolean getValue() {
		return this.checkBox.isSelected();
	}

	@Override
	public void setValue(Boolean value) {
		this.checkBox.setSelected(value);
	}

}
