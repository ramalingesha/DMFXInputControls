package com.bt.dm.fx.controls.utils;

import java.io.InputStream;

import com.bt.dm.core.i18n.AppLocale;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Fonts {
	private Font defaultFont;
	private Font defaultHeaderFont;
	private Font englishFont;
	private Font nudiFont;
	private Font englishHeaderFont;
	private Font nudiHeaderFont;
	private AppLocale appLocale;

	private static Fonts instance;

	private Fonts() {
		if (instance != null) {
			throw new RuntimeException(
					"Use getInstance() method to get the single instance of this class.");
		}
	}

	public static Fonts getInstance() {
		if (instance == null) {
			instance = new Fonts();
			instance.loadFonts();
		}

		return instance;
	}

	private void loadFonts() {
		englishFont = this.loadFont("Roboto-Regular.ttf");
		nudiFont = this.loadFont("Nudi01e.ttf");

		// Load noto sans regular and medium fonts
		// these fonts will be loaded from css file
		this.loadFont("NotoSansKannada-Light.ttf");
		this.loadFont("NotoSansKannada-Regular.ttf");
		this.loadFont("NotoSansKannada-Medium.ttf");
		this.loadFont("NotoSansKannada-Medium.ttf");
//		this.loadFont("Nudi 01 k b.ttf");
//		this.loadFont("Nudi 01 k.ttf");
	}

	public void setLocaleFonts(AppLocale appLocale) {
		instance.appLocale = appLocale;
		double screenWidth = SizeHelper.SCREEN_SIZE.getWidth();
		int baseFontSize = 12;
		if (screenWidth >= 1600) {
			baseFontSize = 16;
		} else if (screenWidth >= 1360) {
			baseFontSize = 20;
		} else if (screenWidth >= 1280) {
			baseFontSize = 16;
		} else if (screenWidth >= 1024) {
			baseFontSize = 16;
		}

		switch (appLocale) {
		case KANNADA:
			englishFont = getEnglishFont(FontWeight.BOLD, baseFontSize);
			nudiFont = getNudiFont(FontWeight.BOLD, baseFontSize);

			englishHeaderFont = getEnglishFont(FontWeight.BOLD,
					baseFontSize + 2);
			nudiHeaderFont = getNudiFont(FontWeight.BOLD, baseFontSize + 2);

			defaultFont = nudiFont;
			defaultHeaderFont = nudiHeaderFont;
			break;
		case ENGLISH:
			englishFont = getEnglishFont(FontWeight.BOLD, baseFontSize);
			nudiFont = getNudiFont(FontWeight.BOLD, baseFontSize);

			englishHeaderFont = getEnglishFont(FontWeight.BOLD,
					baseFontSize + 2);
			nudiHeaderFont = getNudiFont(FontWeight.BOLD, baseFontSize + 2);

			defaultFont = englishFont;
			defaultHeaderFont = englishHeaderFont;
			break;

		default:
			break;
		}
	}

	public Font getDefaultFont() {
		return this.defaultFont;
	}

	public Font getDefaultHeaderFont() {
		return this.defaultHeaderFont;
	}

	public Font getEnglishFont() {
		return this.englishFont;
	}

	public Font getNudiFont() {
		return this.nudiFont;
	}

	public Font getEnglishHeaderFont() {
		return this.englishHeaderFont;
	}

	public Font getNudiHeaderFont() {
		return this.nudiHeaderFont;
	}

	public Font getEnglishFont(FontWeight fontWeight, int size) {
		return Font.font(englishFont.getFamily(), fontWeight, size);
	}

	public Font getNudiFont(FontWeight fontWeight, int size) {
		return Font.font(nudiFont.getFamily(), fontWeight, size);
	}

	private Font loadFont(String fontName) {
		InputStream is = Fonts.class.getClassLoader().getResourceAsStream(
				"com/bt/dm/fx/controls/resources/fonts/" + fontName);
		Font font = Font.loadFont(is, 12);

		return font;
	}

	public AppLocale getAppLocale() {
		return instance.appLocale;
	}
}
