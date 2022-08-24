/**
 * 
 */
package com.bt.dm.fx.controls.main;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Locale;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import com.bt.dm.core.i18n.AppLocale;
import com.bt.dm.core.i18n.DMMessageLocalizer;
import com.bt.dm.fx.controls.dashboard.DMDashboard;
import com.bt.dm.fx.controls.dashboard.DMDashboard.DMDashboardBuilder;
import com.bt.dm.fx.controls.dashboard.DMTitlePanel;
import com.bt.dm.fx.controls.dashboard.DMTitlePanel.DMTitlePanelBuilder;
import com.bt.dm.fx.controls.demo.DemoView;
import com.bt.dm.fx.controls.events.IconClickEvent;
import com.bt.dm.fx.controls.labels.FXMaterialDesignIcon;
import com.bt.dm.fx.controls.utils.Fonts;
import com.bt.dm.fx.controls.utils.SizeHelper;
import com.bt.dm.fx.controls.utils.UIHelper;

/**
 * @author Ramalingesha ML
 *
 *         Created on Sep 22, 2020 7:46:35 PM
 */
public class Main extends Application {

	public static void main(String[] args) {
		Fonts.getInstance().setLocaleFonts(AppLocale.ENGLISH);
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		this.initi18n();
		
		// Create the VBox
		VBox root = new VBox();
		root.setSpacing(20);

		UIHelper.updateTraverseFocusWithEnterKey(root);

		DMDashboard dashboard = new DMDashboard(
				new DMDashboardBuilder().titleBar(new DMTitlePanel(
						new DMTitlePanelBuilder()
								.subTitle(DMMessageLocalizer.getLabel("title"))
								.closeIconClickHandler(new IconClickEvent() {

									@Override
									public void onClick(Event event) {
										Platform.exit();
									}
								})
								.minimizeIconClickHandler(new IconClickEvent() {

									@Override
									public void onClick(Event event) {
										((Stage) ((FXMaterialDesignIcon) event
												.getSource()).getScene()
												.getWindow())
												.setIconified(true);
									}
								})).getView()));

		root.getChildren().add(dashboard.getView());
		dashboard.reloadContentPane(new DemoView());

		this.addRootThemeStyleSheet(root);

		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setStyle("-fx-background-color:transparent;");
		scrollPane.setContent(root);
		scrollPane.setFitToWidth(true);

		// Creating a scene object
		Scene scene = new Scene(scrollPane);
		// Add the StyleSheet to the Scene
		// scene.getStylesheets().add(
		// getClass().getResource("app.css").toExternalForm());

		// Setting title to the Stage
		stage.setTitle("DoodMapan FXControls");

		// Adding scene to the stage
		stage.setScene(scene);
		stage.initStyle(StageStyle.UNDECORATED);
		
		if (SizeHelper.isLargeScreen()) {
			stage.setWidth(SizeHelper.SCREEN_SIZE.getWidth());
			stage.setHeight(SizeHelper.SCREEN_SIZE.getHeight());
		} else {
			stage.setMaximized(true);
		}

		// Displaying the contents of the stage
		stage.show();
	}
	
	private void initi18n() {
		Fonts.getInstance().setLocaleFonts(AppLocale.ENGLISH);
		try {
			File file = new File(".");
			URL[] urls = { file.toURI().toURL() };
			ClassLoader loader = new URLClassLoader(urls);
			DMMessageLocalizer.initializeRB(new Locale("en", "IN"),
					"com.bt.dm.fx.controls.i18n.Labels", loader);
		} catch (MalformedURLException e) {
			System.out.println(e);
		}
	}

	private void addRootThemeStyleSheet(Pane root) {
		root.getStylesheets().clear();
		// root.getStylesheets().add(
		// getClass().getResource("Main.css")
		// .toExternalForm());
		root.getStylesheets().add(
				getClass().getResource(
						DMMessageLocalizer.getAppLocaleCssFileName("Main"))
						.toExternalForm());
	}
}
