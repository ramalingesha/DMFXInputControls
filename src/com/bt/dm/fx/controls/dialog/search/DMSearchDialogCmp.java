/**
 * 
 */
package com.bt.dm.fx.controls.dialog.search;

import com.bt.dm.core.pubsub.ModalWindowPubSubTopicModel;
import com.bt.dm.core.pubsub.PubSubEvent;
import com.bt.dm.fx.controls.button.FXButtonCmp;
import com.bt.dm.fx.controls.button.FXButtonCmp.FXButtonCmpBuilder;
import com.bt.dm.fx.controls.labels.FXFontAwesomeIcon;
import com.bt.dm.fx.controls.labels.FXFontAwesomeIcon.FXFontAwesomeIconBuilder;
import com.bt.dm.fx.controls.labels.FXMaterialDesignIcon;
import com.bt.dm.fx.controls.labels.FXMaterialDesignIcon.FXMaterialDesignIconBuilder;
import com.bt.dm.fx.controls.utils.ControlSize;
import com.bt.dm.fx.controls.utils.UIHelper;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * @author Ramalingesha ML
 *
 *         Created on Sep 26, 2022 06:15:03 PM
 */
public class DMSearchDialogCmp extends VBox {
	private DMSearchDialogInputHandler searchDialogInputHandler;
	private ModalWindowPubSubTopicModel popupSettingModel;

	public DMSearchDialogCmp(DMSearchDialogInputHandler searchDialogInputHandler) {
		this.searchDialogInputHandler = searchDialogInputHandler;
		this.initSearchDialogSettings();
	}

	private void createButton() {
		FXButtonCmp searchButton = new FXButtonCmp(new FXButtonCmpBuilder()
				.text(searchDialogInputHandler.getSearchButtonText())
				.materialIcon(new FXMaterialDesignIcon(
						new FXMaterialDesignIconBuilder(MaterialDesignIcon.FILTER)))
				.className("styled-accent-button")
				.eventHandler(event -> {
					this.showSearchDialog();
				}));
		
		
		Region spacer = UIHelper.getEmptySpace(10, true);
		VBox.setVgrow(spacer, Priority.ALWAYS);
		
		this.getChildren().addAll(spacer, searchButton);
	}
	
	private void showSearchDialog() {
		PubSubEvent.getInstance().publish("openModalWindow", popupSettingModel);
	}
	
	public void closeSearchDialog() {
		PubSubEvent.getInstance().publish("closeModalWindow", null);
	}

	private void initSearchDialogSettings() {
		Pane contentPanel = this.getSearchDialogContentPanel();
		ControlSize size = searchDialogInputHandler.getSearchDialogSize();

		if (size == null) {
			this.popupSettingModel = new ModalWindowPubSubTopicModel(searchDialogInputHandler.getSearchDialogTitle(),
					contentPanel);
		} else {
			this.popupSettingModel = new ModalWindowPubSubTopicModel(searchDialogInputHandler.getSearchDialogTitle(),
					contentPanel, size.getWidth(), size.getHeight());
		}
		
		this.createButton();
	}

	private Pane getSearchDialogContentPanel() {
		FXButtonCmp searchButton = new FXButtonCmp(new FXButtonCmpBuilder()
				.text("app.applySearchButtonText")
				.className("styled-primary-button")
				.faIcon(new FXFontAwesomeIcon(new FXFontAwesomeIconBuilder(FontAwesomeIcon.SEARCH)))
				.eventHandler(event -> {
					searchDialogInputHandler.onSearchButtonClick();
				}));
		
		FXButtonCmp cancelButton = new FXButtonCmp(new FXButtonCmpBuilder()
				.text("dmform.toolbar.button.cancel")
				.className("styled-secondary-button")
				.materialIcon(
						new FXMaterialDesignIcon(
								new FXMaterialDesignIconBuilder(
										MaterialDesignIcon.CLOSE)))
				.eventHandler(event -> {
					this.closeSearchDialog();
				}));
		
		HBox buttonsPanel = new HBox(10);
		
		buttonsPanel.getChildren().addAll(searchButton, cancelButton);
		
		Region spacer = UIHelper.getEmptySpace(10, true);
		VBox.setVgrow(spacer, Priority.ALWAYS);
		
		VBox container = new VBox(20);
		container.setPadding(new Insets(10));
		container.getChildren().addAll(searchDialogInputHandler.getSearchInputPanel(), spacer, buttonsPanel);
		
		return container;
	}
}
