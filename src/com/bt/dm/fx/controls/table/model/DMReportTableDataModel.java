package com.bt.dm.fx.controls.table.model;

import java.util.List;

import com.bt.dm.core.model.DMModel;
import com.bt.dm.core.settings.model.EmailSettingsModel;

public class DMReportTableDataModel implements DMModel {

	private static final long serialVersionUID = 1L;
	private List<List<DMStaticTableDataModel>> dataModels;
	private List<DMStaticTableColumnFooterModel> footerModels;
	private List<EmailSettingsModel> emailSettingsData;

	public DMReportTableDataModel(List<List<DMStaticTableDataModel>> dataModels,
			List<DMStaticTableColumnFooterModel> footerModels) {
		super();
		this.dataModels = dataModels;
		this.footerModels = footerModels;
	}

	public DMReportTableDataModel emailSettingsData(List<EmailSettingsModel> emailSettingsData) {
		this.emailSettingsData = emailSettingsData;
		return this;
	}

	public List<List<DMStaticTableDataModel>> getDataModels() {
		return dataModels;
	}

	public List<DMStaticTableColumnFooterModel> getFooterModels() {
		return footerModels;
	}

	public List<EmailSettingsModel> getEmailSettingsData() {
		return emailSettingsData;
	}
}
