package org.tedros.stock.module.report.model;

import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.fx.TFxKey;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TGenericType;
import org.tedros.fx.annotation.control.TRadio;
import org.tedros.fx.annotation.control.TTableColumn;
import org.tedros.fx.annotation.control.TTableView;
import org.tedros.fx.annotation.control.TTableView.TTableViewSelectionModel;
import org.tedros.fx.annotation.control.TTextField;
import org.tedros.fx.annotation.control.TTextInputControl;
import org.tedros.fx.annotation.control.TVRadioGroup;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.layout.TAccordion;
import org.tedros.fx.annotation.layout.TFieldSet;
import org.tedros.fx.annotation.layout.THBox;
import org.tedros.fx.annotation.layout.THGrow;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.layout.TPriority;
import org.tedros.fx.annotation.layout.TTitledPane;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.process.TReportProcess;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.scene.control.TControl;
import org.tedros.fx.annotation.scene.layout.TRegion;
import org.tedros.fx.builder.TRowFactoryWithOpenAndRemoveAction;
import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.model.TModelView;
import org.tedros.fx.presenter.dynamic.TDynaPresenter;
import org.tedros.fx.presenter.report.behavior.TDataSetReportBehavior;
import org.tedros.fx.presenter.report.decorator.TDataSetReportDecorator;
import org.tedros.person.module.report.action.SearchAction;
import org.tedros.stock.STCKKey;
import org.tedros.stock.domain.DomainApp;
import org.tedros.stock.model.ProductItem;
import org.tedros.stock.model.ProductReportModel;
import org.tedros.stock.module.report.process.ProductReportProcess;
import org.tedros.stock.table.ProductItemTV;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.Priority;


@TForm(header = "", showBreadcrumBar=false, editCssId="")
@TReportProcess(type=ProductReportProcess.class, model = ProductReportModel.class)
@TPresenter(type = TDynaPresenter.class,
	behavior = @TBehavior(type = TDataSetReportBehavior.class, 
		action=SearchAction.class), 
	decorator = @TDecorator(type = TDataSetReportDecorator.class, 
		viewTitle=STCKKey.VIEW_PRODUCT_REPORT))
@TSecurity(	id=DomainApp.PRODUCT_REPORT_FORM_ID, 
appName = STCKKey.APP_STOCK, 
moduleName = STCKKey.MODULE_PRODUCTS, 
viewName = STCKKey.VIEW_PRODUCT_REPORT,
allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EXPORT, TAuthorizationType.SEARCH})
public class ProductReportMV extends TModelView<ProductReportModel>{
	
	@TAccordion(expandedPane="filtro", node=@TNode(id="repdoaacc",parse = true),
		panes={
			@TTitledPane(text=TUsualKey.FILTERS, node=@TNode(id="filtro",parse = true), 
				expanded=true, 
				fields={"codes", "orderBy"}),
			@TTitledPane(text=TUsualKey.RESULT, node=@TNode(id="resultado",parse = true),
				fields={"result"})})	
	private SimpleLongProperty id;
	
	@TLabel(text=TUsualKey.CODE)
	@TTextField(maxLength=60, 
	control=@TControl(tooltip=TUsualKey.TOOLTIP_CODES_COMMA_SEPARATED, parse = true), 
	textInputControl=@TTextInputControl(promptText="PR0,C2,D22", parse = true),
	node=@TNode(requestFocus=true, parse = true) )
	@THBox(	pane=@TPane(children={"codes", "name", "trademark"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="codes", priority=Priority.NEVER), 
			@TPriority(field="name", priority=Priority.ALWAYS), 
			@TPriority(field="trademark", priority=Priority.ALWAYS)}))
	private SimpleStringProperty codes;
	
	@TLabel(text=TUsualKey.NAME)
	@TTextField(maxLength=120)
	private SimpleStringProperty name;

	@TLabel(text=TUsualKey.TRADEMARK)
	@TTextField(maxLength=120)
	private SimpleStringProperty trademark;
	
	@TLabel(text=TFxKey.SORT_BY)
	@TFieldSet(fields = { "orderBy", "orderType" }, 
		region=@TRegion(maxWidth=600, parse = true),
		legend =TUsualKey.RESULT_ORDER)
	@TVRadioGroup(alignment=Pos.TOP_LEFT, spacing=4,
	radio = {@TRadio(text=TUsualKey.NAME, userData="p.name"),  
					@TRadio(text=TUsualKey.CODE, userData="p.code")
	})
	private SimpleStringProperty orderBy;
	
	@TLabel(text=TFxKey.SORT_TYPE)
	@TVRadioGroup(alignment=Pos.TOP_LEFT, spacing=4,
	radio = {@TRadio(text=TFxKey.SORT_BY_ASC, userData="asc"), 
					@TRadio(text=TFxKey.SORT_BY_DESC, userData="desc")
	})
	private SimpleStringProperty orderType;
	
	@TTableView(
			selectionModel=@TTableViewSelectionModel(
					selectionMode=SelectionMode.MULTIPLE,parse = true), 
			rowFactory=TRowFactoryWithOpenAndRemoveAction.class,
		control=@TControl(tooltip=TFxKey.TABLE_MENU_TOOLTIP, parse = true),
		columns = { 
				@TTableColumn(cellValue="code", text = TUsualKey.CODE, resizable=true), 
				@TTableColumn(cellValue="name", text = TUsualKey.NAME, resizable=true), 
				@TTableColumn(cellValue="trademark", text = TUsualKey.TRADEMARK, resizable=true)
			})
	@TGenericType(model=ProductItem.class, modelView=ProductItemTV.class)
	private ITObservableList<ProductItemTV> result;
	
	public ProductReportMV(ProductReportModel entidade) {
		super(entidade);
	}

	public SimpleLongProperty getId() {
		return id;
	}

	public void setId(SimpleLongProperty id) {
		this.id = id;
	}

	/**
	 * @return the codes
	 */
	public SimpleStringProperty getCodes() {
		return codes;
	}

	/**
	 * @param codes the codes to set
	 */
	public void setCodes(SimpleStringProperty codes) {
		this.codes = codes;
	}

	/**
	 * @return the name
	 */
	public SimpleStringProperty getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(SimpleStringProperty name) {
		this.name = name;
	}

	/**
	 * @return the trademark
	 */
	public SimpleStringProperty getTrademark() {
		return trademark;
	}

	/**
	 * @param trademark the trademark to set
	 */
	public void setTrademark(SimpleStringProperty trademark) {
		this.trademark = trademark;
	}

	/**
	 * @return the orderBy
	 */
	public SimpleStringProperty getOrderBy() {
		return orderBy;
	}

	/**
	 * @param orderBy the orderBy to set
	 */
	public void setOrderBy(SimpleStringProperty orderBy) {
		this.orderBy = orderBy;
	}

	/**
	 * @return the orderType
	 */
	public SimpleStringProperty getOrderType() {
		return orderType;
	}

	/**
	 * @param orderType the orderType to set
	 */
	public void setOrderType(SimpleStringProperty orderType) {
		this.orderType = orderType;
	}

	/**
	 * @return the result
	 */
	public ITObservableList<ProductItemTV> getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(ITObservableList<ProductItemTV> result) {
		this.result = result;
	}


}
