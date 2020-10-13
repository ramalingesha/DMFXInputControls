/**
 * 
 */
package com.bt.dm.fx.controls.input;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.scene.control.DatePicker;
import javafx.util.StringConverter;

import com.bt.dm.fx.controls.DMControl;
import com.bt.dm.fx.controls.DMControlBuilder;

/**
 * @author Ramalingesha ML
 *
 *         Created on Oct 10, 2020 8:47:40 AM
 */
public class FXDatePickerCmp extends DMControl<LocalDate> {
	public static class FXDatePickerCmpBuilder extends DMControlBuilder {
		private LocalDate initialDate;
		private String dateFormat = "dd/MM/yyyy";
		private boolean setCurrentDate = true;

		public FXDatePickerCmpBuilder initialDate(LocalDate initialDate) {
			this.initialDate = initialDate;
			return this;
		}

		public FXDatePickerCmpBuilder dateFormat(String dateFormat) {
			this.dateFormat = dateFormat;
			return this;
		}

		public FXDatePickerCmpBuilder setCurrentDate(boolean setCurrentDate) {
			this.setCurrentDate = setCurrentDate;
			return this;
		}
	}

	private DatePicker datePickerCmp;

	public FXDatePickerCmp(DMControlBuilder builder) {
		super(builder);
		this.createComponent();
	}

	@Override
	public void createComponent() {
		datePickerCmp = new DatePicker();

		LocalDate date = ((FXDatePickerCmpBuilder) this.builder).initialDate;
		if (date != null) {
			datePickerCmp.setValue(date);
		} else {
			if (((FXDatePickerCmpBuilder) this.builder).setCurrentDate) {
				datePickerCmp.setValue(LocalDate.now());
			}
		}
		datePickerCmp.setEditable(!this.builder.isReadOnly());

		this.setDateFormat();

		this.getChildren()
				.add(this.constructControlWithDefaults(datePickerCmp));
	}

	private void setDateFormat() {
		String dateFormat = ((FXDatePickerCmpBuilder) builder).dateFormat;
		StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
			DateTimeFormatter dateFormatter = DateTimeFormatter
					.ofPattern(dateFormat);

			@Override
			public String toString(LocalDate date) {
				if (date != null) {
					return dateFormatter.format(date);
				} else {
					return "";
				}
			}

			@Override
			public LocalDate fromString(String string) {
				if (string != null && !string.isEmpty()) {
					return LocalDate.parse(string, dateFormatter);
				} else {
					return null;
				}
			}
		};

		this.datePickerCmp.setConverter(converter);
		// Set placeholder
		String placeholder = this.builder.getPlaceHolder() != null ? this.builder
				.getPlaceHolder() : dateFormat.toLowerCase();
		datePickerCmp.setPromptText(placeholder);
	}

	@Override
	public LocalDate getValue() {
		return this.datePickerCmp.getValue();
	}

	@Override
	public void setValue(LocalDate value) {
		this.datePickerCmp.setValue(value);
	}

}
