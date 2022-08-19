/**
 * 
 */
package org.tedros.extension.domain;

/**
 * @author Davis Gordon
 *
 */
public interface DomainApp {
	static final String SEPARATOR = "_";
	static final String VIEW = "VIEW";
	static final String MODULE = "MODULE";
	static final String FORM = "FORM";
	static final String CONTACT = "COUNTRY";
	/*static final String CITY = "CITY";
	static final String ADMIN_AREA = "ADMIN_AREA";
	static final String PLACE = "PLACE";
	static final String ADDRESS = "ADDRESS";*/
	static final String SEP = SEPARATOR;
	
	static final String MNEMONIC = "TAPEXT";

	static final String CONTACT_FORM_ID = MNEMONIC + SEP + CONTACT + SEP + FORM;
	static final String CONTACT_VIEW_ID = MNEMONIC + SEP + CONTACT + SEP + VIEW;
	static final String CONTACT_MODULE_ID = MNEMONIC + SEP + CONTACT + SEP + MODULE;

	/*static final String CITY_FORM_ID = MNEMONIC + SEP + CITY + SEP + FORM;
	static final String CITY_VIEW_ID = MNEMONIC + SEP + CITY + SEP + VIEW;
	static final String CITY_MODULE_ID = MNEMONIC + SEP + CITY + SEP + MODULE;
	
	static final String ADMIN_AREA_FORM_ID = MNEMONIC + SEP + ADMIN_AREA + SEP + FORM;
	static final String ADMIN_AREA_VIEW_ID = MNEMONIC + SEP + ADMIN_AREA + SEP + VIEW;
	static final String ADMIN_AREA_MODULE_ID = MNEMONIC + SEP + ADMIN_AREA + SEP + MODULE;

	static final String PLACE_FORM_ID = MNEMONIC + SEP + PLACE + SEP + FORM;
	static final String PLACE_VIEW_ID = MNEMONIC + SEP + PLACE + SEP + VIEW;
	static final String PLACE_MODULE_ID = MNEMONIC + SEP + PLACE + SEP + MODULE;

	static final String ADDRESS_FORM_ID = MNEMONIC + SEP + ADDRESS + SEP + FORM;
	static final String ADDRESS_VIEW_ID = MNEMONIC + SEP + ADDRESS + SEP + VIEW;
	static final String ADDRESS_MODULE_ID = MNEMONIC + SEP + ADDRESS + SEP + MODULE;
*/




}
