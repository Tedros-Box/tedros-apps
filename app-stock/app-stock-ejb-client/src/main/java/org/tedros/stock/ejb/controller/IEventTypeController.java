/**
 * 
 */
package org.tedros.stock.ejb.controller;

import jakarta.ejb.Remote;

import org.tedros.server.controller.ITSecureEjbController;
import org.tedros.stock.entity.EventType;

/**
 * @author Davis Dun
 *
 */
@Remote
public interface IEventTypeController extends ITSecureEjbController<EventType> {

	static final String JNDI_NAME = "IEventTypeControllerRemote";
		
}
