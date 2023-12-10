/**
 * 
 */
package com.bt.dm.fx.controls.form;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Separator;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import com.bt.dm.core.model.ReferenceModel;
import com.bt.dm.core.pubsub.PubSubEvent;
import com.bt.dm.core.pubsub.PubSubEventDataModel;
import com.bt.dm.core.pubsub.PubSubEventHandler;
import com.bt.dm.core.utils.ConstantUtils;
import com.bt.dm.fx.controls.DMControl;
import com.bt.dm.fx.controls.DMLabelBuilder;
import com.bt.dm.fx.controls.dialog.FXMessageBox;
import com.bt.dm.fx.controls.dialog.FXMessageBox.FXMessageBoxBuilder;
import com.bt.dm.fx.controls.dialog.setting.DMSettingDialogCmp;
import com.bt.dm.fx.controls.dialog.setting.DMSettingDialogInputHandler;
import com.bt.dm.fx.controls.input.FXDualTextInputCmp;
import com.bt.dm.fx.controls.labels.FXLabelCmp;
import com.bt.dm.fx.controls.theme.ControlsTheme;
import com.bt.dm.fx.controls.toolbar.DMToolBar;
import com.bt.dm.fx.controls.toolbar.DMToolBar.DMToolBarBuilder;
import com.bt.dm.fx.controls.utils.SizeHelper;
import com.bt.dm.fx.controls.utils.UIHelper;

/**
 * @author Ramalingesha ML
 *
 *         Created on Dec 26, 2020 9:01:29 AM
 */
public class DMForm extends Pane {

	public static class DMFormBuilder {
		private String formHeader;
		private DMSettingDialogInputHandler settingDialogInputHandler;
		private Pane formInputsPanel;
		private DMToolBarBuilder toolBarBuilder;
		private ObservableList<Node> formHeaderControls;
		private Map<DMControl<?>, List<InputValidator>> inputControls;
		private boolean applyDefaultStyle = true;
		private boolean fullView = false;

		public DMFormBuilder(Pane formInputsPanel) {
			this.formInputsPanel = formInputsPanel;
		}

		public DMFormBuilder formHeader(String formHeader) {
			this.formHeader = formHeader;
			return this;
		}

		public DMFormBuilder settingDialogInputHandler(DMSettingDialogInputHandler settingDialogInputHandler) {
			this.settingDialogInputHandler = settingDialogInputHandler;
			return this;
		}

		public DMFormBuilder toolBarBuilder(DMToolBarBuilder toolBarBuilder) {
			this.toolBarBuilder = toolBarBuilder;
			return this;
		}

		public DMFormBuilder inputControls(ObservableMap<DMControl<?>, List<InputValidator>> inputControls) {
			this.inputControls = inputControls;
			return this;
		}

		public DMFormBuilder formHeaderControls(ObservableList<Node> formHeaderControls) {
			this.formHeaderControls = formHeaderControls;
			return this;
		}

		public DMFormBuilder applyDefaultStyle(boolean applyDefaultStyle) {
			this.applyDefaultStyle = applyDefaultStyle;
			return this;
		}

		public DMFormBuilder fullView(boolean fullView) {
			this.fullView = fullView;
			return this;
		}
	}

	private DMFormBuilder builder;
	private PubSubEventHandler handler;

	public DMForm(DMFormBuilder builder) {
		this.builder = builder;
		this.createForm();
	}

	private void createForm() {
		this.addRootThemeStyleSheet();
		BorderPane borderPane = new BorderPane();

		if (this.builder.fullView) {
			borderPane.setPrefWidth(SizeHelper.FORM_PANEL_FULL_VIEW_SIZE.getWidth());
			borderPane.setPrefHeight(SizeHelper.FORM_PANEL_FULL_VIEW_SIZE.getHeight());
		}

		if (this.builder.applyDefaultStyle) {
			borderPane.getStyleClass().add("dm-form");
		}

		borderPane.setCenter(this.builder.formInputsPanel);

		if (this.builder.formHeader != null) {
			borderPane.setTop(this.getHeader(this.builder.formHeader));
		}

		if (this.builder.toolBarBuilder != null) {
			borderPane.setBottom(this.getToolbar(this.builder.toolBarBuilder));
		}

		if (this.builder.inputControls == null) {
			this.builder.inputControls = new LinkedHashMap<DMControl<?>, List<InputValidator>>();
		}

		this.subscribeOnThemeChange();

		KeyCombination ctrlS = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN);

		this.setOnKeyPressed(event -> {
			if (ctrlS.match(event) && builder.toolBarBuilder != null
					&& builder.toolBarBuilder.getToolBarEvent() != null) {
				builder.toolBarBuilder.getToolBarEvent().onSaveBtnClick();
			}
		});

		this.getChildren().add(borderPane);
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
		this.getStylesheets().add(getClass().getResource("Form.css").toExternalForm());
		this.getStylesheets().add(getClass().getResource(ControlsTheme.getThemeCssFileName("Form")).toExternalForm());
	}

	private Pane getHeader(String formHeader) {
		BorderPane headerPane = new BorderPane();
		headerPane.setPadding(new Insets(0, 0, 10, 0));

		FXLabelCmp header = new FXLabelCmp(
				new DMLabelBuilder().label(formHeader).classNames(new String[] { "heading1" }));

		HBox headerItemsPanel = new HBox(20);
		headerItemsPanel.getChildren().add(header);

		if (this.builder.settingDialogInputHandler != null) {
			DMSettingDialogCmp settingDialogCmp = new DMSettingDialogCmp(this.builder.settingDialogInputHandler);

			headerItemsPanel.getChildren().add(settingDialogCmp);
		}

		BorderPane.setAlignment(headerItemsPanel, Pos.CENTER);
		headerPane.setLeft(headerItemsPanel);

		if (this.builder.formHeaderControls != null) {
			HBox box = new HBox();
			box.setAlignment(Pos.CENTER);
			box.setSpacing(5);

			box.getChildren().addAll(this.builder.formHeaderControls);
			headerPane.setRight(box);
		}

		VBox headerBox = new VBox(headerPane, new Separator());
		headerBox.getStyleClass().add("header");

		return headerBox;
	}

	private Pane getToolbar(DMToolBarBuilder toolBarBuilder) {
		DMToolBar toolbar = new DMToolBar(toolBarBuilder);
		HBox.setHgrow(toolbar, Priority.ALWAYS);

		HBox box = new HBox(toolbar);
		box.setPadding(new Insets(20, 0, 0, 0));

		return box;
	}

	public void addValidation(DMControl<?> control, InputValidator validator) {
		List<InputValidator> inputValidators = this.builder.inputControls.get(control);
		if (inputValidators == null) {
			inputValidators = new ArrayList<InputValidator>();
			this.builder.inputControls.put(control, inputValidators);
		}

		inputValidators.add(validator);
	}

	public boolean isValidForm() {
		if (this.builder.inputControls.keySet().size() == 0) {
			return true;
		}

		for (DMControl<?> control : this.builder.inputControls.keySet()) {
			List<InputValidator> validators = this.builder.inputControls.get(control);

			if (validators == null) {
				continue;
			}

			boolean invalidInput = validators.stream().anyMatch(validator -> !this.isValidInput(control, validator));
			if (invalidInput) {
				return false;
			}
		}

		return true;
	}

	public void clearFormInputs() {
		if (this.builder.formInputsPanel == null) {
			return;
		}

		UIHelper.clearPaneInputControls(this.builder.formInputsPanel);
	}

	private boolean isValidInput(DMControl<?> control, InputValidator validator) {
		Object value = control.getValue();
		boolean valid = true;
		switch (validator.getValidationType()) {
		case REQUIRED:
			if (value == null) {
				valid = false;
			} else if (value instanceof String && ((String) value).trim().length() == 0) {
				valid = false;
			} else if (value instanceof ReferenceModel
					&& ((ReferenceModel) value) == ConstantUtils.COMBO_SELECT_REF_MODEL) {
				valid = false;
			}
			break;
		case NUMBER:
			if (value != null && value instanceof String) {
				try {
					Double.parseDouble((String) value);
				} catch (NumberFormatException e) {
					valid = false;
				}
			}
			break;
		case INTEGER:
			if (value != null && value instanceof String) {
				try {
					Integer.parseInt((String) value);
				} catch (NumberFormatException e) {
					valid = false;
				}
			}
			break;

		default:
			break;
		}

		if (!valid) {
			FXMessageBox.getInstance().show(
					new FXMessageBoxBuilder(validator.getErrorMessage()).title("app.error").alertType(AlertType.ERROR));
			UIHelper.setFocus(control.getControl());
		} else {
			if (control instanceof FXDualTextInputCmp && !UIHelper.canHideTranslatorField()) {
				FXDualTextInputCmp dualTextInputCmp = (FXDualTextInputCmp) control;
				String translatorValue = dualTextInputCmp.getTranslatorValue();
				if (translatorValue == null || translatorValue.trim().length() == 0) {
					valid = false;
					FXMessageBox.getInstance().show(new FXMessageBoxBuilder(validator.getErrorMessage())
							.title("app.error").alertType(AlertType.ERROR));
					UIHelper.setFocus(dualTextInputCmp.getTranslatorControl());
				}
			}
		}

		return valid;
	}
}
