/**
 * 
 */
package org.tedros.stock.ejb.controller;

import org.tedros.server.controller.ITSecureEjbController;
import org.tedros.stock.entity.Product;

import jakarta.ejb.Remote;

/**
 * @author Davis Dun
 *
 */
@Remote
public interface IProductController extends ITSecureEjbController<Product> {

	static final String JNDI_NAME = "IProductControllerRemote";
		
}
