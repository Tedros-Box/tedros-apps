/**
 * 
 */
package org.tedros.services.domain;

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


	static final String CONTRACT_FORM_ID = MNEMONIC + SEP + CONTRACT + SEP + FORM;
	static final String CONTRACT_VIEW_ID = MNEMONIC + SEP + CONTRACT + SEP + VIEW;
	static final String CONTRACT_MODULE_ID = MNEMONIC + SEP + CONTRACT + SEP + MODULE;
	
	

}
