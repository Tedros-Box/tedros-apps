package org.tedros.stock.module.report.process;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.tedros.core.TLanguage;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.exception.TProcessException;
import org.tedros.fx.process.TReportProcess;
import org.tedros.stock.STCKKey;
import org.tedros.stock.ejb.controller.IInventoryReportController;
import org.tedros.stock.model.InventoryReportModel;
import org.tedros.stock.resource.AppResource;

public class InventoryReportProcess extends TReportProcess<InventoryReportModel> {

	public InventoryReportProcess() throws TProcessException {
		super(IInventoryReportController.JNDI_NAME, 
				TLanguage.getInstance()
				.getString(STCKKey.TITLE_REPORT_INVENTORY));
	}
	
	protected HashMap<String, Object> getReportParameters() {
		TLanguage l = TLanguage.getInstance();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("hCode", l.getString(TUsualKey.CODE));
		params.put("hProduct", l.getString(TUsualKey.NAME));
		params.put("hAmount", l.getString(TUsualKey.AMOUNT));
		params.put("hDate", l.getString(STCKKey.UNTIL_DATE));
		params.put("hCostCenter", l.getString(STCKKey.COST_CENTER));
		params.put("vDate", format(getModel().getDate()));
		params.put("vCostCenter", getModel().getCostCenter()!=null 
				? getModel().getCostCenter().getName()
						: l.getString(STCKKey.ALL));
		params.put("report_title", l.getString(STCKKey.TITLE_REPORT_INVENTORY));
		return params;
	}
	
	private String format(Date dt) {
		if(dt!=null)
			return DateFormat
					.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT, TLanguage.getLocale())
					.format(dt);
		return TLanguage.getInstance().getString(STCKKey.ALL);
	}
	
	protected InputStream getJasperInputStream() {
		try {
			return AppResource.getInventoryJasperInputStream();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void runAfterExport(Map<String, Object> params) {
		
	}

}
