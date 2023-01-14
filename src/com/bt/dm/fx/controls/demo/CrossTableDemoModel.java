package com.bt.dm.fx.controls.demo;

import com.bt.dm.fx.controls.table.model.DMTableModel;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 
 * @author Ramalingesha ML
 *
 *         Created on Feb 17, 2021 9:07:52 PM
 */
public class CrossTableDemoModel extends DMTableModel {

	private static final long serialVersionUID = 1L;

	private final DoubleProperty test1;
	private final StringProperty name;
	private final DoubleProperty test2;
	private final DoubleProperty test3;

	public CrossTableDemoModel(String name, Double test1, Double test2,
			Double test3) {
		this.name = new SimpleStringProperty(name);
		this.test1 = new SimpleDoubleProperty(test1);
		this.test2 = new SimpleDoubleProperty(test2);
		this.test3 = new SimpleDoubleProperty(test3);
	}

	public DoubleProperty test1Property() {
		return test1;
	}

	public StringProperty nameProperty() {
		return name;
	}

	public DoubleProperty test2Property() {
		return test2;
	}

	public DoubleProperty test3Property() {
		return test3;
	}
}