package com.bt.dm.fx.controls.table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.bt.dm.core.utils.DMCollectionUtils;
import com.bt.dm.core.utils.DMNumberUtils;
import com.bt.dm.core.utils.DMStringUtils;
import com.bt.dm.fx.controls.DMLabelBuilder;
import com.bt.dm.fx.controls.events.IconClickEvent;
import com.bt.dm.fx.controls.labels.FXLabelCmp;
import com.bt.dm.fx.controls.labels.FXMaterialDesignIcon;
import com.bt.dm.fx.controls.labels.FXMaterialDesignIcon.FXMaterialDesignIconBuilder;
import com.bt.dm.fx.controls.table.model.DMStaticTableColumnModel;
import com.bt.dm.fx.controls.table.model.DMStaticTableDataModel;
import com.bt.dm.fx.controls.utils.SizeHelper;
import com.bt.dm.fx.controls.utils.UIHelper;
import com.bt.dm.fx.controls.view.DMView;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * @author Ramalingesha ML
 *
 *         Created on Jan 14, 2023 08:34:03 AM
 */
public class DMStaticTableView extends DMView {
	private List<DMStaticTableColumnModel> columnModels;
	private Double tableHeight;
	private VBox tableDataPane;
	private ScrollPane tableDataScrollPane;
	private boolean crossTable;
	private boolean enableScrollBar;
	
	private final double TABLE_DATA_CONTAINER_HEIGHT = SizeHelper.MAIN_PANEL_SIZE.getHeight() - 230;
	private Map<String, List<Object>> tableDataObjects;

	public DMStaticTableView(List<DMStaticTableColumnModel> columnModels) {
		this(columnModels, null);
	}
	
	public DMStaticTableView(List<DMStaticTableColumnModel> columnModels, Double tableHeight) {
		this.columnModels = columnModels;
		this.tableHeight = tableHeight == null ? TABLE_DATA_CONTAINER_HEIGHT : tableHeight;
	}
	
	public DMStaticTableView crossTable(boolean crossTable) {
		this.crossTable = crossTable;
		return this;
	}
	
	public DMStaticTableView enableScrollBar(boolean enableScrollBar) {
		this.enableScrollBar = enableScrollBar;
		return this;
	}

	@Override
	public Pane getView() {
		VBox tableContainer = new VBox(3);
		tableContainer.getStyleClass().add("table-view");

		Pane tableColumnsPane = this.getColumnHeaders();
		tableDataPane = new VBox(3);

		KeyFrame keyFrame1 = new KeyFrame(Duration.millis(10), e -> {
			double width = tableColumnsPane.getBoundsInLocal().getWidth();
			tableDataPane.setPrefWidth(width + 15);
		});
		Timeline timeline = new Timeline(keyFrame1);

		Platform.runLater(timeline::play);

		if(this.enableScrollBar) {
			tableDataScrollPane = new ScrollPane(tableDataPane);
			tableDataScrollPane.setPrefHeight(this.tableHeight);
			tableDataScrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
			
			tableContainer.getChildren().addAll(tableColumnsPane, tableDataScrollPane);
		} else {
			tableContainer.getChildren().addAll(tableColumnsPane, tableDataPane);			
		}
		
		return tableContainer;
	}

	private Pane getColumnHeaders() {
		HBox box = new HBox(1);

		if (DMCollectionUtils.isEmptyOrNull(columnModels)) {
			return box;
		}

		DMStaticTableColumnModel firstColumnModel = columnModels.get(0);

		box.getChildren().add(this.createColumnHeader(firstColumnModel.getColumnHeader(), firstColumnModel.isI18n(),
				this.crossTable ? "no-color" : firstColumnModel.getClassName(), firstColumnModel.getWidth()));

		if (columnModels.size() > 1) {
			columnModels.subList(1, columnModels.size()).forEach(columnModel -> {
				box.getChildren().add(this.createColumnHeader(columnModel.getColumnHeader(), columnModel.isI18n(),
						columnModel.getClassName(), columnModel.getWidth()));
			});
		}

		return box;
	}

	public void renderTableData(List<List<DMStaticTableDataModel>> dataModels) {
		this.tableDataPane.getChildren().clear();
		this.tableDataObjects = new HashMap<>();

		if (DMCollectionUtils.notEmptyOrNull(dataModels)) {
			int row = 1;
			for (List<DMStaticTableDataModel> rowData : dataModels) {
				Map<String, DMStaticTableDataModel> columnDataMap = rowData.stream()
						.collect(Collectors.toMap(DMStaticTableDataModel::getColumnId, v -> v));

				if (columnDataMap == null || columnDataMap.isEmpty()) {
					continue;
				}
				
				String rowStyleClass = row++ % 2 == 0 ? "odd" : "even";

				HBox rowPane = new HBox(6);
				rowPane.getStyleClass().add(String.format("table-row-cell-%s", rowStyleClass));

				for(DMStaticTableColumnModel columnModel: columnModels) {
					String columnId = columnModel.getColumnId();
					DMStaticTableDataModel model = columnDataMap.get(columnId);
					List<Object> rowDataObjects = this.tableDataObjects.get(columnId);
					
					if(rowDataObjects == null) {
						rowDataObjects = new ArrayList<>();
						this.tableDataObjects.put(columnId, rowDataObjects);
					}

					if (model == null) {
						rowPane.getChildren().add(this.createRowCell("", false, columnModel.getWidth(), rowStyleClass,
								null, false, false, null));
						rowDataObjects.add("");
						
						continue;
					}

					if (model.getNode() != null) {
						rowPane.getChildren().add(this.createRowCell(model.getNode(), rowStyleClass));
						rowDataObjects.add(model.getNode());
					} else if (model.getValueProperty() != null) {
						rowPane.getChildren().add(this.createRowCell(model.getValueProperty(), columnModel.getWidth(),
								rowStyleClass, model.getClassName(), model.isRightAlign()));
						rowDataObjects.add(model.getValueProperty());
					} else {
						rowPane.getChildren()
								.add(this.createRowCell(model.getText(), false, columnModel.getWidth(), rowStyleClass,
										model.getClassName(), model.isRightAlign(), model.isShowEditIcon(),
										model.getEditIconClickEvent()));
						rowDataObjects.add(model.getText());
					}
				}

				this.tableDataPane.getChildren().add(rowPane);
			}
		} else {
			FXLabelCmp noDataLabel = new FXLabelCmp(new DMLabelBuilder().label("app.report.noDataAvailable").h3(true));
			noDataLabel.setPrefWidth(300);
			this.tableDataPane.getChildren().add(noDataLabel);
		}
	}

	private Pane createColumnHeader(String text, boolean i18n, String className, double width) {
		Pane header = new Pane(this.createLabel(text, i18n, className, width));
		header.getStyleClass().add("column-header");

		return header;
	}

	private Pane createRowCell(String text, boolean i18n, double width, String rowStyleClass, String className,
			boolean rightAlign, boolean showEditIcon, IconClickEvent editIconClickEvent) {
		return this.createRowCell(text, i18n, null, width, rowStyleClass, className, rightAlign, showEditIcon, editIconClickEvent);
	}

	private Pane createRowCell(StringProperty valueProperty, double width, String rowStyleClass, String className,
			boolean rightAlign) {
		return this.createRowCell("", false, valueProperty, width, rowStyleClass, className, rightAlign, false, null);
	}

	private Pane createRowCell(String text, boolean i18n, StringProperty valueProperty, double width,
			String rowStyleClass, String className, boolean rightAlign, boolean showEditIcon, IconClickEvent editIconClickEvent) {
		FXLabelCmp labelCmp = this.createLabel(text, i18n, null, showEditIcon ? width - 20 : width);

		if (valueProperty != null) {
			labelCmp.textProperty().bind(valueProperty);
		}

		if (className != null) {
			labelCmp.getStyleClass().add(className);
		}

		if (rightAlign) {
			labelCmp.setAlignment(Pos.CENTER_RIGHT);
		}
		
		Pane rowCell = new Pane();
		
		if(showEditIcon) {
			FXMaterialDesignIcon editIcon = new FXMaterialDesignIcon(
					new FXMaterialDesignIconBuilder(MaterialDesignIcon.PENCIL).iconClickEvent(event -> {
						if(editIconClickEvent != null) {
							editIconClickEvent.onClick(event);
						}
					}).size("1.5em"));
			editIcon.setStyle("-fx-fill: #bc3672;");
			
			HBox box = new HBox(labelCmp, editIcon);
			box.setAlignment(Pos.CENTER_LEFT);
			
			rowCell.getChildren().add(box);
		} else {
			rowCell.getChildren().add(labelCmp);
		}

		this.updateRowStyle(rowCell, rowStyleClass);

		return rowCell;
	}

	private Pane createRowCell(Node node, String rowStyleClass) {
		Pane rowCell = new Pane(node);
		this.updateRowStyle(rowCell, rowStyleClass);

		return rowCell;
	}

	private void updateRowStyle(Pane pane, String rowStyleClass) {
		pane.getStyleClass().add("table-row-cell");
		pane.getStyleClass().add(String.format("table-row-cell-%s", rowStyleClass));
	}

	private FXLabelCmp createLabel(String text, boolean i18n, String className, double width) {
		DMLabelBuilder labelBuilder = new DMLabelBuilder().label(text).i18n(i18n);

		if (!DMStringUtils.isEmpty(className)) {
			labelBuilder.classNames(className.split(" "));
		}

		FXLabelCmp labelCmp = new FXLabelCmp(labelBuilder);

		labelCmp.setPrefWidth(width);

		return labelCmp;
	}

	public void updateRowErrorState(List<Integer> rowNumbers) {
		if (DMCollectionUtils.isEmptyOrNull(this.tableDataPane.getChildren())) {
			return;
		}

		int row = 0;
		for (Node node : this.tableDataPane.getChildren()) {
			node.getStyleClass().remove("error-row");

			if (rowNumbers != null && rowNumbers.indexOf(row++) != -1) {
				node.getStyleClass().add("error-row");
			}
		}
	}
	
	public void focusInlineEditTextField(int row, int column) {
		if(!this.enableScrollBar) {
			System.err.println("Scroll bar is not enabled");
			return;
		}
		
		ObservableList<Node> nodes = this.tableDataPane.getChildren();
		
		if (DMCollectionUtils.isEmptyOrNull(nodes)) {
			return;
		}
		
		HBox rowPane = (HBox) nodes.get(row);
		
		if(rowPane == null || (rowPane.getChildren().size() - 1) < column) {
			return;
		}
		
		Node node = ((Pane) rowPane.getChildren().get(column)).getChildren().get(0);
		
		if(node instanceof Control) {
			int noOfRows = nodes.size();
			double vValue = row > (noOfRows - 8) ? 1.0 : (double) row/noOfRows;
			
			Platform.runLater(() -> {
				this.tableDataScrollPane.setVvalue(DMNumberUtils.getQuantityRoundOff(vValue));
			});

			UIHelper.setFocus((Control) node);
		}
	}

	public Map<String, List<Object>> getTableDataObjects() {
		return tableDataObjects;
	}
}
