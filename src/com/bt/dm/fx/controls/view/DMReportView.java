package com.bt.dm.fx.controls.view;

import com.bt.dm.core.type.DMRActionType;
import com.bt.dm.fx.controls.dialog.setting.DMSettingDialogInputHandler;
import com.bt.dm.fx.controls.events.DMReportActionsToolbarEvent;

public abstract class DMReportView extends DMView implements DMReportActionsToolbarEvent, DMSettingDialogInputHandler {
	public abstract void processReport(DMRActionType actionType);
	
	public void onViewBtnClick() {
		this.processReport(DMRActionType.VIEW);
	}

	@Override
	public void onPrintBtnClick() {
		this.processReport(DMRActionType.PRINT);
	}

	@Override
	public void onExcelBtnClick() {
		this.processReport(DMRActionType.EXCEL);
	}

	@Override
	public void onPdfBtnClick() {
		this.processReport(DMRActionType.PDF);
	}
}
