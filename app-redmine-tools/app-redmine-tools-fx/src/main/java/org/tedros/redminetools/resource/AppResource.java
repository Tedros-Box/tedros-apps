/**
 * 
 */
package org.tedros.redminetools.resource;

import org.tedros.redminetools.start.TConstant;
import org.tedros.util.TAppResource;

/**
 * @author Davis Gordon
 *
 */
public class AppResource extends TAppResource{

	private static final String HTML ="teros_ia_response.html";
	/**
	 * 
	 */
	public AppResource() {
		super(TConstant.UUI);
		super.addResource(HTML);
	}
	
	

}
