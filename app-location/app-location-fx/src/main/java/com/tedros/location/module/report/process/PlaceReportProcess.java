package com.tedros.location.module.report.process;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.tedros.core.TLanguage;
import com.tedros.fxapi.exception.TProcessException;
import com.tedros.fxapi.process.TReportProcess;
import com.tedros.location.report.model.PlaceReportModel;
import com.tedros.location.resource.AppResource;
import com.tedros.location.start.TConstant;

public class PlaceReportProcess extends TReportProcess<PlaceReportModel> {

	public PlaceReportProcess() throws TProcessException {
		super("IPlaceReportControllerRemote", "Place");
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
			return AppResource.getPlaceJasperInputStream();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void runAfterExport(Map<String, Object> params) {
		
	}

}
