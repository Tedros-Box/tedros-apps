/**
 * 
 */
package org.tedros.ifood.domain;

/**
 * @author Davis Gordon
 *
 */
public interface DomainApp {

	static final String SEPARATOR = "_";
	static final String VIEW = "VIEW";
	static final String MODULE = "MODULE";
	static final String FORM = "FORM";
	
	static final String IFOOD_CONFIG_ = "IFOOD_CONFIG_";
	static final String SEP = SEPARATOR;
	
	static final String MNEMONIC = "IFOOD";

	static final String IFOOD_CONFIG__MODULE_ID = MNEMONIC + SEP + IFOOD_CONFIG_ + SEP + MODULE;
	static final String IFOOD_CONFIG__FORM_ID = MNEMONIC + SEP + IFOOD_CONFIG_ + SEP + FORM;
	static final String IFOOD_CONFIG__VIEW_ID = MNEMONIC + SEP + IFOOD_CONFIG_ + SEP + VIEW;
	

}
