package org.tedros.samples.module.order.action;

import java.util.Date;
import java.util.List;

import javax.naming.NamingException;

import org.tedros.core.context.TLoader;
import org.tedros.core.context.TModuleContext;
import org.tedros.core.context.TedrosAppManager;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.context.TedrosModuleLoader;
import org.tedros.core.message.TMessage;
import org.tedros.core.message.TMessageType;
import org.tedros.core.service.remote.ServiceLocator;
import org.tedros.fx.builder.TBaseEventHandlerBuilder;
import org.tedros.fx.presenter.dynamic.TDynaPresenter;
import org.tedros.fx.presenter.entity.behavior.TMasterCrudViewBehavior;
import org.tedros.sample.ejb.controller.IOrderController;
import org.tedros.sample.entity.Order;
import org.tedros.sample.entity.Sale;
import org.tedros.samples.SmplsKey;
import org.tedros.samples.module.order.OrderModule;
import org.tedros.samples.module.order.model.OrderMV;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class CreateSaleEvent extends TBaseEventHandlerBuilder<ActionEvent> {

	@SuppressWarnings("unchecked")
	@Override
	public EventHandler<ActionEvent> build() {
		return ev->{
			super.getComponentDescriptor();
			TDynaPresenter<OrderMV> p = (TDynaPresenter<OrderMV>) super.getComponentDescriptor().getForm().gettPresenter();
			TMasterCrudViewBehavior<OrderMV, Order> b = 
					(TMasterCrudViewBehavior<OrderMV, Order>) p.getBehavior();
			Order order = b.getModelView().getEntity();
			if(order.isNew()) {
				b.addMessage(new TMessage(TMessageType.WARNING, SmplsKey.MSG_SAVE_FIRST));
				return;
			}
			Sale sale = order.getSale();
			if(sale==null) {
				TModuleContext ct = TedrosAppManager.getInstance().getModuleContext(OrderModule.class);
				
				sale = new Sale(new Date(), order);
				sale.setIntegratedAppUUID(ct.getModuleDescriptor().getApplicationUUID());
				sale.setIntegratedModulePath(ct.getModuleDescriptor().getPathKeys());
				sale.setIntegratedViewName(SmplsKey.VIEW_ORDERS);
				sale.setIntegratedDate(new Date());
				sale.setIntegratedEntityId(order.getId());
				sale.setIntegratedModelView(b.getModelView().getClass().getName());
				
				saveAndLoadInModule(b, order);
			}else {
				if(sale.isNew())
					saveAndLoadInModule(b, order);
				else
					loadInModule(sale.getId());
			}
		};
	}

	private void saveAndLoadInModule(TMasterCrudViewBehavior<OrderMV, Order> b, Order order) {
		ServiceLocator loc = ServiceLocator.getInstance();
		try {
			IOrderController serv = loc.lookup(IOrderController.JNDI_NAME);
			TResult<Order> res = serv.save(TedrosContext.getLoggedUser().getAccessToken(), order);
			if(res.getState().equals(TState.SUCCESS)) {
				Order saved = res.getValue();
				b.getModelView().reload(saved);
				loadInModule(saved.getSale().getId());
			}else {
				order.setSale(null);
				b.addMessage(new TMessage(TMessageType.ERROR, res.getMessage()));
			}
		} catch (NamingException e) {
			e.printStackTrace();
			order.setSale(null);
			b.addMessage(new TMessage(TMessageType.ERROR, e.getMessage()));
		}finally {
			loc.close();
		}
	}

	private void loadInModule(Long id) {
		Sale sale = new Sale();
		sale.setId(id);
		List<TLoader> l = TedrosModuleLoader.getInstance()
			.getLoader(sale);
		l.get(0).loadInModule();
	}

}
