/**
 * 
 */
package org.tedros.extension.start;

import org.tedros.core.ITApplication;
import org.tedros.core.annotation.TApplication;
import org.tedros.core.annotation.TResourceBundle;

/**
 * @author Davis Gordon
 *
 */
@TApplication(name="#{app.extension.name}", universalUniqueIdentifier=TConstant.UUI,
 packageName = "org.tedros.extension")
@TResourceBundle(resourceName={"TExt"})
public class AppStart implements ITApplication {

	/* (non-Javadoc)
	 * @see org.tedros.core.ITApplication#start()
	 */
	@Override
	public void start() {

	}

}
