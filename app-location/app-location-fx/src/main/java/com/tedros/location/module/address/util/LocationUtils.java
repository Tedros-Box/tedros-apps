/**
 * 
 */
package com.tedros.location.module.address.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;

import com.tedros.util.TedrosFolder;

/**
 * @author Davis Gordon
 *
 */
public class LocationUtils {

	private static final String FOLDER ="geolocation";
	private static final String MAPQUEST_HTML ="location.html";
	
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
	}

}
