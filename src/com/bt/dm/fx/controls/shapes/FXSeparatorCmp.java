/**
 * 
 */
package com.bt.dm.fx.controls.shapes;

import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.VPos;
import javafx.scene.control.Separator;

/**
 * @author Ramalingesha ML
 *
 *         Created on Oct 1, 2020 8:27:24 PM
 */
public class FXSeparatorCmp extends Separator {
	public static class FXSeparatorBuilder {
		private boolean vertical;
		private Double height;
		private Double width;
		private VPos vAlignment;
		private HPos hAlignment;

		public FXSeparatorBuilder vertical(boolean vertical) {
			this.vertical = vertical;
			return this;
		}

		public FXSeparatorBuilder height(Double height) {
			this.height = height;
			return this;
		}

		public FXSeparatorBuilder width(Double width) {
			this.width = width;
			return this;
		}

		public FXSeparatorBuilder vAlignment(VPos vAlignment) {
			this.vAlignment = vAlignment;
			return this;
		}

		public FXSeparatorBuilder hAlignment(HPos hAlignment) {
			this.hAlignment = hAlignment;
			return this;
		}
	}

	private FXSeparatorBuilder builder;

	public FXSeparatorCmp(FXSeparatorBuilder builder) {
		this.builder = builder;
		this.createComponent();
	}

	private void createComponent() {
		if (this.builder.vertical) {
			this.setOrientation(Orientation.VERTICAL);
		}
		if (this.builder.height != null) {
			this.setPrefHeight(this.builder.height);
		}
		if (this.builder.width != null) {
			this.setPrefWidth(this.builder.width);
		}
		if (this.builder.vAlignment != null) {
			this.setValignment(this.builder.vAlignment);
		}
		if (this.builder.hAlignment != null) {
			this.setHalignment(this.builder.hAlignment);
		}
	}
}
