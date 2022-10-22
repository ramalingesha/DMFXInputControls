package com.bt.dm.fx.controls.menu.model;

import java.util.List;

import com.bt.dm.core.model.DMModel;
import com.bt.dm.fx.controls.labels.FXFontAwesomeIcon;

/**
 * @author Ramalingesha ML
 *
 *         Created on Oct 16, 2022 10:36:49 AM
 */
public class DMMenuBarItemsModel implements DMModel {

	private static final long serialVersionUID = 1L;
	private String title;
	private String menuId;
	private FXFontAwesomeIcon fontAwesomeIcon;
	private List<DMMenuBarItemsModel> menuItems;

	public DMMenuBarItemsModel(String title) {
		this(title, null, null);
	}
	
	public DMMenuBarItemsModel(String title, String menuId) {
		this(title, menuId, null);
	}

	public DMMenuBarItemsModel(String title, List<DMMenuBarItemsModel> menuItems) {
		this(title, null, menuItems);
	}
	
	public DMMenuBarItemsModel(String title, String menuId, List<DMMenuBarItemsModel> menuItems) {
		super();
		this.title = title;
		this.menuId = menuId;
		this.menuItems = menuItems;
	}
	
	public DMMenuBarItemsModel fontAwesomeIcon(FXFontAwesomeIcon fontAwesomeIcon) {
		this.fontAwesomeIcon = fontAwesomeIcon;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public List<DMMenuBarItemsModel> getMenuItems() {
		return menuItems;
	}

	public String getMenuId() {
		return menuId;
	}

	public FXFontAwesomeIcon getFontAwesomeIcon() {
		return fontAwesomeIcon;
	}
}
