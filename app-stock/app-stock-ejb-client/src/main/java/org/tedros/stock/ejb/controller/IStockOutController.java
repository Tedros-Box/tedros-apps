/**
 * 
 */
package org.tedros.stock.ejb.controller;

import javax.ejb.Remote;

import org.tedros.server.controller.ITSecureEjbController;

import org.tedros.stock.entity.StockOut;

/**
 * @author Davis Dun
 *
 */
@Remote
public interface IStockOutController extends ITSecureEjbController<StockOut> {

	static final String JNDI_NAME = "IStockOutControllerRemote";
		
}
