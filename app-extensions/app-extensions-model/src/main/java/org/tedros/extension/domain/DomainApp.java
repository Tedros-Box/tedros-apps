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
	static final String CONTACT = "CONTACT";
	static final String DOCUMENT = "DOCUMENT";
	static final String TYPE = "TYPE";
	static final String EVENT = "EVENT";
	static final String STATUS = "STATUS";
	static final String COUNTRY = "COUNTRY";
	static final String CITY = "CITY";
	static final String ADMIN_AREA = "ADMIN_AREA";
	static final String PLACE = "PLACE";
	static final String ADDRESS = "ADDRESS";
	static final String STREET_TYPE = "STREET_TYPE";
	static final String PLACE_TYPE = "PLACE_TYPE";
	static final String PLACE_REPORT = "PLACE_REPORT";
	static final String DOCUMENT_REPORT = "DOCUMENT_REPORT";
	
	static final String SEP = SEPARATOR;
	
	static final String MNEMONIC = "EXTS";

	// contact
	static final String CONTACT_FORM_ID = MNEMONIC + SEP + CONTACT + SEP + FORM;
	static final String CONTACT_VIEW_ID = MNEMONIC + SEP + CONTACT + SEP + VIEW;
	static final String CONTACT_MODULE_ID = MNEMONIC + SEP + CONTACT + SEP + MODULE;

	// document
	static final String DOCUMENT_FORM_ID = MNEMONIC + SEP + DOCUMENT + SEP + FORM;
	static final String DOCUMENT_MODULE_ID = MNEMONIC + SEP + DOCUMENT + SEP + MODULE;

	static final String DOCUMENT_TYPE_FORM_ID = MNEMONIC + SEP + DOCUMENT + SEP + TYPE + SEP + FORM;
	static final String DOCUMENT_STATUS_FORM_ID = MNEMONIC + SEP + DOCUMENT + SEP + STATUS + SEP + FORM;
	
	static final String DOCUMENT_REPORT_FORM_ID = MNEMONIC + SEP + DOCUMENT_REPORT + SEP + FORM;
	static final String DOCUMENT_REPORT_VIEW_ID = MNEMONIC + SEP + DOCUMENT_REPORT + SEP + VIEW;

	// location
	static final String COUNTRY_FORM_ID = MNEMONIC + SEP + COUNTRY + SEP + FORM;
	static final String COUNTRY_VIEW_ID = MNEMONIC + SEP + COUNTRY + SEP + VIEW;
	static final String COUNTRY_MODULE_ID = MNEMONIC + SEP + COUNTRY + SEP + MODULE;

	static final String CITY_FORM_ID = MNEMONIC + SEP + CITY + SEP + FORM;
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

	static final String STREET_TYPE_FORM_ID = MNEMONIC + SEP + STREET_TYPE + SEP + FORM;
	static final String STREET_TYPE_VIEW_ID = MNEMONIC + SEP + STREET_TYPE + SEP + VIEW;
	static final String STREET_TYPE_MODULE_ID = MNEMONIC + SEP + STREET_TYPE + SEP + MODULE;

	static final String PLACE_TYPE_FORM_ID = MNEMONIC + SEP + PLACE_TYPE + SEP + FORM;
	static final String PLACE_TYPE_VIEW_ID = MNEMONIC + SEP + PLACE_TYPE + SEP + VIEW;
	static final String PLACE_TYPE_MODULE_ID = MNEMONIC + SEP + PLACE_TYPE + SEP + MODULE;
	

	static final String PLACE_REPORT_FORM_ID = MNEMONIC + SEP + PLACE_REPORT + SEP + FORM;
	static final String PLACE_REPORT_VIEW_ID = MNEMONIC + SEP + PLACE_REPORT + SEP + VIEW;





}
