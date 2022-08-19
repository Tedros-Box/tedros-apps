package org.tedros.person.server.report.controller;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.tedros.person.domain.DomainApp;
import org.tedros.person.ejb.controller.INaturalPersonReportController;
import org.tedros.person.model.NaturalPerson;
import org.tedros.person.report.model.NaturalPersonItemModel;
import org.tedros.person.report.model.NaturalPersonReportModel;
import org.tedros.person.server.service.NaturalPersonService;

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
