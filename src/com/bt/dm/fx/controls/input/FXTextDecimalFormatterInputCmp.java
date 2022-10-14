package com.bt.dm.fx.controls.input;

import java.text.NumberFormat;

import javafx.beans.value.ChangeListener;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;

import com.bt.dm.core.utils.DMNumberUtils;
import com.bt.dm.core.utils.DMStringUtils;
import com.bt.dm.core.utils.RandomNumberGenerator;
import com.bt.dm.fx.controls.DMControl;
import com.bt.dm.fx.controls.DMControlBuilder;
import com.bt.dm.fx.controls.input.FXTextInputCmp.FXTextInputCmpBuilder;
import com.bt.dm.fx.controls.utils.Fonts;

/**
 * 
 * @author Ramalingesha ML
 *
 *         Created on Jul 9, 2022 8:31:34 AM
 */
public class FXTextDecimalFormatterInputCmp extends DMControl<Object> {
	class DecimalFormatterInput extends TextField {
		private int noOfDecimals;

		public DecimalFormatterInput(int noOfDecimals) {
			this.noOfDecimals = noOfDecimals;
		}

		@Override
		public void replaceText(int start, int end, String text) {
			if (!text.matches("[-]*[0-9]*\\.?[0-9]*")) {
				return;
			}

			String oldText = DMStringUtils.getNutralValue(this.getText());
			int decimalPointIndex = oldText.indexOf(".");

			if (text.equals(".")) {
				if (decimalPointIndex != -1) {
					return;
				} else {
					if ((oldText.length() - start) > noOfDecimals) {
						return;
					}
				}
			}

			if (!text.isEmpty()
					&& decimalPointIndex > -1
					&& start > decimalPointIndex
					&& (Math.abs(decimalPointIndex - oldText.length())) > noOfDecimals) {
				return;
			}

			super.replaceText(start, end, text);
		}

		@Override
		public void replaceSelection(String text) {
			// if (text.matches(this.regExString.toString())) {
			super.replaceSelection(text);
			// }
		}
	}

	public static class FXTextDecimalFormatterInputCmpBuilder extends
			FXTextInputCmpBuilder {
		private int noOfDecimals;
		private NumberFormat numberFormat;

		public FXTextDecimalFormatterInputCmpBuilder(int noOfDecimals,
				NumberFormat numberFormat) {
			this.noOfDecimals = noOfDecimals;
			this.numberFormat = numberFormat;
		}
	}

	private DecimalFormatterInput textFieldCmp;
	FXTextDecimalFormatterInputCmpBuilder decimalFormatterInputCmpBuilder;

	public FXTextDecimalFormatterInputCmp(DMControlBuilder builder) {
		super(builder);
		this.decimalFormatterInputCmpBuilder = (FXTextDecimalFormatterInputCmpBuilder) builder;
		this.createComponent();
	}

	@Override
	public void createComponent() {
		textFieldCmp = new DecimalFormatterInput(
				decimalFormatterInputCmpBuilder.noOfDecimals);
		textFieldCmp.setText(this.builder.getValue());
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
		try {
			this.textFieldCmp
					.setText(decimalFormatterInputCmpBuilder.numberFormat
							.format(value == null ? 0 : value));
		} catch (Exception e) {
			this.textFieldCmp.setText("");
		}
	}

	public void setEditable(boolean editable) {
		this.textFieldCmp.setEditable(editable);
	}

	@Override
	public String generateUniqueId() {
		return "decimalFormatTextFieldCmp_"
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
