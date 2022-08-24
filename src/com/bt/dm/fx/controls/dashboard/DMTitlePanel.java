package com.bt.dm.fx.controls.dashboard;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import com.bt.dm.core.pubsub.DashboardTitlePubSubTopicModel;
import com.bt.dm.core.pubsub.PubSubEvent;
import com.bt.dm.core.pubsub.PubSubEventDataModel;
import com.bt.dm.core.pubsub.PubSubEventHandler;
import com.bt.dm.fx.controls.events.IconClickEvent;
import com.bt.dm.fx.controls.labels.FXLabelCmp;
import com.bt.dm.fx.controls.labels.FXLabelCmp.FXLabelCmpBuilder;
import com.bt.dm.fx.controls.labels.FXMaterialDesignIcon;
import com.bt.dm.fx.controls.labels.FXMaterialDesignIcon.FXMaterialDesignIconBuilder;
import com.bt.dm.fx.controls.theme.AppTheme;
import com.bt.dm.fx.controls.theme.ControlsTheme;
import com.bt.dm.fx.controls.theme.ThemePubSubTopicModel;
import com.bt.dm.fx.controls.view.DMView;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;

/**
 * 
 * @author Ramalingesha ML
 *
 *         Created on Dec 17, 2020 8:50:24 AM
 */
public class DMTitlePanel extends DMView {
	public static class DMTitlePanelBuilder {
		private String subTitle;
		private String mainTitle;
		private IconClickEvent minimizeIconClickHandler;
		private IconClickEvent closeIconClickHandler;

		public DMTitlePanelBuilder subTitle(String subTitle) {
			this.subTitle = subTitle;
			return this;
		}

		public DMTitlePanelBuilder mainTitle(String mainTitle) {
			this.mainTitle = mainTitle;
			return this;
		}

		public DMTitlePanelBuilder minimizeIconClickHandler(
				IconClickEvent minimizeIconClickHandler) {
			this.minimizeIconClickHandler = minimizeIconClickHandler;
			return this;
		}

		public DMTitlePanelBuilder closeIconClickHandler(
				IconClickEvent closeIconClickHandler) {
			this.closeIconClickHandler = closeIconClickHandler;
			return this;
		}
	}

	private DMTitlePanelBuilder builder;
	private PubSubEventHandler handler;
	private FXLabelCmp mainLabelCmp;

	public DMTitlePanel(DMTitlePanelBuilder builder) {
		this.builder = builder;
	}

	@Override
	public Pane getView() {
		FXMaterialDesignIconBuilder closeIconBuilder = new FXMaterialDesignIconBuilder(
				MaterialDesignIcon.CLOSE);
		if (this.builder.closeIconClickHandler != null) {
			closeIconBuilder.iconClickEvent(new IconClickEvent() {

				@Override
				public void onClick(Event event) {
					builder.closeIconClickHandler.onClick(event);
				}
			});
		}

		FXMaterialDesignIconBuilder minimizeIconBuilder = new FXMaterialDesignIconBuilder(
				MaterialDesignIcon.MINUS);
		if (this.builder.minimizeIconClickHandler != null) {
			minimizeIconBuilder.iconClickEvent(new IconClickEvent() {

				@Override
				public void onClick(Event event) {
					builder.minimizeIconClickHandler.onClick(event);
				}
			});
		}

		FXMaterialDesignIcon minimizeIcon = new FXMaterialDesignIcon(
				minimizeIconBuilder);
		FXMaterialDesignIcon closeIcon = new FXMaterialDesignIcon(
				closeIconBuilder);

		minimizeIcon.getStyleClass().add("icon-style");
		closeIcon.getStyleClass().add("icon-style");

		HBox iconBox = new HBox(minimizeIcon, closeIcon);

		HBox box = new HBox();
		box.setSpacing(20);
		box.getChildren().addAll(this.getColorPickerBox(), iconBox);

		BorderPane borderPane = new BorderPane();
		borderPane.setPadding(new Insets(5));

		if (this.builder.subTitle != null) {
			borderPane.setLeft(new FXLabelCmp(new FXLabelCmpBuilder().label(
					this.builder.subTitle).classNames(
					new String[] { "heading2" })));
		}

		mainLabelCmp = new FXLabelCmp(new FXLabelCmpBuilder()
				.label(this.builder.mainTitle).i18n(false).h3(true)
				.classNames(new String[] { "nudi-font-heading3" }));
		if (this.builder.mainTitle != null) {
			borderPane.setCenter(this.mainLabelCmp);
		}
		borderPane.setRight(box);

		subscribeOnMainTitleChange();

		return borderPane;
	}

	private void subscribeOnMainTitleChange() {
		handler = new PubSubEventHandler() {

			@Override
			public void onPubSubData(PubSubEventDataModel data) {
				updateMainTitle(data);
			}
		};
		PubSubEvent.getInstance().subscribe("MAIN_TITLE", handler);
	}

	private void updateMainTitle(PubSubEventDataModel data) {
		this.mainLabelCmp.setText(((DashboardTitlePubSubTopicModel) data)
				.getTitle());
	}

	private HBox getColorPickerBox() {
		HBox colorPickerBox = new HBox();
		colorPickerBox.setAlignment(Pos.CENTER);
		colorPickerBox.setSpacing(5);
		colorPickerBox.getChildren().addAll(
				this.getButton(AppTheme.BLUE, "#0069c0"),
				this.getButton(AppTheme.INDIGO, "#002984"),
				this.getButton(AppTheme.TEAL, "#00675b"),
				this.getButton(AppTheme.DEEP_ORANGE, "#c41c00"));

		return colorPickerBox;
	}

	private Button getButton(AppTheme themeName, String baseColor) {
		Button button = new Button();
		button.getStyleClass().add("theme-color-button");
		button.setStyle(String.format("-fx-base: %s;", baseColor));
		ThemePubSubTopicModel themePubSubTopicModel = new ThemePubSubTopicModel();

		button.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				themePubSubTopicModel.setThemeName(themeName);
				ControlsTheme.APP_THEME = themeName;
				PubSubEvent.getInstance().publish("THEME",
						themePubSubTopicModel);
			}
		});

		return button;
	}
}
