/**
 * 
 */
package org.tedros.stock.table;

import org.tedros.fx.control.TTableCell;
import org.tedros.stock.entity.Product;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

/**
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public class ProductCallBack implements Callback<TableColumn, TableCell> {

	@Override
	public TableCell call(TableColumn tipo) {
		return new TTableCell<TableColumn, Product>() {
			public String processItem(Product item){
				return item.toString();
			}
		};
	}
	
}
