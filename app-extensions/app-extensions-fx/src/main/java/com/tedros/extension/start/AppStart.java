/**
 * 
 */
package com.tedros.extension.start;

import com.tedros.core.ITApplication;
import com.tedros.core.annotation.TApplication;
import com.tedros.core.annotation.TResourceBundle;

/**
 * @author Davis Gordon
 *
 */
@TApplication(name="#{app.extension.name}", universalUniqueIdentifier=TConstant.UUI,
 packageName = "com.tedros.extension")
@TResourceBundle(resourceName={"AppExtensionsLang"})
public class AppStart implements ITApplication {

	/* (non-Javadoc)
	 * @see com.tedros.core.ITApplication#start()
	 */
	@Override
	public void start() {
		// TODO Auto-generated method stub

	}

}
