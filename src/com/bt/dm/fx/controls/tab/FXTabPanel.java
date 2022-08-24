/**
 * 
 */
package com.bt.dm.fx.controls.tab;

import com.bt.dm.core.pubsub.PubSubEvent;
import com.bt.dm.core.pubsub.PubSubEventDataModel;
import com.bt.dm.core.pubsub.PubSubEventHandler;
import com.bt.dm.fx.controls.theme.ControlsTheme;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/**
 * @author Ramalingesha ML
 *
 *         Created on Nov 21, 2020 6:52:07 PM
 */
public class FXTabPanel extends TabPane {

	private PubSubEventHandler handler;

	public FXTabPanel() {
		this(FXCollections.emptyObservableList());
	}

	public FXTabPanel(ObservableList<Tab> tabs) {
		this.createComponent(tabs);
	}

	private void createComponent(ObservableList<Tab> tabs) {
		this.addRootThemeStyleSheet();

		this.getTabs().addAll(tabs);
		this.setTabMinWidth(80);
		this.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

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
				getClass().getResource("TabPanel.css").toExternalForm());
		this.getStylesheets().add(
				getClass().getResource(
						ControlsTheme.getThemeCssFileName("TabPanel"))
						.toExternalForm());
	}

	public void addTab(Tab tab) {
		this.getTabs().add(tab);
	}

	public void addTabs(Tab... tabs) {
		this.getTabs().addAll(tabs);
	}

	public void removeTab(int index) {
		this.getTabs().remove(index);
	}

	public void selectTab(int index) {
		this.getSelectionModel().select(index);	
	}
}
