/**
 * 
 */
package com.bt.dm.fx.controls;

import javafx.geometry.Pos;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import com.bt.dm.fx.controls.utils.ControlSize;

/**
 * @author Ramalingesha ML
 *
 *         Created on Oct 9, 2020 7:36:11 AM
 */
public class DMLabelBuilder {
	private Pos align;
	private TextAlignment textAlign;
	private boolean fixedLabelSize = false;
	private Font font;
	private boolean h3;
	private boolean bold;
	private String label;
	private boolean i18n = true;
	private String[] classNames;
	private ControlSize size;

	public DMLabelBuilder align(Pos align) {
		this.align = align;
		return this;
	}

	public DMLabelBuilder textAlign(TextAlignment textAlign) {
		this.textAlign = textAlign;
		return this;
	}

	public DMLabelBuilder fixedLabelSize(boolean fixedLabelSize) {
		this.fixedLabelSize = fixedLabelSize;
		return this;
	}

	public DMLabelBuilder size(ControlSize size) {
		this.size = size;
		return this;
	}

	public DMLabelBuilder font(Font font) {
		this.font = font;
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

	public DMLabelBuilder h3(boolean h3) {
		this.h3 = h3;
		return this;
	}

	public DMLabelBuilder bold(boolean bold) {
		this.bold = bold;
		return this;
	}

	public boolean isFixedLabelSize() {
		return fixedLabelSize;
	}

	public boolean isH3() {
		return h3;
	}

	public DMLabelBuilder label(String label) {
		this.label = label;
		return this;
	}

	public DMLabelBuilder i18n(boolean i18n) {
		this.i18n = i18n;
		return this;
	}

	public boolean isI18n() {
		return i18n;
	}

	public DMLabelBuilder classNames(String[] classNames) {
		this.classNames = classNames;
		return this;
	}

	public String[] getClassNames() {
		return classNames;
	}

	public boolean isBold() {
		return bold;
	}

	public ControlSize getSize() {
		return size;
	}

	public TextAlignment getTextAlign() {
		return textAlign;
	}
}
