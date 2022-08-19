/**
 * 
 */
package org.tedros.db;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;

/**
 * 
 * Prepare the data folder structure
 * 
 * @author Davis Gordon
 *
 */
public class TedrosData {

	private static final String DATA_FOLDER = System.getProperty("user.home")
			+ File.separator + ".tedrosData";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Checking data folder: "+ DATA_FOLDER);
		File f = new File(DATA_FOLDER);
		if(!f.exists()) {
			f.mkdir();
			f = new File(DATA_FOLDER + File.separator + "h2");
			if(!f.exists()) 
				f.mkdir();
			System.out.println("Data folder created!");
		}else
			System.out.println("Data folder already exist!");

		String sql = "init.sql";
		System.out.println("Checking data file: "+sql);
		f = new File(DATA_FOLDER + File.separator + sql); 
		if(!f.exists()){
			try(InputStream is = TedrosData.class.getResourceAsStream(sql)){
				FileUtils.copyInputStreamToFile(is, f);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			System.out.println("Data file created!");
		}else
			System.out.println("Data file already exist!");

	}

}
