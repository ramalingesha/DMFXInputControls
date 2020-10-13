/**
 * 
 */
package com.bt.dm.fx.controls.labels;

import com.bt.dm.fx.controls.events.IconClickEvent;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
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
	}

	public FXFontAwesomeIcon(FXFontAwesomeIconBuilder builder) {
		super(builder.icon);
		this.setDefaults(builder);
	}

	private void setDefaults(FXFontAwesomeIconBuilder builder) {
		this.setSize(builder.size);
		this.setCursor(Cursor.HAND);
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
