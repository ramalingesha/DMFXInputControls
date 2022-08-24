/**
 * 
 */
package com.bt.dm.fx.controls.toolbar;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

import com.bt.dm.core.pubsub.PubSubEvent;
import com.bt.dm.fx.controls.button.FXButtonCmp;
import com.bt.dm.fx.controls.button.FXButtonCmp.FXButtonCmpBuilder;
import com.bt.dm.fx.controls.events.DMReportActionsToolbarEvent;
import com.bt.dm.fx.controls.labels.FXMaterialDesignIcon;
import com.bt.dm.fx.controls.labels.FXMaterialDesignIcon.FXMaterialDesignIconBuilder;
import com.bt.dm.fx.controls.toolbar.DMToolBar.DMToolBarBuilder;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;

/**
 * @author Ramalingesha ML
 *
 *         Created on Jul 31, 2021 7:59:50 PM
 */
public class DMReportActionsToolbar extends Pane {
	private DMReportActionsToolbarEvent toolbarEvent;
	private boolean showExportExcelOption;
	private boolean showExportPdfOption;

	public DMReportActionsToolbar(DMReportActionsToolbarEvent toolbarEvent) {
		this(toolbarEvent, true, true);
	}

	public DMReportActionsToolbar(DMReportActionsToolbarEvent toolbarEvent,
			boolean showExportExcelOption, boolean showExportPdfOption) {
		this.toolbarEvent = toolbarEvent;
		this.showExportExcelOption = showExportExcelOption;
		this.showExportPdfOption = showExportPdfOption;
		this.constructToolbar();
	}

	private void constructToolbar() {
		this.getChildren().add(this.getReportActionsToolbar());
	}

	private Pane getReportActionsToolbar() {
		FXButtonCmp viewButton = new FXButtonCmp(new FXButtonCmpBuilder()
				.text("dmReportView.buttons.view")
				.className("styled-secondary-button")
				.materialIcon(
						new FXMaterialDesignIcon(
								new FXMaterialDesignIconBuilder(
										MaterialDesignIcon.FILE)))
				.eventHandler(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						toolbarEvent.onViewBtnClick();
					}
				}));

		FXButtonCmp printButton = new FXButtonCmp(new FXButtonCmpBuilder()
				.text("dmReportView.buttons.print")
				.className("styled-secondary-button")
				.materialIcon(
						new FXMaterialDesignIcon(
								new FXMaterialDesignIconBuilder(
										MaterialDesignIcon.PRINTER)))
				.eventHandler(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						toolbarEvent.onPrintBtnClick();
					}
				}));

		FXButtonCmp cancelButton = new FXButtonCmp(new FXButtonCmpBuilder()
				.text("dmform.toolbar.button.cancel")
				.className("styled-secondary-button")
				.materialIcon(
						new FXMaterialDesignIcon(
								new FXMaterialDesignIconBuilder(
										MaterialDesignIcon.CLOSE)))
				.eventHandler(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						PubSubEvent.getInstance().publish("goToHome", null);
					}
				}));

		ObservableList<Node> buttons = FXCollections.observableArrayList(
				viewButton, printButton);

		if (this.showExportExcelOption) {
			FXButtonCmp excelButton = new FXButtonCmp(new FXButtonCmpBuilder()
					.text("dmReportView.buttons.excel")
					.className("styled-secondary-button")
					.materialIcon(
							new FXMaterialDesignIcon(
									new FXMaterialDesignIconBuilder(
											MaterialDesignIcon.FILE_EXCEL)))
					.eventHandler(new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent arg0) {
							toolbarEvent.onExcelBtnClick();
						}
					}));

			buttons.add(excelButton);
		}

		if (this.showExportPdfOption) {
			FXButtonCmp pdfButton = new FXButtonCmp(new FXButtonCmpBuilder()
					.text("dmReportView.buttons.pdf")
					.className("styled-secondary-button")
					.materialIcon(
							new FXMaterialDesignIcon(
									new FXMaterialDesignIconBuilder(
											MaterialDesignIcon.FILE_PDF)))
					.eventHandler(new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent arg0) {
							toolbarEvent.onPdfBtnClick();
						}
					}));

			buttons.add(pdfButton);
		}

		buttons.add(cancelButton);

		DMToolBar toolbar = new DMToolBar(new DMToolBarBuilder()
				.hideDefaultButtons(true).toolBarControls(buttons));
		HBox.setHgrow(toolbar, Priority.ALWAYS);

		HBox box = new HBox(toolbar);
		box.setPadding(new Insets(20, 0, 0, 0));

		return box;
	}
}
