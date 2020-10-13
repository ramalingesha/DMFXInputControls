/**
 * 
 */
package com.bt.dm.fx.controls.image;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author Ramalingesha ML
 *
 *         Created on Oct 10, 2020 5:45:58 PM
 */
public class FXImage extends ImageView {
	public static class FXImageBuilder {
		private String imagePath;
		private Integer width;
		private Integer height;

		public FXImageBuilder(String imagePath) {
			this.imagePath = imagePath;
		}

		public FXImageBuilder width(int width) {
			this.width = width;
			return this;
		}

		public FXImageBuilder height(int height) {
			this.height = height;
			return this;
		}
	}

	private FXImageBuilder builder;

	public FXImage(FXImageBuilder builder) {
		this.builder = builder;
		this.createComponent();
	}

	private void createComponent() {
		Image image = new Image(this.builder.imagePath);
		this.setImage(image);
		this.setSmooth(true);
		this.setCache(true);

		if (this.builder.width != null) {
			this.setFitWidth(this.builder.width);
		}
		if (this.builder.height != null) {
			this.setFitHeight(this.builder.height);
		} else {
			this.setPreserveRatio(true);
		}
	}
}
