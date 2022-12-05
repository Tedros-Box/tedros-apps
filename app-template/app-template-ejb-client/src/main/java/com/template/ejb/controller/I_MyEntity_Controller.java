/**
 * 
 */
package com.template.ejb.controller;

import javax.ejb.Remote;

import org.tedros.server.controller.ITSecureEjbController;

import com.template.entity._MyEntity_;

/**
 * @author myname
 *
 */
@Remote
public interface I_MyEntity_Controller extends ITSecureEjbController<_MyEntity_> {

	static final String JNDI_NAME = "I_MyEntity_ControllerRemote";
		
}
