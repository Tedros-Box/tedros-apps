/**
 * 
 */
package org.tedros.redminetools;

/**
 * The language  resource key domain
 * 
 * Run the org.tedros.fx.util.LanguageKeysBuilder to create the constants from your language resource.
 * Type the resource name without the locale, for example to read the App_en.properties just  
 * type App
 * 
 * @author Davis Dun
 *
 */
public interface RDMN_Key {

	static final String APP_MY_APP = "#{app.rdmn}";
	static final String MENU_MY_APP = "#{menu.rdmn}";
	static final String MODULE_DESC_MY_APP = "#{module.desc.rdmn}";
	static final String MODULE_MY_APP = "#{module.rdmn}";
	static final String MY_APP_MY_VIEW = "#{rdmn.my.view}";
}
