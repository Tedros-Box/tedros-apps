/**
 * 
 */
package com.tedros.services.domain;

/**
 * @author Davis Gordon
 *
 */
public interface DomainApp {

	static final String SEPARATOR = "_";
	static final String VIEW = "VIEW";
	static final String MODULE = "MODULE";
	static final String FORM = "FORM";
	static final String SERVICE = "SERVICE";
	static final String PLAN = "PLAN";
	static final String SERVICE_TYPE = "SERVICE_TYPE";
	static final String DUE = "STAFF";
	static final String EMPLOYEE = "EMPLOYEE";
	static final String REPORT = "REPORT";
	static final String SEP = SEPARATOR;
	
	static final String MNEMONIC = "TPERSON";

	static final String SERVICE_FORM_ID = MNEMONIC + SEP + SERVICE + SEP + FORM;
	static final String SERVICE_VIEW_ID = MNEMONIC + SEP + SERVICE + SEP + VIEW;
	static final String SERVICE_MODULE_ID = MNEMONIC + SEP + SERVICE + SEP + MODULE;

	static final String PLAN_FORM_ID = MNEMONIC + SEP + PLAN + SEP + FORM;
	static final String PLAN_VIEW_ID = MNEMONIC + SEP + PLAN + SEP + VIEW;
	static final String PLAN_MODULE_ID = MNEMONIC + SEP + PLAN + SEP + MODULE;

	static final String SERVICE_TYPE_FORM_ID = MNEMONIC + SEP + SERVICE_TYPE + SEP + FORM;
	static final String SERVICE_TYPE_VIEW_ID = MNEMONIC + SEP + SERVICE_TYPE + SEP + VIEW;
	
	static final String EMPLOYEE_REPORT_FORM_ID = MNEMONIC + SEP + EMPLOYEE + SEP + REPORT + SEP + FORM;
	static final String EMPLOYEE_REPORT_VIEW_ID = MNEMONIC + SEP + EMPLOYEE + SEP + REPORT + SEP + VIEW;

	static final String NATURAL_PERSON_REPORT_FORM_ID = MNEMONIC + SEP + SERVICE + SEP + REPORT + SEP + FORM;
	static final String NATURAL_PERSON_REPORT_VIEW_ID = MNEMONIC + SEP + SERVICE + SEP + REPORT + SEP + VIEW;
	
	static final String LEGAL_PERSON_REPORT_FORM_ID = MNEMONIC + SEP + PLAN + SEP + REPORT + SEP + FORM;
	static final String LEGAL_PERSON_REPORT_VIEW_ID = MNEMONIC + SEP + PLAN + SEP + REPORT + SEP + VIEW;
	
	static final String REPORT_MODULE_ID = MNEMONIC + SEP + REPORT + SEP  + MODULE;

	static final String LEGAL_TYPE_FORM_ID = MNEMONIC + SEP + PLAN + SEP + SERVICE_TYPE + SEP + FORM;
	static final String LEGAL_TYPE_VIEW_ID = MNEMONIC + SEP + PLAN + SEP + SERVICE_TYPE + SEP  + VIEW;

	static final String STAFF_TYPE_FORM_ID = MNEMONIC + SEP + DUE + SEP + SERVICE_TYPE + SEP + FORM;
	static final String STAFF_TYPE_VIEW_ID = MNEMONIC + SEP + DUE + SEP + SERVICE_TYPE + SEP  + VIEW;



}
