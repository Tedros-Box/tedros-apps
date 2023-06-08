/**
 * 
 */
package org.tedros.stock.module.inventory.model;

import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TAutoCompleteEntity;
import org.tedros.fx.annotation.control.TCallbackFactory;
import org.tedros.fx.annotation.control.TCellFactory;
import org.tedros.fx.annotation.control.TCheckBoxField;
import org.tedros.fx.annotation.control.TDoubleField;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TTableColumn;
import org.tedros.fx.annotation.control.TTableView;
import org.tedros.fx.annotation.layout.THBox;
import org.tedros.fx.annotation.layout.THGrow;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.layout.TPriority;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TDetailTableViewPresenter;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.query.TCondition;
import org.tedros.fx.annotation.query.TQuery;
import org.tedros.fx.annotation.scene.control.TControl;
import org.tedros.fx.annotation.scene.control.TLabeled;
import org.tedros.fx.domain.TZeroValidation;
import org.tedros.fx.presenter.dynamic.TDynaPresenter;
import org.tedros.fx.presenter.entity.behavior.TDetailFieldBehavior;
import org.tedros.fx.presenter.entity.decorator.TDetailFieldDecorator;
import org.tedros.fx.presenter.model.TEntityModelView;
import org.tedros.server.query.TCompareOp;
import org.tedros.server.query.TLogicOp;
import org.tedros.stock.ejb.controller.IProductController;
import org.tedros.stock.entity.Product;
import org.tedros.stock.entity.StockConfigItem;
import org.tedros.stock.table.BooleanCallback;
import org.tedros.stock.table.ProductCallBack;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */
@TDetailTableViewPresenter(
	presenter = @TPresenter(	
			behavior = @TBehavior(type = TDetailFieldBehavior.class), 
			decorator = @TDecorator(type = TDetailFieldDecorator.class, 
				viewTitle=TUsualKey.PRODUCTS), type = TDynaPresenter.class),
	tableView = @TTableView(
		control=@TControl(maxHeight=250,parse = true),
		columns = {
		@TTableColumn(text = TUsualKey.PRODUCT, cellValue="product", 
			cellFactory=@TCellFactory(parse = true, 
				callBack=@TCallbackFactory(parse=true, value=ProductCallBack.class))), 
		@TTableColumn(text = TUsualKey.MINIMUN_AMOUNT, cellValue="minimumAmount"), 
		@TTableColumn(text = TUsualKey.NOTIFY_RESPONSABLE, cellValue="notify", 
			cellFactory=@TCellFactory(parse = true, 
				callBack=@TCallbackFactory(parse=true, value=BooleanCallback.class))), 
		@TTableColumn(text = TUsualKey.ALLOW_NEGATIVE_STOCK, cellValue="allowNegativeStock", 
			cellFactory=@TCellFactory(parse = true, 
				callBack=@TCallbackFactory(parse=true, value=BooleanCallback.class)))
		}))
public class ConfigItemMV extends TEntityModelView<StockConfigItem> {


	@TLabel(text=TUsualKey.PRODUCT)
	@TAutoCompleteEntity(required=true, 
	startSearchAt=3, showMaxItems=30,
	service = IProductController.JNDI_NAME,
	query = @TQuery(entity = Product.class, 
		condition = {
			@TCondition(field = "name", operator=TCompareOp.LIKE),
			@TCondition(logicOp=TLogicOp.OR, field = "code", operator=TCompareOp.LIKE)}))
	@THBox(	pane=@TPane(children={"product", "minimumAmount", "notify", "allowNegativeStock"}), 
	spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="product", priority=Priority.NEVER), 
			@TPriority(field="notify", priority=Priority.ALWAYS), 
			@TPriority(field="minimumAmount", priority=Priority.NEVER)}))
	private SimpleObjectProperty<Product> product;
	
	@TLabel(text=TUsualKey.MINIMUN_AMOUNT)
	@TDoubleField(zeroValidation=TZeroValidation.GREATHER_THAN_ZERO)
	private SimpleDoubleProperty minimumAmount;
	
	@TCheckBoxField(labeled = 
	@TLabeled(text=TUsualKey.NOTIFY_RESPONSABLE, parse = true))
	private SimpleObjectProperty<Boolean> notify;

	@TCheckBoxField(labeled = 
	@TLabeled(text=TUsualKey.ALLOW_NEGATIVE_STOCK, parse = true))
	private SimpleObjectProperty<Boolean> allowNegativeStock;
	
	public ConfigItemMV(StockConfigItem entity) {
		super(entity);
	}

	public SimpleObjectProperty<Product> getProduct() {
		return product;
	}

	public void setProduct(SimpleObjectProperty<Product> product) {
		this.product = product;
	}

	public SimpleDoubleProperty getMinimumAmount() {
		return minimumAmount;
	}

	public void setMinimumAmount(SimpleDoubleProperty minimumAmount) {
		this.minimumAmount = minimumAmount;
	}

	public SimpleObjectProperty<Boolean> getNotify() {
		return notify;
	}

	public void setNotify(SimpleObjectProperty<Boolean> notify) {
		this.notify = notify;
	}

	public SimpleObjectProperty<Boolean> getAllowNegativeStock() {
		return allowNegativeStock;
	}

	public void setAllowNegativeStock(SimpleObjectProperty<Boolean> allowNegativeStock) {
		this.allowNegativeStock = allowNegativeStock;
	}

}
