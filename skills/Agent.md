# Chat Conversation

Note: _This is purely the output of the chat conversation and does not contain any raw data, codebase snippets, etc. used to generate the output._

### User Input

Estou criando um conjunto de arquivos markdown no meu projeto Tedros para ser usado como skills para que modelos de IA tenham uma referencia de como implementar. Vou precisar da sua ajuda para criar esses skills. Responda de forma objetiva e retorne os markdowns em ingles. 

Neste momento me ajude a criar o skill de como implementar a camada de persistencia no arquivo @[c:\desenv\tedros\github\tedros-apps\skills\data\how-to-create-repository.md] 

Observação: Voce pode usar como exemplo a skill @[c:\desenv\tedros\github\tedros-apps\skills\data\how-to-create-entities.md] 

A camada de persistencia utliza o pattern EAO (Entity Access Object) similar ao DAO. 

O projeto possui a classe generica @[c:\desenv\tedros\github\Tedros\tedrosbox\tdrs-server\tedros-server-api\src\main\java\org\tedros\server\cdi\eao\TGenericEAO.java] que possue varios  metodos prontos.

Quando um novo app é criado é recomendado a criação de um bean generico que pode ser usado para todas as entidades do projeto, exemplo: @[c:\desenv\tedros\github\tedros-apps\app-itsupport-tools\app-itsupport-tools-ejb\src\main\java\org\tedros\it\tools\cdi\eao\ItSupportToolsEAO.java] 

Porem se uma determinada entidade precise de algum metodo mais especifico ae se faz necessario criar um EAO especifico para a entidade, exemplo: 
@[c:\desenv\tedros\github\tedros-apps\app-itsupport-tools\app-itsupport-tools-ejb\src\main\java\org\tedros\it\tools\cdi\eao\JobEvidenceEao.java] 

### Planner Response

Let me read all the relevant files to understand the patterns before writing the skill.

*Viewed [how-to-create-entities.md](file:///c:/desenv/tedros/github/tedros-apps/skills/data/how-to-create-entities.md) *

*Viewed [TGenericEAO.java](file:///c:/desenv/tedros/github/Tedros/tedrosbox/tdrs-server/tedros-server-api/src/main/java/org/tedros/server/cdi/eao/TGenericEAO.java) *

*Viewed [ItSupportToolsEAO.java](file:///c:/desenv/tedros/github/tedros-apps/app-itsupport-tools/app-itsupport-tools-ejb/src/main/java/org/tedros/it/tools/cdi/eao/ItSupportToolsEAO.java) *

*Viewed [JobEvidenceEao.java](file:///c:/desenv/tedros/github/tedros-apps/app-itsupport-tools/app-itsupport-tools-ejb/src/main/java/org/tedros/it/tools/cdi/eao/JobEvidenceEao.java) *

### Planner Response

Now I have all the information needed. Let me write the skill file.

*Edited relevant file*

### Planner Response

The skill file has been written to `how-to-create-repository.md`. Here's a summary of what was documented:

---

## What's in the skill

The skill is structured in **7 sections**, mirroring the style of `how-to-create-entities.md`:

| # | Section | What it covers |
|---|---------|----------------|
| 1 | **Objective** | Purpose of the EAO pattern in Tedros |
| 2 | **Architectural Placement** | Module (`-ejb`) and package (`cdi.eao`) |
| 3 | **`TGenericEAO<E>`** | Full table of built-in methods, lifecycle hooks, and protected helpers |
| 4 | **CDI Scope Rules** | `@Dependent` for generic EAO, `@RequestScoped` for specific EAOs |
| 5 | **Implementation Patterns** | Two named patterns with template code (generic vs. specific EAO) |
| 6 | **Real-World Examples** | `ItSupportToolsEAO` and `JobEvidenceEao` as direct references |
| 7 | **Development Constraints** | Rules on package, module, scope, EntityManager access, null-safety, and the decision rule for when to create a specific EAO |

### User Input

Agora faça o mesmo para a camada de negocios, crie no arquivo @[c:\desenv\tedros\github\tedros-apps\skills\data\how-to-create-business-object.md] o skill necessario para a implementação da camada de negocios representado pelas classes do tipo TGenericBO.

As premissas são as mesmas usadas na camada de persistencia, a classe @[c:\desenv\tedros\github\tedros-apps\app-itsupport-tools\app-itsupport-tools-ejb\src\main\java\org\tedros\it\tools\cdi\bo\ItSupportToolsBO.java] é um exemplo de classe generica que pode ser usado para qualquer entidade e a classe @[c:\desenv\tedros\github\tedros-apps\app-itsupport-tools\app-itsupport-tools-ejb\src\main\java\org\tedros\it\tools\cdi\bo\JobEvidenceBo.java] é uma classe especializada para a entidade JobEvidence.

### Planner Response

Let me read all the relevant files first.

*Viewed [TGenericBO.java](file:///c:/desenv/tedros/github/Tedros/tedrosbox/tdrs-server/tedros-server-api/src/main/java/org/tedros/server/cdi/bo/TGenericBO.java) *

*Viewed [ItSupportToolsBO.java](file:///c:/desenv/tedros/github/tedros-apps/app-itsupport-tools/app-itsupport-tools-ejb/src/main/java/org/tedros/it/tools/cdi/bo/ItSupportToolsBO.java) *

*Viewed [JobEvidenceBo.java](file:///c:/desenv/tedros/github/tedros-apps/app-itsupport-tools/app-itsupport-tools-ejb/src/main/java/org/tedros/it/tools/cdi/bo/JobEvidenceBo.java) *

### Planner Response

I have everything I need. Writing the skill now.

*Edited relevant file*

### Planner Response

The skill file has been written to `how-to-create-business-object.md`. Here's a summary of what was documented:

---

## What's in the skill

The skill is structured in **8 sections**, consistent with the EAO skill style:

| # | Section | What it covers |
|---|---------|----------------|
| 1 | **Objective** | Role of `TGenericBO` as the business layer between the EJB service and the EAO |
| 2 | **Architectural Placement** | Module (`-ejb`), package (`cdi.bo`), and layer position diagram path |
| 3 | **`TGenericBO<E>`** | Full table of all built-in methods, emphasis on the `userId`-filtered overloads, and the `save` routing logic (persist vs. merge) |
| 4 | **CDI Scope Rules** | `@Dependent` for generic BO, `@RequestScoped` for specific BOs, and scope compatibility rules with the EAO |
| 5 | **Implementation Patterns** | Two named patterns with template code (generic vs. specific BO) and injection examples |
| 6 | **Real-World Examples** | `ItSupportToolsBO` and `JobEvidenceBo` as direct references |
| 7 | **Layer Relationship Summary** | ASCII diagram showing the `EJB → BO → EAO → EntityManager` chain with responsibilities per layer |
| 8 | **Development Constraints** | Rules on package, scope, EAO access, `save()` usage, pairing rule (specific BO ↔ specific EAO), and the decision rule for when to create a specific BO |

### User Input

Agora faça o mesmo para a camada de serviços, crie no arquivo 
@[c:\desenv\tedros\github\tedros-apps\skills\data\how-to-create-service.md] 
 o skill necessario para a implementação da camada de serviços representado pelas classes do tipo @[c:\desenv\tedros\github\Tedros\tedrosbox\tdrs-server\tedros-server-api\src\main\java\org\tedros\server\ejb\service\TEjbService.java].

As premissas são as mesmas usadas na camada de negocio, a classe 
@[c:\desenv\tedros\github\tedros-apps\app-itsupport-tools\app-itsupport-tools-ejb\src\main\java\org\tedros\it\tools\ejb\service\ItSupportToolsService.java] 
 é um exemplo de classe generica que pode ser usado para qualquer entidade e a classe @[c:\desenv\tedros\github\tedros-apps\app-itsupport-tools\app-itsupport-tools-ejb\src\main\java\org\tedros\it\tools\ejb\service\JobEvidenceService.java] é uma classe especializada para a entidade JobEvidence.

### Planner Response

Let me read all the relevant files first.

*Viewed [TEjbService.java](file:///c:/desenv/tedros/github/Tedros/tedrosbox/tdrs-server/tedros-server-api/src/main/java/org/tedros/server/ejb/service/TEjbService.java) *

*Viewed [ItSupportToolsService.java](file:///c:/desenv/tedros/github/tedros-apps/app-itsupport-tools/app-itsupport-tools-ejb/src/main/java/org/tedros/it/tools/ejb/service/ItSupportToolsService.java) *

*Viewed [JobEvidenceService.java](file:///c:/desenv/tedros/github/tedros-apps/app-itsupport-tools/app-itsupport-tools-ejb/src/main/java/org/tedros/it/tools/ejb/service/JobEvidenceService.java) *

### Planner Response

I have everything needed. Writing the skill now.

*Edited relevant file*

### Planner Response

The skill file has been written to `how-to-create-service.md`. Here's a summary:

---

## What's in the skill

**8 sections**, consistent with the EAO and BO skills:

| # | Section | What it covers |
|---|---------|----------------|
| 1 | **Objective** | Role of `TEjbService` as the EJB entry point for client calls |
| 2 | **Architectural Placement** | Module (`-ejb`), package (`ejb.service`), and full layer diagram |
| 3 | **`TEjbService<E>`** | The abstract method `getBussinesObject()`, the transaction strategy (`NOT_SUPPORTED` by default, `REQUIRED` for `save`/`remove`), and the full method delegation table |
| 4 | **EJB Configuration Rules** | `@Stateless`, `@LocalBean`, and `@TransactionAttribute` — why each one is needed |
| 5 | **Implementation Patterns** | Two named patterns with template code (generic vs. specific service) |
| 6 | **Real-World Examples** | `ItSupportToolsService` and `JobEvidenceService` as direct references |
| 7 | **Layer Relationship Summary** | Full `Client → Service → BO → EAO → EntityManager` diagram with responsibility table per layer |
| 8 | **Development Constraints** | No-logic rule, transaction override rules, pairing rule (specific service ↔ specific BO), and the decision rule |

A key observation captured in the skill: **`@TransactionAttribute(NOT_SUPPORTED)` must be repeated on the concrete class** — it cannot be relied on solely from the abstract parent at the EJB container level.

### User Input

ficou bom, porem corrija esse trecho "This is the topmost layer of the server-side stack, acting as the entry point for all remote or local calls from the client (JavaFX/REST). It delegates all operations to the business object (BO) and enforces transaction management through EJB annotations."

porque a topmost layer são os controllers que são as classes de entrada, voce pode ver essa classe de exemplo: @[c:\desenv\tedros\github\tedros-apps\app-itsupport-tools\app-itsupport-tools-ejb\src\main\java\org\tedros\it\tools\ejb\controller\JobEvidenceController.java] 

### Planner Response

Let me read the controller file first to understand the full stack.

*Viewed [JobEvidenceController.java](file:///c:/desenv/tedros/github/tedros-apps/app-itsupport-tools/app-itsupport-tools-ejb/src/main/java/org/tedros/it/tools/ejb/controller/JobEvidenceController.java) *

### Planner Response

Now I have the full picture. The correct stack is `Controller → Service → BO → EAO`. Let me fix sections 1, 2, and 7 of the skill file.

*Edited relevant file*

### Planner Response

Fixed. While at it, let me also resolve the two lint warnings about fenced code blocks missing a language specifier.

*Edited relevant file*

*Edited relevant file*

### Planner Response

Done. Three corrections applied:

1. **Section 1 (Objective)** — Replaced "topmost layer / entry point from the client" with the accurate description: the service sits *below the Controller* and *above the BO*, receiving delegated calls from the Controller.

2. **Section 2 (Architectural Placement)** — Layer chain updated from `Client → Service → ...` to `Client → Controller → EJB Service → BO → EAO → EntityManager`.

3. **Section 7 (Layer Relationship Summary)** — Diagram and responsibility table now include the **Controller** row (`@TSecurityInterceptor`, security enforcement, access policy validation, client entry point) above the Service row.

The two lint warnings about missing fenced code block languages were also fixed as a bonus.

### User Input

Agora faça o mesmo para a camada de controle, crie no arquivo 
@[c:\desenv\tedros\github\tedros-apps\skills\data\how-to-create-controller.md] 
 
 o skill necessario para a implementação da camada de controle representado pelas classes do tipo  @[c:\desenv\tedros\github\Tedros\tedrosbox\tdrs-server\tedros-server-api\src\main\java\org\tedros\server\ejb\controller\TSecureEjbController.java] 

Classes de exemplo: 
-  @[c:\desenv\tedros\github\tedros-apps\app-itsupport-tools\app-itsupport-tools-ejb\src\main\java\org\tedros\it\tools\ejb\controller\JobEvidenceController.java] : Classe de entrada sem nenhuma logica necessaria, somente a interface desta classe é disponibilizada para o cliente
- @[c:\desenv\tedros\github\tedros-apps\app-itsupport-tools\app-itsupport-tools-ejb\src\main\java\org\tedros\it\tools\ejb\controller\GmudController.java] e @[c:\desenv\tedros\github\tedros-apps\app-itsupport-tools\app-itsupport-tools-ejb\src\main\java\org\tedros\it\tools\ejb\controller\ProductivityActivityController.java] são classes de controle mais especializadas. somente a interface destas classes é disponibilizada para o cliente.

As interfaces devem obrigatoriamente possuir o atributo JNDI_NAME com o nome que será realizado o lookup jndi. repare que este nome é o nome da interface terminado com o nome "Remote", isto é um requisito obrigatorio.

Classes envolvidas no fluxo:
@[c:\desenv\tedros\github\Tedros\tedrosbox\tdrs-server\tedros-server-client-api\src\main\java\org\tedros\server\result\TResult.java] : Todos os metodos devem retornar este objeto, 
@[c:\desenv\tedros\github\Tedros\tedrosbox\tdrs-server\tedros-server-client-api\src\main\java\org\tedros\server\security\TAccessToken.java] : Todo metodo que tiver esse parametro será verificado se o usuario tem permissão (isso é feito pelo interceptor: @[c:\desenv\tedros\github\Tedros\tedrosbox\tdrs-server\tedros-server-api\src\main\java\org\tedros\server\interceptor\TSecurityInterceptor.java])

### Planner Response

Let me read all the relevant files first.

*Viewed [TSecureEjbController.java](file:///c:/desenv/tedros/github/Tedros/tedrosbox/tdrs-server/tedros-server-api/src/main/java/org/tedros/server/ejb/controller/TSecureEjbController.java) *

*Viewed [GmudController.java](file:///c:/desenv/tedros/github/tedros-apps/app-itsupport-tools/app-itsupport-tools-ejb/src/main/java/org/tedros/it/tools/ejb/controller/GmudController.java) *

*Viewed [ProductivityActivityController.java](file:///c:/desenv/tedros/github/tedros-apps/app-itsupport-tools/app-itsupport-tools-ejb/src/main/java/org/tedros/it/tools/ejb/controller/ProductivityActivityController.java) *

*Viewed [TResult.java](file:///c:/desenv/tedros/github/Tedros/tedrosbox/tdrs-server/tedros-server-client-api/src/main/java/org/tedros/server/result/TResult.java) *

*Viewed [TAccessToken.java](file:///c:/desenv/tedros/github/Tedros/tedrosbox/tdrs-server/tedros-server-client-api/src/main/java/org/tedros/server/security/TAccessToken.java) *

*Viewed [TSecurityInterceptor.java](file:///c:/desenv/tedros/github/Tedros/tedrosbox/tdrs-server/tedros-server-api/src/main/java/org/tedros/server/interceptor/TSecurityInterceptor.java) *

### Planner Response

Now let me also check the interface example and the `JobEvidenceController` to get the interface pattern with `JNDI_NAME`.

*Viewed [IGmudReviewController.java](file:///c:/desenv/tedros/github/tedros-apps/app-itsupport-tools/app-itsupport-tools-ejb-client/src/main/java/org/tedros/it/tools/ejb/controller/IGmudReviewController.java) *

### Planner Response

Now let me also check if there's an `IGmudController` to see the interface with extra methods.

*Grep searched codebase*

### Planner Response

Now let me check the `IProductivityActivityController` to see how a more complex interface looks.

*Viewed [IProductivityActivityController.java](file:///c:/desenv/tedros/github/tedros-apps/app-itsupport-tools/app-itsupport-tools-ejb-client/src/main/java/org/tedros/it/tools/ejb/controller/IProductivityActivityController.java) *

### Planner Response

I have everything I need. Now writing the skill.

*Edited relevant file*

### Planner Response

The skill file has been written to `how-to-create-controller.md`. Here's the summary:

---

## What's in the skill

**10 sections**, the most complete of the series given the richness of this layer:

| # | Section | What it covers |
|---|---------|----------------|
| 1 | **Objective** | Controller as the topmost server layer — security, response wrapping, exception handling |
| 2 | **Architectural Placement** | Dual-module rule: interface in `-ejb-client`, implementation in `-ejb` |
| 3 | **`TSecureEjbController<E>`** | Abstract method `getService()`, full built-in method table with their `@TMethodSecurity` policies, hook methods, and protected utilities (`processException`, `isTheCause`) |
| 4 | **`TResult<T>`** | All constructors, `TState` values table, and when to use each state |
| 5 | **`TAccessToken` & Interceptor** | What the interceptor does step-by-step (token check → bean policy → method policy) |
| 6 | **Controller Annotations** | Full table: `@TSecurityInterceptor`, `@Stateless`, `@TBeanSecurity`, `@TransactionAttribute`, `ITSecurity` |
| 7 | **Controller Interface Rules** | `@Remote`, `ITSecureEjbController<E>`, the mandatory `JNDI_NAME` constant, naming convention |
| 8 | **Implementation Patterns** | Pattern A (simple, no logic) and Pattern B (with `save` override + custom methods) |
| 9 | **Real-World Examples** | `JobEvidenceController` (simple), `GmudController` (business rules in save/remove), `ProductivityActivityController` (additional injected EJBs + complex interface) |
| 10 | **Development Constraints** | `JNDI_NAME` mandatory rule, `ITSecurity` requirement, `TResult` return type rule, exception wrapping rule, no-query-logic rule |

### User Input

ok, ficou bom, porem acho que voce esqueceu de adicionar o exemplo da classe GmudController no item:  Example 2 — Controller with business rules (GmudController)

*Viewed [how-to-create-controller.md](file:///c:/desenv/tedros/github/tedros-apps/skills/data/how-to-create-controller.md) *

### Planner Response

Found it — lines 368-378. I'll replace the text-only description with the full `GmudController` code.

*Edited relevant file*

### Planner Response

Done. Let me also fix the two lint warnings while I'm at it.

*Edited relevant file*

*Viewed [how-to-create-controller.md](file:///c:/desenv/tedros/github/tedros-apps/skills/data/how-to-create-controller.md) *

### Planner Response

The MD032 lint is a false positive — line 373 is inside a fenced code block (` ```java `), so surrounding annotations aren't markdown lists. All good. The important changes are done.

**Summary of what was updated:**

Example 2 now contains the **full `GmudController` code**, including:
- Class declaration with all required annotations (`@TSecurityInterceptor`, `@Stateless`, `@TBeanSecurity`, `@TransactionAttribute`)
- `getService()` and `getSecurityController()` implementations
- `save()` override — reads current DB state, validates final-status immutability, sets initial `DRAFT` status for new records, recalculates status before delegating to `super.save()`
- `remove()` override — validates the record isn't in `EXECUTING`/`FINISHED` state before delegating to `super.remove()`
- Private helper methods (`recalculateGmudStatus`, `isFinalStatus`, `isExecutionPhase`)
- Its `IGmudController` interface with `JNDI_NAME`
- A callout block highlighting the four key rules to follow

### User Input

Ok, agora vamos criar o skill @[c:\desenv\tedros\github\tedros-apps\skills\data\how-to-define-security-id-names.md]  que orienta o agente de IA a definir e usar corretamente as security ids usados tanto no frontend como no backend.

contexto:

Todo projeto precisa conter uma classe do tipo @[c:\desenv\tedros\github\tedros-apps\app-itsupport-tools\app-itsupport-tools-model\src\main\java\org\tedros\it\tools\domain\DomainApp.java] que contem todos os identificadores de segurança que serão usados para saber se o usuario pode ou não acessar uma funcionalidade do lado do backend, ou do lado do frontend (tela, formulario, modulo ou aplicativo)

Exemplo: A tela CRUD para a entidade @[c:\desenv\tedros\github\tedros-apps\app-itsupport-tools\app-itsupport-tools-model\src\main\java\org\tedros\it\tools\entity\JobEvidence.java] é representada no frontend pela  classe @[c:\desenv\tedros\github\tedros-apps\app-itsupport-tools\app-itsupport-tools-fx\src\main\java\org\tedros\it\tools\module\evidence\model\CreateJobEvidenceMV.java] e o controller desta entidade é a classe @[c:\desenv\tedros\github\tedros-apps\app-itsupport-tools\app-itsupport-tools-ejb\src\main\java\org\tedros\it\tools\ejb\controller\JobEvidenceController.java] , repare que no controller tem a anotação "@TBeanSecurity({@TBeanPolicie(id = DomainApp.EVIDENCE_MANAGER_FORM_ID, 
policie = { TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS })})" que define DomainApp.EVIDENCE_MANAGER_FORM_ID como security id e na classe  CreateJobEvidenceMV tem a anotação "@TSecurity(id=DomainApp.EVIDENCE_MANAGER_FORM_ID, appName = ItToolsKey.APP_ITSUPPORT,
	moduleName = ItToolsKey.MODULE_ITSUPPORT_EVIDENCE, viewName = ItToolsKey.VIEW_JOB_EVIDENCE,
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})" que tambem define DomainApp.EVIDENCE_MANAGER_FORM_ID como security id, ou seja somente os usuario (@[c:\desenv\tedros\github\Tedros\tedrosbox\tdrs-server\tedros-core-model\src\main\java\org\tedros\core\security\model\TUser.java] ) que tiverem um @[c:\desenv\tedros\github\Tedros\tedrosbox\tdrs-server\tedros-core-model\src\main\java\org\tedros\core\security\model\TProfile.java] que contenha um @[c:\desenv\tedros\github\Tedros\tedrosbox\tdrs-server\tedros-core-model\src\main\java\org\tedros\core\security\model\TAuthorization.java] com o atributo securityId igual ao valor atribuido para a constante DomainApp.EVIDENCE_MANAGER_FORM_ID terá acesso tanto para acessar o fomulario via tela como para chamar os metodos no controller. 

Isso é feito em varios niveis: Acesso a formulario, tela, modulo e aplicativo, por exemplo:

@[c:\desenv\tedros\github\tedros-apps\app-itsupport-tools\app-itsupport-tools-fx\src\main\java\org\tedros\it\tools\start\AppStart.java]:  utiliza o security id DomainApp.MNEMONIC
@[c:\desenv\tedros\github\tedros-apps\app-itsupport-tools\app-itsupport-tools-fx\src\main\java\org\tedros\it\tools\module\evidence\JobEvidenceModule.java] : utiliza o security id  DomainApp.EVIDENCE_MANAGER_MODULE_ID
@[c:\desenv\tedros\github\tedros-apps\app-itsupport-tools\app-itsupport-tools-fx\src\main\java\org\tedros\it\tools\module\evidence\model\CreateJobEvidenceMV.java] : utiliza o security id  DomainApp.EVIDENCE_MANAGER_FORM_ID tanto para validar a permissão de acesso a tela como para acesso ao formulario.

### Planner Response

Let me read all the relevant files to understand the full security model.

*Viewed [DomainApp.java](file:///c:/desenv/tedros/github/tedros-apps/app-itsupport-tools/app-itsupport-tools-model/src/main/java/org/tedros/it/tools/domain/DomainApp.java) *

*Viewed [AppStart.java](file:///c:/desenv/tedros/github/tedros-apps/app-itsupport-tools/app-itsupport-tools-fx/src/main/java/org/tedros/it/tools/start/AppStart.java) *

*Viewed [JobEvidenceModule.java](file:///c:/desenv/tedros/github/tedros-apps/app-itsupport-tools/app-itsupport-tools-fx/src/main/java/org/tedros/it/tools/module/evidence/JobEvidenceModule.java) *

*Viewed [CreateJobEvidenceMV.java](file:///c:/desenv/tedros/github/tedros-apps/app-itsupport-tools/app-itsupport-tools-fx/src/main/java/org/tedros/it/tools/module/evidence/model/CreateJobEvidenceMV.java) *

*Viewed [TUser.java](file:///c:/desenv/tedros/github/Tedros/tedrosbox/tdrs-server/tedros-core-model/src/main/java/org/tedros/core/security/model/TUser.java) *

*Viewed [TProfile.java](file:///c:/desenv/tedros/github/Tedros/tedrosbox/tdrs-server/tedros-core-model/src/main/java/org/tedros/core/security/model/TProfile.java) *

*Viewed [TAuthorization.java](file:///c:/desenv/tedros/github/Tedros/tedrosbox/tdrs-server/tedros-core-model/src/main/java/org/tedros/core/security/model/TAuthorization.java) *

### Planner Response

I have everything I need. Writing the skill now.

*Edited relevant file*

### Planner Response

The skill file has been written to `how-to-define-security-id-names.md`. Here's a summary:

---

## What's in the skill

**8 sections** covering the full security ID lifecycle:

| # | Section | What it covers |
|---|---------|----------------|
| 1 | **Objective** | What Security IDs are and why they must match on both frontend and backend |
| 2 | **How It Works** | The full chain: `TUser → TProfile → TAuthorization.securityId` with a diagram |
| 3 | **`DomainApp` Interface** | Module placement, naming convention (`MNEMONIC_FEATURE_LEVEL`), level definitions table, and a complete template |
| 4 | **Where Each ID Is Used** | Four annotated code examples: `AppStart` (`MNEMONIC`), Module (`_MODULE_ID`), ModelView (`_FORM_ID`), and Controller (`_FORM_ID`) |
| 5 | **Complete ID-to-Usage Map** | Real values from IT Support Tools with a table showing each constant, its resolved string, where it's used, and what access type it grants |
| 6 | **Authorization Data Model** | `TAuthorization` fields explained with the runtime check diagram |
| 7 | **Access Hierarchy** | The four-level cascade (`APP_ACCESS → MODULE_ACCESS → VIEW_ACCESS → EDIT/SAVE/DELETE/NEW`) |
| 8 | **Development Constraints** | Rules on single source of truth, unique MNEMONIC, format, backend/frontend parity, module placement, and granularity |

The key insight captured: the `_FORM_ID` constant **must appear identically** in `@TSecurity` on the ModelView AND in `@TBeanSecurity` on the Controller — a mismatch silently breaks the security chain on one side.