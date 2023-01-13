package com.bt.dm.fx.controls.text;

import com.bt.dm.core.utils.DMStringUtils;

import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * @author Ramalingesha ML
 *
 *         Created on Jan 7, 2023 08:46:30 AM
 */
public class FXTextCmp extends Text {
	public static class FXTextCmpBuilder {
		private String text;
		private String classNames;
		private Double wrappingWidth;
		private TextAlignment alignment;
		private boolean englishFont;
		
		public FXTextCmpBuilder text(String text) {
			this.text = text;
			return this;
		}
		
		public FXTextCmpBuilder classNames(String classNames) {
			this.classNames = classNames;
			return this;
		}
		
		public FXTextCmpBuilder wrappingWidth(Double wrappingWidth) {
			this.wrappingWidth = wrappingWidth;
			return this;
		}
		
		public FXTextCmpBuilder alignment(TextAlignment alignment) {
			this.alignment = alignment;
			return this;
		}
		
		public FXTextCmpBuilder englishFont(boolean englishFont) {
			this.englishFont = englishFont;
			return this;
		}
	}

	public FXTextCmp(FXTextCmpBuilder builder) {
		this.initTextField(builder);
	}

	private void initTextField(FXTextCmpBuilder builder) {
		this.setText(builder.text);

		if (builder.wrappingWidth != null) {
			this.setWrappingWidth(builder.wrappingWidth);
		}
		
		if(builder.englishFont) {
			this.getStyleClass().add("english-font");
		} else {
			this.getStyleClass().add("kannada-font");
		}

		if (!DMStringUtils.isEmpty(builder.classNames)) {
			this.getStyleClass().addAll(builder.classNames.split(" "));
		}

		if (builder.alignment != null) {
			this.setTextAlignment(builder.alignment);
		}
	}
}
