package com.bt.dm.fx.controls.events;

import java.util.List;

import com.bt.dm.core.settings.model.DMEmailDataModel;
import com.bt.dm.core.settings.model.EmailSettingsModel;

/**
 * @author Ramalingesha ML
 *
 *         Created on Oct 2, 2023 8:4:03 PM
 */
public interface DMEmailActionsEvent {
	public List<EmailSettingsModel> getEmailIdsList();

	public void processSendEmail(DMEmailDataModel emailDataModel);
}
