/**
 * 
 */
package com.bt.dm.fx.controls;

import javafx.geometry.Pos;
import javafx.scene.text.Font;

/**
 * @author Ramalingesha ML
 *
 *         Created on Oct 9, 2020 7:36:11 AM
 */
public class DMLabelBuilder {
	private Pos align;
	private boolean fixedLabelSize = false;
	private Font font;
	private String label;

	public DMLabelBuilder align(Pos align) {
		this.align = align;
		return this;
	}

	public DMLabelBuilder fixedLabelSize(boolean fixedLabelSize) {
		this.fixedLabelSize = fixedLabelSize;
		return this;
	}

	public DMLabelBuilder font(Font font) {
		this.font = font;
		return this;
	}

	public DMLabelBuilder label(String label) {
		this.label = label;
		return this;
	}

	public Pos getAlign() {
		return align;
	}

	public Font getFont() {
		return font;
	}

	public String getLabel() {
		return label;
	}

	public boolean isFixedLabelSize() {
		return fixedLabelSize;
	}
}
