/**
 * 
 */
package com.bt.dm.fx.controls.utils;

import javafx.scene.layout.Region;

/**
 * @author Ramalingesha ML
 *
 *         Created on Oct 1, 2020 8:17:08 PM
 */
public class UIHelper {
	public static Region getHorizontalSpace(double width) {
		Region spacer = new Region();
		spacer.setPrefWidth(width);

		return spacer;
	}
}
