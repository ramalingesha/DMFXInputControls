package com.bt.dm.fx.controls.input;

import com.bt.dm.core.utils.DMStringUtils;
import com.bt.dm.core.utils.RandomNumberGenerator;
import com.bt.dm.fx.controls.DMControl;
import com.bt.dm.fx.controls.DMControlBuilder;
import com.bt.dm.fx.controls.utils.Fonts;

import javafx.scene.control.Control;
import javafx.scene.control.PasswordField;

/**
 * 
 * @author Ramalingesha ML
 *
 *         Created on May 28, 2023 6:20:54 PM
 */
public class FXPasswordInputCmp extends DMControl<Object> {
	public static class FXPasswordCmpBuilder extends DMControlBuilder {
	}

	private PasswordField passwordCmp;

	public FXPasswordInputCmp(DMControlBuilder builder) {
		super(builder);
		this.createComponent();
	}

	@Override
	public void createComponent() {
		passwordCmp = new PasswordField();
		passwordCmp.setPromptText(this.builder.getPlaceHolder());
		passwordCmp.setEditable(!this.builder.isReadOnly());
		passwordCmp.setFocusTraversable(!this.builder.isReadOnly());
		passwordCmp.setFont(
				this.builder.getFont() != null ? this.builder.getFont() : Fonts.getInstance().getDefaultFont());

		this.getChildren().add(this.constructControlWithDefaults(passwordCmp));
	}

	@Override
	public String getValue() {
		return this.passwordCmp.getText();
	}

	@Override
	public void setValue(Object value) {
		this.passwordCmp.setText(DMStringUtils.getNutralValue(value));
	}

	@Override
	public String generateUniqueId() {
		return "passwordCmp_" + RandomNumberGenerator.getInstance().getRandomNumber(8);
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
		return this.passwordCmp;
	}

	@Override
	public void clear() {
		this.passwordCmp.clear();
	}

	public boolean isEmpty() {
		String value = this.getValue();
		return value == null || value.trim().isEmpty();
	}
}
