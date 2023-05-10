package org.tedros.samples.module.price.model;

import java.math.BigDecimal;

import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TAutoCompleteEntity;
import org.tedros.fx.annotation.control.TAutoCompleteEntity.TEntry;
import org.tedros.fx.annotation.control.TBigDecimalField;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TListViewPresenter;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.process.TEjbService;
import org.tedros.fx.annotation.scene.control.TControl;
import org.tedros.fx.annotation.view.TOption;
import org.tedros.fx.annotation.view.TPaginator;
import org.tedros.fx.presenter.model.TEntityModelView;
import org.tedros.sample.domain.DomainApp;
import org.tedros.sample.ejb.controller.IProductPriceController;
import org.tedros.sample.entity.ProductPrice;
import org.tedros.samples.SmplsKey;
import org.tedros.stock.ejb.controller.IProductController;
import org.tedros.stock.entity.Product;

import javafx.beans.property.SimpleObjectProperty;


@TForm(name = SmplsKey.FORM_PRICE, showBreadcrumBar=true, scroll=false)
@TEjbService(serviceName = IProductPriceController.JNDI_NAME, model=ProductPrice.class)
@TListViewPresenter(
	paginator=@TPaginator(entityClass = ProductPrice.class, serviceName = IProductPriceController.JNDI_NAME,
		show=true, showSearchField=false, 
		orderBy = {	@TOption(text = TUsualKey.NAME , value = "product")}),
	presenter=@TPresenter(
		decorator = @TDecorator(viewTitle=SmplsKey.VIEW_PRICE, buildModesRadioButton=false),
		behavior=@TBehavior(runNewActionAfterSave=true, saveOnlyChangedModels=false, saveAllModels=true)))
@TSecurity(id=DomainApp.PRODUCT_PRICE_FORM_ID, appName = SmplsKey.APP_SAMPLES,
	moduleName = SmplsKey.MODULE_SALES, viewName = SmplsKey.VIEW_PRICE,
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class PriceMV extends TEntityModelView<ProductPrice> {

	@TLabel(text=TUsualKey.PRODUCT)
	@TAutoCompleteEntity(required=true, control=@TControl(maxWidth=250, parse = true),
		entries = @TEntry(entityType = Product.class, 
			fields = { "code", "name" }, service = IProductController.JNDI_NAME))
	private SimpleObjectProperty<Product> product;
	
	@TLabel(text=SmplsKey.UNIT_PRICE)
	@TBigDecimalField(control=@TControl(maxWidth=250, parse = true))
	private SimpleObjectProperty<BigDecimal> unitPrice;
	
	public PriceMV(ProductPrice entity) {
		super(entity);
		super.formatToString("%s (%s)", product, unitPrice);
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

}
