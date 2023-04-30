/**
 * 
 */
package org.tedros.stock.resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.tedros.stock.start.TConstant;
import org.tedros.util.TedrosFolder;

/**
 * @author Davis Gordon
 *
 */
public class AppResource {

	private static final String FOLDER = TConstant.UUI;
	public static final String APP_MODULE_PATH = TedrosFolder.MODULE_FOLDER.getFullPath()+FOLDER+File.separator;
	private static final String INVENTORY_JASPER ="inventory.jasper";
	private static final String PRODUCT_JASPER ="products.jasper";
	
	private static final String[] FILES = {
			INVENTORY_JASPER, "inventory.jrxml", 
			PRODUCT_JASPER, "products.jrxml", 
			"subrep_images.jasper", "subrep_images.jrxml", 
			"subrep_textarea.jasper", "subrep_textarea.jrxml"
			};
	/**
	 * 
	 */
	public AppResource() {
	}

	
	public static InputStream getProductJasperInputStream() throws FileNotFoundException {
		File f = new File(APP_MODULE_PATH+PRODUCT_JASPER);
		if(f.isFile()) {
			return new FileInputStream(f);
		}
		
		return null;
	}
	
	public static InputStream getInventoryJasperInputStream() throws FileNotFoundException {
		File f = new File(APP_MODULE_PATH+INVENTORY_JASPER);
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
