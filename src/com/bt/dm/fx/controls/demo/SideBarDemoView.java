/**
 * 
 */
package com.bt.dm.fx.controls.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import com.bt.dm.fx.controls.dashboard.DMSideBar;
import com.bt.dm.fx.controls.labels.FXFontAwesomeIcon;
import com.bt.dm.fx.controls.labels.FXFontAwesomeIcon.FXFontAwesomeIconBuilder;
import com.bt.dm.fx.controls.labels.FXMaterialDesignIcon;
import com.bt.dm.fx.controls.labels.FXMaterialDesignIcon.FXMaterialDesignIconBuilder;
import com.bt.dm.fx.controls.menu.DMMenuItem;
import com.bt.dm.fx.controls.menu.DMMenuItem.DMMenuItemBuilder;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;

/**
 * @author Ramalingesha ML
 *
 *         Created on Jan 1, 2021 3:50:32 PM
 */
public class SideBarDemoView extends Pane {

	private Node societyMenuItem;
	private Node unionMenuItem;
	private Node membersMenuItem;
	private Node milkAckMenuItem;

	public SideBarDemoView() {
		this.createComponent();
	}

	private void createComponent() {
		DMSideBar sideBar = new DMSideBar(this.getSideBarMenuItems());
		this.getChildren().add(sideBar.getView());
	}

	private ObservableList<Node> getSideBarMenuItems() {
		FXMaterialDesignIcon societyMenuIcon = new FXMaterialDesignIcon(
				new FXMaterialDesignIconBuilder(MaterialDesignIcon.HOME));
		FXMaterialDesignIcon unionMenuIcon = new FXMaterialDesignIcon(
				new FXMaterialDesignIconBuilder(MaterialDesignIcon.HOME_MODERN));
		FXMaterialDesignIcon membersMenuIcon = new FXMaterialDesignIcon(
				new FXMaterialDesignIconBuilder(
						MaterialDesignIcon.ACCOUNT_MULTIPLE));
		FXFontAwesomeIcon milkAckMenuIcon = new FXFontAwesomeIcon(
				new FXFontAwesomeIconBuilder(FontAwesomeIcon.TRUCK));

		societyMenuItem = new DMMenuItem(new DMMenuItemBuilder("society")
				.materialIcon(societyMenuIcon).menuTitle("society"));
		unionMenuItem = new DMMenuItem(new DMMenuItemBuilder("union")
				.materialIcon(unionMenuIcon).menuTitle("union"));
		membersMenuItem = new DMMenuItem(new DMMenuItemBuilder("members")
				.materialIcon(membersMenuIcon).menuTitle("members"));
		milkAckMenuItem = new DMMenuItem(new DMMenuItemBuilder("milkAck")
				.faIcon(milkAckMenuIcon).menuTitle("milkAck"));

		ObservableList<Node> menuItems = FXCollections.observableArrayList();
		menuItems.add(societyMenuItem);
		menuItems.add(unionMenuItem);
		menuItems.add(membersMenuItem);
		menuItems.add(milkAckMenuItem);

		return menuItems;
	}
}
