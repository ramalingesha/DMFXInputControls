package com.bt.dm.fx.controls.input;

import javafx.scene.control.TextField;

import com.bt.dm.fx.controls.DMControl;
import com.bt.dm.fx.controls.DMControlBuilder;
import com.bt.dm.fx.controls.utils.Fonts;

/**
 * 
 * @author Ramalingesha ML
 *
 *         Created on Sep 22, 2020 7:28:54 PM
 */
public class FXTextInputCmp extends DMControl<String> {
	public static class FXTextInputCmpBuilder extends DMControlBuilder {
	}

	private TextField textFieldCmp;

	public FXTextInputCmp(DMControlBuilder builder) {
		super(builder);
		this.createComponent();
	}

	@Override
	public void createComponent() {
		textFieldCmp = new TextField(this.builder.getValue());
		textFieldCmp.setPromptText(this.builder.getPlaceHolder());
		textFieldCmp.setEditable(!this.builder.isReadOnly());
		textFieldCmp.setFont(this.builder.getFont() != null ? this.builder
				.getFont() : Fonts.getInstance().getDefaultFont());

		this.getChildren().add(this.constructControlWithDefaults(textFieldCmp));
	}

	@Override
	public String getValue() {
		return this.textFieldCmp.getText();
	}

	@Override
	public void setValue(String value) {
		this.textFieldCmp.setText(value);
	}

	public void setEditable(boolean editable) {
		this.textFieldCmp.setEditable(editable);
	}
}
