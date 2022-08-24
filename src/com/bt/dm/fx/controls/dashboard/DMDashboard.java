package com.bt.dm.fx.controls.dashboard;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import com.bt.dm.core.pubsub.PubSubEvent;
import com.bt.dm.core.pubsub.PubSubEventDataModel;
import com.bt.dm.core.pubsub.PubSubEventHandler;
import com.bt.dm.fx.controls.theme.ControlsTheme;
import com.bt.dm.fx.controls.utils.SizeHelper;
import com.bt.dm.fx.controls.view.DMView;

/**
 * 
 * @author Ramalingesha ML
 *
 *         Created on Dec 16, 2020 10:55:12 AM
 */
public class DMDashboard extends DMView {
	public static class DMDashboardBuilder {
		private Pane titleBar;
		private Pane headerPane;
		private Pane sideBar;
		private Pane footerPane;

		public DMDashboardBuilder titleBar(Pane titleBar) {
			this.titleBar = titleBar;
			return this;
		}

		public DMDashboardBuilder headerPane(Pane headerPane) {
			this.headerPane = headerPane;
			return this;
		}

		public DMDashboardBuilder sideBar(Pane sideBar) {
			this.sideBar = sideBar;
			return this;
		}

		public DMDashboardBuilder footerPane(Pane footerPane) {
			this.footerPane = footerPane;
			return this;
		}
	}

	private DMDashboardBuilder builder;
	private Pane contentPane;
	private PubSubEventHandler handler;
	private BorderPane borderPane;

	public DMDashboard(DMDashboardBuilder builder) {
		this.builder = builder;
	}

	@Override
	public Pane getView() {
		borderPane = new BorderPane();
		borderPane.getStyleClass().add("dashboard");
		this.addRootThemeStyleSheet();

		// Content section
		contentPane = new VBox();
		int headerFooterHeight = 0;

		// Top Section
		if (this.builder.titleBar != null || this.builder.headerPane != null) {
			headerFooterHeight += 50;
			VBox box = new VBox();
			if (this.builder.titleBar != null) {
				this.builder.titleBar.getStyleClass()
						.add("dashboard-title-bar");
				box.getChildren().add(this.builder.titleBar);
			}
			if (this.builder.headerPane != null) {
				this.builder.titleBar.getStyleClass().add(
						"dashboard-header-pane");
				box.getChildren().add(this.builder.headerPane);
			}

			borderPane.setTop(box);
		}

		// Left Section
		if (this.builder.sideBar != null) {
			this.builder.titleBar.getStyleClass().add("dashboard-side-bar");
			borderPane.setLeft(this.builder.sideBar);
		}

		// Footer Section
		if (this.builder.footerPane != null) {
			headerFooterHeight += 50;
			this.builder.titleBar.getStyleClass().add("dashboard-footer-pane");
			borderPane.setBottom(this.builder.footerPane);
		}

		ScrollPane scrollPane = new ScrollPane(contentPane);
		scrollPane.setPrefHeight(SizeHelper.MAIN_PANEL_SIZE.getHeight()
				- headerFooterHeight);
		scrollPane.setFitToHeight(true);

		borderPane.setCenter(scrollPane);

		this.subscribeOnThemeChange();

		return borderPane;
	}

	private void subscribeOnThemeChange() {
		handler = new PubSubEventHandler() {

			@Override
			public void onPubSubData(PubSubEventDataModel data) {
				addRootThemeStyleSheet();
			}
		};
		PubSubEvent.getInstance().subscribe("THEME", handler);
	}

	private void addRootThemeStyleSheet() {
		borderPane.getStylesheets().clear();
		borderPane.getStylesheets().add(
				getClass().getResource("Dashboard.css").toExternalForm());
		borderPane.getStylesheets().add(
				getClass().getResource(
						ControlsTheme.getThemeCssFileName("Dashboard"))
						.toExternalForm());
	}

	public void reloadContentPane(Pane contentPane) {
		this.contentPane.getChildren().clear();
		this.contentPane.getChildren().add(contentPane);
	}
}
