# Tedros + AI (Artificial Intelligence)
![Tedros](https://github.com/Tedros-Box/tedros-apps/blob/master/printscreen/tedrosbox.png)

## **With Tedros frakework it is possible to develop integrated applications quickly and easily. Develop rich applications in a client-server topology with all Java EE, Java FX and ChatGpt solutions.**

**Integrated with the power of Chatgpt artificial intelligence.**
![Chatgpt](https://github.com/Tedros-Box/tedros-apps/blob/master/printscreen/teros3.png)

**Prerequisites:**
  - Java 8
  - Maven 3+

**See on Wiki:**
- [Setting up the development environment](https://github.com/Tedros-Box/tedros-apps/wiki/Configure)
- [Starting the system](https://github.com/Tedros-Box/tedros-apps/wiki/Inicializa%C3%A7%C3%A3o)

Code examples can be found in the app-samples project, below we will demonstrate how to develop a CRUD for the ProductPrice.java entity

  1. Entity
```java
@Entity
@Table(name=DomainTables.product_price, schema=DomainSchema.schema,
uniqueConstraints= {@UniqueConstraint(columnNames = { "legal_person_id","cost_center_id","prod_id" })})
public class ProductPrice extends TVersionEntity implements ICostCenterAccounting {

private static final long serialVersionUID = 6841828609438785671L;

@ManyToOne(fetch=FetchType.EAGER)
@JoinColumn(name="legal_person_id", nullable=false)
private LegalPerson legalPerson;

@ManyToOne(fetch=FetchType.EAGER)
@JoinColumn(name="cost_center_id", nullable=false)
private CostCenter costCenter;

@ManyToOne(fetch=FetchType.EAGER)
@JoinColumn(name="prod_id",
nullable=false, updatable=false)
private Product product;

@Column(nullable = false)
private BigDecimal unitPrice;

//Getters and Setters
}
```
2. Remote interface

```java
@Remote
public interface IProductPriceController extends ITSecureEjbController<ProductPrice> {

static final String JNDI_NAME = "IProductPriceControllerRemote";

}
```
3. Stateless Bean
```java
@TSecurityInterceptor
@Stateless(name="IProductPriceController")
@TBeanSecurity({@TBeanPolicie(id = DomainApp.PRODUCT_PRICE_FORM_ID,
policy = { TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS })})
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class ProductPriceController extends TSecureEjbController<ProductPrice> implements IProductPriceController, ITSecurity {

@EJB
private SmplsService<ProductPrice> serv;

@EJB
private ITSecurityController securityController;

@Override
public ITEjbService<ProductPrice> getService() {
return serv;
}

@Override
public ITSecurityController getSecurityController() {
return securityController;
}
}
```
4. Model View
```java
@TForm(header = SmplsKey.FORM_PRICE, showBreadcrumBar=true, scroll=false)
@TEjbService(serviceName = IProductPriceController.JNDI_NAME, model=ProductPrice.class)
@TListViewPresenter(
page=@TPage(serviceName = IProductPriceController.JNDI_NAME,
query = @TQuery(entity=ProductPrice.class,
condition= {
@TCondition(field = "name", alias="p", operator=TCompareOp.LIKE, label=TUsualKey.PRODUCT),
@TCondition(field = "name", alias="lp", operator=TCompareOp.LIKE, label=TUsualKey.LEGAL_PERSON)},
join = { @TJoin(field = "product", joinAlias = "p"),
@TJoin(field = "legalPerson", joinAlias = "lp"),
@TJoin(field = "costCenter", joinAlias = "cc")},
orderBy= { @TOrder(label = TUsualKey.PRODUCT , field = "name", alias="p"),
@TOrder(label = TUsualKey.COST_CENTER , field = "name", alias="cc"),
@TOrder(label = TUsualKey.LEGAL_PERSON , field = "name", alias="lp")}
),showSearch=true, showOrderBy=true),
presenter=@TPresenter(
decorator = @TDecorator(viewTitle=SmplsKey.VIEW_PRICE, buildModesRadioButton=false),
behavior=@TBehavior(runNewActionAfterSave=true, saveOnlyChangedModels=false, saveAllModels=false)))
@TSecurity(id=DomainApp.PRODUCT_PRICE_FORM_ID, appName = SmplsKey.APP_SAMPLES,
moduleName = SmplsKey.MODULE_SALES, viewName = SmplsKey.VIEW_PRICE,
allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT,
TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class PriceMV extends TEntityModelView<ProductPrice> {

@TLabel(text=TUsualKey.LEGAL_PERSON)
@TAutoCompleteEntity(required=true,
startSearchAt=3, showMaxItems=30,
service = IPersonController.JNDI_NAME,
query = @TQuery(entity = LegalPerson.class,
condition = {
@TCondition(field = "name", operator=TCompareOp.LIKE),
@TCondition(logicOp=TLogicOp.OR, field = "otherName",
operator=TCompareOp.LIKE)}))
@TTrigger(type = FilterCostCenterTrigger.class,
targetFieldName="costCenter", runAfterFormBuild=true)
@THBox( pane=@TPane(children={"legalPerson", "costCenter", "product", "unitPrice"}), spacing=10, fillHeight=true,
hgrow=@THGrow(priority={@TPriority(field="costCenter", priority=Priority.SOMETIMES),
@TPriority(field="legalPerson", priority=Priority.ALWAYS),
@TPriority(field="product", priority=Priority.ALWAYS),
@TPriority(field="unitPrice", priority=Priority.NEVER)}))
protected SimpleObjectProperty<LegalPerson> legalPerson;

@TLabel(text=TUsualKey.COST_CENTER)
@TComboBoxField(required=true)
protected SimpleObjectProperty<CostCenter> costCenter;

@TLabel(text=TUsualKey.PRODUCT)
@TAutoCompleteEntity(required=true,
control=@TControl(maxWidth=250, parse = true),
startSearchAt=3, showMaxItems=30,
service = IProductController.JNDI_NAME,
query = @TQuery(entity = Product.class,
condition = {
@TCondition(field = "name", operator=TCompareOp.LIKE),
@TCondition(logicOp=TLogicOp.OR, field = "code",
operator=TCompareOp.LIKE)}))
protected SimpleObjectProperty<Product> product;

@TLabel(text=TUsualKey.UNIT_PRICE)
@TBigDecimalField(control=@TControl(maxWidth=100, parse = true))
protected SimpleObjectProperty<BigDecimal> unitPrice;

public PriceMV(ProductPrice entity) {
super(entity);
super.formatToString("%s [%s], %s (%s)", legalPerson, costCenter, product, unitPrice);
}
}

```
5. Module
```java
@TView(items = {
@TItem(title = SimplesKey.VIEW_PRICE,
model=ProductPrice.class, modelView=PriceMV.class) })
@TSecurity(id=DomainApp.PRODUCT_PRICE_MODULE_ID,
appName = SmplsKey.APP_SAMPLES,
moduleName = SmplsKey.MODULE_PRICE,
allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class PriceModule extends TModule {

}
```
6. AppStart
```java
@TApplication(name=SmplsKey.APP_SAMPLES,
module = {
@TModule(type=PriceModule.class,
name=SmplsKey.MODULE_PRICE,
menu=SmplsKey.MENU_CRUD_VIEWS,
description=SmplsKey.MODULE_DESC_PRICE)
}, packageName = "org.tedros.samples",
universalUniqueIdentifier=TConstant.UUI)
@TResourceBundle(resourceName={"Smpls"})
@TSecurity(id=DomainApp.MNEMONIC,
appName = SmplsKey.APP_SAMPLES,
allowedAccesses=TAuthorizationType.APP_ACCESS)
public class AppStart implements ITApplication {

@Override
public void start() {
// Runs at startup
}

@Override
public void stop() {
// Executed on exit and logout
}
}
```

Screen print screen:
![View](https://github.com/Tedros-Box/tedros-apps/blob/master/printscreen/producprice.png)
