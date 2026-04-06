package org.tedros.it.tools.module.governance.report;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.tedros.core.TLanguage;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.exception.TProcessException;
import org.tedros.fx.process.TReportProcess;
import org.tedros.it.tools.ItToolsKey;
import org.tedros.it.tools.ejb.controller.IServiceCatalogReportController;
import org.tedros.it.tools.model.ServiceCatalogReportModel;
import org.tedros.it.tools.resource.AppResource;

public class ServiceCatalogReportProcess extends TReportProcess<ServiceCatalogReportModel> {

	public ServiceCatalogReportProcess() throws TProcessException {
		super(IServiceCatalogReportController.JNDI_NAME, 
				"Relatório de Catálogo de Serviços");
	}
	

	protected HashMap<String, Object> getReportParameters() {
		TLanguage l = TLanguage.getInstance();
		HashMap<String, Object> params = new HashMap<>();
		params.put("hName", l.getString(TUsualKey.NAME));
		params.put("hNumber", l.getString(TUsualKey.NUMBER));
		params.put("hVariantId", l.getString(ItToolsKey.VARIANT_ID));
		params.put("hComplexity", l.getString(ItToolsKey.VARIANT_COMPLEXITY));
		params.put("hScope", l.getString(ItToolsKey.VARIANT_SCOPE));
		params.put("hEstimatedHours",  l.getString(ItToolsKey.VARIANT_ESTIMATED_HOURS));
		params.put("hDeliverables",  l.getString(ItToolsKey.VARIANT_DELIVERABLES));
		params.put("hRequiredProfiles",  l.getString(ItToolsKey.VARIANT_REQUIRED_PROFILES));		
		params.put("hActivitiesPerformed",  l.getString(ItToolsKey.VARIANT_ACTIVITIES_PERFORMED));
		params.put("SUBREPORT_DIR", AppResource.APP_MODULE_PATH);
		//params.put("logo", getLogoInputStream());
		return params;
		
	}
	
	protected InputStream getJasperInputStream() {
		try {
			return AppResource.getServiceCatalogJasperInputStream();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}


	@Override
	protected void runAfterExport(Map<String, Object> params) {
		// TODO Auto-generated method stub
		
	}

}
