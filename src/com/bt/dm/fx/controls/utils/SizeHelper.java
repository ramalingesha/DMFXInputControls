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
	public static ControlSize INPUT_CONTROL_SMALL_SIZE;

	static {
		computeControlSizeValues();
	}

	private static void computeControlSizeValues() {
		Rectangle2D dimensions = Screen.getPrimary().getBounds();
		SizeHelper.SCREEN_SIZE = new ControlSize(dimensions.getWidth(),
				dimensions.getHeight());

		SizeHelper.LABEL_SIZE = new ControlSize(
				SizeHelper.SCREEN_SIZE.getWidth() * 0.12, 30);
		SizeHelper.INPUT_CONTROL_SIZE = new ControlSize(
				SizeHelper.SCREEN_SIZE.getWidth() * 0.15, 30);
		SizeHelper.INPUT_CONTROL_SMALL_SIZE = new ControlSize(
				SizeHelper.SCREEN_SIZE.getWidth() * 0.07, 30);
	}
}
