/**
 * 
 */
package org.tedros.stock.ejb.controller;

import javax.ejb.Remote;

import org.tedros.server.controller.ITSecureEjbController;

import org.tedros.stock.entity.StockEntry;

/**
 * @author Davis Dun
 *
 */
@Remote
public interface IStockEntryController extends ITSecureEjbController<StockEntry> {

	static final String JNDI_NAME = "IStockEntryControllerRemote";
		
}
