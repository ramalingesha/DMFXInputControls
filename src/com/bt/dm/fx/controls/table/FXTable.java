/**
 * 
 */
package com.bt.dm.fx.controls.table;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

/**
 * @author Ramalingesha ML
 *
 *         Created on Oct 10, 2020 7:39:23 PM
 */
public class FXTable<S extends TableModel> extends TableView<S> {
	public static class FXTableBuilder<S extends TableModel> {
		private ObservableList<FxTableColumnSizeMapper<S>> columnSizeMapperList;
		private ObservableList<S> data;

		public FXTableBuilder(
				ObservableList<FxTableColumnSizeMapper<S>> columnSizeMapperList) {
			this.columnSizeMapperList = columnSizeMapperList;
		}

		public FXTableBuilder<S> data(ObservableList<S> data) {
			this.data = data;
			return this;
		}
	}

	private FXTableBuilder<S> builder;

	public FXTable(FXTableBuilder<S> builder) {
		this.builder = builder;
		this.createComponent();
	}

	private void createComponent() {
		this.builder.columnSizeMapperList.stream()
				.forEach(
						mapper -> this.getColumns().add(
								mapper.getFxTableColumnModel()));
		if (this.builder.data != null) {
			this.setItems(this.builder.data);
		}
	}

	public void addRecord(S record) {
		this.builder.data.add(record);
	}

	public void resizeColumns() {
		this.builder.columnSizeMapperList.stream().forEach(
				mapper -> mapper
						.getFxTableColumnModel()
						.prefWidthProperty()
						.bind(this.prefWidthProperty().multiply(
								mapper.getColWidthPercentage())));
	}
}
