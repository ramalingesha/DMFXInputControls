package com.bt.dm.fx.controls.labels;

import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

import com.bt.dm.core.pubsub.PubSubEvent;
import com.bt.dm.core.pubsub.PubSubEventDataModel;
import com.bt.dm.core.pubsub.PubSubEventHandler;
import com.bt.dm.fx.controls.DMLabelBuilder;
import com.bt.dm.fx.controls.DMLabelControl;
import com.bt.dm.fx.controls.theme.ControlsTheme;

/**
 * 
 * @author Ramalingesha ML
 *
 *         Created on Nov 28, 2021 6:44:13 AM
 */
public class FXRadioButtonCmp extends RadioButton {
	public static class FXRadioButtonCmpBuilder extends DMLabelBuilder {
		private ToggleGroup group;

		public FXRadioButtonCmpBuilder group(ToggleGroup group) {
			this.group = group;
			return this;
		}

		public ToggleGroup getGroup() {
			return group;
		}
	}

	private PubSubEventHandler handler;

	public FXRadioButtonCmp(DMLabelBuilder builder) {
		this.initComponent((FXRadioButtonCmpBuilder) builder);
	}

	private void initComponent(FXRadioButtonCmpBuilder builder) {
//		String[] classNames = builder.getClassNames();
//		if (classNames == null || classNames.length == 0) {
//			builder.classNames(new String[] { "input-label" });
//		}

		DMLabelControl.applyDefaults(this, builder);

		if (builder.getGroup() != null) {
			this.setToggleGroup(builder.getGroup());
		}

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
				getClass().getClassLoader()
						.getResource("com/bt/dm/fx/controls/DMControl.css")
						.toExternalForm());
		this.getStylesheets()
				.add(getClass()
						.getClassLoader()
						.getResource(
								"com/bt/dm/fx/controls/"
										+ ControlsTheme
												.getThemeCssFileName("DMControl"))
						.toExternalForm());
	}
}
