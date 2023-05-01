/**
 * 
 */
package org.tedros.stock.module.inventory.assistant;

import org.tedros.server.model.TJsonModel;
import org.tedros.stock.entity.OutType;

/**
 * @author Davis Gordon
 *
 */
public class OutTypeJson extends TJsonModel<OutType> {

	private static final long serialVersionUID = 1L;

	public OutTypeJson() {
		super.addData(new OutType());
	}

	@Override
	public Class<OutType> getModelType() {
		return OutType.class;
	}
}
