/**
 * 
 */
package com.tedros.person.domain;

/**
 * @author Davis Gordon
 *
 */
public interface DomainApp {

	static final String SEPARATOR = "_";
	static final String VIEW = "VIEW";
	static final String MODULE = "MODULE";
	static final String FORM = "FORM";
	static final String NATURAL_PERSON = "NATURAL_PERSON";
	static final String LEGAL_PERSON = "LEGAL_PERSON";
	static final String TYPE = "TYPE";
	static final String STAFF = "STAFF";
	static final String EMPLOYEE = "EMPLOYEE";
	static final String SEP = SEPARATOR;
	
	static final String MNEMONIC = "TPERSON";

	static final String NATURAL_PERSON_FORM_ID = MNEMONIC + SEP + NATURAL_PERSON + SEP + FORM;
	static final String NATURAL_PERSON_VIEW_ID = MNEMONIC + SEP + NATURAL_PERSON + SEP + VIEW;
	static final String NATURAL_PERSON_MODULE_ID = MNEMONIC + SEP + NATURAL_PERSON + SEP + MODULE;

	static final String LEGAL_PERSON_FORM_ID = MNEMONIC + SEP + LEGAL_PERSON + SEP + FORM;
	static final String LEGAL_PERSON_VIEW_ID = MNEMONIC + SEP + LEGAL_PERSON + SEP + VIEW;
	static final String LEGAL_PERSON_MODULE_ID = MNEMONIC + SEP + LEGAL_PERSON + SEP + MODULE;

	static final String EMPLOYEE_FORM_ID = MNEMONIC + SEP + EMPLOYEE + SEP + FORM;
	static final String EMPLOYEE_VIEW_ID = MNEMONIC + SEP + EMPLOYEE + SEP + VIEW;
	static final String EMPLOYEE_MODULE_ID = MNEMONIC + SEP + EMPLOYEE + SEP + MODULE;

	static final String LEGAL_TYPE_FORM_ID = MNEMONIC + SEP + LEGAL_PERSON + SEP + TYPE + SEP + FORM;
	static final String LEGAL_TYPE_VIEW_ID = MNEMONIC + SEP + LEGAL_PERSON + SEP + TYPE + SEP  + VIEW;

	static final String STAFF_TYPE_FORM_ID = MNEMONIC + SEP + STAFF + SEP + TYPE + SEP + FORM;
	static final String STAFF_TYPE_VIEW_ID = MNEMONIC + SEP + STAFF + SEP + TYPE + SEP  + VIEW;



}
