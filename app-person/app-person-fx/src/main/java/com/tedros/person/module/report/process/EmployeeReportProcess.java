package com.tedros.person.module.report.process;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.tedros.core.TLanguage;
import com.tedros.fxapi.exception.TProcessException;
import com.tedros.fxapi.process.TReportProcess;
import com.tedros.person.report.model.EmployeeReportModel;
import com.tedros.person.resource.AppResource;
import com.tedros.person.start.TConstant;

public class EmployeeReportProcess extends TReportProcess<EmployeeReportModel> {

	public EmployeeReportProcess() throws TProcessException {
		super("IEmployeeReportControllerRemote", "Employee");
	}
	
	protected HashMap<String, Object> getReportParameters() {
		TLanguage l = TLanguage.getInstance(TConstant.UUI);
		super.setSubReportDir(AppResource.APP_MODULE_PATH);
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("hTitle", l.getString("#{label.title}"));
		params.put("hDesc", l.getString("#{label.description}"));
		params.put("hType", l.getString("#{label.type}"));
		params.put("hAddr", l.getString("#{label.address}"));
		params.put("hContact", l.getString("#{label.contacts}"));
		params.put("report_title", l.getString("#{header.jasper.place}"));
		return params;
	}
	
	protected InputStream getJasperInputStream() {
		try {
			return AppResource.getEmployeeJasperInputStream();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void runAfterExport(Map<String, Object> params) {
		
	}

}
