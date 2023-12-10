/**
 * 
 */
package com.bt.dm.fx.controls.events;

import com.bt.dm.fx.controls.table.model.DMReportTableDataModel;

/**
 * @author Ramalingesha ML
 *
 *         Created on Jul 31, 2021 8:16:31 PM
 */
public interface DMReportActionsToolbarEvent {
	public void onViewBtnClick();

	public void onPrintBtnClick();

	public void onExcelBtnClick();

	public void onPdfBtnClick();

	public default DMReportTableDataModel getReportData() {
		return null;
	}

	public default void onCancelBtnClick() {
	}
}
