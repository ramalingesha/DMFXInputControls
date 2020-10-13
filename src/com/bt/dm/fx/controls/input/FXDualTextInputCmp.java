/**
 * 
 */
package com.bt.dm.fx.controls.input;

import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;

import com.bt.dm.fx.controls.DMControl;
import com.bt.dm.fx.controls.DMControlBuilder;
import com.bt.dm.fx.controls.labels.FXLabelCmp;
import com.bt.dm.fx.controls.labels.FXLabelCmp.FXLabelCmpBuilder;
import com.bt.dm.fx.controls.utils.Fonts;

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
		gridPane.setVgap(5);

		int column = 0;

		if (!this.builder.isHideLabel()) {
			FXLabelCmp labelCmp = new FXLabelCmp(new FXLabelCmpBuilder()
					.label(this.builder.getLabel()).fixedLabelSize(true)
					.align(Pos.CENTER_RIGHT));
			gridPane.add(labelCmp, column, 0);
			column++;
		}

		this.builder.hideLabel(true);
		this.builder.placeHolder("Kannada");
		textField = new FXTextInputCmp(this.builder);
		gridPane.add(textField, column, 0);

		FXDualTextInputCmpBuilder dualTextInputCmpBuilder = (FXDualTextInputCmpBuilder) this.builder;
		if (!dualTextInputCmpBuilder.hideTranslatorField) {
			try {
				DMControlBuilder translatorCmpBuilder = (DMControlBuilder) this.builder
						.clone();
				translatorCmpBuilder.font(Fonts.getInstance().getEnglishFont());
				translatorCmpBuilder.placeHolder("English");

				translatorTextField = new FXTextInputCmp(translatorCmpBuilder);
				gridPane.add(translatorTextField, column, 1);
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
		return this.translatorTextField.getValue();
	}

	public void setTranslatorValue(String value) {
		this.translatorTextField.setValue(value);
	}
}
