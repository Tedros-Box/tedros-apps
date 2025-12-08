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
	static final String ADD_NEW_APP_TITLE = "#{label.add.new.app.title}";
	static final String ATTRIBUTES_CAPTURED = "#{label.attributes.captured}";
	static final String OUTPUT_FOLDER = "#{label.output.folder}";
	static final String SAVED_TO = "#{label.saved.to}";
	static final String START_MONITORING = "#{label.start.monitoring}";
	static final String STATUS_RUNNING = "#{label.status.running}";
	static final String STATUS_STOPPED = "#{label.status.stopped}";
	static final String STOP_MONITORING = "#{label.stop.monitoring}";
	static final String TARGET_APPLICATIONS = "#{label.target.applications}";
	static final String UNKNOWN_APP = "#{label.unknown.app}";
	static final String MENU_ITSUPPORT = "#{menu.itsupport}";
	static final String MODULE_ITSUPPORT_EVIDENCE = "#{module.itsupport.evidence}";
	static final String MODULE_ITSUPPORT_EVIDENCE_DESC = "#{module.itsupport.evidence.desc}";
	static final String VIEW_ITSUPPORT_CAPTURE_EVIDENCE = "#{view.itsupport.capture.evidence}";
	static final String VIEW_ITSUPPORT_CAPTURE_EVIDENCE_DESC = "#{view.itsupport.capture.evidence.desc}";
}
