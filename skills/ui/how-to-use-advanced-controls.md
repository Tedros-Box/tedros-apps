# Skill: How to Use Advanced Form Controls in Tedros

## 1. Objective

This skill explains how to use **advanced field controls** inside a Tedros ModelView. It covers the controls that go beyond basic text inputs — auto-complete fields, combo boxes, list views, pick lists, and table views — all configured entirely through annotations.

---

## 2. The Form Engine Rendering Order Rule

> **Critical rule you must understand before ordering fields.**

The `TFormEngine` and `TModelViewLoader` build form components by iterating fields starting from the **last declared field** upward. This reverse-processing order has one key consequence:

**Layout containers (e.g., `@TFlowPane`, `@TTabPane`) reference controls by name. By the time the engine processes a layout field, all controls it references must already have been instantiated.** Since controls are built first (bottom-to-top), and layouts are assembled afterward, you must declare the **layout field above the fields it groups**.

In practice:
- Declare fields that belong to a layout group **below** the field that carries the layout annotation.
- The layout-carrying field acts as the anchor and must appear **first** in the class body, followed by its children fields.
- `@TTabPane` should be declared above all the groups its tabs reference.

### Visual example

```
// Declared order in class (top to bottom)
@TTabPane(tabs = { @TTab(fields={"a","b"}) })   ← layout anchor, declared FIRST
private SimpleStringProperty tab;

@TFlowPane(pane=@TPane(children={"a","b"}))      ← a is layout anchor for the flow pane
private SimpleStringProperty a;                  ← must appear above b

private SimpleStringProperty b;                  ← processed first by engine
```

Engine processing order (bottom to top): `b` → `a` (field + FlowPane built) → `tab` (TabPane assembled using already-built `a` and `b`).

---

## 3. `@TAutoCompleteTextField` — Static and Factory AutoComplete

Provides a text field with a dropdown of suggestions. Suggestions can come from a **static list of keys**, or from a **factory class** that builds the list programmatically.

### 3.1 Static List of Keys

```java
@TLabel(text = "Street Type")
@TAutoCompleteTextField(
    entries = @TEntry(
        values = {
            MyKeys.OPT_ALLEY,
            MyKeys.OPT_AVENUE,
            MyKeys.OPT_BOULEVARD,
            MyKeys.OPT_LANE,
            MyKeys.OPT_ROAD,
            MyKeys.OPT_STREET
        }))
private SimpleStringProperty streetType;
```

- `entries.values` — array of i18n key constants. The framework resolves each key to a display string at runtime.
- Field type must be `SimpleStringProperty`.
- Field name must match the entity field name exactly.

### 3.2 Factory-Based List

Use a factory class when the list must be generated programmatically (e.g., from locale, business logic, or a static data source).

```java
@TLabel(text = "European Country")
@TAutoCompleteTextField(
    entries = @TEntry(factory = CountriesListBuilder.class))
private SimpleStringProperty europeanCountry;
```

The factory class must extend `TGenericBuilder<List<String>>` and implement `build()`:

```java
public class CountriesListBuilder extends TGenericBuilder<List<String>> {

    @Override
    public List<String> build() {
        return Arrays.asList("Albania", "Andorra", "Austria", ...);
    }
}
```

---

## 4. `@TAutoCompleteEntity` — Database-Backed AutoComplete

Provides a text field that queries the **server in real time** as the user types. Used when the suggestion list is too large to load upfront or must reflect live database content.

```java
@TLabel(text = "Country")
@TAutoCompleteEntity(
    service = ICountryController.JNDI_NAME,
    query = @TQuery(
        entity = Country.class,
        condition = @TCondition(field = "name", operator = TCompareOp.LIKE),
        orderBy   = @TOrder(field = "name")))
private SimpleObjectProperty<Country> country;
```

| Attribute   | Description                                                              |
|:------------|:-------------------------------------------------------------------------|
| `service`   | JNDI name of the EJB service that handles the search                     |
| `query`     | `@TQuery` with a `@TCondition(LIKE)` on the searchable field             |
| `orderBy`   | Optional `@TOrder` to sort results                                       |

- Field type must be `SimpleObjectProperty<EntityClass>`.
- Field name must exactly match the entity field name.
- The `@TCondition` operator should be `TCompareOp.LIKE` for text matching.

---

## 5. `@TComboBoxField` — Combo Box

Renders a dropdown selection box. Items can come from a **static factory** or from a **database query via EJB service**.

### 5.1 Factory-Based Items (static or computed)

```java
@TLabel(text = "European Country")
@TComboBoxField(items = CountriesObservableListBuilder.class)
private SimpleObjectProperty<Country> combobox;
```

The factory must extend `TGenericBuilder<ObservableList>` and return an `ObservableList`:

```java
@SuppressWarnings("rawtypes")
public class CountriesObservableListBuilder extends TGenericBuilder<ObservableList> {

    @Override
    public ObservableList<Country> build() {
        return FXCollections.observableArrayList(...);
    }
}
```

### 5.2 Database-Backed Items (via EJB service)

```java
@TLabel(text = "Country")
@TComboBoxField(
    process = @TProcess(
        service = ICountryController.JNDI_NAME,
        query   = @TQuery(
            entity  = Country.class,
            orderBy = @TOrder(field = "name"))))
private SimpleObjectProperty<Country> country;
```

- `process.service` — JNDI name of the EJB controller.
- `process.query.entity` — the JPA entity class to list.
- `process.query.orderBy` — sort order for the list.
- Field type must be `SimpleObjectProperty<EntityClass>` (or `SimpleObjectProperty<String>` for string lists).
- Field name must exactly match the entity field name.

---

## 6. `@TListViewField` — List View

Renders a scrollable list where the user can select **one or multiple items**. Items can come from a static factory or from a database query.

### 6.1 Multiple Selection — Static Items

```java
@TLabel(text = "European Countries")
@TListViewField(
    items   = CountriesObservableListBuilder.class,
    control = @TControl(parse = true, maxHeight = 100, prefHeight = 100))
@TGenericType(model = Country.class)
private ITObservableList<Country> countries;
```

### 6.2 Single Selection — Database Items

```java
@TLabel(text = "Country")
@TListViewField(
    control = @TControl(parse = true, maxHeight = 100, prefHeight = 100),
    process = @TProcess(
        service = ICountryController.JNDI_NAME,
        query   = @TQuery(
            entity  = Country.class,
            orderBy = @TOrder(field = "name"))))
private SimpleObjectProperty<Country> selectedCountry;
```

| Attribute        | Description                                                        |
|:-----------------|:-------------------------------------------------------------------|
| `items`          | Factory class (`TGenericBuilder<ObservableList>`) for static items |
| `process`        | EJB service + query for database-backed items                      |
| `control`        | UI sizing (`maxHeight`, `prefHeight`, `parse = true`)              |

**Field typing rules:**
- For **multiple selection** → use `ITObservableList<EntityClass>` + `@TGenericType(model = EntityClass.class)`.
- For **single selection** → use `SimpleObjectProperty<EntityClass>`.
- Field name must exactly match the entity field name.

> **Important:** `@TGenericType` is mandatory when the field type is `ITObservableList`. Without it the framework cannot instantiate the list items and will throw a `COLLECTION_CONFIGURATION` error at runtime.

---

## 7. `@TPickListField` — Pick List (Source → Target)

Renders a dual-panel selection control: the user moves items from a source list to a target list. Supports single or multiple selection.

### 7.1 Static Source List

```java
@TLabel(text = "Source from static list")
@TPickListField(
    height          = 100,
    sourceLabel     = MyKeys.EUROPEAN_COUNTRIES,
    targetLabel     = TUsualKey.SELECTEDS,
    selectionMode   = SelectionMode.MULTIPLE,
    items           = CountriesObservableListBuilder.class)
@TGenericType(model = Country.class)
private ITObservableList<Country> selectedCountries;
```

### 7.2 Database Source List

```java
@TLabel(text = "Source from database")
@TPickListField(
    height          = 100,
    sourceLabel     = MyKeys.COUNTRIES,
    targetLabel     = TUsualKey.SELECTEDS,
    selectionMode   = SelectionMode.MULTIPLE,
    process = @TProcess(
        service = ICountryController.JNDI_NAME,
        query   = @TQuery(
            entity  = Country.class,
            orderBy = @TOrder(field = "name"))))
@TGenericType(model = Country.class)
private ITObservableList<CountryMV> selectedCountries;
```

| Attribute       | Description                                                          |
|:----------------|:---------------------------------------------------------------------|
| `height`        | Height (px) of each panel                                            |
| `sourceLabel`   | i18n key for the source panel header                                 |
| `targetLabel`   | i18n key for the target panel header                                 |
| `selectionMode` | `SelectionMode.SINGLE` or `SelectionMode.MULTIPLE`                   |
| `items`         | Factory class for static source items                                |
| `process`       | EJB service + query for database-backed source items                 |

- Field type must always be `ITObservableList<T>`.
- `@TGenericType(model = EntityClass.class)` is mandatory.
- Field name must exactly match the entity field name.

---

## 8. `@TTableView` — Table View (Read-Only Grid)

Renders an inline read-only data table. Each row is a `TModelView` wrapping a model object. Column values are resolved by field name on the row's model view.

```java
@TTableView(columns = {
    @TTableColumn(text = "Name",         cellValue = "name"),
    @TTableColumn(text = "Code",         cellValue = "code"),
    @TTableColumn(text = "Date Created", cellValue = "createdDate",
        cellFactory = @TCellFactory(parse = true,
            callBack = @TCallbackFactory(parse = true,
                value = TMediumDateTimeCallback.class)))
})
@TGenericType(model = MyRowModel.class, modelView = MyRowMV.class)
private ITObservableList<MyRowMV> tableData;
```

| Attribute on `@TTableColumn` | Description                                                              |
|:-----------------------------|:-------------------------------------------------------------------------|
| `text`                       | Column header label (i18n key or literal)                                |
| `cellValue`                  | Field name on the row's ModelView whose value populates this column      |
| `cellFactory`                | Optional custom cell renderer (e.g., for dates, icons, formatted text)   |

**Row ModelView requirements:**
- Must extend `TModelView<RowModel>`.
- Field names must exactly match the column `cellValue` strings.
- The constructor must call `super(model)`.

```java
public class MyRowMV extends TModelView<MyRowModel> {

    SimpleStringProperty name;
    SimpleStringProperty code;
    SimpleObjectProperty<Date> createdDate;

    public MyRowMV(MyRowModel model) {
        super(model);
    }
}
```

**`@TGenericType` on a table field:**
- `model` → the plain model class (`MyRowModel.class`)
- `modelView` → the TModelView subclass used per row (`MyRowMV.class`)

> **Important:** `@TGenericType` is mandatory for `@TTableView`. Without it the framework cannot instantiate row objects.

---

## 9. Grouping Advanced Controls with `@TFlowPane`

You can group any combination of advanced controls inside a horizontal flow pane. Declare `@TFlowPane` on the **first field** of the group and list all sibling field names in `pane.children`.

```java
@TLabel(text = "Street Type")
@TAutoCompleteTextField(entries = @TEntry(values = {...}))
@TFlowPane(hgap = 20,
    pane = @TPane(children = {"streetType", "europeanCountry", "country", "combobox0", "combobox1"}))
private SimpleStringProperty streetType;

@TLabel(text = "European Country")
@TAutoCompleteTextField(entries = @TEntry(factory = CountriesListBuilder.class))
private SimpleStringProperty europeanCountry;

@TLabel(text = "Autocomplete Country (DB)")
@TAutoCompleteEntity(service = ICountryController.JNDI_NAME, ...)
private SimpleObjectProperty<Country> country;

// ... combobox0, combobox1 declared below
```

---

## 10. Grouping Advanced Controls with `@TTabPane`

Use `@TTabPane` to partition the form into tabs. Each tab lists the field names it contains.

```java
@TTabPane(tabs = {
    @TTab(fields = {"streetType", "listview0", "picklistview0"}, text = "Input controls"),
    @TTab(fields = {"tableData"},                                text = "Table View")
})
private SimpleStringProperty tab;
```

- Declare `@TTabPane` on a dedicated `SimpleStringProperty` field that acts only as a layout anchor.
- This field should appear **at the top of the class** (before all fields it references), because the engine processes fields bottom-up.
- All field names listed in `fields` must exactly match Java field names in the class.

---

## 11. `@TGenericType` Rules Summary

| Scenario                                 | Required annotation                                      |
|:-----------------------------------------|:---------------------------------------------------------|
| `ITObservableList` with entity items     | `@TGenericType(model = EntityClass.class)`               |
| `@TTableView` table rows                 | `@TGenericType(model = RowModel.class, modelView = RowMV.class)` |
| `@TDetailListField` child entities       | `@TGenericType(model = ChildEntity.class, modelView = ChildMV.class)` |

Omitting `@TGenericType` on any `ITObservableList` field causes a `COLLECTION_CONFIGURATION` runtime error.

---

## 12. Field Naming Rule (Model ↔ ModelView Binding)

`TModelView` automatically binds each property field to the corresponding field on the entity/model by **matching field names**. The binding engine:

1. Collects all fields declared on the entity/model class (and its superclasses up to `ITEntity` or `ITModel`).
2. Collects all property fields declared on the ModelView (and its superclasses up to `TModelView`).
3. For each ModelView field, looks up a field with the **same name** on the entity/model.
4. Generates JavaFX `ChangeListener` / `ListChangeListener` instances that propagate value changes from the property back to the entity setter.

**Consequence:** If a ModelView field is named `country` but the entity field is named `selectedCountry`, the binding silently fails — the property will never propagate its value to the entity. **Always use identical names.**

---

## 13. Complete Example — `AdvancedControlMV`

This is the reference implementation located at:
`app-samples-fx/src/main/java/org/tedros/samples/module/control/model/AdvancedControlMV.java`

```java
@TPresenter(
    model     = FieldModel.class,
    decorator = @TDecorator(type = TViewDecorator.class, viewTitle = "Advanced components"),
    behavior  = @TBehavior(type = TViewBehavior.class))
public class AdvancedControlMV extends TModelView<FieldModel> {

    // ── Section header ────────────────────────────────────────────────────
    @TText(textStyle = TTextStyle.LARGE, text = "Samples of advanced components")
    @TFieldBox(node = @TNode(parse = true, id = TFieldBox.TITLE))
    private SimpleStringProperty header;

    // ── Tab layout anchor ─────────────────────────────────────────────────
    // Declared ABOVE all fields referenced in its tabs.
    @TTabPane(tabs = {
        @TTab(fields = {"autoCompleteTextField", "listview0", "picklistview0"}, text = "Input controls"),
        @TTab(fields = {"list0"},                                               text = "Table View")
    })
    private SimpleStringProperty tab;

    // ── AutoComplete + ComboBox group (FlowPane) ─────────────────────────
    // @TFlowPane declared on the FIRST field of the group.
    @TLabel(text = "Auto Complete (Static entries): " + TUsualKey.STREET_TYPE)
    @TAutoCompleteTextField(
        entries = @TEntry(
            values = {SmplsKey.OPT_ALLEY, SmplsKey.OPT_AVENUE,
                      SmplsKey.OPT_BOULEVARD, SmplsKey.OPT_LANE,
                      SmplsKey.OPT_ROAD,   SmplsKey.OPT_STREET}))
    @TFlowPane(hgap = 20,
        pane = @TPane(children = {"autoCompleteTextField", "autoCompleteTextField0",
                                  "autoCompleteEntity", "combobox0", "combobox1"}))
    private SimpleStringProperty autoCompleteTextField;

    @TLabel(text = "Auto Complete (Factored entries): " + SmplsKey.EUROPEAN_COUNTRY)
    @TAutoCompleteTextField(
        entries = @TEntry(factory = CountriesListBuilder.class))
    private SimpleStringProperty autoCompleteTextField0;

    @TLabel(text = "Auto Complete (Database entries): " + TUsualKey.COUNTRY)
    @TAutoCompleteEntity(
        service = ICountryController.JNDI_NAME,
        query = @TQuery(
            entity    = Country.class,
            condition = @TCondition(field = "name", operator = TCompareOp.LIKE),
            orderBy   = @TOrder(field = "name")))
    private SimpleObjectProperty<Country> autoCompleteEntity;

    @TLabel(text = "Combo Box (Static items): " + SmplsKey.EUROPEAN_COUNTRY)
    @TComboBoxField(items = CountriesObservableListBuilder.class)
    private SimpleObjectProperty<Country> combobox0;

    @TLabel(text = "Combo Box (Database items): " + TUsualKey.COUNTRY)
    @TComboBoxField(
        process = @TProcess(
            service = ICountryController.JNDI_NAME,
            query   = @TQuery(
                entity  = Country.class,
                orderBy = @TOrder(field = "name"))))
    private SimpleObjectProperty<Country> combobox1;

    // ── List View group (FlowPane) ────────────────────────────────────────
    @TLabel(text = "List View (Multiple selection from static items):\n " + SmplsKey.EUROPEAN_COUNTRIES)
    @TListViewField(
        items   = CountriesObservableListBuilder.class,
        control = @TControl(parse = true, maxHeight = 100, prefHeight = 100))
    @TFlowPane(hgap = 20,
        pane = @TPane(children = {"listview0", "listview1"}))
    @TGenericType(model = Country.class)
    private ITObservableList<Country> listview0;

    @TLabel(text = "List View (Single Selection from database):\n" + SmplsKey.COUNTRIES)
    @TListViewField(
        control = @TControl(parse = true, maxHeight = 100, prefHeight = 100),
        process = @TProcess(
            service = ICountryController.JNDI_NAME,
            query   = @TQuery(
                entity  = Country.class,
                orderBy = @TOrder(field = "name"))))
    private SimpleObjectProperty<Country> listview1;

    // ── Pick List group (FlowPane) ────────────────────────────────────────
    @TLabel(text = "Pick List (Source from static list)")
    @TPickListField(
        height        = 100,
        sourceLabel   = SmplsKey.EUROPEAN_COUNTRIES,
        targetLabel   = TUsualKey.SELECTEDS,
        selectionMode = SelectionMode.MULTIPLE,
        items         = CountriesObservableListBuilder.class)
    @TFlowPane(hgap = 20,
        pane = @TPane(children = {"picklistview0", "picklistview1"}))
    @TGenericType(model = Country.class)
    private ITObservableList<Country> picklistview0;

    @TLabel(text = "Pick List (Source from database) ")
    @TPickListField(
        height        = 100,
        sourceLabel   = SmplsKey.COUNTRIES,
        targetLabel   = TUsualKey.SELECTEDS,
        selectionMode = SelectionMode.MULTIPLE,
        process = @TProcess(
            service = ICountryController.JNDI_NAME,
            query   = @TQuery(
                entity  = Country.class,
                orderBy = @TOrder(field = "name"))))
    @TGenericType(model = Country.class)
    private ITObservableList<CountryMV> picklistview1;

    // ── Table View ────────────────────────────────────────────────────────
    @TTableView(columns = {
        @TTableColumn(text = "Field string0",  cellValue = "string0"),
        @TTableColumn(text = "Field integer0", cellValue = "integer0"),
        @TTableColumn(text = "Field long0",    cellValue = "long0"),
        @TTableColumn(text = "Field double0",  cellValue = "double0"),
        @TTableColumn(text = "Field date0",    cellValue = "date0",
            cellFactory = @TCellFactory(parse = true,
                callBack = @TCallbackFactory(parse = true,
                    value = TMediumDateTimeCallback.class))),
        @TTableColumn(text = "Field detail0",  cellValue = "detail0")
    })
    @TGenericType(model = MasterModel.class, modelView = MasterMV.class)
    private ITObservableList<MasterMV> list0;

    public AdvancedControlMV(FieldModel model) {
        super(model);
    }
}
```

---

## 14. Row ModelView for `@TTableView`

Each row of a `@TTableView` is rendered using a `TModelView` subclass. Its fields must match the `cellValue` strings defined on the columns. No annotations are needed on row ModelView fields — they exist purely for data binding.

```java
public class MasterMV extends TModelView<MasterModel> {

    SimpleStringProperty               string0;
    SimpleIntegerProperty              integer0;
    SimpleLongProperty                 long0;
    SimpleDoubleProperty               double0;
    SimpleObjectProperty<Date>         date0;
    SimpleObjectProperty<DetailModel>  detail0;

    public MasterMV(MasterModel model) {
        super(model);
    }
}
```

The underlying model must expose standard getters/setters with matching names:

```java
public class MasterModel implements ITModel {
    private String      string0  = "Master string value";
    private Integer     integer0 = 100;
    private Long        long0    = 200L;
    private Double      double0  = 3.14;
    private Date        date0    = new Date();
    private DetailModel detail0  = new DetailModel();

    // getters and setters...
}
```

---

## 15. Building Item-List Factories

### `TGenericBuilder<List<String>>` — for `@TAutoCompleteTextField`

Returns a plain `List<String>` of suggestion strings.

```java
public class MyListBuilder extends TGenericBuilder<List<String>> {

    @Override
    public List<String> build() {
        return Arrays.asList("Option A", "Option B", "Option C");
    }
}
```

### `TGenericBuilder<ObservableList>` — for `@TComboBoxField` / `@TListViewField` / `@TPickListField`

Returns a JavaFX `ObservableList`. Items can be strings, entities, or any object whose `toString()` renders the display label.

```java
@SuppressWarnings("rawtypes")
public class MyItemsBuilder extends TGenericBuilder<ObservableList> {

    @Override
    public ObservableList<MyItem> build() {
        return FXCollections.observableArrayList(
            new MyItem("A"), new MyItem("B"), new MyItem("C"));
    }
}
```

Both factory types must be public concrete classes with a no-arg constructor.

---

## 16. Checklist for Advanced Controls

- [ ] **Field names** match the entity/model field names exactly (binding rule).
- [ ] **`@TGenericType`** is present on every `ITObservableList` field (`@TListViewField`, `@TPickListField`, `@TTableView`).
- [ ] **`@TTableView`** row `cellValue` strings match field names in the row ModelView.
- [ ] **Factory classes** extend the correct `TGenericBuilder` subtype and have a public no-arg constructor.
- [ ] **`@TFlowPane`** is placed on the **first** field listed in its `children` array.
- [ ] **`@TTabPane`** is declared above all fields referenced in its `tabs`.
- [ ] **`@TAutoCompleteEntity`** uses `TCompareOp.LIKE` in its search condition.
- [ ] **`@TComboBoxField` / `@TListViewField` with `process`** provide both `service` and `query`.
- [ ] **`@TPickListField`** always has both `sourceLabel` and `targetLabel`.
- [ ] All fields with control annotations have **getters and setters**.

---

## 17. Rules and Constraints

- **Reverse build order:** The engine processes fields bottom-to-top. Declare layout anchors (`@TTabPane`, `@TFlowPane`) on fields that appear **above** the fields they group.
- **`ITObservableList` always needs `@TGenericType`:** The framework uses it to instantiate the correct model/view type for collection items.
- **`@TTableView` rows require their own `TModelView`:** The row MV fields are bound automatically — no additional annotations needed on them.
- **Factory classes must be public and have a no-arg constructor:** They are instantiated reflectively at runtime.
- **Database-backed controls (`@TAutoCompleteEntity`, `@TComboBoxField(process)`, `@TListViewField(process)`)** require a live EJB service reachable via JNDI.
- **`cellValue` in `@TTableColumn`** must be a field name on the **row ModelView**, not on the entity directly.
- **`selectionMode` in `@TPickListField`** defaults to single selection; declare `SelectionMode.MULTIPLE` explicitly when needed.
