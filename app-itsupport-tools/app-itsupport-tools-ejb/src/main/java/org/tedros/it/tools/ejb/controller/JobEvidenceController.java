/**
 * 
 */
package org.tedros.it.tools.ejb.controller;

import org.tedros.it.tools.domain.DomainApp;
import org.tedros.it.tools.ejb.service.ItSupportToolsService;
import org.tedros.it.tools.entity.JobEvidence;
import org.tedros.server.ejb.controller.ITSecurityController;
import org.tedros.server.ejb.controller.TSecureEjbController;
import org.tedros.server.security.ITSecurity;
import org.tedros.server.security.TAccessPolicie;
import org.tedros.server.security.TBeanPolicie;
import org.tedros.server.security.TBeanSecurity;
import org.tedros.server.security.TSecurityInterceptor;
import org.tedros.server.service.ITEjbService;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;

/**
 * The controller bean
 * 
 * @author Davis Dun
 *
 */
@TSecurityInterceptor
@Stateless(name="IJobEvidenceController")
@TBeanSecurity({@TBeanPolicie(id = DomainApp.EVIDENCE_MANAGER_FORM_ID, 
policie = { TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS })})
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class JobEvidenceController extends TSecureEjbController<JobEvidence> implements IJobEvidenceController, ITSecurity  {

	@EJB
	private ItSupportToolsService<JobEvidence> serv;
	
	@EJB
	private ITSecurityController securityController;
	
	@Override
	public ITEjbService<JobEvidence> getService() {
		return serv;
	}
	
	@Override
	public ITSecurityController getSecurityController() {
		return securityController;
	}
}
