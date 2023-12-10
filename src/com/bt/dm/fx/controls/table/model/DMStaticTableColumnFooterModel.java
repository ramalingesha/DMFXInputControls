package com.bt.dm.fx.controls.table.model;

import com.bt.dm.core.model.DMModel;

/**
 * @author Ramalingesha ML
 *
 *         Created on Oct 1, 2023 07:58:03 AM
 */
public class DMStaticTableColumnFooterModel implements DMModel {

	private static final long serialVersionUID = 1L;
	private String columnId;
	private String footerText;
	private boolean i18n = false;
	private String className;
	private boolean rightAlign;

	public DMStaticTableColumnFooterModel(String columnId, String footerText) {
		this.columnId = columnId;
		this.footerText = footerText;
	}

	public DMStaticTableColumnFooterModel i18n(boolean i18n) {
		this.i18n = i18n;
		return this;
	}

	public DMStaticTableColumnFooterModel className(String className) {
		this.className = className;
		return this;
	}

	public DMStaticTableColumnFooterModel rightAlign(boolean rightAlign) {
		this.rightAlign = rightAlign;
		return this;
	}

	public String getColumnId() {
		return columnId;
	}

	public String getFooterText() {
		return footerText;
	}

	public boolean isI18n() {
		return i18n;
	}

	public String getClassName() {
		return className;
	}

	public boolean isRightAlign() {
		return rightAlign;
	}
}
