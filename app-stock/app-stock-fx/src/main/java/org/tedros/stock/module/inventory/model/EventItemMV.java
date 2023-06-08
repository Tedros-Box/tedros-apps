/**
 * 
 */
package org.tedros.stock.module.inventory.model;

import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TAutoCompleteEntity;
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
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TDetailTableViewPresenter;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.query.TCondition;
import org.tedros.fx.annotation.query.TQuery;
import org.tedros.fx.annotation.scene.control.TControl;
import org.tedros.fx.presenter.dynamic.TDynaPresenter;
import org.tedros.fx.presenter.entity.behavior.TDetailFieldBehavior;
import org.tedros.fx.presenter.entity.decorator.TDetailFieldDecorator;
import org.tedros.fx.presenter.model.TEntityModelView;
import org.tedros.server.query.TCompareOp;
import org.tedros.server.query.TLogicOp;
import org.tedros.stock.ejb.controller.IProductController;
import org.tedros.stock.entity.Product;
import org.tedros.stock.entity.StockItem;
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
		control=@TControl(parse = true),
		columns = {
			@TTableColumn(text = TUsualKey.PRODUCT, cellValue="product", 
				cellFactory=@TCellFactory(parse = true, 
					callBack=@TCallbackFactory(parse=true, value=ProductCallBack.class))), 
			@TTableColumn(text = TUsualKey.AMOUNT, cellValue="amount")
		}))
public class EventItemMV extends TEntityModelView<StockItem> {


	@TLabel(text=TUsualKey.PRODUCT)
	@TAutoCompleteEntity(required=true, 
	control=@TControl(maxWidth=250, parse = true),
	startSearchAt=3, showMaxItems=30,
	service = IProductController.JNDI_NAME,
	query = @TQuery(entity = Product.class, 
		condition = {
			@TCondition(field = "name", operator=TCompareOp.LIKE),
			@TCondition(logicOp=TLogicOp.OR, field = "code", operator=TCompareOp.LIKE)}))
	@THBox(	pane=@TPane(children={"product", "amount"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="product", priority=Priority.NEVER), 
			@TPriority(field="amount", priority=Priority.NEVER)}))
	private SimpleObjectProperty<Product> product;
	
	@TLabel(text=TUsualKey.AMOUNT)
	@TDoubleField()
	private SimpleDoubleProperty amount;

	public EventItemMV(StockItem entity) {
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
}
