/**
 * 
 */
package com.bt.dm.fx.controls.crosstable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.bt.dm.fx.controls.table.DMTableModel;

/**
 * @author Ramalingesha ML
 *
 *         Created on Feb 19, 2021 7:42:15 PM
 */
public class DMCrossTableRowDataMap extends DMTableModel {
	private static final long serialVersionUID = 1L;
	private Map<String, Object> rowDataMap;

	public DMCrossTableRowDataMap(Map<String, Object> rowDataMap) {
		this.rowDataMap = rowDataMap;
	}

	public Object getValue(String columnName) {
		return this.rowDataMap.get(columnName);
	}

	public List<String> getKeys() {
		return new ArrayList<String>(this.rowDataMap.keySet());
	}

	public Object setValue(String columnName, Object value) {
		return this.rowDataMap.put(columnName, value);
	}
}
