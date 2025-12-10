package org.tedros.it.tools.evidence.report.process;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.tedros.core.TLanguage;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.exception.TProcessException;
import org.tedros.fx.process.TReportProcess;
import org.tedros.it.tools.ItToolsKey;
import org.tedros.it.tools.ejb.controller.IJobEvidenceController;
import org.tedros.it.tools.model.JobEvidenceReportModel;
import org.tedros.it.tools.resource.AppResource;
import org.tedros.server.result.TResult;

import net.sf.jasperreports.engine.JRException;

public class JobEvidenceReportProcess extends TReportProcess<JobEvidenceReportModel> {

	public JobEvidenceReportProcess() throws TProcessException {
		super(IJobEvidenceController.JNDI_NAME, 
				TLanguage.getInstance()
				.getString("Relatorio de evidencias"));
	}
	
	/* (non-Javadoc)
	 * @see org.tedros.fx.process.TReportProcess#runExportPdf()
	 */
	@Override
	protected TResult<JobEvidenceReportModel> runExportPdf() throws JRException {
		initImages();
		return super.runExportPdf();
	}
	/* (non-Javadoc)
	 * @see org.tedros.fx.process.TReportProcess#runExportXls()
	 */
	@Override
	protected TResult<JobEvidenceReportModel> runExportXls() throws JRException {
		initImages();
		return super.runExportXls();
	}

	private void initImages() {
		JobEvidenceReportModel m = super.getModel();
		if(m.getResult()!=null)
			m.getResult().forEach(e->{
				if(e.getItems()!=null)
					e.getItems().forEach(i->i.initInputStream());
			});
	}

	protected HashMap<String, Object> getReportParameters() {
		TLanguage l = TLanguage.getInstance();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("hCode", l.getString(TUsualKey.CODE));
		params.put("hProduct", l.getString(TUsualKey.NAME));
		params.put("hName", l.getString(TUsualKey.NAME));
		params.put("hTrademark", l.getString(TUsualKey.TRADEMARK));
		params.put("hUnitMeasure", l.getString(TUsualKey.UNIT_MEASURE));
		params.put("hMeasure",  l.getString(TUsualKey.MEASURE));
		params.put("hSize",  l.getString(TUsualKey.SIZE));
		params.put("hWeight",  l.getString(TUsualKey.WEIGHT));
		params.put("hDesc",  l.getString(TUsualKey.DESCRIPTION));
		params.put("hImages",  l.getString(TUsualKey.IMAGE));
		params.put("report_title", l.getString("Relatorio de evidencias"));
		params.put("SUBREPORT_DIR", AppResource.APP_MODULE_PATH);
		return params;
	}
	
	protected InputStream getJasperInputStream() {
		try {
			return AppResource.getJobEvidenceJasperInputStream();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void runAfterExport(Map<String, Object> params) {
		JobEvidenceReportModel m = super.getModel();
		if(m.getResult()!=null)
			m.getResult().forEach(e->{
				if(e.getItems()!=null)
					e.getItems().forEach(i->{
						try {
							i.closeInputStream();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					});
			});
	}

}
