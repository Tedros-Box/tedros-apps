package org.tedros.samples.module.sale.model;

import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TCheckBoxField;
import org.tedros.fx.annotation.control.TComboBoxField;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TOptionsList;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.process.TEjbService;
import org.tedros.fx.annotation.scene.control.TLabeled;
import org.tedros.fx.domain.TLabelPosition;
import org.tedros.fx.presenter.entity.behavior.TSaveViewBehavior;
import org.tedros.fx.presenter.entity.decorator.TSaveViewDecorator;
import org.tedros.fx.presenter.model.TEntityModelView;
import org.tedros.sample.domain.DomainApp;
import org.tedros.sample.ejb.controller.ISaleEventConfigController;
import org.tedros.sample.entity.SaleEventConfig;
import org.tedros.samples.SmplsKey;
import org.tedros.samples.module.sale.action.NewSaleEventConfigAction;
import org.tedros.stock.ejb.controller.IEventTypeController;
import org.tedros.stock.entity.EntryType;
import org.tedros.stock.entity.OutType;
import org.tedros.stock.module.inventory.model.EntryTypeMV;
import org.tedros.stock.module.inventory.model.OutTypeMV;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;


@TPresenter(
	behavior=@TBehavior(type=TSaveViewBehavior.class, action=NewSaleEventConfigAction.class),
	decorator=@TDecorator(type=TSaveViewDecorator.class, viewTitle=SmplsKey.VIEW_SALES_EVENT_CONFIG))
@TEjbService(serviceName = ISaleEventConfigController.JNDI_NAME, model=SaleEventConfig.class)
@TSecurity(id=DomainApp.SALE_EVENT_CONFIG_FORM_ID, appName = SmplsKey.APP_SAMPLES,
moduleName = SmplsKey.MODULE_SALES, viewName = SmplsKey.VIEW_SALES_EVENT_CONFIG,
allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, 
				TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class SaleEventConfigMV extends TEntityModelView<SaleEventConfig> {

	@TLabel(text=SmplsKey.TEXT_UPDATE_STOCK, position=TLabelPosition.LEFT)
	@TCheckBoxField(labeled=@TLabeled(text=TUsualKey.YES, parse = true))
	private SimpleBooleanProperty updateStock;
	
	@TLabel(text=SmplsKey.TEXT_STOCK_OUT_EVENT, position=TLabelPosition.LEFT)
	@TComboBoxField(required=true,
	optionsList=@TOptionsList(serviceName = IEventTypeController.JNDI_NAME, 
	optionModelViewClass=OutTypeMV.class,
	entityClass=OutType.class))
	private SimpleObjectProperty<OutType> outType;

	@TLabel(text=SmplsKey.TEXT_STOCK_ENTRY_EVENT, position=TLabelPosition.LEFT)
	@TComboBoxField(required=true,
	optionsList=@TOptionsList(serviceName = IEventTypeController.JNDI_NAME, 
	optionModelViewClass=EntryTypeMV.class,
	entityClass=EntryType.class))
	private SimpleObjectProperty<EntryType> entryType;
	
	public SaleEventConfigMV(SaleEventConfig entity) {
		super(entity);
	}

}
