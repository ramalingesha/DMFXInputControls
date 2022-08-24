/**
 * 
 */
package com.bt.dm.fx.controls.table.event;

import com.bt.dm.core.model.DMModel;
import com.bt.dm.fx.controls.table.DMTableModel;

/**
 * @author Ramalingesha ML
 *
 *         Created on Jan 3, 2021 6:32:42 PM
 */
public interface DMTableModelCreateEvent<M extends DMModel, T extends DMTableModel> {
	T createTableModel(int index, M model);
}
