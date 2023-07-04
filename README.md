# Tedros 
Com o Tedros frakework é possível desenvolver aplicações integradas de forma fácil e rápida. Desenvolva aplicações ricas em uma topologia cliente servidor com todas as soluções do Java EE, Java FX e ChatGpt.

Pre-requisitos:
 - Java 8
 - Maven 3+

Veja na Wiki:
- [Configurando o ambiente de desenvolvimento](https://github.com/Tedros-Box/tedros-apps/wiki/Configura%C3%A7%C3%A3o)
- [Iniciando o sistema](https://github.com/Tedros-Box/tedros-apps/wiki/Inicializa%C3%A7%C3%A3o)

Exemplos de codigo podem ser encontradas no projeto app-samples, vamos abaixo demonstrar como desenvolver um CRUD para a entidade ProductPrice.java

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
policie = { TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS })})
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class ProductPriceController extends TSecureEjbController<ProductPrice> implements IProductPriceController, ITSecurity  {

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
