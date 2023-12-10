package com.bt.dm.fx.controls.email;

import java.util.ArrayList;
import java.util.List;

import com.bt.dm.core.pubsub.PubSubEvent;
import com.bt.dm.core.service.data.DesignationMasterService;
import com.bt.dm.core.settings.model.DMEmailDataModel;
import com.bt.dm.core.settings.model.EmailSettingsModel;
import com.bt.dm.core.utils.DMCollectionUtils;
import com.bt.dm.core.utils.DMStringUtils;
import com.bt.dm.fx.controls.DMLabelBuilder;
import com.bt.dm.fx.controls.button.FXButtonCmp;
import com.bt.dm.fx.controls.button.FXButtonCmp.FXButtonCmpBuilder;
import com.bt.dm.fx.controls.dialog.FXMessageBox;
import com.bt.dm.fx.controls.dialog.FXMessageBox.FXMessageBoxBuilder;
import com.bt.dm.fx.controls.events.DMEmailActionsEvent;
import com.bt.dm.fx.controls.input.FXTextAreaCmp;
import com.bt.dm.fx.controls.input.FXTextAreaCmp.FXTextAreaCmpBuilder;
import com.bt.dm.fx.controls.input.FXTextInputCmp;
import com.bt.dm.fx.controls.input.FXTextInputCmp.FXTextInputCmpBuilder;
import com.bt.dm.fx.controls.labels.FXCheckBoxCmp;
import com.bt.dm.fx.controls.labels.FXLabelCmp;
import com.bt.dm.fx.controls.labels.FXMaterialDesignIcon;
import com.bt.dm.fx.controls.labels.FXMaterialDesignIcon.FXMaterialDesignIconBuilder;
import com.bt.dm.fx.controls.utils.ControlSize;
import com.bt.dm.fx.controls.utils.SizeHelper;
import com.bt.dm.fx.controls.utils.UIHelper;
import com.bt.dm.fx.controls.view.DMView;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * @author Ramalingesha ML
 *
 *         Created on Oct 2, 2023 6:36:03 AM
 */
public class DMEmailCreatroView extends DMView {
	private FXTextInputCmp subjectTextCmp;
	private FXTextAreaCmp bodyTextCmp;
	private FlowPane recipientspanel;
	private List<String> selectedRecipientsList;
	private DMEmailActionsEvent emailActionsEvent;

	public DMEmailCreatroView(DMEmailActionsEvent emailActionsEvent) {
		this.emailActionsEvent = emailActionsEvent;
	}

	@Override
	public Pane getView() {
		HBox box = new HBox(40);
		box.setPadding(new Insets(10));
		box.getChildren().addAll(this.getEmailComposePanel(), this.getEmailIdsList());

		return box;
	}

	private Pane getEmailComposePanel() {
		ControlSize size = new ControlSize(SizeHelper.SCREEN_SIZE.getWidth() * 0.55,
				SizeHelper.INPUT_CONTROL_SIZE.getHeight());

		this.subjectTextCmp = new FXTextInputCmp(
				new FXTextInputCmpBuilder().hideLabel(true).verticalAlign(true).inputSize(size));
		this.bodyTextCmp = new FXTextAreaCmp(new FXTextAreaCmpBuilder().hideLabel(true).verticalAlign(true)
				.inputSize(new ControlSize(size.getWidth(), 300)));

		FXButtonCmp sendButton = new FXButtonCmp(new FXButtonCmpBuilder().text("dmReportView.buttons.send")
				.className("styled-secondary-button")
				.materialIcon(new FXMaterialDesignIcon(new FXMaterialDesignIconBuilder(MaterialDesignIcon.SEND)))
				.eventHandler(event -> this.sendEmail()));

		FXButtonCmp cancelButton = new FXButtonCmp(new FXButtonCmpBuilder().text("dmform.toolbar.button.close")
				.className("styled-secondary-button")
				.materialIcon(new FXMaterialDesignIcon(new FXMaterialDesignIconBuilder(MaterialDesignIcon.CLOSE)))
				.eventHandler(event -> this.onCancelBtnClick()));

		FXLabelCmp subjectLbl = new FXLabelCmp(new DMLabelBuilder().label("emailComposeView.form.subject"));
		FXCheckBoxCmp englishSubjectCmp = new FXCheckBoxCmp(new DMLabelBuilder()
				.label("dmDualTextInput.placeHolder.english").classNames(new String[] { "english-font" }));
		englishSubjectCmp.setFocusTraversable(false);

		englishSubjectCmp.setOnAction(event -> {
			TextField textField = (TextField) this.subjectTextCmp.getControl();

			if (englishSubjectCmp.isSelected()) {
				textField.getStyleClass().add("english-font");
			} else {
				textField.getStyleClass().remove("english-font");
			}
		});

		HBox subjectHeaderPanel = new HBox(10);
		subjectHeaderPanel.getChildren().addAll(subjectLbl, englishSubjectCmp);

		VBox subjectBox = new VBox();
		subjectBox.getChildren().addAll(subjectHeaderPanel, subjectTextCmp);

		FXLabelCmp bodyLbl = new FXLabelCmp(new DMLabelBuilder().label("emailComposeView.form.body"));
		FXCheckBoxCmp englishBodyCmp = new FXCheckBoxCmp(new DMLabelBuilder()
				.label("dmDualTextInput.placeHolder.english").classNames(new String[] { "english-font" }));
		englishBodyCmp.setFocusTraversable(false);

		englishBodyCmp.setOnAction(event -> {
			TextArea textArea = (TextArea) this.bodyTextCmp.getControl();

			if (englishBodyCmp.isSelected()) {
				textArea.getStyleClass().add("english-font");
			} else {
				textArea.getStyleClass().remove("english-font");
			}
		});

		HBox bodyHeaderPanel = new HBox(10);
		bodyHeaderPanel.getChildren().addAll(bodyLbl, englishBodyCmp);

		VBox bodyBox = new VBox();
		bodyBox.getChildren().addAll(bodyHeaderPanel, bodyTextCmp);

		HBox buttonsPanel = new HBox(10);
		buttonsPanel.getChildren().addAll(sendButton, cancelButton);

		VBox box = new VBox(20);
		box.getChildren().addAll(this.getRecipientsPanel(size.getWidth()), subjectBox, bodyBox, buttonsPanel);

		return box;
	}

	private Pane getRecipientsPanel(double width) {
		selectedRecipientsList = new ArrayList<>();

		recipientspanel = new FlowPane(10, 5);
		recipientspanel.setPadding(new Insets(0, 10, 0, 10));
		recipientspanel.getStyleClass().add("dm-list-box");
		recipientspanel.setPrefSize(width, 100);

		ScrollPane scrollPane = new ScrollPane(recipientspanel);
		scrollPane.setFitToWidth(true);

		FXLabelCmp labelCmp = new FXLabelCmp(new DMLabelBuilder().label("emailComposeView.form.recipients"));

		VBox box = new VBox();
		box.getChildren().addAll(labelCmp, scrollPane);

		return box;
	}

	private ScrollPane getEmailIdsList() {
		FXLabelCmp headerLbl = new FXLabelCmp(new DMLabelBuilder().label("emailComposeView.form.emailIdsList"));

		int row = 0;
		GridPane gridPane = new GridPane();
		gridPane.getStyleClass().add("dm-list-box");
		gridPane.setPrefHeight(300);
		gridPane.setPrefWidth(SizeHelper.SCREEN_SIZE.getWidth() * 0.4);
		ScrollPane scrollPane = new ScrollPane(gridPane);
		scrollPane.getStyleClass().add("dm-list-box-container");
		scrollPane.setFitToWidth(true);

		gridPane.setVgap(10);
		gridPane.setHgap(20);

		gridPane.add(headerLbl, 0, row++, 3, 1);
		gridPane.add(new Separator(), 0, row++, 3, 1);

		List<EmailSettingsModel> emailSettingModels = this.emailActionsEvent.getEmailIdsList();

		if (DMCollectionUtils.notEmptyOrNull(emailSettingModels)) {
			for (EmailSettingsModel model : emailSettingModels) {
				FXLabelCmp emailIdCmp = new FXLabelCmp(new DMLabelBuilder().label(model.getEmailId()).i18n(false)
						.classNames(new String[] { "english-font" }));
				FXLabelCmp designationCmp = new FXLabelCmp(new DMLabelBuilder()
						.label(DesignationMasterService.getInstance().getValue(model.getDesignation(), false))
						.i18n(false));
				FXMaterialDesignIcon addBtn = new FXMaterialDesignIcon(
						new FXMaterialDesignIconBuilder(MaterialDesignIcon.PLUS)
								.iconClickEvent(event -> this.addRecipient(model)));

				gridPane.add(emailIdCmp, 0, row);
				gridPane.add(designationCmp, 1, row);
				gridPane.add(addBtn, 2, row++);
			}
		}

		return scrollPane;
	}

	private void addRecipient(EmailSettingsModel model) {
		String emailId = model.getEmailId();

		if (selectedRecipientsList.indexOf(emailId) != -1) {
			return;
		}

		selectedRecipientsList.add(emailId);
		this.reloadRecipientsList();
	}

	private void removeRecipient(String emailId) {
		selectedRecipientsList.remove(emailId);

		this.reloadRecipientsList();
	}

	private void reloadRecipientsList() {
		this.recipientspanel.getChildren().clear();

		if (DMCollectionUtils.isEmptyOrNull(this.selectedRecipientsList)) {
			return;
		}

		this.selectedRecipientsList.forEach(emailId -> {
			FXLabelCmp emailIdCmp = new FXLabelCmp(
					new DMLabelBuilder().label(emailId).i18n(false).classNames(new String[] { "english-font" }));
			FXMaterialDesignIcon removeBtn = new FXMaterialDesignIcon(
					new FXMaterialDesignIconBuilder(MaterialDesignIcon.CLOSE).size("16px")
							.iconClickEvent(event -> this.removeRecipient(emailId)));

			HBox box = new HBox(5);
			box.setAlignment(Pos.CENTER);
			box.getChildren().addAll(emailIdCmp, removeBtn);

			this.recipientspanel.getChildren().add(box);
		});
	}

	public ControlSize getSize() {
		return new ControlSize(SizeHelper.SCREEN_SIZE.getWidth(), SizeHelper.SCREEN_SIZE.getHeight());
	}

	private void onCancelBtnClick() {
		PubSubEvent.getInstance().publish("closeModalWindow", null);
	}

	private void sendEmail() {
		if (DMCollectionUtils.isEmptyOrNull(selectedRecipientsList)) {
			FXMessageBox.getInstance()
					.show(new FXMessageBoxBuilder("emailComposeView.form.input.error.recipients.required")
							.alertType(AlertType.ERROR));
			return;
		}

		if (DMStringUtils.isEmpty(subjectTextCmp.getValue())) {
			FXMessageBox.getInstance()
					.show(new FXMessageBoxBuilder("emailComposeView.form.input.error.subject.required")
							.alertType(AlertType.ERROR));
			UIHelper.setFocus(subjectTextCmp.getControl());
			return;
		}

		if (DMStringUtils.isEmpty(bodyTextCmp.getValue())) {
			FXMessageBox.getInstance().show(new FXMessageBoxBuilder("emailComposeView.form.input.error.body.required")
					.alertType(AlertType.ERROR));
			UIHelper.setFocus(bodyTextCmp.getControl());
			return;
		}

		DMEmailDataModel emailDataModel = new DMEmailDataModel(selectedRecipientsList, "doodmapan@gmail.com",
				"ilew rrbq afgi iymx", subjectTextCmp.getValue()).content(bodyTextCmp.getValue());

		this.emailActionsEvent.processSendEmail(emailDataModel);

		PubSubEvent.getInstance().publish("closeModalWindow", null);
	}
}
