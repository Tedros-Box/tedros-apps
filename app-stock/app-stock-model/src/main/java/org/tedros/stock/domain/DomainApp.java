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
	static final String STOCK_CONFIG = "STOCK_CONFIG";
	static final String ENTRY_TYPE = "ENTRY_TYPE";
	static final String OUT_TYPE = "OUT_TYPE";
	static final String STOCK_ENTRY = "STOCK_ENTRY";
	static final String STOCK_OUT = "STOCK_OUT";
	static final String INVENTORY_REPORT = "INVENTORY_REPORT";
	static final String PRODUCT_REPORT = "PRODUCT_REPORT";
	static final String SEP = SEPARATOR;
	
	static final String MNEMONIC = "STCK";

	static final String PRODUCT_REPORT_FORM_ID = MNEMONIC + SEP + PRODUCT_REPORT + SEP + FORM;
	static final String PRODUCT_REPORT_VIEW_ID = MNEMONIC + SEP + PRODUCT_REPORT + SEP + VIEW;
	
	static final String INVENTORY_REPORT_FORM_ID = MNEMONIC + SEP + INVENTORY_REPORT + SEP + FORM;
	static final String INVENTORY_REPORT_VIEW_ID = MNEMONIC + SEP + INVENTORY_REPORT + SEP + VIEW;
	
	static final String PRODUCT_MODULE_ID = MNEMONIC + SEP + PRODUCT + SEP + MODULE;
	static final String PRODUCT_FORM_ID = MNEMONIC + SEP + PRODUCT + SEP + FORM;
	static final String PRODUCT_VIEW_ID = MNEMONIC + SEP + PRODUCT + SEP + VIEW;

	static final String STOCK_CONFIG_MODULE_ID = MNEMONIC + SEP + STOCK_CONFIG + SEP + MODULE;
	static final String STOCK_CONFIG_FORM_ID = MNEMONIC + SEP + STOCK_CONFIG + SEP + FORM;
	static final String STOCK_CONFIG_VIEW_ID = MNEMONIC + SEP + STOCK_CONFIG + SEP + VIEW;

	static final String ENTRY_TYPE_MODULE_ID = MNEMONIC + SEP + ENTRY_TYPE + SEP + MODULE;
	static final String ENTRY_TYPE_FORM_ID = MNEMONIC + SEP + ENTRY_TYPE + SEP + FORM;
	static final String ENTRY_TYPE_VIEW_ID = MNEMONIC + SEP + ENTRY_TYPE + SEP + VIEW;

	static final String OUT_TYPE_MODULE_ID = MNEMONIC + SEP + OUT_TYPE + SEP + MODULE;
	static final String OUT_TYPE_FORM_ID = MNEMONIC + SEP + OUT_TYPE + SEP + FORM;
	static final String OUT_TYPE_VIEW_ID = MNEMONIC + SEP + OUT_TYPE + SEP + VIEW;

	static final String STOCK_ENTRY_MODULE_ID = MNEMONIC + SEP + STOCK_ENTRY + SEP + MODULE;
	static final String STOCK_ENTRY_FORM_ID = MNEMONIC + SEP + STOCK_ENTRY + SEP + FORM;
	static final String STOCK_ENTRY_VIEW_ID = MNEMONIC + SEP + STOCK_ENTRY + SEP + VIEW;

	static final String STOCK_OUT_MODULE_ID = MNEMONIC + SEP + STOCK_OUT + SEP + MODULE;
	static final String STOCK_OUT_FORM_ID = MNEMONIC + SEP + STOCK_OUT + SEP + FORM;
	static final String STOCK_OUT_VIEW_ID = MNEMONIC + SEP + STOCK_OUT + SEP + VIEW;



}
