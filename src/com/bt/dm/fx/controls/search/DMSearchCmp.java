/**
 * 
 */
package com.bt.dm.fx.controls.search;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;

import com.bt.dm.core.i18n.DMMessageLocalizer;
import com.bt.dm.fx.controls.input.FXTextInputCmp;
import com.bt.dm.fx.controls.input.FXTextInputCmp.FXTextInputCmpBuilder;

/**
 * @author Ramalingesha ML
 *
 *         Created on Dec 31, 2020 10:58:03 AM
 */
public class DMSearchCmp extends Region {
	private FXTextInputCmp textInputCmp;

	public DMSearchCmp() {
		this.createComponent();
	}

	private void createComponent() {
		this.getStylesheets().add(
				this.getClass().getResource("Search.css").toExternalForm());
		this.getStyleClass().add("search-box");

		textInputCmp = new FXTextInputCmp(new FXTextInputCmpBuilder()
				.hideLabel(true).placeHolder(
						DMMessageLocalizer.getLabel("dmTable.toolbar.search")));

		final ChangeListener<String> textListener = (
				ObservableValue<? extends String> observable, String oldValue,
				String newValue) -> {
			System.out.println(newValue);
		};

		((TextField) textInputCmp.getControl()).textProperty().addListener(
				textListener);

		this.getChildren().addAll(textInputCmp);
	}

	@Override
	protected void layoutChildren() {
		this.textInputCmp.resize(getWidth(), getHeight());
	}
}
