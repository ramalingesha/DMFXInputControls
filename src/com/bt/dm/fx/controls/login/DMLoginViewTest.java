/**
 * 
 */
package com.bt.dm.fx.controls.login;

import com.bt.dm.fx.controls.utils.ControlSize;
import com.bt.dm.fx.controls.utils.SizeHelper;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * 
 * @author Ramalingesha ML
 *
 *         Created on Nov 16, 2021 12:03:38 PM
 */
public class DMLoginViewTest extends Application {
	private Pane splashLayout;
	private ProgressBar loadProgress;
	private Label progressText;
	private Stage mainStage;
	private static final int SPLASH_WIDTH = 676;
	private static final int SPLASH_HEIGHT = 227;

	public static void main(String[] args) throws Exception {
		launch(args);
	}

	// @Override
	// public void init() {
	// ImageView splash = new ImageView(new Image(
	// "http://fxexperience.com/wp-content/uploads/2010/06/logo.png"));
	// loadProgress = new ProgressBar();
	// loadProgress.setPrefWidth(SPLASH_WIDTH - 20);
	// progressText = new Label("Loading hobbits with pie . . .");
	// splashLayout = new VBox();
	// splashLayout.getChildren().addAll(splash, loadProgress, progressText);
	// progressText.setAlignment(Pos.CENTER);
	// splashLayout
	// .setStyle("-fx-padding: 5; -fx-background-color: cornsilk; -fx-border-width:5; -fx-border-color: linear-gradient(to bottom, chocolate, derive(chocolate, 50%));");
	// splashLayout.setEffect(new DropShadow());
	// }

	@Override
	public void start(final Stage stage) throws Exception {
		// showSplash(stage);
		// showMainStage();

		ControlSize screenSize = SizeHelper.SCREEN_SIZE;
		Scene scene = new Scene(new DMLoginView(new Pane()).getView(),
				screenSize.getWidth() * 0.75, screenSize.getHeight() * 0.75, Color.TRANSPARENT);

		// Adding scene to the stage
		stage.setScene(scene);
		stage.initStyle(StageStyle.TRANSPARENT);
		stage.centerOnScreen();

		stage.show();
	}

	private void showMainStage() {
		mainStage = new Stage(StageStyle.DECORATED);
		mainStage.setTitle("FX Experience");
		mainStage.setIconified(true);

		Label testLabel = new Label("Testing...");

		// layout the scene.
		Scene scene = new Scene(testLabel, 1000, 600);

		mainStage.setScene(scene);
		mainStage.show();
	}

	private void showSplash(Stage initStage) {
		Scene splashScene = new Scene(splashLayout);
		initStage.initStyle(StageStyle.UNDECORATED);
		final Rectangle2D bounds = Screen.getPrimary().getBounds();
		initStage.setScene(splashScene);
		initStage.setX(bounds.getMinX() + bounds.getWidth() / 2 - SPLASH_WIDTH
				/ 2);
		initStage.setY(bounds.getMinY() + bounds.getHeight() / 2
				- SPLASH_HEIGHT / 2);
		initStage.show();
	}
}