/**
 * 
 */
package com.bt.dm.fx.controls;

import javafx.scene.text.Font;

import com.bt.dm.fx.controls.events.DMFocusListener;
import com.bt.dm.fx.controls.utils.ControlSize;

/**
 * @author Ramalingesha ML
 *
 *         Created on Sep 23, 2020 11:02:43 AM
 */
public abstract class DMControlBuilder implements Cloneable {
	private boolean fixedControlSize = true;
	private boolean fixedLabelSize = true;
	private DMFocusListener focusListener;
	private Font font;
	private boolean hideLabel;
	private ControlSize inputSize;
	private String label;
	private ControlSize labelSize;
	private String placeHolder;
	private boolean readOnly;
	private String value;
	private boolean verticalAlign;
	private boolean englishFont;
	private String[] classNames;
	private boolean i18n = true;

	public DMControlBuilder fixedControlSize(boolean fixedControlSize) {
		this.fixedControlSize = fixedControlSize;
		return this;
	}

	public DMControlBuilder classNames(String[] classNames) {
		this.classNames = classNames;
		return this;
	}

	public DMControlBuilder fixedLabelSize(boolean fixedLabelSize) {
		this.fixedLabelSize = fixedLabelSize;
		return this;
	}

	public DMControlBuilder i18n(boolean i18n) {
		this.i18n = i18n;
		return this;
	}

	public DMControlBuilder focusListener(DMFocusListener focusListener) {
		this.focusListener = focusListener;
		return this;
	}

	public DMControlBuilder font(Font font) {
		this.font = font;
		return this;
	}

	public DMFocusListener getFocusListener() {
		return focusListener;
	}

	public DMControlBuilder hideLabel(boolean hideLabel) {
		this.hideLabel = hideLabel;
		return this;
	}

	public DMControlBuilder englishFont(boolean englishFont) {
		this.englishFont = englishFont;
		return this;
	}

	public DMControlBuilder inputSize(ControlSize inputSize) {
		this.inputSize = inputSize;
		return this;
	}

	public DMControlBuilder label(String label) {
		this.label = label;
		return this;
	}

	public DMControlBuilder labelSize(ControlSize labelSize) {
		this.labelSize = labelSize;
		return this;
	}

	public DMControlBuilder placeHolder(String placeHolder) {
		this.placeHolder = placeHolder;
		return this;
	}

	public DMControlBuilder readOnly(boolean readOnly) {
		this.readOnly = readOnly;
		return this;
	}

	public DMControlBuilder value(String value) {
		this.value = value;
		return this;
	}

	public DMControlBuilder verticalAlign(boolean verticalAlign) {
		this.verticalAlign = verticalAlign;
		return this;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public Font getFont() {
		return font;
	}

	public ControlSize getInputSize() {
		return inputSize;
	}

	public String getLabel() {
		return label;
	}

	public ControlSize getLabelSize() {
		return labelSize;
	}

	public String getPlaceHolder() {
		return placeHolder;
	}

	public String getValue() {
		return value;
	}

	public boolean isFixedControlSize() {
		return fixedControlSize;
	}

	public boolean isFixedLabelSize() {
		return fixedLabelSize;
	}

	public boolean isHideLabel() {
		return hideLabel;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public boolean isVerticalAlign() {
		return verticalAlign;
	}

	public boolean isEnglishFont() {
		return englishFont;
	}

	public String[] getClassNames() {
		return classNames;
	}

	public boolean isI18n() {
		return i18n;
	}
}
