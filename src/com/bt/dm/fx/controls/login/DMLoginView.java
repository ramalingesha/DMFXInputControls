/**
 * 
 */
package com.bt.dm.fx.controls.login;

import java.awt.AWTException;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import com.bt.dm.fx.controls.utils.UIHelper;
import com.bt.dm.fx.controls.view.DMView;

/**
 * @author Ramalingesha ML
 *
 *         Created on Nov 16, 2021 11:54:28 AM
 */
public class DMLoginView extends DMView {
	private Pane contentPane;

	public DMLoginView(Pane contentPane) {
		this.contentPane = contentPane;
		try {
			UIHelper.updateTraverseFocusWithEnterKey(contentPane);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Pane getView() {
		Image image = new Image(
				"com/bt/dm/fx/controls/resources/images/splash.png");
		BackgroundImage backgroundImage = new BackgroundImage(image,
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.DEFAULT, new BackgroundSize(1.0, 1.0, true,
						true, false, false));

		VBox box = new VBox(this.contentPane);
		box.setAlignment(Pos.CENTER);
		box.setBackground(new Background(backgroundImage));

		return box;
	}

}
