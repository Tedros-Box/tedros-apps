# Skill: How to Use Web and HTML Editor Controls in Tedros

## 1. Objective

This skill explains how to embed **web browser views** and **rich-text HTML editors** inside a Tedros ModelView. Both controls are configured through annotations and require only a `SimpleStringProperty` field — no additional property types are needed.

---

## 2. Overview

| Annotation      | Purpose                                                                               |
|:----------------|:--------------------------------------------------------------------------------------|
| `@TWebView`     | Embeds a JavaFX `WebView` browser panel that loads a URL or inline HTML content       |
| `@THTMLEditor`  | Embeds a rich-text HTML editor where the user can type and format content             |

Both controls use `SimpleStringProperty` as the field type. The `@TWebView` field does **not** need to match an entity field name (it is a UI-only display component). The `@THTMLEditor` field **does** need to match an entity `String` field, because the HTML content typed by the user is saved back to the entity.

---

## 3. `@TWebView` — Embedded Web Browser

Renders a full JavaFX `WebView` panel inside the form. It can load:
- A **remote URL** (internet or intranet page)
- A **local HTML file** from the module folder
- **Inline HTML content** directly as a string

### 3.1 Load a Remote URL

```java
@TWebView(
    engine    = @TWebEngine(load = "https://www.example.com"),
    maxHeight = 500)
private SimpleStringProperty webContent;
```

### 3.2 Load a Local HTML File (from Module folder)

```java
@TWebView(
    engine = @TWebEngine(
        load = TWebEngine.MODULE_FOLDER + "/help/index.html"))
private SimpleStringProperty helpPage;
```

`TWebEngine.MODULE_FOLDER` resolves to the user's Tedros module directory at runtime:
`/user.home/.tedros/MODULE/`

### 3.3 Load Inline HTML Content

```java
@TWebView(
    engine = @TWebEngine(
        loadContent = "<html><body><h1>Hello World</h1></body></html>"))
private SimpleStringProperty inlineContent;
```

---

## 4. `@TWebView` Attribute Reference

### `@TWebView` attributes

| Attribute            | Default               | Description                                                                 |
|:--------------------|:----------------------|:----------------------------------------------------------------------------|
| `engine`             | *(required)*          | `@TWebEngine` nested annotation configuring what the `WebView` loads        |
| `maxHeight`          | `-1` (unrestricted)   | Maximum height of the browser panel in pixels                               |
| `maxWidth`           | `-1` (unrestricted)   | Maximum width                                                               |
| `minHeight`          | `-1`                  | Minimum height                                                              |
| `minWidth`           | `-1`                  | Minimum width                                                               |
| `prefHeight`         | `-1`                  | Preferred height                                                            |
| `prefWidth`          | `-1`                  | Preferred width                                                             |
| `zoom`               | `-1` (default zoom)   | Zoom factor applied to the entire page                                      |
| `fontScale`          | `1.0`                 | Scale factor for text content (does not affect images or fixed-size elements) |
| `fontSmoothingType`  | `FontSmoothingType.LCD` | Font rendering mode: `LCD` or `GRAY`                                    |
| `contextMenuEnabled` | `true`                | Enable or disable the right-click context menu                              |

### `@TWebEngine` attributes (nested inside `engine`)

| Attribute                | Default             | Description                                                                               |
|:------------------------|:--------------------|:------------------------------------------------------------------------------------------|
| `load`                   | `""`                | URL to load. Use `TWebEngine.MODULE_FOLDER + "/path/to/file.html"` for local files        |
| `loadContent`            | `""`                | Raw HTML string to render directly (no URL needed)                                        |
| `contentType`            | `""`                | Content-type hint for `loadContent` (e.g., `"text/html"`)                                |
| `javaScriptEnabled`      | `true`              | Enable or disable JavaScript execution in the page                                        |
| `userStyleSheetLocation` | `""`                | URL of a custom CSS stylesheet (`data:`, `file:`, or `jar:` protocols only)               |
| `userAgent`              | *(system default)*  | Override the HTTP `User-Agent` header string                                              |
| `userDataDirectory`      | *(auto)*            | `ITGenericBuilder<File>` class providing the directory for `localStorage` data            |
| `onAlert`                | —                   | `ITEventHandlerBuilder` for the JavaScript `alert()` handler                              |
| `onStatusChanged`        | —                   | `ITEventHandlerBuilder` for the JavaScript status-bar change event                        |
| `onResized`              | —                   | `ITEventHandlerBuilder` for JavaScript window resize events                               |
| `onVisibilityChanged`    | —                   | `ITEventHandlerBuilder` for JavaScript window visibility change events                    |
| `createPopupHandler`     | —                   | `ITGenericBuilder<Callback<PopupFeatures, WebEngine>>` for popup window handling          |
| `confirmHandler`         | —                   | `ITGenericBuilder<Callback<String, Boolean>>` for JavaScript `confirm()` dialog           |
| `promptHandler`          | —                   | `ITGenericBuilder<Callback<PromptData, String>>` for JavaScript `prompt()` dialog         |
| `onError`                | —                   | `ITEventHandlerBuilder<WebErrorEvent>` for catching web engine errors                     |

> **`TWebEngine.MODULE_FOLDER`** is the constant `"file:MODULE_FOLDER"`. The framework replaces it at runtime with the actual path to the user's Tedros module directory.

---

## 5. `@THTMLEditor` — Rich-Text HTML Editor

Renders a JavaFX `HTMLEditor` control with a formatting toolbar. The user types rich text, and the editor stores it as an HTML string. The HTML string is saved to the entity's corresponding `String` field through the standard `TModelView` binding.

```java
@THTMLEditor(
    showActionsToolBar = true,
    control = @TControl(parse = true, maxHeight = 500, minHeight = 500))
private SimpleStringProperty content;
```

**Attributes:**

| Attribute           | Default  | Description                                                                              |
|:--------------------|:---------|:-----------------------------------------------------------------------------------------|
| `showActionsToolBar`| `false`  | Show additional action buttons (Print and PDF export) above the editor toolbar           |
| `required`          | `false`  | Marks the field as mandatory — the form will not save if the editor is empty             |
| `html`              | `""`     | Initial HTML content pre-loaded into the editor when the form opens                     |
| `control`           | —        | `@TControl` for sizing (`maxHeight`, `minHeight`, `prefWidth`, etc.). Use `parse = true` |

### Sizing the HTML Editor

The HTML editor does not auto-size inside a scroll pane. Always set **both** `maxHeight` and `minHeight` to the same value to fix the editor height:

```java
@THTMLEditor(control = @TControl(parse = true, maxHeight = 450, minHeight = 450))
private SimpleStringProperty description;
```

### Entity Binding

The `content` field in the ModelView binds to the `content` field of the entity/model class. The HTML string produced by the editor is automatically propagated to `entity.setContent(html)` through the `TModelView` change listener.

```java
// Entity/Model
public class WebModel implements ITModel {
    private String content;
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}

// ModelView field — name MUST match the entity field name
@THTMLEditor(showActionsToolBar = true,
    control = @TControl(parse = true, maxHeight = 500, minHeight = 500))
private SimpleStringProperty content;
```

---

## 6. Using `@TTabPane` with Web and HTML Controls

`@TWebView` and `@THTMLEditor` can be placed in different tabs. The `@TTabPane` annotation goes on the **same field that also carries `@TWebView`**, provided that field is listed first in the first tab. The anchor field must be declared **above** all other tab-member fields in the class body.

```java
// ── Tab anchor: web field is first in its tab AND carries @TTabPane ───────
@TWebView(
    engine    = @TWebEngine(load = "https://docs.oracle.com/..."),
    maxHeight = 500)
@TTabPane(tabs = {
    @TTab(fields = {"web"},     text = "Web View"),
    @TTab(fields = {"content"}, text = "Html Editor")
})
private SimpleStringProperty web;

// ── Tab child, declared BELOW the anchor ──────────────────────────────────
@THTMLEditor(
    showActionsToolBar = true,
    control = @TControl(parse = true, maxHeight = 500, minHeight = 500))
private SimpleStringProperty content;
```

> **Ordering rule:** The engine processes fields bottom-to-top. The `@TTabPane` field (`web`) is processed after `content`, so by the time the tab container is assembled, the `content` component is already built and can be referenced by name.

---

## 7. Complete Example — `WebControlMV`

Reference source: `app-samples-fx/.../module/control/model/WebControlMV.java`

```java
@TPresenter(
    model     = WebModel.class,
    decorator = @TDecorator(type = TViewDecorator.class, viewTitle = "Web components"),
    behavior  = @TBehavior(type = TViewBehavior.class))
public class WebControlMV extends TModelView<WebModel> {

    private static final double HEIGHT = 500;

    // ── Section header (UI-only) ──────────────────────────────────────────
    @TText(textStyle = TTextStyle.LARGE, text = "Samples of web components")
    @TFieldBox(node = @TNode(parse = true, id = TFieldBox.TITLE))
    private SimpleStringProperty header;

    // ── Tab anchor: @TWebView + @TTabPane on the SAME field ──────────────
    @TWebView(
        engine    = @TWebEngine(load = "https://docs.oracle.com/javase/8/javafx/get-started-tutorial/jfx-overview.htm"),
        maxHeight = HEIGHT)
    @TTabPane(tabs = {
        @TTab(fields = {"web"},     text = "Web View"),
        @TTab(fields = {"content"}, text = "Html Editor")
    })
    private SimpleStringProperty web;

    // ── Html Editor (tab child, declared below the anchor) ────────────────
    @THTMLEditor(
        showActionsToolBar = true,
        control = @TControl(parse = true, maxHeight = HEIGHT, minHeight = HEIGHT))
    private SimpleStringProperty content;

    public WebControlMV(WebModel model) {
        super(model);
    }

    // --- getters and setters ---
}
```

And the model:

```java
public class WebModel implements ITModel {

    private static final long serialVersionUID = 1L;

    private String content;   // bound to the @THTMLEditor field "content"

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}
```

> `web` has no matching entity field (`WebModel` has no `web` field). The `TModelView` binding engine simply skips it — `@TWebView` is a pure display/UI control that does not need entity backing.

---

## 8. Quick Reference

| Annotation     | Field Type             | Entity Field Type | Persisted? | Key Attributes                                    |
|:---------------|:-----------------------|:------------------|:-----------|:--------------------------------------------------|
| `@TWebView`    | `SimpleStringProperty` | *(none needed)*   | No         | `engine` (`load` or `loadContent`), `maxHeight`   |
| `@THTMLEditor` | `SimpleStringProperty` | `String`          | Yes        | `showActionsToolBar`, `required`, `control` (sizing)|

---

## 9. Checklist for Web and HTML Controls

- [ ] `@TWebView` field type is `SimpleStringProperty`. The field does NOT need to match any entity field.
- [ ] `@THTMLEditor` field type is `SimpleStringProperty`. The field name **must** match the entity's `String` field name exactly.
- [ ] `engine` is mandatory on `@TWebView`. Always set either `load` or `loadContent` inside `@TWebEngine`.
- [ ] For local HTML files, use `TWebEngine.MODULE_FOLDER + "/relative/path/to/file.html"` as the `load` value.
- [ ] `@THTMLEditor` height is fixed by setting **both** `maxHeight` and `minHeight` to the same value in `@TControl(parse = true, ...)`.
- [ ] When `@TWebView` and `@THTMLEditor` are placed in tabs together, the `@TTabPane` annotation goes on the **`@TWebView` field**, declared **above** the `@THTMLEditor` field in the class body.
- [ ] All fields have **getters and setters**, even if the field has no entity binding (like the `web` field).

---

## 10. Rules and Constraints

- **`@TWebView` is UI-only:** It has no entity binding. The `web` property field will not cause an error if no matching entity field exists — the `TModelView` engine skips it silently.
- **`@THTMLEditor` saves HTML:** The editor stores the full HTML markup (including tags like `<html>`, `<body>`, `<p>`, etc.) in the entity field. Ensure the corresponding database column is large enough (e.g., `@Lob` or `TEXT` type in JPA).
- **Sizing `@THTMLEditor`:** Always provide `@TControl(parse = true, maxHeight = N, minHeight = N)`. Without explicit sizing, the editor may collapse or expand unpredictably inside the form layout.
- **`@TTabPane` ordering:** Because the form engine processes fields bottom-to-top, the `@TTabPane` field must appear **first (above)** all fields it references. When `@TWebView` and `@TTabPane` share the same field, that field must be declared above the `@THTMLEditor` field.
- **JavaScript:** JavaScript is enabled by default in `@TWebView`. Disable it with `@TWebEngine(javaScriptEnabled = false)` if loading untrusted content.
- **`showActionsToolBar = true`** adds **Print** and **Export to PDF** buttons to the HTML editor toolbar. Use only when those features are needed by the end user.
