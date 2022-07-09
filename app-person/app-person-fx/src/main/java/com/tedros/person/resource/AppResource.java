/**
 * 
 */
package com.tedros.person.resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;

import com.tedros.util.TedrosFolder;

/**
 * @author Davis Gordon
 *
 */
public class AppResource {

	private static final String FOLDER ="person";
	public static final String APP_MODULE_PATH = TedrosFolder.MODULE_FOLDER.getFullPath()+FOLDER+File.separator;
	private static final String EMPLOYEE_JASPER ="employee.jasper";
	private static final String EMPLOYEE_JRXML ="employee.jrxml";
	private static final String SUBREP1_JASPER ="subrep_textarea.jasper";
	private static final String SUBREP1_JRXML ="subrep_textarea.jrxml";
	private static final String SUBREP2_JASPER ="subrep_docs.jasper";
	private static final String SUBREP2_JRXML ="subrep_docs.jrxml";
	private static final String SUBREP3_JASPER ="subrep_attr.jasper";
	private static final String SUBREP3_JRXML ="subrep_attr.jrxml";
	/**
	 * 
	 */
	public AppResource() {
	}
	
	public static InputStream getEmployeeJasperInputStream() throws FileNotFoundException {
		File f = new File(APP_MODULE_PATH+EMPLOYEE_JASPER);
		if(f.isFile()) {
			return new FileInputStream(f);
		}
		
		return null;
	}
	
	public static void createResource() {
		String[] arr = new String[0];
		arr = ArrayUtils.addAll(arr,EMPLOYEE_JASPER, EMPLOYEE_JRXML, 
				SUBREP1_JASPER, SUBREP1_JRXML,
				SUBREP2_JASPER, SUBREP2_JRXML,
				SUBREP3_JASPER, SUBREP3_JRXML
				);
		
		for(String ref : arr) {
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
