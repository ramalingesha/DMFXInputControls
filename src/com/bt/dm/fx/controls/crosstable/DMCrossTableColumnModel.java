/**
 * 
 */
package com.bt.dm.fx.controls.crosstable;

import javafx.beans.property.SimpleObjectProperty;

import com.bt.dm.core.type.TextFormatTypeEnum;
import com.bt.dm.fx.controls.table.DMTableModel;
import com.bt.dm.fx.controls.table.FXTableColumnModel;
import com.bt.dm.fx.controls.table.event.TableCellValueUpdateEvent;

/**
 * @author Ramalingesha ML
 *
 *         Created on Feb 18, 2021 5:53:36 PM
 */
public class DMCrossTableColumnModel<S extends DMTableModel> extends
		FXTableColumnModel<DMCrossTableRowDataMap, Object> {
	public static class DMCrossTableColumnModelBuilder {
		private String header;
		private String propertyName;
		private TextFormatTypeEnum textFormatType;
		private TableCellValueUpdateEvent tableCellValueUpdateEvent;
		private boolean readFromLocale = true;
		private boolean englishFont;
		private Double colWidthPercentage;
		private Double colWidth;

		public DMCrossTableColumnModelBuilder(String header, String propertyName) {
			this.header = header;
			this.propertyName = propertyName;
		}

		public DMCrossTableColumnModelBuilder textFormatType(
				TextFormatTypeEnum textFormatType) {
			this.textFormatType = textFormatType;
			return this;
		}
		
		public DMCrossTableColumnModelBuilder tableCellValueUpdateEvent(
				TableCellValueUpdateEvent tableCellValueUpdateEvent) {
			this.tableCellValueUpdateEvent = tableCellValueUpdateEvent;
			return this;
		}

		public DMCrossTableColumnModelBuilder readFromLocale(
				boolean readFromLocale) {
			this.readFromLocale = readFromLocale;
			return this;
		}

		public DMCrossTableColumnModelBuilder englishFont(boolean englishFont) {
			this.englishFont = englishFont;
			return this;
		}

		public DMCrossTableColumnModelBuilder colWidthPercentage(
				Double colWidthPercentage) {
			this.colWidthPercentage = colWidthPercentage;
			return this;
		}

		public DMCrossTableColumnModelBuilder colWidth(Double colWidth) {
			this.colWidth = colWidth;
			return this;
		}
	}

	public DMCrossTableColumnModel(DMCrossTableColumnModelBuilder builder) {
		super(builder.header, builder.propertyName, builder.textFormatType,
				builder.readFromLocale, builder.englishFont, false);
		this.initColumn(builder);
	}

	private void initColumn(DMCrossTableColumnModelBuilder builder) {
		if (builder.colWidth != null) {
			this.setColumnWidth(builder.colWidth);
		} else {
			this.setColWidthPercentage(builder.colWidthPercentage);
		}

		this.setCellValueFactory(cellData -> {
			return new SimpleObjectProperty<>(cellData.getValue().getValue(
					this.getPropertyName()));
		});
	}
}
