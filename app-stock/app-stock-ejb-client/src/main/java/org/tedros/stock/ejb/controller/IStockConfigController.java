/**
 * 
 */
package org.tedros.stock.ejb.controller;

import org.tedros.server.controller.ITSecureEjbController;
import org.tedros.stock.entity.StockConfig;

import jakarta.ejb.Remote;

/**
 * @author Davis Dun
 *
 */
@Remote
public interface IStockConfigController extends ITSecureEjbController<StockConfig> {

	static final String JNDI_NAME = "IStockConfigControllerRemote";
		
}
