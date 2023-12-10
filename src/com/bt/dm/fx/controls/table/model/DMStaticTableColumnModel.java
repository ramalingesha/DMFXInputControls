package com.bt.dm.fx.controls.table.model;

import com.bt.dm.core.model.DMModel;
import com.bt.dm.core.model.excel.ExcelColumnDataType;

/**
 * @author Ramalingesha ML
 *
 *         Created on Jan 14, 2023 08:55:03 AM
 */
public class DMStaticTableColumnModel implements DMModel {

	private static final long serialVersionUID = 1L;
	private String columnId;
	private String columnHeader;
	private boolean i18n = true;
	private String className;
	private double width = 100;
	private ExcelColumnDataType dataType;

	public DMStaticTableColumnModel(String columnId, String columnHeader) {
		this.columnId = columnId;
		this.columnHeader = columnHeader;
	}

	public DMStaticTableColumnModel i18n(boolean i18n) {
		this.i18n = i18n;
		return this;
	}

	public DMStaticTableColumnModel className(String className) {
		this.className = className;
		return this;
	}

	public DMStaticTableColumnModel width(double width) {
		this.width = width;
		return this;
	}
	
	public DMStaticTableColumnModel dataType(ExcelColumnDataType dataType) {
		this.dataType = dataType;
		return this;
	}

	public String getColumnId() {
		return columnId;
	}

	public String getColumnHeader() {
		return columnHeader;
	}

	public boolean isI18n() {
		return i18n;
	}

	public String getClassName() {
		return className;
	}

	public double getWidth() {
		return width;
	}

	public ExcelColumnDataType getDataType() {
		return dataType;
	}
}
