/**
 * 
 */
package org.tedros.stock.module.inventory.model;

import java.util.Date;
import java.util.Locale;

import org.tedros.core.TLanguage;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TComboBoxField;
import org.tedros.fx.annotation.control.TContent;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TOptionsList;
import org.tedros.fx.annotation.control.TTab;
import org.tedros.fx.annotation.control.TTabPane;
import org.tedros.fx.annotation.form.TDetailForm;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.form.TSetting;
import org.tedros.fx.annotation.layout.THBox;
import org.tedros.fx.annotation.layout.THGrow;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.layout.TPriority;
import org.tedros.fx.annotation.page.TPage;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TListViewPresenter;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.process.TEjbService;
import org.tedros.fx.annotation.query.TCondition;
import org.tedros.fx.annotation.query.TJoin;
import org.tedros.fx.annotation.query.TOrder;
import org.tedros.fx.annotation.query.TQuery;
import org.tedros.fx.annotation.query.TTemporal;
import org.tedros.server.query.TCompareOp;
import org.tedros.stock.STCKKey;
import org.tedros.stock.domain.DomainApp;
import org.tedros.stock.ejb.controller.IEventTypeController;
import org.tedros.stock.ejb.controller.IStockEventController;
import org.tedros.stock.entity.OutType;
import org.tedros.stock.entity.StockOut;
import org.tedros.stock.module.inventory.setting.ResponsableSetting;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */
@TSetting(ResponsableSetting.class)
@TForm(name = "", showBreadcrumBar=false, scroll=false)
@TEjbService(serviceName = IStockEventController.JNDI_NAME, model=StockOut.class)
@TListViewPresenter(listViewMinWidth=350,
	page=@TPage(serviceName = IStockEventController.JNDI_NAME,
		query = @TQuery(entity=StockOut.class, 
			condition= { 
				@TCondition(field = "date", operator=TCompareOp.GREATER_EQ_THAN, label=TUsualKey.DATE, temporal=TTemporal.DATE),
				@TCondition(field = "name", alias="lp", operator=TCompareOp.LIKE, label=TUsualKey.LEGAL_PERSON)},
			join = {@TJoin(field = "legalPerson",  joinAlias = "lp"),
				@TJoin(field = "costCenter",  joinAlias = "cc")},
			orderBy= { @TOrder(label = TUsualKey.DATE , field = "date"),
				@TOrder(label = TUsualKey.COST_CENTER , field = "name", alias="cc"),
				@TOrder(label = TUsualKey.LEGAL_PERSON , field = "name", alias="lp")}
				),showSearch=true, showOrderBy=true),
	presenter=@TPresenter(
		decorator = @TDecorator(viewTitle=STCKKey.VIEW_STOCK_OUT, buildModesRadioButton=false),
		behavior=@TBehavior(runNewActionAfterSave=false)))
@TSecurity(id=DomainApp.STOCK_OUT_FORM_ID, appName = STCKKey.APP_STOCK,
	moduleName = STCKKey.MODULE_INVENTORY, viewName = STCKKey.VIEW_STOCK_OUT,
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class StockOutMV extends StockEventMV<StockOut> {

	@TTabPane(tabs = { 
		@TTab( text = TUsualKey.MAIN_DATA, scroll=false, 
			content = @TContent(detailForm=@TDetailForm(fields={"type","observation" }))),
		@TTab(text = TUsualKey.PRODUCTS, 
			content = @TContent(detailForm=@TDetailForm(fields={"items"})))
	})
	private SimpleLongProperty id;

	@TLabel(text=TUsualKey.TYPE)
	@TComboBoxField(required=true,
	optionsList=@TOptionsList(serviceName = IEventTypeController.JNDI_NAME, 
	optionModelViewClass=OutTypeMV.class,
	entityClass=OutType.class))
	@THBox(	spacing=10, fillHeight=true,
		pane=@TPane(children={"legalPerson", "costCenter", "date", "responsable", "type"}), 
	hgrow=@THGrow(priority={@TPriority(field="type", priority=Priority.NEVER), 
		@TPriority(field="legalPerson", priority=Priority.NEVER), 
		@TPriority(field="responsable", priority=Priority.NEVER), 
		@TPriority(field="costCenter", priority=Priority.NEVER), 
		@TPriority(field="date", priority=Priority.NEVER)}))
	private SimpleObjectProperty<OutType> type;
	
	public StockOutMV(StockOut entity) {
		super(entity);
		if(entity.isNew())
			date.setValue(new Date());
		String dtf = TLanguage.getLocale().equals(new Locale("pt"))
				? "em %4$td/%4$tm/%4$tY às %4$tT"
						: "on %4$tm-%4$td-%4$tY at %4$tT";
		super.formatToString("%s [%s], %s "+dtf, legalPerson, costCenter, type, date);
	}


}
