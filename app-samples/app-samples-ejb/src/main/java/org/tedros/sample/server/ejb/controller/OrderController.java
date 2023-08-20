/**
 * 
 */
package org.tedros.sample.server.ejb.controller;

import java.util.Date;
import java.util.Locale;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.tedros.person.model.CostCenter;
import org.tedros.person.model.Employee;
import org.tedros.person.model.LegalPerson;
import org.tedros.person.model.Person;
import org.tedros.sample.domain.DomainApp;
import org.tedros.sample.ejb.controller.IOrderController;
import org.tedros.sample.entity.Order;
import org.tedros.sample.entity.OrderStatus;
import org.tedros.sample.server.ejb.service.OrderService;
import org.tedros.server.controller.TParam;
import org.tedros.server.ejb.controller.ITSecurityController;
import org.tedros.server.ejb.controller.TSecureEjbController;
import org.tedros.server.model.ITChartModel;
import org.tedros.server.model.TChartModel;
import org.tedros.server.model.TChartSerie;
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
import org.tedros.server.service.ITEjbService;
import org.tedros.util.TDateUtil;

/**
 * The controller bean
 * 
 * @author Davis
 *
 */
@TSecurityInterceptor
@Stateless(name="IOrderController")
@TBeanSecurity({@TBeanPolicie(id = DomainApp.ORDER_FORM_ID, 
policie = { TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS })})
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class OrderController extends TSecureEjbController<Order> implements IOrderController, ITSecurity  {

	@EJB
	private OrderService serv;
	
	@EJB
	private ITSecurityController securityController;
	
	@Override
	public ITEjbService<Order> getService() {
		return serv;
	}
	
	@Override
	public ITSecurityController getSecurityController() {
		return securityController;
	}
	

	@Override
	@TMethodSecurity({
	@TMethodPolicie(policie = {TActionPolicie.SAVE, TActionPolicie.NEW})})
	public TResult<Order> save(TAccessToken token, Order e) {
		try{
			Order s = serv.save(token, e);
			processEntity(token, s);
			return new TResult<>(TState.SUCCESS, s);
		}catch(Exception ex){
			return processException(token, e, ex);
		}
	}

	@Override
	public TResult<ITChartModel> process(TAccessToken token, TParam... params) {
		
		Date begin=null; Date end = null;
		LegalPerson legalPerson = null; CostCenter costCenter = null;
		Person customer=null; Employee seller=null; OrderStatus status=null;
		for(TParam p : params) {
			if(p.getCode().equals("begin") && p.getValue()!=null)
				begin=(Date) p.getValue(); 
			if(p.getCode().equals("end") && p.getValue()!=null)
				end = (Date) p.getValue();
			if(p.getCode().equals("legalPerson") && p.getValue()!=null)
				legalPerson = (LegalPerson) p.getValue(); 
			if(p.getCode().equals("costCenter") && p.getValue()!=null)
				costCenter = (CostCenter) p.getValue();
			if(p.getCode().equals("customer") && p.getValue()!=null)
				customer=(Person) p.getValue(); 
			if(p.getCode().equals("seller") && p.getValue()!=null)
				seller=(Employee) p.getValue(); 
			if(p.getCode().equals("status") && p.getValue()!=null)
				status=(OrderStatus) p.getValue();
		}
		
		
		try {
			
			Map<Date, Long> map = serv.count(begin, end, legalPerson, costCenter, customer, seller, status);
		
			TChartModel<String, Long> cm = new TChartModel<>();
			TChartSerie<String,Long> s = new TChartSerie<String,Long>("#{view.orders}");
				cm.addSerie(s);
			map.keySet().forEach(e->{
				s.addData(TDateUtil.formatMediumDate(e, Locale.ENGLISH), map.get(e));
			});
			return new TResult<>(TState.SUCCESS, cm);
		} catch (Exception e) {
			return super.processException(token, null, e);
		}
		
	}
}
