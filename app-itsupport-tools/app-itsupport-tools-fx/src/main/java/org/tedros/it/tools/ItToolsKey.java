/**
 * 
 */
package org.tedros.it.tools;

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
public interface ItToolsKey {

	static final String APP_ITSUPPORT = "#{app.itsupport}";
	static final String MENU_ITSUPPORT = "#{menu.itsupport}";
	static final String MODULE_ITSUPPORT_EVIDENCE = "#{module.itsupport.evidence}";
	static final String MODULE_ITSUPPORT_EVIDENCE_DESC = "#{module.itsupport.evidence.desc}";
	static final String VIEW_ITSUPPORT_CAPTURE_EVIDENCE = "#{view.itsupport.capture.evidence}";
	static final String VIEW_ITSUPPORT_CAPTURE_EVIDENCE_DESC = "#{view.itsupport.capture.evidence.desc}";
}
