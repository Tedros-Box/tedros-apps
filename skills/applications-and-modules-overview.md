---
name: applications-and-modules-overview
description: >
  Visão geral da arquitetura do sistema Tedros para criação de aplicativos, módulos e telas (views).
  Explica o ciclo de vida completo: da descoberta de apps pelo contexto até a exibição na TreeView
  e a navegação entre telas pelo usuário.
---

# Visão Geral: Aplicativos, Módulos e Telas no Tedros

## 1. Conceitos Fundamentais

O sistema Tedros é uma plataforma JavaFX modular. Cada **aplicativo** é composto de um ou mais **módulos**, e cada módulo expõe uma ou mais **telas (views)**. O menu lateral (TreeView) exibe a hierarquia de todos os aplicativos e módulos registrados.

```
TedrosBox (Shell JavaFX)
└── Menu Lateral (TreeView)
    └── AllPagesPage                  ← raiz oculta
        └── CategoryPage ("Módulos")  ← agrupador
            └── CategoryPage <App>    ← grupo por menu/@TModule.menu()
                └── InternalViewPage  ← item de menu por módulo
```

---

## 2. Como Criar um Novo Aplicativo

### 2.1 – A classe `AppStart` (ponto de entrada do aplicativo)

Toda aplicação começa com uma classe anotada com `@TApplication`. Ela implementa `ITApplication` e é o ponto central onde todos os módulos são declarados.

```java
@TApplication(
    name = "minha.app.key",           // chave i18n do nome do app
    packageName = "com.mycompany.app",// pacote raiz (usado para segurança)
    universalUniqueIdentifier = "MINHA-UUID-AQUI",
    module = {
        @TModule(
            type = MeuModulo.class,   // classe que estende TModule
            name = "minha.module.key",// chave i18n do nome do módulo
            menu = "minha.menu.key",  // chave i18n do grupo no menu
            description = "minha.module.desc",
            icon = "icons/meu-modulo.png",      // ícone no painel central
            menuIcon = "icons/meu-modulo-menu.png" // ícone no menu lateral
        )
    }
)
@TResourceBundle(resourceName = {"meuapp"}) // bundle de i18n
@TSecurity(id = "MINHA_APP_ID",
    appName = "minha.app.key",
    allowedAccesses = TAuthorizationType.APP_ACCESS)
public class AppStart implements ITApplication {

    @Override
    public void start() {
        // Executado na inicialização do app (após login)
        // Inicializar resources, serviços em background, etc.
        AppResource.createResource();
    }

    @Override
    public void stop() {
        // Executado no logout e encerramento do sistema
    }
}
```

> **Regra importante:** A anotação `@TApplication` é o que o sistema usa para
> descobrir o app automaticamente via reflection (`TedrosContext.searchApps()`).
> Ela DEVE existir na classe `AppStart` do aplicativo.

**Exemplo real:** `org.tedros.it.tools.start.AppStart`

---

## 3. Como Criar um Módulo

Cada módulo é uma classe que **estende `TModule`** e é anotada com `@TView` para declarar suas telas.

### 3.1 – A classe do Módulo

```java
@TView(items = {
    @TItem(
        title = "minha.view.key",               // título i18n da aba/tela
        description = "minha.view.desc.key",    // tooltip i18n
        modelView = MeuModelView.class,          // classe ModelView (ViewModel)
        model = MinhaEntidade.class              // entidade JPA/model
    ),
    @TItem(
        title = "outra.view.key",
        modelView = OutroModelView.class,
        model = OutraEntidade.class
    )
})
@TSecurity(
    id = "MINHA_MODULE_SECURITY_ID",
    appName = "minha.app.key",
    moduleName = "minha.module.key",
    allowedAccesses = TAuthorizationType.MODULE_ACCESS
)
public class MeuModulo extends TModule {
    // Corpo vazio: o @TView configura tudo.
    // Só adicione código aqui se precisar de lógica extra ao iniciar/parar.
}
```

### 3.2 – Relação entre `@TModule` (na AppStart) e `@TView` (no Módulo)

| Anotação           | Onde fica          | O que define                                          |
|--------------------|--------------------|-------------------------------------------------------|
| `@TApplication`    | `AppStart`         | O app inteiro: nome, UUID, lista de módulos, segurança |
| `@TModule`         | Dentro de `@TApplication` | Nome, menu, ícones e **classe** do módulo           |
| `@TView`           | Na classe do módulo| Lista de telas (`@TItem`) que o módulo expõe          |
| `@TItem`           | Dentro de `@TView` | Cada tela: título, ModelView, Model                   |

**Exemplo real:** `org.tedros.it.tools.module.evidence.JobEvidenceModule`

---

## 4. Fluxo de Inicialização do Sistema

```
TedrosBox.start()
    └── init(primaryStage)
        ├── TedrosContext.setViewBuilder(new TViewBuilder())
        └── setupSystem()
            └── TedrosNavigationManager (inicializa TreeView listeners)

// Quando o usuário faz login → buildApplicationMenu() é chamado:
TedrosBox.buildApplicationMenu()
    ├── new Pages()                        ← cria a estrutura raiz da TreeView
    ├── pages.parseModules()               ← InternalViewHelper.loadApplication()
    │       └── lê TedrosAppManager.getModuleContexts()
    │           → cria CategoryPage e InternalViewPage para cada módulo
    └── navManager.goToPage(pages.getModules())  ← navega para a tela inicial
```

### 4.1 – Descoberta de Aplicativos: `TedrosContext.searchApps()`

Chamado antes do login, varre o classpath via reflection em busca de todas as
classes anotadas com `@TApplication` e as registra no `TedrosAppManager`:

```java
TReflections.getInstance()
    .getClassesAnnotatedWith(TApplication.class)
    .forEach(c -> TedrosAppManager.getInstance().addApplication(c));
```

Cada classe encontrada gera um `TAppContext`, que por sua vez cria um
`TModuleContext` para cada `@TModule` declarado.

---

## 5. A TreeView: Hierarquia de Pages

A TreeView é montada por `Pages.parseModules()` usando três tipos de `Page` (todos estendem `javafx.scene.control.TreeItem<String>`):

### `AllPagesPage`
- **Papel:** Nó raiz invisível da TreeView (`showRoot = false`).
- Sempre tem valor `"All"`.
- `createModule()` → retorna `null`.

### `CategoryPage`
- **Papel:** Agrupa módulos por nome de menu (valor do atributo `menu` do `@TModule`).
- Quando clicado, exibe uma tela com tiles dos módulos filhos.
- `createModule()` → constrói um painel com tiles clicáveis de cada `InternalViewPage` filho.

### `InternalViewPage`
- **Papel:** Representa um único módulo na árvore.
- Contém uma referência ao `TModuleContext` do módulo.
- `createModule()` → chama `context.buildModule()` para instanciar o `TModule` via reflection.
- `getModule()` → retorna a instância já criada (ou `null` se ainda não foi aberto).

```
AllPagesPage ("All")                   ← raiz oculta
└── CategoryPage ("Aplicativos")       ← pages.getModules()
    ├── CategoryPage ("IT Support")    ← agrupador pelo @TModule.menu()
    │   ├── InternalViewPage ("Job Evidence")
    │   ├── InternalViewPage ("Redmine")
    │   └── InternalViewPage ("GMUD")
    └── CategoryPage ("Outro Menu")
        └── InternalViewPage ("...")
```

---

## 6. Fluxo de Abertura de uma Tela (goToPage)

### 6.1 – Listener da TreeView

Quando o usuário clica num item do menu:

```java
menuTree.getSelectionModel().selectedItemProperty()
    .addListener((observable, oldValue, newValue) -> {
        if (!navManager.isChangingPage()) {
            Page selectedPage = (Page) menuTree.getSelectionModel().getSelectedItem();
            if (selectedPage != navManager.getPages().getRoot())
                navManager.goToPage(selectedPage);
        }
    });
```

O flag `changingPage` evita recursão quando o `NavigationManager` programa a seleção do item.

### 6.2 – `TedrosNavigationManager.goToPage(Page page)`

```
goToPage(page)
  └── TedrosContext.setPageProperty(page, addHistory=true, force=false, swapViews=true)
        └── dispara listener → goToPage(page, addHistory, force, swapViews)
              ├── Verifica se o módulo atual pode parar (tCanStop())
              │     → Se não: mostra TConfirmMessageBox
              └── callPage(page, addHistory, force, swapViews)
                    ├── page.getModule() → null se nunca aberto
                    ├── se null: page.createModule() → instancia o TModule
                    │     └── context.buildModule() via reflection
                    ├── itModule.tStart() → chama TViewBuilder.build(this)
                    │     └── Lê @TView do módulo:
                    │           • 1 @TItem → cria TDynaView
                    │           • N @TItem → cria TGroupView com TDynaGroupView
                    ├── Envolve em ScrollPane e coloca no pageArea
                    ├── TedrosContext.setView(view)
                    ├── Adiciona ao histórico (history Stack)
                    ├── menuTree.getSelectionModel().select(page)
                    └── breadcrumbBar.setPath(currentPagePath)
```

### 6.3 – `TModule.tStart()` e `TViewBuilder`

```java
// TModule.tStart() — método base chamado automaticamente
public void tStart() {
    tShowView(TedrosContext.getViewBuilder().build(this));
}

// TViewBuilder.build(TModule m)
// Lê @TView do módulo:
TView ann = m.getClass().getAnnotation(TView.class);
TItem[] arr = ann.items();

if (arr.length == 1) {
    // Uma única tela: TDynaView simples
    view = new TDynaView(m, arr[0].modelView());
} else {
    // Múltiplas telas: TGroupView com abas
    view = new TGroupView<>(m, ann.title(), items); // cada item é um TDynaGroupView
}
```

---

## 7. Ciclo de Vida do Módulo

| Método        | Quando é chamado                                        |
|:-------------|:--------------------------------------------------------|
| `tStart()`   | Ao abrir pela primeira vez (via `callPage`)             |
| `tShowView()`| Internamente por `tStart()` para exibir a view          |
| `tCanStop()` | Antes de trocar de página (verifica unsaved changes)    |
| `tStop()`    | Ao sair da página (remove do histórico ou troca de app) |

O controle do ciclo de vida é delegado ao `TModuleContext`, que gerencia a instância do módulo e as views abertas (`TViewContext`).

---

## 8. Tipos de View

| Classe           | Uso                                              | Template FXML          |
|:----------------|:-------------------------------------------------|:-----------------------|
| `TDynaView`     | Módulo com **1 tela** (`@TView` com 1 `@TItem`) | `TDynamicView.fxml`    |
| `TDynaGroupView`| Tela dentro de um grupo (1 aba de TGroupView)    | `TDynamicGroupView.fxml`|
| `TGroupView`    | Módulo com **N telas** (cria abas)              | —                      |

---

## 9. Navegação Programática

É possível navegar para um módulo por código de outras partes do sistema:

```java
// Por classe do módulo
TedrosAppManager.getInstance().goToModule(MeuModulo.class);

// Por path string
TedrosContext.setPagePathProperty("NomeMenu/NomeModulo", true, false, true);

// Navegar E carregar um ModelView específico
TedrosAppManager.getInstance().goToModule(MeuModulo.class, MeuModelView.class);

// Navegar e executar ação após carregamento
TedrosAppManager.getInstance().goToModule(MeuModulo.class, MeuModelView.class,
    module -> { /* ação após carregar */ });
```

---

## 10. Histórico de Navegação

`TedrosNavigationManager` mantém dois `Stack<Page>`:
- `history` — páginas visitadas (botão Voltar)
- `forwardHistory` — páginas para avançar após voltar

A profundidade máxima do histórico é configurada pela propriedade `sys.totalPageHistory` (padrão: 3, máximo: 10).

Ao remover uma página do histórico que não está em nenhuma janela destacada (`ITWindow`), o sistema chama `itModule.tStop()` para liberar recursos.

---

## 11. Checklist para Criar um Novo Aplicativo

- [ ] Criar classe `AppStart` implementando `ITApplication`
- [ ] Anotar com `@TApplication(name, packageName, universalUniqueIdentifier, module={...})`
- [ ] Anotar com `@TResourceBundle` se tiver i18n próprio
- [ ] Anotar com `@TSecurity` para controle de acesso
- [ ] Para cada módulo:
  - [ ] Criar classe estendendo `TModule`
  - [ ] Anotar com `@TView(items={@TItem(...)})`
  - [ ] Anotar com `@TSecurity` para permissão de acesso
  - [ ] Para cada `@TItem`: criar a classe `ModelView` correspondente
- [ ] Registrar o `@TModule` no `@TApplication` da `AppStart`

---

## 12. Classes-chave e suas Responsabilidades

| Classe                   | Responsabilidade                                                   |
|:------------------------|:-------------------------------------------------------------------|
| `TedrosContext`          | Contexto global: propriedades reativas, usuário, navegação         |
| `TedrosAppManager`       | Singleton: gerencia todos os `TAppContext` e `TModuleContext`       |
| `TedrosAppLoader`        | Base abstrata do manager: carrega/para apps                        |
| `TAppContext`            | Contexto de um aplicativo: descritor e lista de `TModuleContext`   |
| `TModuleContext`         | Contexto de um módulo: instância, views abertas                    |
| `TedrosBox`              | Shell principal JavaFX: monta a UI, chama `setupSystem()`          |
| `TedrosNavigationManager`| Gerencia TreeView, área de páginas, histórico de navegação         |
| `Pages`                  | Constrói a árvore de `Page` a partir dos `TModuleContext`          |
| `AllPagesPage`           | Raiz oculta da TreeView                                            |
| `CategoryPage`           | Agrupador de módulos por menu                                      |
| `InternalViewPage`       | Item de menu folha; cria o módulo sob demanda                      |
| `TModule`                | Base de todo módulo; implementa `tStart/tStop`                     |
| `TViewBuilder`           | Cria `TDynaView` ou `TGroupView` com base no `@TView` do módulo   |
| `TDynaView`              | View dinâmica para 1 ModelView                                     |
| `TDynaGroupView`         | View dinâmica usada como aba dentro do `TGroupView`                |
