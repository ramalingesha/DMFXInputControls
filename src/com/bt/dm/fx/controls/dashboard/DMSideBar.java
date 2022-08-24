/**
 * 
 */
package com.bt.dm.fx.controls.dashboard;

import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import com.bt.dm.fx.controls.toolbar.DMToolBar;
import com.bt.dm.fx.controls.toolbar.DMToolBar.DMToolBarBuilder;
import com.bt.dm.fx.controls.view.DMView;

/**
 * @author Ramalingesha ML
 *
 *         Created on Jan 1, 2021 12:31:40 PM
 */
public class DMSideBar extends DMView {
	private ObservableList<Node> menuItems;

	public DMSideBar(ObservableList<Node> observableList) {
		this.menuItems = observableList;
	}

	@Override
	public Pane getView() {
		DMToolBar toolBar = new DMToolBar(new DMToolBarBuilder()
				.toolBarControls(menuItems).hideDefaultButtons(true)
				.orientation(Orientation.VERTICAL).displayLogo(true));
		toolBar.getStyleClass().add("side-bar");

		VBox box = new VBox();
		VBox.setVgrow(toolBar, Priority.ALWAYS);
		box.getChildren().add(toolBar);

		return box;
	}

}
