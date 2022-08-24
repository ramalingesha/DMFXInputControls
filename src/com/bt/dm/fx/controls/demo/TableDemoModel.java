package com.bt.dm.fx.controls.demo;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import com.bt.dm.fx.controls.table.DMTableModel;

/**
 * 
 * @author Ramalingesha ML
 *
 *         Created on Dec 30, 2020 1:54:18 PM
 */
public class TableDemoModel extends DMTableModel {

	private static final long serialVersionUID = 1L;

	private final IntegerProperty slNo;
	private final StringProperty name;
	private final StringProperty dateOfBirth;
	private final StringProperty address;

	public TableDemoModel(Integer slNo, String name, String dateOfBirth,
			String address) {
		this.slNo = new SimpleIntegerProperty(slNo);
		this.name = new SimpleStringProperty(name);
		this.dateOfBirth = new SimpleStringProperty(dateOfBirth);
		this.address = new SimpleStringProperty(address);
	}

	public IntegerProperty slNoProperty() {
		return slNo;
	}

	public StringProperty nameProperty() {
		return name;
	}

	public StringProperty dateOfBirthProperty() {
		return dateOfBirth;
	}

	public StringProperty addressProperty() {
		return address;
	}
}