package org.tedros.stock.module.report.process;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.tedros.core.TLanguage;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.exception.TProcessException;
import org.tedros.fx.process.TReportProcess;
import org.tedros.server.result.TResult;
import org.tedros.stock.STCKKey;
import org.tedros.stock.ejb.controller.IProductController;
import org.tedros.stock.model.ProductReportModel;
import org.tedros.stock.resource.AppResource;

import net.sf.jasperreports.engine.JRException;

public class ProductReportProcess extends TReportProcess<ProductReportModel> {

	public ProductReportProcess() throws TProcessException {
		super(IProductController.JNDI_NAME, 
				TLanguage.getInstance()
				.getString(STCKKey.TITLE_REPORT_PRODUCT));
	}
	
	/* (non-Javadoc)
	 * @see org.tedros.fx.process.TReportProcess#runExportPdf()
	 */
	@Override
	protected TResult<ProductReportModel> runExportPdf() throws JRException {
		initImages();
		return super.runExportPdf();
	}
	/* (non-Javadoc)
	 * @see org.tedros.fx.process.TReportProcess#runExportXls()
	 */
	@Override
	protected TResult<ProductReportModel> runExportXls() throws JRException {
		initImages();
		return super.runExportXls();
	}

	private void initImages() {
		ProductReportModel m = super.getModel();
		if(m.getResult()!=null)
			m.getResult().forEach(e->{
				if(e.getImages()!=null)
					e.getImages().forEach(i->i.initInputStream());
			});
	}

	protected HashMap<String, Object> getReportParameters() {
		TLanguage l = TLanguage.getInstance();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("hCode", l.getString(TUsualKey.CODE));
		params.put("hProduct", l.getString(TUsualKey.NAME));
		params.put("hName", l.getString(TUsualKey.NAME));
		params.put("hTrademark", l.getString(STCKKey.TRADEMARK));
		params.put("hUnitMeasure", l.getString(STCKKey.UNIT_MEASURE));
		params.put("hMeasure",  l.getString(STCKKey.MEASURE));
		params.put("hSize",  l.getString(STCKKey.SIZE));
		params.put("hWeight",  l.getString(STCKKey.WEIGHT));
		params.put("hDesc",  l.getString(TUsualKey.DESCRIPTION));
		params.put("hImages",  l.getString(TUsualKey.IMAGE));
		params.put("report_title", l.getString(STCKKey.TITLE_REPORT_PRODUCT));
		params.put("SUBREPORT_DIR", AppResource.APP_MODULE_PATH);
		return params;
	}
	
	protected InputStream getJasperInputStream() {
		try {
			return AppResource.getProductJasperInputStream();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void runAfterExport(Map<String, Object> params) {
		ProductReportModel m = super.getModel();
		if(m.getResult()!=null)
			m.getResult().forEach(e->{
				if(e.getImages()!=null)
					e.getImages().forEach(i->{
						try {
							i.closeInputStream();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					});
			});
	}

}
