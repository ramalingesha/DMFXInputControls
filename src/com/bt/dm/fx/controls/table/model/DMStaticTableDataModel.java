package com.bt.dm.fx.controls.table.model;

import com.bt.dm.core.model.DMModel;
import com.bt.dm.fx.controls.events.IconClickEvent;

import javafx.beans.property.StringProperty;
import javafx.scene.Node;

/**
 * @author Ramalingesha ML
 *
 *         Created on Jan 14, 2023 09:02:03 AM
 */
public class DMStaticTableDataModel implements DMModel {

	private static final long serialVersionUID = 1L;
	private String columnId;
	private String text;
	private StringProperty valueProperty;
	private Node node;
	private String className;
	private boolean i18n;
	private boolean rightAlign;
	private boolean showEditIcon;
	private double width = 100;
	private IconClickEvent editIconClickEvent;

	public DMStaticTableDataModel(String columnId, String text) {
		this(columnId, text, false);
	}
	
	public DMStaticTableDataModel(String columnId, String text, boolean showEditIcon) {
		this.columnId = columnId;
		this.text = text;
		this.showEditIcon = showEditIcon;
	}
	
	public DMStaticTableDataModel(String columnId, Node node) {
		this.columnId = columnId;
		this.node = node;
	}
	
	public DMStaticTableDataModel(String columnId, StringProperty valueProperty) {
		this.columnId = columnId;
		this.valueProperty = valueProperty;
	}

	public DMStaticTableDataModel i18n(boolean i18n) {
		this.i18n = i18n;
		return this;
	}

	public DMStaticTableDataModel className(String className) {
		this.className = className;
		return this;
	}

	public DMStaticTableDataModel rightAlign(boolean rightAlign) {
		this.rightAlign = rightAlign;
		return this;
	}
	
	public DMStaticTableDataModel width(double width) {
		this.width = width;
		return this;
	}
	
	public DMStaticTableDataModel editIconClickEvent(IconClickEvent editIconClickEvent) {
		this.editIconClickEvent = editIconClickEvent;
		return this;
	}

	public String getColumnId() {
		return columnId;
	}

	public String getText() {
		return text;
	}

	public String getClassName() {
		return className;
	}

	public boolean isI18n() {
		return i18n;
	}

	public boolean isRightAlign() {
		return rightAlign;
	}

	public Node getNode() {
		return node;
	}

	public StringProperty getValueProperty() {
		return valueProperty;
	}

	public boolean isShowEditIcon() {
		return showEditIcon;
	}

	public IconClickEvent getEditIconClickEvent() {
		return editIconClickEvent;
	}

	public double getWidth() {
		return width;
	}
}
