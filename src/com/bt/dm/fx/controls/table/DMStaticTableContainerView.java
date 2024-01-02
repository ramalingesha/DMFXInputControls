package com.bt.dm.fx.controls.table;

import java.util.List;
import java.util.Map;

import com.bt.dm.core.pubsub.PubSubEvent;
import com.bt.dm.core.pubsub.PubSubEventDataModel;
import com.bt.dm.core.pubsub.PubSubEventHandler;
import com.bt.dm.core.utils.DMCollectionUtils;
import com.bt.dm.core.utils.DMStringUtils;
import com.bt.dm.fx.controls.DMLabelBuilder;
import com.bt.dm.fx.controls.labels.FXLabelCmp;
import com.bt.dm.fx.controls.table.model.DMStaticTableColumnModel;
import com.bt.dm.fx.controls.table.model.DMStaticTableDataModel;
import com.bt.dm.fx.controls.theme.ControlsTheme;
import com.bt.dm.fx.controls.toolbar.DMToolBar;
import com.bt.dm.fx.controls.toolbar.DMToolBar.DMToolBarBuilder;
import com.bt.dm.fx.controls.utils.UIHelper;
import com.bt.dm.fx.controls.view.DMView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * @author Ramalingesha ML
 *
 *         Created on Feb 07, 2023 10:30:03 AM
 */
public class DMStaticTableContainerView extends DMView {
	private List<DMStaticTableColumnModel> columnModels;
	private String tableHeader;
	private ObservableList<Node> tableHeaderControls;
	private Double width;
	private Double height;
	private DMStaticTableView staticTableView;
	private Pane tablePane;
	private boolean crossTable;

	public DMStaticTableContainerView(List<DMStaticTableColumnModel> columnModels) {
		this(null, columnModels);
	}
	
	public DMStaticTableContainerView(String tableHeader, List<DMStaticTableColumnModel> columnModels) {
		this.tableHeader = tableHeader;
		this.columnModels = columnModels;
	}

	public DMStaticTableContainerView tableHeaderControls(ObservableList<Node> tableHeaderControls) {
		this.tableHeaderControls = tableHeaderControls;

		return this;
	}
	
	public DMStaticTableContainerView width(Double width) {
		this.width = width;

		return this;
	}
	
	public DMStaticTableContainerView height(Double height) {
		this.height = height;

		return this;
	}
	
	public DMStaticTableContainerView crossTable(boolean crossTable) {
		this.crossTable = crossTable;

		return this;
	}

	@Override
	public Pane getView() {
		VBox root = new VBox(this.getTable());

		this.addRootThemeStyleSheet(root);
		this.subscribeOnThemeChange(root);

		return root;
	}

	private ScrollPane getTable() {
		VBox container = new VBox(10);
		container.getStyleClass().add("dm-table-pane");

		if (this.width != null) {
			container.setPrefWidth(this.width);
		}

		if (this.height != null) {
			container.setPrefHeight(this.height);
		}

		// Table toolbar
		if (!DMStringUtils.isEmpty(this.tableHeader) || DMCollectionUtils.notEmptyOrNull(this.tableHeaderControls)) {
			container.getChildren().add(this.getTableToolbarPane());
		}

		this.tablePane = new VBox();
		this.createTable();
		
		ScrollPane tableDataScrollPane = new ScrollPane(tablePane);
		tableDataScrollPane.setPrefHeight(this.height - 108);
		
		container.getChildren().add(tableDataScrollPane);

		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setContent(container);

		return scrollPane;
	}
	
	private void createTable() {
		staticTableView = new DMStaticTableView(this.columnModels, this.height - 108).crossTable(this.crossTable);
		
		tablePane.getChildren().clear();
		tablePane.getChildren().add(staticTableView.getView());
	}

	private Pane getTableToolbarPane() {
		ObservableList<Node> nodes = FXCollections.observableArrayList();
		
		// Header
		if (this.tableHeader != null) {
			FXLabelCmp title = new FXLabelCmp(new DMLabelBuilder().label(this.tableHeader).h3(true));
			BorderPane.setAlignment(title, Pos.CENTER);
			
			nodes.add(title);
		}
		
		if(DMCollectionUtils.notEmptyOrNull(this.tableHeaderControls)) {
			Region spacer = UIHelper.getEmptySpace(10, false);
			HBox.setHgrow(spacer, Priority.ALWAYS);
			
			nodes.add(spacer);
			
			nodes.addAll(this.tableHeaderControls);
		}
		
		DMToolBar toolBar = new DMToolBar(new DMToolBarBuilder()
				.toolBarControls(nodes).hideDefaultButtons(true));
		
		VBox headerBox = new VBox(toolBar, new Separator());

		return headerBox;
	}
	
	public void loadTableData(List<List<DMStaticTableDataModel>> dataModels) {
		if(this.crossTable) {
			this.loadCrossTableData(dataModels);
		} else {
			this.staticTableView.renderTableData(dataModels);			
		}
	}
	
	private void loadCrossTableData(List<List<DMStaticTableDataModel>> dataModels) {
		this.columnModels = UIHelper.getCrossTableColumns(dataModels);
		
		if(DMCollectionUtils.isEmptyOrNull(this.columnModels)) {
			this.staticTableView.renderTableData(null);
			return;
		}
		
		this.createTable();
		
		UIHelper.renderCrossTableData(dataModels, staticTableView);
	}
	
	public Map<String, List<Object>> getTableDataObjects() {
		return this.staticTableView.getTableDataObjects();
	}

	private void subscribeOnThemeChange(Pane pane) {
		PubSubEventHandler handler = new PubSubEventHandler() {

			@Override
			public void onPubSubData(PubSubEventDataModel data) {
				addRootThemeStyleSheet(pane);
			}
		};
		PubSubEvent.getInstance().subscribe("THEME", handler);
	}

	private void addRootThemeStyleSheet(Pane pane) {
		pane.getStylesheets().clear();
		pane.getStylesheets().add(getClass().getResource("Table.css").toExternalForm());
		pane.getStylesheets().add(getClass().getResource(ControlsTheme.getThemeCssFileName("Table")).toExternalForm());
	}
}
