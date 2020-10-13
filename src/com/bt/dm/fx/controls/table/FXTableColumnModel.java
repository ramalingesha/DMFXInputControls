/**
 * 
 */
package com.bt.dm.fx.controls.table;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.util.Callback;

/**
 * @author Ramalingesha ML
 *
 *         Created on Oct 10, 2020 8:10:00 PM
 */
public class FXTableColumnModel<S extends TableModel, T extends Object> extends
		TableColumn<S, T> {
	private String header;
	private String propertyName;

	public FXTableColumnModel(String header, String propertyName) {
		this(header, propertyName, null);
	}

	public FXTableColumnModel(String header, String propertyName,
			Double colWidthPercentage) {
		this.header = header;
		this.propertyName = propertyName;
		this.createComponent();
	}

	private void createComponent() {
		this.setText(this.header);
		this.setCellValueFactory(new PropertyValueFactory<S, T>(propertyName));
//		this.setCellFactory(new Callback<TableColumn<S, T>, TableCell<S, T>>() {
//
//			@Override
//			public TableCell<S, T> call(TableColumn<S, T> column) {
//				return new TableCell<S, T>() {
//					@Override
//					protected void updateItem(T item, boolean empty) {
//						super.updateItem(item, empty);
//
//						if (!empty) {
//							System.out.println(item);
//							// setFont(Fonts.getInstance().getEnglishFont());
//							setItem(item);
//							setTextFill(Color.BLUE);
//							setStyle("-fx-background-color: red;");
//						}
//					}
//				};
//			}
//		});
		// this.setCellFactory((column) -> {
		// TableCell<S, T> cell = new TableCell<S, T>(){
		//
		// };
		//
		// return cell;
		// });
	}
}
