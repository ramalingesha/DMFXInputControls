/**
 * 
 */
package com.bt.dm.fx.controls.table.renderer;

import com.bt.dm.fx.controls.labels.DMHyperLinkCmp;
import com.bt.dm.fx.controls.labels.DMHyperLinkCmp.DMHyperLinkCmpBuilder;
import com.bt.dm.fx.controls.table.event.TableCellClickEvent;
import com.bt.dm.fx.controls.table.model.DMTableModel;

import javafx.scene.Node;
import javafx.scene.control.TableCell;

/**
 * @author Ramalingesha ML
 *
 *         Created on Jan 1, 2024 1:06:21 PM
 */
public class HyperlinkCellRenderer<S extends DMTableModel> extends
		TableCell<S, String> {
	private final DMHyperLinkCmp hyperLinkCmp;

	public HyperlinkCellRenderer(String text) {
		this(text, null);
	}
	
	public HyperlinkCellRenderer(String text,
			TableCellClickEvent cellClickEvent) {
		hyperLinkCmp = new DMHyperLinkCmp(new DMHyperLinkCmpBuilder(text));

		this.addClickHandler(cellClickEvent);
	}

	private void addClickHandler(TableCellClickEvent cellClickEvent) {
		this.setOnMouseClicked(event -> {
			@SuppressWarnings("unchecked")
			TableCell<S, DMHyperLinkCmp> cell = (TableCell<S, DMHyperLinkCmp>) event
					.getSource();
			cellClickEvent.onCellClick(cell.getIndex());
		});
	}

	@Override
	protected void updateItem(String icon, boolean empty) {
		super.updateItem(icon, empty);

		if (icon != null) {
			hyperLinkCmp.setText(getAccessibleHelp());
		}

		if (empty) {
			setGraphic(null);
			return;
		} else {
			setGraphic(hyperLinkCmp);
		}
	}
}
