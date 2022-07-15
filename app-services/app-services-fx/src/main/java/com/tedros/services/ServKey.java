/**
 * 
 */
package com.tedros.services;

/**
 * @author Davis Gordon
 *
 */
public interface ServKey {

	static final String APP_SERVICE = "#{app.service}";
	static final String MENU_SERVICE = "#{menu.service}";
	static final String MODULE_CONTRACTUAL_PERSONS = "#{module.contractual.persons}";
	static final String MODULE_DESC_CONTRACTUAL_PERSONS = "#{module.desc.contractual.persons}";
	static final String MODULE_DESC_PLAN = "#{module.desc.plan}";
	static final String MODULE_DESC_SERVICE = "#{module.desc.service}";
	static final String MODULE_DESC_SERVICE_REPORTS = "#{module.desc.service.reports}";
	static final String MODULE_PLANS = "#{module.plans}";
	static final String MODULE_SERVICE_REPORTS = "#{module.service.reports}";
	static final String MODULE_SERVICES = "#{module.services}";
	static final String VIEW_CONTRACTED = "#{view.contracted}";
	static final String VIEW_CONTRACTOR = "#{view.contractor}";
	static final String VIEW_PLAN = "#{view.plan}";
	static final String VIEW_SERVICE = "#{view.service}";
	static final String VIEW_SERVICE_LOCATION = "#{view.service.location}";
	static final String VIEW_SERVICE_REPORTS = "#{view.service.reports}";
	static final String VIEW_SERVICE_TYPE = "#{view.service.type}";
}
