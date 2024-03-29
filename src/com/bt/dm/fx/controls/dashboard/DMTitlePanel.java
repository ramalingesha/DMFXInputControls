package com.bt.dm.fx.controls.dashboard;

import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.bt.dm.core.controller.DMCoreServiceController;
import com.bt.dm.core.pubsub.DashboardTitlePubSubTopicModel;
import com.bt.dm.core.pubsub.PubSubEvent;
import com.bt.dm.core.pubsub.PubSubEventDataModel;
import com.bt.dm.core.pubsub.PubSubEventHandler;
import com.bt.dm.core.settings.constants.SettingTableKeys;
import com.bt.dm.core.settings.model.SettingsModel;
import com.bt.dm.fx.controls.events.IconClickEvent;
import com.bt.dm.fx.controls.labels.FXFontAwesomeIcon;
import com.bt.dm.fx.controls.labels.FXFontAwesomeIcon.FXFontAwesomeIconBuilder;
import com.bt.dm.fx.controls.labels.FXLabelCmp;
import com.bt.dm.fx.controls.labels.FXLabelCmp.FXLabelCmpBuilder;
import com.bt.dm.fx.controls.labels.FXMaterialDesignIcon;
import com.bt.dm.fx.controls.labels.FXMaterialDesignIcon.FXMaterialDesignIconBuilder;
import com.bt.dm.fx.controls.menu.DMContextMenu;
import com.bt.dm.fx.controls.menu.DMMenuItem;
import com.bt.dm.fx.controls.menu.DMMenuItem.DMMenuItemBuilder;
import com.bt.dm.fx.controls.theme.AppTheme;
import com.bt.dm.fx.controls.theme.ControlsTheme;
import com.bt.dm.fx.controls.theme.ThemePubSubTopicModel;
import com.bt.dm.fx.controls.view.DMView;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;

/**
 * 
 * @author Ramalingesha ML
 *
 *         Created on Dec 17, 2020 8:50:24 AM
 */
public class DMTitlePanel extends DMView {
	public static class DMTitlePanelBuilder {
		private String subTitle;
		private String mainTitle;
		private IconClickEvent minimizeIconClickHandler;
		private IconClickEvent closeIconClickHandler;
		private boolean showCloudStatus;

		public DMTitlePanelBuilder subTitle(String subTitle) {
			this.subTitle = subTitle;
			return this;
		}

		public DMTitlePanelBuilder mainTitle(String mainTitle) {
			this.mainTitle = mainTitle;
			return this;
		}

		public DMTitlePanelBuilder minimizeIconClickHandler(
				IconClickEvent minimizeIconClickHandler) {
			this.minimizeIconClickHandler = minimizeIconClickHandler;
			return this;
		}

		public DMTitlePanelBuilder closeIconClickHandler(
				IconClickEvent closeIconClickHandler) {
			this.closeIconClickHandler = closeIconClickHandler;
			return this;
		}
		
		public DMTitlePanelBuilder showCloudStatus(boolean showCloudStatus) {
			this.showCloudStatus = showCloudStatus;
			return this;
		}
	}

	private DMTitlePanelBuilder builder;
	private PubSubEventHandler handler;
	private FXLabelCmp mainLabelCmp;
	private DMCoreServiceController coreServiceController;
	private FXMaterialDesignIcon cloudOnlineIcon;
	private FXMaterialDesignIcon cloudOfflineIcon;

	public DMTitlePanel(DMTitlePanelBuilder builder) {
		this.builder = builder;
		this.coreServiceController = new DMCoreServiceController();
	}

	@Override
	public Pane getView() {
		FXMaterialDesignIconBuilder closeIconBuilder = new FXMaterialDesignIconBuilder(
				MaterialDesignIcon.CLOSE);
		if (this.builder.closeIconClickHandler != null) {
			closeIconBuilder.iconClickEvent(new IconClickEvent() {

				@Override
				public void onClick(Event event) {
					builder.closeIconClickHandler.onClick(event);
				}
			});
		}

		FXMaterialDesignIconBuilder minimizeIconBuilder = new FXMaterialDesignIconBuilder(
				MaterialDesignIcon.MINUS);
		if (this.builder.minimizeIconClickHandler != null) {
			minimizeIconBuilder.iconClickEvent(new IconClickEvent() {

				@Override
				public void onClick(Event event) {
					builder.minimizeIconClickHandler.onClick(event);
				}
			});
		}

		FXMaterialDesignIcon minimizeIcon = new FXMaterialDesignIcon(
				minimizeIconBuilder);
		FXMaterialDesignIcon closeIcon = new FXMaterialDesignIcon(
				closeIconBuilder);
		FXMaterialDesignIcon helpIcon = new FXMaterialDesignIcon(new FXMaterialDesignIconBuilder(MaterialDesignIcon.HELP_CIRCLE));
		
		minimizeIcon.getStyleClass().add("icon-style");
		closeIcon.getStyleClass().add("icon-style");
		helpIcon.getStyleClass().add("icon-style");
		
		HBox box = new HBox();
		box.setAlignment(Pos.CENTER_LEFT);
		
		box.getChildren().addAll(this.getColorPickerBox(), helpIcon, minimizeIcon, closeIcon);
		
		if(builder.showCloudStatus) {
			cloudOnlineIcon = new FXMaterialDesignIcon(
					new FXMaterialDesignIconBuilder(MaterialDesignIcon.CLOUD_CHECK));
			cloudOfflineIcon = new FXMaterialDesignIcon(
					new FXMaterialDesignIconBuilder(
							MaterialDesignIcon.CLOUD_OUTLINE_OFF));
			
			cloudOnlineIcon.getStyleClass().add("icon-style");
			cloudOfflineIcon.getStyleClass().add("cloud-offline");
			
			this.showCloudOnlineStatus(false);
			
			box.getChildren().addAll(1, Arrays.asList(cloudOnlineIcon, cloudOfflineIcon));
		}
		
		BorderPane borderPane = new BorderPane();
		borderPane.setPadding(new Insets(5));

		if (this.builder.subTitle != null) {
			FXLabelCmp title = new FXLabelCmp(new FXLabelCmpBuilder().label(
					this.builder.subTitle).classNames(
					new String[] { "heading2" }));
			HBox titlePanel = new HBox(title);
			titlePanel.setAlignment(Pos.CENTER_LEFT);
			
			borderPane.setLeft(titlePanel);
		}

		mainLabelCmp = new FXLabelCmp(new FXLabelCmpBuilder()
				.label(this.builder.mainTitle).i18n(false).h3(true)
				.classNames(new String[] { "nudi-font-heading3" }));
		if (this.builder.mainTitle != null) {
			borderPane.setCenter(this.mainLabelCmp);
		}
		borderPane.setRight(box);

		subscribeOnMainTitleChange();

		return borderPane;
	}
	
	public void showCloudOnlineStatus(boolean online) {
		this.cloudOnlineIcon.setVisible(online);
		this.cloudOfflineIcon.setVisible(!online);
	}

	private void subscribeOnMainTitleChange() {
		handler = new PubSubEventHandler() {

			@Override
			public void onPubSubData(PubSubEventDataModel data) {
				updateMainTitle(data);
			}
		};
		PubSubEvent.getInstance().subscribe("MAIN_TITLE", handler);
	}

	private void updateMainTitle(PubSubEventDataModel data) {
		this.mainLabelCmp.setText(((DashboardTitlePubSubTopicModel) data)
				.getTitle());
	}

	private HBox getColorPickerBox() {
		FXFontAwesomeIcon paintBrushIcon = new FXFontAwesomeIcon(
				new FXFontAwesomeIconBuilder(FontAwesomeIcon.PAINT_BRUSH));
		paintBrushIcon.getStyleClass().add("menu-icon");
		
		DMMenuItem colorPickerMenu = new DMMenuItem(
				new DMMenuItemBuilder("color-picker")
				.menuTitle("app.appThemeChooser")
				.classNames("small-font-menu")
				.faIcon(paintBrushIcon)
				.verticalAlign(false));
		
		HBox colorPickerBox = new HBox();
		colorPickerBox.getChildren().addAll(colorPickerMenu);
		
		List<DMMenuItem> menuItems = new ArrayList<DMMenuItem>();
		menuItems.add(this.getThemeColorMenuItem("blue", "app.themeColor.blue", AppTheme.BLUE, "#0069c0"));
		menuItems.add(this.getThemeColorMenuItem("indigo", "app.themeColor.indigo", AppTheme.INDIGO, "#002984"));
		menuItems.add(this.getThemeColorMenuItem("teal", "app.themeColor.teal", AppTheme.TEAL, "#00675b"));
		menuItems.add(this.getThemeColorMenuItem("deepOrange", "app.themeColor.deepOrange", AppTheme.DEEP_ORANGE, "#c41c00"));
		menuItems.add(this.getThemeColorMenuItem("dark", "app.themeColor.dark", AppTheme.DARK, "#121212"));
		
		DMContextMenu themeContextMenu = new DMContextMenu(menuItems);
		
		colorPickerBox.setOnMouseClicked(event -> {
			themeContextMenu.show(colorPickerMenu);
		});

		return colorPickerBox;
	}
	
	private DMMenuItem getThemeColorMenuItem(String menuId, String titleKey, AppTheme themeName, String colorCode) {
		DMMenuItem menuItem = new DMMenuItem(new DMMenuItemBuilder(menuId)
				.menuTitle(titleKey)
				.classNames("english-font")
				.menuItemClickEvent(event -> {
					ThemePubSubTopicModel themePubSubTopicModel = new ThemePubSubTopicModel();
					themePubSubTopicModel.setThemeName(themeName);
					ControlsTheme.APP_THEME = themeName;

					SettingsModel model = this.coreServiceController.getSettingModel(SettingTableKeys.APP_THEME_COLOR,
							AppTheme.INDIGO.toString());
					model.setValue(themeName.toString());
					
					this.coreServiceController.updateSetting(model);
					
					PubSubEvent.getInstance().publish("THEME",
							themePubSubTopicModel);
				}));
		
		menuItem.setStyle(String.format("-fx-background-color: %s;", colorCode));
		
		return menuItem;
	}
}
