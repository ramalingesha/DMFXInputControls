/**
 * 
 */
package com.bt.dm.fx.controls.labels;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import com.bt.dm.fx.controls.DMLabelBuilder;
import com.bt.dm.fx.controls.DMLabelControl;

/**
 * @author Ramalingesha ML
 *
 *         Created on Jan 2, 2021 6:04:17 PM
 */
public class DMKeyValueLabelCmp extends Pane {
	private DMLabelBuilder keyLabelBuilder;
	private DMLabelBuilder valueLabelBuilder;
	private boolean verticalAlign;

	private FXLabelCmp keyLabelCmp;
	private FXLabelCmp valueLabelCmp;

	public DMKeyValueLabelCmp(DMLabelBuilder keyLabelBuilder,
			DMLabelBuilder valueLabelBuilder) {
		this(keyLabelBuilder, valueLabelBuilder, false);
	}

	public DMKeyValueLabelCmp(DMLabelBuilder keyLabelBuilder,
			DMLabelBuilder valueLabelBuilder, boolean verticalAlign) {
		this.verticalAlign = verticalAlign;
		this.keyLabelBuilder = keyLabelBuilder;
		this.valueLabelBuilder = valueLabelBuilder;

		this.createComponent();
	}

	private void createComponent() {
		this.keyLabelCmp = new FXLabelCmp(this.keyLabelBuilder);
		this.valueLabelCmp = new FXLabelCmp(this.valueLabelBuilder.bold(true));

		DMLabelControl.applyDefaults(this.keyLabelCmp, this.keyLabelBuilder);
		DMLabelControl
				.applyDefaults(this.valueLabelCmp, this.valueLabelBuilder);

		Pane pane;
		if (this.verticalAlign) {
			pane = new VBox(this.keyLabelCmp, this.valueLabelCmp);
		} else {
			pane = new HBox(5);

			// Set center alignment
			((HBox) pane).setAlignment(Pos.CENTER);

			FXLabelCmp separator = new FXLabelCmp(new DMLabelBuilder().label(
					":").i18n(false));

			pane.getChildren().addAll(this.keyLabelCmp, separator,
					this.valueLabelCmp);
		}

		this.getChildren().add(pane);
	}

	public void setKeyLabelText(String text) {
		this.keyLabelCmp.setText(text);
	}

	public void setValueLabelText(String text) {
		this.valueLabelCmp.setText(text);
	}

	public String getValue() {
		return this.valueLabelCmp.getText();
	}
}
