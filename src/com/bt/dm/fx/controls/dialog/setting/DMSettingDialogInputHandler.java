package com.bt.dm.fx.controls.dialog.setting;

import com.bt.dm.core.i18n.DMMessageLocalizer;
import com.bt.dm.fx.controls.utils.ControlSize;

import javafx.scene.layout.Pane;

/**
 * @author Ramalingesha ML
 *
 *         Created on Oct 07, 2022 07:45:03 AM
 */
public interface DMSettingDialogInputHandler {
	public Pane getSettingInputPanel();

	public void onSettingButtonClick();

	public default String getSettingDialogTitle() {
		return DMMessageLocalizer.getLabel("reportSetting.dialog.title");
	}

	public ControlSize getSettingDialogSize();
}
