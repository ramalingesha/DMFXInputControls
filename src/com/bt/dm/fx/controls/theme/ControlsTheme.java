/**
 * 
 */
package com.bt.dm.fx.controls.theme;

/**
 * @author Ramalingesha ML
 *
 *         Created on Dec 13, 2020 7:54:07 PM
 */
public final class ControlsTheme {
	public static AppTheme APP_THEME = AppTheme.TEAL;

	public static class Blue {
		public static class Colors {
			public static String PRIMARY = "#2196f3";
			public static String PRIMARY_DARK = "#0069c0";
			public static String PRIMARY_LIGHT = "#6ec6ff";
			public static String SECONDARY = "#ffab91";
			public static String SECONDARY_DARK = "#c97b63";
			public static String SECONDARY_LIGHT = "#ffddc1";
		}
		
		public static class TextColors {
			public static String BLACK = "#232323";
			public static String WHITE = "#fafafa";
		}
	}

	public static String getThemeCssFileName(String componentName) {
		return String.format("%s_%s.css", componentName, APP_THEME.toString());
	}
}
