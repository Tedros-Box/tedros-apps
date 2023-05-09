/**
 * 
 */
package org.tedros.sample.domain;

/**
 * @author Davis Gordon
 *
 */
public interface DomainApp {

	static final String SEPARATOR = "_";
	static final String VIEW = "VIEW";
	static final String MODULE = "MODULE";
	static final String FORM = "FORM";
	
	static final String SALE = "SALE";
	static final String SALE_STATUS = "SALE_STATUS";
	static final String SALE_TYPE = "SALE_TYPE";
	static final String ORDER = "ORDER";
	static final String ORDER_STATUS = "ORDER_STATUS";
	static final String PRODUCT_PRICE = "PRODUCT_PRICE";
	static final String SEP = SEPARATOR;
	
	static final String MNEMONIC = "Smpls";

	static final String SALE_MODULE_ID = MNEMONIC + SEP + SALE + SEP + MODULE;
	static final String SALE_FORM_ID = MNEMONIC + SEP + SALE + SEP + FORM;
	static final String SALE_VIEW_ID = MNEMONIC + SEP + SALE + SEP + VIEW;

	static final String SALE_TYPE_MODULE_ID = MNEMONIC + SEP + SALE_TYPE + SEP + MODULE;
	static final String SALE_TYPE_FORM_ID = MNEMONIC + SEP + SALE_TYPE + SEP + FORM;
	static final String SALE_TYPE_VIEW_ID = MNEMONIC + SEP + SALE_TYPE + SEP + VIEW;

	static final String SALE_STATUS_MODULE_ID = MNEMONIC + SEP + SALE_STATUS + SEP + MODULE;
	static final String SALE_STATUS_FORM_ID = MNEMONIC + SEP + SALE_STATUS + SEP + FORM;
	static final String SALE_STATUS_VIEW_ID = MNEMONIC + SEP + SALE_STATUS + SEP + VIEW;

	static final String ORDER_MODULE_ID = MNEMONIC + SEP + ORDER + SEP + MODULE;
	static final String ORDER_FORM_ID = MNEMONIC + SEP + ORDER + SEP + FORM;
	static final String ORDER_VIEW_ID = MNEMONIC + SEP + ORDER + SEP + VIEW;

	static final String ORDER_STATUS_MODULE_ID = MNEMONIC + SEP + ORDER_STATUS + SEP + MODULE;
	static final String ORDER_STATUS_FORM_ID = MNEMONIC + SEP + ORDER_STATUS + SEP + FORM;
	static final String ORDER_STATUS_VIEW_ID = MNEMONIC + SEP + ORDER_STATUS + SEP + VIEW;

	static final String PRODUCT_PRICE_MODULE_ID = MNEMONIC + SEP + PRODUCT_PRICE + SEP + MODULE;
	static final String PRODUCT_PRICE_FORM_ID = MNEMONIC + SEP + PRODUCT_PRICE + SEP + FORM;
	static final String PRODUCT_PRICE_VIEW_ID = MNEMONIC + SEP + PRODUCT_PRICE + SEP + VIEW;



}
