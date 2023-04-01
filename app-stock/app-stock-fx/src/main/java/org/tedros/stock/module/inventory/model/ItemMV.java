/**
 * 
 */
package org.tedros.stock.module.inventory.model;

import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TAutoCompleteEntity;
import org.tedros.fx.annotation.control.TAutoCompleteEntity.TEntry;
import org.tedros.fx.annotation.control.TCallbackFactory;
import org.tedros.fx.annotation.control.TCellFactory;
import org.tedros.fx.annotation.control.TDoubleField;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TTableColumn;
import org.tedros.fx.annotation.control.TTableView;
import org.tedros.fx.annotation.layout.THBox;
import org.tedros.fx.annotation.layout.THGrow;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.layout.TPriority;
import org.tedros.fx.annotation.presenter.TDetailTableViewPresenter;
import org.tedros.fx.annotation.scene.control.TControl;
import org.tedros.fx.presenter.model.TEntityModelView;
import org.tedros.stock.STCKKey;
import org.tedros.stock.ejb.controller.IProductController;
import org.tedros.stock.entity.Product;
import org.tedros.stock.entity.StockConfigItem;
import org.tedros.stock.table.ProductCallBack;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */
@TDetailTableViewPresenter(tableView = @TTableView(
control=@TControl(maxHeight=250,parse = true),
columns = 
	{ @TTableColumn(text = STCKKey.PRODUCT, cellValue="product", 
			cellFactory=@TCellFactory(parse = true, 
			callBack=@TCallbackFactory(parse=true, value=ProductCallBack.class))), 
		@TTableColumn(text = TUsualKey.AMOUNT, cellValue="amount"), 
		@TTableColumn(text = STCKKey.MINIMUN_AMOUNT, cellValue="minimumAmount"), 
	}))
public class ItemMV extends TEntityModelView<StockConfigItem> {


	@TLabel(text=STCKKey.PRODUCT)
	@TAutoCompleteEntity(required=true,
	startSearchAt=2, showMaxItems=30,
	entries = @TEntry(entityType = Product.class, fields = {"code", "name"}, 
	service = IProductController.JNDI_NAME))
	@THBox(	pane=@TPane(children={"product", "amount", "minimumAmount"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="product", priority=Priority.NEVER), 
			@TPriority(field="amount", priority=Priority.NEVER), 
			@TPriority(field="minimumAmount", priority=Priority.NEVER)}))
	private SimpleObjectProperty<Product> product;
	
	@TLabel(text=TUsualKey.AMOUNT)
	@TDoubleField()
	private SimpleDoubleProperty amount;

	@TLabel(text=STCKKey.MINIMUN_AMOUNT)
	@TDoubleField()
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
