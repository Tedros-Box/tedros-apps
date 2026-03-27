# Tedros Framework — Visão Geral Completa

> **Objetivo deste documento:** Servir como guia de referência para desenvolvedores humanos e agentes de IA que precisam entender a arquitetura, a responsabilidade de cada módulo e os padrões de desenvolvimento do framework Tedros (projeto `tedrosbox`).

---

## 1. O que é o Tedros Framework?

O **Tedros** é um framework Java de aplicação desktop construído sobre **JavaFX 17** no lado cliente e **Apache TomEE (Jakarta EE 9 / EJB 3.2)** no lado servidor. Ele fornece uma plataforma completa — com autenticação, autorização, UI gerada por anotações, acesso remoto transparente via JNDI/EJB e suporte a IA — para o desenvolvimento de aplicações corporativas modulares.

### Tecnologias centrais

| Camada           | Tecnologia principal                                    |
|------------------|---------------------------------------------------------|
| Cliente (UI)     | JavaFX 17, CSS Caspian customizado                      |
| Servidor         | Apache TomEE 9.x, Jakarta EJB 3.2, CDI                 |
| Persistência     | JPA / EclipseLink 4.x, banco H2 (embutido ou servidor) |
| Comunicação      | JNDI remoto (chamadas EJB via HTTP `tomee/ejb`)         |
| Mensageria       | Apache ActiveMQ 5.x                                     |
| IA               | Integração com OpenAI (cliente `openai-client 0.18.x`)  |
| Relatórios       | JasperReports 7.x                                       |
| Serialização     | Jackson 2.17.x                                          |

---

## 2. Estrutura de módulos Maven (tedrosbox)

O POM raiz (`tedrosbox`) agrega cinco grandes grupos de módulos:

```
tedrosbox/                      ← POM pai (groupId: org.tedros, version: 17-0.0.1)
├── tdrs-box/                   ← Núcleo cliente (JavaFX)
├── tdrs-fx/                    ← API de UI/widgets JavaFX
├── tdrs-miscellaneous/         ← Utilitários genéricos
├── tdrs-server/                ← Camadas servidor (EJB, JPA, API REST-like)
└── app-chat/                   ← Módulo de chat entre os usuarios do sistema
```

---

## 3. Grupo `tdrs-box` — Núcleo da Aplicação Cliente

### 3.1 `tedros-box`

**Pacote raiz:** `org.tedros`  
**Classe principal:** `TedrosBox` (extends `javafx.application.Application`)

É o **shell/container da aplicação JavaFX**. Responsável por:

- Inicializar o **Stage** JavaFX com título, ícone e estilo `UNDECORATED`.
- Construir o layout principal: barra superior (`ToolBar`), área de página (`Pane`), menu lateral deslizante (`TSliderMenu`), barra de breadcrumb.
- Gerenciar **login/logout** — ao inicializar, exibe o módulo de login via `TDynaView<LoginMV>`.
- Orquestrar os managers internos:
  - `TedrosNavigationManager` — histórico de páginas (back/forward), navegação para módulos.
  - `TedrosLogoManager` — exibição e animação do logo.
  - `TedrosPopOverManager` — pop-overs de usuário, informações, chat, IA (Teros).
- Registrar os `ResourceBundles` de i18n (`TedrosLoginLabels`, `TCoreLabels`, `TFx`, `TUsual`).
- Aplicar CSS (`caspian.css`, `immutable-styles.css`, CSS de tema customizável).
- Escutar propriedades reativas do `TedrosContext` para exibir modais de mensagem, recarregar estilo ou mostrar a lista de notificações.

**`TWindow`** — janela secundária destacável (detach). Permite que qualquer `ITModule` seja exibido em um novo `Stage` JavaFX independente, com a mesma identidade visual do box principal.

### 3.2 `tedros-core`

**Pacote raiz:** `org.tedros.core`

Biblioteca de núcleo do lado cliente. Contém as **abstrações e contratos** que toda aplicação Tedros usa:

| Subpacote | Conteúdo |
|-----------|----------|
| `core` (raiz) | `ITModule`, `TModule`, `ITedrosBox`, `TCoreKeys`, `TLanguage` (i18n) |
| `core.annotation` | Anotações de configuração de módulo, visão, formulário |
| `core.context` | `TedrosContext` (singleton global), `TedrosAppManager`, `Page`, `Pages` |
| `core.control` | Controles JavaFX customizados (`PopOver`, breadcrumb, resize bar) |
| `core.model` | `ITModelView`, `TFormatter` |
| `core.repository` | `TRepository` (repositório genérico de listeners) |
| `core.service` | Interfaces de serviço cliente |
| `core.style` | `TThemeUtil`, `TStyleResourceValue` — gerenciamento de temas CSS |
| `core.task` | Tarefas em background |
| `core.user` | Gerenciamento de sessão de usuário |
| `core.ux` | `ITWindow` |

**`TedrosContext`** é o ponto central compartilhado por todo o lado cliente. Ele mantém referência à aplicação `TedrosBox`, ao usuário logado, ao módulo corrente, às janelas abertas e às propriedades reativas usadas para comunicação entre componentes.

**`TLanguage`** — motor de internacionalização. Carrega `ResourceBundles` e reexpõe strings com suporte a formatação positional (`#{chave}`).

### 3.3 `tedros-box-app-base`

**Pacote raiz:** `org.tedros.app`

Base de componentes e processos reutilizáveis para as aplicações concretas. Contém:

- `component/` — componentes de UI prontos para uso.
- `process/` — implementações de fluxo/processo de negócio.

É a camada "utilitária de UI de alto nível" que as extensões de aplicação (`app-extensions`, `app-samples`, etc.) herdam.

### 3.4 `tedros-box-app-settings`

**Pacote raiz:** `org.tedros.app` (settings)

Módulo de **configurações globais da aplicação** dentro do shell. Permite ao usuário ajustar parâmetros de sistema, tema visual e conexão JNDI.

---

## 4. Grupo `tdrs-fx` — API de UI JavaFX

### 4.1 `tedros-fx-api`

**Pacote raiz:** `org.tedros.fx`

Biblioteca que fornece **todos os componentes visuais, anotações de declaração de formulários e o motor de binding Model↔View**. É o coração do lado cliente.

#### Subpacotes principais

| Subpacote | Responsabilidade |
|-----------|------------------|
| `fx.annotation` | ~30 anotações declarativas para configurar controles em `TModelView` |
| `fx.builder` | Construtores de UI a partir de anotações |
| `fx.component` | Componentes compostos de alto nível |
| `fx.control` | Controles JavaFX específicos do Tedros (`TLabel`, etc.) |
| `fx.converter` | Conversores de tipos para binding |
| `fx.descriptor` | Descritores de campo/formulário |
| `fx.domain` | Tipos de domínio da camada de apresentação |
| `fx.form` | Classes de formulário genérico |
| `fx.layout` | Layouts customizados (ex.: `TSliderMenu`) |
| `fx.modal` | Caixas de diálogo (`TMessageBox`, `TConfirmMessageBox`, `TModalPane`) |
| `fx.model` | **`TModelView`**, `TEntityModelView`, `TListenerHelper` |
| `fx.presenter` | Presenters de visão dinâmica (`TDynaView`) |
| `fx.process` | Processos da UI (ex.: validação, submissão) |
| `fx.reader` | Leitura de dados para popular widgets |
| `fx.scene` | Gerenciamento de cena |
| `fx.util` | Utilitários de reflexão e propriedade |

#### `TModelView<M extends ITModel>` — o padrão central

`TModelView` é a **classe mais importante do lado cliente**. Ela implementa o padrão *ViewModel* do Tedros:

- Recebe no construtor um objeto de modelo (`ITModel` ou `ITEntity`) e **via reflexão** vincula automaticamente cada campo JavaFX `Property` ao campo correspondente do modelo.
- Detecta mudanças pelo `hashCode` do modelo — `isChanged()` retorna `true` se algum campo foi alterado desde o último carregamento.
- Suporta coleções observáveis (`ObservableList`, `ObservableSet`, `ObservableMap`) com listeners que propagam mudanças de volta ao modelo.
- `TEntityModelView<E extends ITEntity>` — especialização que adiciona a propriedade `id` ao binding.

**Regra para agentes de IA:** Ao criar um `TModelView` para uma entidade do servidor, declare um campo JavaFX `Property` com o **mesmo nome** do campo na entidade. O motor de binding faz a conexão por reflexão. Use a anotação `@TGenericType(modelView=..., model=...)` em campos de coleção de modelos aninhados.

#### Anotações de formulário (`fx.annotation`)

As anotações decoram o `TModelView` e controlam como a interface é gerada:

- `@TForm` / `@TView` — declaram o formulário/visão completa.
- `@TPresenter(...)` — define o tipo de presenter.
- Anotações de controle: `@TTextField`, `@TComboBox`, `@TDatePicker`, `@TCheckBox`, `@TTextArea`, etc.
- `@TPanel`, `@TTab` — layout de agrupamento.
- `@TQuery(...)` — configura a consulta padrão do formulário de listagem.
- `@TProcess(...)` — vincula um processo de negócio ao botão de ação.

**Regra para agentes de IA:** Para criar uma nova tela completa no Tedros, basta criar:

1. Uma entidade (`ITEntity`) no servidor.
2. Um `TModelView` decorado com anotações de formulário no módulo cliente.
3. O controlador EJB remoto correspondente.

---

## 5. Grupo `tdrs-server` — Camada Servidor

### 5.1 `tedros-server-client-api`

**Pacote raiz:** `org.tedros.server`

API **compartilhada entre cliente e servidor**. Todo artefato aqui deve ser serializável e não ter dependências de runtime do servidor. Contém:

| Classe/Interface | Descrição |
|------------------|-----------|
| `TResult<E>` | Envelope padrão de retorno de **todos** os métodos EJB. Carrega `TState` (SUCCESS, ERROR, WARNING, OUTDATED, NO_RESULT), mensagem opcional e valor tipado. |
| `TAccessToken` | Token de sessão serializado passado em toda chamada EJB remota para autenticar e autorizar o usuário. |
| `ITEntity` | Interface base para todas as entidades JPA expostas remotamente. |
| `ITModel` | Interface base para modelos não-entidade. |
| `ITSecureEjbController<E>` | Contrato do controlador EJB seguro. |
| `TSelect<E>` | Objeto de consulta parametrizada. |
| `TMethodSecurity`, `TMethodPolicie`, `TActionPolicie` | Anotações de segurança por método. |
| `TSecurityInterceptor` | Anotação `@InterceptorBinding` para ativar controle de acesso CDI. |
| `TBeanSecurity`, `TBeanPolicie` | Segurança em nível de bean. |
| `ITEjbService<E>` | Contrato do serviço de persistência. |

**Regra para agentes de IA:**

- **Todo método EJB remoto deve retornar `TResult<T>`.**
- **Todo método que recebe uma chamada remota autenticada deve receber `TAccessToken` como primeiro parâmetro.**
- Nunca lance exceções não tratadas: o controlador as captura e converte em `TResult` com `TState.ERROR`.

### 5.2 `tedros-server-api`

**Pacote raiz:** `org.tedros.server`

Implementações e contratos internos do servidor (não expostos ao cliente diretamente).

#### Segurança

| Classe | Papel |
|--------|-------|
| `TSecurityInterceptor` | `@InterceptorBinding` — marca métodos/beans para interceptação de segurança |
| `TMethodSecurity` | Anotação de método com lista de `TMethodPolicie` |
| `TMethodPolicie` | Define quais `TActionPolicie` (EDIT, READ, SEARCH, SAVE, NEW, DELETE) são permitidas |
| `TAccessPolicie`, `TActionPolicie` | Constantes de políticas de acesso |
| `ITSecurity` | Interface do serviço de segurança |

#### Controladores EJB (abstratos)

| Classe | Papel |
|--------|-------|
| `TSecureEjbController<E>` | **Implementação abstrata principal** de um controlador EJB com segurança. Fornece: autenticação via `TAccessToken`, tratamento centralizado de exceções (`processException`), hooks `processEntity` / `processEntityList`, suporte a paginação. |
| `TEjbController<E>` | Controlador mais simples, sem controle de segurança. |
| `TEjbImportController<E>` | Especialização para operações de importação em lote. |

**Regra para agentes de IA — criar um controlador:**

1. Crie uma interface em `tedros-core-ejb-client` que estenda `ITSecureEjbController<E>` (ou declare métodos adicionais).
2. O nome JNDI **obrigatório** é `<NomeDaInterface>Remote` (ex.: `ICityControllerRemote`).
3. Crie a implementação em `tedros-core-ejb` estendendo `TSecureEjbController<E>`.
4. Adicione `@Stateless`, `@TransactionAttribute` e implemente `getService()` retornando o `ITEjbService<E>` correspondente.
5. Todos os métodos públicos remotos devem retornar `TResult<?>`.

### 5.3 `tedros-core-model`

**Pacote raiz:** `org.tedros.core`

Entidades JPA e domínio do núcleo do framework (usuários, perfis, segurança, configurações, IA).

#### Subpacotes e entidades

| Subpacote | Entidades / Conteúdo |
|-----------|----------------------|
| `core.security.model` | `TUser` — usuário com `TAccessToken`, credenciais e `TProfile` ativo |
| `core.security.model` | `TProfile` — perfil de acesso com lista de `TAuthorization` |
| `core.security.model` | `TAuthorization` — par (securityId, tipo de ação) que representa uma permissão |
| `core.security.model` | `TAuditLog` — log de auditoria de ações do usuário |
| `core.domain` | `DomainApp` — interface com constantes de IDs de módulo, form e view do núcleo |
| `core.domain` | `DomainTables`, `DomainSchema` — mapeamento de tabelas JPA |
| `core.domain` | `TSystemPropertie` — propriedades de sistema persistidas |
| `core.ai.model` | Modelos para integração com IA (respostas de completion, chat, imagem) |
| `core.notify` | Entidade de notificação de sistema |
| `core.setting` | Configurações persistidas de aplicação |

#### Fluxo de autenticação e autorização

```
Cliente → TLoginController.login(usuario, senha)
       ↓
Servidor: TLoginController → TSecurityService.addUser(TUser)
       ↓ retorna TAccessToken
Cliente armazena o token na sessão
       ↓
Cada chamada subsequente passa TAccessToken
       ↓
TSecurityService.isAssigned(token) / isActionGranted(token, securityId, action)
       ↓
Interceptor / TSecureEjbController valida antes de executar o método
```

`TSecurityService` é um `@Singleton @Startup` que mantém em memória a lista de usuários logados (`List<TUser>`) com seus tokens e perfis. Suporta leitura concorrente (`@Lock(READ)`) e escrita exclusiva (`@Lock(WRITE)`).

### 5.4 `tedros-common-model`

**Pacote raiz:** `org.tedros.common`

Entidades e modelos **reutilizáveis por qualquer módulo de aplicação**, não exclusivos do núcleo:

| Classe | Descrição |
|--------|-----------|
| `TFileEntity` | Entidade para armazenamento de arquivos binários no banco |
| `TMimeType` | Tipo MIME registrado no sistema |
| `TByteEntity` | Blob de bytes genérico |
| `TBarcode` | Suporte a geração de código de barras |
| `TFileContentInfo` | Metadados de arquivo |
| `TMimeTypeImport` | Importação de tipos MIME |

### 5.5 `tedros-core-ejb`

**Pacote raiz:** `org.tedros.core.ejb`

**Implementações concretas dos EJBs do núcleo** que rodam no TomEE.

#### Controladores implementados

| Controlador | Responsabilidade |
|-------------|-----------------|
| `TLoginController` | Autenticação de usuários, geração e invalidação de `TAccessToken` |
| `TAuthorizationControllerImpl` | CRUD de autorizações/permissões |
| `TProfileControllerImpl` | CRUD de perfis de acesso |
| `TUserControllerImpl` | CRUD de usuários |
| `TPropertieControllerImpl` | CRUD de propriedades de sistema |
| `TFileEntityControllerImpl` | CRUD de arquivos binários |
| `TMimeTypeControllerImpl` | CRUD de tipos MIME |
| `TMimeTypeImportControllerImpl` | Importação em lote de tipos MIME |
| `TNotifyControllerImpl` | CRUD de notificações |
| `TProfileChartControllerImpl` | Dados de gráfico de perfil |
| `TAiChatCompletionControllerImpl` | Chat completion via IA (OpenAI) |
| `TAiChatMessageControllerImpl` | Histórico de mensagens de chat com IA |
| `TAiCompletionControllerImpl` | Text completion via IA |
| `TAiCreateImageControllerImpl` | Geração de imagens via IA |
| `TEmailController` | Envio de e-mail |
| `TSecurityController` | Verificação de segurança/autorização |

#### Serviços implementados

Cada controlador delega a lógica de persistência a um `@Stateless` service que implementa `ITEjbService<E>`:

`TAiChatCompletionService`, `TAuthorizationService`, `TFileEntityService`, `TPropertieService`, `TSecurityService` (singleton), `TUserService`, `TNotifyService`, etc.

#### Startup / Timer

- `startup/` — beans que rodam na inicialização do servidor (aplicam dados iniciais, migrações).
- `timer/` — beans `@Schedule` para tarefas periódicas.

### 5.6 `tedros-core-ejb-client`

**Pacote raiz:** `org.tedros.core.controller`

Interfaces dos controladores EJB **usadas pelo cliente JavaFX para lookup JNDI**. Cada interface estende `ITSecureEjbController` ou declara métodos remotos adicionais:

`ITLoginController`, `ITEmailController`, `TAiChatCompletionController`, `TAuthorizationController`, `TFileEntityController`, `TMimeTypeController`, `TNotifyController`, `TProfileController`, `TPropertieController`, `TUserController`, `TProfileChartController`, e variantes de IA.

**Convenção JNDI obrigatória:**

```java
// Interface no módulo client:
public interface ICidadeController extends ITSecureEjbController<Cidade> { }

// Lookup no cliente:
// nome JNDI = nome da interface + "Remote"
// ex.: "ICidadeControllerRemote"
```

### 5.7 `tedros-core-ejb-ear`

Empacotador EAR do núcleo do servidor. Agrega os JARs `tedros-core-ejb` e `tedros-core-ejb-client` para deploy no TomEE.

---

## 6. Grupo `tdrs-miscellaneous` — Utilitários

### 6.1 `tedros-util`

**Pacote raiz:** `org.tedros.util`

Utilitários genéricos usados por todos os outros módulos:

- `TLoggerUtil` — wrapper sobre SLF4J/Logback com medição de complexidade temporal.
- `TedrosFolder` — enumeração de pastas do sistema Tedros (módulos, CSS, config, etc.).
- Outros helpers de I/O e reflexão.

---

## 7. Módulo `app-chat` — Chat Integrado com IA

Módulo completo de chat empresarial com integração de IA. Estrutura em camadas:

| Submódulo | Responsabilidade |
|-----------|-----------------|
| `app-chat-model` | Entidades JPA de chat (mensagem, sala, participante) |
| `app-chat-ejb` | Implementações dos EJBs de chat (backend TomEE) |
| `app-chat-ejb-client` | Interfaces dos controladores (lookup JNDI) |
| `app-chat-ejb-ear` | Empacotamento EAR para deploy |
| `app-chat-fx` | Módulo JavaFX de interface de chat (usa `tedros-fx-api`) |
| `app-chat-server` | Servidor de chat (push, WebSocket ou mensageria ActiveMQ) |

---

## 8. Padrões de Arquitetura — Guia Rápido para Agentes de IA

### 8.1 Criar um novo módulo de negócio (tela completa)

1. **Entidade servidor** (`ITEntity`, com `@Entity` JPA):

   ```java
   @Entity @Table(name = "TB_CIDADE")
   public class Cidade implements ITEntity { Long id; String nome; ... }
   ```

2. **Modelo de visão cliente** (extends `TEntityModelView<Cidade>`):

   ```java
   @TForm(header = LocatKey.FORM_KEEP_UPDATE, showBreadcrumBar=true, scroll=false)
   @TEjbService(serviceName = ICidadeController.JNDI_NAME, model=Cidade.class)
   @TListViewPresenter(presenter=@TPresenter(decorator = @TDecorator(viewTitle=LocatKey.VIEW_CITY, buildImportButton=true),
    behavior=@TBehavior(importModelViewClass=CidadeImportMV.class, runNewActionAfterSave=true)))
   @TSecurity(id=DomainApp.CITY_FORM_ID, allowedAccesses={VIEW_ACCESS, EDIT, SAVE, DELETE, NEW},
    appName = LocatKey.APP_LOCATION_NAME, moduleName = LocatKey.MODULE_CITIES, viewName = LocatKey.VIEW_CITY)
   public class CidadeMV extends TEntityModelView<Cidade> {
       private SimpleStringProperty nome;
       // + getter/setter
   }

   ```

3. **Interface do controlador** (módulo `ejb-client`):

   ```java
   @Remote
   public interface ICidadeController extends ITSecureEjbController<Cidade> { 
    static final String JNDI_NAME = "ICidadeControllerRemote";
   }
   
   ```

4. **Implementação EJB** (`tedros-core-ejb` do módulo de negócio):

   ```java
   @TSecurityInterceptor
   @Stateless(name="ICidadeController")
   @TBeanSecurity({@TBeanPolicie(id = DomainApp.CITY_FORM_ID, 
     policie = { TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS })})
   @TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
   public class CidadeController extends TSecureEjbController<Cidade> implements ICidadeController, ITSecurity {
       @EJB CidadeService service;
       @Override protected ITEjbService<Cidade> getService() { return service; }
   }

   ```

5. **Serviço de persistência** (implementa `ITEjbService<Cidade>`).

### 8.2 Diagrama de camadas

```

┌──────────────────────────────────────────────────────┐
│  CLIENTE (JavaFX)                                    │
│  TedrosBox → TModule → TDynaView → TModelView(anot.) │
│                           ↓ JNDI lookup              │
├──────────────────────────────────────────────────────┤
│  INTERFACE COMPARTILHADA                              │
│  tedros-server-client-api                             │
│  TResult, TAccessToken, ITEntity, ITSecureEjbController │
├──────────────────────────────────────────────────────┤
│  SERVIDOR (TomEE)                                    │
│  TSecureEjbController → Service → JPA Repository     │
│  TSecurityService (Singleton) — validação de token   │
│  TLoginController — autenticação                     │
└──────────────────────────────────────────────────────┘

```

### 8.3 Regras invariantes do framework

| Regra | Detalhe |
|-------|---------|
| **Retorno EJB** | Todo método remoto retorna `TResult<T>`. Nunca lance exceção para o cliente. |
| **Segurança** | Todo método de negócio remoto recebe `TAccessToken` como 1º parâmetro. |
| **JNDI** | O nome do bean no lookup é sempre `<NomeDaInterface>Remote`. |
| **Controlador** | Sempre estear de `TSecureEjbController<E>` e implementar `getService()`. |
| **Model View** | Campos em `TModelView` devem ter o **mesmo nome** dos campos na entidade. |
| **Coleções** | Campos de coleção de modelos em `TModelView` exigem `@TGenericType`. |
| **Módulo cliente** | Um módulo JavaFX implementa `ITModule` e expõe `tStart()` / `tStop()`. |
| **Internacionalização** | Textos de UI usam `TLanguage.getInstance().getString("chave")`. |

---

## 9. Referência Rápida de Classes Chave

| Classe | Módulo | Para que serve |
|--------|--------|---------------|
| `TedrosBox` | `tedros-box` | Ponto de entrada da aplicação JavaFX |
| `TedrosContext` | `tedros-core` | Singleton global do lado cliente |
| `TLanguage` | `tedros-core` | Internacionalização (i18n) |
| `TModule` | `tedros-core` | Base de um módulo de aplicação |
| `TModelView<M>` | `tedros-fx-api` | Motor de binding Model↔View |
| `TEntityModelView<E>` | `tedros-fx-api` | ModelView para entidades JPA |
| `TResult<E>` | `tedros-server-client-api` | Envelope de retorno EJB |
| `TAccessToken` | `tedros-server-client-api` | Token de sessão autenticada |
| `ITEntity` | `tedros-server-client-api` | Contrato de entidade JPA remota |
| `TSecureEjbController<E>` | `tedros-server-api` | Base abstrata de controladores seguros |
| `TSecurityService` | `tedros-core-ejb` | Gerenciamento de sessões em memória |
| `TSecurityInterceptor` | `tedros-server-client-api` | Anotação CDI de interceptação de segurança |
| `DomainApp` | `tedros-core-model` | IDs de módulos/forms/views do núcleo |

---

## 10. Estrutura de Deploy

```

TomEE 9.x
└── apps/
    ├── tedros-core-ejb-ear.ear      ← núcleo do framework
    └── <app>-ejb-ear.ear            ← cada módulo de negócio

```

O cliente JavaFX acessa o servidor via:

```

http://<host>:8080/tomee/ejb

```

configurado na propriedade `tomee.ejb.url` do POM raiz.

Para Docker, os EARs são copiados para `docker.app.folder` definido no POM.

---

*Documento gerado com base na análise do código-fonte do projeto `tedrosbox` versão `17-0.0.1`.*
