package com.tedros.location.module.report.process;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.tedros.core.TLanguage;
import com.tedros.fxapi.exception.TProcessException;
import com.tedros.fxapi.process.TReportProcess;
import com.tedros.location.report.model.PlaceReportModel;
import com.tedros.location.resource.AppResource;
import com.tedros.location.start.TConstant;
import com.tedros.settings.util.TOwnerProperties;
import com.tedros.util.TedrosFolder;

public class PlaceReportProcess extends TReportProcess<PlaceReportModel> {

	public PlaceReportProcess() throws TProcessException {
		super("IPlaceReportControllerRemote", "Place");
	}
	
	protected HashMap<String, Object> getReportParameters() {
		TOwnerProperties o = null;// new TOwnerProperties();
		
		TLanguage l = TLanguage.getInstance(TConstant.UUI);
		InputStream logoIs = null;
		if(o!=null && o.getLogotype()!=null && o.getLogotype().getByteEntity()!=null)
			logoIs = new ByteArrayInputStream(o.getLogotype().getByteEntity().getBytes());
		else
			try {
				logoIs = new FileInputStream(new File(TedrosFolder.IMAGES_FOLDER.getFullPath()+"logo-tedros-medium.png"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		HashMap<String, Object> params = new HashMap<String, Object>();
		if(logoIs!=null)
			params.put("logo", logoIs);
		params.put("SUBREPORT_DIR", AppResource.APP_MODULE_PATH);
		params.put("hTitle", l.getString("#{label.title}"));
		params.put("hDesc", l.getString("#{label.description}"));
		params.put("hType", l.getString("#{label.type}"));
		params.put("hAddr", l.getString("#{label.address}"));
		params.put("hContact", l.getString("#{label.contacts}"));
		params.put("report_title", l.getString("#{header.jasper.place}"));
		if(o!=null && StringUtils.isNotBlank(o.getOrganization()))
			params.put("report_org", o.getOrganization());
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
		if(params.containsKey("logo"))
		try {
			((InputStream)params.get("logo")).close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
