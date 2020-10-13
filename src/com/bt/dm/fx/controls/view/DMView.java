/**
 * 
 */
package com.bt.dm.fx.controls.view;

import javafx.scene.layout.Pane;


/**
 * @author Ramalingesha ML
 *
 *         Created on Oct 3, 2020 3:43:07 PM
 */
public abstract class DMView {
	
	public DMView() {
		this.getView();
	}

	public abstract Pane getView();
}
