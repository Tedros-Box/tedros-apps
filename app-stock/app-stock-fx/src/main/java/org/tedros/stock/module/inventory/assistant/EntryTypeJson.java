/**
 * 
 */
package org.tedros.stock.module.inventory.assistant;

import org.tedros.server.model.TJsonModel;
import org.tedros.stock.entity.EntryType;

/**
 * @author Davis Gordon
 *
 */
public class EntryTypeJson extends TJsonModel<EntryType> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4966876910558794226L;

	public EntryTypeJson() {
		super.addData(new EntryType());
	}
}
