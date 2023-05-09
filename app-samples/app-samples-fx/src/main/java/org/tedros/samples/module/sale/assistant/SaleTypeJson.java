/**
 * 
 */
package org.tedros.samples.module.sale.assistant;

import org.tedros.sample.entity.SaleType;
import org.tedros.server.model.TJsonModel;

/**
 * @author Davis Gordon
 *
 */
public class SaleTypeJson extends TJsonModel<SaleType> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SaleTypeJson() {
		super.addData(new SaleType());
	}
	
	@Override
	public Class<SaleType> getModelType() {
		return SaleType.class;
	}

}
