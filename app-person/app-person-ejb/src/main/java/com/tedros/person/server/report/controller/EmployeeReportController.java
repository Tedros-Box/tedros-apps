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
import com.tedros.location.domain.DomainApp;
import com.tedros.person.ejb.controller.IEmployeeReportController;
import com.tedros.person.model.Employee;
import com.tedros.person.report.model.EmployeeItemModel;
import com.tedros.person.report.model.EmployeeReportModel;
import com.tedros.person.server.service.EmployeeService;

@TSecurityInterceptor

@TBeanSecurity({@TBeanPolicie(id = DomainApp.PLACE_REPORT_FORM_ID, 
policie = { TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS })})
@Stateless(name="IEmployeeReportController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class EmployeeReportController implements IEmployeeReportController, ITSecurity {

	@EJB
	private EmployeeService serv;
	
	@EJB
	private ITSecurityController security;
	
	public EmployeeReportController() {
	}

	@Override	
	@TMethodSecurity({
		@TMethodPolicie(policie = {TActionPolicie.SEARCH})})
	public TResult<EmployeeReportModel> process(TAccessToken token, EmployeeReportModel m) {
		try{
			List<Employee> lst = serv.filterBy(m);
			if(lst!=null){
				List<EmployeeItemModel> itens = new ArrayList<>();
				for(Employee a : lst){
					itens.add(new EmployeeItemModel(a));
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
