/**
 * 
 */
package com.bt.dm.fx.controls.menu;

import java.util.List;

import javafx.geometry.Bounds;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.layout.Pane;

import com.bt.dm.core.utils.DMCollectionUtils;

/**
 * @author Ramalingesha ML
 *
 *         Created on Aug 11, 2021 8:26:49 AM
 */
public class DMContextMenu extends Pane {
	private List<DMMenuItem> menuItems;
	private ContextMenu contextMenu;

	public DMContextMenu(List<DMMenuItem> menuItems) {
		this.menuItems = menuItems;
		this.createContextMenu();
	}

	private void createContextMenu() {
		this.contextMenu = new ContextMenu();

		if (DMCollectionUtils.isEmptyOrNull(this.menuItems)) {
			return;
		}

		this.menuItems.forEach(menuItem -> {
			CustomMenuItem customMenuItem = new CustomMenuItem();
			customMenuItem.setContent(menuItem);

			this.contextMenu.getItems().add(customMenuItem);
		});
	}

	public void show(DMMenuItem parentMenu) {
		this.hide();

		Bounds bounds = parentMenu.localToScene(parentMenu.getBoundsInLocal());
		double xPos = bounds.getMinX() + bounds.getWidth();
		double yPos = bounds.getMinY();

		this.contextMenu.show(parentMenu, xPos, yPos);
	}

	public void hide() {
		this.contextMenu.hide();
	}
}
