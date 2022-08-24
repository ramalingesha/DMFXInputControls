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
import com.bt.dm.fx.controls.labels.FXFontAwesomeIcon;
import com.bt.dm.fx.controls.labels.FXFontAwesomeIcon.FXFontAwesomeIconBuilder;
import com.bt.dm.fx.controls.utils.SizeHelper;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;

/**
 * 
 * @author Ramalingesha ML
 *
 *         Created on Jan 17, 2021 8:03:25 AM
 */
public class FAIconsDemoView extends Pane {

	public FAIconsDemoView() {
		this.createComponent();
	}

	private void createComponent() {
		TilePane iconsPane = new TilePane();
		iconsPane.setPrefWidth(SizeHelper.SCREEN_SIZE.getWidth() - 40);
		iconsPane.setPadding(new Insets(10));
		iconsPane.setVgap(15);
		iconsPane.setHgap(15);

		FontAwesomeIcon icons[] = FontAwesomeIcon.values();
		for (FontAwesomeIcon icon : icons) {
			FXFontAwesomeIcon fxIcon = new FXFontAwesomeIcon(
					new FXFontAwesomeIconBuilder(icon).size("48px")
							.faIconClickEvent(new IconClickEvent() {

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
