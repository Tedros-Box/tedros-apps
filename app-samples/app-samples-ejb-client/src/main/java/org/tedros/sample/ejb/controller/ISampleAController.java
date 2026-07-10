/**
 * 
 */
package org.tedros.sample.ejb.controller;

import org.tedros.sample.entity.SampleA;
import org.tedros.server.controller.ITSecureEjbController;

import jakarta.ejb.Remote;

/**
 * @author Davis
 *
 */
@Remote
public interface ISampleAController extends ITSecureEjbController<SampleA> {

	static final String JNDI_NAME = "ISampleAControllerRemote";
		
}
