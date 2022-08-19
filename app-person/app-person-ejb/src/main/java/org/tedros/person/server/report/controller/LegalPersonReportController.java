package org.tedros.person.server.report.controller;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.tedros.person.domain.DomainApp;
import org.tedros.person.ejb.controller.ILegalPersonReportController;
import org.tedros.person.model.LegalPerson;
import org.tedros.person.report.model.LegalPersonItemModel;
import org.tedros.person.report.model.LegalPersonReportModel;
import org.tedros.person.server.service.LegalPersonService;

import org.tedros.server.ejb.controller.ITSecurityController;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;
import org.tedros.server.security.ITSecurity;
import org.tedros.server.security.TAccessPolicie;
import org.tedros.server.security.TAccessToken;
import org.tedros.server.security.TActionPolicie;
import org.tedros.server.security.TBeanPolicie;
import org.tedros.server.security.TBeanSecurity;
import org.tedros.server.security.TMethodPolicie;
import org.tedros.server.security.TMethodSecurity;
import org.tedros.server.security.TSecurityInterceptor;

@TSecurityInterceptor

@TBeanSecurity({@TBeanPolicie(id = DomainApp.LEGAL_PERSON_REPORT_FORM_ID, 
policie = { TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS })})
@Stateless(name="ILegalPersonReportController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class LegalPersonReportController implements ILegalPersonReportController, ITSecurity {

	@EJB
	private LegalPersonService serv;
	
	@EJB
	private ITSecurityController security;
	
	public LegalPersonReportController() {
	}

	@Override	
	@TMethodSecurity({
		@TMethodPolicie(policie = {TActionPolicie.SEARCH})})
	public TResult<LegalPersonReportModel> process(TAccessToken token, LegalPersonReportModel m) {
		try{
			List<LegalPerson> lst = serv.filterBy(m);
			if(lst!=null){
				List<LegalPersonItemModel> itens = new ArrayList<>();
				for(LegalPerson a : lst){
					itens.add(new LegalPersonItemModel(a));
				}
				m.setResult(itens);
			}
			return new TResult<>(TState.SUCCESS, m);
		}catch(Exception e){
			return new TResult<>(TState.ERROR, e.getMessage());
		}
	}

	@Override
	public ITSecurityController getSecurityController() {
		return security;
	}

}
