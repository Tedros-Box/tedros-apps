/**
 * 
 */
package org.tedros.location.model;

import org.tedros.common.model.TFileEntity;
import org.tedros.server.entity.ITFileEntity;
import org.tedros.server.model.ITImportModel;

/**
 * @author Davis Gordon
 *
 */
public class CountryImport implements ITImportModel {

	private static final long serialVersionUID = -5030633206012895009L;

	private String rules;
	
	private ITFileEntity file;
	
	public CountryImport() {
		file = new TFileEntity();
	}
	

	/**
	 * @return the rules
	 */
	public String getRules() {
		return rules;
	}

	/**
	 * @param rules the rules to set
	 */
	public void setRules(String rules) {
		this.rules = rules;
	}


	/**
	 * @return the file
	 */
	public ITFileEntity getFile() {
		return file;
	}


	/**
	 * @param file the file to set
	 */
	public void setFile(ITFileEntity file) {
		this.file = file;
	}


}
