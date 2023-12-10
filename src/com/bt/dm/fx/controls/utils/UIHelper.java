/**
 * 
 */
package com.bt.dm.fx.controls.utils;

import java.awt.AWTException;
import java.awt.Robot;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import com.bt.dm.core.i18n.AppLocale;
import com.bt.dm.core.model.DMModel;
import com.bt.dm.core.utils.DMCollectionUtils;
import com.bt.dm.core.utils.DMNumberUtils;
import com.bt.dm.fx.controls.DMControl;
import com.bt.dm.fx.controls.dialog.FXMessageBox;
import com.bt.dm.fx.controls.dialog.FXMessageBox.FXMessageBoxBuilder;
import com.bt.dm.fx.controls.input.FXTextDecimalFormatterInputCmp;
import com.bt.dm.fx.controls.input.FXTextDecimalFormatterInputCmp.FXTextDecimalFormatterInputCmpBuilder;
import com.bt.dm.fx.controls.labels.FXCheckBoxCmp;
import com.bt.dm.fx.controls.table.DMStaticTableView;
import com.bt.dm.fx.controls.table.event.DMTableModelCreateEvent;
import com.bt.dm.fx.controls.table.model.DMStaticTableColumnModel;
import com.bt.dm.fx.controls.table.model.DMStaticTableDataModel;
import com.bt.dm.fx.controls.table.model.DMTableModel;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Control;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

/**
 * @author Ramalingesha ML
 *
 *         Created on Oct 1, 2020 8:17:08 PM
 */
public class UIHelper {
	public static final ButtonType ALERT_OK_BUTTON = new ButtonType("OK",
			ButtonData.OK_DONE);
	public static final ButtonType ALERT_CANCEL_BUTTON = new ButtonType(
			"Cancel", ButtonData.CANCEL_CLOSE);

	public static Region getEmptySpace(double size, boolean vertical) {
		Region spacer = new Region();

		if (vertical) {
			spacer.setPrefHeight(size);
		} else {
			spacer.setPrefWidth(size);
		}

		return spacer;
	}

	public static void setFocus(Control inputControl) {
		Platform.runLater(() -> inputControl.requestFocus());
	}

	public static void updateTraverseFocusWithEnterKey(Node node)
			throws AWTException {
		node.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			Robot eventRobot = new Robot();

			@Override
			public void handle(KeyEvent keyEvent) {
				switch (keyEvent.getCode()) {
				case ENTER:
					EventTarget target = keyEvent.getTarget();
					if (target instanceof Button) {
						((Button) target).fire();
					} else {
						eventRobot.keyPress(java.awt.event.KeyEvent.VK_TAB);
						eventRobot.keyRelease(java.awt.event.KeyEvent.VK_TAB);
						keyEvent.consume();
					}

					break;

				default:
					break;
				}
			}
		});
	}

	public static void clearPaneInputControls(Pane pane) {
		ObservableList<Node> controls = pane.getChildren();
		controls.forEach(control -> {
			if (control instanceof DMControl<?>) {
				((DMControl<?>) control).clear();
			} else if (control instanceof FXCheckBoxCmp) {
				((FXCheckBoxCmp) control).setSelected(false);
			}
		});
	}

	public static boolean canHideTranslatorField() {
		return Fonts.getInstance().getAppLocale() == AppLocale.ENGLISH;
	}

	public static <M extends DMModel, T extends DMTableModel> ObservableList<T> constructDMTableModels(
			List<M> dmModels,
			DMTableModelCreateEvent<M, T> tableModelCreateEvent) {
		if (DMCollectionUtils.isEmptyOrNull(dmModels)) {
			return FXCollections.emptyObservableList();
		}

		ObservableList<T> tableData = FXCollections.observableArrayList();

		HashMap<Integer, M> collect = dmModels.stream().collect(
				HashMap<Integer, M>::new,
				(map, streamValue) -> map.put(map.size(), streamValue),
				(map, map2) -> {
				});

		collect.forEach((index, record) -> {
			tableData.add(tableModelCreateEvent.createTableModel(index, record));
		});

		return tableData;
	}

	public static boolean isEnglishLocaleSelected() {
		return Fonts.getInstance().getAppLocale() == AppLocale.ENGLISH;
	}
	
	public static List<DMStaticTableColumnModel> getCrossTableColumns(List<List<DMStaticTableDataModel>> dataModels) {
		if (DMCollectionUtils.isEmptyOrNull(dataModels)) {
			return null;
		}

		List<DMStaticTableColumnModel> columnModels = new ArrayList<>();

		dataModels.get(0).forEach(columnData -> {
			columnModels.add(new DMStaticTableColumnModel(columnData.getColumnId(), columnData.getText())
					.i18n(columnData.isI18n()).width(columnData.getWidth()));
		});

		return columnModels;
	}

	public static void renderCrossTableData(List<List<DMStaticTableDataModel>> dataModels,
			DMStaticTableView staticTableView) {
		if (DMCollectionUtils.isEmptyOrNull(dataModels) || dataModels.size() == 1) {
			staticTableView.renderTableData(null);
			return;
		}

		staticTableView.renderTableData(dataModels.subList(1, dataModels.size()));
	}
	
	public static DMStaticTableDataModel getStaticTableAmountColumnDataModel(String key, String value,
			boolean editMode) {
		return UIHelper.getStaticTableAmountColumnDataModel(key, value, editMode, 150);
	}
	
	public static DMStaticTableDataModel getStaticTableAmountColumnDataModel(String key, String value,
			boolean editMode, int controlSize) {
		if (!editMode) {
			return new DMStaticTableDataModel(key, value).rightAlign(true);
		}

		FXTextDecimalFormatterInputCmp inputCmp = new FXTextDecimalFormatterInputCmp(
				new FXTextDecimalFormatterInputCmpBuilder(2, new DecimalFormat("0.00")).value(value).hideLabel(true)
						.inputSize(new ControlSize(controlSize, SizeHelper.INPUT_CONTROL_SIZE.getHeight())));

		return new DMStaticTableDataModel(key, inputCmp);
	}
	
	public static Comparator<String> getStringIntegerComparator() {
		return new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {
				int v1 = DMNumberUtils.parseInt(o1, true);
				int v2 = DMNumberUtils.parseInt(o2, true);

				return Integer.compare(v1, v2);
			}
		};
	}
	
	public static <T> boolean checkReportEmptyData(List<T> records) {
		boolean isEmpty = DMCollectionUtils.isEmptyOrNull(records);
		
		if (isEmpty) {
			FXMessageBox.getInstance().show(new FXMessageBoxBuilder("app.reportView.noDataAvailable"));
		}
		
		return isEmpty;
	}
}
