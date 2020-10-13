/**
 * 
 */
package com.bt.dm.fx.controls.labels;

import javafx.scene.control.CheckBox;

import com.bt.dm.fx.controls.DMLabelBuilder;
import com.bt.dm.fx.controls.DMLabelControl;

/**
 * @author Ramalingesha ML
 *
 *         Created on Oct 9, 2020 7:25:35 AM
 */
public class FXCheckBoxCmp extends CheckBox {
	public static class FXCheckBoxCmpBuilder extends DMLabelBuilder {
	}

	public FXCheckBoxCmp(DMLabelBuilder builder) {
		DMLabelControl.applyDefaults(this, builder);
	}
}
