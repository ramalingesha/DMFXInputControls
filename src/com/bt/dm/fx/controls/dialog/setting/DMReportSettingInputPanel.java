package com.bt.dm.fx.controls.dialog.setting;

import com.bt.dm.core.reportsetting.model.ReportSettingDataModel;
import com.bt.dm.core.reportsetting.service.ReportSettingJsonService;
import com.bt.dm.core.settings.constants.ReportBorderTypeEnum;
import com.bt.dm.fx.controls.DMLabelBuilder;
import com.bt.dm.fx.controls.labels.FXLabelCmp;
import com.bt.dm.fx.controls.labels.FXRadioButtonCmp;
import com.bt.dm.fx.controls.labels.FXRadioButtonCmp.FXRadioButtonCmpBuilder;
import com.bt.dm.fx.controls.utils.ControlSize;
import com.bt.dm.fx.controls.utils.SizeHelper;

import javafx.scene.control.Separator;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * @author Ramalingesha ML
 *
 *         Created on Oct 7, 2022 08:12:03 AM
 */
public class DMReportSettingInputPanel extends VBox {
	private ReportBorderTypeEnum reportBorderType;
	private FXRadioButtonCmp noBorderTypeCmp;
	private FXRadioButtonCmp pageBorderTypeCmp;
	private FXRadioButtonCmp columnBorderTypeCmp;

	public DMReportSettingInputPanel(String reportName) {
		this.createAndShowGUI();
		this.initSettings(reportName);
		this.registerEvents();
	}

	private void createAndShowGUI() {
		ToggleGroup paymentReportGroup = new ToggleGroup();

		noBorderTypeCmp = new FXRadioButtonCmp(new FXRadioButtonCmpBuilder().group(paymentReportGroup)
				.label("reportSetting.border.noBorder").classNames(new String[] { "english-font" }));
		pageBorderTypeCmp = new FXRadioButtonCmp(new FXRadioButtonCmpBuilder().group(paymentReportGroup)
				.label("reportSetting.border.pageBorder").classNames(new String[] { "english-font" }));
		columnBorderTypeCmp = new FXRadioButtonCmp(new FXRadioButtonCmpBuilder().group(paymentReportGroup)
				.label("reportSetting.border.columnBorder").classNames(new String[] { "english-font" }));

		FlowPane flowPane = new FlowPane(10, 10);
		flowPane.getChildren().addAll(noBorderTypeCmp, pageBorderTypeCmp, columnBorderTypeCmp);

		FXLabelCmp header = new FXLabelCmp(new DMLabelBuilder().label("reportSetting.border.settingHeader").h3(true)
				.classNames(new String[] { "english-font" }));

		Region spacer = new Region();
		spacer.setPrefHeight(5);

		VBox headerBox = new VBox(5);
		headerBox.getChildren().addAll(header, new Separator(), spacer);

		this.getChildren().addAll(headerBox, flowPane);
	}

	public void initSettings(String reportName) {
		ReportSettingDataModel settingModel = ReportSettingJsonService.getInstance().getReportSettings(reportName);
		
		this.initSettings(settingModel);
	}

	public void initSettings(ReportSettingDataModel settingModel) {
		if(settingModel == null) {
			return;
		}
		
		this.reportBorderType = ReportBorderTypeEnum.valueOf(settingModel.getBorderType());
		
		if(this.reportBorderType == null) {
			return;
		}

		this.selectBorderType();
	}

	private void selectBorderType() {
		switch (this.reportBorderType) {
		case NO_BORDER:
			noBorderTypeCmp.setSelected(true);
			break;
		case PAGE_BORDER:
			pageBorderTypeCmp.setSelected(true);
			break;
		case COLUMN_BORDER:
			columnBorderTypeCmp.setSelected(true);
			break;

		default:
			break;
		}
	}

	private void registerEvents() {
		this.noBorderTypeCmp.selectedProperty().addListener((obj, oldValue, newValue) -> {
			if (newValue) {
				reportBorderType = ReportBorderTypeEnum.NO_BORDER;
			}
		});
		this.pageBorderTypeCmp.selectedProperty().addListener((obj, oldValue, newValue) -> {
			if (newValue) {
				reportBorderType = ReportBorderTypeEnum.PAGE_BORDER;
			}
		});
		this.columnBorderTypeCmp.selectedProperty().addListener((obj, oldValue, newValue) -> {
			if (newValue) {
				reportBorderType = ReportBorderTypeEnum.COLUMN_BORDER;
			}
		});
	}

	public ReportSettingDataModel getSettingInputModel() {
		ReportSettingDataModel settingModel = new ReportSettingDataModel();

		settingModel.setBorderType(reportBorderType.getValue());

		return settingModel;
	}

	public ControlSize getPanelSize() {
		return new ControlSize(SizeHelper.INPUT_CONTROL_SIZE.getWidth() * 2.1, 250);
	}
}
