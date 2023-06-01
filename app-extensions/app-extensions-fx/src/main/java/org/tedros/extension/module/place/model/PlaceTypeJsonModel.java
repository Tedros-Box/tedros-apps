/**
 * 
 */
package org.tedros.extension.module.place.model;

import org.tedros.extension.model.PlaceType;
import org.tedros.server.model.TJsonModel;

/**
 * @author Davis Gordon
 *
 */
public class PlaceTypeJsonModel extends TJsonModel<PlaceType> {

	private static final long serialVersionUID = 6976551938275223485L;

	public PlaceTypeJsonModel() {
		super.addData(new PlaceType());
	}

	@Override
	public Class<PlaceType> getModelType() {
		return PlaceType.class;
	}

}
