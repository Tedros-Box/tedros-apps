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
	static final String ACTIVITY_PER_ACTIVE_WINDOW = "#{label.activity.per.active.window}";
	static final String ACTIVITY_PER_HOUR = "#{label.activity.per.hour}";
	static final String ADD_NEW_APP_TITLE = "#{label.add.new.app.title}";
	static final String ADD_TO_ANALYSIS = "#{label.add.to.analysis}";
	static final String APPLY_CHANGES = "#{label.apply.changes}";
	static final String ASSIGNED_TO = "#{label.assigned.to}";
	static final String ATTRIBUTES_CAPTURED = "#{label.attributes.captured}";
	static final String CATALOG_SERVICE_GROUP = "#{label.catalog.service.group}";
	static final String CATALOG_SERVICE_NAME = "#{label.catalog.service.name}";
	static final String CATALOG_SERVICE_NUMBER = "#{label.catalog.service.number}";
	static final String CLEAR_ALL = "#{label.clear.all}";
	static final String COUNT = "#{label.count}";
	static final String DESCRIBE_IA_INSTRUCTIONS = "#{label.describe.ia.instructions}";
	static final String DUE_DATE = "#{label.due.date}";
	static final String EMPLOYEE_ACTIVITY = "#{label.employee.activity}";
	static final String EMPLOYEE_INACTIVITY = "#{label.employee.inactivity}";
	static final String EVIDENCES = "#{label.evidences}";
	static final String EXECUTION_DATE = "#{label.execution.date}";
	static final String EXECUTION_ORDER = "#{label.execution.order}";
	static final String GITLAB_PROJECT = "#{label.gitlab.project}";
	static final String GMUD_ADD_REVIEWER = "#{label.gmud.add.reviewer}";
	static final String GMUD_DETAILS = "#{label.gmud.details}";
	static final String GMUD_EXECUTION_PLAN = "#{label.gmud.execution.plan}";
	static final String GMUD_REDMINE_REFERENCE = "#{label.gmud.redmine.reference}";
	static final String GMUD_REDMINE_REFERENCED = "#{label.gmud.redmine.referenced}";
	static final String GMUD_REVIEWERS = "#{label.gmud.reviewers}";
	static final String GMUD_ROLLBACK_PLAN = "#{label.gmud.rollback.plan}";
	static final String GMUD_STATUS = "#{label.gmud.status}";
	static final String HOUR = "#{label.hour}";
	static final String ISSUE_ANALYSIS_WITH_TEROS = "#{label.issue.analysis.with.teros}";
	static final String ISSUE_ID = "#{label.issue.id}";
	static final String ISSUE_LINK = "#{label.issue.link}";
	static final String ISSUE_NUMBER = "#{label.issue.number}";
	static final String KEYBOARD_EVENTS = "#{label.keyboard.events}";
	static final String MOUSE_EVENTS = "#{label.mouse.events}";
	static final String OTHER_WINDOWS = "#{label.other.windows}";
	static final String OUTPUT_FOLDER = "#{label.output.folder}";
	static final String PER_ACTIVE_WINDOW = "#{label.per.active.window}";
	static final String PER_HOUR = "#{label.per.hour}";
	static final String PERCENTAGE_DONE = "#{label.percentage.done}";
	static final String PRODUCTIVITY_SUMMARY = "#{label.productivity.summary}";
	static final String REMOVE_SELECTED = "#{label.remove.selected}";
	static final String REVIEW_DATE = "#{label.review.date}";
	static final String REVIEW_STATUS = "#{label.review.status}";
	static final String REVIEWER = "#{label.reviewer}";
	static final String SAVED_TO = "#{label.saved.to}";
	static final String SEACH_FOR_ISSUE = "#{label.seach.for.issue}";
	static final String SEARCH_RESULTS = "#{label.search.results}";
	static final String SELECT_STATUS_TO_APPLY = "#{label.select.status.to.apply}";
	static final String SELECTED_EVIDENCES = "#{label.selected.evidences}";
	static final String SELECTED_ITEMS_TO_ANALYSE = "#{label.selected.items.to.analyse}";
	static final String SEND_TO_TEROS = "#{label.send.to.teros}";
	static final String SERVICE_CATALOG_GROUPS = "#{label.service.catalog.groups}";
	static final String SERVICE_CATALOG_NAME = "#{label.service.catalog.name}";
	static final String SERVICE_GROUP_NAME = "#{label.service.group.name}";
	static final String START_MONITORING = "#{label.start.monitoring}";
	static final String STATUS_RUNNING = "#{label.status.running}";
	static final String STATUS_STOPPED = "#{label.status.stopped}";
	static final String STOP_MONITORING = "#{label.stop.monitoring}";
	static final String TARGET_APPLICATIONS = "#{label.target.applications}";
	static final String TEROS_INSTRUCTIONS = "#{label.teros.instructions}";
	static final String TIME = "#{label.time}";
	static final String TOOL = "#{label.tool}";
	static final String TOOL_SHORT = "#{label.tool.short}";
	static final String UNKNOWN_APP = "#{label.unknown.app}";
	static final String UPDATE_STATUS = "#{label.update.status}";
	static final String VARIANT_ACTIVITIES_PERFORMED = "#{label.variant.activities.performed}";
	static final String VARIANT_COMPLEXITY = "#{label.variant.complexity}";
	static final String VARIANT_DELIVERABLES = "#{label.variant.deliverables}";
	static final String VARIANT_ESTIMATED_HOURS = "#{label.variant.estimated.hours}";
	static final String VARIANT_ID = "#{label.variant.id}";
	static final String VARIANT_REQUIRED_PROFILES = "#{label.variant.required.profiles}";
	static final String VARIANT_SCOPE = "#{label.variant.scope}";
	static final String WINDOW_TITLE = "#{label.window.title}";
	static final String MENU_GOVERNANCE = "#{menu.governance}";
	static final String MENU_ITSUPPORT = "#{menu.itsupport}";
	static final String MODULE_GOVERNANCE = "#{module.governance}";
	static final String MODULE_GOVERNANCE_DESC = "#{module.governance.desc}";
	static final String MODULE_ITSUPPORT_EMPLOYEE_ACTIVITY = "#{module.itsupport.employee.activity}";
	static final String MODULE_ITSUPPORT_EMPLOYEE_ACTIVITY_DESC = "#{module.itsupport.employee.activity.desc}";
	static final String MODULE_ITSUPPORT_EVIDENCE = "#{module.itsupport.evidence}";
	static final String MODULE_ITSUPPORT_EVIDENCE_DESC = "#{module.itsupport.evidence.desc}";
	static final String MODULE_ITSUPPORT_GMUD = "#{module.itsupport.gmud}";
	static final String MODULE_ITSUPPORT_GMUD_DESC = "#{module.itsupport.gmud.desc}";
	static final String MODULE_ITSUPPORT_REDMINE = "#{module.itsupport.redmine}";
	static final String MODULE_ITSUPPORT_REDMINE_DESC = "#{module.itsupport.redmine.desc}";
	static final String PROMPT_EDIT_DESCRIPTION = "#{prompt.edit.description}";
	static final String TEXT_ADD_ISSUES_TO_ANALYSIS = "#{text.add.issues.to.analysis}";
	static final String TEXT_ALERT_ERROR_HEADER = "#{text.alert.error.header}";
	static final String TEXT_ALERT_ISSUE_NOT_FOUND = "#{text.alert.issue.not.found}";
	static final String TEXT_CANNOT_REVERT_FINISHED_ITEMS_TO_EXECUTING = "#{text.cannot.revert.finished.items.to.executing}";
	static final String TEXT_CANNOT_REVERT_FINISHED_ITEMS_TO_FAILED = "#{text.cannot.revert.finished.items.to.failed}";
	static final String TEXT_END_DATE_CANNOT_BE_BEFORE_START_DATE = "#{text.end.date.cannot.be.before.start.date}";
	static final String TEXT_NO_SCREENS_CAPTURED = "#{text.no.screens.captured}";
	static final String TEXT_ONLY_ITEMS_EXECUTING_CAN_BE_FAILED = "#{text.only.items.executing.can.be.failed}";
	static final String TEXT_ONLY_ITEMS_EXECUTING_CAN_BE_FINISHED = "#{text.only.items.executing.can.be.finished}";
	static final String TEXT_PROVIDE_INSTRUCTIONS = "#{text.provide.instructions}";
	static final String TEXT_SCREEN_REFRESHED_RETRY_OPERATION = "#{text.screen.refreshed.retry.operation}";
	static final String TEXT_SELECT_EMPLOYEE = "#{text.select.employee}";
	static final String TEXT_SELECT_END_DATE = "#{text.select.end.date}";
	static final String TEXT_SELECT_EVIDENCES = "#{text.select.evidences}";
	static final String TEXT_SELECT_ITEMS_FIRST = "#{text.select.items.first}";
	static final String TEXT_SELECT_ITEMS_TO_UPDATE = "#{text.select.items.to.update}";
	static final String TEXT_SELECT_START_DATE = "#{text.select.start.date}";
	static final String TEXT_SELECT_STATUS_TO_APPLY = "#{text.select.status.to.apply}";
	static final String TITLE_ALERT_REDMINE = "#{title.alert.redmine}";
	static final String TITLE_CATALOG_SERVICE = "#{title.catalog.service}";
	static final String TITLE_CATALOG_SERVICE_LIST = "#{title.catalog.service.list}";
	static final String TITLE_JOB_EVIDENCE_REPORT = "#{title.job.evidence.report}";
	static final String TITLE_SERVICE_CATALOG = "#{title.service.catalog}";
	static final String TITLE_SERVICE_CATALOG_LIST = "#{title.service.catalog.list}";
	static final String TITLE_SERVICE_GROUP = "#{title.service.group}";
	static final String VIEW_CAPTURE_EVIDENCE = "#{view.capture.evidence}";
	static final String VIEW_CAPTURE_EVIDENCE_DESC = "#{view.capture.evidence.desc}";
	static final String VIEW_CATALOG_SERVICE = "#{view.catalog.service}";
	static final String VIEW_CATALOG_SERVICE_DESC = "#{view.catalog.service.desc}";
	static final String VIEW_EMPLOYEE_ACTIVITY_MONITORING = "#{view.employee.activity.monitoring}";
	static final String VIEW_EMPLOYEE_ACTIVITY_MONITORING_DESC = "#{view.employee.activity.monitoring.desc}";
	static final String VIEW_GMUD_EDIT = "#{view.gmud.edit}";
	static final String VIEW_GMUD_EDIT_DESC = "#{view.gmud.edit.desc}";
	static final String VIEW_JOB_EVIDENCE = "#{view.job.evidence}";
	static final String VIEW_JOB_EVIDENCE_DESC = "#{view.job.evidence.desc}";
	static final String VIEW_JOB_EVIDENCE_REPORT = "#{view.job.evidence.report}";
	static final String VIEW_JOB_EVIDENCE_REPORT_DESC = "#{view.job.evidence.report.desc}";
	static final String VIEW_REDMINE_SEARCH_ISSUES_TO_TEROS = "#{view.redmine.search.issues.to.teros}";
	static final String VIEW_REDMINE_SEARCH_ISSUES_TO_TEROS_DESC = "#{view.redmine.search.issues.to.teros.desc}";
	static final String VIEW_SERVICE_CATALOG = "#{view.service.catalog}";
	static final String VIEW_SERVICE_CATALOG_DESC = "#{view.service.catalog.desc}";
	static final String VIEW_SERVICE_CATALOG_IMPORT = "#{view.service.catalog.import}";
	static final String VIEW_SERVICE_CATALOG_IMPORT_DESC = "#{view.service.catalog.import.desc}";

	static final String TITLE_SERVICE_CATALOG_IMPORT = "#{title.service.catalog.import}";
	static final String LABEL_SELECT_JSON_FILE = "#{label.select.json.file}";
	static final String MSG_NO_FILE_SELECTED = "#{msg.no.file.selected}";
	static final String MSG_STRUCTURE_TO_IMPORT = "#{msg.structure.to.import}";
	static final String LABEL_IMPORT = "#{label.import}";
	static final String TITLE_SELECT_JSON_FILE = "#{title.select.json.file}";
	static final String LABEL_JSON_FILES = "#{label.json.files}";
	static final String ERR_ROOT_CATALOG_NOT_FOUND = "#{err.root.catalog.not.found}";
	static final String ERR_CATALOG_NAME_MISSING = "#{err.catalog.name.missing}";
	static final String ERR_CATALOG_GROUPS_MISSING = "#{err.catalog.groups.missing}";
	static final String ERR_NAME_MISSING = "#{err.name.missing}";
	static final String WARN_NO_SERVICES = "#{warn.no.services}";
	static final String ERR_NUMBER_MISSING = "#{err.number.missing}";
	static final String WARN_NO_VARIANTS = "#{warn.no.variants}";
	static final String ERR_ID_MISSING = "#{err.id.missing}";
	static final String ERR_COMPLEXITY_MISSING = "#{err.complexity.missing}";
	static final String ERR_HOURS_MISSING = "#{err.hours.missing}";
	static final String MSG_JSON_VALID = "#{msg.json.valid}";
	static final String MSG_JSON_VALID_WARNINGS = "#{msg.json.valid.warnings}";
	static final String MSG_JSON_INVALID = "#{msg.json.invalid}";
	static final String MSG_ERROR_READING_FILE = "#{msg.error.reading.file}";
	static final String MSG_ERROR_PROCESSING_JSON = "#{msg.error.processing.json}";
	static final String LABEL_SERVICES = "#{label.services}";
	static final String LABEL_VARIANTS = "#{label.variants}";
	static final String LABEL_SUMMARY = "#{label.summary}";
	static final String LABEL_GROUPS = "#{label.groups}";
	static final String MSG_FIX_ERRORS = "#{msg.fix.errors}";
	static final String MSG_IMPORT_SUCCESS = "#{msg.import.success}";

}
