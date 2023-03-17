/**
 * 
 */
package org.tedros.stock.ejb.controller;

import javax.ejb.Remote;

import org.tedros.server.controller.ITSecureEjbController;

import org.tedros.stock.entity.Product;

/**
 * @author Davis Dun
 *
 */
@Remote
public interface IProductController extends ITSecureEjbController<Product> {

	static final String JNDI_NAME = "IProductControllerRemote";
		
}
