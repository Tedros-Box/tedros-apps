# Skill: How to Use File Controls in Tedros

## 1. Objective

This skill explains how to declare and configure **file and image controls** inside a Tedros ModelView. It covers directory selection, single-file selection (with optional image preview and server persistence), and multi-image selection galleries — all configured entirely through annotations.

---

## 2. Overview of File Controls

| Annotation            | Purpose                                                                           |
|:----------------------|:----------------------------------------------------------------------------------|
| `@TDirectoryField`    | Lets the user browse and pick a **local directory**                               |
| `@TFileField`         | Lets the user browse and pick a **single file**, optionally storing it as a model/entity |
| `@TSelectImageField`  | Image gallery that lets the user select and manage **multiple images** (local or remote) |

---

## 3. File-Specific Property Types

File controls use specialized property types that carry both the file reference and the file metadata (name, extension, bytes, size, path).

| Property Type                        | Use when                                                              |
|:-------------------------------------|:----------------------------------------------------------------------|
| `SimpleObjectProperty<File>`         | Holding a reference to a local `java.io.File` (used with `@TDirectoryField`) |
| `TSimpleFileProperty<TFileModel>`    | Holding a single file + its metadata for non-persisted storage (in-memory model) |
| `TSimpleFileProperty<TFileEntity>`   | Holding a single file + its metadata for JPA-persisted storage (entity) |
| `ITObservableList<ITFileBaseModel>`  | Holding multiple file/image selections (used with `@TSelectImageField`) |

### `TSimpleFileProperty<T>` Internal Properties

`TSimpleFileProperty<T extends ITFileBaseModel>` extends `SimpleObjectProperty<T>` and automatically maintains synchronized sub-properties that can be read programmatically:

| Sub-property accessor           | Type                        | Description                              |
|:--------------------------------|:----------------------------|:-----------------------------------------|
| `tFileProperty()`               | `SimpleObjectProperty<File>`| The selected `java.io.File` object       |
| `tFileNameProperty()`           | `SimpleStringProperty`      | File name (e.g., `photo.jpg`)            |
| `tFileExtensionProperty()`      | `ReadOnlyStringProperty`    | Extension only (e.g., `jpg`)             |
| `tFilePathProperty()`           | `SimpleStringProperty`      | Full directory path                      |
| `tFileSizeProperty()`           | `LongProperty`              | File size in bytes                       |
| `tBytesProperty()`              | `SimpleObjectProperty<byte[]>` | Raw byte content of the file          |

When a file is selected by the user, all these sub-properties are automatically updated. When changes are made to `tFileNameProperty` or `tBytesProperty`, the backing model object (`TFileModel` / `TFileEntity`) is also updated automatically.

---

## 4. `@TDirectoryField` — Directory Picker

Renders a text field with a **Browse** button that opens the system directory chooser. The selected directory path is stored as a `java.io.File`.

```java
@TLabel(text = "Select folder")
@TDirectoryField
private SimpleObjectProperty<File> dirField;
```

**All attributes (with defaults):**

| Attribute        | Default        | Description                                                  |
|:----------------|:---------------|:-------------------------------------------------------------|
| `required`       | `false`        | Marks the field as mandatory                                 |
| `showFilePath`   | `true`         | Show the chosen path in the text field next to the button    |
| `cleanAction`    | —              | Optional `TEventHandler` triggered when the clean button is clicked |
| `selectAction`   | —              | Optional `TEventHandler` triggered when the select button is clicked |

- Property type must be `SimpleObjectProperty<File>` (`java.io.File`).
- The entity field must be of type `File`.
- Field name must exactly match the entity/model field name.

---

## 5. `@TFileField` — Single File Picker

Renders a text field with **Browse**, **Clean**, and optionally an **image preview** area. The selected file is stored as a `TSimpleFileProperty`.

### 5.1 Minimal — In-Memory Model (non-persisted)

Use `TFileModelType.MODEL` when the file data is kept in a transient model object (`TFileModel`) and does not need to be persisted to the database as an entity.

```java
@TLabel(text = "Select file")
@TFileField(propertyValueType = TFileModelType.MODEL)
private TSimpleFileProperty<TFileModel> fileField;
```

### 5.2 Full — JPA Entity with Image Preview

Use `TFileModelType.ENTITY` when the file is stored in a JPA entity (`TFileEntity`) mapped with `@OneToOne(cascade = CascadeType.ALL)` on the parent entity.

```java
@TFileReader
@TLabel(text = "Product Image")
@TFieldBox(node = @TNode(id = "image", parse = true))
@TFileField(
    showImage         = true,
    propertyValueType = TFileModelType.ENTITY,
    preLoadFileBytes  = true,
    extensions        = {TFileExtension.JPG, TFileExtension.PNG},
    showFilePath      = true)
private TSimpleFileProperty<TFileEntity> image;
```

**All attributes (with defaults):**

| Attribute             | Default            | Description                                                                     |
|:---------------------|:-------------------|:--------------------------------------------------------------------------------|
| `propertyValueType`   | `TFileModelType.NONE` | Declares the backing model type: `MODEL` (transient) or `ENTITY` (JPA)      |
| `required`            | `false`            | Marks the field as mandatory                                                    |
| `showFilePath`        | `true`             | Display the file path beside the button                                         |
| `showImage`           | `false`            | Render an image preview panel below the file picker                             |
| `preLoadFileBytes`    | `false`            | Load the file bytes immediately when the form opens (vs. lazily)                |
| `extensions`          | `ALL_FILES`        | Array of `TFileExtension` values to restrict the file chooser dialog            |
| `moreExtensions`      | `{}`               | Additional raw extension strings (e.g., `".docx"`) beyond `TFileExtension`     |
| `minFileSize`         | `-1` (no limit)    | Minimum allowed file size in bytes                                              |
| `maxFileSize`         | `-1` (no limit)    | Maximum allowed file size in bytes                                              |
| `minImageWidth`       | `-1` (no limit)    | Minimum image width in pixels (only validated when `showImage = true`)          |
| `maxImageWidth`       | `-1` (no limit)    | Maximum image width in pixels                                                   |
| `minImageHeight`      | `-1` (no limit)    | Minimum image height in pixels                                                  |
| `maxImageHeight`      | `-1` (no limit)    | Maximum image height in pixels                                                  |
| `fileNameField`       | `""`               | Name of a `SimpleStringProperty` field in the ModelView to sync the file name  |
| `initialDirectory`    | `"user.home"`      | Starting directory for the file chooser. Constants: `TFileField.USER_HOME`, `TFileField.TEDROS_ROOT`, `TFileField.TEDROS_MODULE` |
| `openAction`          | —                  | Optional `TEventHandler` for the open button click                              |
| `cleanAction`         | —                  | Optional `TEventHandler` for the clean button click                             |
| `selectAction`        | —                  | Optional `TEventHandler` for the select button click                            |
| `imageAction`         | —                  | Optional `TEventHandler` for clicking the image preview                         |

### `TFileModelType` values

| Value              | Use case                                                                            |
|:-------------------|:------------------------------------------------------------------------------------|
| `TFileModelType.NONE`   | No explicit model type. Suitable when the file is not persisted.               |
| `TFileModelType.MODEL`  | File is held in a `TFileModel` (transient, non-JPA).                           |
| `TFileModelType.ENTITY` | File is held in a `TFileEntity` (JPA-persisted with its own `@Entity` mapping).|

### `TFileExtension` values (common)

`TFileExtension.ALL_FILES`, `TFileExtension.JPG`, `TFileExtension.PNG`, `TFileExtension.GIF`, `TFileExtension.PDF`, `TFileExtension.TXT`, `TFileExtension.CSV`, `TFileExtension.XLS`, `TFileExtension.XLSX`, `TFileExtension.DOC`, `TFileExtension.DOCX`, and others.

---

## 6. `@TSelectImageField` — Multi-Image Gallery

Renders a full image gallery panel where the user can **browse, select, and manage multiple images**. Supports local and remote image sources.

### 6.1 Local → Local (select from disk, store locally)

```java
@TSelectImageField(source = TEnvironment.LOCAL, target = TEnvironment.LOCAL)
private ITObservableList<ITFileBaseModel> selectImgField;
```

### 6.2 Local → Remote (select from disk, upload to remote repository)

```java
@TSelectImageField(
    source      = TEnvironment.LOCAL,
    target      = TEnvironment.REMOTE,
    remoteOwner = {"myApp"})
private ITObservableList<ITFileBaseModel> images;
```

### 6.3 Remote → Remote (browse remote repository, select to associate)

```java
@TSelectImageField(
    source      = TEnvironment.REMOTE,
    target      = TEnvironment.REMOTE,
    remoteOwner = {"myApp"})
private SimpleObjectProperty<ITFileBaseModel> selectedRemoteImage;
```

> **Note:** When using `source = TEnvironment.REMOTE`, the field can be typed as `SimpleObjectProperty<ITFileBaseModel>` for single-image selection, or as `ITObservableList<ITFileBaseModel>` for multi-image.

**All attributes (with defaults):**

| Attribute          | Default                  | Description                                                                           |
|:------------------|:-------------------------|:--------------------------------------------------------------------------------------|
| `source`           | `TEnvironment.LOCAL`     | Where the images are loaded from: `LOCAL` (disk) or `REMOTE` (server repository)     |
| `target`           | `TEnvironment.LOCAL`     | Where the selected images are stored: `LOCAL` or `REMOTE`                             |
| `remoteOwner`      | `{""}`                   | Identifier(s) for the remote file namespace (required when `source` or `target` is `REMOTE`) |
| `localFolder`      | `""`                     | Default local folder path to open when browsing                                       |
| `extension`        | `TImageExtension.ALL_IMAGES` | Image type filter: `ALL_IMAGES`, `JPG`, `PNG`, `GIF`, etc.                       |
| `height`           | `450`                    | Height of the gallery panel in the form                                               |
| `maxFileSize`      | `1500000` (1.5 MB)       | Maximum allowed image file size in bytes                                              |
| `fitWidth`         | `-1` (auto)              | Thumbnail fit width in pixels                                                         |
| `fitHeight`        | `250`                    | Thumbnail fit height in pixels                                                        |
| `scroll`           | `true`                   | Wrap the gallery in a scroll pane                                                     |
| `preLoadFileBytes` | `true`                   | Load all image bytes on open. Set to `false` to load only on mouse hover (lazy)       |
| `required`         | `false`                  | Marks the field as mandatory                                                          |
| `imageViewEvents`  | —                        | Array of `TEventHandler` subclasses for image click events                            |

### `TEnvironment` values

| Value              | Description                                          |
|:-------------------|:-----------------------------------------------------|
| `TEnvironment.LOCAL`  | File system on the user's local machine           |
| `TEnvironment.REMOTE` | Server-side file repository shared across apps   |

---

## 7. Using `@TTabPane` with File Controls

File controls can be organized into tabs like any other control. The `@TTabPane` anchor field must be declared **above** all fields listed in its tabs (because the engine processes fields bottom-to-top).

```java
// ── Tab layout anchor — declared FIRST (above all tab children) ───────────
@TLabel(text = "Select folder")
@TDirectoryField
@TTabPane(tabs = {
    @TTab(fields = {"dirField", "fileField"}, text = "Choose Folder/File"),
    @TTab(fields = {"selectImgField"},        text = "Select Images")
})
private SimpleObjectProperty<File> dirField;

// ── Tab children — declared BELOW the anchor ─────────────────────────────
@TLabel(text = "Select file")
@TFileField(propertyValueType = TFileModelType.MODEL)
private TSimpleFileProperty<TFileModel> fileField;

@TSelectImageField(source = TEnvironment.LOCAL, target = TEnvironment.LOCAL)
private ITObservableList<ITFileBaseModel> selectImgField;
```

> In this example, `dirField` carries the `@TTabPane` annotation and is the first field of the first tab. Its `@TDirectoryField` annotation makes the first tab immediately useful — the directory picker is the first visible control.

---

## 8. Entity Mapping for `@TFileField` with `TFileModelType.ENTITY`

When using `TFileModelType.ENTITY`, the parent JPA entity must declare the file relationship with full cascade:

```java
@Entity
@Table(name = "product")
public class Product extends TEntity {

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private TFileEntity image;

    // getter and setter...
}
```

And the corresponding ModelView field:

```java
@TFileReader
@TLabel(text = "Product Image")
@TFieldBox(node = @TNode(id = "image", parse = true))
@TFileField(
    showImage         = true,
    propertyValueType = TFileModelType.ENTITY,
    preLoadFileBytes  = true,
    extensions        = {TFileExtension.JPG, TFileExtension.PNG},
    showFilePath      = true)
private TSimpleFileProperty<TFileEntity> image;
```

The `TSimpleFileProperty` binding engine automatically reads the `TFileEntity` from the entity's getter on form load, and writes changes back via the setter when the user selects a new file.

> **`@TFileReader`** — place this annotation on the field in addition to `@TFileField` when you also want the field to be rendered in read-only (reader) mode. Without it, the file control is hidden in reader mode.

---

## 9. Complete Example — `FileControlMV`

Reference source: `app-samples-fx/.../module/control/model/FileControlMV.java`

```java
@TPresenter(
    model     = FieldModel.class,
    decorator = @TDecorator(type = TViewDecorator.class, viewTitle = "File components"),
    behavior  = @TBehavior(type = TViewBehavior.class))
public class FileControlMV extends TModelView<FieldModel> {

    // ── Section header ────────────────────────────────────────────────────
    @TText(textStyle = TTextStyle.LARGE, text = "Samples of file components")
    @TFieldBox(node = @TNode(parse = true, id = TFieldBox.TITLE))
    private SimpleStringProperty header;

    // ── Tab layout anchor: dirField is FIRST and carries @TTabPane ────────
    @TLabel(text = "Select folder")
    @TDirectoryField
    @TTabPane(tabs = {
        @TTab(fields = {"dirField", "fileField"}, text = "Choose Folder/File"),
        @TTab(fields = {"selectImgField"},        text = "Select Images")
    })
    private SimpleObjectProperty<File> dirField;

    // ── Tab children ──────────────────────────────────────────────────────
    @TLabel(text = "Select file")
    @TFileField(propertyValueType = TFileModelType.MODEL)
    private TSimpleFileProperty<TFileModel> fileField;

    @TSelectImageField(source = TEnvironment.LOCAL, target = TEnvironment.LOCAL)
    private ITObservableList<ITFileBaseModel> selectImgField;

    public FileControlMV(FieldModel model) {
        super(model);
    }

    // --- getters and setters for every field ---
}
```

---

## 10. Quick Reference — File Controls

| Annotation             | Property Type                          | Entity Type         | Key Attributes                                         |
|:-----------------------|:---------------------------------------|:--------------------|:-------------------------------------------------------|
| `@TDirectoryField`     | `SimpleObjectProperty<File>`           | `File`              | `required`, `showFilePath`, `cleanAction`, `selectAction` |
| `@TFileField`          | `TSimpleFileProperty<TFileModel>`      | —                   | `propertyValueType=MODEL`, `extensions`, `showFilePath` |
| `@TFileField`          | `TSimpleFileProperty<TFileEntity>`     | `TFileEntity` (JPA) | `propertyValueType=ENTITY`, `showImage`, `preLoadFileBytes` |
| `@TSelectImageField`   | `ITObservableList<ITFileBaseModel>`    | —                   | `source`, `target`, `remoteOwner`, `height`, `extension` |
| `@TSelectImageField`   | `SimpleObjectProperty<ITFileBaseModel>`| —                   | Use for single-image remote selection                   |

---

## 11. Checklist for File Controls

- [ ] `@TDirectoryField` → field type is `SimpleObjectProperty<File>` and entity field is `File`.
- [ ] `@TFileField` → field type is `TSimpleFileProperty<TFileModel>` (transient) or `TSimpleFileProperty<TFileEntity>` (persisted).
- [ ] `propertyValueType` is explicitly set on `@TFileField`: `MODEL` for transient, `ENTITY` for JPA.
- [ ] When using `TFileModelType.ENTITY`, the parent JPA entity has a `@OneToOne(cascade = CascadeType.ALL)` mapping to `TFileEntity`.
- [ ] `@TSelectImageField` → field type is `ITObservableList<ITFileBaseModel>` (multi) or `SimpleObjectProperty<ITFileBaseModel>` (single remote).
- [ ] `remoteOwner` is set when `source` or `target` is `TEnvironment.REMOTE`.
- [ ] `@TTabPane` is placed on the **same field** that is the first entry in the first tab's `fields` list, AND declared **above** all other tab children in the class body.
- [ ] `@TFileReader` is added alongside `@TFileField` if the file control must also render in read-only mode.
- [ ] All fields have **getters and setters**.

---

## 12. Rules and Constraints

- **Field naming:** `TSimpleFileProperty` fields must have names that match the entity field names exactly, just like all other ModelView fields. The binding engine uses reflection to find the setter on the entity.
- **`TSimpleFileProperty` is not a plain `Property`:** Do not use `SimpleObjectProperty<TFileModel>` — you must use `TSimpleFileProperty<TFileModel>`. The framework registers this type specifically to handle file metadata propagation.
- **`TFileModelType.NONE` skips binding:** If `propertyValueType` is left as `NONE`, no model type is instantiated — use only when the file field is purely for local UI selection without any persistence.
- **`@TSelectImageField` does not require `@TGenericType`:** Unlike other collection fields, the image list uses `ITFileBaseModel` directly and the framework handles instantiation internally.
- **`@TTabPane` field ordering rule:** Because the engine processes fields bottom-to-top, the `@TTabPane` anchor must be **above** the fields it references in tabs. In `FileControlMV`, `dirField` is first (top) and `selectImgField` is last (bottom).
- **`showImage = true` requires compatible extensions:** If `showImage = true` is set but the user selects a non-image file, the preview area remains blank. Always also set `extensions` to image types when enabling `showImage`.
- **File size limits:** `minFileSize` and `maxFileSize` are in bytes. Set them to `-1` to disable the limit.
- **`preLoadFileBytes = false` on `@TSelectImageField`:** Suitable for large galleries where loading all bytes upfront would be slow. The bytes are loaded lazily on mouse hover.
