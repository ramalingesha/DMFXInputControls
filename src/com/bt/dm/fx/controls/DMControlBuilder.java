/**
 * 
 */
package com.bt.dm.fx.controls;

import javafx.scene.text.Font;

import com.bt.dm.fx.controls.utils.ControlSize;

/**
 * @author Ramalingesha ML
 *
 *         Created on Sep 23, 2020 11:02:43 AM
 */
public abstract class DMControlBuilder implements Cloneable {
	private boolean fixedLabelSize = true;
	private boolean fixedControlSize = true;
	private Font font;
	private boolean hideLabel;
	private ControlSize inputSize;
	private String label;
	private ControlSize labelSize;
	private String placeHolder;
	private boolean readOnly;
	private String value;
	private boolean verticalAlign;

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public DMControlBuilder fixedLabelSize(boolean fixedLabelSize) {
		this.fixedLabelSize = fixedLabelSize;
		return this;
	}

	public DMControlBuilder fixedControlSize(boolean fixedControlSize) {
		this.fixedControlSize = fixedControlSize;
		return this;
	}

	public DMControlBuilder font(Font font) {
		this.font = font;
		return this;
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

	public DMControlBuilder hideLabel(boolean hideLabel) {
		this.hideLabel = hideLabel;
		return this;
	}

	public DMControlBuilder inputSize(ControlSize inputSize) {
		this.inputSize = inputSize;
		return this;
	}

	public boolean isFixedLabelSize() {
		return fixedLabelSize;
	}

	public boolean isFixedControlSize() {
		return fixedControlSize;
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
}
