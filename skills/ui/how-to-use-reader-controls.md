# Skill: How to Use Reader / Display Controls in Tedros

## 1. Objective

This skill explains how to use **read-only display controls** inside a Tedros ModelView. These controls render entity field values as formatted labels — they do not accept user input. They are used to show data in a clean, readable layout, either bound directly to a top-level property or to fields on a nested model object.

---

## 2. Overview of Reader Controls

| Annotation      | Purpose                                                                                      |
|:----------------|:---------------------------------------------------------------------------------------------|
| `@TShowField`   | Renders the value of a property field as a formatted read-only label                         |
| `@TFieldSet`    | Groups `@TShowField` displays for a **nested model object** inside a titled bordered container |

Both annotations use the same standard JavaFX property types used for other controls. No special property type is needed.

---

## 3. `@TShowField` — Read-Only Field Display

Renders a read-only label that shows the current value of the bound property. Supports formatting, masks, date/time styles, converters, and HTML rendering.

### 3.1 Minimal — display the value as-is

```java
@TLabel(text = "Long Value")
@TShowField()
private SimpleLongProperty long0;
```

### 3.2 With `format` — apply `String.format` pattern

```java
@TLabel(text = "String Value")
@TShowField(fields = @TField(format = "--> %s <--"))
private SimpleStringProperty string0;
```

```java
@TLabel(text = "Price")
@TShowField(fields = @TField(format = " $ %(,.2f "))
private SimpleDoubleProperty price;
```

The `format` string follows Java's `String.format()` syntax. The bound property value is passed as the single argument.

### 3.3 With `mask` — apply a display mask

```java
@TLabel(text = "Code")
@TShowField(fields = @TField(mask = "9999-99"))
private SimpleIntegerProperty code;
```

Mask characters:
- `9` → numeric digits `[0-9]`
- `A` → alphabetic characters `[A-Z]`
- `#` → any character (alpha, numeric, or symbol)
- Any other character is treated as a literal separator

### 3.4 With `dateStyle` and `timeStyle` — format a `Date`

```java
@TLabel(text = "Created At (time only)")
@TShowField(fields = @TField(dateStyle = TDateStyle.NONE, timeStyle = TTimeStyle.FULL))
private SimpleObjectProperty<Date> createdAt;
```

```java
@TLabel(text = "Birth Date (date only)")
@TShowField(fields = @TField(dateStyle = TDateStyle.MEDIUM, timeStyle = TTimeStyle.NONE))
private SimpleObjectProperty<Date> birthDate;
```

#### `TDateStyle` values

| Value              | Output example (locale-dependent)          |
|:-------------------|:-------------------------------------------|
| `TDateStyle.NONE`  | Date part suppressed (time-only display)   |
| `TDateStyle.DEFAULT` | Default format for the locale            |
| `TDateStyle.SHORT` | `29/03/26`                                 |
| `TDateStyle.MEDIUM`| `29 Mar 2026`                              |
| `TDateStyle.LONG`  | `29 March 2026`                            |
| `TDateStyle.FULL`  | `Sunday, 29 March 2026`                    |

#### `TTimeStyle` values

| Value              | Output example (locale-dependent)          |
|:-------------------|:-------------------------------------------|
| `TTimeStyle.NONE`  | Time part suppressed (date-only display)   |
| `TTimeStyle.DEFAULT`| Default format for the locale            |
| `TTimeStyle.SHORT` | `20:53`                                    |
| `TTimeStyle.MEDIUM`| `20:53:10`                                 |
| `TTimeStyle.LONG`  | `20:53:10 BRT`                             |
| `TTimeStyle.FULL`  | `20:53:10 Brasília Standard Time`          |

### 3.5 With `renderHtml = true` — render HTML content

```java
@TLabel(text = "Description")
@TShowField(fields = @TField(renderHtml = true))
private SimpleStringProperty description;
```

When `renderHtml = true`, the string value is interpreted as HTML markup and rendered in a `WebView`.

### 3.6 With `converter` — apply a custom value converter

```java
@TLabel(text = "Active")
@TShowField(fields = @TField(converter = TField.BOOLEAN_TO_YES_NO_CONVERTER))
private SimpleObjectProperty<Boolean> active;
```

`TField.BOOLEAN_TO_YES_NO_CONVERTER` is a built-in constant that converts `true`/`false` to `"Yes"`/`"No"`. You can also supply any custom class implementing `TConverter<Object, String>`.

---

## 4. `@TShowField` — Attribute Reference

### On `@TShowField` itself

| Attribute  | Default              | Description                                                                  |
|:-----------|:---------------------|:-----------------------------------------------------------------------------|
| `fields`   | `{@TField()}`        | Array of `@TField` descriptors defining how to display the value             |
| `layout`   | `TLayoutType.HBOX`   | Layout of the label + value pair: `HBOX` (horizontal), `VBOX` (vertical), `FLOWPANE` |

### On the nested `@TField`

| Attribute       | Default              | Description                                                                               |
|:---------------|:---------------------|:------------------------------------------------------------------------------------------|
| `name`          | `""`                 | Field name on the nested model. Empty string = use the property field's value directly    |
| `format`        | `""`                 | `String.format()` pattern. E.g. `"$ %(,.2f"`, `"--> %s <--"`. Not used if empty.         |
| `mask`          | `""`                 | Display mask. `9`=digit, `A`=letter, `#`=any. Not used if empty.                         |
| `dateStyle`     | `TDateStyle.DEFAULT` | Date formatting style. Use `TDateStyle.NONE` to suppress the date part.                   |
| `timeStyle`     | `TTimeStyle.NONE`    | Time formatting style. Use `TTimeStyle.NONE` to suppress the time part (default).         |
| `label`         | `""`                 | Inline label to show beside the value (used in `@TFieldSet` multi-field mode).            |
| `labelPosition` | `TLabelPosition.DEFAULT` | Position of the inline label: `DEFAULT`, `LEFT`, `TOP`, `RIGHT`, `BOTTOM`.           |
| `renderHtml`    | `false`              | If `true`, the string value is rendered as HTML in a `WebView`.                           |
| `converter`     | —                    | `TConverter<Object, String>` class to transform the value before display.                 |

---

## 5. `@TFieldSet` — Grouped Display for Nested Models

`@TFieldSet` wraps a set of `@TShowField` definitions into a **titled, bordered group box** (HTML `<fieldset>` equivalent in JavaFX). It is specifically designed to display multiple fields from a **nested model object** (an object referenced by a property field, not a top-level primitive).

### Pattern

1. Place `@TFieldSet` on a property field that holds a **nested model object** (e.g., `SimpleObjectProperty<MasterModel>`).
2. List the nested fields to display using `@TShowField(fields = {...})` on the same property field.
3. Use dot-notation (`"detail0.string0"`) to access fields on objects nested two levels deep.

```java
@TFieldSet(
    fields  = {"model0"},
    legend  = "Showing the MasterModel fields",
    region  = @TRegion(parse = true, minWidth = 480, maxWidth = 480, prefWidth = 480))
@TShowField(
    layout = TLayoutType.FLOWPANE,
    fields = {
        @TField(name = "string0",  label = "String value"),
        @TField(name = "integer0", label = "Integer value"),
        @TField(name = "long0",    label = "Long value"),
        @TField(name = "double0",  label = "Double value"),
        @TField(name = "date0",    label = "Date value", timeStyle = TTimeStyle.SHORT),

        // Two levels deep: field on a nested model inside the nested model
        @TField(name = "detail0.string0",  label = "Detail string"),
        @TField(name = "detail0.integer0", label = "Detail integer"),
        @TField(name = "detail0.long0",    label = "Detail long"),
        @TField(name = "detail0.double0",  label = "Detail double"),
        @TField(name = "detail0.date0",    label = "Detail date",
            dateStyle = TDateStyle.FULL, timeStyle = TTimeStyle.FULL)
    })
private SimpleObjectProperty<MasterModel> model0;
```

### `@TFieldSet` Attribute Reference

| Attribute          | Default                            | Description                                                                      |
|:------------------|:-----------------------------------|:---------------------------------------------------------------------------------|
| `fields`           | *(required)*                       | Array of field names that belong to this fieldset (typically just the one anchor field name) |
| `legend`           | *(required)*                       | Title/caption displayed in the fieldset border                                   |
| `region`           | `@TRegion(parse = false)`          | Sizing constraints for the fieldset container (`minWidth`, `maxWidth`, `prefWidth`, etc.) |
| `layoutType`       | `TLayoutType.FLOWPANE`             | Internal layout of the displayed fields: `HBOX`, `VBOX`, or `FLOWPANE`           |
| `skipAnnotatedField`| `false`                           | If `true`, the anchor property field itself is not rendered — only the fieldset group is |
| `mode`             | `{TViewMode.EDIT, TViewMode.READER}` | Restrict rendering to `EDIT` mode only or `READER` mode only if needed         |

> **Dot-notation for nested access:** `"detail0.string0"` means "get `model0.getDetail0().getString0()"`. The framework resolves the getter chain at runtime. The depth can be two or more levels.

---

## 6. `@TLayoutType` — Layout Options

Used in `@TShowField.layout` and `@TFieldSet.layoutType`:

| Value                  | JavaFX equivalent | Description                                     |
|:-----------------------|:------------------|:------------------------------------------------|
| `TLayoutType.HBOX`     | `HBox`            | Stacks label and value horizontally (default for `@TShowField`) |
| `TLayoutType.VBOX`     | `VBox`            | Stacks label above value vertically             |
| `TLayoutType.FLOWPANE` | `FlowPane`        | Wraps items in a flowing horizontal+vertical layout (default for `@TFieldSet`) |

---

## 7. Grouping `@TShowField` Controls with `@TFlowPane`

Multiple `@TShowField` controls can be placed side-by-side in a horizontal flow with `@TFlowPane`, exactly like any other Tedros control.

```java
@TLabel(text = "String value")
@TShowField(fields = @TField(format = "--> %s <--"))
@TFlowPane(hgap = 20,
    pane = @TPane(children = {"string0", "integer0", "long0", "double0", "date0"}))
private SimpleStringProperty string0;

@TLabel(text = "Integer value")
@TShowField(fields = @TField(mask = "9999-99"))
private SimpleIntegerProperty integer0;

// ... long0, double0, date0 declared below
```

The `@TFlowPane` annotation goes on the **first field** listed in `children`, which must be declared **above** all other group members in the class body.

---

## 8. Complete Example — `ReaderControlMV`

Reference source: `app-samples-fx/.../module/control/model/ReaderControlMV.java`

```java
@TPresenter(
    model     = FieldModel.class,
    decorator = @TDecorator(type = TViewDecorator.class, viewTitle = "Reader components"),
    behavior  = @TBehavior(type = TViewBehavior.class))
public class ReaderControlMV extends TModelView<FieldModel> {

    // ── Section header ────────────────────────────────────────────────────
    @TText(textStyle = TTextStyle.LARGE, text = "Samples of readers components")
    @TFieldBox(node = @TNode(parse = true, id = TFieldBox.TITLE))
    private SimpleStringProperty header;

    // ── FlowPane anchor — first field of the display group ────────────────
    @TLabel(text = "Shows string0 field")
    @TShowField(fields = @TField(format = "--> %s <--"))
    @TFlowPane(hgap = 20,
        pane = @TPane(children = {"string0", "integer0", "long0", "double0", "date0"}))
    private SimpleStringProperty string0;

    @TLabel(text = "Shows integer0 field")
    @TShowField(fields = @TField(mask = "9999-99"))
    private SimpleIntegerProperty integer0;

    @TLabel(text = "Shows double0 field")
    @TShowField(fields = @TField(format = " $ %(,.2f "))
    private SimpleDoubleProperty double0;

    @TLabel(text = "Shows long0 field")
    @TShowField()
    private SimpleLongProperty long0;

    @TLabel(text = "Shows date0 field")
    @TShowField(fields = @TField(dateStyle = TDateStyle.NONE, timeStyle = TTimeStyle.FULL))
    private SimpleObjectProperty<Date> date0;

    // ── FieldSet for nested model (all sub-fields declared inline) ────────
    @TFieldSet(
        fields  = {"model0"},
        legend  = "Showing the MasterModel fields",
        region  = @TRegion(parse = true, minWidth = 480, maxWidth = 480, prefWidth = 480))
    @TShowField(
        layout = TLayoutType.FLOWPANE,
        fields = {
            @TField(name = "string0",  label = "Shows string0 field"),
            @TField(name = "integer0", label = "Shows integer0 field"),
            @TField(name = "long0",    label = "Shows long0 field"),
            @TField(name = "double0",  label = "Shows double0 field"),
            @TField(name = "date0",    label = "Shows date0 field",
                timeStyle = TTimeStyle.SHORT),

            @TField(name = "detail0.string0",  label = "Shows detail0.string0 field"),
            @TField(name = "detail0.integer0", label = "Shows detail0.integer0 field"),
            @TField(name = "detail0.long0",    label = "Shows detail0.long0 field"),
            @TField(name = "detail0.double0",  label = "Shows detail0.double0 field"),
            @TField(name = "detail0.date0",    label = "Shows detail0.date0 field",
                dateStyle = TDateStyle.FULL, timeStyle = TTimeStyle.FULL)
        })
    private SimpleObjectProperty<MasterModel> model0;

    public ReaderControlMV(FieldModel model) {
        super(model);
    }

    // --- getters and setters ---
}
```

---

## 9. Quick Reference

| Scenario                                   | Annotation + key config                                           |
|:-------------------------------------------|:------------------------------------------------------------------|
| Display any value as-is                    | `@TShowField()`                                                   |
| Display with `String.format` pattern       | `@TShowField(fields = @TField(format = "$ %(,.2f"))`             |
| Display with input mask                    | `@TShowField(fields = @TField(mask = "9999-99"))`                 |
| Display date only                          | `@TShowField(fields = @TField(dateStyle = MEDIUM, timeStyle = NONE))` |
| Display time only                          | `@TShowField(fields = @TField(dateStyle = NONE, timeStyle = FULL))` |
| Display date + time                        | `@TShowField(fields = @TField(dateStyle = MEDIUM, timeStyle = SHORT))` |
| Display HTML content                       | `@TShowField(fields = @TField(renderHtml = true))`               |
| Display Boolean as Yes/No                  | `@TShowField(fields = @TField(converter = TField.BOOLEAN_TO_YES_NO_CONVERTER))` |
| Display nested model fields                | `@TFieldSet(...) @TShowField(fields = {@TField(name = "fieldName", ...)})` |
| Display deep nested fields                 | `@TField(name = "nested.deepField", ...)`                         |
| Group multiple show-fields horizontally    | `@TFlowPane` on the first field (same as other controls)          |

---

## 10. Checklist for Reader Controls

- [ ] Field name matches the entity/model field name exactly (binding rule).
- [ ] Property type matches the entity field type (`SimpleStringProperty` → `String`, `SimpleDoubleProperty` → `Double`, etc.).
- [ ] For `Date` fields: always set both `dateStyle` and `timeStyle` explicitly. Default is `dateStyle = DEFAULT, timeStyle = NONE`.
- [ ] For nested model display via `@TFieldSet`: `@TField.name` is the **entity field name** on the nested object — not the ModelView field name.
- [ ] For two-level nesting: use dot-notation `"nested.fieldName"` in `@TField.name`.
- [ ] `@TFlowPane` is placed on the **first** field listed in its `children` array, declared **above** the other group members.
- [ ] `@TFieldSet.fields` lists the **ModelView field name** that holds the nested model (`"model0"`, not the nested fields themselves).
- [ ] `@TFieldSet.legend` is always set (it is required by the annotation — no default value).
- [ ] All fields have **getters and setters**.

---

## 11. Rules and Constraints

- **`@TShowField` is read-only:** It renders data from the bound property but does not accept user input. Do not use it where the user needs to edit a value.
- **`format` vs `mask`:** Use `format` for `String.format()` patterns (works on any type). Use `mask` for character-by-character display masks (best suited for `String` and numeric values).
- **`TDateStyle.NONE` + `TTimeStyle.NONE` combination:** Avoid it — it produces no output. At least one of `dateStyle` or `timeStyle` must be non-`NONE` for a `Date` field.
- **`@TFieldSet.fields`** lists the **ModelView** field names that will be absorbed into the fieldset container — not the sub-fields of the nested model. Usually this is just `{"model0"}`.
- **`@TShowField.fields` inside `@TFieldSet`:** Each `@TField.name` here refers to a field **on the nested entity** (e.g., `"string0"` on `MasterModel`). Leave `name` empty only on a standalone `@TShowField` at the top level (it then reads the property's value directly).
- **Dot-notation in nested `@TField.name`:** The framework resolves the chain by calling successive getters. All intermediate objects must be non-null at render time, or the value will not display.
- **`TField.BOOLEAN_TO_YES_NO_CONVERTER`:** This is a constant (`Class<TBooleanToYesNoConverter>`), not an instance. Pass it directly to `converter = TField.BOOLEAN_TO_YES_NO_CONVERTER`.
- **`@TFieldSet` bottom-to-top rule:** Because the engine processes fields bottom-to-top, any `@TFieldSet` that references a field by name (`fields = {"model0"}`) must be declared **after** that field in the class body when field ordering matters. In practice, since `@TFieldSet` renders an entirely separate grouped panel, it is safe to declare it at the bottom of the class.
