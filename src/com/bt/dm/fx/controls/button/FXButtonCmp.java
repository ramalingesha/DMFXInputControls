/**
 * 
 */
package com.bt.dm.fx.controls.button;

import com.bt.dm.fx.controls.labels.FXFontAwesomeIcon;
import com.bt.dm.fx.controls.labels.FXMaterialDesignIcon;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.Button;

/**
 * @author Ramalingesha ML
 *
 *         Created on Oct 8, 2020 1:53:02 PM
 */
public class FXButtonCmp extends Button {
	public static class FXButtonCmpBuilder {
		private String text;
		private FXFontAwesomeIcon faIcon;
		private FXMaterialDesignIcon materialIcon;
		private EventHandler<ActionEvent> eventHandler;

		public FXButtonCmpBuilder text(String text) {
			this.text = text;
			return this;
		}

		public FXButtonCmpBuilder faIcon(FXFontAwesomeIcon faIcon) {
			this.faIcon = faIcon;
			return this;
		}

		public FXButtonCmpBuilder materialIcon(FXMaterialDesignIcon materialIcon) {
			this.materialIcon = materialIcon;
			return this;
		}

		public FXButtonCmpBuilder eventHandler(
				EventHandler<ActionEvent> eventHandler) {
			this.eventHandler = eventHandler;
			return this;
		}
	}

	private FXButtonCmpBuilder builder;

	public FXButtonCmp(FXButtonCmpBuilder builder) {
		this.builder = builder;
		this.createComponent();
	}

	private void createComponent() {
		this.setText(this.builder.text != null ? this.builder.text : "");

		if (this.builder.materialIcon != null) {
			this.setGraphic(this.builder.materialIcon);
		} else if (this.builder.faIcon != null) {
			this.setGraphic(this.builder.faIcon);
		}

		if (this.builder.eventHandler != null) {
			this.setOnAction(this.builder.eventHandler);
		}
		
		this.setCursor(Cursor.HAND);
	}
}
