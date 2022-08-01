package com.tedros.location.module.report.process;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.tedros.core.TLanguage;
import com.tedros.ejb.controller.IPlaceReportController;
import com.tedros.fxapi.TUsualKey;
import com.tedros.fxapi.exception.TProcessException;
import com.tedros.fxapi.process.TReportProcess;
import com.tedros.location.LocatKey;
import com.tedros.location.report.model.PlaceReportModel;
import com.tedros.location.resource.AppResource;
import com.tedros.location.start.TConstant;

public class PlaceReportProcess extends TReportProcess<PlaceReportModel> {

	public PlaceReportProcess() throws TProcessException {
		super(IPlaceReportController.JNDI_NAME, "Place");
	}
	
	protected HashMap<String, Object> getReportParameters() {
		TLanguage l = TLanguage.getInstance(TConstant.UUI);
		super.setSubReportDir(new AppResource().getFolderPath());
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("hTitle", l.getString(TUsualKey.TITLE));
		params.put("hDesc", l.getString(TUsualKey.DESCRIPTION));
		params.put("hType", l.getString(TUsualKey.TYPE));
		params.put("hAddr", l.getString(TUsualKey.ADDRESS));
		params.put("hContact", l.getString(TUsualKey.CONTACTS));
		params.put("report_title", l.getString(LocatKey.HEADER_JASPER_PLACE));
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
