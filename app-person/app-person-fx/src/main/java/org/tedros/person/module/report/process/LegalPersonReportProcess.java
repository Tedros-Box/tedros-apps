package org.tedros.person.module.report.process;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.tedros.person.PersonKeys;
import org.tedros.person.report.model.LegalPersonReportModel;
import org.tedros.person.resource.AppResource;
import org.tedros.person.start.TConstant;

import org.tedros.core.TLanguage;
import org.tedros.server.result.TResult;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.exception.TProcessException;
import org.tedros.fx.process.TReportProcess;

import net.sf.jasperreports.engine.JRException;

public class LegalPersonReportProcess extends TReportProcess<LegalPersonReportModel> {

	public LegalPersonReportProcess() throws TProcessException {
		super("ILegalPersonReportControllerRemote", 
				TLanguage.getInstance().getString(PersonKeys.REPORT_HEADER_LEGAL_PERSON));
	}
	
	protected HashMap<String, Object> getReportParameters() {
		TLanguage l = TLanguage.getInstance();
		super.setSubReportDir(AppResource.APP_MODULE_PATH);
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("hName", l.getString(TUsualKey.NAME));
		params.put("hOtherName", l.getString(TUsualKey.TRADE_NAME));
		params.put("hOccup", l.getString(TUsualKey.OCCUPATION));
		params.put("hStartDt", l.getString(TUsualKey.START_ACTIVITIES));
		params.put("hEndDt", l.getString(TUsualKey.END_ACTIVITIES));
		params.put("hHirDt", l.getString(TUsualKey.HIRING_DATE));
		params.put("hResDt", l.getString(TUsualKey.RESIGNATION_DATE));
		params.put("hEmployees", l.getString(TUsualKey.STAFF));
		params.put("hDocs", l.getString(TUsualKey.DOCUMENTS));
		params.put("hAttrib", l.getString(TUsualKey.ATTRIBUTES));
		params.put("hObs", l.getString(TUsualKey.OBSERVATION));
		params.put("hVale", l.getString(TUsualKey.ADDITIONAL_DATA));
		params.put("hCode", l.getString(TUsualKey.REF_CODE));
		params.put("hState", l.getString(TUsualKey.STATE));
		params.put("hFile", l.getString(TUsualKey.FILE));
		params.put("hDesc", l.getString(TUsualKey.DESCRIPTION));
		params.put("hType", l.getString(TUsualKey.TYPE));
		params.put("hAddr", l.getString(TUsualKey.ADDRESS));
		params.put("hContact", l.getString(TUsualKey.CONTACTS));
		params.put("report_title", l.getString(PersonKeys.REPORT_HEADER_LEGAL_PERSON));
		return params;
	}
	
	
	protected InputStream getJasperInputStream() {
		try {
			return AppResource.getLegalPersonJasperInputStream();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void runAfterExport(Map<String, Object> params) {
		
	}

}
