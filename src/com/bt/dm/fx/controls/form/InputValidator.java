/**
 * 
 */
package com.bt.dm.fx.controls.form;

/**
 * @author Ramalingesha ML
 *
 *         Created on Dec 29, 2020 8:18:39 AM
 */
public class InputValidator {
	private ValidationType validationType;
	private String errorMessage;

	public InputValidator(ValidationType validationType, String errorMessage) {
		super();
		this.validationType = validationType;
		this.errorMessage = errorMessage;
	}

	public ValidationType getValidationType() {
		return validationType;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
}
