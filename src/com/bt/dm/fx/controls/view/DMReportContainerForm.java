package com.bt.dm.fx.controls.view;

import java.util.List;
import java.util.Map;

import com.bt.dm.core.pubsub.PubSubEvent;
import com.bt.dm.fx.controls.button.FXButtonCmp;
import com.bt.dm.fx.controls.button.FXButtonCmp.FXButtonCmpBuilder;
import com.bt.dm.fx.controls.datatable.DMReportDataTable;
import com.bt.dm.fx.controls.events.DMEmailActionsEvent;
import com.bt.dm.fx.controls.events.DMReportActionsToolbarEvent;
import com.bt.dm.fx.controls.form.DMForm;
import com.bt.dm.fx.controls.form.DMForm.DMFormBuilder;
import com.bt.dm.fx.controls.labels.FXMaterialDesignIcon;
import com.bt.dm.fx.controls.labels.FXMaterialDesignIcon.FXMaterialDesignIconBuilder;
import com.bt.dm.fx.controls.table.model.DMStaticTableColumnModel;
import com.bt.dm.fx.controls.utils.SizeHelper;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class DMReportContainerForm extends DMView implements DMReportActionsToolbarEvent {
	private DMReportView reportView;
	private DMReportDataTable reportDataTable;
	private List<DMStaticTableColumnModel> columnModels;
	private Pane reportFormView;
	private Pane reportTableView;
	private Map<String, String> filterColumnsMap;
	private DMEmailActionsEvent emailActionsEvent;

	public DMReportContainerForm(DMReportView reportView, List<DMStaticTableColumnModel> columnModels,
			DMEmailActionsEvent emailActionsEvent) {
		this.reportView = reportView;
		this.columnModels = columnModels;
		this.emailActionsEvent = emailActionsEvent;
	}

	public DMReportContainerForm filterColumnsMap(Map<String, String> filterColumnsMap) {
		this.filterColumnsMap = filterColumnsMap;
		return this;
	}

	@Override
	public Pane getView() {
		return new DMForm(new DMFormBuilder(this.getFormView()));
	}

	private Pane getFormView() {
		this.reportDataTable = new DMReportDataTable(this.columnModels, this, this.emailActionsEvent)
				.filterColumnsMap(filterColumnsMap);

		reportFormView = this.reportView.getView();
		reportTableView = reportDataTable.getView();

		VBox formViewPanel = new VBox(10);
		formViewPanel.getChildren().addAll(reportFormView, this.getButtonsPanel());

		StackPane container = new StackPane(formViewPanel, reportTableView);
		container.setPrefWidth(SizeHelper.FORM_PANEL_FULL_VIEW_SIZE.getWidth() - 15);

		VBox box = new VBox(10);
		box.getChildren().addAll(container);

		this.showReportTableView(false);

		return box;
	}

	private Pane getButtonsPanel() {
		FXButtonCmp viewButton = new FXButtonCmp(new FXButtonCmpBuilder().text("dmReportView.buttons.view")
				.className("styled-secondary-button")
				.materialIcon(new FXMaterialDesignIcon(new FXMaterialDesignIconBuilder(MaterialDesignIcon.FILE)))
				.eventHandler(event -> {
					this.onViewBtnClick();
				}));

		FXButtonCmp saveButton = new FXButtonCmp(new FXButtonCmpBuilder().text("dmReportView.buttons.save")
				.className("styled-secondary-button")
				.materialIcon(new FXMaterialDesignIcon(new FXMaterialDesignIconBuilder(MaterialDesignIcon.TABLE)))
				.eventHandler(event -> {
					this.showTableData();
				}));

		FXButtonCmp cancelButton = new FXButtonCmp(new FXButtonCmpBuilder().text("dmform.toolbar.button.cancel")
				.className("styled-secondary-button")
				.materialIcon(new FXMaterialDesignIcon(new FXMaterialDesignIconBuilder(MaterialDesignIcon.CLOSE)))
				.eventHandler(event -> {
					PubSubEvent.getInstance().publish("goToHome", null);
				}));

		HBox box = new HBox(10);
		box.getChildren().addAll(viewButton, saveButton, cancelButton);

		return box;
	}

	private void showReportTableView(boolean show) {
		reportTableView.setVisible(show);
		reportFormView.setVisible(!show);
	}

	private void showTableData() {
		this.showReportTableView(true);
		this.reportDataTable.loadData(this.reportView.getReportData());
	}

	@Override
	public void onViewBtnClick() {
		this.reportView.onViewBtnClick();
	}

	@Override
	public void onCancelBtnClick() {
		this.reportDataTable.loadData(null);
		this.showReportTableView(false);
	}

	@Override
	public void onPrintBtnClick() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onExcelBtnClick() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onPdfBtnClick() {
		// TODO Auto-generated method stub
	}
}
