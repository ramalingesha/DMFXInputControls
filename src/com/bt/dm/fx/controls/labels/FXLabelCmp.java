/**
 * 
 */
package com.bt.dm.fx.controls.labels;

import javafx.scene.control.Label;

import com.bt.dm.fx.controls.DMLabelBuilder;
import com.bt.dm.fx.controls.DMLabelControl;

/**
 * @author Ramalingesha ML
 *
 *         Created on Oct 5, 2020 11:22:30 AM
 */
public class FXLabelCmp extends Label {
	public static class FXLabelCmpBuilder extends DMLabelBuilder {

	}

	public FXLabelCmp(DMLabelBuilder builder) {
		DMLabelControl.applyDefaults(this, builder);
	}
}
