/**
 * 
 */
package com.tedros.location.resource;

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

	private static final String FOLDER ="geolocation";
	public static final String APP_MODULE_PATH = TedrosFolder.MODULE_FOLDER.getFullPath()+FOLDER+File.separator;
	private static final String MAPQUEST_HTML ="location.html";
	private static final String SETTINGS ="map-settings.properties";
	private static final String PLACE_JASPER ="place.jasper";
	private static final String PLACE_JRXML ="place.jrxml";
	private static final String SUBREP_JASPER ="subrep_textarea.jasper";
	private static final String SUBREP_JRXML ="subrep_textarea.jrxml";
	/**
	 * 
	 */
	public AppResource() {
	}
	
	public static InputStream getPlaceJasperInputStream() throws FileNotFoundException {
		File f = new File(APP_MODULE_PATH+PLACE_JASPER);
		if(f.isFile()) {
			return new FileInputStream(f);
		}
		
		return null;
	}
	
	public static void createResource() {
		String[] arr = new String[0];
		arr = ArrayUtils.addAll(arr, MAPQUEST_HTML, SETTINGS,
				PLACE_JASPER, PLACE_JRXML, 
				SUBREP_JASPER, SUBREP_JRXML);
		
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
	
	public static void saveSettings(Properties p) {
		File f = new File(APP_MODULE_PATH+SETTINGS);
		try(OutputStream out = new FileOutputStream(f)){
			p.store(out, "Map settings");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public static Properties getSettings() {
		File f = new File(APP_MODULE_PATH+SETTINGS);
		if(f.isFile()) {
			Properties p = new Properties();
			try(InputStream is = new FileInputStream(f)){
				p.load(is);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
			return p;
		}
		return null;
	}

}
