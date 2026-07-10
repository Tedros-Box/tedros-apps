/**
 * 
 */
package org.tedros.sample.ejb.controller;

import org.tedros.sample.entity.ProductPrice;
import org.tedros.server.controller.ITSecureEjbController;

import jakarta.ejb.Remote;

/**
 * @author Davis
 *
 */
@Remote
public interface IProductPriceController extends ITSecureEjbController<ProductPrice> {

	static final String JNDI_NAME = "IProductPriceControllerRemote";
		
}
