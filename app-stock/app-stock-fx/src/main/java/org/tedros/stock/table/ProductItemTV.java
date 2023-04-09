/**
 * 
 */
package org.tedros.stock.table;

import org.tedros.fx.presenter.model.TModelView;
import org.tedros.stock.model.ProductItem;

import javafx.beans.property.SimpleStringProperty;

/**
 * @author Davis Gordon
 *
 */
public class ProductItemTV extends TModelView<ProductItem> {

	private SimpleStringProperty code;
	
	private SimpleStringProperty name;
	
	private SimpleStringProperty trademark;

	public ProductItemTV(ProductItem entity) {
		super(entity);
		super.formatToString("%s", name);
	}

	/**
	 * @return the trademark
	 */
	public SimpleStringProperty getTrademark() {
		return trademark;
	}

	/**
	 * @param trademark the trademark to set
	 */
	public void setTrademark(SimpleStringProperty trademark) {
		this.trademark = trademark;
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
