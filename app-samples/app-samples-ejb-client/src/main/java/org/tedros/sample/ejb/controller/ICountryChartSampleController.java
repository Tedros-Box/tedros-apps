/**
 * 
 */
package org.tedros.sample.ejb.controller;

import jakarta.ejb.Remote;

import org.tedros.server.controller.ITEjbChartController;

/**
 * @author Davis
 *
 */
@Remote
public interface ICountryChartSampleController extends ITEjbChartController {

	static final String JNDI_NAME = "ICountryChartSampleController";
		
}
