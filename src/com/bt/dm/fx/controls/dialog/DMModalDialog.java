/**
 * 
 */
package com.bt.dm.fx.controls.dialog;

import java.awt.AWTException;

import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import com.bt.dm.core.pubsub.PubSubEvent;
import com.bt.dm.core.pubsub.PubSubEventDataModel;
import com.bt.dm.core.pubsub.PubSubEventHandler;
import com.bt.dm.core.utils.DMStringUtils;
import com.bt.dm.fx.controls.events.IconClickEvent;
import com.bt.dm.fx.controls.labels.FXLabelCmp;
import com.bt.dm.fx.controls.labels.FXLabelCmp.FXLabelCmpBuilder;
import com.bt.dm.fx.controls.labels.FXMaterialDesignIcon;
import com.bt.dm.fx.controls.labels.FXMaterialDesignIcon.FXMaterialDesignIconBuilder;
import com.bt.dm.fx.controls.theme.ControlsTheme;
import com.bt.dm.fx.controls.utils.Fonts;
import com.bt.dm.fx.controls.utils.UIHelper;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;

/**
 * @author Ramalingesha ML
 *
 *         Created on Mar 27, 2021 4:58:00 PM
 */
public class DMModalDialog {
	public static class DMModelDialogBuilder {
		private Window parent;
		private Pane contentPane;
		private String title;
		private Double width;
		private Double height;

		public DMModelDialogBuilder(Window parent) {
			this.parent = parent;
		}

		public DMModelDialogBuilder width(Double width) {
			this.width = width;
			return this;
		}

		public DMModelDialogBuilder height(Double height) {
			this.height = height;
			return this;
		}

		public DMModelDialogBuilder title(String title) {
			this.title = title;
			return this;
		}

		public DMModelDialogBuilder contentPane(Pane contentPane) {
			this.contentPane = contentPane;
			return this;
		}
	}

	private Stage stage;
	private Pane contentPanel;
	private DMModelDialogBuilder builder;
	private PubSubEventHandler handler;
	private BorderPane root;
	private FXLabelCmp titleCmp;
	private Pane titleBarPanel;

	public DMModalDialog(DMModelDialogBuilder builder) {
		this.builder = builder;
		this.createStage();
	}

	private void createStage() {
		this.contentPanel = new Pane();
		if (this.builder.contentPane != null) {
			this.contentPanel.getChildren().add(this.builder.contentPane);
		}

		try {
			UIHelper.updateTraverseFocusWithEnterKey(contentPanel);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		titleBarPanel = this.getTitleBar();

		root = new BorderPane();
		root.setPadding(new Insets(0));
		root.setCenter(this.contentPanel);
		root.setTop(titleBarPanel);

		// Creating a scene object
		Scene scene = new Scene(root);

		// Create Stage
		stage = new Stage();
		stage.initOwner(this.builder.parent);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initStyle(StageStyle.UNDECORATED);

		// Adding scene to the stage
		stage.setScene(scene);

		if (this.builder.width != null) {
			stage.setWidth(this.builder.width);
		} else {
			stage.setWidth(this.builder.parent.getWidth() - 10);
		}

		if (this.builder.height != null) {
			stage.setHeight(this.builder.height);
		} else {
			stage.setHeight(this.builder.parent.getHeight() - 40);
		}

		this.addRootThemeStyleSheet();
		this.subscribeOnThemeChange();
	}

	private Pane getTitleBar() {
		FXMaterialDesignIconBuilder closeIconBuilder = new FXMaterialDesignIconBuilder(
				MaterialDesignIcon.CLOSE).className("icon-style")
				.iconClickEvent(new IconClickEvent() {

					@Override
					public void onClick(Event event) {
						close();
					}
				});
		FXMaterialDesignIcon closeIcon = new FXMaterialDesignIcon(
				closeIconBuilder);

		titleCmp = new FXLabelCmp(new FXLabelCmpBuilder().label(
				this.builder.title != null ? this.builder.title : "app.title")
				.font(Fonts.getInstance().getDefaultHeaderFont()));

		Region spaceRegion = new Region();
		HBox.setHgrow(spaceRegion, Priority.ALWAYS);

		HBox titleBox = new HBox(titleCmp, spaceRegion, closeIcon);
		titleBox.getStyleClass().add("dashboard-title-bar");
		titleBox.setPadding(new Insets(10));

		return titleBox;
	}

	private void subscribeOnThemeChange() {
		handler = new PubSubEventHandler() {

			@Override
			public void onPubSubData(PubSubEventDataModel data) {
				addRootThemeStyleSheet();
			}
		};
		PubSubEvent.getInstance().subscribe("THEME", handler);
	}

	private void addRootThemeStyleSheet() {
		this.root.getStylesheets().clear();
		this.root
				.getStylesheets()
				.add(getClass()
						.getClassLoader()
						.getResource(
								"com/bt/dm/fx/controls/dashboard/Dashboard.css")
						.toExternalForm());
		this.root.getStylesheets().add(
				getClass().getClassLoader()
						.getResource("com/bt/dm/fx/controls/DMControl.css")
						.toExternalForm());
		this.root
				.getStylesheets()
				.add(getClass()
						.getClassLoader()
						.getResource(
								"com/bt/dm/fx/controls/"
										+ ControlsTheme
												.getThemeCssFileName("DMControl"))
						.toExternalForm());
		this.root
				.getStylesheets()
				.add(getClass()
						.getClassLoader()
						.getResource(
								"com/bt/dm/fx/controls/dashboard/"
										+ ControlsTheme
												.getThemeCssFileName("Dashboard"))
						.toExternalForm());

		this.root.getStylesheets().addAll(
				this.builder.parent.getScene().getStylesheets());
	}

	public void show() {
		stage.show();
	}

	public void close() {
		stage.close();
	}

	public void setWidth(Double width) {
		this.stage.setWidth(width);
	}

	public void setHeight(Double height) {
		this.stage.setHeight(height);
	}

	public void setTitle(String title) {
		this.titleCmp.setText(DMStringUtils.getNutralValue(title));
	}
	
	public void showTitleBar(boolean show) {
		this.titleBarPanel.setVisible(show);
	}

	public void reloadContent(Pane contentPane) {
		this.contentPanel.getChildren().clear();
		this.contentPanel.getChildren().add(contentPane);
	}

	public void updateParentWindow(Window parent) {
		stage.initOwner(parent);
	}
}
