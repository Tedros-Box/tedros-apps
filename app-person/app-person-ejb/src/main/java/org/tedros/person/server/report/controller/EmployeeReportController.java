package org.tedros.person.server.report.controller;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.tedros.person.domain.DomainApp;
import org.tedros.person.ejb.controller.IEmployeeReportController;
import org.tedros.person.model.Employee;
import org.tedros.person.report.model.EmployeeItemModel;
import org.tedros.person.report.model.EmployeeReportModel;
import org.tedros.person.server.service.EmployeeService;

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

@TBeanSecurity({@TBeanPolicie(id = DomainApp.EMPLOYEE_REPORT_FORM_ID, 
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
