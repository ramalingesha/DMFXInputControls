/**
 * 
 */
package com.bt.dm.fx.controls.table;

import java.text.NumberFormat;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import com.bt.dm.core.i18n.DMMessageLocalizer;
import com.bt.dm.core.type.TextFormatTypeEnum;
import com.bt.dm.fx.controls.table.event.TableCellValueUpdateEvent;
import com.bt.dm.fx.controls.table.renderer.AmountCellRenderer;
import com.bt.dm.fx.controls.table.renderer.EnglishFontCellRenderer;
import com.bt.dm.fx.controls.table.renderer.NumberCellRenderer;
import com.bt.dm.fx.controls.table.renderer.QuantityCellRenderer;
import com.bt.dm.fx.controls.table.renderer.TextFieldCellRenderer;

/**
 * @author Ramalingesha ML
 *
 *         Created on Oct 10, 2020 8:10:00 PM
 */
public class FXTableColumnModel<T, V extends Object> extends TableColumn<T, V> {
	private String header;
	private String propertyName;
	private TextFormatTypeEnum textFormatType;
	private boolean readFromLocale;
	private boolean englishFont;
	private Double colWidthPercentage;
	private String className;

	public FXTableColumnModel(String header, String propertyName) {
		this(header, propertyName, null);
	}

	public FXTableColumnModel(String header, String propertyName,
			boolean englishFont) {
		this(header, propertyName, englishFont, null);
	}

	public FXTableColumnModel(String header, String propertyName,
			TextFormatTypeEnum textFormatType) {
		this(header, propertyName, textFormatType, true, false, null);
	}

	public FXTableColumnModel(String header, String propertyName,
			TextFormatTypeEnum textFormatType, String className) {
		this(header, propertyName, textFormatType, true, false,
				className);
	}

	public FXTableColumnModel(String header, String propertyName,
			boolean englishFont, TextFormatTypeEnum textFormatType) {
		this(header, propertyName, textFormatType, true, englishFont,
				null);
	}

	public FXTableColumnModel(String header, String propertyName,
			TextFormatTypeEnum textFormatType, boolean readFromLocale,
			boolean englishFont, boolean nudiFont) {
		this(header, propertyName, textFormatType, readFromLocale, englishFont,
				null);
	}

	public FXTableColumnModel(String header, String propertyName,
			TextFormatTypeEnum textFormatType, boolean readFromLocale,
			boolean englishFont, String className) {
		this.header = header;
		this.propertyName = propertyName;
		this.textFormatType = textFormatType;
		this.readFromLocale = readFromLocale;
		this.englishFont = englishFont;
		this.className = className;
		this.initColumn();
	}

	private void initColumn() {
		if (this.header != null) {
			this.setText(this.readFromLocale ? DMMessageLocalizer
					.getLabel(this.header) : this.header);
		}

		if (propertyName != null) {
			this.setId(propertyName);
		}

		this.setCellValueFactory(new PropertyValueFactory<T, V>(propertyName));

		if (this.englishFont) {
			this.getStyleClass().add("english-font");
		}

		if (className != null) {
			this.getStyleClass().addAll(className.split(","));
		}

		if (textFormatType == null) {
			return;
		}

		// Apply text format
		switch (textFormatType) {
		case AMOUNT:
			this.setCellFactory(column -> new AmountCellRenderer<T, V>());
			break;
		case QUANTITY:
			this.setCellFactory(column -> new QuantityCellRenderer<T, V>());
			break;
		case ENGLISH:
			this.setCellFactory(column -> new EnglishFontCellRenderer<T, V>());
			break;
		case TEXT_INPUT:
			this.setCellFactory(column -> new TextFieldCellRenderer<T, V>(
					new TableCellValueUpdateEvent() {

						@Override
						public void onCellValueUpdate(int row, String columnId, String value) {
							System.out.println(value);
						}
					}));
			break;

		default:
			break;
		}
	}

	public void setNumberFormat(NumberFormat numberFormat) {
		this.setCellFactory(column -> new NumberCellRenderer<T, V>(numberFormat));
	}

	public String getPropertyName() {
		return propertyName;
	}

	public Double getColWidthPercentage() {
		return colWidthPercentage;
	}

	public void setColWidthPercentage(Double colWidthPercentage) {
		this.colWidthPercentage = colWidthPercentage;
	}

	public void updateHeader(String newHeader, boolean readFromLocale) {
		if (newHeader != null) {
			this.setText(readFromLocale ? DMMessageLocalizer
					.getLabel(newHeader) : newHeader);
		}
	}

	public void setColumnWidth(double width) {
		this.setPrefWidth(width);
	}
}
