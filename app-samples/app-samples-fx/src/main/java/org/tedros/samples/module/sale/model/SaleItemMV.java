/**
 * 
 */
package org.tedros.samples.module.sale.model;

import java.math.BigDecimal;

import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TAutoCompleteEntity;
import org.tedros.fx.annotation.control.TAutoCompleteEntity.TEntry;
import org.tedros.fx.annotation.control.TBigDecimalField;
import org.tedros.fx.annotation.control.TCallbackFactory;
import org.tedros.fx.annotation.control.TCellFactory;
import org.tedros.fx.annotation.control.TDoubleField;
import org.tedros.fx.annotation.control.TIntegerField;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TShowField;
import org.tedros.fx.annotation.control.TTableColumn;
import org.tedros.fx.annotation.control.TTableView;
import org.tedros.fx.annotation.control.TTrigger;
import org.tedros.fx.annotation.form.TSetting;
import org.tedros.fx.annotation.layout.THBox;
import org.tedros.fx.annotation.layout.THGrow;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.layout.TPriority;
import org.tedros.fx.annotation.presenter.TDetailTableViewPresenter;
import org.tedros.fx.annotation.scene.control.TControl;
import org.tedros.fx.presenter.model.TEntityModelView;
import org.tedros.sample.entity.SaleItem;
import org.tedros.samples.SmplsKey;
import org.tedros.samples.module.sale.setting.SaleItemSetting;
import org.tedros.samples.module.sale.trigger.SetPriceTrigger;
import org.tedros.stock.STCKKey;
import org.tedros.stock.ejb.controller.IProductController;
import org.tedros.stock.entity.Product;
import org.tedros.stock.table.ProductCallBack;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */
@TSetting(SaleItemSetting.class)
@TDetailTableViewPresenter(tableView = @TTableView(
control=@TControl(parse = true),
columns = 
	{ @TTableColumn(text = STCKKey.PRODUCT, cellValue="product", 
			cellFactory=@TCellFactory(parse = true, 
			callBack=@TCallbackFactory(parse=true, value=ProductCallBack.class))), 
		@TTableColumn(text = SmplsKey.UNIT_PRICE, cellValue="unitPrice"), 
		@TTableColumn(text = TUsualKey.AMOUNT, cellValue="amount"), 
		@TTableColumn(text = TUsualKey.DISCOUNT, cellValue="rebate"), 
		@TTableColumn(text = "Total", cellValue="total")
	}))
public class SaleItemMV extends TEntityModelView<SaleItem> {

	@TLabel(text=STCKKey.PRODUCT)
	@TAutoCompleteEntity(required=true, control=@TControl(maxWidth=250, parse = true),
		entries = @TEntry(entityType = Product.class, 
			fields = { "code", "name" }, service = IProductController.JNDI_NAME))
	@TTrigger(triggerClass = SetPriceTrigger.class, targetFieldName="unitPrice") // Trigger example
	@THBox(	spacing=10, fillHeight=true,
		pane=@TPane(children={"product", "unitPrice", "amount", "rebate", "total"}), 
	hgrow=@THGrow(priority={@TPriority(field="product", priority=Priority.NEVER), 
			@TPriority(field="unitPrice", priority=Priority.NEVER),
		@TPriority(field="amount", priority=Priority.NEVER),
		@TPriority(field="total", priority=Priority.NEVER),
		@TPriority(field="rebate", priority=Priority.NEVER)}))
	private SimpleObjectProperty<Product> product;
	
	@TLabel(text=SmplsKey.UNIT_PRICE)
	@TBigDecimalField(control=@TControl(maxWidth=250, parse = true))
	private SimpleObjectProperty<BigDecimal> unitPrice;
	
	@TLabel(text=TUsualKey.AMOUNT)
	@TIntegerField
	private SimpleIntegerProperty amount;
	
	@TLabel(text=TUsualKey.DISCOUNT)
	@TDoubleField
	private SimpleDoubleProperty rebate;

	@TLabel(text="Total")
	@TShowField
	private SimpleObjectProperty<BigDecimal> total;
	
	public SaleItemMV(SaleItem entity) {
		super(entity);
		// Important, remember to register 
		// the fields which don't exist
		// in the entity. Some components like
		// TableView only can see registered 
		// property fields.
		super.registerProperty("total", total);
	}

	/**
	 * @return the product
	 */
	public SimpleObjectProperty<Product> getProduct() {
		return product;
	}

	/**
	 * @param product the product to set
	 */
	public void setProduct(SimpleObjectProperty<Product> product) {
		this.product = product;
	}

	/**
	 * @return the unitPrice
	 */
	public SimpleObjectProperty<BigDecimal> getUnitPrice() {
		return unitPrice;
	}

	/**
	 * @param unitPrice the unitPrice to set
	 */
	public void setUnitPrice(SimpleObjectProperty<BigDecimal> unitPrice) {
		this.unitPrice = unitPrice;
	}

	/**
	 * @return the amount
	 */
	public SimpleIntegerProperty getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(SimpleIntegerProperty amount) {
		this.amount = amount;
	}

	/**
	 * @return the rebate
	 */
	public SimpleDoubleProperty getRebate() {
		return rebate;
	}

	/**
	 * @param rebate the rebate to set
	 */
	public void setRebate(SimpleDoubleProperty rebate) {
		this.rebate = rebate;
	}

	/**
	 * @return the total
	 */
	public SimpleObjectProperty<BigDecimal> getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(SimpleObjectProperty<BigDecimal> total) {
		this.total = total;
	}

}
