/**
 * 
 */
package org.tedros.stock.table;

import org.tedros.fx.model.TEntityModelView;
import org.tedros.stock.entity.Product;

import javafx.beans.property.SimpleStringProperty;

/**
 * @author Davis Gordon
 *
 */
public class ProductTV extends TEntityModelView<Product> {

	private SimpleStringProperty code;
	
	private SimpleStringProperty name;

	public ProductTV(Product entity) {
		super(entity);
		super.formatToString("%s %s", code, name);
	}

	public SimpleStringProperty getCode() {
		return code;
	}

	public void setCode(SimpleStringProperty code) {
		this.code = code;
	}

	public SimpleStringProperty getName() {
		return name;
	}

	public void setName(SimpleStringProperty name) {
		this.name = name;
	}

}
