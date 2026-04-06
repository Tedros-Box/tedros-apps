package org.tedros.it.tools.module.governance.report;

import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.fx.TFxKey;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TGenericType;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TTableColumn;
import org.tedros.fx.annotation.control.TTableView;
import org.tedros.fx.annotation.control.TTableView.TTableViewSelectionModel;
import org.tedros.fx.annotation.control.TTextField;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.layout.TAccordion;
import org.tedros.fx.annotation.layout.TTitledPane;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.process.TReportProcess;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.scene.control.TControl;
import org.tedros.fx.builder.TRowFactoryWithOpenAndRemoveAction;
import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.model.TModelView;
import org.tedros.fx.presenter.dynamic.TDynaPresenter;
import org.tedros.fx.presenter.report.behavior.TDataSetReportBehavior;
import org.tedros.fx.presenter.report.decorator.TDataSetReportDecorator;
import org.tedros.it.tools.ItToolsKey;
import org.tedros.it.tools.domain.DomainApp;
import org.tedros.it.tools.model.ServiceCatalogReportItemModel;
import org.tedros.it.tools.model.ServiceCatalogReportModel;
import org.tedros.person.module.report.action.SearchAction;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.SelectionMode;


@TForm(header = "", showBreadcrumBar=false, editCssId="")
@TReportProcess(type=ServiceCatalogReportProcess.class, model = ServiceCatalogReportModel.class)
@TPresenter(type = TDynaPresenter.class,
	behavior = @TBehavior(type = TDataSetReportBehavior.class, 
		action=SearchAction.class), 
	decorator = @TDecorator(type = TDataSetReportDecorator.class, 
		viewTitle="Relatório de Catálogo de Serviços"))
@TSecurity(	id=DomainApp.GOVERNANCE_SERVICE_REPORT_FORM_ID, 
appName = ItToolsKey.APP_ITSUPPORT, 
moduleName = ItToolsKey.MODULE_GOVERNANCE, 
viewName = "Relatório de Catálogo de Serviços",
allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EXPORT, TAuthorizationType.SEARCH})
public class ServiceCatalogReportMV extends TModelView<ServiceCatalogReportModel>{
	
	@TAccordion(expandedPane="filtro", node=@TNode(id="repdoaacc",parse = true),
		panes={
			@TTitledPane(text=TUsualKey.FILTERS, node=@TNode(id="filtro",parse = true), 
				expanded=true, 
				fields={"name"}),
			@TTitledPane(text=TUsualKey.RESULT, node=@TNode(id="resultado",parse = true),
				fields={"result"})})	
	private SimpleLongProperty id;
		
	@TLabel(text=TUsualKey.NAME)
	@TTextField(maxLength=120)
	private SimpleStringProperty name;	
	
	@TTableView(
			selectionModel=@TTableViewSelectionModel(
					selectionMode=SelectionMode.MULTIPLE,parse = true), 
			rowFactory=TRowFactoryWithOpenAndRemoveAction.class,
		control=@TControl(tooltip=TFxKey.TABLE_MENU_TOOLTIP, parse = true),
		columns = { 
				@TTableColumn(cellValue="name", text = TUsualKey.NAME, resizable=true)
			})
	@TGenericType(model=ServiceCatalogReportItemModel.class, modelView=ServiceCatalogTV.class)
	private ITObservableList<ServiceCatalogTV> result;
	
	public ServiceCatalogReportMV(ServiceCatalogReportModel entidade) {
		super(entidade);
	}

	public SimpleLongProperty getId() {
		return id;
	}

	public void setId(SimpleLongProperty id) {
		this.id = id;
	}

	public SimpleStringProperty getName() {
		return name;
	}

	public void setName(SimpleStringProperty name) {
		this.name = name;
	}


	public ITObservableList<ServiceCatalogTV> getResult() {
		return result;
	}

	public void setResult(ITObservableList<ServiceCatalogTV> result) {
		this.result = result;
	}


}
