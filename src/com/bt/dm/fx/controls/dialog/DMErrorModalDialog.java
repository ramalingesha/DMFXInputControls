/**
 * 
 */
package com.bt.dm.fx.controls.dialog;

import java.awt.AWTException;
import java.util.List;

import com.bt.dm.core.pubsub.PubSubEvent;
import com.bt.dm.core.pubsub.PubSubEventDataModel;
import com.bt.dm.core.pubsub.PubSubEventHandler;
import com.bt.dm.core.utils.DMCollectionUtils;
import com.bt.dm.core.utils.DMStringUtils;
import com.bt.dm.fx.controls.events.IconClickEvent;
import com.bt.dm.fx.controls.labels.FXLabelCmp;
import com.bt.dm.fx.controls.labels.FXLabelCmp.FXLabelCmpBuilder;
import com.bt.dm.fx.controls.labels.FXMaterialDesignIcon;
import com.bt.dm.fx.controls.labels.FXMaterialDesignIcon.FXMaterialDesignIconBuilder;
import com.bt.dm.fx.controls.theme.ControlsTheme;
import com.bt.dm.fx.controls.utils.Fonts;
import com.bt.dm.fx.controls.utils.SizeHelper;
import com.bt.dm.fx.controls.utils.UIHelper;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

/**
 * @author Ramalingesha ML
 *
 *         Created on Dec 20, 2022 3:11:00 PM
 */
public class DMErrorModalDialog {
	public static class DMErrorModalDialogBuilder {
		private Window parent;
		private String title;
		private List<String> errorMessages;
		private String classNames;

		public DMErrorModalDialogBuilder(Window parent) {
			this.parent = parent;
		}

		public DMErrorModalDialogBuilder errorMessages(List<String> errorMessages) {
			this.errorMessages = errorMessages;
			return this;
		}

		public DMErrorModalDialogBuilder title(String title) {
			this.title = title;
			return this;
		}
		
		public DMErrorModalDialogBuilder classNames(String classNames) {
			this.classNames = classNames;
			return this;
		}
	}

	private Stage stage;
	private Pane contentPanel;
	private DMErrorModalDialogBuilder builder;
	private PubSubEventHandler handler;
	private BorderPane root;
	private FXLabelCmp titleCmp;
	private Pane titleBarPanel;

	public DMErrorModalDialog(DMErrorModalDialogBuilder builder) {
		this.builder = builder;
		this.createStage();
	}

	private void createStage() {
		this.contentPanel = new VBox(10);
		this.contentPanel.setPadding(new Insets(10));
		this.showErrors(builder.errorMessages);

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
		stage.setWidth(SizeHelper.SCREEN_SIZE.getWidth() * 0.65);
		stage.setHeight(SizeHelper.SCREEN_SIZE.getHeight() * 0.5);

		this.addRootThemeStyleSheet();
		this.subscribeOnThemeChange();
	}
	
	public void showErrors(List<String> errorMessages) {
		this.contentPanel.getChildren().clear();
		
		if(DMCollectionUtils.notEmptyOrNull(errorMessages)) {
			String errorText = String.join("\n", errorMessages);
			
			Text errorTextCmp = new Text(errorText);
			errorTextCmp.setWrappingWidth((SizeHelper.SCREEN_SIZE.getWidth() * 0.65) - 50);
			errorTextCmp.setStroke(Color.RED);
			
			if(builder.classNames == null) {
				errorTextCmp.getStyleClass().add("kannada-error-font");	
			} else {
				errorTextCmp.getStyleClass().addAll(builder.classNames.split(" "));
			}
			
			
			this.contentPanel.getChildren().add(errorTextCmp);
		}
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
				this.builder.title != null ? this.builder.title : "app.error")
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
		
		this.root.getStylesheets().add(
				getClass().getResource("MessageBox.css").toExternalForm());
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

	public void updateParentWindow(Window parent) {
		stage.initOwner(parent);
	}
}
