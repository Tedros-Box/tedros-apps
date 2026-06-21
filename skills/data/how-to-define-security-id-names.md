# Skill: Tedros Security ID Definition Guide

## 1. Objective

Provide a technical reference for defining and using **Security IDs** (`securityId`) consistently across the Tedros ecosystem. Security IDs are the single shared key that connects access control on the **frontend** (JavaFX — which screens and features a user can see) and the **backend** (EJB Controller — which remote methods a user can call). A user who does not hold the required authorization for a given Security ID is blocked at both layers simultaneously.

## 2. The Security Model: How It Works

```text
TUser
  └── has many TProfile
            └── has many TAuthorization
                      ├── securityId  ← must match DomainApp constant
                      ├── type        ← the access type granted (APP_ACCESS, VIEW_ACCESS, EDIT, etc.)
                      ├── appName
                      ├── moduleName
                      └── viewName
```

When a user attempts to:
- **Open a screen** → the framework reads `@TSecurity(id=...)` on the frontend class and checks if the user's active `TProfile` has a `TAuthorization` with a matching `securityId` and `type`.
- **Call a controller method** → the `@TSecurityInterceptor` reads `@TBeanSecurity(id=...)` on the backend controller and performs the same check via `ITSecurityController`.

**The `securityId` value must be identical in both places.** All values are centralized as constants in `DomainApp`.

## 3. The `DomainApp` Interface

Every application **must** contain a `DomainApp` interface in the `-model` module. It is the single source of truth for all Security IDs.

- **Module:** `[APP_NAME]-model`
- **Package:** `org.tedros.[app_name].domain`
- **Class:** `DomainApp` (interface)

### 3.1 Naming Convention

Security IDs are composed by concatenating segments with `_` (underscore) as the separator:

```text
<MNEMONIC> _ <FEATURE_NAME> _ <LEVEL>
```

| Segment | Description | Examples |
| :--- | :--- | :--- |
| `MNEMONIC` | Short uppercase app identifier. **Must be unique across all apps.** | `ISPT`, `FIN`, `HR` |
| `FEATURE_NAME` | Name of the feature or functional area, uppercase with `_` | `EVIDENCE_MANAGER`, `CHANGE_MANAGER_GMUD_EDIT` |
| `LEVEL` | Scope of access: `MODULE`, `VIEW`, or `FORM` | `MODULE`, `VIEW`, `FORM` |

### 3.2 Level Definitions

| Level suffix | Where it is used | Access type granted |
| :--- | :--- | :--- |
| _(none — mnemonic only)_ | `AppStart` — the application entry point | `APP_ACCESS` |
| `_MODULE` | Module class (`extends TModule`) | `MODULE_ACCESS` |
| `_VIEW` | View-level security (optional additional layer) | `VIEW_ACCESS` |
| `_FORM` | Form/ModelView class and backend Controller | `VIEW_ACCESS`, `EDIT`, `SAVE`, `DELETE`, `NEW` |

### 3.3 `DomainApp` Template

```java
package org.tedros.myapp.domain;

/**
 * Centralised Security ID definitions for MyApp.
 * All IDs must be registered here and referenced by constant — never hardcoded.
 *
 * @author Author Name
 */
public interface DomainApp {

    // ─── Separators ──────────────────────────────────────────────────────────
    String SEPARATOR = "_";
    String SEP       = SEPARATOR;
    String VIEW      = "VIEW";
    String MODULE    = "MODULE";
    String FORM      = "FORM";

    // ─── Application Mnemonic ─────────────────────────────────────────────────
    // IMPORTANT: this must be unique across all Tedros applications.
    String MNEMONIC = "MYAP";  // e.g. "MYAP" for MyApp

    // ─── Feature names (raw segments — do not use these directly as IDs) ─────
    String SOME_FEATURE   = "SOME_FEATURE";
    String ANOTHER_FEATURE = "ANOTHER_FEATURE";

    // ─── Composed Security IDs ────────────────────────────────────────────────
    // App-level (used in AppStart)
    // ID value: "MYAP"

    // Module-level (used in Module classes)
    String SOME_FEATURE_MODULE_ID   = MNEMONIC + SEP + SOME_FEATURE + SEP + MODULE;
    // ID value: "MYAP_SOME_FEATURE_MODULE"

    // View-level (optional intermediate layer)
    String SOME_FEATURE_VIEW_ID     = MNEMONIC + SEP + SOME_FEATURE + SEP + VIEW;
    // ID value: "MYAP_SOME_FEATURE_VIEW"

    // Form-level (used in ModelView classes and EJB Controllers)
    String SOME_FEATURE_FORM_ID     = MNEMONIC + SEP + SOME_FEATURE + SEP + FORM;
    // ID value: "MYAP_SOME_FEATURE_FORM"

    String ANOTHER_FEATURE_MODULE_ID = MNEMONIC + SEP + ANOTHER_FEATURE + SEP + MODULE;
    String ANOTHER_FEATURE_FORM_ID   = MNEMONIC + SEP + ANOTHER_FEATURE + SEP + FORM;
}
```

## 4. Where Each Security ID Is Used

### 4.1 Application Level — `AppStart`

The application entry point uses the raw `MNEMONIC` as the Security ID. A user must have `APP_ACCESS` for this ID to even launch the application.

```java
@TSecurity(
    id          = DomainApp.MNEMONIC,      // e.g. "ISPT"
    appName     = ItToolsKey.APP_ITSUPPORT,
    allowedAccesses = TAuthorizationType.APP_ACCESS
)
public class AppStart implements ITApplication { ... }
```

### 4.2 Module Level — `TModule` subclass

The module class uses a `_MODULE` suffixed ID. A user must have `MODULE_ACCESS` to see and enter this module in the navigation menu.

```java
@TSecurity(
    id          = DomainApp.EVIDENCE_MANAGER_MODULE_ID,  // "ISPT_EVIDENCE_MANAGER_MODULE"
    appName     = ItToolsKey.APP_ITSUPPORT,
    moduleName  = ItToolsKey.MODULE_ITSUPPORT_EVIDENCE,
    allowedAccesses = TAuthorizationType.MODULE_ACCESS
)
public class JobEvidenceModule extends TModule { ... }
```

### 4.3 Form Level — ModelView class (Frontend)

The form/ModelView class uses a `_FORM` suffixed ID. This controls which CRUD actions (view, edit, save, delete, new) the user can perform on the form.

```java
@TSecurity(
    id          = DomainApp.EVIDENCE_MANAGER_FORM_ID,  // "ISPT_EVIDENCE_MANAGER_FORM"
    appName     = ItToolsKey.APP_ITSUPPORT,
    moduleName  = ItToolsKey.MODULE_ITSUPPORT_EVIDENCE,
    viewName    = ItToolsKey.VIEW_JOB_EVIDENCE,
    allowedAccesses = {
        TAuthorizationType.VIEW_ACCESS,
        TAuthorizationType.EDIT,
        TAuthorizationType.SAVE,
        TAuthorizationType.DELETE,
        TAuthorizationType.NEW
    }
)
public class CreateJobEvidenceMV extends TEntityModelView<JobEvidence> { ... }
```

### 4.4 Form Level — EJB Controller (Backend)

The **same `_FORM` ID** used in the ModelView is referenced in the backend controller. This ensures the same authorization check is enforced on both sides.

```java
@TSecurityInterceptor
@Stateless(name = "IJobEvidenceController")
@TBeanSecurity({
    @TBeanPolicie(
        id      = DomainApp.EVIDENCE_MANAGER_FORM_ID,  // same ID as the ModelView
        policie = {TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS}
    )
})
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class JobEvidenceController extends TSecureEjbController<JobEvidence>
        implements IJobEvidenceController, ITSecurity { ... }
```

## 5. Complete ID-to-Usage Map (Real Example: IT Support Tools)

```text
DomainApp.MNEMONIC                        = "ISPT"
DomainApp.EVIDENCE_MANAGER_MODULE_ID      = "ISPT_EVIDENCE_MANAGER_MODULE"
DomainApp.EVIDENCE_MANAGER_VIEW_ID        = "ISPT_EVIDENCE_MANAGER_VIEW"    (optional)
DomainApp.EVIDENCE_MANAGER_FORM_ID        = "ISPT_EVIDENCE_MANAGER_FORM"
```

| Constant | Value | Used in | Access type |
| :--- | :--- | :--- | :--- |
| `MNEMONIC` | `"ISPT"` | `AppStart` (`@TSecurity`) | `APP_ACCESS` |
| `EVIDENCE_MANAGER_MODULE_ID` | `"ISPT_EVIDENCE_MANAGER_MODULE"` | `JobEvidenceModule` (`@TSecurity`) | `MODULE_ACCESS` |
| `EVIDENCE_MANAGER_FORM_ID` | `"ISPT_EVIDENCE_MANAGER_FORM"` | `CreateJobEvidenceMV` (`@TSecurity`) | `VIEW_ACCESS`, `EDIT`, `SAVE`, `DELETE`, `NEW` |
| `EVIDENCE_MANAGER_FORM_ID` | `"ISPT_EVIDENCE_MANAGER_FORM"` | `JobEvidenceController` (`@TBeanSecurity`) | `APP_ACCESS`, `VIEW_ACCESS` |

## 6. The Authorization Data Model

Access is granted at runtime by checking the user's `TAuthorization` records:

```text
TUser.activeProfile
    └── TProfile.autorizations  (List<TAuthorization>)
              └── TAuthorization
                    ├── securityId      ← must equal the DomainApp constant value
                    ├── type            ← e.g. "VIEW_ACCESS", "EDIT", "SAVE", "DELETE", "NEW"
                    ├── appName         ← application name key
                    ├── moduleName      ← module name key (nullable)
                    ├── viewName        ← view name key (nullable)
                    └── enabled         ← "YES" or "NO"
```

`TAuthorization` records are created and managed through the Tedros administration screen (user/profile management). The `securityId` stored in the database is the **runtime string value** of the `DomainApp` constant.

## 7. Access Hierarchy

Access checks are cumulative from top to bottom. A user must pass **all levels** to reach a form:

```text
1. APP_ACCESS      (MNEMONIC)               → Can the user launch the app?
2. MODULE_ACCESS   (*_MODULE_ID)            → Can the user open the module?
3. VIEW_ACCESS     (*_FORM_ID or *_VIEW_ID) → Can the user see the screen/form?
4. EDIT / SAVE / DELETE / NEW (*_FORM_ID)   → What actions can the user perform?
```

Failing at any level blocks the user from proceeding further.

## 8. Development Constraints

- **Single source of truth:** Every Security ID must be defined as a constant in `DomainApp` — **never hardcode the string value directly** in an annotation.
- **Unique MNEMONIC:** The `MNEMONIC` must be unique across all Tedros applications to prevent authorization cross-contamination.
- **Naming format:** Always follow `<MNEMONIC>_<FEATURE>_<LEVEL>` — never invent other formats.
- **Backend/frontend parity:** Every `_FORM` ID used in a `@TSecurity` annotation on a ModelView **must** have an exact match in the `@TBeanSecurity` annotation on its corresponding Controller. A mismatch silently breaks the security chain.
- **Model module only:** `DomainApp` lives in the `-model` module and must be the only such file in the project. Both the `-ejb` and `-fx` modules depend on `-model`, so they can all reference the same constants.
- **Granularity decision rule:** Create a new constant pair (`_MODULE_ID` + `_FORM_ID`) for each distinct functional feature that requires independent access control. Do not reuse an existing form ID for a different feature.
