/**
 * 
 */
package org.tedros.stock.ejb.controller;

import javax.ejb.Remote;

import org.tedros.server.controller.ITSecureEjbController;

import org.tedros.stock.entity.OutType;

/**
 * @author Davis Dun
 *
 */
@Remote
public interface IOutTypeController extends ITSecureEjbController<OutType> {

	static final String JNDI_NAME = "IOutTypeControllerRemote";
		
}
