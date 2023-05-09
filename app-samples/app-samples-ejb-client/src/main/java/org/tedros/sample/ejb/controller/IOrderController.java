/**
 * 
 */
package org.tedros.sample.ejb.controller;

import javax.ejb.Remote;

import org.tedros.sample.entity.Order;
import org.tedros.server.controller.ITSecureEjbController;

/**
 * @author Davis
 *
 */
@Remote
public interface IOrderController extends ITSecureEjbController<Order> {

	static final String JNDI_NAME = "IOrderControllerRemote";
		
}
