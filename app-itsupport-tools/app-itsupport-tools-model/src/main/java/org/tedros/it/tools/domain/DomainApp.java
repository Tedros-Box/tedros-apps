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
	
	static final String REDMINE_CONFIG = "REDMINE_CONFIG";
	static final String REDMINE_PROJECT = "REDMINE_PROJECT";
	static final String SEP = SEPARATOR;
	
	static final String MNEMONIC = "RDMN_";

	static final String REDMINE_CONFIG_MODULE_ID = MNEMONIC + SEP + REDMINE_CONFIG + SEP + MODULE;
	static final String REDMINE_CONFIG_FORM_ID = MNEMONIC + SEP + REDMINE_CONFIG + SEP + FORM;
	static final String REDMINE_CONFIG_VIEW_ID = MNEMONIC + SEP + REDMINE_CONFIG + SEP + VIEW;
	
	static final String REDMINE_PROJECT_MODULE_ID = MNEMONIC + SEP + REDMINE_PROJECT + SEP + MODULE;
	static final String REDMINE_PROJECT_FORM_ID = MNEMONIC + SEP + REDMINE_PROJECT + SEP + FORM;
	static final String REDMINE_PROJECT_VIEW_ID = MNEMONIC + SEP + REDMINE_PROJECT + SEP + VIEW;

}
