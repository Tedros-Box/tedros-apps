# Skill: How to Use Basic Form Controls in Tedros

## 1. Objective

This skill explains how to declare and configure **basic field controls** inside a Tedros ModelView. It covers all the fundamental input types — text, numeric, date, boolean, radio groups, color picker, mask, slider, and textarea — configured entirely through Java annotations on `TModelView` fields.

---

## 2. Prerequisites

- You must already know how to create a ModelView. See the skill `how-to-create-view.md`.
- Every field name in the ModelView **must exactly match** the corresponding field name in the entity/model class, because `TModelView` uses reflection to bind properties to the entity by name (see Section 12 of `how-to-use-advanced-controls.md` for a detailed explanation of the binding mechanism).
- The form engine processes fields **bottom-to-top**: declare layout anchors (`@TFlowPane`) on fields that appear **above** the fields they group.

---

## 3. Field Annotation Pattern

Each form field follows a consistent three-layer annotation pattern:

```
1. @TLabel          — the visible label above or beside the control
2. @T<ControlType>  — the control itself (e.g., @TTextField, @TDatePickerField)
3. @TFlowPane       — optional layout grouping (declared only on the FIRST field of a group)
```

All fields must also have **getters and setters**.

---

## 4. `@TTextField` — Single-Line Text Input

```java
@TLabel(text = "Name")
@TTextField
private SimpleStringProperty name;
```

| Attribute   | Default | Description                          |
|:------------|:--------|:-------------------------------------|
| `maxLength` | `0`     | Maximum number of characters allowed |
| `required`  | `false` | Marks the field as mandatory         |

- Property type: `SimpleStringProperty`.
- Field name must match the entity's `String` field name exactly.

---

## 5. `@TMaskField` — Masked Text Input

Use when the input must conform to a specific format (postal code, phone number, etc.).

```java
@TLabel(text = "Postal Code")
@TMaskField(mask = "9999-999")
private SimpleStringProperty postalCode;
```

| Attribute | Description                                                                          |
|:----------|:-------------------------------------------------------------------------------------|
| `mask`    | Pattern string. `9` = digit, `A` = letter, `*` = any character. Literal characters are typed as-is. |

- Property type: `SimpleStringProperty`.
- Field name must match the entity's `String` field name exactly.

---

## 6. `@TPasswordField` — Password Input

Renders a masked text field where characters are hidden.

```java
@TLabel(text = "Password")
@TPasswordField(required = true)
private SimpleStringProperty password;
```

| Attribute  | Default | Description              |
|:-----------|:--------|:-------------------------|
| `required` | `false` | Marks the field as mandatory |

- Property type: `SimpleStringProperty`.
- Field name must match the entity's `String` field name exactly.

---

## 7. Numeric Fields

### 7.1 `@TIntegerField` — Integer Input

```java
@TLabel(text = "Quantity")
@TIntegerField(validate = TValidateNumber.GREATHER_THAN_ZERO)
private SimpleIntegerProperty quantity;
```

| Attribute  | Default | Description                                              |
|:-----------|:--------|:---------------------------------------------------------|
| `validate` | —       | Optional `TValidateNumber` rule (e.g., `GREATHER_THAN_ZERO`) |

- Property type: `SimpleIntegerProperty`.
- Entity field type: `Integer` or `int`.

---

### 7.2 `@TLongField` — Long Integer Input

```java
@TLabel(text = "Record ID")
@TLongField
private SimpleLongProperty recordId;
```

- Property type: `SimpleLongProperty`.
- Entity field type: `Long` or `long`.

---

### 7.3 `@TDoubleField` — Double Input

```java
@TLabel(text = "Measurement")
@TDoubleField
private SimpleDoubleProperty measurement;
```

- Property type: `SimpleDoubleProperty`.
- Entity field type: `Double` or `double`.

---

### 7.4 `@TBigIntegerField` — BigInteger Input

```java
@TLabel(text = "Big Integer")
@TBigIntegerField
private SimpleObjectProperty<BigInteger> bigIntegerField;
```

- Property type: `SimpleObjectProperty<BigInteger>`.
- Entity field type: `BigInteger`.

---

### 7.5 `@TBigDecimalField` — BigDecimal Input (Currency / Decimal)

```java
@TLabel(text = "Price")
@TBigDecimalField
private SimpleObjectProperty<BigDecimal> price;
```

- Property type: `SimpleObjectProperty<BigDecimal>`.
- Entity field type: `BigDecimal`.

---

### 7.6 `@TNumberSpinnerField` — Spinner with Min/Max

Renders a numeric spinner control the user can increment or decrement.

```java
@TLabel(text = "Month")
@TNumberSpinnerField(maxValue = 12)
private SimpleLongProperty month;
```

| Attribute  | Default | Description              |
|:-----------|:--------|:-------------------------|
| `minValue` | `0`     | Minimum allowed value    |
| `maxValue` | `100`   | Maximum allowed value    |

- Property type: `SimpleLongProperty`.
- Entity field type: `Long` or `long`.

---

### 7.7 `@TSliderField` — Slider

Renders a horizontal slider for selecting a numeric value within a range.

```java
@TLabel(text = "Volume")
@TSliderField(
    max             = 500,
    min             = 0,
    showTickLabels  = true,
    showTickMarks   = true,
    snapToTicks     = true,
    blockIncrement  = 1,
    majorTickUnit   = 175,
    minorTickCount  = 25)
private SimpleDoubleProperty volume;
```

| Attribute        | Default | Description                                          |
|:----------------|:--------|:-----------------------------------------------------|
| `min`            | `0`     | Minimum value                                        |
| `max`            | `100`   | Maximum value                                        |
| `showTickLabels` | `false` | Display numeric labels at tick marks                 |
| `showTickMarks`  | `false` | Display tick mark lines on the slider track          |
| `snapToTicks`    | `false` | Snap the thumb to the nearest tick                   |
| `blockIncrement` | `1`     | How much the value changes per keyboard block action |
| `majorTickUnit`  | `25`    | Distance between major (labeled) ticks               |
| `minorTickCount` | `3`     | Number of minor ticks between each major tick        |

- Property type: `SimpleDoubleProperty`.
- Entity field type: `Double` or `double`.

---

## 8. `@TTextAreaField` — Multi-Line Text Input

```java
@TLabel(text = "Notes")
@TTextAreaField(wrapText = true, prefRowCount = 6)
private SimpleStringProperty notes;
```

| Attribute      | Default | Description                                   |
|:--------------|:--------|:----------------------------------------------|
| `wrapText`     | `false` | Wrap long lines inside the text area          |
| `prefRowCount` | `3`     | Preferred visible row count (controls height) |

- Property type: `SimpleStringProperty`.
- Entity field type: `String`.

---

## 9. `@TDatePickerField` — Date Picker

### 9.1 Date Only (default format)

```java
@TLabel(text = "Birth Date")
@TDatePickerField
private SimpleObjectProperty<Date> birthDate;
```

### 9.2 Date + Time (custom format)

Provide a `dateFormat` factory class to render a date-time picker. The built-in `DateTimeFormatBuilder` adds time selection.

```java
@TLabel(text = "Appointment Date/Time")
@TDatePickerField(dateFormat = DateTimeFormatBuilder.class)
private SimpleObjectProperty<Date> appointmentDate;
```

| Attribute    | Default | Description                                                        |
|:------------|:--------|:-------------------------------------------------------------------|
| `required`   | `false` | Marks the field as mandatory                                       |
| `dateFormat` | —       | Optional `TGenericBuilder` class that provides the date format pattern |

- Property type: `SimpleObjectProperty<Date>`.
- Entity field type: `Date`.
- Field name must match the entity's `Date` field name exactly.

---

## 10. `@TColorPickerField` — Color Picker

Renders a color selection button that opens a JavaFX color palette.

```java
@TLabel(text = "Background Color")
@TColorPickerField
private SimpleObjectProperty<Color> backgroundColor;
```

- Property type: `SimpleObjectProperty<Color>` (`java.awt.Color`).
- Entity field type: `Color`.
- Field name must match the entity's `Color` field name exactly.

---

## 11. Radio Group Controls

### 11.1 `@THRadioGroup` — Horizontal Radio Button Group

Renders radio buttons laid out **horizontally**.

```java
@TLabel(text = "Gender")
@THRadioGroup(radio = {
    @TRadio(text = TUsualKey.MASCULINE, userData = "male"),
    @TRadio(text = TUsualKey.FEMININE,  userData = "female")
})
private SimpleStringProperty gender;
```

### 11.2 `@TVRadioGroup` — Vertical Radio Button Group

Renders radio buttons stacked **vertically**.

```java
@TLabel(text = "Gender")
@TVRadioGroup(radio = {
    @TRadio(text = TUsualKey.MASCULINE, userData = "male"),
    @TRadio(text = TUsualKey.FEMININE,  userData = "female")
})
private SimpleStringProperty genderVertical;
```

**`@TRadio` attributes:**

| Attribute  | Description                                                              |
|:-----------|:-------------------------------------------------------------------------|
| `text`     | i18n key or literal string for the radio button label                    |
| `userData` | The string value stored in the property when this button is selected     |

- Property type: `SimpleStringProperty`.
- Entity field type: `String`.
- Selected value written to the entity is the `userData` string of the chosen radio button.

---

## 12. `@TCheckBoxField` — Checkbox

Renders a checkbox. Optionally includes an inline label beside the checkbox.

```java
@TLabel(text = "Active: ", position = TLabelPosition.LEFT)
@TCheckBoxField(labeled = @TLabeled(parse = true, text = TUsualKey.ACTIVE))
private SimpleObjectProperty<Boolean> active;
```

| Attribute on `@TLabel` | Description                                                            |
|:----------------------|:-----------------------------------------------------------------------|
| `position`            | `TLabelPosition.LEFT` places the label to the left of the checkbox. Default is above. |

| Attribute on `@TCheckBoxField` | Description                                                  |
|:-------------------------------|:-------------------------------------------------------------|
| `labeled`                      | `@TLabeled` configures an additional text drawn on the checkbox control itself |

| Attribute on `@TLabeled` | Description                                                    |
|:------------------------|:---------------------------------------------------------------|
| `parse`                 | `true` = resolve `text` as an i18n key                        |
| `text`                  | i18n key or literal string shown next to the checkbox          |

- Property type: `SimpleObjectProperty<Boolean>`.
- Entity field type: `Boolean` or `boolean`.

---

## 13. Section Headers with `@TFieldBox` + `@TText`

Use this pattern to add a large styled title/section header into the form. No model binding is needed — the field is UI-only.

```java
@TText(textStyle = TTextStyle.LARGE, text = "my.section.title.key")
@TFieldBox(node = @TNode(parse = true, id = TFieldBox.TITLE))
private SimpleStringProperty header;
```

| Attribute on `@TText` | Description                                                         |
|:---------------------|:--------------------------------------------------------------------|
| `textStyle`          | Visual style: `TTextStyle.LARGE`, `MEDIUM`, `SMALL`, `TITLE`, etc. |
| `text`               | i18n key or literal string to display                               |

> Since `header` is used only as a UI anchor and holds no entity data, it will not cause a binding error even if the model has no `header` field — the engine simply skips fields that have no matching entity field.

---

## 14. Grouping Fields with `@TFlowPane`

Any set of basic controls can be grouped into a horizontal wrapping row using `@TFlowPane`. Place the annotation on the **first field** of the group.

```java
@TLabel(text = "Text Field")
@TTextField
@TFlowPane(hgap = 20,
    pane = @TPane(children = {
        "textField", "maskField", "passwordField",
        "integerField", "longField", "doubleField",
        "bigIntegerField", "bigDecimalField", "numberSpinnerField"
    }))
private SimpleStringProperty textField;

@TLabel(text = "Mask Field (Postal Code)")
@TMaskField(mask = "9999-999")
private SimpleStringProperty maskField;

// ... other children fields declared below
```

**Rules:**
- `pane.children` must list Java field names in the order they should appear left-to-right.
- The `@TFlowPane` annotation goes on the field whose name is listed **first** in `children`.
- All sibling fields must be declared **below** the anchor in the class body (the engine processes bottom-up, so children are ready when the layout is assembled).

---

## 15. Complete Example — `BasicControlMV`

Reference source: `app-samples-fx/.../module/control/model/BasicControlMV.java`

```java
@TPresenter(
    model     = FieldModel.class,
    decorator = @TDecorator(type = TViewDecorator.class, viewTitle = "Basic components"),
    behavior  = @TBehavior(type = TViewBehavior.class))
public class BasicControlMV extends TModelView<FieldModel> {

    // ── Section header (UI-only, no entity binding needed) ────────────────
    @TText(textStyle = TTextStyle.LARGE, text = "Samples of Field Components")
    @TFieldBox(node = @TNode(parse = true, id = TFieldBox.TITLE))
    private SimpleStringProperty header;

    // ── Text / Numeric group (FlowPane anchor on textField) ───────────────
    @TLabel(text = "Text Field")
    @TTextField
    @TFlowPane(hgap = 20,
        pane = @TPane(children = {
            "textField", "maskField", "passwordField",
            "integerField", "longField", "doubleField",
            "bigIntegerField", "bigDecimalField", "numberSpinnerField"
        }))
    private SimpleStringProperty textField;

    @TLabel(text = "Mask Field (Postal Code)")
    @TMaskField(mask = "9999-999")
    private SimpleStringProperty maskField;

    @TLabel(text = "Password Field")
    @TPasswordField(required = true)
    private SimpleStringProperty passwordField;

    @TLabel(text = "Integer Field")
    @TIntegerField(validate = TValidateNumber.GREATHER_THAN_ZERO)
    private SimpleIntegerProperty integerField;

    @TLabel(text = "Long Field")
    @TLongField
    private SimpleLongProperty longField;

    @TLabel(text = "Double Field")
    @TDoubleField
    private SimpleDoubleProperty doubleField;

    @TLabel(text = "Big Integer Field")
    @TBigIntegerField
    private SimpleObjectProperty<BigInteger> bigIntegerField;

    @TLabel(text = "Big Decimal Field")
    @TBigDecimalField
    private SimpleObjectProperty<BigDecimal> bigDecimalField;

    @TLabel(text = "Number Spinner Field")
    @TNumberSpinnerField(maxValue = 12)
    private SimpleLongProperty numberSpinnerField;

    // ── Standalone controls ───────────────────────────────────────────────
    @TLabel(text = "Slider Field")
    @TSliderField(
        max            = 500,
        min            = 0,
        showTickLabels = true,
        showTickMarks  = true,
        snapToTicks    = true,
        blockIncrement = 1,
        majorTickUnit  = 175,
        minorTickCount = 25)
    private SimpleDoubleProperty sliderField;

    @TLabel(text = "Textarea Field")
    @TTextAreaField(wrapText = true, prefRowCount = 6)
    private SimpleStringProperty textAreaField;

    // ── Date / Color / Radio / Checkbox group (FlowPane anchor on dateField)
    @TLabel(text = "Date Picker Field")
    @TDatePickerField
    @TFlowPane(hgap = 20,
        pane = @TPane(children = {
            "dateField", "dateTimeField", "colorField",
            "horRadioButtonField", "verRadioButtonField", "checkBoxField"
        }))
    private SimpleObjectProperty<Date> dateField;

    @TLabel(text = "Date Time Picker Field")
    @TDatePickerField(dateFormat = DateTimeFormatBuilder.class)
    private SimpleObjectProperty<Date> dateTimeField;

    @TLabel(text = "Color Picker Field")
    @TColorPickerField
    private SimpleObjectProperty<Color> colorField;

    @TLabel(text = "Horizontal Radio Group (" + TUsualKey.SEX + ")")
    @THRadioGroup(radio = {
        @TRadio(text = TUsualKey.MASCULINE, userData = "male"),
        @TRadio(text = TUsualKey.FEMININE,  userData = "female")
    })
    private SimpleStringProperty horRadioButtonField;

    @TLabel(text = "Vertical Radio Group (" + TUsualKey.SEX + ")")
    @TVRadioGroup(radio = {
        @TRadio(text = TUsualKey.MASCULINE, userData = "male"),
        @TRadio(text = TUsualKey.FEMININE,  userData = "female")
    })
    private SimpleStringProperty verRadioButtonField;

    @TLabel(text = "Checkbox Field: ", position = TLabelPosition.LEFT)
    @TCheckBoxField(labeled = @TLabeled(parse = true, text = TUsualKey.ACTIVE))
    private SimpleObjectProperty<Boolean> checkBoxField;

    public BasicControlMV(FieldModel model) {
        super(model);
    }

    // --- getters and setters for every field ---
}
```

---

## 16. Quick Reference — Basic Controls

| Annotation              | Property Type                       | Entity Type          | Notes                                         |
|:------------------------|:------------------------------------|:---------------------|:----------------------------------------------|
| `@TTextField`           | `SimpleStringProperty`              | `String`             | `maxLength`, `required`                       |
| `@TMaskField`           | `SimpleStringProperty`              | `String`             | `mask` pattern (`9`=digit, `A`=letter)        |
| `@TPasswordField`       | `SimpleStringProperty`              | `String`             | `required`                                    |
| `@TIntegerField`        | `SimpleIntegerProperty`             | `Integer` / `int`    | `validate` with `TValidateNumber`             |
| `@TLongField`           | `SimpleLongProperty`                | `Long` / `long`      | No extra attributes required                  |
| `@TDoubleField`         | `SimpleDoubleProperty`              | `Double` / `double`  | No extra attributes required                  |
| `@TBigIntegerField`     | `SimpleObjectProperty<BigInteger>`  | `BigInteger`         | No extra attributes required                  |
| `@TBigDecimalField`     | `SimpleObjectProperty<BigDecimal>`  | `BigDecimal`         | No extra attributes required                  |
| `@TNumberSpinnerField`  | `SimpleLongProperty`                | `Long` / `long`      | `minValue`, `maxValue`                        |
| `@TSliderField`         | `SimpleDoubleProperty`              | `Double` / `double`  | `min`, `max`, `majorTickUnit`, tick options   |
| `@TTextAreaField`       | `SimpleStringProperty`              | `String`             | `wrapText`, `prefRowCount`                    |
| `@TDatePickerField`     | `SimpleObjectProperty<Date>`        | `Date`               | `required`, `dateFormat` for date+time        |
| `@TColorPickerField`    | `SimpleObjectProperty<Color>`       | `Color` (awt)        | No extra attributes required                  |
| `@THRadioGroup`         | `SimpleStringProperty`              | `String`             | Each `@TRadio.userData` is the stored value   |
| `@TVRadioGroup`         | `SimpleStringProperty`              | `String`             | Same as `@THRadioGroup`, stacked vertically   |
| `@TCheckBoxField`       | `SimpleObjectProperty<Boolean>`     | `Boolean`            | `labeled` for inline text; `TLabelPosition`   |

---

## 17. Checklist for Basic Controls

- [ ] Field name matches the entity/model field name exactly (binding rule).
- [ ] Property type matches the entity field type according to the table above.
- [ ] Every field has both **getter** and **setter**.
- [ ] `@TLabel` is present above every control annotation.
- [ ] `@TFlowPane` is declared only on the **first** field listed in its `children` array.
- [ ] All fields in a `@TFlowPane` group are declared **below** the anchor field in the class body.
- [ ] `required = true` is set on mandatory fields (`@TTextField`, `@TPasswordField`, `@TDatePickerField`).
- [ ] For `@TMaskField`, the `mask` pattern uses `9` (digit), `A` (letter), or `*` (any character) correctly.
- [ ] For `@TNumberSpinnerField` and `@TSliderField`, `minValue`/`maxValue` / `min`/`max` are always set explicitly.
- [ ] For `@TDatePickerField` with time, supply a `dateFormat` builder class (e.g., `DateTimeFormatBuilder.class`).
- [ ] For `@TCheckBoxField`, use `TLabelPosition.LEFT` on `@TLabel` if you want the label beside the checkbox.
- [ ] Section headers (`@TText` + `@TFieldBox`) do not need matching entity fields.

---

## 18. Rules and Constraints

- **Field name binding:** The `TModelView` engine matches property names to entity field names by reflection. A mismatch silently skips the binding — value changes on the property will not reach the entity.
- **Reverse render order:** The `TFormEngine` processes ModelView fields bottom-to-top. Layout containers are assembled after all controls they reference have been built. Always declare the `@TFlowPane` anchor **above** its children in the class.
- **No `@TGenericType` needed for basic controls:** That annotation is only required for collection-based fields (`ITObservableList`).
- **`@TFieldBox` section headers:** These are purely presentational. The header field (`SimpleStringProperty header`) does not need to match any entity field.
- **`@TRadio.userData`:** The string assigned to `userData` is the value stored in the entity when the user selects that radio button. Use meaningful, stable string constants — not display labels.
- **`Color` type:** Use `java.awt.Color` for `@TColorPickerField`. The framework handles the JavaFX ↔ AWT color conversion internally.
- **Standalone fields:** `@TSliderField` and `@TTextAreaField` typically render as full-width standalone controls and do not need to be inside a `@TFlowPane`.
