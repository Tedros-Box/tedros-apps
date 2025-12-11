package org.tedros.it.tools.ejb.controller;

import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.tedros.common.model.TFileEntity;
import org.tedros.core.controller.TFileEntityController;
import org.tedros.it.tools.domain.DomainApp;
import org.tedros.it.tools.ejb.service.JobEvidenceService;
import org.tedros.it.tools.entity.JobEvidence;
import org.tedros.it.tools.model.JobEvidenceReportItemModel;
import org.tedros.it.tools.model.JobEvidenceReportModel;
import org.tedros.server.ejb.controller.ITSecurityController;
import org.tedros.server.ejb.controller.TSecureEjbController;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;
import org.tedros.server.security.ITSecurity;
import org.tedros.server.security.TAccessPolicie;
import org.tedros.server.security.TAccessToken;
import org.tedros.server.security.TBeanPolicie;
import org.tedros.server.security.TBeanSecurity;
import org.tedros.server.security.TSecurityInterceptor;
import org.tedros.server.service.ITEjbService;
import org.tedros.server.service.TServiceLocator;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;

@TSecurityInterceptor
@TBeanSecurity({@TBeanPolicie(id = DomainApp.EVIDENCE_REPORT_FORM_ID, 
policie = { TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS })})
@Stateless(name="IJobEvidenceReportController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class JobEvidenceReportController extends TSecureEjbController<JobEvidence> 
implements IJobEvidenceReportController, ITSecurity {

	@EJB
	private JobEvidenceService serv;
	
	@EJB
	private ITSecurityController security;
	
	@Override
	public ITSecurityController getSecurityController() {
		return security;
	}
	
	@Override
	protected ITEjbService<JobEvidence> getService() {
		return serv;
	}

	@Override
	public TResult<JobEvidenceReportModel> process(TAccessToken token, JobEvidenceReportModel model) {
		
		try {
			List<JobEvidence> lst = serv.search(model.getName(), model.getIssueNumber(), model.getIssueTitle(), model.getEmployee(), 
					model.getExecutionDate(), model.getOrderBy(), model.getOrderType());
			
			
			if(ObjectUtils.isNotEmpty(lst)) {
				try(TServiceLocator locator = TServiceLocator.getInstance()){
					TFileEntityController fileEntityController = locator.lookup(TFileEntityController.JNDI_NAME);
					List<JobEvidenceReportItemModel> items = lst.stream()
							.map(e->{								
								if(ObjectUtils.isNotEmpty(e.getItems())) {
									e.getItems().forEach(i->{
										TResult<TFileEntity> fe = fileEntityController.loadBytes(token, i.getFile());
										if(fe.getState().equals(TState.SUCCESS) && fe.getValue()!=null) {
											i.setFile(fe.getValue());
										}
									});
								}
								
								return new JobEvidenceReportItemModel(e);
							})
							.toList();
					
					model.setResult(items);
				}
				
			}
			
			return new TResult<>(TState.SUCCESS, model);
		}catch (Exception e) {
			return super.processException(token, null, e);
		}
	}	

}
