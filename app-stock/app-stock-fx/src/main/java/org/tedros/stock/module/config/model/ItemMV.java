/**
 * 
 */
package org.tedros.stock.module.config.model;

import org.tedros.fx.presenter.model.TEntityModelView;
import org.tedros.stock.entity.Product;
import org.tedros.stock.entity.StockConfigItem;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * @author Davis Gordon
 *
 */
public class ItemMV extends TEntityModelView<StockConfigItem> {

	private SimpleObjectProperty<Product> product;
	
	private SimpleDoubleProperty amount;

	private SimpleDoubleProperty minimumAmount;
	
	public ItemMV(StockConfigItem entity) {
		super(entity);
	}

	public SimpleObjectProperty<Product> getProduct() {
		return product;
	}

	public void setProduct(SimpleObjectProperty<Product> product) {
		this.product = product;
	}

	public SimpleDoubleProperty getAmount() {
		return amount;
	}

	public void setAmount(SimpleDoubleProperty amount) {
		this.amount = amount;
	}

	public SimpleDoubleProperty getMinimumAmount() {
		return minimumAmount;
	}

	public void setMinimumAmount(SimpleDoubleProperty minimumAmount) {
		this.minimumAmount = minimumAmount;
	}

}
