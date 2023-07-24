/**
 * 
 */
package org.tedros.services;

/**
 * @author Davis Gordon
 *
 */
public interface ServKey {

	static final String APP_SERVICE = "#{app.service}";
	static final String MENU_SERVICE = "#{menu.service}";
	static final String MODULE_DESC_PLAN = "#{module.desc.plan}";
	static final String MODULE_DESC_SERVICE = "#{module.desc.service}";
	static final String MODULE_PLANS = "#{module.plans}";
	static final String MODULE_SERVICES = "#{module.services}";
	static final String VIEW_CONTRACT = "#{view.contract}";
	static final String VIEW_CONTRACT_DESC = "#{view.contract.desc}";
	static final String VIEW_PLAN = "#{view.plan}";
	static final String VIEW_PLAN_DESC = "#{view.plan.desc}";
	static final String VIEW_SERVICE = "#{view.service}";
	static final String VIEW_SERVICE_DESC = "#{view.service.desc}";
	static final String VIEW_SERVICE_LOCATION = "#{view.service.location}";
	static final String VIEW_SERVICE_LOCATION_DESC = "#{view.service.location.desc}";
	static final String VIEW_SERVICE_REPORTS = "#{view.service.reports}";
	static final String VIEW_SERVICE_TYPE = "#{view.service.type}";
	static final String VIEW_SERVICE_TYPE_DESC = "#{view.service.type.desc}";
}
