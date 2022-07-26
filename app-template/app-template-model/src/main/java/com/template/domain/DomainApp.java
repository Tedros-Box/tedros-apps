/**
 * 
 */
package com.template.domain;

/**
 * @author Davis Gordon
 *
 */
public interface DomainApp {

	static final String SEPARATOR = "_";
	static final String VIEW = "VIEW";
	static final String MODULE = "MODULE";
	static final String FORM = "FORM";
	
	static final String MY_ENTITY_ = "MY_ENTITY_";
	static final String SEP = SEPARATOR;
	
	static final String MNEMONIC = "TMP_";

	static final String MY_ENTITY__MODULE_ID = MNEMONIC + SEP + MY_ENTITY_ + SEP + MODULE;
	static final String MY_ENTITY__FORM_ID = MNEMONIC + SEP + MY_ENTITY_ + SEP + FORM;
	static final String MY_ENTITY__VIEW_ID = MNEMONIC + SEP + MY_ENTITY_ + SEP + VIEW;
	

}
