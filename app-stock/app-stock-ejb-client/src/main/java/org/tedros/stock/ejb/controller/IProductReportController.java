/**
 * 
 */
package org.tedros.stock.ejb.controller;

import org.tedros.server.controller.ITEjbReportController;
import org.tedros.stock.model.ProductReportModel;

import jakarta.ejb.Remote;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IProductReportController extends ITEjbReportController<ProductReportModel> {

	static final String JNDI_NAME = "IProductReportControllerRemote";
}
