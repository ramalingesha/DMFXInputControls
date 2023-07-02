/**
 * 
 */
package com.bt.dm.fx.controls.input;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javafx.beans.value.ChangeListener;
import javafx.scene.control.Control;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.util.StringConverter;

import com.bt.dm.core.utils.RandomNumberGenerator;
import com.bt.dm.fx.controls.DMControl;
import com.bt.dm.fx.controls.DMControlBuilder;

/**
 * @author Ramalingesha ML
 *
 *         Created on Oct 10, 2020 8:47:40 AM
 */
public class FXDatePickerCmp extends DMControl<Date> {
	public static class FXDatePickerCmpBuilder extends DMControlBuilder {
		private LocalDate initialDate;
		private String dateFormat = "dd/MM/yyyy";
		private boolean setCurrentDate = true;
		private LocalDate calendarStartDate;

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

		public FXDatePickerCmpBuilder calendarStartDate(
				LocalDate calendarStartDate) {
			this.calendarStartDate = calendarStartDate;
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

		if (this.builder.isReadOnly()) {
			datePickerCmp.setDisable(true);
			datePickerCmp.setStyle("-fx-opacity: 1");
		}

		this.setDateFormat();

		if (((FXDatePickerCmpBuilder) builder).calendarStartDate != null) {
			this.disablePastDates();
		}

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
	public Date getValue() {
		LocalDate value = this.datePickerCmp.getValue();
		if (value == null) {
			return null;
		}
		return Date
				.from(value.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

	@Override
	public void setValue(Date value) {
		if (value == null) {
			this.datePickerCmp.setValue(null);
		} else {
			this.datePickerCmp.setValue(value.toInstant()
					.atZone(ZoneId.systemDefault()).toLocalDate());
		}
	}

	public void setValue(LocalDate value) {
		if (value == null) {
			this.datePickerCmp.setValue(null);
		} else {
			this.datePickerCmp.setValue(value);
		}
	}
	
	public void clearDateSelection() {
		this.datePickerCmp.setValue(null);
	}

	@Override
	public String generateUniqueId() {
		return "dateInputCmp_"
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
		return this.datePickerCmp;
	}

	@Override
	public void clear() {
		if (((FXDatePickerCmpBuilder) this.builder).setCurrentDate) {
			datePickerCmp.setValue(LocalDate.now());
		} else {
			datePickerCmp.setValue(null);
		}
	}

	public void addListener(ChangeListener<LocalDate> changeListener) {
		this.datePickerCmp.valueProperty().addListener(changeListener);
	}

	public void updateCalendarStartDate(LocalDate calendarStartDate) {
		((FXDatePickerCmpBuilder) builder).calendarStartDate(calendarStartDate);
	}

	private void disablePastDates() {
		this.datePickerCmp.getEditor().setDisable(true);
		this.datePickerCmp.getEditor().setOpacity(1);

		this.datePickerCmp.setDayCellFactory(picker -> new DateCell() {
			public void updateItem(LocalDate date, boolean empty) {
				super.updateItem(date, empty);
				LocalDate calendarStartDate = ((FXDatePickerCmpBuilder) builder).calendarStartDate;

				setDisable(empty || date.compareTo(calendarStartDate) < 0);
			}
		});
	}
}
