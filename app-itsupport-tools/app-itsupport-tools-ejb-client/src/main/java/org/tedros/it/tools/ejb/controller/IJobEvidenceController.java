/**
 * 
 */
package org.tedros.it.tools.ejb.controller;

import org.tedros.it.tools.entity.JobEvidence;
import org.tedros.server.controller.ITSecureEjbController;

import jakarta.ejb.Remote;

/**
 * @author Davis Dun
 *
 */
@Remote
public interface IJobEvidenceController extends ITSecureEjbController<JobEvidence> {

	static final String JNDI_NAME = "IJobEvidenceControllerRemote";
		
}
