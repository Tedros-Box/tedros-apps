/**
 * 
 */
package com.tedros.location.module.address.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.commons.io.FileUtils;

import com.tedros.util.TedrosFolder;

/**
 * @author Davis Gordon
 *
 */
public class LocationUtils {

	private static final String FOLDER ="geolocation";
	private static final String MAPQUEST_HTML ="location.html";
	private static final String SETTINGS ="map-settings.properties";
	/**
	 * 
	 */
	public LocationUtils() {
	}
	
	public static void createResource() {
		File f = new File(TedrosFolder.MODULE_FOLDER.getFullPath()+FOLDER+File.separator+MAPQUEST_HTML);
		if(!f.isFile()) {
			try(InputStream is = LocationUtils.class.getResourceAsStream(MAPQUEST_HTML)){
				FileUtils.copyInputStreamToFile(is, f);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		f = new File(TedrosFolder.MODULE_FOLDER.getFullPath()+FOLDER+File.separator+SETTINGS);
		if(!f.isFile()) {
			try(InputStream is = LocationUtils.class.getResourceAsStream(SETTINGS)){
				FileUtils.copyInputStreamToFile(is, f);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public static void saveSettings(Properties p) {
		File f = new File(TedrosFolder.MODULE_FOLDER.getFullPath()+FOLDER+File.separator+SETTINGS);
		try(OutputStream out = new FileOutputStream(f)){
			p.store(out, "Map settings");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public static Properties getSettings() {
		File f = new File(TedrosFolder.MODULE_FOLDER.getFullPath()+FOLDER+File.separator+SETTINGS);
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
