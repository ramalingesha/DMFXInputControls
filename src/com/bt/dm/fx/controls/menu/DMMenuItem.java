/**
 * 
 */
package com.bt.dm.fx.controls.menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

import com.bt.dm.core.pubsub.PubSubEvent;
import com.bt.dm.core.pubsub.PubSubEventDataModel;
import com.bt.dm.core.pubsub.PubSubEventHandler;
import com.bt.dm.core.utils.DMStringUtils;
import com.bt.dm.fx.controls.DMLabelBuilder;
import com.bt.dm.fx.controls.events.IconClickEvent;
import com.bt.dm.fx.controls.labels.FXFontAwesomeIcon;
import com.bt.dm.fx.controls.labels.FXLabelCmp;
import com.bt.dm.fx.controls.labels.FXLabelCmp.FXLabelCmpBuilder;
import com.bt.dm.fx.controls.labels.FXMaterialDesignIcon;
import com.bt.dm.fx.controls.theme.ControlsTheme;

/**
 * @author Ramalingesha ML
 *
 *         Created on Jan 1, 2021 11:59:58 AM
 */
public class DMMenuItem extends Pane {
	public static class DMMenuItemBuilder {
		private String menuId;
		private String menuTitle;
		private FXMaterialDesignIcon materialIcon;
		private FXFontAwesomeIcon faIcon;
		private boolean verticalAlign = true;
		private IconClickEvent menuItemClickEvent;
		private String classNames;

		public DMMenuItemBuilder(String menuId) {
			this.menuId = menuId;
		}

		public DMMenuItemBuilder menuTitle(String menuTitle) {
			this.menuTitle = menuTitle;
			return this;
		}

		public DMMenuItemBuilder materialIcon(FXMaterialDesignIcon materialIcon) {
			this.materialIcon = materialIcon;
			return this;
		}

		public DMMenuItemBuilder faIcon(FXFontAwesomeIcon faIcon) {
			this.faIcon = faIcon;
			return this;
		}

		public DMMenuItemBuilder classNames(String classNames) {
			this.classNames = classNames;
			return this;
		}

		public DMMenuItemBuilder verticalAlign(boolean verticalAlign) {
			this.verticalAlign = verticalAlign;
			return this;
		}

		public DMMenuItemBuilder menuItemClickEvent(
				IconClickEvent menuItemClickEvent) {
			this.menuItemClickEvent = menuItemClickEvent;
			return this;
		}
	}

	private DMMenuItemBuilder builder;
	private PubSubEventHandler handler;

	public DMMenuItem(DMMenuItemBuilder builder) {
		this.builder = builder;
		this.createIconMenu();
		this.setPadding(new Insets(5, 0, 5, 0));
	}

	private void createIconMenu() {
		this.addRootThemeStyleSheet();
		this.getStyleClass().add("menu-item");

		Pane box;

		// Create vbox when vertical align is enabled otherwise create hbox
		if (this.builder.verticalAlign) {
			box = new VBox();
			box.getStyleClass().add("vertical-menu");
			((VBox) box).setAlignment(Pos.CENTER);
		} else {
			box = new HBox(5);
			box.getStyleClass().add("menu");
			((HBox) box).setAlignment(Pos.CENTER);
		}

		// Add material icon or fa icon whichever is passed
		if (this.builder.materialIcon != null) {
			this.builder.materialIcon.getStyleClass().add("menu-icon");
			box.getChildren().add(this.builder.materialIcon);
		} else if (this.builder.faIcon != null) {
			this.builder.faIcon.getStyleClass().add("menu-icon");
			box.getChildren().add(this.builder.faIcon);
		}

		// Add menu title if it passed
		if (this.builder.menuTitle != null) {
			DMLabelBuilder labelCmpBuilder = new FXLabelCmpBuilder()
					.label(this.builder.menuTitle)
					.classNames(new String[] { "menu-title" })
					.align(Pos.CENTER).textAlign(TextAlignment.CENTER);

			List<String> classNames = new ArrayList<String>();
			classNames.add("menu-title");

			if (!DMStringUtils.isEmpty(this.builder.classNames)) {
				classNames.addAll(Arrays.asList(this.builder.classNames
						.split(" ")));
			}

			labelCmpBuilder.classNames(classNames.toArray(new String[0]));

			FXLabelCmp title = new FXLabelCmp(labelCmpBuilder);

			box.getChildren().add(title);
		}

		box.setId(this.builder.menuId);
		box.setCursor(Cursor.HAND);
		box.setOnMouseClicked(event -> {
			if (this.builder.menuItemClickEvent != null) {
				this.builder.menuItemClickEvent.onClick(event);
			}
		});

		this.subscribeOnThemeChange();
		this.getChildren().add(box);
	}

	public void select() {
		this.getStyleClass().add("active");
	}

	public void unSelect() {
		this.getStyleClass().remove("active");
	}

	private void subscribeOnThemeChange() {
		handler = new PubSubEventHandler() {

			@Override
			public void onPubSubData(PubSubEventDataModel data) {
				addRootThemeStyleSheet();
			}
		};
		PubSubEvent.getInstance().subscribe("THEME", handler);
	}

	private void addRootThemeStyleSheet() {
		this.getStylesheets().clear();
		this.getStylesheets().add(
				getClass().getResource("MenuItem.css").toExternalForm());
		this.getStylesheets().add(
				getClass().getResource(
						ControlsTheme.getThemeCssFileName("MenuItem"))
						.toExternalForm());
	}
}
