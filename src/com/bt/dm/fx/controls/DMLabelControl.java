/**
 * 
 */
package com.bt.dm.fx.controls;

import javafx.scene.control.Labeled;
import javafx.scene.text.Font;

import com.bt.dm.fx.controls.utils.Fonts;
import com.bt.dm.fx.controls.utils.SizeHelper;

/**
 * @author Ramalingesha ML
 *
 *         Created on Oct 9, 2020 8:03:46 AM
 */
public final class DMLabelControl {
	public static void applyDefaults(Labeled control, DMLabelBuilder builder) {
		Font font = builder.getFont() != null ? builder.getFont() : Fonts
				.getInstance().getDefaultFont();

		control.setText(builder.getLabel());
		control.setFont(font);

		if (builder.isFixedLabelSize()) {
			control.setPrefSize(SizeHelper.LABEL_SIZE.getWidth(),
					SizeHelper.LABEL_SIZE.getHeight());
		}
		if (builder.getAlign() != null) {
			control.setAlignment(builder.getAlign());
		}
	}
}
