/**
 * 
 */
package org.tedros.stock.server.ejb.timer;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.ScheduleExpression;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.tedros.core.notify.model.TAction;
import org.tedros.core.notify.model.TNotify;
import org.tedros.core.notify.model.TState;
import org.tedros.extension.model.Contact;
import org.tedros.extension.model.ContactType;
import org.tedros.person.model.CostCenter;
import org.tedros.person.model.Person;
import org.tedros.stock.entity.StockConfig;
import org.tedros.stock.model.Inventory;
import org.tedros.stock.server.cdi.eao.InventoryEAO;
import org.tedros.stock.server.ejb.service.STCKService;

/**
 * @author Davis Gordon
 *
 */
@Startup
@Singleton
@Lock(LockType.READ) // allows timers to execute in parallel
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class InventoryTimer {
	
	private final static String DEFAULT = "INVENTORY_TIMER";
	
	@EJB
	private STCKService<CostCenter> ccServ;
	
	@EJB
	private STCKService<StockConfig> cfgServ;
	
	@EJB
	private STCKService<TNotify> nServ;
	
	@Inject
	private InventoryEAO invEao;;
	
	@Resource
    private TimerService timerService;
	
	@SuppressWarnings("unused")
	private Timer defaultTimer;
	
	
    @PostConstruct
    private void construct() {
        start();
    }
	/**
	 * @param interval
	 */
	public void start() {
        	final TimerConfig cfg = new TimerConfig(DEFAULT, false);
        	defaultTimer = timerService
        			.createCalendarTimer(new ScheduleExpression()
        					.minute("50").hour("05"), cfg);
	}

    @Timeout
    public void timeout(Timer timer) {
    	if(DEFAULT.equals(timer.getInfo())) {
    		try {
				List<CostCenter> lst = ccServ.listAll(CostCenter.class);
				if(lst!=null) {
					lst.forEach(cc->{
						StockConfig sc = new StockConfig();
						sc.setCostCenter(cc);
						List<StockConfig> cfgs=null;
						try {
							cfgs = cfgServ.findAll(sc);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						if(cfgs!=null) {
							cfgs.forEach(cfg->{
								Person emp = cfg.getResponsable();
								if(emp!=null) {
									Set<Contact> contacts = emp.getContacts();
									if(contacts!=null) {
										contacts.forEach(c->{
											if(c.getType().equals(ContactType.EMAIL) 
													&& c.getValue()!=null) {
												String email = c.getValue();
												cfg.getItems().forEach(i->{
													if(i.getNotify()!=null && i.getNotify()) {
														List<Inventory> invs = invEao.calculate(cfg.getLegalPerson(), cfg.getCostCenter(), null,
																Arrays.asList(i.getProduct()), null, null);
														if(invs.size()>0) {
															Inventory inv = invs.get(0);
															if(i.getMinimumAmount()>=inv.getAmount()) {
																
																StringBuilder sb = new StringBuilder();
																sb.append("The product ");
																sb.append(i.getProduct().toString()+" ");
																sb.append("reached the minimum amount in stock!");
																sb.append("<hr>");
																sb.append("Company: ").append(cc.getLegalPerson().getName()).append("<br>");
																sb.append("Cost center: ").append(cc.getName()).append("<br>");
																sb.append("Minimum amount acceptable: ").append(i.getMinimumAmount()).append("<br>");
																sb.append("Current amount in stock: ").append(inv.getAmount());
																TNotify n = new TNotify();
																n.setTo(email);
																n.setState(TState.QUEUED);
																n.setProcessedTime(new Date());
																n.setAction(TAction.NONE);
																n.addEventLog(TState.QUEUED, null);
																n.setSubject("[Tedros] Inventory info");
																n.setContent(sb.toString());
																try {
																	nServ.save(n);
																} catch (Exception e) {
																	e.printStackTrace();
																}
															}
														}
													}
												});
											}
										});
									}
								}
							});
						}
					});
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    }
}
