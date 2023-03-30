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

	static final String APP_STOCK = "#{app.stock}";
	static final String CLOSING_DATE = "#{label.closing.date}";
	static final String MEASURE = "#{label.measure}";
	static final String MINIMUN_AMOUNT = "#{label.minimun.amount}";
	static final String OPENING_DATE = "#{label.opening.date}";
	static final String PRODUCT = "#{label.product}";
	static final String RESPONSABLE = "#{label.responsable}";
	static final String SIZE = "#{label.size}";
	static final String TRADEMARK = "#{label.trademark}";
	static final String UNIT_MEASURE = "#{label.unit.measure}";
	static final String WEIGHT = "#{label.weight}";
	static final String MENU_STOCK = "#{menu.stock}";
	static final String MODULE_COST_CENTER = "#{module.cost.center}";
	static final String MODULE_DESC_COST_CENTER = "#{module.desc.cost.center}";
	static final String MODULE_DESC_PRODUCTS = "#{module.desc.products}";
	static final String MODULE_PRODUCTS = "#{module.products}";
	static final String TOOLTIP_SIZE = "#{tooltip.size}";
	static final String VIEW_COST_CENTER = "#{view.cost.center}";
	static final String VIEW_PRODUCT = "#{view.product}";
	static final String VIEW_STOCK_CONFIG = "#{view.stock.config}";
}
