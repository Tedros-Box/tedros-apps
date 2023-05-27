/**
 * 
 */
package org.tedros.samples.module.order.assistant;

import org.tedros.sample.entity.OrderStatus;
import org.tedros.server.model.TJsonModel;

/**
 * @author Davis Gordon
 *
 */
public class OrderStatusJson extends TJsonModel<OrderStatus> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OrderStatusJson() {
		super.addData(new OrderStatus());
	}
	
	@Override
	public Class<OrderStatus> getModelType() {
		return OrderStatus.class;
	}

}
