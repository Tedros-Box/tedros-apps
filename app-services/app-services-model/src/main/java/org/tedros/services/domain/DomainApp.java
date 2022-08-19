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
	static final String SERVICE_LOCATION = "SERVICE_LOCATION";
	static final String CONTRACT = "CONTRACT";
	static final String CONTRACTED = "CONTRACTED";
	static final String CONTRACTOR = "CONTRACTOR";
	static final String REPORT = "REPORT";
	static final String SEP = SEPARATOR;
	
	static final String MNEMONIC = "TASERV";

	static final String SERVICE_FORM_ID = MNEMONIC + SEP + SERVICE + SEP + FORM;
	static final String SERVICE_VIEW_ID = MNEMONIC + SEP + SERVICE + SEP + VIEW;
	static final String SERVICE_MODULE_ID = MNEMONIC + SEP + SERVICE + SEP + MODULE;

	static final String SERVICE_TYPE_FORM_ID = MNEMONIC + SEP + SERVICE_TYPE + SEP + FORM;
	static final String SERVICE_TYPE_VIEW_ID = MNEMONIC + SEP + SERVICE_TYPE + SEP + VIEW;
	
	static final String SERVICE_LOCATION_FORM_ID = MNEMONIC + SEP + SERVICE_LOCATION + SEP + FORM;
	static final String SERVICE_LOCATION_VIEW_ID = MNEMONIC + SEP + SERVICE_LOCATION + SEP + VIEW;
	static final String SERVICE_LOCATION_MODULE_ID = MNEMONIC + SEP + SERVICE_LOCATION + SEP + MODULE;

	static final String PLAN_FORM_ID = MNEMONIC + SEP + PLAN + SEP + FORM;
	static final String PLAN_VIEW_ID = MNEMONIC + SEP + PLAN + SEP + VIEW;
	static final String PLAN_MODULE_ID = MNEMONIC + SEP + PLAN + SEP + MODULE;

	static final String CONTRACTOR_FORM_ID = MNEMONIC + SEP + CONTRACTOR + SEP + FORM;
	static final String CONTRACTOR_VIEW_ID = MNEMONIC + SEP + CONTRACTOR + SEP + VIEW;
	static final String CONTRACTOR_MODULE_ID = MNEMONIC + SEP + CONTRACTOR + SEP + MODULE;
	
	static final String CONTRACTED_FORM_ID = MNEMONIC + SEP + CONTRACTED + SEP + FORM;
	static final String CONTRACTED_VIEW_ID = MNEMONIC + SEP + CONTRACTED + SEP + VIEW;
	static final String CONTRACTED_MODULE_ID = MNEMONIC + SEP + CONTRACTED + SEP + MODULE;

	static final String CONTRACT_FORM_ID = MNEMONIC + SEP + CONTRACT + SEP + FORM;
	static final String CONTRACT_VIEW_ID = MNEMONIC + SEP + CONTRACT + SEP + VIEW;
	static final String CONTRACT_MODULE_ID = MNEMONIC + SEP + CONTRACT + SEP + MODULE;
	
	
/*
	static final String EMPLOYEE_REPORT_FORM_ID = MNEMONIC + SEP + EMPLOYEE + SEP + REPORT + SEP + FORM;
	static final String EMPLOYEE_REPORT_VIEW_ID = MNEMONIC + SEP + EMPLOYEE + SEP + REPORT + SEP + VIEW;

	static final String NATURAL_PERSON_REPORT_FORM_ID = MNEMONIC + SEP + SERVICE + SEP + REPORT + SEP + FORM;
	static final String NATURAL_PERSON_REPORT_VIEW_ID = MNEMONIC + SEP + SERVICE + SEP + REPORT + SEP + VIEW;
	
	static final String LEGAL_PERSON_REPORT_FORM_ID = MNEMONIC + SEP + PLAN + SEP + REPORT + SEP + FORM;
	static final String LEGAL_PERSON_REPORT_VIEW_ID = MNEMONIC + SEP + PLAN + SEP + REPORT + SEP + VIEW;
	
	static final String REPORT_MODULE_ID = MNEMONIC + SEP + REPORT + SEP  + MODULE;
*/
	


}
