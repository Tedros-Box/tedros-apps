/**
 * 
 */
package org.tedros.stock;

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
public interface STCKKey {

	static final String APP_MY_APP = "#{app.app_stock}";
	static final String MENU_MY_APP = "#{menu.app_stock}";
	static final String MODULE_DESC_MY_APP = "#{module.desc.app_stock}";
	static final String MODULE_MY_APP = "#{module.app_stock}";
	static final String MY_APP_MY_VIEW = "#{app_stock.my.view}";
}
