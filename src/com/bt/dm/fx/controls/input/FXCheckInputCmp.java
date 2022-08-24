/**
 * 
 */
package com.bt.dm.fx.controls.input;

import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;

import com.bt.dm.core.pubsub.PubSubEvent;
import com.bt.dm.core.pubsub.PubSubEventDataModel;
import com.bt.dm.core.pubsub.PubSubEventHandler;
import com.bt.dm.core.utils.RandomNumberGenerator;
import com.bt.dm.fx.controls.DMControl;
import com.bt.dm.fx.controls.DMControlBuilder;
import com.bt.dm.fx.controls.theme.ControlsTheme;

/**
 * @author Ramalingesha ML
 *
 *         Created on Oct 9, 2020 8:55:23 AM
 */
public class FXCheckInputCmp extends DMControl<Boolean> {
	public static class FXCheckInputCmpBuilder extends DMControlBuilder {
	}

	private CheckBox checkBox;
	private PubSubEventHandler handler;

	public FXCheckInputCmp(DMControlBuilder builder) {
		super(builder);
		this.createComponent();
	}

	@Override
	public void createComponent() {
		this.checkBox = new CheckBox();

		this.getChildren()
				.add(this.constructControlWithDefaults(this.checkBox));
		this.addRootThemeStyleSheet();
		this.subscribeOnThemeChange();
	}

	@Override
	public Boolean getValue() {
		return this.checkBox.isSelected();
	}

	@Override
	public void setValue(Boolean value) {
		this.checkBox.setSelected(value);
	}

	@Override
	public String generateUniqueId() {
		return "checkInputCmp_"
				+ RandomNumberGenerator.getInstance().getRandomNumber(8);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public Control getControl() {
		return this.checkBox;
	}

	@Override
	public void clear() {
		this.checkBox.setSelected(false);
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
