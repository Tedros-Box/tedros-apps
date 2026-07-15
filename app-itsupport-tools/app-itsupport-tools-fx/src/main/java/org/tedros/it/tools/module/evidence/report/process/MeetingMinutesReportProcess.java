package org.tedros.it.tools.module.evidence.report.process;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.tedros.core.TLanguage;
import org.tedros.fx.process.TRuntimeReportProcess;
import org.tedros.it.tools.ItToolsKey;
import org.tedros.it.tools.entity.MeetingEvidence;
import org.tedros.it.tools.entity.MeetingMinutes;
import org.tedros.it.tools.resource.AppResource;

public class MeetingMinutesReportProcess extends TRuntimeReportProcess<MeetingMinutes> {

	public MeetingMinutesReportProcess() {
		super(TLanguage.getInstance().getString(ItToolsKey.VIEW_MEETING_MINUTES));
		setSubReportDir(AppResource.APP_MODULE_PATH);
	}

	@Override
	protected InputStream getJasperInputStream() {
		try {
			return AppResource.getAtaReuniaoJasperInputStream();
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected Map<String, Object> getReportParameters() {
		Map<String, Object> params = new HashMap<>();
		params.put("SUBREPORT_DIR", AppResource.APP_MODULE_PATH);
		return params;
	}

	@Override
	protected void runBeforeExport(Collection<MeetingMinutes> data, Map<String, Object> params) {
		if (data == null)
			return;
		for (MeetingMinutes m : data) {
			if (m.getEvidences() != null) {
				for (MeetingEvidence e : m.getEvidences()) {
					e.initInputStream();
				}
			}
		}
	}

	@Override
	protected void runAfterExport(Collection<MeetingMinutes> data, Map<String, Object> params) {
		if (data == null)
			return;
		for (MeetingMinutes m : data) {
			if (m.getEvidences() != null) {
				for (MeetingEvidence e : m.getEvidences()) {
					try {
						e.closeInputStream();
					} catch (IOException ex) {
						// ignore close errors
					}
				}
			}
		}
	}
}
