/**
 * 
 */
package org.tedros.it.tools.ejb.controller;

import org.tedros.it.tools.entity.GmudReview;
import org.tedros.server.controller.ITSecureEjbController;

import jakarta.ejb.Remote;

/**
 * @author Davis Dun
 *
 */
@Remote
public interface IGmudReviewController extends ITSecureEjbController<GmudReview> {

	static final String JNDI_NAME = "IGmudReviewControllerRemote";
		
}
