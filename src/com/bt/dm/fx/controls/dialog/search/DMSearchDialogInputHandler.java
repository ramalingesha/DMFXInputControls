/**
 * 
 */
package com.bt.dm.fx.controls.dialog.search;

import com.bt.dm.fx.controls.utils.ControlSize;

import javafx.scene.layout.Pane;

/**
 * @author Ramalingesha ML
 *
 *         Created on Sep 26, 2022 06:00:03 PM
 */
public interface DMSearchDialogInputHandler {
	public Pane getSearchInputPanel();

	public void onSearchButtonClick();

	public default String getSearchDialogTitle() {
		return "";
	}

	public ControlSize getSearchDialogSize();

	public default String getSearchButtonText() {
		return "app.searchButtonText";
	}
}
