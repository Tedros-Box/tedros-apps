/**
 * 
 */
package org.tedros.sample.ejb.controller;

import org.tedros.sample.entity.Order;
import org.tedros.server.controller.ITEjbChartController;
import org.tedros.server.controller.ITSecureEjbController;

import jakarta.ejb.Remote;

/**
 * @author Davis
 *
 */
@Remote
public interface IOrderController extends ITSecureEjbController<Order>, ITEjbChartController {

	static final String JNDI_NAME = "IOrderControllerRemote";
		
}
