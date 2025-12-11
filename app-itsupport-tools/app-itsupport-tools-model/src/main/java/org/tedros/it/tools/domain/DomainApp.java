/**
 * 
 */
package org.tedros.it.tools.domain;

/**
 * @author Davis Gordon
 *
 */
public interface DomainApp {

	static final String SEPARATOR = "_";
	static final String VIEW = "VIEW";
	static final String MODULE = "MODULE";
	static final String FORM = "FORM";
	
	static final String EVIDENCE_MANAGER = "EVIDENCE_MANAGER";
	static final String EVIDENCE_CAPTURE = "EVIDENCE_CAPTURE";
	static final String EVIDENCE_REPORT = "EVIDENCE_REPORT";
	static final String SEP = SEPARATOR;
	
	static final String MNEMONIC = "ISPT";

	static final String EVIDENCE_MANAGER_MODULE_ID = MNEMONIC + SEP + EVIDENCE_MANAGER + SEP + MODULE;
	static final String EVIDENCE_MANAGER_FORM_ID = MNEMONIC + SEP + EVIDENCE_MANAGER + SEP + FORM;
	static final String EVIDENCE_MANAGER_VIEW_ID = MNEMONIC + SEP + EVIDENCE_MANAGER + SEP + VIEW;
		
	static final String EVIDENCE_REPORT_FORM_ID = MNEMONIC + SEP + EVIDENCE_REPORT + SEP + FORM;
	static final String EVIDENCE_REPORT_VIEW_ID = MNEMONIC + SEP + EVIDENCE_REPORT + SEP + VIEW;
	
	static final String EVIDENCE_CAPTURE_FORM_ID = MNEMONIC + SEP + EVIDENCE_CAPTURE + SEP + FORM;
	static final String EVIDENCE_CAPTURE_VIEW_ID = MNEMONIC + SEP + EVIDENCE_CAPTURE + SEP + VIEW;

}
