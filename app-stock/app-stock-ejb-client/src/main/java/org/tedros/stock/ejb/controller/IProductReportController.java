/**
 * 
 */
package org.tedros.stock.ejb.controller;

import javax.ejb.Remote;

import org.tedros.server.controller.ITEjbReportController;
import org.tedros.stock.model.ProductReportModel;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IProductReportController extends ITEjbReportController<ProductReportModel> {

	static final String JNDI_NAME = "IProductReportControllerRemote";
}
