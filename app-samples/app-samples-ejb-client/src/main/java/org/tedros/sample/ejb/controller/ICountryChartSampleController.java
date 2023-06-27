/**
 * 
 */
package org.tedros.sample.ejb.controller;

import javax.ejb.Remote;

import org.tedros.server.controller.ITEjbChartController;

/**
 * @author Davis
 *
 */
@Remote
public interface ICountryChartSampleController extends ITEjbChartController {

	static final String JNDI_NAME = "ICountryChartSampleController";
		
}
