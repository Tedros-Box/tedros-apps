/**
 * 
 */
package org.tedros.stock.ejb.controller;

import jakarta.ejb.Remote;

import org.tedros.server.controller.ITSecureEjbController;
import org.tedros.stock.entity.StockEvent;

/**
 * @author Davis Dun
 *
 */
@Remote
public interface IStockEventController extends ITSecureEjbController<StockEvent> {

	static final String JNDI_NAME = "IStockEventControllerRemote";
		
}
