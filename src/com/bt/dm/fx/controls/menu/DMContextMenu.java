/**
 * 
 */
package com.bt.dm.fx.controls.menu;

import java.util.List;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.layout.Pane;

import com.bt.dm.core.i18n.DMMessageLocalizer;
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
			List<DMMenuItem> subMenus = menuItem.getSubMenus();
			
			if(DMCollectionUtils.notEmptyOrNull(subMenus)) {
				Menu menu = new Menu(DMMessageLocalizer.getLabel(menuItem.getMenuTitle()));
				
				subMenus.forEach(subMenu -> {
					CustomMenuItem customMenuItem = new CustomMenuItem();
					customMenuItem.setContent(subMenu);
					
					menu.getItems().add(customMenuItem);	
				});
				
				this.contextMenu.getItems().add(menu);
			} else {
				CustomMenuItem customMenuItem = new CustomMenuItem();
				customMenuItem.setContent(menuItem);
				this.contextMenu.getItems().add(customMenuItem);
			}

			
		});
	}

	public void show(Node parentMenu) {
		this.show(parentMenu, 0);
	}
	
	public void show(Node parentMenu, boolean showBelowParent) {
		this.show(parentMenu, 0, showBelowParent);
	}
	
	public void show(Node parentMenu, int offsetX) {
		this.show(parentMenu, offsetX, false);
	}
	
	public void show(Node parentMenu, int offsetX, boolean showBelowParent) {
		this.hide();

		Bounds bounds = parentMenu.localToScene(parentMenu.getBoundsInLocal());
		double xPos = showBelowParent ? bounds.getMinX() : (bounds.getMinX() + bounds.getWidth() + offsetX);
		double yPos = showBelowParent ? (bounds.getMaxY() + bounds.getHeight()) : bounds.getMinY();

		this.contextMenu.show(parentMenu, xPos, yPos);
	}

	public void hide() {
		this.contextMenu.hide();
	}
}
