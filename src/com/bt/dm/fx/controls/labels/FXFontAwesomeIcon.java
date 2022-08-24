/**
 * 
 */
package com.bt.dm.fx.controls.labels;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Cursor;

import com.bt.dm.fx.controls.events.IconClickEvent;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

/**
 * @author Ramalingesha ML
 *
 *         Created on Oct 4, 2020 9:51:17 AM
 */
public class FXFontAwesomeIcon extends FontAwesomeIconView {
	public static class FXFontAwesomeIconBuilder {
		private FontAwesomeIcon icon;
		private String size = "2em";
		private IconClickEvent faIconClickEvent;
		private String className;

		public FXFontAwesomeIconBuilder(FontAwesomeIcon icon) {
			this.icon = icon;
		}

		public FXFontAwesomeIconBuilder size(String size) {
			this.size = size;
			return this;
		}

		public FXFontAwesomeIconBuilder faIconClickEvent(
				IconClickEvent faIconClickEvent) {
			this.faIconClickEvent = faIconClickEvent;
			return this;
		}

		public FXFontAwesomeIconBuilder className(String className) {
			this.className = className;
			return this;
		}
	}

	public FXFontAwesomeIcon(FXFontAwesomeIconBuilder builder) {
		super(builder.icon);
		this.setDefaults(builder);
	}

	private void setDefaults(FXFontAwesomeIconBuilder builder) {
		this.setSize(builder.size);
		this.setCursor(Cursor.HAND);
		this.getStyleClass().add("dm-icon");

		if (builder.className != null) {
			this.getStyleClass().add(builder.className);
		}

		this.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				if (builder.faIconClickEvent != null) {
					builder.faIconClickEvent.onClick(event);
				}
			}
		});
	}
}
