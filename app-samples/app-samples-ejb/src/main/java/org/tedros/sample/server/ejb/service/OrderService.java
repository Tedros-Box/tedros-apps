/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package org.tedros.sample.server.ejb.service;

import java.util.Date;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.tedros.person.model.CostCenter;
import org.tedros.person.model.Employee;
import org.tedros.person.model.LegalPerson;
import org.tedros.person.model.Person;
import org.tedros.sample.entity.Order;
import org.tedros.sample.entity.OrderStatus;
import org.tedros.sample.server.cdi.bo.OrderBO;
import org.tedros.server.cdi.bo.ITGenericBO;
import org.tedros.server.ejb.service.TEjbService;
import org.tedros.server.security.TAccessToken;

/**
 * The transact service bean 
 *
 * @author Davis
 *
 */
@LocalBean
@Stateless(name="OrderService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class OrderService extends TEjbService<Order>  {
	
	@Inject
	private OrderBO bo;
	
	@Override
	public ITGenericBO<Order> getBussinesObject() {
		return bo;
	}
	 
	public Map<Date, Long> count(Date begin, Date end,
			LegalPerson legalPerson, CostCenter costCenter,
			Person customer, Employee seller, OrderStatus status){
		return bo.count(begin, end, legalPerson, costCenter, customer, seller, status);
	}
	
	
	@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
	public Order save(TAccessToken token, Order entidade) throws Exception {
		return bo.save(token, entidade);
	}
	
	
}
