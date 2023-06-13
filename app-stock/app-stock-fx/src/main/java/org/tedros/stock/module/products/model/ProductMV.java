/**
 * 
 */
package org.tedros.stock.module.products.model;

import org.tedros.common.model.TBarcode;
import org.tedros.common.model.TFileEntity;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.fx.TFxKey;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TBarcodeGenerator;
import org.tedros.fx.annotation.control.TContent;
import org.tedros.fx.annotation.control.TDoubleField;
import org.tedros.fx.annotation.control.TFieldBox;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TGenericType;
import org.tedros.fx.annotation.control.TSelectImageField;
import org.tedros.fx.annotation.control.TTab;
import org.tedros.fx.annotation.control.TTabPane;
import org.tedros.fx.annotation.control.TTextAreaField;
import org.tedros.fx.annotation.control.TTextField;
import org.tedros.fx.annotation.form.TDetailForm;
import org.tedros.fx.annotation.form.TForm;
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
import org.tedros.fx.annotation.query.TOrder;
import org.tedros.fx.annotation.query.TQuery;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.scene.control.TControl;
import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.domain.TEnvironment;
import org.tedros.fx.model.TEntityModelView;
import org.tedros.server.model.ITFileBaseModel;
import org.tedros.server.query.TCompareOp;
import org.tedros.stock.STCKKey;
import org.tedros.stock.domain.DomainApp;
import org.tedros.stock.ejb.controller.IProductController;
import org.tedros.stock.entity.Product;
import org.tedros.stock.start.TConstant;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.Priority;

/**
 * @author Davis Dun
 *
 */
@TForm(name = "", showBreadcrumBar=false, scroll=true)
@TEjbService(serviceName = IProductController.JNDI_NAME, model=Product.class)
@TListViewPresenter(
	page=@TPage(serviceName = IProductController.JNDI_NAME,
		query = @TQuery(entity=Product.class, condition= {
				@TCondition(field = "name", operator=TCompareOp.LIKE, label=TUsualKey.NAME)},
			orderBy= {@TOrder(label = TUsualKey.NAME, field = "name")}
		),showSearch=true, showOrderBy=true),
	presenter=@TPresenter(decorator = @TDecorator(viewTitle=STCKKey.VIEW_PRODUCT,
		buildModesRadioButton=false),
	behavior=@TBehavior(runNewActionAfterSave=false, saveAllModels=false, 
	saveOnlyChangedModels=false)))
@TSecurity(id=DomainApp.PRODUCT_FORM_ID, appName = STCKKey.APP_STOCK,
	moduleName = STCKKey.MODULE_PRODUCTS, viewName = STCKKey.VIEW_PRODUCT,
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class ProductMV extends TEntityModelView<Product> {

	@TTabPane(
	tabs = { 
			@TTab(text = TUsualKey.MAIN_DATA, 
				content = @TContent(detailForm=@TDetailForm(fields = {"code", "unitMeasure", "description"}))),
			@TTab(text = TFxKey.BARCODE, 
				content = @TContent(detailForm=@TDetailForm(fields = {"barcode"}))),
			@TTab(text = TUsualKey.PICTURES, 
			content = @TContent(detailForm=@TDetailForm(fields = {"images"})))  
			})
	private SimpleLongProperty id;
	
	@TLabel(text=TUsualKey.CODE)
	@TTextField(maxLength=60,
	node=@TNode(requestFocus=true, parse = true) )
	@THBox(	pane=@TPane(children={"code", "name", "trademark"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="code", priority=Priority.NEVER), 
			@TPriority(field="name", priority=Priority.ALWAYS), 
			@TPriority(field="trademark", priority=Priority.ALWAYS)}))
	private SimpleStringProperty code;
	
	@TLabel(text=TUsualKey.NAME)
	@TTextField(maxLength=120, required = true)
	private SimpleStringProperty name;

	@TLabel(text=TUsualKey.TRADEMARK)
	@TTextField(maxLength=120)
	private SimpleStringProperty trademark;

	@TLabel(text=TUsualKey.UNIT_MEASURE)
	@TTextField(maxLength=20)
	@THBox(	pane=@TPane(children={"unitMeasure", "measure", "size", "weight"}), 
	spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="unitMeasure", priority=Priority.NEVER), 
			@TPriority(field="measure", priority=Priority.NEVER), 
			@TPriority(field="size", priority=Priority.NEVER), 
			@TPriority(field="weight", priority=Priority.NEVER)}))
	private SimpleStringProperty unitMeasure;
	
	@TLabel(text=TUsualKey.MEASURE)
	@TDoubleField()
	private SimpleDoubleProperty measure;
	
	@TLabel(text=TUsualKey.SIZE)
	@TTextField(maxLength=15,
	control=@TControl(tooltip=STCKKey.TOOLTIP_SIZE, parse = true) )
	private SimpleStringProperty size;
	
	@TLabel(text=TUsualKey.WEIGHT)
	@TDoubleField()
	private SimpleDoubleProperty weight;
	
	@TLabel(text=TUsualKey.DESCRIPTION)
	@TTextAreaField(wrapText=true)
	private SimpleStringProperty description;
	

	@TLabel(text=TFxKey.BARCODE, show=false)
	@TBarcodeGenerator(modelType = TBarcode.class, required=false)
	private SimpleObjectProperty<TBarcode> barcode;
	
	@TFieldBox(node=@TNode(id="img", parse = true))
	@TSelectImageField(source=TEnvironment.LOCAL, 
	target=TEnvironment.REMOTE, remoteOwner=TConstant.UUI)
	@TGenericType(model = TFileEntity.class)
	private ITObservableList<ITFileBaseModel> images;
	
	public ProductMV(Product entity) {
		super(entity);
		super.formatToString("%s %s", code, name);
	}

	public SimpleStringProperty getName() {
		return name;
	}

	public void setName(SimpleStringProperty name) {
		this.name = name;
	}

	public SimpleStringProperty getDescription() {
		return description;
	}

	public void setDescription(SimpleStringProperty description) {
		this.description = description;
	}

	public SimpleLongProperty getId() {
		return id;
	}

	public void setId(SimpleLongProperty id) {
		this.id = id;
	}

	public SimpleStringProperty getCode() {
		return code;
	}

	public void setCode(SimpleStringProperty code) {
		this.code = code;
	}

	public SimpleStringProperty getTrademark() {
		return trademark;
	}

	public void setTrademark(SimpleStringProperty trademark) {
		this.trademark = trademark;
	}

	public SimpleStringProperty getUnitMeasure() {
		return unitMeasure;
	}

	public void setUnitMeasure(SimpleStringProperty unitMeasure) {
		this.unitMeasure = unitMeasure;
	}

	public SimpleDoubleProperty getMeasure() {
		return measure;
	}

	public void setMeasure(SimpleDoubleProperty measure) {
		this.measure = measure;
	}

	public SimpleStringProperty getSize() {
		return size;
	}

	public void setSize(SimpleStringProperty size) {
		this.size = size;
	}

	public SimpleDoubleProperty getWeight() {
		return weight;
	}

	public void setWeight(SimpleDoubleProperty weight) {
		this.weight = weight;
	}

	public ITObservableList<ITFileBaseModel> getImages() {
		return images;
	}

	public void setImages(ITObservableList<ITFileBaseModel> images) {
		this.images = images;
	}

	/**
	 * @return the barcode
	 */
	public SimpleObjectProperty<TBarcode> getBarcode() {
		return barcode;
	}

	/**
	 * @param barcode the barcode to set
	 */
	public void setBarcode(SimpleObjectProperty<TBarcode> barcode) {
		this.barcode = barcode;
	}
}
