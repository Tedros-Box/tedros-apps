/**
 * 
 */
package org.tedros.stock.domain;

/**
 * @author Davis Gordon
 *
 */
public interface DomainApp {

	static final String SEPARATOR = "_";
	static final String VIEW = "VIEW";
	static final String MODULE = "MODULE";
	static final String FORM = "FORM";
	
	static final String PRODUCT = "PRODUCT";
	static final String SEP = SEPARATOR;
	
	static final String MNEMONIC = "STCK";

	static final String PRODUCT_MODULE_ID = MNEMONIC + SEP + PRODUCT + SEP + MODULE;
	static final String PRODUCT_FORM_ID = MNEMONIC + SEP + PRODUCT + SEP + FORM;
	static final String PRODUCT_VIEW_ID = MNEMONIC + SEP + PRODUCT + SEP + VIEW;
	

}
