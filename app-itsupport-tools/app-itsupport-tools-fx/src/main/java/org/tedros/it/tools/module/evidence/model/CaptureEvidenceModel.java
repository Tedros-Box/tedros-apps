package org.tedros.it.tools.module.evidence.model;

import org.tedros.server.model.ITModel;

public class CaptureEvidenceModel implements ITModel {

	private static final long serialVersionUID = -6189754265169425392L;
	
	private String uuid;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}	

}
