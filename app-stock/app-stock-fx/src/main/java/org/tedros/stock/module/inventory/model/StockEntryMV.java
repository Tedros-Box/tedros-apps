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
import org.tedros.fx.annotation.layout.THBox;
import org.tedros.fx.annotation.layout.THGrow;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.layout.TPriority;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TListViewPresenter;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.process.TEjbService;
import org.tedros.stock.STCKKey;
import org.tedros.stock.domain.DomainApp;
import org.tedros.stock.ejb.controller.IEventTypeController;
import org.tedros.stock.ejb.controller.IStockEventController;
import org.tedros.stock.entity.EntryType;
import org.tedros.stock.entity.StockEntry;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */
@TForm(name = "", showBreadcrumBar=false, scroll=false)
@TEjbService(serviceName = IStockEventController.JNDI_NAME, model=StockEntry.class)
@TListViewPresenter(listViewMinWidth=350,
	presenter=@TPresenter(decorator = @TDecorator(viewTitle=STCKKey.VIEW_STOCK_ENTRY,
		buildModesRadioButton=false),
	behavior=@TBehavior(runNewActionAfterSave=false)))
@TSecurity(id=DomainApp.STOCK_ENTRY_FORM_ID, appName = STCKKey.APP_STOCK,
	moduleName = STCKKey.MODULE_INVENTORY, viewName = STCKKey.VIEW_STOCK_ENTRY,
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class StockEntryMV extends StockEventMV<StockEntry> {

	@TTabPane(tabs = { 
		@TTab( text = TUsualKey.MAIN_DATA, 
			content = @TContent(detailForm=@TDetailForm(fields={"type","observation"}))),
		@TTab(text =  TUsualKey.PRODUCTS, 
			content = @TContent(detailForm=@TDetailForm(fields={"items"})))
	})
	private SimpleLongProperty id;
	
	@TLabel(text=TUsualKey.TYPE)
	@TComboBoxField(required=true,
	optionsList=@TOptionsList(serviceName = IEventTypeController.JNDI_NAME, 
	optionModelViewClass=EntryTypeMV.class,
	entityClass=EntryType.class))
	@THBox(	spacing=10, fillHeight=true,
			pane=@TPane(children={"costCenter", "date", "responsable", "type"}), 
	hgrow=@THGrow(priority={@TPriority(field="type", priority=Priority.NEVER), 
			@TPriority(field="responsable", priority=Priority.NEVER), 
			@TPriority(field="costCenter", priority=Priority.NEVER), 
			@TPriority(field="date", priority=Priority.NEVER)}))
	private SimpleObjectProperty<EntryType> type;
	
	public StockEntryMV(StockEntry entity) {
		super(entity);
		if(entity.isNew())
			date.setValue(new Date());
		String dtf = TLanguage.getLocale().equals(new Locale("pt"))
				? "em %3$td/%3$tm/%3$tY às %3$tT"
						: "on %3$tm-%3$td-%3$tY at %3$tT";
		super.formatToString("%s, %s "+dtf, costCenter, type, date);
	}

	public SimpleLongProperty getId() {
		return id;
	}

	public void setId(SimpleLongProperty id) {
		this.id = id;
	}

	public SimpleObjectProperty<EntryType> getType() {
		return type;
	}

	public void setType(SimpleObjectProperty<EntryType> type) {
		this.type = type;
	}

}
