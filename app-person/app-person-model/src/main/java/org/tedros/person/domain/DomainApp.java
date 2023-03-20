/**
 * 
 */
package org.tedros.person.domain;

/**
 * @author Davis Gordon
 *
 */
public interface DomainApp {

	static final String SEPARATOR = "_";
	static final String VIEW = "VIEW";
	static final String MODULE = "MODULE";
	static final String FORM = "FORM";
	static final String PERSON = "PERSON";
	static final String OTHER_PERSON = "OTHER_PERSON";
	static final String PERSON_CATEGORY = "PERSON_CATEGORY";
	static final String CLIENT_COMPANY = "CLIENT_COMPANY";
	static final String CUSTOMER = "CUSTOMER";
	static final String NATURAL_PERSON = "NATURAL_PERSON";
	static final String LEGAL_PERSON = "LEGAL_PERSON";
	static final String TYPE = "TYPE";
	static final String STATUS = "STATUS";
	static final String STAFF = "STAFF";
	static final String EMPLOYEE = "EMPLOYEE";
	static final String REPORT = "REPORT";
	static final String SEP = SEPARATOR;
	
	static final String MNEMONIC = "TPERSON";
	
	static final String CUSTOMER_MODULE_ID = MNEMONIC + SEP + CUSTOMER + SEP + MODULE;
	static final String CUSTOMER_FORM_ID = MNEMONIC + SEP + CUSTOMER + SEP + FORM;
	static final String CUSTOMER_VIEW_ID = MNEMONIC + SEP + CUSTOMER + SEP + VIEW;

	static final String NATURAL_PERSON_FORM_ID = MNEMONIC + SEP + NATURAL_PERSON + SEP + FORM;
	static final String NATURAL_PERSON_VIEW_ID = MNEMONIC + SEP + NATURAL_PERSON + SEP + VIEW;
	static final String NATURAL_PERSON_MODULE_ID = MNEMONIC + SEP + NATURAL_PERSON + SEP + MODULE;

	static final String LEGAL_PERSON_FORM_ID = MNEMONIC + SEP + LEGAL_PERSON + SEP + FORM;
	static final String LEGAL_PERSON_VIEW_ID = MNEMONIC + SEP + LEGAL_PERSON + SEP + VIEW;
	static final String LEGAL_PERSON_MODULE_ID = MNEMONIC + SEP + LEGAL_PERSON + SEP + MODULE;

	static final String EMPLOYEE_FORM_ID = MNEMONIC + SEP + EMPLOYEE + SEP + FORM;
	static final String EMPLOYEE_VIEW_ID = MNEMONIC + SEP + EMPLOYEE + SEP + VIEW;
	static final String EMPLOYEE_MODULE_ID = MNEMONIC + SEP + EMPLOYEE + SEP + MODULE;
	
	static final String EMPLOYEE_REPORT_FORM_ID = MNEMONIC + SEP + EMPLOYEE + SEP + REPORT + SEP + FORM;
	static final String EMPLOYEE_REPORT_VIEW_ID = MNEMONIC + SEP + EMPLOYEE + SEP + REPORT + SEP + VIEW;

	static final String NATURAL_PERSON_REPORT_FORM_ID = MNEMONIC + SEP + NATURAL_PERSON + SEP + REPORT + SEP + FORM;
	static final String NATURAL_PERSON_REPORT_VIEW_ID = MNEMONIC + SEP + NATURAL_PERSON + SEP + REPORT + SEP + VIEW;
	
	static final String LEGAL_PERSON_REPORT_FORM_ID = MNEMONIC + SEP + LEGAL_PERSON + SEP + REPORT + SEP + FORM;
	static final String LEGAL_PERSON_REPORT_VIEW_ID = MNEMONIC + SEP + LEGAL_PERSON + SEP + REPORT + SEP + VIEW;
	
	static final String REPORT_MODULE_ID = MNEMONIC + SEP + REPORT + SEP  + MODULE;

	static final String LEGAL_TYPE_FORM_ID = MNEMONIC + SEP + LEGAL_PERSON + SEP + TYPE + SEP + FORM;
	static final String LEGAL_TYPE_VIEW_ID = MNEMONIC + SEP + LEGAL_PERSON + SEP + TYPE + SEP  + VIEW;

	static final String PERSON_TYPE_MODULE_ID = MNEMONIC + SEP + PERSON + TYPE + SEP  + MODULE;
	
	static final String STAFF_TYPE_FORM_ID = MNEMONIC + SEP + STAFF + SEP + TYPE + SEP + FORM;
	static final String STAFF_TYPE_VIEW_ID = MNEMONIC + SEP + STAFF + SEP + TYPE + SEP  + VIEW;
	
	static final String PERSON_TYPE_FORM_ID = MNEMONIC + SEP + PERSON + SEP + TYPE + SEP + FORM;
	static final String PERSON_TYPE_VIEW_ID = MNEMONIC + SEP + PERSON + SEP + TYPE + SEP  + VIEW;

	static final String PERSON_STATUS_MODULE_ID = MNEMONIC + SEP + PERSON + TYPE + SEP  + MODULE;
	static final String PERSON_STATUS_FORM_ID = MNEMONIC + SEP + PERSON + SEP + STATUS + SEP + FORM;
	static final String PERSON_STATUS_VIEW_ID = MNEMONIC + SEP + PERSON + SEP + STATUS + SEP  + VIEW;
	
	static final String OTHER_PERSON_MODULE_ID = MNEMONIC + SEP + OTHER_PERSON + SEP  + MODULE;
	static final String OTHER_PERSON_FORM_ID = MNEMONIC + SEP + OTHER_PERSON + SEP + FORM;
	static final String OTHER_PERSON_VIEW_ID = MNEMONIC + SEP + OTHER_PERSON + SEP  + VIEW;

	static final String PERSON_CATEGORY_MODULE_ID = MNEMONIC + SEP + PERSON_CATEGORY + SEP  + MODULE;
	static final String PERSON_CATEGORY_FORM_ID = MNEMONIC + SEP + PERSON_CATEGORY + SEP + FORM;
	static final String PERSON_CATEGORY_VIEW_ID = MNEMONIC + SEP + PERSON_CATEGORY + SEP  + VIEW;


}
