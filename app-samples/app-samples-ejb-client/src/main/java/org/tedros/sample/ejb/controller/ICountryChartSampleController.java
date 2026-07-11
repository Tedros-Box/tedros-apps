/**
 * 
 */
package org.tedros.sample.ejb.controller;

import org.tedros.server.controller.ITEjbChartController;

import jakarta.ejb.Remote;

/**
 * @author Davis
 *
 */
@Remote
public interface ICountryChartSampleController extends ITEjbChartController {

	static final String JNDI_NAME = "ICountryChartSampleController";
		
}
