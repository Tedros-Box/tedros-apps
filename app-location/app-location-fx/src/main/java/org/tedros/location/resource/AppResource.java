/**
 * 
 */
package org.tedros.location.resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.tedros.location.start.TConstant;

import org.tedros.util.TAppResource;
import org.tedros.util.TZipUtil;

/**
 * @author Davis Gordon
 *
 */
public class AppResource extends TAppResource{

	private static final String MAPQUEST_HTML ="location.html";
	private static final String SETTINGS ="map-settings.properties";
	private static final String PLACE_JASPER ="place.jasper";
	private static final String PLACE_JRXML ="place.jrxml";
	private static final String SUBREP_JASPER ="subrep_textarea.jasper";
	private static final String SUBREP_JRXML ="subrep_textarea.jrxml";
	private static final String ZIP ="files_to_import.zip";
	/**
	 * 
	 */
	public AppResource() {
		super(TConstant.UUI);
		super.addResource(MAPQUEST_HTML, SETTINGS,
				PLACE_JASPER, PLACE_JRXML, 
				SUBREP_JASPER, SUBREP_JRXML, ZIP);
	}
	
	@Override
	public void copyToFolder() {
		super.copyToFolder();
		String folder = super.getFolderPath();
		File f = new File(folder+"files_to_import");
		if(!f.exists()) {
			try(InputStream zipFile = AppResource.class.getResourceAsStream(ZIP)){
				TZipUtil.unZip(zipFile, folder);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static InputStream getPlaceJasperInputStream() throws FileNotFoundException {
		File f = new File(new AppResource().getFolderPath()+PLACE_JASPER);
		if(f.isFile()) {
			return new FileInputStream(f);
		}
		
		return null;
	}
	
	public static void saveSettings(Properties p) {
		File f = new File(new AppResource().getFolderPath()+SETTINGS);
		try(OutputStream out = new FileOutputStream(f)){
			p.store(out, "Map settings");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public static Properties getSettings() {
		File f = new File(new AppResource().getFolderPath()+SETTINGS);
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
