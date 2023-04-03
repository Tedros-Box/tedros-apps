/**
 * 
 */
package org.tedros.stock.table;

import org.tedros.core.TLanguage;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.control.TTableCell;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

/**
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public class BooleanCallback implements Callback<TableColumn, TableCell> {


	@Override
	public TableCell call(TableColumn c) {
		return new TTableCell<TableColumn, Boolean>() {
			public String processItem(Boolean item){
				return item==null
						? TLanguage.getInstance().getString(TUsualKey.NO)
								:  TLanguage.getInstance().getString(item ? TUsualKey.YES : TUsualKey.NO);
			}
		};
	}
}
