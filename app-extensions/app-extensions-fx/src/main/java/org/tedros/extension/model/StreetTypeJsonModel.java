/**
 * 
 */
package org.tedros.extension.model;

import org.tedros.server.model.TJsonModel;

/**
 * @author Davis Gordon
 *
 */
public class StreetTypeJsonModel extends TJsonModel<StreetType> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4160453169402868218L;

	/**
	 * 
	 */
	public StreetTypeJsonModel() {
		super.addData(new StreetType());
	}

	/* (non-Javadoc)
	 * @see org.tedros.server.model.TJsonModel#getModelType()
	 */
	@Override
	public Class<StreetType> getModelType() {
		return StreetType.class;
	}

}
