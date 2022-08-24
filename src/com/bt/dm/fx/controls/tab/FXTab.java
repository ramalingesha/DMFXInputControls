/**
 * 
 */
package com.bt.dm.fx.controls.tab;

import com.bt.dm.core.i18n.DMMessageLocalizer;

import javafx.scene.Node;
import javafx.scene.control.Tab;

/**
 * @author Ramalingesha ML
 *
 *         Created on Nov 21, 2020 6:53:19 PM
 */
public class FXTab extends Tab {
	private String title;
	private Node contentPanel;

	public FXTab(String title, Node contentPanel) {
		this.title = DMMessageLocalizer.getLabel(title);
		this.contentPanel = contentPanel;

		this.createComponent();
	}

	private void createComponent() {
		this.setText(title);
		this.setContent(contentPanel);
	}
}
