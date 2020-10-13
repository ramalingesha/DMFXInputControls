/**
 * 
 */
package com.bt.dm.fx.controls.toolbar;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Control;
import javafx.scene.control.ToolBar;

import com.bt.dm.fx.controls.button.FXButtonCmp;
import com.bt.dm.fx.controls.button.FXButtonCmp.FXButtonCmpBuilder;
import com.bt.dm.fx.controls.events.DMFormPanelToolBarEvent;
import com.bt.dm.fx.controls.labels.FXMaterialDesignIcon;
import com.bt.dm.fx.controls.labels.FXMaterialDesignIcon.FXMaterialDesignIconBuilder;
import com.bt.dm.fx.controls.utils.UIHelper;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;

/**
 * @author Ramalingesha ML
 *
 *         Created on Oct 8, 2020 10:41:30 AM
 */
public class DMFormPanelToolBar extends ToolBar {
	public static class DMFormPanelToolBarBuilder {
		private boolean hideDefaultButtons;
		private ObservableList<Control> toolBarControls;
		private DMFormPanelToolBarEvent toolBarEvent;

		public DMFormPanelToolBarBuilder toolBarControls(
				ObservableList<Control> toolBarControls) {
			this.toolBarControls = toolBarControls;
			return this;
		}

		public DMFormPanelToolBarBuilder hideDefaultButtons(
				boolean hideDefaultButtons) {
			this.hideDefaultButtons = hideDefaultButtons;
			return this;
		}

		public DMFormPanelToolBarBuilder toolBarEvent(
				DMFormPanelToolBarEvent toolBarEvent) {
			this.toolBarEvent = toolBarEvent;
			return this;
		}
	}

	private FXButtonCmp saveButton;
	private FXButtonCmp cancelButton;
	private DMFormPanelToolBarBuilder builder;

	public DMFormPanelToolBar(DMFormPanelToolBarBuilder builder) {
		this.builder = builder;
		this.createControl();
	}

	private void createControl() {
		// Add default buttons
		if (!this.builder.hideDefaultButtons) {
			this.addDefaultButtons();
		}

		// Add toolbar controls
		if (this.builder.toolBarControls != null) {
			this.builder.toolBarControls.stream().forEach(
					control -> this.getItems().addAll(
							UIHelper.getHorizontalSpace(1), control));
		}
	}

	private void addDefaultButtons() {
		this.saveButton = new FXButtonCmp(new FXButtonCmpBuilder()
				.text("Save")
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
				.text("Cancel")
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

		this.getItems().addAll(this.saveButton, UIHelper.getHorizontalSpace(1),
				this.cancelButton);
	}
}
