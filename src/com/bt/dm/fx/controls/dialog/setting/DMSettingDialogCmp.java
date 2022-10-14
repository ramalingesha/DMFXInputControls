package com.bt.dm.fx.controls.dialog.setting;

import com.bt.dm.core.pubsub.PubSubEvent;
import com.bt.dm.core.pubsub.settings.ReportSettingPubSubModel;
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
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * @author Ramalingesha ML
 *
 *         Created on Oct 07, 2022 07:42:03 AM
 */
public class DMSettingDialogCmp extends HBox {
	private DMSettingDialogInputHandler settingDialogInputHandler;
	private ReportSettingPubSubModel popupSettingModel;

	public DMSettingDialogCmp(DMSettingDialogInputHandler settingDialogInputHandler) {
		super();
		this.settingDialogInputHandler = settingDialogInputHandler;
		this.createButton();
	}

	private void initSettingDialog() {
		Pane contentPanel = this.getSettingDialogContentPanel();
		ControlSize size = settingDialogInputHandler.getSettingDialogSize();

		if (size == null) {
			this.popupSettingModel = new ReportSettingPubSubModel(settingDialogInputHandler.getSettingDialogTitle(),
					contentPanel);
		} else {
			this.popupSettingModel = new ReportSettingPubSubModel(settingDialogInputHandler.getSettingDialogTitle(),
					contentPanel, size.getWidth(), size.getHeight());
		}
	}

	private void createButton() {
		FXFontAwesomeIcon settingsIcon = new FXFontAwesomeIcon(
				new FXFontAwesomeIconBuilder(FontAwesomeIcon.GEARS).size("28px")
				.faIconClickEvent(event -> {
					this.initSettingDialog();
					this.showSettingDialog();
				}));

		settingsIcon.setStyle("-fx-fill: #bc3672;");

		this.setAlignment(Pos.CENTER);
		this.getChildren().add(settingsIcon);
	}

	private void showSettingDialog() {
		PubSubEvent.getInstance().publish("openModalWindow", popupSettingModel);
	}

	public void closeSettingDialog() {
		PubSubEvent.getInstance().publish("closeModalWindow", null);
	}

	private Pane getSettingDialogContentPanel() {
		FXButtonCmp settingButton = new FXButtonCmp(
				new FXButtonCmpBuilder().text("dmform.toolbar.button.save").className("styled-primary-button")
						.faIcon(new FXFontAwesomeIcon(new FXFontAwesomeIconBuilder(FontAwesomeIcon.SEARCH)))
						.eventHandler(event -> {
							settingDialogInputHandler.onSettingButtonClick();
							this.closeSettingDialog();
						}));

		FXButtonCmp cancelButton = new FXButtonCmp(new FXButtonCmpBuilder().text("dmform.toolbar.button.cancel")
				.className("styled-secondary-button")
				.materialIcon(new FXMaterialDesignIcon(new FXMaterialDesignIconBuilder(MaterialDesignIcon.CLOSE)))
				.eventHandler(event -> {
					this.closeSettingDialog();
				}));

		HBox buttonsPanel = new HBox(10);

		buttonsPanel.getChildren().addAll(settingButton, cancelButton);

		Region spacer = UIHelper.getEmptySpace(10, true);
		VBox.setVgrow(spacer, Priority.ALWAYS);

		VBox container = new VBox(20);
		container.setPadding(new Insets(10));
		container.getChildren().addAll(settingDialogInputHandler.getSettingInputPanel(), spacer, buttonsPanel);

		return container;
	}
}
