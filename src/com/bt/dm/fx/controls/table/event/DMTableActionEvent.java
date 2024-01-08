package com.bt.dm.fx.controls.table.event;

import com.bt.dm.core.model.DMModel;

import javafx.event.Event;

/**
 * @author Ramalingesha ML
 *
 *         Created on Sep 23, 2021 4:15:53 PM
 */
public interface DMTableActionEvent {
	public abstract void onDeleteClick(int id);

	public abstract void onEditClick(DMModel model);
	
	public default void onEditClick(DMModel model, int selectedRowIndex) {}

	public default void onExpandClick(Event event) {

	}

	public default void generateReport() {

	}
}
