/**
 * 
 */
package org.tedros.person.resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.tedros.person.start.TConstant;
import org.tedros.util.TedrosFolder;

/**
 * @author Davis Gordon
 *
 */
public class AppResource {

	private static final String FOLDER = TConstant.UUI;
	public static final String APP_MODULE_PATH = TedrosFolder.MODULE_FOLDER.getFullPath()+FOLDER+File.separator;
	private static final String EMPLOYEE_JASPER ="employee.jasper";
	private static final String LEGALPERSON_JASPER ="legal_person.jasper";
	private static final String NATURALPERSON_JASPER ="natural_person.jasper";
	
	private static final String[] FILES = {
			EMPLOYEE_JASPER, "employee.jrxml",
			LEGALPERSON_JASPER, "legal_person.jrxml", 
			NATURALPERSON_JASPER, "natural_person.jrxml", 
			"subrep_textarea.jasper", "subrep_textarea.jrxml", 
			"subrep_docs.jasper", "subrep_docs.jrxml",
			"subrep_attr.jasper","subrep_attr.jrxml",
			"subrep_employee.jasper","subrep_employee.jrxml"
			};
	/**
	 * 
	 */
	public AppResource() {
	}
	
	public static InputStream getNaturalPersonJasperInputStream() throws FileNotFoundException {
		File f = new File(APP_MODULE_PATH+NATURALPERSON_JASPER);
		if(f.isFile()) {
			return new FileInputStream(f);
		}
		
		return null;
	}
	
	public static InputStream getLegalPersonJasperInputStream() throws FileNotFoundException {
		File f = new File(APP_MODULE_PATH+LEGALPERSON_JASPER);
		if(f.isFile()) {
			return new FileInputStream(f);
		}
		
		return null;
	}
	
	public static InputStream getEmployeeJasperInputStream() throws FileNotFoundException {
		File f = new File(APP_MODULE_PATH+EMPLOYEE_JASPER);
		if(f.isFile()) {
			return new FileInputStream(f);
		}
		
		return null;
	}
	
	public static void createResource() {
		for(String ref : FILES) {
			File f = new File(APP_MODULE_PATH+ref);
			if(!f.isFile()) {
				try(InputStream is = AppResource.class.getResourceAsStream(ref)){
					FileUtils.copyInputStreamToFile(is, f);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	

}
