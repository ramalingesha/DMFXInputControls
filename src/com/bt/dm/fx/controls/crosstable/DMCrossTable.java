package com.bt.dm.fx.controls.crosstable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ScrollPane;

import com.bt.dm.core.pubsub.PubSubEvent;
import com.bt.dm.core.pubsub.PubSubEventDataModel;
import com.bt.dm.core.pubsub.PubSubEventHandler;
import com.bt.dm.core.utils.DMCollectionUtils;
import com.bt.dm.fx.controls.table.DMTableModel;
import com.bt.dm.fx.controls.table.FXTableColumnModel;
import com.bt.dm.fx.controls.table.FXTableView;
import com.bt.dm.fx.controls.table.FXTableView.FXTableViewBuilder;
import com.bt.dm.fx.controls.table.FxTableColumnSizeMapper;
import com.bt.dm.fx.controls.theme.ControlsTheme;

/**
 * @author Ramalingesha ML
 *
 *         Created on Feb 15, 2021 9:02:59 AM
 */
public class DMCrossTable<S extends DMTableModel> extends ScrollPane {
	public static class FXDMCrossTableBuilder<S extends DMTableModel> extends
			FXTableViewBuilder<S> {
		private DMCrossTableModel<S> crossTableModel;

		public FXDMCrossTableBuilder(DMCrossTableModel<S> crossTableModel) {
			super(null);
			this.crossTableModel = crossTableModel;
		}
	}

	private PubSubEventHandler handler;
	private FXTableView<S> crossTableView;

	public DMCrossTable(FXDMCrossTableBuilder<S> crossTableBuilder) {
		this.createComponent(crossTableBuilder.crossTableModel,
				crossTableBuilder);
	}

	@SuppressWarnings("unchecked")
	private void createComponent(DMCrossTableModel<S> crossTableModel,
			FXDMCrossTableBuilder<S> crossTableBuilder) {
		this.addRootThemeStyleSheet();

		ObservableList<FxTableColumnSizeMapper<S>> columnSizeMapperList = FXCollections
				.observableArrayList();
		if (crossTableModel.getRowHeaderColumn() != null) {
			columnSizeMapperList.add(crossTableModel.getRowHeaderColumn());
			// Add row header column style class
			this.addRowHeaderColumnStyle(crossTableModel.getRowHeaderColumn()
					.getFxTableColumnModel());
		}

		if (DMCollectionUtils.notEmptyOrNull(crossTableModel.getColumns())) {
			columnSizeMapperList.addAll(crossTableModel.getColumns());
		}

		crossTableBuilder.columnSizeMapperList(columnSizeMapperList);
		crossTableBuilder.showDeleteIcon(false);
		crossTableBuilder.showEditIcon(false);

		crossTableView = new FXTableView<S>(crossTableBuilder);

		if (DMCollectionUtils.notEmptyOrNull(crossTableBuilder.crossTableModel
				.getRecords())) {
			this.addRecords((ObservableList<S>) crossTableBuilder.crossTableModel
					.getRecords());
		}

		this.setContent(crossTableView);

		this.subscribeOnThemeChange();
	}

	private void addRowHeaderColumnStyle(FXTableColumnModel<S, ?> columnModel) {
		columnModel.getStyleClass().add("row-header");
	}

	public void addRecords(ObservableList<S> records) {
		this.crossTableView.addRecords(records);
	}

	public void clearTableRecords() {
		this.crossTableView.clearTableRecords();
	}

	public void addTableColumns(
			ObservableList<FxTableColumnSizeMapper<S>> columnSizeMapperList) {
		this.addTableColumns(columnSizeMapperList, true);
	}

	public void addTableColumns(
			ObservableList<FxTableColumnSizeMapper<S>> columnSizeMapperList,
			boolean markRowHeaderColumn) {
		if (DMCollectionUtils.isEmptyOrNull(columnSizeMapperList)) {
			return;
		}

		if (markRowHeaderColumn) {
			this.addRowHeaderColumnStyle(columnSizeMapperList.get(0)
					.getFxTableColumnModel());
		}

		this.crossTableView.addTableColumns(columnSizeMapperList);
	}

	public void clearTableColumns() {
		this.crossTableView.clearTableColumns();
	}

	public void setTableWidth(double width) {
		this.crossTableView.setTableWidth(width);
	}

	public void setTableHeight(double height) {
		this.crossTableView.setTableHeight(height);
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
				getClass().getResource("Cross_Table.css").toExternalForm());
		this.getStylesheets().add(
				getClass().getResource(
						ControlsTheme.getThemeCssFileName("Cross_Table"))
						.toExternalForm());
	}
}
