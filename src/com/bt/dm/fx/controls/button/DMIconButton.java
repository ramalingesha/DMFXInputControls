/**
 * 
 */
package com.bt.dm.fx.controls.button;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import com.bt.dm.core.pubsub.PubSubEvent;
import com.bt.dm.core.pubsub.PubSubEventDataModel;
import com.bt.dm.core.pubsub.PubSubEventHandler;
import com.bt.dm.fx.controls.events.IconClickEvent;
import com.bt.dm.fx.controls.labels.FXFontAwesomeIcon;
import com.bt.dm.fx.controls.labels.FXFontAwesomeIcon.FXFontAwesomeIconBuilder;
import com.bt.dm.fx.controls.labels.FXLabelCmp;
import com.bt.dm.fx.controls.labels.FXLabelCmp.FXLabelCmpBuilder;
import com.bt.dm.fx.controls.labels.FXMaterialDesignIcon;
import com.bt.dm.fx.controls.labels.FXMaterialDesignIcon.FXMaterialDesignIconBuilder;
import com.bt.dm.fx.controls.theme.ControlsTheme;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;

/**
 * @author Ramalingesha ML
 *
 *         Created on Mar 23, 2021 4:35:19 PM
 */
public class DMIconButton extends Pane {
	public static class DMIconButtonBuilder {
		private String title;
		private MaterialDesignIcon materialIcon;
		private FontAwesomeIcon faIcon;
		private IconClickEvent buttonClickEvent;

		public DMIconButtonBuilder(String title) {
			this.title = title;
		}

		public DMIconButtonBuilder materialIcon(MaterialDesignIcon materialIcon) {
			this.materialIcon = materialIcon;
			return this;
		}

		public DMIconButtonBuilder faIcon(FontAwesomeIcon faIcon) {
			this.faIcon = faIcon;
			return this;
		}

		public DMIconButtonBuilder buttonClickEvent(
				IconClickEvent buttonClickEvent) {
			this.buttonClickEvent = buttonClickEvent;
			return this;
		}
	}

	private DMIconButtonBuilder builder;
	private PubSubEventHandler handler;
	private HBox box;

	public DMIconButton(DMIconButtonBuilder builder) {
		this.builder = builder;
		this.createIconButton();
		this.setPadding(new Insets(5));
	}

	private void createIconButton() {
		box = new HBox(5);
		box.getStyleClass().add("icon-button");
		box.setAlignment(Pos.BASELINE_CENTER);

		// Add material icon or fa icon whichever is passed
		if (this.builder.materialIcon != null) {
			FXMaterialDesignIcon materialDesignIcon = new FXMaterialDesignIcon(
					new FXMaterialDesignIconBuilder(this.builder.materialIcon)
							.size("32px"));
			materialDesignIcon.getStyleClass().add("menu-icon");

			box.getChildren().add(materialDesignIcon);
		} else if (this.builder.faIcon != null) {
			FXFontAwesomeIcon fontAwesomeIcon = new FXFontAwesomeIcon(
					new FXFontAwesomeIconBuilder(this.builder.faIcon));
			fontAwesomeIcon.getStyleClass().add("menu-icon");

			box.getChildren().add(fontAwesomeIcon);
		}

		// Add menu title if it passed
		FXLabelCmp title = new FXLabelCmp(new FXLabelCmpBuilder()
				.label(this.builder.title).h3(true).align(Pos.CENTER));
		box.getChildren().add(title);

		box.setCursor(Cursor.HAND);
		box.setOnMouseClicked(event -> {
			if (this.builder.buttonClickEvent != null) {
				this.builder.buttonClickEvent.onClick(event);
			}
		});

		this.addRootThemeStyleSheet();
		this.subscribeOnThemeChange();
		this.getChildren().add(box);
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
		box.getStylesheets().clear();
		box.getStylesheets().add(
				getClass().getResource("DMIconButton.css").toExternalForm());
		box.getStylesheets().add(
				getClass().getResource(
						ControlsTheme.getThemeCssFileName("DMIconButton"))
						.toExternalForm());
	}
}
