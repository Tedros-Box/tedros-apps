/**
 * 
 */
package org.tedros.docs.domain;

/**
 * @author Davis Gordon
 *
 */
public interface DomainApp {

	static final String SEPARATOR = "_";
	static final String VIEW = "VIEW";
	static final String MODULE = "MODULE";
	static final String FORM = "FORM";
	static final String DOCUMENT = "DOCUMENT";
	static final String TYPE = "TYPE";
	static final String EVENT = "EVENT";
	static final String STATE = "STATE";
	static final String DOCUMENT_REPORT = "DOCUMENT_REPORT";
	static final String SEP = SEPARATOR;
	
	static final String MNEMONIC = "TDOC";


	static final String DOCUMENT_FORM_ID = MNEMONIC + SEP + DOCUMENT + SEP + FORM;
	static final String DOCUMENT_VIEW_ID = MNEMONIC + SEP + DOCUMENT + SEP + VIEW;
	static final String DOCUMENT_MODULE_ID = MNEMONIC + SEP + DOCUMENT + SEP + MODULE;

	static final String DOCUMENT_TYPE_FORM_ID = MNEMONIC + SEP + DOCUMENT + SEP + TYPE + SEP + FORM;
	static final String DOCUMENT_TYPE_VIEW_ID = MNEMONIC + SEP + DOCUMENT + SEP + TYPE + SEP  + VIEW;

	static final String DOCUMENT_STATE_FORM_ID = MNEMONIC + SEP + DOCUMENT + SEP + STATE + SEP + FORM;
	static final String DOCUMENT_STATE_VIEW_ID = MNEMONIC + SEP + DOCUMENT + SEP + STATE + SEP  + VIEW;
	
	static final String DOCUMENT_REPORT_FORM_ID = MNEMONIC + SEP + DOCUMENT_REPORT + SEP + FORM;
	static final String DOCUMENT_REPORT_VIEW_ID = MNEMONIC + SEP + DOCUMENT_REPORT + SEP + VIEW;



}
