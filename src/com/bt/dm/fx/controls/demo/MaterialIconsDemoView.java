/**
 * 
 */
package com.bt.dm.fx.controls.demo;

import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;

import com.bt.dm.fx.controls.dialog.FXMessageBox;
import com.bt.dm.fx.controls.dialog.FXMessageBox.FXMessageBoxBuilder;
import com.bt.dm.fx.controls.events.IconClickEvent;
import com.bt.dm.fx.controls.labels.FXMaterialDesignIcon;
import com.bt.dm.fx.controls.labels.FXMaterialDesignIcon.FXMaterialDesignIconBuilder;
import com.bt.dm.fx.controls.utils.SizeHelper;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;

/**
 * 
 * @author Ramalingesha ML
 *
 *         Created on Jan 17, 2021 7:52:58 AM
 */
public class MaterialIconsDemoView extends Pane {

	public MaterialIconsDemoView() {
		this.createComponent();
	}

	private void createComponent() {
		TilePane iconsPane = new TilePane();
		iconsPane.setPrefWidth(SizeHelper.SCREEN_SIZE.getWidth() - 40);
		iconsPane.setPadding(new Insets(10));
		iconsPane.setVgap(15);
		iconsPane.setHgap(15);

		MaterialDesignIcon icons[] = MaterialDesignIcon.values();
		for (MaterialDesignIcon icon : icons) {
			FXMaterialDesignIcon fxIcon = new FXMaterialDesignIcon(
					new FXMaterialDesignIconBuilder(icon).size("48px")
							.iconClickEvent(new IconClickEvent() {

								@Override
								public void onClick(Event event) {
									FXMessageBox.getInstance().show(
											new FXMessageBoxBuilder(icon
													.toString(), false));
								}
							}));

			iconsPane.getChildren().add(fxIcon);
		}

		this.getChildren().add(iconsPane);
	}
}
