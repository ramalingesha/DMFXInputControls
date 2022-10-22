package com.bt.dm.fx.controls.menu;

import java.util.List;

import com.bt.dm.core.i18n.DMMessageLocalizer;
import com.bt.dm.core.pubsub.DashboardMenuSelectionEventModel;
import com.bt.dm.core.pubsub.PubSubEvent;
import com.bt.dm.core.pubsub.PubSubEventDataModel;
import com.bt.dm.core.pubsub.PubSubEventHandler;
import com.bt.dm.core.utils.DMCollectionUtils;
import com.bt.dm.core.utils.DMStringUtils;
import com.bt.dm.fx.controls.menu.model.DMMenuBarItemsModel;
import com.bt.dm.fx.controls.theme.ControlsTheme;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;

/**
 * @author Ramalingesha ML
 *
 *         Created on Oct 16, 2022 8:42:49 AM
 */
public class DMMenuBar extends Pane {
	private List<DMMenuBarItemsModel> menus;
	private String styleClassNames;

	public DMMenuBar(List<DMMenuBarItemsModel> menus) {
		this(menus, null);
	}
	
	public DMMenuBar(List<DMMenuBarItemsModel> menus, String styleClassNames) {
		this.menus = menus;
		this.styleClassNames = styleClassNames;
		this.renderMenuBar();
	}

	private void renderMenuBar() {
		if (DMCollectionUtils.isEmptyOrNull(this.menus)) {
			return;
		}

		MenuBar menuBar = new MenuBar();
		
		if(!DMStringUtils.isEmpty(this.styleClassNames)) {
			menuBar.getStyleClass().addAll(this.styleClassNames.split(" "));	
		}

		this.menus.forEach(menu -> {
			Menu menuCmp = new Menu(menu.getTitle() == null ? "" : DMMessageLocalizer.getLabel(menu.getTitle()));
			
			if (menu.getFontAwesomeIcon() != null) {
				menuCmp.setGraphic(menu.getFontAwesomeIcon());
			}

			menuBar.getMenus().add(menuCmp);

			List<DMMenuBarItemsModel> menuItems = menu.getMenuItems();

			if (DMCollectionUtils.isEmptyOrNull(menuItems)) {
				return;
			}

			menuItems.forEach(menuItem -> {
				String title = DMMessageLocalizer.getLabel(menuItem.getTitle());
				List<DMMenuBarItemsModel> subMenus = menuItem.getMenuItems();

				if (DMCollectionUtils.isEmptyOrNull(subMenus)) {
					MenuItem menuItemCmp = new MenuItem(title);
					menuCmp.getItems().add(menuItemCmp);
				} else {
					Menu subMenuCmp = new Menu(title);
					menuCmp.getItems().add(subMenuCmp);

					subMenus.forEach(subMenu -> {
						MenuItem subMenuItemCmp = new MenuItem(DMMessageLocalizer.getLabel(subMenu.getTitle()));
						subMenuCmp.getItems().add(subMenuItemCmp);

						subMenuItemCmp.setOnAction(event -> {
							PubSubEvent.getInstance().publish("selectMenu",
									new DashboardMenuSelectionEventModel(subMenu.getMenuId()));
						});
					});
				}
			});
		});

		this.getChildren().add(menuBar);
		this.addRootThemeStyleSheet();
		this.subscribeOnThemeChange();
	}

	private void subscribeOnThemeChange() {
		PubSubEventHandler handler = new PubSubEventHandler() {

			@Override
			public void onPubSubData(PubSubEventDataModel data) {
				addRootThemeStyleSheet();
			}
		};
		PubSubEvent.getInstance().subscribe("THEME", handler);
	}

	private void addRootThemeStyleSheet() {
		this.getStylesheets().clear();
		this.getStylesheets()
				.add(getClass().getClassLoader().getResource("com/bt/dm/fx/controls/DMControl.css").toExternalForm());
		this.getStylesheets()
				.add(getClass().getClassLoader()
						.getResource("com/bt/dm/fx/controls/" + ControlsTheme.getThemeCssFileName("DMControl"))
						.toExternalForm());
	}
}
