export const PRODUCT_CODE = `@Entity
@Table(name = "product")
@TImportInfo(description = "Regras para importar um arquivo para a tabela de produtos ", 
fileType = { TFileType.CSV, TFileType.XLS })
@JsonClassDescription("a stockable product")
public class Product extends TVersionEntity {

	private static final long serialVersionUID = -4590169775657544834L;

	@Column(length=60)
	@TField(required = false, 
		label = "Codigo do produto", column = "Codigo", 
		numberType=Integer.class, example="22")
	@JsonProperty
	private String code;
	
	@Column(length=120, nullable = false)
	@TField(required = true, 
	label = "Nome do produto", column = "Nome Produto",
	example="Arroz")
	@JsonProperty(required=true)
	private String name;
	
	@Column
	@JsonProperty
	private String description;
	
	@Column(length=120)
	@JsonProperty
	private String trademark;
	
	@Column(length=20)
	@TField(required = true, 
	label = "Unidade medida", column = "Unidade Medida",
	example="KG", caseSensitive=TCaseSensitive.UPPER, 
	possibleValues= {"KG", "LT", "UNID","PCT","ML","GR"})
	@JsonIgnore
	private String unitMeasure;
	
	@Column @JsonIgnore
	private Double measure;
	
	@Column(length=15)
	@JsonIgnore
	private String size;
	
	@Column @JsonIgnore
	private Double weight;
	
	@JsonIgnore
	@OneToOne(cascade=CascadeType.ALL, 
			fetch=FetchType.EAGER, 
			optional=true)
	@JoinColumn(name="barcode_id")
	private TBarcode barcode;
	
	@JsonIgnore
	@ManyToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinTable(name=DomainTables.product_images, schema=DomainSchema.schema, 
	uniqueConstraints=@UniqueConstraint(columnNames = { "prod_id", "file_id" }),
	joinColumns=@JoinColumn(name="prod_id"), inverseJoinColumns=@JoinColumn(name="file_id"))
	private List<TFileEntity> images;

    // Getters, Setters, hashCode, equals, toString...
}`;

export const PRODUCT_MV_CODE = `@TForm(header = "", showBreadcrumBar=false, scroll=true)
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
			@TTab(text = TUsualKey.MAIN_DATA, fields = {"code", "unitMeasure", "description"}),
			@TTab(text = TFxKey.BARCODE, fields = {"barcode"}),
			@TTab(text = TUsualKey.PICTURES, fields = {"images"})
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
	
    // Constructors, Getters, Setters...
}`;

export const PRODUCT_MODULE_CODE = `@TView(title=STCKKey.MODULE_PRODUCTS,
items = {
	@TItem(title = STCKKey.VIEW_PRODUCT, description=STCKKey.VIEW_PRODUCT_DESC,
	model = Product.class, modelView=ProductMV.class),
	@TItem(title = STCKKey.VIEW_PRODUCT_REPORT, description=STCKKey.VIEW_PRODUCT_REPORT_DESC,
	model = ProductReportModel.class, modelView=ProductReportMV.class)
})
@TSecurity(id=DomainApp.PRODUCT_MODULE_ID, 
appName = STCKKey.APP_STOCK, 
moduleName = STCKKey.MODULE_PRODUCTS, 
allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class ProductModule extends TModule {

}`;

export const APP_START_CODE = `@TApplication(name=STCKKey.APP_STOCK, 
module = {	
	@TModule(type=ProductModule.class, 
			name=STCKKey.MODULE_PRODUCTS, 
			menu=STCKKey.MENU_STOCK, 
			description=STCKKey.MODULE_DESC_PRODUCTS,
			icon=TConstant.ICONS_FOLDER + "product.png", 
			menuIcon=TConstant.ICONS_FOLDER + "product_menu.png"),
	@TModule(type=InventoryModule.class, 
		name=STCKKey.MODULE_INVENTORY, 
		menu=STCKKey.MENU_STOCK, 
		description=STCKKey.MODULE_INVENTORY_DESC,
		icon=TConstant.ICONS_FOLDER + "inventory.png", 
		menuIcon=TConstant.ICONS_FOLDER + "inventory_menu.png")
}, packageName = "org.tedros.stock", universalUniqueIdentifier=TConstant.UUI)
@TResourceBundle(resourceName={"STCK"})
@TSecurity(id=DomainApp.MNEMONIC, 
appName = STCKKey.APP_STOCK, 
allowedAccesses=TAuthorizationType.APP_ACCESS)
public class AppStart implements ITApplication {

	@Override
	public void start() {
		AppResource.createResource();
	}

	@Override
	public void stop() {
		// Executed on exit and logout
	}

}`;
