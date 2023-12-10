package com.bt.dm.fx.controls.form;

import com.bt.dm.core.model.DMModel;

/**
 * @author Ramalingesha ML
 *
 *         Created on Sep 23, 2021 4:15:53 PM
 */
public interface DMFormActionEvent {
	public abstract void onCancelForm();
	
	public abstract void onSave(DMModel model);
}
