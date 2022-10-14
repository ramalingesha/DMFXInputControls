/**
 * 
 */
package com.bt.dm.fx.controls;

import javafx.geometry.Pos;
import javafx.scene.control.Control;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import com.bt.dm.fx.controls.events.DMFocusListener;
import com.bt.dm.fx.controls.labels.FXLabelCmp;
import com.bt.dm.fx.controls.labels.FXLabelCmp.FXLabelCmpBuilder;
import com.bt.dm.fx.controls.utils.ControlSize;
import com.bt.dm.fx.controls.utils.SizeHelper;

/**
 * @author Ramalingesha ML
 *
 *         Created on Sep 23, 2020 10:57:24 AM
 */
public abstract class DMControl<T> extends Pane {
	protected final String controlId;

	public DMControlBuilder builder;
	public FXLabelCmp labelCmp;

	public DMControl(DMControlBuilder builder) {
		this.builder = builder;
		this.controlId = this.generateUniqueId();
	}

	public abstract void createComponent();

	public abstract T getValue();

	public abstract Control getControl();

	public abstract void setValue(T value);

	public abstract void clear();

	public abstract String generateUniqueId();

	public void setLabelFont(Font font) {
		this.labelCmp.setFont(font);
	}

	public void setLabelSize(ControlSize size) {
		this.labelCmp.setPrefSize(size.getWidth(), size.getHeight());
	}
	
	public void hide(boolean visible) {
		this.setVisible(!visible);
	}

	protected Pane constructControlWithDefaults(Control inputControl) {
		Pane pane;
		if (this.builder.isVerticalAlign()) {
			pane = new VBox();
		} else {
			pane = new HBox();

			// Set center alignment
			((HBox) pane).setAlignment(Pos.CENTER);
			// Set the horizontal spacing between children to 5px
			((HBox) pane).setSpacing(5);
		}

		// Set label size
		if (!this.builder.isHideLabel()) {
			this.labelCmp = new FXLabelCmp(new FXLabelCmpBuilder()
					.label(this.builder.getLabel()).i18n(this.builder.isI18n())
					.font(this.builder.getFont())
					.classNames(new String[] { "input-label" }));
			if (this.builder.isFixedLabelSize()) {
				this.labelCmp.setPrefSize(SizeHelper.LABEL_SIZE.getWidth(),
						SizeHelper.LABEL_SIZE.getHeight());
			} else {
				ControlSize labelSize = this.builder.getLabelSize();
				if (labelSize != null) {
					this.labelCmp.setPrefSize(labelSize.getWidth(),
							labelSize.getHeight());
				} else {
					this.labelCmp.setPrefHeight(SizeHelper.LABEL_SIZE
							.getHeight());
				}
			}

			if (!this.builder.isVerticalAlign()) {
				// Align right
				this.labelCmp.setAlignment(Pos.CENTER_RIGHT);
			}

			if (this.builder.getClassNames() != null) {
				this.labelCmp.getStyleClass().addAll(
						this.builder.getClassNames());
				inputControl.getStyleClass().addAll(
						this.builder.getClassNames());
			}

			pane.getChildren().add(this.labelCmp);
		}

		// Set input control size
		ControlSize controlSize = this.builder.getInputSize();
		if (controlSize != null) {
			inputControl.setPrefSize(controlSize.getWidth(),
					controlSize.getHeight());
		} else {
			if (this.builder.isFixedControlSize()) {
				inputControl.setPrefSize(
						SizeHelper.INPUT_CONTROL_SIZE.getWidth(),
						SizeHelper.INPUT_CONTROL_SIZE.getHeight());
			}
		}

		// Add focus listener
		DMFocusListener dmFocusListener = this.builder.getFocusListener();
		if (dmFocusListener != null) {
			inputControl.focusedProperty().addListener(
					(Observable, oldValue, newValue) -> {
						if (newValue) {
							dmFocusListener.onFocus();
						} else {
							dmFocusListener.onBlur();
						}
					});
		}

		// Add english font style class
		if (this.builder.isEnglishFont()) {
			inputControl.getStyleClass().add("english-font");
		}

		pane.getChildren().add(inputControl);

		return pane;
	}

	@Override
	public int hashCode() {
		return this.controlId.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		@SuppressWarnings("unchecked")
		DMControl<T> other = (DMControl<T>) obj;

		if (controlId == null) {
			if (other.controlId != null) {
				return false;
			}
		} else if (!controlId.equals(other.controlId)) {
			return false;
		}

		return true;
	}
}
