/**
 * 
 */
package com.bt.dm.fx.controls.table;

import com.bt.dm.fx.controls.crosstable.DMCrossTableColumnModel;
import com.bt.dm.fx.controls.table.model.DMTableModel;
import com.bt.dm.fx.controls.table.model.FXTableColumnModel;

/**
 * @author Ramalingesha ML
 *
 *         Created on Oct 11, 2020 8:49:36 AM
 */
public class FxTableColumnSizeMapper<S extends DMTableModel> {
	private Double colWidthPercentage;
	private Double colWidthPercentageInMiniTable;
	private FXTableColumnModel<S, ?> fxTableColumnModel;
	private boolean showInMiniTable;

	public FxTableColumnSizeMapper(FXTableColumnModel<S, ?> fxTableColumnModel) {
		this.fxTableColumnModel = fxTableColumnModel;
	}

	public FxTableColumnSizeMapper(FXTableColumnModel<S, ?> fxTableColumnModel,
			Double colWidthPercentage) {
		this.fxTableColumnModel = fxTableColumnModel;
		this.colWidthPercentage = colWidthPercentage;
	}

	public FxTableColumnSizeMapper(FXTableColumnModel<S, ?> fxTableColumnModel,
			Double colWidthPercentage, boolean showInMiniTable,
			Double colWidthPercentageInMiniTable) {
		this.fxTableColumnModel = fxTableColumnModel;
		this.colWidthPercentage = colWidthPercentage;
		this.showInMiniTable = showInMiniTable;
		this.colWidthPercentageInMiniTable = colWidthPercentageInMiniTable;
	}

	public FxTableColumnSizeMapper(
			DMCrossTableColumnModel<DMTableModel> dmCrossTableColumnModel) {
		this(dmCrossTableColumnModel, null);
	}

	@SuppressWarnings("unchecked")
	public FxTableColumnSizeMapper(
			DMCrossTableColumnModel<DMTableModel> dmCrossTableColumnModel,
			Double colWidthPercentage) {
		this.fxTableColumnModel = (FXTableColumnModel<S, ?>) dmCrossTableColumnModel;
		this.colWidthPercentage = colWidthPercentage;
	}

	public Double getColWidthPercentage() {
		return colWidthPercentage;
	}

	public Double getColWidthPercentageInMiniTable() {
		return colWidthPercentageInMiniTable;
	}

	public FXTableColumnModel<S, ?> getFxTableColumnModel() {
		return fxTableColumnModel;
	}

	public boolean isShowInMiniTable() {
		return showInMiniTable;
	}
}
