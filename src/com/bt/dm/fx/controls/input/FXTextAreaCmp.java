package com.bt.dm.fx.controls.input;

import javafx.scene.control.Control;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;

import com.bt.dm.core.utils.DMStringUtils;
import com.bt.dm.core.utils.RandomNumberGenerator;
import com.bt.dm.fx.controls.DMControl;
import com.bt.dm.fx.controls.DMControlBuilder;
import com.bt.dm.fx.controls.utils.Fonts;
import com.bt.dm.fx.controls.utils.SizeHelper;
import com.sun.javafx.scene.control.skin.BehaviorSkinBase;

/**
 * 
 * @author Ramalingesha ML
 *
 *         Created on Jan 4, 2022 6:37:04 AM
 */
public class FXTextAreaCmp extends DMControl<Object> {
	public static class FXTextAreaCmpBuilder extends DMControlBuilder {
	}

	private TextArea textAreaCmp;

	public FXTextAreaCmp(DMControlBuilder builder) {
		super(builder);
		this.createComponent();
		this.registerEvents();
	}

	@Override
	public void createComponent() {
		if (this.builder.getInputSize() == null) {
			this.builder.inputSize(SizeHelper.MULTI_LINE_INPUT_CONTROL_SIZE);
		}

		textAreaCmp = new TextArea(this.builder.getValue());
		textAreaCmp.setPromptText(this.builder.getPlaceHolder());
		textAreaCmp.setEditable(!this.builder.isReadOnly());
		textAreaCmp.setFocusTraversable(!this.builder.isReadOnly());
		textAreaCmp.setFont(this.builder.getFont() != null ? this.builder
				.getFont() : Fonts.getInstance().getDefaultFont());

		this.getChildren().add(this.constructControlWithDefaults(textAreaCmp));
	}

	@SuppressWarnings("rawtypes")
	private void registerEvents() {
		this.textAreaCmp.setOnKeyPressed(event -> {
			if ((!event.isControlDown() && event.getCode() == KeyCode.ENTER)
					|| event.getCode() == KeyCode.TAB) {
				((BehaviorSkinBase) this.textAreaCmp.getSkin()).getBehavior()
						.traverseNext();
				event.consume();
			}
		});
	}

	@Override
	public String getValue() {
		return this.textAreaCmp.getText();
	}

	@Override
	public void setValue(Object value) {
		this.textAreaCmp.setText(DMStringUtils.getNutralValue(value));
	}

	public void setEditable(boolean editable) {
		this.textAreaCmp.setEditable(editable);
	}

	@Override
	public String generateUniqueId() {
		return "textAreaCmp_"
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
		return this.textAreaCmp;
	}

	@Override
	public void clear() {
		this.textAreaCmp.clear();
	}

	public boolean isEmpty() {
		String value = this.getValue();
		return value == null || value.trim().isEmpty();
	}
}
