/**
 * 
 */
package org.tedros.sample.ejb.controller;

import javax.ejb.Remote;

import org.tedros.sample.entity.ProductPrice;
import org.tedros.server.controller.ITSecureEjbController;

/**
 * @author Davis
 *
 */
@Remote
public interface IProductPriceController extends ITSecureEjbController<ProductPrice> {

	static final String JNDI_NAME = "IProductPriceControllerRemote";
		
}
