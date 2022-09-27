/**
 * 
 */
package com.bt.dm.fx.controls.dialog.search;

import java.util.Date;

import com.bt.dm.core.model.DMModel;

/**
 * @author Ramalingesha ML
 *
 *         Created on Sep 26, 2022 07:43:03 PM
 */
public class DMSearchInputModel implements DMModel {

	private static final long serialVersionUID = 1L;
	private Date fromDate;
	private Date toDate;
	private Integer shift;
	private Integer cattleType;

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Integer getShift() {
		return shift;
	}

	public void setShift(Integer shift) {
		this.shift = shift;
	}

	public Integer getCattleType() {
		return cattleType;
	}

	public void setCattleType(Integer cattleType) {
		this.cattleType = cattleType;
	}
}
