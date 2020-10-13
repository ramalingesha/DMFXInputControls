/**
 * 
 */
package com.bt.dm.fx.controls.table;

/**
 * @author Ramalingesha ML
 *
 *         Created on Oct 11, 2020 8:49:36 AM
 */
public class FxTableColumnSizeMapper<S extends TableModel> {
	private FXTableColumnModel<S, ?> fxTableColumnModel;
	private Double colWidthPercentage;

	public FxTableColumnSizeMapper(FXTableColumnModel<S, ?> fxTableColumnModel) {
		this.fxTableColumnModel = fxTableColumnModel;
	}

	public FxTableColumnSizeMapper(FXTableColumnModel<S, ?> fxTableColumnModel,
			Double colWidthPercentage) {
		this.fxTableColumnModel = fxTableColumnModel;
		this.colWidthPercentage = colWidthPercentage;
	}

	public FXTableColumnModel<S, ?> getFxTableColumnModel() {
		return fxTableColumnModel;
	}

	public Double getColWidthPercentage() {
		return colWidthPercentage;
	}
}
