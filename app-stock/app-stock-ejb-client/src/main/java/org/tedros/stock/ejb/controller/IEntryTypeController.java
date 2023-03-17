/**
 * 
 */
package org.tedros.stock.ejb.controller;

import javax.ejb.Remote;

import org.tedros.server.controller.ITSecureEjbController;

import org.tedros.stock.entity.EntryType;

/**
 * @author Davis Dun
 *
 */
@Remote
public interface IEntryTypeController extends ITSecureEjbController<EntryType> {

	static final String JNDI_NAME = "IEntryTypeControllerRemote";
		
}
