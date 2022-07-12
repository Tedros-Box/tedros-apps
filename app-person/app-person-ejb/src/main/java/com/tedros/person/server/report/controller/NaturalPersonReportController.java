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
import com.tedros.person.ejb.controller.INaturalPersonReportController;
import com.tedros.person.model.NaturalPerson;
import com.tedros.person.report.model.NaturalPersonItemModel;
import com.tedros.person.report.model.NaturalPersonReportModel;
import com.tedros.person.server.service.NaturalPersonService;

@TSecurityInterceptor

@TBeanSecurity({@TBeanPolicie(id = DomainApp.NATURAL_PERSON_REPORT_FORM_ID, 
policie = { TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS })})
@Stateless(name="INaturalPersonReportController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class NaturalPersonReportController implements INaturalPersonReportController, ITSecurity {

	@EJB
	private NaturalPersonService serv;
	
	@EJB
	private ITSecurityController security;
	
	public NaturalPersonReportController() {
	}

	@Override	
	@TMethodSecurity({
		@TMethodPolicie(policie = {TActionPolicie.SEARCH})})
	public TResult<NaturalPersonReportModel> process(TAccessToken token, NaturalPersonReportModel m) {
		try{
			List<NaturalPerson> lst = serv.filterBy(m);
			if(lst!=null){
				List<NaturalPersonItemModel> itens = new ArrayList<>();
				for(NaturalPerson a : lst){
					itens.add(new NaturalPersonItemModel(a));
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
