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
	static final String VOLUNTARY = "VOLUNTARY";
	static final String MEMBER = "MEMBER";
	static final String PHILANTHROPE = "PHILANTHROPE";
	static final String PERSON_CATEGORY = "PERSON_CATEGORY";
	static final String CLIENT_COMPANY = "CLIENT_COMPANY";
	static final String CUSTOMER = "CUSTOMER";
	static final String NATURAL_PERSON = "NATURAL_PERSON";
	static final String LEGAL_PERSON = "LEGAL_PERSON";
	static final String COST_CENTER = "COST_CENTER";
	static final String TYPE = "TYPE";
	static final String STATUS = "STATUS";
	static final String STAFF = "STAFF";
	static final String EMPLOYEE = "EMPLOYEE";
	static final String REPORT = "REPORT";
	static final String SEP = SEPARATOR;
	
	static final String MNEMONIC = "TPERSON";
	
	// CUSTOMER
	static final String CUSTOMER_MODULE_ID = MNEMONIC + SEP + CUSTOMER + SEP + MODULE;
	
	static final String CUSTOMER_FORM_ID = MNEMONIC + SEP + CUSTOMER + SEP + FORM;
	static final String CUSTOMER_VIEW_ID = MNEMONIC + SEP + CUSTOMER + SEP + VIEW;

	static final String CLIENT_COMPANY_FORM_ID = MNEMONIC + SEP + CLIENT_COMPANY + SEP + FORM;
	static final String CLIENT_COMPANY_VIEW_ID = MNEMONIC + SEP + CLIENT_COMPANY + SEP + VIEW;

	static final String CUSTOMER_TYPE_FORM_ID = MNEMONIC + SEP + CUSTOMER + SEP + TYPE + SEP + FORM;
	static final String CUSTOMER_TYPE_VIEW_ID = MNEMONIC + SEP + CUSTOMER + SEP + TYPE + SEP  + VIEW;

	static final String CUSTOMER_STATUS_FORM_ID = MNEMONIC + SEP + CUSTOMER + SEP + STATUS + SEP + FORM;
	static final String CUSTOMER_STATUS_VIEW_ID = MNEMONIC + SEP + CUSTOMER + SEP + STATUS + SEP  + VIEW;

	static final String CLIENT_COMPANY_TYPE_FORM_ID = MNEMONIC + SEP + CLIENT_COMPANY + SEP + TYPE + SEP + FORM;
	static final String CLIENT_COMPANY_TYPE_VIEW_ID = MNEMONIC + SEP + CLIENT_COMPANY + SEP + TYPE + SEP  + VIEW;

	static final String CLIENT_COMPANY_STATUS_FORM_ID = MNEMONIC + SEP + CLIENT_COMPANY + SEP + STATUS + SEP + FORM;
	static final String CLIENT_COMPANY_STATUS_VIEW_ID = MNEMONIC + SEP + CLIENT_COMPANY + SEP + STATUS + SEP  + VIEW;
	
	// NATURAL PERSON
	static final String NATURAL_PERSON_MODULE_ID = MNEMONIC + SEP + NATURAL_PERSON + SEP + MODULE;

	static final String NATURAL_PERSON_FORM_ID = MNEMONIC + SEP + NATURAL_PERSON + SEP + FORM;
	static final String NATURAL_PERSON_VIEW_ID = MNEMONIC + SEP + NATURAL_PERSON + SEP + VIEW;
	
	
	static final String MEMBER_FORM_ID = MNEMONIC + SEP + MEMBER + SEP + FORM;
	static final String MEMBER_VIEW_ID = MNEMONIC + SEP + MEMBER + SEP + VIEW;

	static final String VOLUNTARY_FORM_ID = MNEMONIC + SEP + VOLUNTARY + SEP + FORM;
	static final String VOLUNTARY_VIEW_ID = MNEMONIC + SEP + VOLUNTARY + SEP + VIEW;
	
	static final String PHILANTHROPE_FORM_ID = MNEMONIC + SEP + PHILANTHROPE + SEP + FORM;
	static final String PHILANTHROPE_VIEW_ID = MNEMONIC + SEP + PHILANTHROPE + SEP + VIEW;

	static final String NATURAL_PERSON_TYPE_FORM_ID = MNEMONIC + SEP + NATURAL_PERSON + SEP + TYPE + SEP + FORM;
	static final String NATURAL_PERSON_TYPE_VIEW_ID = MNEMONIC + SEP + NATURAL_PERSON + SEP + TYPE + SEP  + VIEW;

	static final String NATURAL_PERSON_STATUS_FORM_ID = MNEMONIC + SEP + NATURAL_PERSON + SEP + STATUS + SEP + FORM;
	static final String NATURAL_PERSON_STATUS_VIEW_ID = MNEMONIC + SEP + NATURAL_PERSON + SEP + STATUS + SEP  + VIEW;

	static final String MEMBER_TYPE_FORM_ID = MNEMONIC + SEP + MEMBER + SEP + TYPE + SEP + FORM;
	static final String MEMBER_TYPE_VIEW_ID = MNEMONIC + SEP + MEMBER + SEP + TYPE + SEP  + VIEW;

	static final String MEMBER_STATUS_FORM_ID = MNEMONIC + SEP + MEMBER + SEP + STATUS + SEP + FORM;
	static final String MEMBER_STATUS_VIEW_ID = MNEMONIC + SEP + MEMBER + SEP + STATUS + SEP  + VIEW;

	static final String VOLUNTARY_TYPE_FORM_ID = MNEMONIC + SEP + VOLUNTARY + SEP + TYPE + SEP + FORM;
	static final String VOLUNTARY_TYPE_VIEW_ID = MNEMONIC + SEP + VOLUNTARY + SEP + TYPE + SEP  + VIEW;

	static final String VOLUNTARY_STATUS_FORM_ID = MNEMONIC + SEP + VOLUNTARY + SEP + STATUS + SEP + FORM;
	static final String VOLUNTARY_STATUS_VIEW_ID = MNEMONIC + SEP + VOLUNTARY + SEP + STATUS + SEP  + VIEW;

	static final String PHILANTHROPE_TYPE_FORM_ID = MNEMONIC + SEP + PHILANTHROPE + SEP + TYPE + SEP + FORM;
	static final String PHILANTHROPE_TYPE_VIEW_ID = MNEMONIC + SEP + PHILANTHROPE + SEP + TYPE + SEP  + VIEW;

	static final String PHILANTHROPE_STATUS_FORM_ID = MNEMONIC + SEP + PHILANTHROPE + SEP + STATUS + SEP + FORM;
	static final String PHILANTHROPE_STATUS_VIEW_ID = MNEMONIC + SEP + PHILANTHROPE + SEP + STATUS + SEP  + VIEW;

	static final String NATURAL_PERSON_REPORT_FORM_ID = MNEMONIC + SEP + NATURAL_PERSON + SEP + REPORT + SEP + FORM;
	static final String NATURAL_PERSON_REPORT_VIEW_ID = MNEMONIC + SEP + NATURAL_PERSON + SEP + REPORT + SEP + VIEW;
	
	// LEGAL PERSON
	static final String LEGAL_PERSON_MODULE_ID = MNEMONIC + SEP + LEGAL_PERSON + SEP + MODULE;

	static final String LEGAL_PERSON_FORM_ID = MNEMONIC + SEP + LEGAL_PERSON + SEP + FORM;
	static final String LEGAL_PERSON_VIEW_ID = MNEMONIC + SEP + LEGAL_PERSON + SEP + VIEW;
	
	static final String EMPLOYEE_FORM_ID = MNEMONIC + SEP + EMPLOYEE + SEP + FORM;
	static final String EMPLOYEE_VIEW_ID = MNEMONIC + SEP + EMPLOYEE + SEP + VIEW;
	
	static final String LEGAL_TYPE_FORM_ID = MNEMONIC + SEP + LEGAL_PERSON + SEP + TYPE + SEP + FORM;
	static final String LEGAL_TYPE_VIEW_ID = MNEMONIC + SEP + LEGAL_PERSON + SEP + TYPE + SEP  + VIEW;

	static final String COST_CENTER_FORM_ID = MNEMONIC + SEP + COST_CENTER + SEP + FORM;
	static final String COST_CENTER_VIEW_ID = MNEMONIC + SEP + COST_CENTER + SEP + VIEW;
	
	static final String LEGAL_STATUS_FORM_ID = MNEMONIC + SEP + LEGAL_PERSON + SEP + STATUS + SEP + FORM;
	static final String LEGAL_STATUS_VIEW_ID = MNEMONIC + SEP + LEGAL_PERSON + SEP + STATUS + SEP  + VIEW;
	
	static final String STAFF_TYPE_FORM_ID = MNEMONIC + SEP + STAFF + SEP + TYPE + SEP + FORM;
	static final String STAFF_TYPE_VIEW_ID = MNEMONIC + SEP + STAFF + SEP + TYPE + SEP  + VIEW;

	static final String EMPLOYEE_STATUS_FORM_ID = MNEMONIC + SEP + EMPLOYEE + SEP + STATUS + SEP + FORM;
	static final String EMPLOYEE_STATUS_VIEW_ID = MNEMONIC + SEP + EMPLOYEE + SEP + STATUS + SEP  + VIEW;
	
	static final String LEGAL_PERSON_REPORT_FORM_ID = MNEMONIC + SEP + LEGAL_PERSON + SEP + REPORT + SEP + FORM;
	static final String LEGAL_PERSON_REPORT_VIEW_ID = MNEMONIC + SEP + LEGAL_PERSON + SEP + REPORT + SEP + VIEW;

	static final String EMPLOYEE_REPORT_FORM_ID = MNEMONIC + SEP + EMPLOYEE + SEP + REPORT + SEP + FORM;
	static final String EMPLOYEE_REPORT_VIEW_ID = MNEMONIC + SEP + EMPLOYEE + SEP + REPORT + SEP + VIEW;

	// PERSON CATEGORY
	static final String PERSON_CATEGORY_MODULE_ID = MNEMONIC + SEP + PERSON_CATEGORY + SEP  + MODULE;
	
	static final String PERSON_CATEGORY_FORM_ID = MNEMONIC + SEP + PERSON_CATEGORY + SEP + FORM;
	static final String PERSON_CATEGORY_VIEW_ID = MNEMONIC + SEP + PERSON_CATEGORY + SEP  + VIEW;


}
