/**
 * 
 */
package org.tedros.sample.ejb.controller;

import javax.ejb.Remote;

import org.tedros.sample.entity.SampleB;
import org.tedros.server.controller.ITSecureEjbController;

/**
 * @author Davis
 *
 */
@Remote
public interface ISampleBController extends ITSecureEjbController<SampleB> {

	static final String JNDI_NAME = "ISampleBControllerRemote";
		
}
