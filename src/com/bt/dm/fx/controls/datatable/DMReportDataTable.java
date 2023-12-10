package com.bt.dm.fx.controls.datatable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.bt.dm.core.i18n.DMMessageLocalizer;
import com.bt.dm.core.model.excel.ExcelColumnDataType;
import com.bt.dm.core.model.excel.ExcelTableColumnDataModel;
import com.bt.dm.core.model.excel.ExcelTableSettingModel;
import com.bt.dm.core.pubsub.PubSubEvent;
import com.bt.dm.core.pubsub.PubSubEventDataModel;
import com.bt.dm.core.pubsub.PubSubEventHandler;
import com.bt.dm.core.pubsub.settings.SettingPubSubModel;
import com.bt.dm.core.service.excel.ExcelExportService;
import com.bt.dm.core.settings.model.DMEmailDataModel;
import com.bt.dm.core.settings.model.EmailSettingsModel;
import com.bt.dm.core.utils.DMCollectionUtils;
import com.bt.dm.fx.controls.DMLabelBuilder;
import com.bt.dm.fx.controls.button.FXButtonCmp;
import com.bt.dm.fx.controls.button.FXButtonCmp.FXButtonCmpBuilder;
import com.bt.dm.fx.controls.dialog.FXMessageBox;
import com.bt.dm.fx.controls.dialog.FXMessageBox.FXMessageBoxBuilder;
import com.bt.dm.fx.controls.email.DMEmailCreatroView;
import com.bt.dm.fx.controls.events.DMEmailActionsEvent;
import com.bt.dm.fx.controls.events.DMReportActionsToolbarEvent;
import com.bt.dm.fx.controls.labels.FXCheckBoxCmp;
import com.bt.dm.fx.controls.labels.FXFontAwesomeIcon;
import com.bt.dm.fx.controls.labels.FXFontAwesomeIcon.FXFontAwesomeIconBuilder;
import com.bt.dm.fx.controls.labels.FXMaterialDesignIcon;
import com.bt.dm.fx.controls.labels.FXMaterialDesignIcon.FXMaterialDesignIconBuilder;
import com.bt.dm.fx.controls.table.DMStaticTableView;
import com.bt.dm.fx.controls.table.model.DMReportTableDataModel;
import com.bt.dm.fx.controls.table.model.DMStaticTableColumnModel;
import com.bt.dm.fx.controls.table.model.DMStaticTableDataModel;
import com.bt.dm.fx.controls.theme.ControlsTheme;
import com.bt.dm.fx.controls.toolbar.DMToolBar;
import com.bt.dm.fx.controls.toolbar.DMToolBar.DMToolBarBuilder;
import com.bt.dm.fx.controls.utils.ControlSize;
import com.bt.dm.fx.controls.utils.SizeHelper;
import com.bt.dm.fx.controls.utils.UIHelper;
import com.bt.dm.fx.controls.view.DMView;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * @author Ramalingesha ML
 *
 *         Created on Sep 26, 2023 11:52:03 PM
 */
public class DMReportDataTable extends DMView implements DMEmailActionsEvent {
	private DMReportActionsToolbarEvent toolbarEvent;
	private List<DMStaticTableColumnModel> columnModels;
	private DMStaticTableView table;
	private VBox root;
	private Map<String, String> filterColumnsMap;
	private DMReportTableDataModel reportTableDataModel;
	private List<String> removedFilterColumns;
	private ExcelExportService excelExportService;
	private DMEmailCreatroView emailCreatorView;
	private DMEmailActionsEvent emailActionsEvent;	

	public DMReportDataTable(List<DMStaticTableColumnModel> columnModels, DMReportActionsToolbarEvent toolbarEvent,
			DMEmailActionsEvent emailActionsEvent) {
		this.columnModels = columnModels;
		this.toolbarEvent = toolbarEvent;
		this.emailActionsEvent = emailActionsEvent;
	}

	public DMReportDataTable filterColumnsMap(Map<String, String> filterColumnsMap) {
		this.filterColumnsMap = filterColumnsMap;
		return this;
	}

	@Override
	public Pane getView() {
		this.excelExportService = ExcelExportService.getInstance();

		this.readSettings();
		double tableHeight = SizeHelper.TABLE_PANEL_FULL_VIEW_SIZE.getHeight() - 50;

		table = new DMStaticTableView(this.getFilteredColumnModels(), tableHeight).enableScrollBar(true);

		root = new VBox();
		root.getChildren().addAll(this.getToolbar(), table.getView());

		this.addRootThemeStyleSheet();
		this.subscribeOnThemeChange();

		return root;
	}

	public void loadData(DMReportTableDataModel reportTableDataModel) {
		this.reportTableDataModel = reportTableDataModel;

		if (reportTableDataModel == null) {
			this.table.renderTableData(this.getFilteredColumnModels(), null, null);
		} else {
			this.table.renderTableData(this.getFilteredColumnModels(), reportTableDataModel.getDataModels(),
					reportTableDataModel.getFooterModels());
		}
	}

	private void readSettings() {
		// TODO:: Read removed columns from database
		this.removedFilterColumns = new ArrayList<>();
	}

	private List<DMStaticTableColumnModel> getFilteredColumnModels() {
		if (DMCollectionUtils.isEmptyOrNull(removedFilterColumns)) {
			return this.columnModels;
		}

		return this.columnModels.stream()
				.filter(columnModel -> removedFilterColumns.indexOf(columnModel.getColumnId()) == -1)
				.collect(Collectors.toList());
	}

	private Pane getToolbar() {
		FXButtonCmp columnSettingsButton = new FXButtonCmp(new FXButtonCmpBuilder()
				.text("dmReportView.buttons.columnSelection").className("styled-secondary-button")
				.faIcon(new FXFontAwesomeIcon(new FXFontAwesomeIconBuilder(FontAwesomeIcon.GEARS)))
				.eventHandler(event -> this.showColumnSelectionDialog()));

		FXButtonCmp printButton = new FXButtonCmp(new FXButtonCmpBuilder().text("dmReportView.buttons.print")
				.className("styled-secondary-button")
				.materialIcon(new FXMaterialDesignIcon(new FXMaterialDesignIconBuilder(MaterialDesignIcon.PRINTER)))
				.eventHandler(event -> toolbarEvent.onPrintBtnClick()));

		FXButtonCmp cancelButton = new FXButtonCmp(new FXButtonCmpBuilder().text("dmform.toolbar.button.close")
				.className("styled-secondary-button")
				.materialIcon(new FXMaterialDesignIcon(new FXMaterialDesignIconBuilder(MaterialDesignIcon.CLOSE)))
				.eventHandler(event -> toolbarEvent.onCancelBtnClick()));

		FXButtonCmp excelButton = new FXButtonCmp(new FXButtonCmpBuilder().text("dmReportView.buttons.excel")
				.className("styled-secondary-button")
				.materialIcon(new FXMaterialDesignIcon(new FXMaterialDesignIconBuilder(MaterialDesignIcon.FILE_EXCEL)))
				.eventHandler(event -> this.exportToExcel()));

		FXButtonCmp pdfButton = new FXButtonCmp(new FXButtonCmpBuilder().text("dmReportView.buttons.pdf")
				.className("styled-secondary-button")
				.materialIcon(new FXMaterialDesignIcon(new FXMaterialDesignIconBuilder(MaterialDesignIcon.FILE_PDF)))
				.eventHandler(event -> toolbarEvent.onPdfBtnClick()));
		FXButtonCmp emailButton = new FXButtonCmp(new FXButtonCmpBuilder().text("dmReportView.buttons.email")
				.className("styled-secondary-button")
				.materialIcon(new FXMaterialDesignIcon(new FXMaterialDesignIconBuilder(MaterialDesignIcon.EMAIL)))
				.eventHandler(event -> this.showSendEmailDialog()));

		ObservableList<Node> buttons = FXCollections.observableArrayList(columnSettingsButton, printButton, excelButton,
				pdfButton, emailButton, cancelButton);

		DMToolBar toolBar = new DMToolBar(new DMToolBarBuilder().hideDefaultButtons(true).toolBarControls(buttons));

		HBox box = new HBox(toolBar);
		box.setAlignment(Pos.CENTER_RIGHT);

		return box;
	}

	private boolean isDataAvailable() {
		List<DMStaticTableColumnModel> columns = this.getFilteredColumnModels();

		if (this.reportTableDataModel == null
				|| DMCollectionUtils.isEmptyOrNull(this.reportTableDataModel.getDataModels())
				|| DMCollectionUtils.isEmptyOrNull(columns)) {
			FXMessageBox.getInstance()
					.show(new FXMessageBoxBuilder("app.report.noDataAvailable").alertType(AlertType.ERROR));
			return false;
		}

		return true;
	}

	private void exportToExcel() {
		if (!this.isDataAvailable()) {
			return;
		}

		File file = this.getDestinationFileName("Save Report as Excel",
				new ExtensionFilter("Excel", "*.xls", "*.xlsx"));

		if (file == null) {
			return;
		}

		this.constructExcelFile(file);
	}

	private void constructExcelFile(File file) {
		List<ExcelTableColumnDataModel> columnModels = new ArrayList<>();
		List<DMStaticTableColumnModel> columns = this.getFilteredColumnModels();

		for (DMStaticTableColumnModel column : columns) {
			ExcelColumnDataType dataType = column.getDataType();

			ExcelTableColumnDataModel columnDataModel = new ExcelTableColumnDataModel(column.getColumnId(),
					DMMessageLocalizer.getLabel(column.getColumnHeader())).dataType(dataType);
			columnModels.add(columnDataModel);
		}

		List<Map<String, String>> rowValuesList = new ArrayList<>();

		for (List<DMStaticTableDataModel> rowData : this.reportTableDataModel.getDataModels()) {
			Map<String, String> valuesMap = rowData.stream()
					.collect(Collectors.toMap(DMStaticTableDataModel::getColumnId, DMStaticTableDataModel::getText));

			rowValuesList.add(valuesMap);
		}

		ExcelTableSettingModel excelTableSettingModel = new ExcelTableSettingModel(file, columnModels, rowValuesList);

		this.excelExportService.export(excelTableSettingModel);
	}

	private File getDestinationFileName(String title, ExtensionFilter extensionFilter) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle(title);
		fileChooser.getExtensionFilters().add(extensionFilter);

		return fileChooser.showSaveDialog(null);
	}

	private void showSendEmailDialog() {
		if (!isDataAvailable()) {
			return;
		}

		if (this.emailCreatorView == null) {
			emailCreatorView = new DMEmailCreatroView(this);
		}

		ControlSize size = this.emailCreatorView.getSize();

		SettingPubSubModel popupSettingModel = new SettingPubSubModel(
				DMMessageLocalizer.getLabel("dmReportView.buttons.email"), this.emailCreatorView.getView(),
				size.getWidth(), size.getHeight());

		PubSubEvent.getInstance().publish("openModalWindow", popupSettingModel);
	}

	private void showColumnSelectionDialog() {
		FXButtonCmp applyButton = new FXButtonCmp(new FXButtonCmpBuilder().text("dmReportView.buttons.apply")
				.className("styled-primary-button").eventHandler(event -> this.applyColumnSettings()));

		Region spacer = UIHelper.getEmptySpace(5, true);
		VBox.setVgrow(spacer, Priority.ALWAYS);

		double width = SizeHelper.FORM_PANEL_FULL_VIEW_SIZE.getWidth() * 0.7;
		double height = SizeHelper.FORM_PANEL_FULL_VIEW_SIZE.getHeight() * 0.5;

		VBox contentPanel = new VBox(10);
		contentPanel.setPadding(new Insets(15));
		contentPanel.setPrefSize(width, height - 60);
		contentPanel.getChildren().addAll(this.getColumnsPanel(), spacer, applyButton);

		SettingPubSubModel popupSettingModel = new SettingPubSubModel(
				DMMessageLocalizer.getLabel("dmReportView.buttons.columnSelection"), contentPanel, width, height);

		PubSubEvent.getInstance().publish("openModalWindow", popupSettingModel);
	}

	private Pane getColumnsPanel() {
		FlowPane selectionPanel = new FlowPane(10, 10);

		this.filterColumnsMap.forEach((key, value) -> {
			FXCheckBoxCmp columnCheckCmp = new FXCheckBoxCmp(new DMLabelBuilder().label(value));
			columnCheckCmp.setSelected(true);

			selectionPanel.getChildren().add(columnCheckCmp);

			columnCheckCmp.setOnAction(event -> {
				if (columnCheckCmp.isSelected()) {
					this.removedFilterColumns.removeIf(item -> item.equals(key));
				} else {
					this.removedFilterColumns.add(key);
				}
			});
		});

		return selectionPanel;
	}

	private void applyColumnSettings() {
		this.table.renderTableData(this.getFilteredColumnModels(), this.reportTableDataModel.getDataModels(),
				this.reportTableDataModel.getFooterModels());
		PubSubEvent.getInstance().publish("closeModalWindow", null);
	}

	private void subscribeOnThemeChange() {
		PubSubEventHandler handler = new PubSubEventHandler() {

			@Override
			public void onPubSubData(PubSubEventDataModel data) {
				addRootThemeStyleSheet();
			}
		};
		PubSubEvent.getInstance().subscribe("THEME", handler);
	}

	private void addRootThemeStyleSheet() {
		this.root.getStylesheets().clear();
		this.root.getStylesheets()
				.add(getClass().getResource("/com/bt/dm/fx/controls/table/Table.css").toExternalForm());
		this.root.getStylesheets().add(getClass()
				.getResource(ControlsTheme.getThemeCssFileName("/com/bt/dm/fx/controls/table/Table")).toExternalForm());
	}

	@Override
	public List<EmailSettingsModel> getEmailIdsList() {
		return this.emailActionsEvent.getEmailIdsList();
	}

	@Override
	public void processSendEmail(DMEmailDataModel emailDataModel) {
		try {
			File file = File.createTempFile("data", ".xlsx");
			this.constructExcelFile(file);
			emailDataModel.attachment(file);
			this.emailActionsEvent.processSendEmail(emailDataModel);

			file.deleteOnExit();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
