/**
 * 
 */
package com.bt.dm.fx.controls.labels;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Cursor;

import com.bt.dm.fx.controls.events.IconClickEvent;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;

/**
 * 
 * @author Ramalingesha ML
 *
 *         Created on Oct 4, 2020 5:23:43 PM
 */
public class FXMaterialDesignIcon extends MaterialDesignIconView {
	public static class FXMaterialDesignIconBuilder {
		private MaterialDesignIcon icon;
		private String size = "2em";
		private IconClickEvent iconClickEvent;
		private String className;

		public FXMaterialDesignIconBuilder(MaterialDesignIcon icon) {
			this.icon = icon;
		}

		public FXMaterialDesignIconBuilder size(String size) {
			this.size = size;
			return this;
		}

		public FXMaterialDesignIconBuilder iconClickEvent(
				IconClickEvent iconClickEvent) {
			this.iconClickEvent = iconClickEvent;
			return this;
		}

		public FXMaterialDesignIconBuilder className(String className) {
			this.className = className;
			return this;
		}
	}

	public FXMaterialDesignIcon(FXMaterialDesignIconBuilder builder) {
		super(builder.icon);
		this.setDefaults(builder);
	}

	private void setDefaults(FXMaterialDesignIconBuilder builder) {
		this.setSize(builder.size);
		this.setCursor(Cursor.HAND);
		this.getStyleClass().add("dm-icon");

		if (builder.className != null) {
			this.getStyleClass().add(builder.className);
		}

		this.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				if (builder.iconClickEvent != null) {
					builder.iconClickEvent.onClick(event);
				}
			}
		});
	}

	public void changeIcon(MaterialDesignIcon icon) {
		this.setIcon(icon);
	}
}
