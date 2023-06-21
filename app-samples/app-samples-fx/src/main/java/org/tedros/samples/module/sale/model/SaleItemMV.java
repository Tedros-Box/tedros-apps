/**
 * 
 */
package org.tedros.samples.module.sale.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TAutoCompleteEntity;
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
import org.tedros.fx.annotation.query.TCondition;
import org.tedros.fx.annotation.query.TQuery;
import org.tedros.fx.annotation.scene.control.TControl;
import org.tedros.fx.domain.TValidateNumber;
import org.tedros.fx.model.TEntityModelView;
import org.tedros.sample.entity.SaleItem;
import org.tedros.samples.module.sale.setting.SaleItemSetting;
import org.tedros.samples.module.sale.trigger.SetPriceTrigger;
import org.tedros.server.query.TCompareOp;
import org.tedros.server.query.TLogicOp;
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
@TDetailTableViewPresenter(
	tableView = @TTableView(
		control=@TControl(maxHeight=300, parse = true),
		columns = 
		{ @TTableColumn(text = TUsualKey.PRODUCT, cellValue="product", 
				cellFactory=@TCellFactory(parse = true, 
				callBack=@TCallbackFactory(parse=true, value=ProductCallBack.class))), 
			@TTableColumn(text = TUsualKey.UNIT_PRICE, cellValue="unitPrice"), 
			@TTableColumn(text = TUsualKey.AMOUNT, cellValue="amount"), 
			@TTableColumn(text = TUsualKey.DISCOUNT, cellValue="rebate"), 
			@TTableColumn(text = "Total", cellValue="total")
		}))
public class SaleItemMV extends TEntityModelView<SaleItem> {

	@TLabel(text=TUsualKey.PRODUCT)
	@TAutoCompleteEntity(required=true, 
	control=@TControl(maxWidth=250, parse = true),
	startSearchAt=3, showMaxItems=30,
	service = IProductController.JNDI_NAME,
	query = @TQuery(entity = Product.class, 
		condition = {
			@TCondition(field = "name", operator=TCompareOp.LIKE),
			@TCondition(logicOp=TLogicOp.OR, field = "code", operator=TCompareOp.LIKE)}))
	@TTrigger(type = SetPriceTrigger.class, targetFieldName="unitPrice") // Trigger example
	@THBox(	spacing=10, fillHeight=true,
		pane=@TPane(children={"product", "unitPrice", "amount", "rebate", "total"}), 
	hgrow=@THGrow(priority={@TPriority(field="product", priority=Priority.NEVER), 
			@TPriority(field="unitPrice", priority=Priority.NEVER),
		@TPriority(field="amount", priority=Priority.NEVER),
		@TPriority(field="total", priority=Priority.NEVER),
		@TPriority(field="rebate", priority=Priority.NEVER)}))
	private SimpleObjectProperty<Product> product;
	
	@TLabel(text=TUsualKey.UNIT_PRICE)
	@TBigDecimalField(validate=TValidateNumber.GREATHER_THAN_ZERO, 
	control=@TControl(maxWidth=80, parse = true))
	private SimpleObjectProperty<BigDecimal> unitPrice;
	
	@TLabel(text=TUsualKey.AMOUNT)
	@TIntegerField(validate=TValidateNumber.GREATHER_THAN_ZERO, 
	control=@TControl(maxWidth=80, parse = true))
	private SimpleIntegerProperty amount;
	
	@TLabel(text=TUsualKey.DISCOUNT)
	@TDoubleField(control=@TControl(maxWidth=80, parse = true))
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
		calcTotal();
	}
	
	public void reload(SaleItem entity) {
		super.reload(entity);
		calcTotal();
	}
	
	public void calcTotal() {

		BigDecimal t = unitPrice.getValue()!=null ? unitPrice.getValue() : BigDecimal.ZERO;
		Integer q = amount.getValue()!=null ? amount.getValue() : 0;
		Double r = rebate.getValue()!=null ? rebate.getValue() : 0;
		
		BigDecimal m = t.multiply(new BigDecimal(q));
		BigDecimal v = m.multiply(new BigDecimal(r));
		v = v.divide(new BigDecimal(100), 2, RoundingMode.UNNECESSARY);
		m = m.subtract(v);

		total.setValue(m);
	}
	
	/**
	 * @return the unitPrice
	 */
	public SimpleObjectProperty<BigDecimal> getUnitPrice() {
		return unitPrice;
	}

	/**
	 * @return the total
	 */
	public SimpleObjectProperty<BigDecimal> getTotal() {
		return total;
	}


}
