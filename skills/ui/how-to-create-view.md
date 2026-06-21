# Skill: How to Create a ModelView (View) in Tedros

## 1. Objective

This skill explains how to create a **ModelView** class in the Tedros platform. A ModelView is the central piece that defines a view inside a module — it declares the form layout, field controls, presenter behavior, EJB service binding, and security configuration entirely through annotations, without writing UI code.

---

## 2. Architectural Placement

- **Project Location:** All ModelView classes must be created in the `[APP_NAME]-fx` Maven module.
- **Package:** `org.tedros.[app_name].module.[module_name].model`
- **Base class:** Extend either:
  - `TEntityModelView<E>` — for views that display and edit a **JPA entity** (persisted to database). Provides a list view + CRUD form.
  - `TModelView<M>` — for views that work with a **non-entity model** (no list, no CRUD). Used for custom screens, dashboards, etc.

---

## 3. Two ModelView Patterns

### Pattern A — Entity ModelView (`TEntityModelView<E>`)

Use this when the view needs a **list of records** on the left and a **CRUD form** on the right (the standard list-detail pattern).

**Required class-level annotations:**

| Annotation              | Purpose                                                            |
|:------------------------|:-------------------------------------------------------------------|
| `@TForm`                | Configures the form layout (breadcrumb, scroll, header)            |
| `@TEjbService`          | Binds the EJB service used for CRUD operations                     |
| `@TListViewPresenter`   | Configures the list view, pagination, search, presenter, decorator  |
| `@TSecurity` (optional) | Declares allowed access types for this view                        |
| `@TSetting` (optional)  | Attaches a setting class to register listeners between fields      |

**Constructor:** Must call `super(entity)`. Optionally call `super.formatToString(...)` to set how the record appears in the list.

**Minimal example:**

```java
package org.tedros.myapp.module.customer.model;

import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TTextField;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.layout.TFlowPane;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TListViewPresenter;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.process.TEjbService;
import org.tedros.fx.model.TEntityModelView;
import org.tedros.myapp.ejb.controller.ICustomerController;
import org.tedros.myapp.entity.Customer;

import javafx.beans.property.SimpleStringProperty;

@TForm(showBreadcrumBar = false, scroll = false)
@TEjbService(serviceName = ICustomerController.JNDI_NAME, model = Customer.class)
@TListViewPresenter(
    presenter = @TPresenter(
        decorator = @TDecorator(viewTitle = "Customers"),
        behavior  = @TBehavior(runNewActionAfterSave = false)))
public class CustomerMV extends TEntityModelView<Customer> {

    @TLabel(text = "customer.name")
    @TTextField(maxLength = 120, required = true)
    private SimpleStringProperty name;

    @TLabel(text = "customer.email")
    @TTextField(maxLength = 200)
    private SimpleStringProperty email;

    public CustomerMV(Customer entity) {
        super(entity);
        super.formatToString("%s", name); // how record appears in the list
    }

    // --- getters and setters ---
    public SimpleStringProperty getName() { return name; }
    public void setName(SimpleStringProperty name) { this.name = name; }
    public SimpleStringProperty getEmail() { return email; }
    public void setEmail(SimpleStringProperty email) { this.email = email; }
}
```

---

### Pattern B — Simple ModelView (`TModelView<M>`)

Use this when the view does **not** need a list or CRUD. Typical for dashboards, capture screens, monitoring views, etc. The model is a plain non-entity Java class.

**Required class-level annotations:**

| Annotation   | Purpose                                                                      |
|:-------------|:-----------------------------------------------------------------------------|
| `@TPresenter`| Declares the model class, decorator type, and behavior type for the view     |

**Constructor:** Must call `super(model)`.

**Minimal example:**

```java
package org.tedros.myapp.module.dashboard.model;

import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.model.TModelView;
import org.tedros.fx.presenter.model.behavior.TViewBehavior;
import org.tedros.fx.presenter.model.decorator.TViewDecorator;
import org.tedros.myapp.module.dashboard.model.DashboardModel;

import javafx.beans.property.SimpleStringProperty;

@TPresenter(
    model     = DashboardModel.class,
    decorator = @TDecorator(type = TViewDecorator.class, viewTitle = "Dashboard"),
    behavior  = @TBehavior(type = TViewBehavior.class))
public class DashboardMV extends TModelView<DashboardModel> {

    @TText(textStyle = TTextStyle.LARGE, text = "dashboard.title")
    @TFieldBox(node = @TNode(parse = true, id = TFieldBox.TITLE))
    private SimpleStringProperty header;

    @TComponent(type = DashboardComponent.class)
    private SimpleStringProperty chart;

    public DashboardMV(DashboardModel model) {
        super(model);
    }

    public SimpleStringProperty getHeader() { return header; }
    public void setHeader(SimpleStringProperty header) { this.header = header; }
    public SimpleStringProperty getChart() { return chart; }
    public void setChart(SimpleStringProperty chart) { this.chart = chart; }
}
```

---

## 4. Class-Level Annotations Reference

### `@TForm`

Configures the overall form container.

| Attribute         | Default | Description                                                      |
|:-----------------|:--------|:-----------------------------------------------------------------|
| `showBreadcrumBar`| `false` | Show navigation breadcrumb bar inside the form                   |
| `header`          | `""`    | Optional static header text above the form                       |
| `scroll`          | `true`  | Wrap the form in a scroll pane. Use `false` for fixed-height views |
| `editCssId`       |`"t-form"`| CSS ID applied to the edit panel                                |
| `readerCssId`     |`"t-reader"`| CSS ID applied to the read-only panel                         |

---

### `@TEjbService`

Binds the CRUD EJB service to the view. **Required only for `TEntityModelView`.**

| Attribute           | Default | Description                                                          |
|:-------------------|:--------|:---------------------------------------------------------------------|
| `serviceName`       | —       | JNDI name of the EJB service interface (e.g. `ICustomerController.JNDI_NAME`) |
| `model`             | —       | The JPA entity class this service operates on                        |
| `filterByLoggedUser`| `false` | When `true`, CRUD operations filter records by the logged-in user    |
| `remoteMode`        | `true`  | Use remote EJB lookup                                                |

---

### `@TListViewPresenter`

Configures the list panel and presenter for entity views.

| Attribute                     | Default  | Description                                              |
|:-----------------------------|:---------|:---------------------------------------------------------|
| `listViewMaxWidth`            | `350`    | Maximum width of the list panel                          |
| `listViewMinWidth`            | `350`    | Minimum width of the list panel                          |
| `reloadListViewAfterCrudActions` | `false` | Auto-reload list after save/delete                     |
| `autoHideListView`            | `false`  | Collapse list panel after user clicks an item            |
| `page`                        | hidden   | Pagination and search configuration (`@TPage`)           |
| `presenter`                   | default  | Presenter, decorator, and behavior configuration         |

#### Nested: `@TPage` (pagination + search)

| Attribute           | Default | Description                                                    |
|:-------------------|:--------|:---------------------------------------------------------------|
| `show`              | `true`  | Show the paginator                                             |
| `showSearch`        | `false` | Show the search panel below the list                           |
| `showOrderBy`       | `false` | Show order-by selector in the search panel                     |
| `serviceName`       | —       | JNDI name for the service that provides pagination data        |
| `filterByLoggedUser`| `false` | Filter pages by the logged-in user                             |
| `query`             | —       | The `@TQuery` that defines searchable fields and sort options  |

**`@TQuery` structure:**

```java
query = @TQuery(
    entity = MyEntity.class,
    condition = {
        @TCondition(field = "name",   operator = TCompareOp.LIKE,  label = "Name"),
        @TCondition(field = "code",   operator = TCompareOp.EQUAL, label = "Code")
    },
    orderBy = {
        @TOrder(label = "Name", field = "name"),
        @TOrder(label = "Code", field = "code")
    }
)
```

#### Nested: `@TPresenter`

| Attribute   | Default               | Description                                                    |
|:-----------|:----------------------|:---------------------------------------------------------------|
| `type`      | `TDynaPresenter`      | Presenter implementation class                                 |
| `decorator` | `@TDecorator`         | Layout and button configuration                                |
| `behavior`  | `@TBehavior`          | Action and save behavior configuration                         |
| `model`     | `ITModel.class`       | Optional: the model class for this view                        |

#### Nested: `@TDecorator`

| Attribute              | Default | Description                                                  |
|:-----------------------|:--------|:-------------------------------------------------------------|
| `type`                 | `TMasterCrudViewDecorator` | Decorator implementation  |
| `viewTitle`            | `""`    | Title shown in the view header (i18n key or literal string)  |
| `listTitle`            | `""`    | Title shown above the list panel                             |
| `buildNewButton`       | `true`  | Show the **New** button                                      |
| `buildSaveButton`      | `true`  | Show the **Save** button                                     |
| `buildDeleteButton`    | `true`  | Show the **Delete** button                                   |
| `buildCancelButton`    | `true`  | Show the **Cancel** button                                   |
| `buildPrintButton`     | `false` | Show the **Print** button                                    |
| `buildImportButton`    | `false` | Show the **Import** button                                   |
| `buildModesRadioButton`| `false` | Show Edit/Read-only mode toggle                              |

#### Nested: `@TBehavior`

| Attribute                | Default | Description                                                         |
|:------------------------|:--------|:--------------------------------------------------------------------|
| `type`                  | `TMasterCrudViewBehavior` | Behavior implementation class                      |
| `saveAllModels`         | `true`  | Save all changed records in the list on save                        |
| `saveOnlyChangedModels` | `true`  | Only save records that have been modified                           |
| `runNewActionAfterSave` | `false` | After save, automatically start a new record                        |
| `action`                | `{}`    | Additional custom action classes (`TPresenterAction` subclasses)    |

---

### `@TSecurity`

Declares access control for the view. Attach to individual views that need fine-grained permission.

```java
@TSecurity(
    id          = DomainApp.MY_FORM_SECURITY_ID,  // unique constant from DomainApp
    appName     = MyKeys.APP_NAME,
    moduleName  = MyKeys.MODULE_NAME,
    viewName    = MyKeys.VIEW_NAME,
    allowedAccesses = {
        TAuthorizationType.VIEW_ACCESS,
        TAuthorizationType.EDIT,
        TAuthorizationType.SAVE,
        TAuthorizationType.DELETE,
        TAuthorizationType.NEW
    }
)
```

---

### `@TSetting`

Attaches a setting class to the view. Setting classes register JavaFX property listeners to implement UI dependencies (e.g., update an AdminArea combo box when a Country is selected).

```java
@TSetting(MyViewSetting.class)
```

---

## 5. Field Declarations

Each form field is a JavaFX `Property` field annotated with control annotations. The order of annotations on a field determines:

1. **The label** (`@TLabel`)
2. **The control type** (`@TTextField`, `@TDatePickerField`, etc.)
3. **The layout container** (`@TFlowPane`, `@THBox`, `@TTabPane`, etc.)
4. **Optional extras** (`@TTrigger`, `@TFieldBox`, etc.)

### 5.1 Property Types

| Property type                          | Use when the field holds          |
|:--------------------------------------|:----------------------------------|
| `SimpleStringProperty`                 | Strings                           |
| `SimpleLongProperty`                   | Long numbers, IDs                 |
| `SimpleIntegerProperty`                | Integer numbers                   |
| `SimpleBigDecimalProperty`             | Decimal numbers, currency         |
| `SimpleObjectProperty<Date>`           | Dates                             |
| `SimpleObjectProperty<MyEntity>`       | A single related entity           |
| `SimpleObjectProperty<Boolean>`        | Booleans                          |
| `ITObservableList<MyDetailMV>`         | A collection of child records     |

### 5.2 Common Field Control Annotations

| Annotation          | Property type     | Description                                              |
|:--------------------|:------------------|:---------------------------------------------------------|
| `@TTextField`       | `SimpleStringProperty` | Single-line text input                             |
| `@TTextAreaField`   | `SimpleStringProperty` | Multi-line text input                              |
| `@THTMLEditor`      | `SimpleStringProperty` | Rich text HTML editor                              |
| `@TDatePickerField` | `SimpleObjectProperty<Date>` | Date picker                                |
| `@TNumberSpinnerField` | `SimpleLongProperty` / `SimpleIntegerProperty` | Numeric spinner |
| `@TBigDecimalField` | `SimpleBigDecimalProperty` | Decimal number field                          |
| `@TCheckBoxField`   | `SimpleObjectProperty<Boolean>` | Checkbox                                  |
| `@TComboBoxField`   | `SimpleObjectProperty<T>` | ComboBox loaded via EJB service              |
| `@TAutoCompleteEntity` | `SimpleObjectProperty<Entity>` | AutoComplete with server-side search    |
| `@TDetailField`     | `SimpleObjectProperty<DetailMV>` | Inline single-entity detail editor     |
| `@TDetailListField` | `ITObservableList<DetailMV>` | Inline list of child entities             |
| `@TEditEntityModal` | `ITObservableList<ChildMV>` | Edit child entities via a popup modal    |
| `@TComponent`       | `SimpleStringProperty` | Embeds a custom JavaFX component               |
| `@TShowField`       | `SimpleStringProperty` | Read-only display field                        |
| `@TSelectImageField`| `SimpleObjectProperty<ITFileBaseModel>` | Image selection and upload       |
| `@TText`            | `SimpleStringProperty` | Static styled text label in the form           |

### 5.3 Common Attribute for `@TTextField`

| Attribute    | Default | Description                        |
|:------------|:--------|------------------------------------|
| `maxLength`  | `0`     | Maximum characters allowed         |
| `required`   | `false` | Marks the field as mandatory       |

### 5.4 Layout Annotations

Use layout annotations on a field to declare a container that groups multiple fields together.

| Annotation      | Description                                                              |
|:----------------|:-------------------------------------------------------------------------|
| `@TFlowPane`    | Groups fields in a wrapping horizontal flow. Use `pane=@TPane(children={...})` to list grouped field names. |
| `@THBox`        | Groups fields in a horizontal box. Use `pane=@TPane(children={...})` to list field names. |
| `@TTabPane`     | Wraps the field in a tab pane container. Uses `tabs={@TTab(...)}` to define each tab. |
| `@TFieldBox`    | Wraps the field in a styled box (e.g., for section titles). Use `node=@TNode(parse=true, id=TFieldBox.TITLE)` for section headers. |

**Example — grouping fields with `@TFlowPane`:**

```java
@TLabel(text = "Name")
@TTextField(maxLength = 120, required = true)
@TFlowPane(hgap = 20, vgap = 12,
    pane = @TPane(children = {"name", "email", "phone"}))
private SimpleStringProperty name;

@TLabel(text = "Email")
@TTextField(maxLength = 200)
private SimpleStringProperty email;

@TLabel(text = "Phone")
@TTextField(maxLength = 20)
private SimpleStringProperty phone;
```

> **Rule:** Declare the `@TFlowPane` (or `@THBox`) on the **first field** listed in `children`. The container will automatically absorb the subsequent named fields.

**Example — tabs with `@TTabPane`:**

```java
@TTabPane(tabs = {
    @TTab(text = "Main Data",    fields = {"name", "email"}),
    @TTab(text = "Description",  fields = {"notes"}),
    @TTab(text = "Details",      fields = {"items"})
})
private SimpleStringProperty name; // first field drives the TabPane
```

---

## 6. Using `@TAutoCompleteEntity`

Provides a server-side auto-complete for a related entity.

```java
@TLabel(text = "Employee")
@TAutoCompleteEntity(
    required = true,
    service  = IEmployeeController.JNDI_NAME,
    query    = @TQuery(
        entity    = Employee.class,
        condition = {
            @TCondition(field = "name",     operator = TCompareOp.LIKE),
            @TCondition(logicOp = TLogicOp.OR, field = "lastName", operator = TCompareOp.LIKE)
        }))
private SimpleObjectProperty<Employee> employee;
```

---

## 7. Using `@TDetailListField` (Master-Detail)

Embeds an inline editable list of child entities within the parent form.

```java
@TLabel(text = "Items", show = false)
@TFieldBox(node = @TNode(id = "list", parse = true))
@TDetailListField(
    region    = @TRegion(maxHeight = 400, parse = true),
    modelView = ItemDetailMV.class,
    entity    = Item.class)
@TGenericType(model = Item.class, modelView = ItemDetailMV.class)
private ITObservableList<ItemDetailMV> items;
```

> **Rule:** Always pair `@TDetailListField` with `@TGenericType` declaring both the entity and the detail ModelView class.

---

## 8. Using `@TEditEntityModal` (Modal Editor)

Embeds a list of child entities that open a modal popup for editing.

```java
@TLabel(text = "Contacts")
@TEditEntityModal(
    height      = 70,
    modalHeight = 400,
    modalWidth  = 600,
    model       = Contact.class,
    modelView   = ContactMV.class)
@TGenericType(model = Contact.class, modelView = ContactMV.class)
protected ITObservableList<ContactMV> contacts;
```

---

## 9. Using `@TComponent` (Custom Component)

Embeds a fully custom JavaFX component inside the form. The component handles its own logic.

```java
@TComponent(type = MyCustomComponent.class)
private SimpleStringProperty myComponent;
```

> The field must be a `SimpleStringProperty` regardless of the component's internal type.

---

## 10. Using `@TTrigger`

Executes a custom action when the field value changes (e.g., after user types in an issue number, auto-fill the title).

```java
@TLabel(text = "Issue Number")
@TTextField(maxLength = 50, required = true)
@TTrigger(type = SearchForIssueTrigger.class,
          targetFieldName = "issueTitle",
          associatedFieldBox = "issueLink")
private SimpleStringProperty issueNumber;
```

---

## 11. The `formatToString` Method

Defines how each entity record is displayed as a string in the list view. Called in the constructor.

```java
// Single field
super.formatToString("%s", name);

// Multiple fields
super.formatToString("[%s] %s", iso2Code, name);

// With custom formatting logic
super.formatToString(TFormatter.create()
    .add(executionDate, in -> in != null
        ? "[" + TDateUtil.formatShortDate((Date) in, TLanguage.getLocale()) + "]"
        : "")
    .add(" %s -", issueNumber)
    .add(" %s", name));
```

---

## 12. Complete Real-World Examples

### Example A — Entity view with tabs, auto-complete, and list field

**Source:** `CreateJobEvidenceMV.java`

```java
@TSetting(JobEvidenceSettings.class)
@TForm(header = "", showBreadcrumBar = false, scroll = false)
@TEjbService(serviceName = IJobEvidenceController.JNDI_NAME,
             model = JobEvidence.class, filterByLoggedUser = true)
@TListViewPresenter(
    page = @TPage(
        serviceName = IJobEvidenceController.JNDI_NAME,
        filterByLoggedUser = true,
        query = @TQuery(
            entity = JobEvidence.class,
            condition = {
                @TCondition(field = "name",        operator = TCompareOp.LIKE,  label = "Name"),
                @TCondition(field = "issueNumber",  operator = TCompareOp.EQUAL, label = "Issue #")
            },
            orderBy = {
                @TOrder(label = "Issue #",         field = "issueNumber"),
                @TOrder(label = "Execution Date",  field = "executionDate"),
                @TOrder(label = "Name",            field = "name")
            }),
        showSearch = true, showOrderBy = true),
    presenter = @TPresenter(
        decorator = @TDecorator(viewTitle = "Job Evidence", buildModesRadioButton = false),
        behavior  = @TBehavior(runNewActionAfterSave = false,
                               saveAllModels = false, saveOnlyChangedModels = false)))
@TSecurity(id = DomainApp.EVIDENCE_MANAGER_FORM_ID,
           appName = "App IT Support",
           moduleName = "Evidence Manager",
           viewName = "Job Evidence",
           allowedAccesses = {TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT,
                              TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class CreateJobEvidenceMV extends TEntityModelView<JobEvidence> {

    // Tab grouper (on the id field)
    @TTabPane(tabs = {
        @TTab(text = "Main Data",    fields = {"issueNumber"}),
        @TTab(text = "Description",  fields = {"description"}),
        @TTab(text = "Evidences",    fields = {"itemsHeader", "component"})
    })
    private SimpleLongProperty id;

    @TLabel(text = "Issue #")
    @TTextField(maxLength = 50, required = true)
    @TFlowPane(hgap = 20, vgap = 12,
        pane = @TPane(children = {"issueNumber", "name", "tool", "employee", "executionDate", "issueTitle"}))
    @TTrigger(type = SearchForIssueTrigger.class,
              targetFieldName = "issueTitle", associatedFieldBox = "issueLink")
    private SimpleStringProperty issueNumber;

    @TLabel(text = "Name")
    @TTextField(maxLength = 120, required = true)
    private SimpleStringProperty name;

    @TLabel(text = "Employee")
    @TAutoCompleteEntity(required = true,
        service = IEmployeeController.JNDI_NAME,
        query = @TQuery(entity = Employee.class,
            condition = {
                @TCondition(field = "name",     operator = TCompareOp.LIKE),
                @TCondition(logicOp = TLogicOp.OR, field = "lastName", operator = TCompareOp.LIKE)
            }))
    private SimpleObjectProperty<Employee> employee;

    @TLabel(text = "Execution Date")
    @TDatePickerField(required = true)
    private SimpleObjectProperty<Date> executionDate;

    @THTMLEditor(control = @TControl(maxHeight = 450, parse = true))
    private SimpleStringProperty description;

    @TComponent(type = EvidenceSelectorComponent.class)
    private SimpleStringProperty component;

    @TGenericType(model = JobEvidenceItem.class)
    private ITObservableList<JobEvidenceItem> items;

    public CreateJobEvidenceMV(JobEvidence entity) {
        super(entity);
        super.formatToString(TFormatter.create()
            .add(executionDate, in -> in != null
                ? "[" + TDateUtil.formatShortDate((Date) in, TLanguage.getLocale()) + "]" : "")
            .add(" %s -", issueNumber)
            .add(" %s", name));
        if (entity != null && entity.isNew())
            executionDate.setValue(new Date());
    }
    // ... getters and setters
}
```

---

### Example B — Simple view with custom component (`TModelView`)

**Source:** `CaptureEvidenceMV.java`

```java
@TPresenter(
    model     = CaptureEvidenceModel.class,
    decorator = @TDecorator(type = TViewDecorator.class, viewTitle = "Capture Evidence"),
    behavior  = @TBehavior(type = TViewBehavior.class))
public class CaptureEvidenceMV extends TModelView<CaptureEvidenceModel> {

    @TText(textStyle = TTextStyle.LARGE, text = "No screens captured yet")
    @TFieldBox(node = @TNode(parse = true, id = TFieldBox.TITLE))
    private SimpleStringProperty header;

    @TComponent(type = EvidenceMonitorComponent.class)
    private SimpleStringProperty monitorComponent;

    public CaptureEvidenceMV(CaptureEvidenceModel model) {
        super(model);
    }
    // ... getters and setters
}
```

---

### Example C — Entity view with pagination, search, and read-only buttons

**Source:** `PageSearchMV.java`

```java
@TForm(header = "Countries", showBreadcrumBar = true, scroll = false)
@TEjbService(serviceName = ICountryController.JNDI_NAME, model = Country.class)
@TListViewPresenter(
    page = @TPage(
        serviceName = ICountryController.JNDI_NAME,
        query = @TQuery(
            entity = Country.class,
            condition = {
                @TCondition(field = "iso2Code", operator = TCompareOp.EQUAL, label = "ISO2 Code"),
                @TCondition(field = "name",     operator = TCompareOp.LIKE,  label = "Name")
            },
            orderBy = {
                @TOrder(label = "Name",     field = "name"),
                @TOrder(label = "ISO2",     field = "iso2Code")
            }),
        showSearch = true, showOrderBy = true),
    presenter = @TPresenter(
        decorator = @TDecorator(
            viewTitle         = "Page and Search Countries",
            buildSaveButton   = false,
            buildDeleteButton = false,
            buildNewButton    = false)))
public class PageSearchMV extends TEntityModelView<Country> {
    // fields...
    public PageSearchMV(Country entity) {
        super(entity);
        super.formatToString("[%s] %s", iso2Code, name);
    }
}
```

---

### Example D — Entity view with custom `@TBehavior` actions

**Source:** `CustomActionMV.java`

```java
@TForm(showBreadcrumBar = false, scroll = false)
@TEjbService(serviceName = ISampleBController.JNDI_NAME, model = SampleB.class)
@TListViewPresenter(
    presenter = @TPresenter(
        decorator = @TDecorator(viewTitle = "Custom action sample"),
        behavior  = @TBehavior(action = {DisableCrudAction.class, NewAction.class})))
public class CustomActionMV extends TEntityModelView<SampleB> { ... }
```

---

### Example E — Entity view with custom Decorator class

**Source:** `CustomDecoratorMV.java`

```java
@TForm(showBreadcrumBar = false, scroll = false)
@TEjbService(serviceName = ISampleBController.JNDI_NAME, model = SampleB.class)
@TListViewPresenter(
    presenter = @TPresenter(
        decorator = @TDecorator(
            viewTitle         = "Custom decorator sample",
            type              = CustomDecorator.class,  // custom layout
            buildSaveButton   = false,
            buildDeleteButton = false)))
public class CustomDecoratorMV extends TEntityModelView<SampleB> { ... }
```

---

## 13. Checklist for Creating a New ModelView

- [ ] Choose the correct base class: `TEntityModelView<E>` (entity + CRUD list) or `TModelView<M>` (custom screen)
- [ ] Add `@TForm` (scroll, breadcrumb, header)
- [ ] Add `@TEjbService` (service JNDI name and entity class) — **Entity views only**
- [ ] Add `@TListViewPresenter` with `@TPresenter` / `@TDecorator` / `@TBehavior` — **Entity views only**
- [ ] Add `@TPage` inside `@TListViewPresenter` if pagination or search is needed
- [ ] Add `@TSecurity` if this view has specific access control requirements
- [ ] Add `@TSetting` if fields have UI dependencies (e.g., cascading combo boxes)
- [ ] For each field:
  - [ ] Add `@TLabel` with the i18n key or literal string
  - [ ] Add the field control annotation (`@TTextField`, `@TDatePickerField`, etc.)
  - [ ] Add layout annotation (`@TFlowPane`, `@THBox`, `@TTabPane`) on the **first** field of each group
  - [ ] Use `@TGenericType` alongside `@TDetailListField` or `@TEditEntityModal`
- [ ] Implement `formatToString(...)` in the constructor to set list display text — **Entity views only**
- [ ] All fields must have a **getter and setter**

---

## 14. Rules and Constraints

- **Module:** Create ModelViews only in the `-fx` Maven module of the application.
- **Package:** Always use `org.tedros.[app_name].module.[module_name].model`.
- **Field names in layout annotations** (`children={...}`) must exactly match the Java field names in the class.
- **`@TTabPane` placement:** Place it on the **`id` field** (`SimpleLongProperty id`) when you want tabs to span the entire form. Alternatively, place it on the first field of the first tab group.
- **`@TGenericType` is mandatory** for every `@TDetailListField` and `@TEditEntityModal` field.
- **`@TComponent` fields** must always be typed as `SimpleStringProperty`, even if the component internally handles a different type.
- **`formatToString`** must be called in the constructor for entity views, since it determines how each record appears in the list view.
- **Read-only views:** Set `buildSaveButton=false`, `buildDeleteButton=false`, `buildNewButton=false` in `@TDecorator` to produce a display-only view.
- **i18n:** All label texts and titles should reference i18n key constants (from a `*Key` class) rather than hardcoded strings.
