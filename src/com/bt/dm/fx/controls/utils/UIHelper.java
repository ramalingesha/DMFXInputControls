/**
 * 
 */
package com.bt.dm.fx.controls.utils;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.HashMap;
import java.util.List;

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

import com.bt.dm.core.i18n.AppLocale;
import com.bt.dm.core.model.DMModel;
import com.bt.dm.core.utils.DMCollectionUtils;
import com.bt.dm.fx.controls.DMControl;
import com.bt.dm.fx.controls.labels.FXCheckBoxCmp;
import com.bt.dm.fx.controls.table.DMTableModel;
import com.bt.dm.fx.controls.table.event.DMTableModelCreateEvent;

/**
 * @author Ramalingesha ML
 *
 *         Created on Oct 1, 2020 8:17:08 PM
 */
public class UIHelper {
	public static final ButtonType ALERT_OK_BUTTON = new ButtonType("OK",
			ButtonData.OK_DONE);;
	public static final ButtonType ALERT_CANCEL_BUTTON = new ButtonType(
			"Cancel", ButtonData.CANCEL_CLOSE);

	public static Region getHorizontalSpace(double width) {
		Region spacer = new Region();
		spacer.setPrefWidth(width);

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
}
