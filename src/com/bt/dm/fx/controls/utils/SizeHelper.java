/**
 * 
 */
package com.bt.dm.fx.controls.utils;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

/**
 * @author Ramalingesha ML
 *
 *         Created on Sep 22, 2020 9:01:48 PM
 */
public final class SizeHelper {
	public static ControlSize SCREEN_SIZE;
	public static ControlSize LABEL_SIZE;
	public static ControlSize INPUT_CONTROL_SIZE;
	public static ControlSize MULTI_LINE_INPUT_CONTROL_SIZE;
	public static ControlSize INPUT_CONTROL_SMALL_SIZE;
	private static boolean LARGE_SCREEN;
	public static ControlSize MAIN_PANEL_SIZE;
	public static double ERROR_DIALOG_WIDTH;
	public static double ERROR_DIALOG_HEIGHT;

	static {
		computeControlSizeValues();
	}

	private static void computeControlSizeValues() {
		Rectangle2D dimensions = Screen.getPrimary().getBounds();
		double width = dimensions.getWidth() > 1366 ? 1366 : dimensions.getWidth();
		double height = dimensions.getHeight() > 768 ? 768 : dimensions.getHeight();
//		double width = dimensions.getWidth();
//		double height = dimensions.getHeight();

		double mainPanelWidthOffset = 104;
		double mainPanelHeightOffset = 60;

		SizeHelper.SCREEN_SIZE = new ControlSize(width, height);
		SizeHelper.LARGE_SCREEN = dimensions.getWidth() >= width;

		SizeHelper.LABEL_SIZE = new ControlSize(SizeHelper.SCREEN_SIZE.getWidth() * 0.12, 30);
		SizeHelper.INPUT_CONTROL_SIZE = new ControlSize(SizeHelper.SCREEN_SIZE.getWidth() * 0.15, 30);
		SizeHelper.MULTI_LINE_INPUT_CONTROL_SIZE = new ControlSize(SizeHelper.SCREEN_SIZE.getWidth() * 0.15, 100);
		SizeHelper.INPUT_CONTROL_SMALL_SIZE = new ControlSize(SizeHelper.SCREEN_SIZE.getWidth() * 0.07, 30);
		SizeHelper.MAIN_PANEL_SIZE = new ControlSize(SizeHelper.SCREEN_SIZE.getWidth() - mainPanelWidthOffset,
				SizeHelper.SCREEN_SIZE.getHeight() - mainPanelHeightOffset);
		
		SizeHelper.ERROR_DIALOG_WIDTH = SizeHelper.SCREEN_SIZE.getWidth() * 0.65;
		SizeHelper.ERROR_DIALOG_HEIGHT = SizeHelper.SCREEN_SIZE.getHeight() * 0.5;
	}

	public static boolean isLargeScreen() {
		return LARGE_SCREEN;
	}
}
