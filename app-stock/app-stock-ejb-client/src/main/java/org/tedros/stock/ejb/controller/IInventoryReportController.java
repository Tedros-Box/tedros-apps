/**
 * 
 */
package org.tedros.stock.ejb.controller;

import javax.ejb.Remote;

import org.tedros.server.controller.ITEjbReportController;
import org.tedros.stock.model.InventoryReportModel;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IInventoryReportController extends ITEjbReportController<InventoryReportModel> {

	static final String JNDI_NAME = "IInventoryReportControllerRemote";
}
