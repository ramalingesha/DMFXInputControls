/**
 * 
 */
package com.bt.dm.fx.controls.table.renderer;

import javafx.scene.control.TableCell;

import com.bt.dm.fx.controls.labels.FXMaterialDesignIcon;
import com.bt.dm.fx.controls.labels.FXMaterialDesignIcon.FXMaterialDesignIconBuilder;
import com.bt.dm.fx.controls.table.DMTableModel;
import com.bt.dm.fx.controls.table.event.TableCellClickEvent;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;

/**
 * @author Ramalingesha ML
 *
 *         Created on Jan 4, 2021 6:01:21 PM
 */
public class IconCellRenderer<S extends DMTableModel> extends
		TableCell<S, MaterialDesignIcon> {
	private final FXMaterialDesignIcon fxMaterialDesignIcon;

	public IconCellRenderer(MaterialDesignIcon icon) {
		this(icon, null);
	}
	
	public IconCellRenderer(MaterialDesignIcon icon,
			TableCellClickEvent cellClickEvent) {
		fxMaterialDesignIcon = new FXMaterialDesignIcon(
				new FXMaterialDesignIconBuilder(icon));
		fxMaterialDesignIcon.getStyleClass().add(
				String.format("%s-icon-cell", icon.toString().toLowerCase()));

		this.addClickHandler(cellClickEvent);
	}

	private void addClickHandler(TableCellClickEvent cellClickEvent) {
		this.setOnMouseClicked(event -> {
			@SuppressWarnings("unchecked")
			TableCell<S, MaterialDesignIcon> cell = (TableCell<S, MaterialDesignIcon>) event
					.getSource();
			cellClickEvent.onCellClick(cell.getIndex());
		});
	}

	@Override
	protected void updateItem(MaterialDesignIcon icon, boolean empty) {
		super.updateItem(icon, empty);

		if (icon != null) {
			fxMaterialDesignIcon.setIcon(icon);
		}

		if (empty) {
			setGraphic(null);
			return;
		} else {
			setGraphic(fxMaterialDesignIcon);
		}
	}
}
