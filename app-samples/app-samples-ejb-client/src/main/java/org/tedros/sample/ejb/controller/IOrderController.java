/**
 * 
 */
package org.tedros.sample.ejb.controller;

import javax.ejb.Remote;

import org.tedros.sample.entity.Order;
import org.tedros.server.controller.ITEjbChartController;
import org.tedros.server.controller.ITSecureEjbController;

/**
 * @author Davis
 *
 */
@Remote
public interface IOrderController extends ITSecureEjbController<Order>, ITEjbChartController {

	static final String JNDI_NAME = "IOrderControllerRemote";
		
}
