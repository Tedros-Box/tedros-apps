package com.tedros.person.server.report.controller;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.tedros.ejb.base.controller.ITSecurityController;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.TState;
import com.tedros.ejb.base.security.ITSecurity;
import com.tedros.ejb.base.security.TAccessPolicie;
import com.tedros.ejb.base.security.TAccessToken;
import com.tedros.ejb.base.security.TActionPolicie;
import com.tedros.ejb.base.security.TBeanPolicie;
import com.tedros.ejb.base.security.TBeanSecurity;
import com.tedros.ejb.base.security.TMethodPolicie;
import com.tedros.ejb.base.security.TMethodSecurity;
import com.tedros.ejb.base.security.TSecurityInterceptor;
import com.tedros.person.domain.DomainApp;
import com.tedros.person.ejb.controller.ILegalPersonReportController;
import com.tedros.person.model.LegalPerson;
import com.tedros.person.report.model.LegalPersonItemModel;
import com.tedros.person.report.model.LegalPersonReportModel;
import com.tedros.person.server.service.LegalPersonService;

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
