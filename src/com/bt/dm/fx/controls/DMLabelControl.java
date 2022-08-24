/**
 * 
 */
package com.bt.dm.fx.controls;

import javafx.scene.control.Labeled;
import javafx.scene.text.Font;

import com.bt.dm.core.i18n.DMMessageLocalizer;
import com.bt.dm.fx.controls.utils.ControlSize;
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

		control.setText(builder.isI18n() ? DMMessageLocalizer.getLabel(builder
				.getLabel()) : builder.getLabel());
		control.setFont(font);

		String[] classNames = builder.getClassNames();
		if (classNames != null) {
			control.getStyleClass().addAll(classNames);
		}

		// Set label size
		ControlSize size = builder.getSize();
		if (size != null) {
			control.setPrefSize(size.getWidth(), size.getHeight());
		} else {
			if (builder.isFixedLabelSize()) {
				control.setPrefSize(SizeHelper.LABEL_SIZE.getWidth(),
						SizeHelper.LABEL_SIZE.getHeight());
			} else {
				control.setPrefHeight(SizeHelper.LABEL_SIZE.getHeight());
			}
		}

		if (builder.getAlign() != null) {
			control.setAlignment(builder.getAlign());
		}

		if (builder.getTextAlign() != null) {
			control.setTextAlignment(builder.getTextAlign());
		}

		if (builder.isH3()) {
			control.getStyleClass().add("heading3");
		}

		if (builder.isBold()) {
			control.getStyleClass().add("bold-text");
		}

		control.setWrapText(true);
	}
}
