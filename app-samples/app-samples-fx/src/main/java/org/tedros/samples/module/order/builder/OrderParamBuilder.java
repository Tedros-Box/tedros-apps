/**
 * 
 */
package org.tedros.samples.module.order.builder;

import java.util.Date;

import org.apache.commons.lang3.ArrayUtils;
import org.tedros.core.context.TedrosAppManager;
import org.tedros.fx.builder.TParamBuilder;
import org.tedros.fx.presenter.dynamic.TDynaPresenter;
import org.tedros.sample.entity.Order;
import org.tedros.samples.module.order.model.OrderChartMV;
import org.tedros.server.controller.TParam;

import javafx.beans.property.SimpleObjectProperty;

/**
 * @author Davis Gordon
 *
 */
public class OrderParamBuilder extends TParamBuilder {

	/**
	 * 
	 */
	public OrderParamBuilder() {
	}

	/* (non-Javadoc)
	 * @see org.tedros.fx.builder.ITGenericBuilder#build()
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public TParam[] build() {
		
		TDynaPresenter p = (TDynaPresenter) TedrosAppManager.getInstance().getCurrentView().gettPresenter();
		
		
		OrderChartMV mv = (OrderChartMV) p.getBehavior().getModelView();// super.getComponentDescriptor().getModelView();
		Order m = mv.getModel();
		
		TParam[] arr = new TParam[] {};
		
		SimpleObjectProperty<Date> begin = mv.getProperty("begin");
		SimpleObjectProperty<Date> end = mv.getProperty("end");
		
		if(begin.getValue()!=null)
			arr = ArrayUtils.add(arr, new TParam("begin", begin.getValue()));

		if(end.getValue()!=null)
			arr = ArrayUtils.add(arr, new TParam("end", end.getValue()));

		if(m.getStatus()!=null)
			arr = ArrayUtils.add(arr, new TParam("status", m.getStatus()));

		if(m.getCustomer()!=null)
			arr = ArrayUtils.add(arr, new TParam("customer", m.getCustomer()));

		if(m.getLegalPerson()!=null)
			arr = ArrayUtils.add(arr, new TParam("legalPerson", m.getLegalPerson()));

		if(m.getCostCenter()!=null)
			arr = ArrayUtils.add(arr, new TParam("costCenter", m.getCostCenter()));

		if(m.getSeller()!=null)
			arr = ArrayUtils.add(arr, new TParam("seller", m.getSeller()));
		
		return arr;
	}

}
