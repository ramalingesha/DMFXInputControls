package com.bt.dm.fx.controls.menu;

import java.util.List;

import com.bt.dm.fx.controls.dashboard.DMDashboardMenuEventListener;
import com.bt.dm.fx.controls.labels.FXFontAwesomeIcon;
import com.bt.dm.fx.controls.labels.FXMaterialDesignIcon;
import com.bt.dm.fx.controls.labels.FXFontAwesomeIcon.FXFontAwesomeIconBuilder;
import com.bt.dm.fx.controls.labels.FXMaterialDesignIcon.FXMaterialDesignIconBuilder;
import com.bt.dm.fx.controls.menu.DMMenuItem.DMMenuItemBuilder;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;

/**
 * @author Ramalingesha ML
 *
 *         Created on Oct 16, 2022 11:44:34 AM
 */
public class DMMenuItemHelper {
	public static DMMenuItem createMenu(MaterialDesignIcon materialIcon,
			FontAwesomeIcon faIcon, String titleLocaleKey) {
		return createMenu(materialIcon, faIcon, titleLocaleKey, null);
	}

	public static DMMenuItem createMenu(MaterialDesignIcon materialIcon,
			FontAwesomeIcon faIcon, String titleLocaleKey, String classNames) {
		return new DMMenuItem(createMenuItemBuilder(materialIcon, faIcon,
				titleLocaleKey, true, classNames));
	}

	private static DMMenuItemBuilder createMenuItemBuilder(
			MaterialDesignIcon materialIcon, FontAwesomeIcon faIcon,
			String titleLocaleKey, boolean verticalAlign, String classNames) {
		DMMenuItemBuilder builder = new DMMenuItemBuilder(titleLocaleKey)
				.menuTitle(titleLocaleKey).verticalAlign(verticalAlign)
				.classNames(classNames);

		if (materialIcon != null) {
			FXMaterialDesignIcon materialDesignIcon = new FXMaterialDesignIcon(
					new FXMaterialDesignIconBuilder(materialIcon));
			builder.materialIcon(materialDesignIcon);
		} else if (faIcon != null) {
			FXFontAwesomeIcon fontAwesomeIcon = new FXFontAwesomeIcon(
					new FXFontAwesomeIconBuilder(faIcon));
			builder.faIcon(fontAwesomeIcon);
		}

		return builder;
	}

	public static DMMenuItem createMenuItem(MaterialDesignIcon materialIcon,
			FontAwesomeIcon faIcon, String titleLocaleKey, String menuId, DMDashboardMenuEventListener dashboardMenuEventListener) {
		return createMenuItem(materialIcon, faIcon, titleLocaleKey,
				menuId, null, null, dashboardMenuEventListener);
	}
	
	public static DMMenuItem createMenuItem(MaterialDesignIcon materialIcon, FontAwesomeIcon faIcon,
			String titleLocaleKey, String menuId, List<DMMenuItem> subMenus,
			DMDashboardMenuEventListener dashboardMenuEventListener) {
		return createMenuItem(materialIcon, faIcon, titleLocaleKey, menuId, subMenus, null, dashboardMenuEventListener);
	}

	private static DMMenuItem createMenuItem(MaterialDesignIcon materialIcon,
			FontAwesomeIcon faIcon, String titleLocaleKey, String menuId, List<DMMenuItem> subMenus,
			String classNames, DMDashboardMenuEventListener dashboardMenuEventListener) {
		DMMenuItemBuilder builder = createMenuItemBuilder(materialIcon,
				faIcon, titleLocaleKey, false, classNames)
				.subMenus(subMenus);
		
		builder.menuItemClickEvent(event -> {
			dashboardMenuEventListener.onMenuClick(menuId);
		});

		return new DMMenuItem(builder);
	}
}
