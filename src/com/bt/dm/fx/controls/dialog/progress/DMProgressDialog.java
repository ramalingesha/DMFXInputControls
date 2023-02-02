package com.bt.dm.fx.controls.dialog.progress;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.HBox;

import com.bt.dm.core.exception.PubSubErrorDataModel;
import com.bt.dm.core.pubsub.ModalWindowPubSubTopicModel;
import com.bt.dm.core.pubsub.PubSubEvent;
import com.bt.dm.fx.controls.DMLabelBuilder;
import com.bt.dm.fx.controls.events.DMTaskProgressEvent;
import com.bt.dm.fx.controls.labels.FXLabelCmp;

/**
 * @author Ramalingesha ML
 *
 *         Created on Jul 15, 2022 7:45:46 PM
 */
public class DMProgressDialog {
	private ProgressIndicator loadingSpinner;
	private ModalWindowPubSubTopicModel modelData;
	private static DMProgressDialog instance;
	private SpinnerService service;

	private DMProgressDialog() {
		// private constructor
	}

	public static DMProgressDialog getInstance(
			DMTaskProgressEvent dmTaskProgressEvent) {
		if (instance == null) {
			instance = new DMProgressDialog();
		}

		instance.createPopupDialogContent(dmTaskProgressEvent);

		return instance;
	}

	private void createPopupDialogContent(
			DMTaskProgressEvent dmTaskProgressEvent) {
		this.service = new SpinnerService(dmTaskProgressEvent);
		this.service.setOnSucceeded(event -> {
			dmTaskProgressEvent.successCallback();
			this.stopLoadingSpinner();
		});
		
		this.service.setOnFailed(event -> {
			PubSubEvent.getInstance().publish("showAppError", new PubSubErrorDataModel(this.service.getException()));
			this.stopLoadingSpinner();
		});

		this.initLoadingSpinner();

		FXLabelCmp loadingMessageLbl = new FXLabelCmp(
				new DMLabelBuilder().label("app.spinner.message"));

		HBox contentPanel = new HBox(10);
		contentPanel.setPadding(new Insets(20));
		contentPanel.setAlignment(Pos.CENTER);
		contentPanel.getChildren().addAll(this.loadingSpinner,
				loadingMessageLbl);

		modelData = new ModalWindowPubSubTopicModel(null, contentPanel, 500d,
				200d);
	}

	private void initLoadingSpinner() {
		this.loadingSpinner = new ProgressIndicator();
		this.loadingSpinner.setMaxSize(100, 100);
	}

	public void startLoadingSpinner() {
		service.restart();
		PubSubEvent.getInstance().publish("openModalWindow", this.modelData);
	}

	public void stopLoadingSpinner() {
		PubSubEvent.getInstance().publish("closeModalWindow", null);
	}
}

class SpinnerService extends Service<Void> {
	private DMTaskProgressEvent dmTaskProgressEvent;

	public SpinnerService(DMTaskProgressEvent dmTaskProgressEvent) {
		this.dmTaskProgressEvent = dmTaskProgressEvent;
	}

	@Override
	protected Task<Void> createTask() {
		return new Task<Void>() {

			@Override
			protected Void call() throws Exception {
				updateProgress(50, 100);
				dmTaskProgressEvent.backgroundTask();
				updateProgress(100, 100);

				return null;
			}
		};
	}

}