/**
 * 
 */
package com.bt.dm.fx.controls.theme;

import com.bt.dm.core.pubsub.PubSubEventDataModel;

/**
 * @author Ramalingesha ML
 *
 *         Created on Dec 21, 2020 10:46:55 AM
 */
public class ThemePubSubTopicModel implements PubSubEventDataModel {
	private AppTheme themeName;

	public AppTheme getThemeName() {
		return themeName;
	}

	public void setThemeName(AppTheme themeName) {
		this.themeName = themeName;
	}

}
