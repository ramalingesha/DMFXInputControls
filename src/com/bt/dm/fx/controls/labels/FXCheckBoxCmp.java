/**
 * 
 */
package com.bt.dm.fx.controls.labels;

import javafx.scene.control.CheckBox;

import com.bt.dm.core.pubsub.PubSubEvent;
import com.bt.dm.core.pubsub.PubSubEventDataModel;
import com.bt.dm.core.pubsub.PubSubEventHandler;
import com.bt.dm.fx.controls.DMLabelBuilder;
import com.bt.dm.fx.controls.DMLabelControl;
import com.bt.dm.fx.controls.theme.ControlsTheme;

/**
 * @author Ramalingesha ML
 *
 *         Created on Oct 9, 2020 7:25:35 AM
 */
public class FXCheckBoxCmp extends CheckBox {
	public static class FXCheckBoxCmpBuilder extends DMLabelBuilder {
	}

	private PubSubEventHandler handler;

	public FXCheckBoxCmp(DMLabelBuilder builder) {
		this.initComponent(builder);
	}

	private void initComponent(DMLabelBuilder builder) {
		DMLabelControl.applyDefaults(this, builder);
		this.addRootThemeStyleSheet();
		this.subscribeOnThemeChange();
	}

	private void subscribeOnThemeChange() {
		handler = new PubSubEventHandler() {

			@Override
			public void onPubSubData(PubSubEventDataModel data) {
				addRootThemeStyleSheet();
			}
		};
		PubSubEvent.getInstance().subscribe("THEME", handler);
	}

	private void addRootThemeStyleSheet() {
		this.getStylesheets().clear();
		this.getStylesheets().add(
				getClass().getClassLoader().getResource("com/bt/dm/fx/controls/DMControl.css").toExternalForm());
		this.getStylesheets().add(
				getClass().getClassLoader().getResource(
						"com/bt/dm/fx/controls/" + ControlsTheme.getThemeCssFileName("DMControl"))
						.toExternalForm());
	}
}
