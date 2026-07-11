/**
 * 
 */
package org.tedros.stock.ejb.controller;

import org.tedros.server.controller.ITEjbReportController;
import org.tedros.stock.model.InventoryReportModel;

import jakarta.ejb.Remote;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IInventoryReportController extends ITEjbReportController<InventoryReportModel> {

	static final String JNDI_NAME = "IInventoryReportControllerRemote";
}
