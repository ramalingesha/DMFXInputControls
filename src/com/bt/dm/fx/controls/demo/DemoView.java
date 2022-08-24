package com.bt.dm.fx.controls.demo;

import javafx.collections.FXCollections;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;

import com.bt.dm.fx.controls.tab.FXTab;
import com.bt.dm.fx.controls.tab.FXTabPanel;
import com.bt.dm.fx.controls.utils.SizeHelper;

/**
 * @author Ramalingesha ML
 *
 *         Created on Nov 21, 2020 6:29:49 PM
 */
public class DemoView extends Pane {
	public DemoView() {
		this.createComponent();
	}

	private void createComponent() {
		FormDemoView formDemoView = new FormDemoView();
		FXTab controlsTab = new FXTab("Controls", new ComponentsDemoView());
		FXTab formTab = new FXTab("Form", formDemoView);
		FXTab tableTab = new FXTab("Table", new TableDemoView());
		FXTab crossTableTab = new FXTab("Cross Table", new CrossTableDemoView());
		FXTab sideBar = new FXTab("Sidebar", new SideBarDemoView());
		FXTab materialDesignIcons = new FXTab("Material Icons",
				new MaterialIconsDemoView());
		FXTab fontAwesomeIcons = new FXTab("Font Icons", new FAIconsDemoView());

		FXTabPanel tabPanel = new FXTabPanel(FXCollections.observableArrayList(
				controlsTab, formTab, tableTab, crossTableTab, sideBar, materialDesignIcons,
				fontAwesomeIcons));
		tabPanel.getSelectionModel().selectedItemProperty()
				.addListener((ov, oldTab, newTab) -> {
					if (newTab == formTab) {
						formDemoView.setFocus();
					}
				});
		
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setPrefSize(SizeHelper.SCREEN_SIZE.getWidth(), SizeHelper.SCREEN_SIZE.getHeight());
		scrollPane.setContent(tabPanel);
		scrollPane.setFitToWidth(true);

		this.getChildren().add(scrollPane);
	}
}
