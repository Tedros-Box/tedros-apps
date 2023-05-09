/**
 * 
 */
package org.tedros.samples.module.sale.assistant;

import org.tedros.sample.entity.SaleStatus;
import org.tedros.server.model.TJsonModel;

/**
 * @author Davis Gordon
 *
 */
public class SaleStatusJson extends TJsonModel<SaleStatus> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SaleStatusJson() {
		super.addData(new SaleStatus());
	}
	
	@Override
	public Class<SaleStatus> getModelType() {
		return SaleStatus.class;
	}

}
