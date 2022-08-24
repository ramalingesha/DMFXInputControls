package com.bt.dm.fx.controls.input;

import javafx.beans.value.ChangeListener;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;

import com.bt.dm.core.type.TextFormatTypeEnum;
import com.bt.dm.core.utils.DMNumberUtils;
import com.bt.dm.core.utils.DMStringUtils;
import com.bt.dm.core.utils.RandomNumberGenerator;
import com.bt.dm.fx.controls.DMControl;
import com.bt.dm.fx.controls.DMControlBuilder;
import com.bt.dm.fx.controls.utils.Fonts;

/**
 * 
 * @author Ramalingesha ML
 *
 *         Created on Sep 22, 2020 7:28:54 PM
 */
public class FXTextInputCmp extends DMControl<Object> {
	public static class FXTextInputCmpBuilder extends DMControlBuilder {
		private TextFormatTypeEnum inputTextType;

		public FXTextInputCmpBuilder inputTextType(
				TextFormatTypeEnum inputTextType) {
			this.inputTextType = inputTextType;
			return this;
		}
	}

	private TextField textFieldCmp;

	public FXTextInputCmp(DMControlBuilder builder) {
		super(builder);
		this.createComponent();
	}

	@Override
	public void createComponent() {
		textFieldCmp = new TextField(this.builder.getValue());
		textFieldCmp.setPromptText(this.builder.getPlaceHolder());
		textFieldCmp.setEditable(!this.builder.isReadOnly());
		textFieldCmp.setFocusTraversable(!this.builder.isReadOnly());
		textFieldCmp.setFont(this.builder.getFont() != null ? this.builder
				.getFont() : Fonts.getInstance().getDefaultFont());

		this.getChildren().add(this.constructControlWithDefaults(textFieldCmp));
	}

	@Override
	public String getValue() {
		return this.textFieldCmp.getText();
	}

	public Integer getIntValue() {
		String value = this.getValue();
		return DMNumberUtils.parseInt(DMStringUtils.isEmpty(value) ? 0 : value);
	}

	public Double getDoubleValue() {
		String value = this.getValue();
		return DMNumberUtils.parseDouble(DMStringUtils.isEmpty(value) ? 0
				: value);
	}

	@Override
	public void setValue(Object value) {
		if (value == null) {
			this.textFieldCmp.setText("");
			return;
		}

		if (!(this.builder instanceof FXTextInputCmpBuilder)) {
			this.textFieldCmp.setText(DMStringUtils.getNutralValue(value));
			return;
		}

		TextFormatTypeEnum inputTextType = ((FXTextInputCmpBuilder) this.builder).inputTextType;
		if (inputTextType == null) {
			this.textFieldCmp.setText(DMStringUtils.getNutralValue(value));
			return;
		}

		switch (inputTextType) {
		case AMOUNT:
			this.textFieldCmp.setText(DMNumberUtils.getAmountString(value));
			break;
		case QUANTITY:
			this.textFieldCmp.setText(DMNumberUtils.getQuantityString(value));
			break;
		case INTEGER:
			Integer intNumber = DMNumberUtils.parseInt(DMStringUtils
					.getNutralValue(value));
			this.textFieldCmp.setText(intNumber == null ? "" : String
					.valueOf(intNumber));
			break;
		case DOUBLE:
			Double floatNumber = DMNumberUtils.parseDouble(DMStringUtils
					.getNutralValue(value));
			this.textFieldCmp.setText(floatNumber == null ? "" : String
					.valueOf(floatNumber));
			break;

		default:
			this.textFieldCmp.setText(DMStringUtils.getNutralValue(value));
		}
	}

	public void setEditable(boolean editable) {
		this.textFieldCmp.setEditable(editable);
	}

	@Override
	public String generateUniqueId() {
		return "textFieldCmp_"
				+ RandomNumberGenerator.getInstance().getRandomNumber(8);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public Control getControl() {
		return this.textFieldCmp;
	}

	@Override
	public void clear() {
		this.textFieldCmp.clear();
	}

	public void addListener(ChangeListener<String> changeListener) {
		this.textFieldCmp.textProperty().addListener(changeListener);
	}

	public boolean isEmpty() {
		String value = this.getValue();
		return value == null || value.trim().isEmpty();
	}
}
