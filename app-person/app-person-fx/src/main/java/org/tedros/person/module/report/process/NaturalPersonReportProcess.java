package org.tedros.person.module.report.process;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.tedros.core.TLanguage;
import org.tedros.core.context.TedrosContext;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.exception.TProcessException;
import org.tedros.fx.process.TReportProcess;
import org.tedros.person.PersonKeys;
import org.tedros.person.report.model.NaturalPersonReportModel;
import org.tedros.person.resource.AppResource;
import org.tedros.server.result.TResult;

import net.sf.jasperreports.engine.JRException;

public class NaturalPersonReportProcess extends TReportProcess<NaturalPersonReportModel> {

	public NaturalPersonReportProcess() throws TProcessException {
		super("INaturalPersonReportControllerRemote", 
				TLanguage.getInstance().getString(PersonKeys.REPORT_HEADER_NATURAL_PERSON));
	}
	
	protected HashMap<String, Object> getReportParameters() {
		TLanguage l = TLanguage.getInstance();
		super.setSubReportDir(AppResource.APP_MODULE_PATH);
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("hName", l.getString(TUsualKey.NAME));
		params.put("hBirthDt", l.getString(TUsualKey.BIRTHDATE));
		params.put("hSex", l.getString(TUsualKey.SEX));
		params.put("hGender", l.getString(TUsualKey.GENDER));
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
		params.put("report_title", l.getString(PersonKeys.REPORT_HEADER_NATURAL_PERSON));
		return params;
	}
	
	protected TResult<NaturalPersonReportModel> runExportPdf() throws JRException {
		evaluateModel();
		return super.runExportPdf();
	}
	
	protected TResult<NaturalPersonReportModel> runExportXls() throws JRException {
		evaluateModel();
		return super.runExportXls();
	}

	private void evaluateModel() {
		TLanguage l = TLanguage.getInstance();
		NaturalPersonReportModel m = super.getModel();
		if(m.getResult()!=null)
			m.getResult().parallelStream().forEach(c->{
				if(c.getSex()!=null)
					c.setSex(l.getString(c.getSex()));
				if(c.getGender()!=null)
					c.setGender(l.getString(c.getGender()));
				if(c.getCivilStatus()!=null)
					c.setCivilStatus(l.getString(c.getCivilStatus()));
			});
	}
	
	protected InputStream getJasperInputStream() {
		try {
			return AppResource.getNaturalPersonJasperInputStream();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void runAfterExport(Map<String, Object> params) {
		
	}

}
