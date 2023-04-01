/**
 * 
 */
package org.tedros.stock.server.ejb.controller;

import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.tedros.server.ejb.controller.ITSecurityController;
import org.tedros.server.ejb.controller.TSecureEjbController;
import org.tedros.server.result.TResult;
import org.tedros.server.security.ITSecurity;
import org.tedros.server.security.TAccessPolicie;
import org.tedros.server.security.TAccessToken;
import org.tedros.server.security.TBeanPolicie;
import org.tedros.server.security.TBeanSecurity;
import org.tedros.server.security.TSecurityInterceptor;
import org.tedros.server.service.ITEjbService;
import org.tedros.stock.domain.DomainApp;
import org.tedros.stock.ejb.controller.ICostCenterController;
import org.tedros.stock.entity.CostCenter;
import org.tedros.stock.model.Inventory;
import org.tedros.stock.server.ejb.service.InventoryService;
import org.tedros.stock.server.ejb.service.STCKService;

/**
 * The controller bean
 * 
 * @author Davis Dun
 *
 */
@TSecurityInterceptor
@Stateless(name="ICostCenterController")
@TBeanSecurity({@TBeanPolicie(id = DomainApp.COST_CENTER_FORM_ID, 
policie = { TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS })})
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TCostCenterController extends TSecureEjbController<CostCenter> implements ICostCenterController, ITSecurity  {

	@EJB
	private STCKService<CostCenter> serv;
	
	@EJB
	private ITSecurityController securityController;
	
	@EJB private InventoryService s;
	
	@Override
	public ITEjbService<CostCenter> getService() {
		return serv;
	}
	
	@Override
	public ITSecurityController getSecurityController() {
		return securityController;
	}
	
	@Override
	public TResult<Map<String, Object>> pageAll(TAccessToken token, CostCenter entity, int firstResult, int maxResult,
			boolean orderByAsc) {
		CostCenter cc = new CostCenter();
		cc.setId(1L);
		List<Inventory> l;
		try {
			 l = s.calculate(serv.findById(cc));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return super.pageAll(token, entity, firstResult, maxResult, orderByAsc);
	}
}
