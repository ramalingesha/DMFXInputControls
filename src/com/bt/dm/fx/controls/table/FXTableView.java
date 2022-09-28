/**
 * 
 */
package com.bt.dm.fx.controls.table;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import com.bt.dm.core.pubsub.PubSubEvent;
import com.bt.dm.core.pubsub.PubSubEventDataModel;
import com.bt.dm.core.pubsub.PubSubEventHandler;
import com.bt.dm.core.utils.DMCollectionUtils;
import com.bt.dm.fx.controls.DMLabelBuilder;
import com.bt.dm.fx.controls.dialog.FXMessageBox;
import com.bt.dm.fx.controls.dialog.FXMessageBox.FXMessageBoxBuilder;
import com.bt.dm.fx.controls.dialog.search.DMSearchDialogCmp;
import com.bt.dm.fx.controls.dialog.search.DMSearchDialogInputHandler;
import com.bt.dm.fx.controls.events.IconClickEvent;
import com.bt.dm.fx.controls.labels.FXFontAwesomeIcon;
import com.bt.dm.fx.controls.labels.FXFontAwesomeIcon.FXFontAwesomeIconBuilder;
import com.bt.dm.fx.controls.labels.FXLabelCmp;
import com.bt.dm.fx.controls.labels.FXMaterialDesignIcon;
import com.bt.dm.fx.controls.labels.FXMaterialDesignIcon.FXMaterialDesignIconBuilder;
import com.bt.dm.fx.controls.search.DMSearchCmp;
import com.bt.dm.fx.controls.table.FXTable.FXTableBuilder;
import com.bt.dm.fx.controls.table.event.TableCellClickEvent;
import com.bt.dm.fx.controls.table.renderer.CheckBoxCellRenderer;
import com.bt.dm.fx.controls.table.renderer.IconCellRenderer;
import com.bt.dm.fx.controls.theme.ControlsTheme;
import com.bt.dm.fx.controls.utils.UIHelper;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;

/**
 * @author Ramalingesha ML
 *
 *         Created on Dec 30, 2020 7:33:32 PM
 */
public class FXTableView<S extends DMTableModel> extends Pane {
	public static class FXTableViewBuilder<S extends DMTableModel> {
		private ObservableList<FxTableColumnSizeMapper<S>> columnSizeMapperList;
		private ObservableList<S> data;
		private String header;
		private ObservableList<Node> tableHeaderControls;
		private boolean miniTable;
		private boolean enableSearch;
		private boolean enableExcelExport;
		private boolean enablePdfExport;
		private boolean enableDataReport;
		private Double width;
		private Double height;
		private DMTableActions<S> tableActions = new DMTableActions<S>();
		private boolean showEditIcon = true;
		private boolean showDeleteIcon = true;
		private boolean equalColumnWidth = false;
		private boolean showTableToolbar = true;
		private boolean showRowCheckColumn = false;
		private Double checkBoxColumnWidth;
		private Double editIconColumnWidth;
		private Double deleteIconColumnWidth;
		private DMSearchDialogInputHandler searchDialogInputHandler;

		public FXTableViewBuilder(
				ObservableList<FxTableColumnSizeMapper<S>> columnSizeMapperList) {
			this.columnSizeMapperList = columnSizeMapperList;
		}

		public FXTableViewBuilder<S> columnSizeMapperList(
				ObservableList<FxTableColumnSizeMapper<S>> columnSizeMapperList) {
			this.columnSizeMapperList = columnSizeMapperList;
			return this;
		}

		public FXTableViewBuilder<S> data(ObservableList<S> data) {
			this.data = data;
			return this;
		}

		public FXTableViewBuilder<S> width(double width) {
			this.width = width;
			return this;
		}

		public FXTableViewBuilder<S> height(double height) {
			this.height = height;
			return this;
		}

		public FXTableViewBuilder<S> header(String header) {
			this.header = header;
			return this;
		}

		public FXTableViewBuilder<S> tableHeaderControls(
				ObservableList<Node> tableHeaderControls) {
			this.tableHeaderControls = tableHeaderControls;
			return this;
		}

		public FXTableViewBuilder<S> enableSearch(boolean enableSearch) {
			this.enableSearch = enableSearch;
			return this;
		}

		public FXTableViewBuilder<S> enableExcelExport(boolean enableExcelExport) {
			this.enableExcelExport = enableExcelExport;
			return this;
		}

		public FXTableViewBuilder<S> enablePdfExport(boolean enablePdfExport) {
			this.enablePdfExport = enablePdfExport;
			return this;
		}

		public FXTableViewBuilder<S> enableDataReport(boolean enableDataReport) {
			this.enableDataReport = enableDataReport;
			return this;
		}

		public FXTableViewBuilder<S> miniTable(boolean miniTable) {
			this.miniTable = miniTable;
			return this;
		}

		public FXTableViewBuilder<S> tableActions(DMTableActions<S> tableActions) {
			this.tableActions = tableActions;
			return this;
		}

		public FXTableViewBuilder<S> showEditIcon(boolean showEditIcon) {
			this.showEditIcon = showEditIcon;
			return this;
		}

		public FXTableViewBuilder<S> showDeleteIcon(boolean showDeleteIcon) {
			this.showDeleteIcon = showDeleteIcon;
			return this;
		}

		public FXTableViewBuilder<S> equalColumnWidth(boolean equalColumnWidth) {
			this.equalColumnWidth = equalColumnWidth;
			return this;
		}

		public FXTableViewBuilder<S> showTableToolbar(boolean showTableToolbar) {
			this.showTableToolbar = showTableToolbar;
			return this;
		}

		public FXTableViewBuilder<S> showRowCheckColumn(
				boolean showRowCheckColumn) {
			this.showRowCheckColumn = showRowCheckColumn;
			return this;
		}

		public FXTableViewBuilder<S> checkBoxColumnWidth(
				Double checkBoxColumnWidth) {
			this.checkBoxColumnWidth = checkBoxColumnWidth;
			return this;
		}

		public FXTableViewBuilder<S> deleteIconColumnWidth(
				Double deleteIconColumnWidth) {
			this.deleteIconColumnWidth = deleteIconColumnWidth;
			return this;
		}

		public FXTableViewBuilder<S> editIconColumnWidth(
				Double editIconColumnWidth) {
			this.editIconColumnWidth = editIconColumnWidth;
			return this;
		}
		
		public FXTableViewBuilder<S> searchDialogInputHandler(
				DMSearchDialogInputHandler searchDialogInputHandler) {
			this.searchDialogInputHandler = searchDialogInputHandler;
			return this;
		}
	}

	private FXTableViewBuilder<S> builder;
	private FXTable<S> table;
	private PubSubEventHandler handler;

	public FXTableView(FXTableViewBuilder<S> builder) {
		this.builder = builder;
		this.createComponent();
	}

	private void createComponent() {
		this.addRootThemeStyleSheet();

		this.table = new FXTable<S>(new FXTableBuilder<S>(
				this.builder.columnSizeMapperList).data(this.builder.data)
				.miniTable(this.builder.miniTable)
				.tableActions(this.builder.tableActions)
				.showEditIcon(this.builder.showEditIcon)
				.showDeleteIcon(this.builder.showDeleteIcon)
				.showRowCheck(this.builder.showRowCheckColumn)
				.equalColumnWidth(this.builder.equalColumnWidth)
				.checkBoxColumnWidth(this.builder.checkBoxColumnWidth)
				.editIconColumnWidth(this.builder.editIconColumnWidth)
				.deleteIconColumnWidth(this.builder.deleteIconColumnWidth));

		if (this.builder.height != null) {
			this.table.setPrefHeight(this.builder.height);
		}

		if (this.builder.width != null) {
			this.setTableWidth(this.builder.width);
		}

		VBox root = new VBox();
		root.getStyleClass().add("dm-table-pane");
		root.setSpacing(10);

		// Table header pane
		if (this.builder.showTableToolbar
				&& (this.builder.header != null || this.builder.enableSearch
						|| this.builder.enableDataReport
						|| this.builder.enableExcelExport
						|| this.builder.enablePdfExport || this.builder.tableHeaderControls != null)) {
			root.getChildren().add(this.getTableHeaderPane());
		}

		// Table
		ScrollPane tableScrollPane = new ScrollPane(this.table);
		root.getChildren().add(tableScrollPane);

		this.getChildren().add(root);
		this.subscribeOnThemeChange();
	}

	public void setTableWidth(double width) {
		this.table.setPrefWidth(width);
		this.table.resizeColumns();
	}

	public void setTableHeight(double height) {
		this.table.setPrefHeight(height);
	}

	public void addRecords(ObservableList<S> records) {
		this.table.addRecords(records);
	}

	public void clearTableRecords() {
		this.table.clearTableRecords();
	}

	public void addTableColumns(
			ObservableList<FxTableColumnSizeMapper<S>> columnSizeMapperList) {
		this.table.addTableColumns(columnSizeMapperList);
	}

	public void updateColumnHeader(String newHeader, boolean readFromLocale,
			String propertyName) {
		this.table.updateColumnHeader(newHeader, readFromLocale, propertyName);
	}

	public void clearTableColumns() {
		this.table.clearTableColumns();
	}

	public void setTableEditable(boolean editable) {
		this.table.setEditable(editable);
	}

	public S getTableRecord(int rowIndex) {
		return rowIndex >= 0 && rowIndex < this.table.getItems().size() ? this.table
				.getItems().get(rowIndex) : null;
	}

	public ObservableList<S> getTableRecords() {
		return this.table.getItems();
	}

	public int getNoOfRecords() {
		return this.table.getItems().size();
	}

	private Pane getTableHeaderPane() {
		BorderPane headerPane = new BorderPane();
		headerPane.getStyleClass().add("toolbar-pane");
		headerPane.setPadding(new Insets(10));
		// Header
		if (this.builder.header != null) {
			FXLabelCmp title = new FXLabelCmp(new DMLabelBuilder().label(
					this.builder.header).h3(true));
			BorderPane.setAlignment(title, Pos.CENTER);
			headerPane.setLeft(title);
		}

		HBox box = new HBox();
		box.setAlignment(Pos.CENTER);
		box.setSpacing(5);

		if (builder.tableHeaderControls != null) {
			box.getChildren().addAll(builder.tableHeaderControls);
		}

		if (builder.searchDialogInputHandler != null) {
			DMSearchDialogCmp searchDialogCmp = new DMSearchDialogCmp(builder.searchDialogInputHandler, true);

			box.getChildren().addAll(searchDialogCmp);
		}
		
		if (builder.enableSearch) {
			DMSearchCmp searchCmp = new DMSearchCmp();
			box.getChildren().add(searchCmp);
		}

		if (builder.enableDataReport) {
			FXMaterialDesignIcon reportIconButton = this
					.createDataExportIcon(MaterialDesignIcon.FILE);
			box.getChildren().add(reportIconButton);
		}

		if (builder.enableExcelExport) {
			FXMaterialDesignIcon excelIconButton = this
					.createDataExportIcon(MaterialDesignIcon.FILE_EXCEL);
			box.getChildren().add(excelIconButton);
		}

		if (builder.enablePdfExport) {
			FXMaterialDesignIcon pdfIconButton = this
					.createDataExportIcon(MaterialDesignIcon.FILE_PDF);

			box.getChildren().addAll(pdfIconButton);
		}
		
		if (builder.miniTable) {
			FXFontAwesomeIcon maximizeWindowIcon = new FXFontAwesomeIcon(
					new FXFontAwesomeIconBuilder(FontAwesomeIcon.EXTERNAL_LINK)
							.size("28px").className("maximize-icon")
							.faIconClickEvent(new IconClickEvent() {

								@Override
								public void onClick(Event event) {
									builder.tableActions.onExpandClick(event);
								}
							}));
			box.getChildren().add(maximizeWindowIcon);
		}

		headerPane.setRight(box);

		VBox headerBox = new VBox(headerPane, new Separator());

		return headerBox;
	}

	private FXMaterialDesignIcon createDataExportIcon(MaterialDesignIcon icon) {
		FXMaterialDesignIcon iconButton = new FXMaterialDesignIcon(
				new FXMaterialDesignIconBuilder(icon).size("28px")
						.iconClickEvent(new IconClickEvent() {

							@Override
							public void onClick(Event event) {
								onDataExportIconClick(icon);
							}
						}));
		iconButton.getStyleClass().add("export-icon");

		return iconButton;
	}

	private void onDataExportIconClick(MaterialDesignIcon icon) {
		switch (icon) {
		case FILE:
			this.builder.tableActions.generateReport();
			break;
		case FILE_EXCEL:
			this.builder.tableActions.exportToExcel();
			break;
		case FILE_PDF:
			this.builder.tableActions.exportToPdf();
			break;

		default:
			break;
		}
	}

	private void subscribeOnThemeChange() {
		handler = new PubSubEventHandler() {

			@Override
			public void onPubSubData(PubSubEventDataModel data) {
				addRootThemeStyleSheet();
			}
		};
		PubSubEvent.getInstance().subscribe("THEME", handler);
	}

	private void addRootThemeStyleSheet() {
		this.getStylesheets().clear();
		this.getStylesheets().add(
				getClass().getResource("Table.css").toExternalForm());
		this.getStylesheets().add(
				getClass().getResource(
						ControlsTheme.getThemeCssFileName("Table"))
						.toExternalForm());
	}
}

class FXTable<S extends DMTableModel> extends TableView<S> {
	public static class FXTableBuilder<S extends DMTableModel> {
		private ObservableList<FxTableColumnSizeMapper<S>> columnSizeMapperList;
		private ObservableList<S> data;
		private boolean miniTable;
		private boolean showEditIcon;
		private boolean showDeleteIcon;
		private boolean showRowCheck;
		private DMTableActions<S> tableActions;
		private boolean equalColumnWidth = false;
		private Double checkBoxColumnWidth;
		private Double editIconColumnWidth;
		private Double deleteIconColumnWidth;

		public FXTableBuilder(
				ObservableList<FxTableColumnSizeMapper<S>> columnSizeMapperList) {
			this.columnSizeMapperList = columnSizeMapperList;
		}

		public FXTableBuilder<S> data(ObservableList<S> data) {
			this.data = data;
			return this;
		}

		public FXTableBuilder<S> miniTable(boolean miniTable) {
			this.miniTable = miniTable;
			return this;
		}

		public FXTableBuilder<S> showEditIcon(boolean showEditIcon) {
			this.showEditIcon = showEditIcon;
			return this;
		}

		public FXTableBuilder<S> showDeleteIcon(boolean showDeleteIcon) {
			this.showDeleteIcon = showDeleteIcon;
			return this;
		}

		public FXTableBuilder<S> tableActions(DMTableActions<S> tableActions) {
			this.tableActions = tableActions;
			return this;
		}

		public FXTableBuilder<S> equalColumnWidth(boolean equalColumnWidth) {
			this.equalColumnWidth = equalColumnWidth;
			return this;
		}

		public FXTableBuilder<S> showRowCheck(boolean showRowCheck) {
			this.showRowCheck = showRowCheck;
			return this;
		}

		public FXTableBuilder<S> checkBoxColumnWidth(Double checkBoxColumnWidth) {
			this.checkBoxColumnWidth = checkBoxColumnWidth;
			return this;
		}

		public FXTableBuilder<S> deleteIconColumnWidth(
				Double deleteIconColumnWidth) {
			this.deleteIconColumnWidth = deleteIconColumnWidth;
			return this;
		}

		public FXTableBuilder<S> editIconColumnWidth(Double editIconColumnWidth) {
			this.editIconColumnWidth = editIconColumnWidth;
			return this;
		}
	}

	private FXTableBuilder<S> builder;
	private BooleanProperty selectAllCheckProperty;

	public FXTable(FXTableBuilder<S> builder) {
		this.builder = builder;
		this.createComponent();
	}

	private void createComponent() {
		// Set placeholder when no data available
		this.setPlaceholder(new FXLabelCmp(new DMLabelBuilder()
				.label("app.report.noDataAvailable")));

		// Show check column
		if (this.builder.showRowCheck) {
			this.selectAllCheckProperty = new SimpleBooleanProperty();

			this.setEditable(true);
			FXTableColumnModel<S, Boolean> selectColumn = new FXTableColumnModel<S, Boolean>(
					"", "select");

			selectColumn.setCellValueFactory(model -> model.getValue()
					.checkedProperty());

			selectColumn.setCellFactory(c -> new CheckBoxCellRenderer<S>((
					selectedRowIndex, value) -> {
				if (selectedRowIndex >= 0) {
					S record = this.getItems().get(selectedRowIndex);
					record.checkedProperty().setValue(value);

					if (selectAllCheckProperty.getValue() && !value) {
						selectAllCheckProperty.setValue(false);
					}

					this.builder.tableActions.onRowCheckBoxSelected(value,
							record);

				}
			}));

			CheckBox selectAllBox = new CheckBox();
			selectAllBox.selectedProperty().bindBidirectional(
					selectAllCheckProperty);
			selectAllBox.setFocusTraversable(false);
			selectAllBox.setOnAction(e -> {
				boolean selected = selectAllBox.isSelected();
				this.getItems().forEach(record -> {
					record.checkedProperty().set(selected);
				});

				this.builder.tableActions.onAllRowsCheckBoxClicked(selected,
						selected ? this.getItems() : null);
			});

			selectColumn.setGraphic(selectAllBox);

			double width = this.builder.checkBoxColumnWidth == null ? 1
					: this.builder.checkBoxColumnWidth;
			FxTableColumnSizeMapper<S> selectColumnWrapper = new FxTableColumnSizeMapper<S>(
					selectColumn, width);
			this.builder.columnSizeMapperList.add(0, selectColumnWrapper);
		}

		// Show edit icon
		if (this.builder.showEditIcon) {
			FXTableColumnModel<S, MaterialDesignIcon> editColumn = new FXTableColumnModel<S, MaterialDesignIcon>(
					null, "edit");
			editColumn.setCellFactory(c -> new IconCellRenderer<S>(
					MaterialDesignIcon.PENCIL, new TableCellClickEvent() {

						@Override
						public void onCellClick(int selectedRowIndex) {
							if (builder.tableActions != null) {
								int columnIndex = getColumns().indexOf(
										editColumn);
								builder.tableActions.onEditClick(
										getSelectionModel().getSelectedItem(),
										selectedRowIndex, columnIndex);
							}
						}
					}));

			double width = this.builder.editIconColumnWidth == null ? 0.07
					: this.builder.editIconColumnWidth;

			FxTableColumnSizeMapper<S> editColumnSizeMapper = new FxTableColumnSizeMapper<S>(
					editColumn, 0.05, true, width);
			this.builder.columnSizeMapperList.add(editColumnSizeMapper);
		}

		// Show delete icon
		if (this.builder.showDeleteIcon) {
			FXTableColumnModel<S, MaterialDesignIcon> deleteColumn = new FXTableColumnModel<S, MaterialDesignIcon>(
					null, "delete");
			deleteColumn.setCellFactory(column -> new IconCellRenderer<S>(
					MaterialDesignIcon.DELETE, new TableCellClickEvent() {

						@Override
						public void onCellClick(int selectedRowIndex) {
							FXMessageBox messageBox = FXMessageBox
									.getInstance();
							messageBox
									.show(new FXMessageBoxBuilder(
											"app.record.delete.confirm").title(
											"app.confirm").alertType(
											AlertType.CONFIRMATION))
									.filter(response -> response == UIHelper.ALERT_OK_BUTTON)
									.ifPresent(
											response -> {
												if (builder.tableActions != null) {
													int columnIndex = getColumns()
															.indexOf(
																	deleteColumn);
													builder.tableActions
															.onDeleteClick(
																	getSelectionModel()
																			.getSelectedItem(),
																	selectedRowIndex,
																	columnIndex);
												}
											});
						}
					}));

			double width = this.builder.deleteIconColumnWidth == null ? 0.07
					: this.builder.deleteIconColumnWidth;

			FxTableColumnSizeMapper<S> deleteColumnSizeMapper = new FxTableColumnSizeMapper<S>(
					deleteColumn, 0.05, true, width);
			this.builder.columnSizeMapperList.add(deleteColumnSizeMapper);
		}

		this.addTableColumns(this.builder.columnSizeMapperList);

		if (this.builder.data != null) {
			this.getItems().addAll(this.builder.data);
		}

		if (this.builder.equalColumnWidth) {
			this.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		}
	}

	public void addRecords(ObservableList<S> records) {
		this.getItems().addAll(records);
	}

	public void clearTableRecords() {
		this.getItems().clear();
	}

	public void addTableColumns(
			ObservableList<FxTableColumnSizeMapper<S>> columnSizeMapperList) {
		if (DMCollectionUtils.isEmptyOrNull(columnSizeMapperList)) {
			return;
		}
		columnSizeMapperList.stream().forEach(mapper -> {
			if (this.builder.miniTable && !mapper.isShowInMiniTable()) {
				return;
			}

			this.getColumns().add(mapper.getFxTableColumnModel());
		});
	}

	public void updateColumnHeader(String newHeader, boolean readFromLocale,
			String propertyName) {
		if (DMCollectionUtils.notEmptyOrNull(this.getColumns())) {
			for (TableColumn<S, ?> column : this.getColumns()) {
				if (column.getId().equalsIgnoreCase(propertyName)) {
					((FXTableColumnModel<S, ?>) column).updateHeader(newHeader,
							readFromLocale);
					break;
				}
			}
		}
	}

	public void clearTableColumns() {
		this.getColumns().clear();
	}

	public void resizeColumns() {
		this.builder.columnSizeMapperList
				.stream()
				.forEach(
						mapper -> {
							if ((this.builder.miniTable && mapper
									.getColWidthPercentageInMiniTable() == null)
									|| (!this.builder.miniTable
											&& mapper.getColWidthPercentage() == null && mapper
											.getFxTableColumnModel()
											.getColumns().size() == 0)) {
								return;
							}
							if (this.builder.miniTable) {
								if (mapper.isShowInMiniTable()) {
									mapper.getFxTableColumnModel()
											.prefWidthProperty()
											.bind(this
													.prefWidthProperty()
													.multiply(
															mapper.getColWidthPercentageInMiniTable()));
								}
							} else {
								if (mapper.getFxTableColumnModel().getColumns()
										.size() > 0) {
									for (TableColumn<S, ?> column : mapper
											.getFxTableColumnModel()
											.getColumns()) {
										FXTableColumnModel<S, ?> fxTableColumn = (FXTableColumnModel<S, ?>) column;
										Double width = fxTableColumn
												.getColWidthPercentage();

										if (width != null) {
											fxTableColumn
													.prefWidthProperty()
													.bind(this
															.prefWidthProperty()
															.multiply(width));
										}
									}

								} else if (mapper.getColWidthPercentage() != null) {
									mapper.getFxTableColumnModel()
											.prefWidthProperty()
											.bind(this
													.prefWidthProperty()
													.multiply(
															mapper.getColWidthPercentage()));
								}
							}
						});
	}
}
