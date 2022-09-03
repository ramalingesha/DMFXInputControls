/**
 * 
 */
package com.bt.dm.fx.controls.toolbar;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;

import com.bt.dm.core.pubsub.PubSubEvent;
import com.bt.dm.core.pubsub.PubSubEventDataModel;
import com.bt.dm.core.pubsub.PubSubEventHandler;
import com.bt.dm.fx.controls.button.FXButtonCmp;
import com.bt.dm.fx.controls.button.FXButtonCmp.FXButtonCmpBuilder;
import com.bt.dm.fx.controls.events.DMFocusListener;
import com.bt.dm.fx.controls.events.DMFormPanelToolBarEvent;
import com.bt.dm.fx.controls.image.FXImage;
import com.bt.dm.fx.controls.image.FXImage.FXImageBuilder;
import com.bt.dm.fx.controls.labels.FXMaterialDesignIcon;
import com.bt.dm.fx.controls.labels.FXMaterialDesignIcon.FXMaterialDesignIconBuilder;
import com.bt.dm.fx.controls.theme.ControlsTheme;
import com.bt.dm.fx.controls.utils.UIHelper;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;

/**
 * @author Ramalingesha ML
 *
 *         Created on Oct 8, 2020 10:41:30 AM
 */
public class DMToolBar extends ToolBar {
	public static class DMToolBarBuilder {
		private boolean hideDefaultButtons;
		private ObservableList<Node> toolBarControls;
		private DMFormPanelToolBarEvent toolBarEvent;
		private Orientation orientation;
		private boolean displayLogo = false;
		private boolean addSpaceBetweenControls;
		private DMFocusListener saveBtnFocusListener;
		private DMFocusListener cancelBtnFocusListener;

		public DMToolBarBuilder toolBarControls(
				ObservableList<Node> toolBarControls) {
			return toolBarControls(toolBarControls, true);
		}

		public DMToolBarBuilder toolBarControls(
				ObservableList<Node> toolBarControls,
				boolean addSpaceBetweenControls) {
			this.toolBarControls = toolBarControls;
			this.addSpaceBetweenControls = addSpaceBetweenControls;
			return this;
		}

		public DMToolBarBuilder hideDefaultButtons(boolean hideDefaultButtons) {
			this.hideDefaultButtons = hideDefaultButtons;
			return this;
		}

		public DMToolBarBuilder toolBarEvent(
				DMFormPanelToolBarEvent toolBarEvent) {
			this.toolBarEvent = toolBarEvent;
			return this;
		}

		public DMToolBarBuilder orientation(Orientation orientation) {
			this.orientation = orientation;
			return this;
		}

		public DMToolBarBuilder displayLogo(boolean displayLogo) {
			this.displayLogo = displayLogo;
			return this;
		}

		public DMToolBarBuilder saveBtnFocusListener(
				DMFocusListener saveBtnFocusListener) {
			this.saveBtnFocusListener = saveBtnFocusListener;
			return this;
		}

		public DMToolBarBuilder cancelBtnFocusListener(
				DMFocusListener cancelBtnFocusListener) {
			this.cancelBtnFocusListener = cancelBtnFocusListener;
			return this;
		}
	}

	private FXButtonCmp saveButton;
	private FXButtonCmp cancelButton;
	private DMToolBarBuilder builder;
	private PubSubEventHandler handler;

	public DMToolBar(DMToolBarBuilder builder) {
		this.builder = builder;
		this.createControl();
	}

	private void createControl() {
		this.addRootThemeStyleSheet();

		if (this.builder.displayLogo) {
			// Add logo
			FXImage logo = new FXImage(new FXImageBuilder(
					"com/bt/dm/fx/controls/resources/images/logo.png").width(
					100).height(75));
			this.getItems().addAll(logo, new Separator());
		}

		// Add default buttons
		if (!this.builder.hideDefaultButtons) {
			this.addDefaultButtons();
		}

		// Add toolbar controls
		if (this.builder.toolBarControls != null) {
			this.builder.toolBarControls.stream().forEach(control -> {
				if (this.builder.addSpaceBetweenControls) {
					this.getItems().add(UIHelper.getEmptySpace(1, false));
				}

				this.getItems().add(control);
			});
		}

		if (this.builder.orientation != null) {
			this.setOrientation(this.builder.orientation);
		}

		this.subscribeOnThemeChange();
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
		this.getStylesheets().clear();
		this.getStylesheets().add(
				getClass().getResource("Toolbar.css").toExternalForm());
		this.getStylesheets().add(
				getClass().getResource(
						ControlsTheme.getThemeCssFileName("Toolbar"))
						.toExternalForm());
	}

	private void addDefaultButtons() {
		this.saveButton = new FXButtonCmp(new FXButtonCmpBuilder()
				.text("dmform.toolbar.button.save")
				.className("styled-primary-button")
				.focusListener(builder.saveBtnFocusListener)
				.materialIcon(
						new FXMaterialDesignIcon(
								new FXMaterialDesignIconBuilder(
										MaterialDesignIcon.FLOPPY)))
				.eventHandler(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						if (builder.toolBarEvent != null) {
							builder.toolBarEvent.onSaveBtnClick();
						}
					}
				}));
		this.cancelButton = new FXButtonCmp(new FXButtonCmpBuilder()
				.text("dmform.toolbar.button.cancel")
				.className("styled-secondary-button")
				.focusListener(builder.cancelBtnFocusListener)
				.materialIcon(
						new FXMaterialDesignIcon(
								new FXMaterialDesignIconBuilder(
										MaterialDesignIcon.CLOSE)))
				.eventHandler(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						if (builder.toolBarEvent != null) {
							builder.toolBarEvent.onCancelBtnClick();
						}
					}
				}));

		this.getItems().addAll(this.saveButton, UIHelper.getEmptySpace(1, false),
				this.cancelButton);
	}
}
