/**
 * 
 */
package org.tedros.sample.ejb.controller;

import javax.ejb.Remote;

import org.tedros.sample.entity.SampleA;
import org.tedros.server.controller.ITSecureEjbController;

/**
 * @author Davis
 *
 */
@Remote
public interface ISampleAController extends ITSecureEjbController<SampleA> {

	static final String JNDI_NAME = "ISampleAControllerRemote";
		
}
