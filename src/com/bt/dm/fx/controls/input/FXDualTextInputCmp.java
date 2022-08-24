/**
 * 
 */
package com.bt.dm.fx.controls.input;

import javafx.geometry.Pos;
import javafx.scene.control.Control;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

import com.bt.dm.core.i18n.DMMessageLocalizer;
import com.bt.dm.core.utils.RandomNumberGenerator;
import com.bt.dm.fx.controls.DMControl;
import com.bt.dm.fx.controls.DMControlBuilder;
import com.bt.dm.fx.controls.labels.FXLabelCmp;
import com.bt.dm.fx.controls.labels.FXLabelCmp.FXLabelCmpBuilder;
import com.bt.dm.fx.controls.utils.Fonts;
import com.bt.dm.fx.controls.utils.UIHelper;

/**
 * @author Ramalingesha ML
 *
 *         Created on Oct 7, 2020 4:27:02 PM
 */
public class FXDualTextInputCmp extends DMControl<String> {
	public static class FXDualTextInputCmpBuilder extends DMControlBuilder {
		private boolean hideTranslatorField;

		public FXDualTextInputCmpBuilder hideTranslatorField(
				boolean hideTranslatorField) {
			this.hideTranslatorField = hideTranslatorField;
			return this;
		}
	}

	private FXTextInputCmp textField;
	private FXTextInputCmp translatorTextField;

	public FXDualTextInputCmp(DMControlBuilder builder) {
		super(builder);
		this.createComponent();
	}

	@Override
	public void createComponent() {
		GridPane gridPane = new GridPane();
		gridPane.setHgap(5);

		int column = 0, row = 0;

		if (!this.builder.isHideLabel()) {
			FXLabelCmp labelCmp = new FXLabelCmp(new FXLabelCmpBuilder()
					.label(this.builder.getLabel())
					.fixedLabelSize(true)
					.align(!this.builder.isVerticalAlign() ? Pos.CENTER_RIGHT
							: Pos.CENTER_LEFT)
					.classNames(new String[] { "input-label" }));
			gridPane.add(labelCmp, column, row);

			if (!this.builder.isVerticalAlign()) {
				column++;
			} else {
				row++;
			}
		}

		this.builder.hideLabel(true);
		this.builder.placeHolder(DMMessageLocalizer
				.getLabel("dmDualTextInput.placeHolder.kannada"));
		textField = new FXTextInputCmp(this.builder);
		gridPane.add(textField, column, row++);

		FXDualTextInputCmpBuilder dualTextInputCmpBuilder = (FXDualTextInputCmpBuilder) this.builder;
		// Set hide translator field flag when it is false and english locale is
		// selected
		dualTextInputCmpBuilder.hideTranslatorField = dualTextInputCmpBuilder.hideTranslatorField
				|| UIHelper.canHideTranslatorField();

		if (!dualTextInputCmpBuilder.hideTranslatorField) {
			try {
				DMControlBuilder translatorCmpBuilder = (DMControlBuilder) this.builder
						.clone();
				translatorCmpBuilder.font(Fonts.getInstance().getEnglishFont());
				translatorCmpBuilder.placeHolder(DMMessageLocalizer
						.getLabel("dmDualTextInput.placeHolder.english"));

				translatorTextField = new FXTextInputCmp(translatorCmpBuilder);
				translatorTextField.getStyleClass()
						.add("translator-text-field");

				Region spacer = new Region();
				spacer.setPrefHeight(5);

				gridPane.add(spacer, column, row++);
				gridPane.add(translatorTextField, column, row);
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		this.getChildren().add(gridPane);
	}

	@Override
	public String getValue() {
		return this.textField.getValue();
	}

	@Override
	public void setValue(String value) {
		this.textField.setValue(value);
	}

	public String getTranslatorValue() {
		return this.translatorTextField != null ? this.translatorTextField
				.getValue() : "";
	}

	public void setTranslatorValue(String value) {
		if (this.translatorTextField != null) {
			this.translatorTextField.setValue(value);
		}
	}

	public Control getTranslatorControl() {
		return this.translatorTextField != null ? this.translatorTextField
				.getControl() : null;

	}

	@Override
	public String generateUniqueId() {
		return "dualTextInputCmp_"
				+ RandomNumberGenerator.getInstance().getRandomNumber(8);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public Control getControl() {
		return this.textField.getControl();
	}

	@Override
	public void clear() {
		this.textField.clear();
		if (this.translatorTextField != null) {
			this.translatorTextField.clear();
		}
	}
}
