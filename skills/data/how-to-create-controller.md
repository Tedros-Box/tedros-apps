# Skill: Tedros Controller Layer Implementation Guide

## 1. Objective

Provide a technical reference for creating the **Controller layer** within the Tedros ecosystem using **`TSecureEjbController`**. The Controller is the **topmost server-side layer** — it is the entry point for all client calls. It enforces authentication and access policy, wraps every operation in a `TResult` response envelope, handles all exceptions uniformly, and delegates all business execution to the underlying EJB Service.

## 2. Architectural Placement

- **Controller class:** `[APP_NAME]-ejb` project → package `org.tedros.[app_name].ejb.controller`
- **Controller interface:** `[APP_NAME]-ejb-client` project → package `org.tedros.[app_name].ejb.controller`
- **Dependency:** Controllers must extend `TSecureEjbController<E>` from `org.tedros.server.ejb.controller`.
- **Layer position:** `Client` → **`Controller`** → `EJB Service` → `BO` → `EAO` → `EntityManager`

> **Important:** Only the **interface** is distributed to the client module (`-ejb-client`). The implementation class stays in the `-ejb` module and is **never** exposed directly.

## 3. Base Class: `TSecureEjbController<E>`

`TSecureEjbController<E extends ITEntity>` is the abstract base class that provides:

- **Security enforcement** via `@TSecurityInterceptor` and `@TMethodSecurity`
- **Uniform response wrapping** — every method returns `TResult<T>`
- **Centralised exception handling** via `processException()`
- **Pagination** — paginated methods return `TResult<Map<String, Object>>` with `"list"` and `"total"` keys
- **Post-processing hooks** — `processEntity()` and `processEntityList()` for subclass customisation

The single abstract method every controller **must** implement is:

```java
protected abstract ITEjbService<E> getService();
```

### 3.1 Built-in Methods (from `TSecureEjbController`)

All methods require a `TAccessToken token` as the first parameter. When this token is present, the `@TSecurityInterceptor` automatically intercepts the call and validates authentication and access policies **before** the method body executes.

| Method | TResult type | `@TMethodSecurity` policies |
| :--- | :--- | :--- |
| `save(token, entity)` | `TResult<E>` | `SAVE`, `NEW` |
| `remove(token, entity)` | `TResult<E>` | `DELETE` |
| `findById(token, entity)` | `TResult<E>` | `EDIT`, `READ` |
| `findById(token, userId, entity)` | `TResult<E>` | `EDIT`, `READ` |
| `find(token, entity)` | `TResult<E>` | `EDIT`, `READ`, `SEARCH` |
| `find(token, userId, entity)` | `TResult<E>` | `EDIT`, `READ`, `SEARCH` |
| `findAll(token, entity)` | `TResult<List<E>>` | `EDIT`, `READ`, `SEARCH` |
| `findAll(token, userId, entity)` | `TResult<List<E>>` | `EDIT`, `READ`, `SEARCH` |
| `findAll(token, entity, first, max, asc, keywords)` | `TResult<Map<String,Object>>` | `EDIT`, `READ`, `SEARCH` |
| `findAll(token, userId, entity, first, max, asc, keywords)` | `TResult<Map<String,Object>>` | `EDIT`, `READ`, `SEARCH` |
| `listAll(token, Class entity)` | `TResult<List<E>>` | `EDIT`, `READ`, `SEARCH` |
| `listAll(token, userId, Class entity)` | `TResult<List<E>>` | `EDIT`, `READ`, `SEARCH` |
| `pageAll(token, entity, first, max, asc)` | `TResult<Map<String,Object>>` | `EDIT`, `READ`, `SEARCH` |
| `pageAll(token, userId, entity, first, max, asc)` | `TResult<Map<String,Object>>` | `EDIT`, `READ`, `SEARCH` |
| `search(token, sel)` | `TResult<List<E>>` | `EDIT`, `READ`, `SEARCH` |
| `search(token, userId, sel)` | `TResult<List<E>>` | `EDIT`, `READ`, `SEARCH` |
| `search(token, sel, first, max)` | `TResult<Map<String,Object>>` | `EDIT`, `READ`, `SEARCH` |
| `search(token, userId, sel, first, max)` | `TResult<Map<String,Object>>` | `EDIT`, `READ`, `SEARCH` |

### 3.2 Overridable Hook Methods

Override these empty methods in a specific controller when post-processing of results is needed (e.g., data masking, enrichment):

| Method | When it is called |
| :--- | :--- |
| `processEntity(TAccessToken token, E entity)` | After every single-entity retrieval or save |
| `processEntityList(TAccessToken token, List<E> entities)` | After every list retrieval |

### 3.3 Protected Utility Methods

| Method | Description |
| :--- | :--- |
| `processException(token, entity, e)` | Converts exceptions into the appropriate `TResult` state. Handles `OptimisticLockException` → `OUTDATED`, `EJBTransactionRolledbackException` → `ERROR`, `TBusinessException` → `ERROR` or `WARNING`, integrity constraint → `ERROR`. |
| `isTheCause(e, ExceptionClass)` | Walks the exception cause chain to find a specific exception type. Useful inside overridden `save`/`remove` methods. |

## 4. The `TResult<T>` Response Envelope

All controller methods **must** return `TResult<T>`. This is the serializable contract between server and client.

| Constructor | Use case |
| :--- | :--- |
| `new TResult<>(TState.SUCCESS, value)` | Operation succeeded, returns data |
| `new TResult<>(TState.SUCCESS)` | Operation succeeded, no data to return |
| `new TResult<>(TState.WARNING, "message")` | Operation blocked by a business rule |
| `new TResult<>(TState.ERROR, "message")` | Unrecoverable error |
| `new TResult<>(TState.ERROR, true, "message")` | Error with `priorityMessage = true` (forces display to user) |
| `new TResult<>(TState.OUTDATED, "msg", entity)` | Optimistic lock conflict — returns the current DB state |

### `TState` values

| State | Value | Meaning |
| :--- | :--- | :--- |
| `SUCCESS` | 1 | Operation completed successfully |
| `WARNING` | 0 | Blocked by a business rule — no exception |
| `ERROR` | -1 | Operation failed |
| `OUTDATED` | -2 | Optimistic lock conflict |
| `NO_RESULT` | 2 | No result available |

## 5. The `TAccessToken` and Security Interceptor

`TAccessToken` is a serializable wrapper around a string session token. It is the first parameter of every controller method that requires authentication.

**What the interceptor does automatically** when a method receives a `TAccessToken`:

1. Checks if the token is authenticated via `ITSecurityController.isAccessGranted(token)`
2. Validates **bean-level** access policies from `@TBeanSecurity` / `@TBeanPolicie`
3. Validates **method-level** action policies from `@TMethodSecurity` / `@TMethodPolicie`
4. Throws `IllegalStateException` if any check fails (caught by the EJB container and returned as an error)

> **Rule:** Every method that must be protected must receive a `TAccessToken` as a parameter. The interceptor only activates when this parameter is detected.

## 6. Controller Annotations

Every controller implementation class **must** carry the following annotations:

| Annotation | Required | Description |
| :--- | :--- | :--- |
| `@TSecurityInterceptor` | **Yes** | Activates the security interceptor for all methods |
| `@Stateless(name="IXxxController")` | **Yes** | EJB bean name — must match the interface name (without the `I` prefix is the class, with `I` prefix is the JNDI name) |
| `@TBeanSecurity({...})` | **Yes** | Declares bean-level access policies via `@TBeanPolicie` (form ID + required access policies) |
| `@TransactionAttribute(NOT_SUPPORTED)` | **Yes** | Default for all methods; write operations inherit `REQUIRED` from the service |
| `implements IXxxController, ITSecurity` | **Yes** | Controller must implement its own interface and `ITSecurity` (required by the interceptor) |

## 7. The Controller Interface (client-side contract)

The interface is the **only artefact shared with the client**. It must be created in the `-ejb-client` Maven module.

### Interface Rules

| Rule | Description |
| :--- | :--- |
| **`@Remote`** | Must be annotated `@Remote` for EJB remote lookup |
| **`extends ITSecureEjbController<E>`** | Inherits all standard operation signatures |
| **`JNDI_NAME` constant** | **Mandatory.** Must be `static final String JNDI_NAME = "<InterfaceName>Remote"` — the interface name followed by the word `Remote` |
| **Custom methods** | Declare any additional methods exposed to the client |

### JNDI Name Convention

```text
Interface name:  IJobEvidenceController
JNDI_NAME:       "IJobEvidenceControllerRemote"

Interface name:  IGmudController
JNDI_NAME:       "IGmudControllerRemote"
```

## 8. Implementation Patterns

### 8.1 Pattern A — Simple Controller (no custom logic)

When the entity requires only standard CRUD operations, the controller has no body — it simply wires the service and security controller.

**Interface** (in `-ejb-client`):

```java
package org.tedros.myapp.ejb.controller;

import org.tedros.myapp.entity.SomeEntity;
import org.tedros.server.controller.ITSecureEjbController;
import jakarta.ejb.Remote;

@Remote
public interface ISomeEntityController extends ITSecureEjbController<SomeEntity> {

    static final String JNDI_NAME = "ISomeEntityControllerRemote";
}
```

**Implementation** (in `-ejb`):

```java
package org.tedros.myapp.ejb.controller;

import org.tedros.myapp.domain.DomainApp;
import org.tedros.myapp.ejb.service.MyAppService;
import org.tedros.myapp.entity.SomeEntity;
import org.tedros.server.ejb.controller.ITSecurityController;
import org.tedros.server.ejb.controller.TSecureEjbController;
import org.tedros.server.security.ITSecurity;
import org.tedros.server.security.TAccessPolicie;
import org.tedros.server.security.TBeanPolicie;
import org.tedros.server.security.TBeanSecurity;
import org.tedros.server.security.TSecurityInterceptor;
import org.tedros.server.service.ITEjbService;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;

/**
 * The controller bean for SomeEntity.
 *
 * @author Author Name
 */
@TSecurityInterceptor
@Stateless(name = "ISomeEntityController")
@TBeanSecurity({@TBeanPolicie(id = DomainApp.SOME_FORM_ID,
    policie = {TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS})})
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class SomeEntityController extends TSecureEjbController<SomeEntity>
        implements ISomeEntityController, ITSecurity {

    @EJB
    private MyAppService<SomeEntity> serv;

    @EJB
    private ITSecurityController securityController;

    @Override
    public ITEjbService<SomeEntity> getService() {
        return serv;
    }

    @Override
    public ITSecurityController getSecurityController() {
        return securityController;
    }
}
```

---

### 8.2 Pattern B — Specialized Controller (custom business methods)

When the entity requires custom operations (e.g., business-rule validation before save, custom search), override the relevant methods and/or add new ones declared in the interface.

**Interface** (in `-ejb-client`):

```java
package org.tedros.myapp.ejb.controller;

import java.util.List;
import org.tedros.myapp.entity.SomeEntity;
import org.tedros.server.controller.ITSecureEjbController;
import org.tedros.server.result.TResult;
import org.tedros.server.security.TAccessToken;
import jakarta.ejb.Remote;

@Remote
public interface ISomeEntityController extends ITSecureEjbController<SomeEntity> {

    static final String JNDI_NAME = "ISomeEntityControllerRemote";

    // Custom method exposed to the client
    TResult<List<SomeEntity>> search(TAccessToken token, String name, String code);
}
```

**Implementation** (in `-ejb`) — overriding `save` and adding a custom `search`:

```java
package org.tedros.myapp.ejb.controller;

import java.util.List;
import org.tedros.myapp.cdi.bo.SomeEntityBo;  // or the specific service
import org.tedros.myapp.domain.DomainApp;
import org.tedros.myapp.ejb.service.SomeEntityService;
import org.tedros.myapp.entity.SomeEntity;
import org.tedros.server.ejb.controller.ITSecurityController;
import org.tedros.server.ejb.controller.TSecureEjbController;
import org.tedros.server.result.TResult;
import org.tedros.server.security.ITSecurity;
import org.tedros.server.security.TAccessPolicie;
import org.tedros.server.security.TAccessToken;
import org.tedros.server.security.TBeanPolicie;
import org.tedros.server.security.TBeanSecurity;
import org.tedros.server.security.TSecurityInterceptor;
import org.tedros.server.service.ITEjbService;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;

@TSecurityInterceptor
@Stateless(name = "ISomeEntityController")
@TBeanSecurity({@TBeanPolicie(id = DomainApp.SOME_FORM_ID,
    policie = {TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS})})
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class SomeEntityController extends TSecureEjbController<SomeEntity>
        implements ISomeEntityController, ITSecurity {

    @EJB
    private SomeEntityService serv;

    @EJB
    private ITSecurityController securityController;

    @Override
    public ITEjbService<SomeEntity> getService() {
        return serv;
    }

    @Override
    public ITSecurityController getSecurityController() {
        return securityController;
    }

    // Override save to add business-rule validation before delegating
    @Override
    public TResult<SomeEntity> save(TAccessToken token, SomeEntity entity) {
        try {
            // Business rule: block save if entity is in a final state
            if (!entity.isNew()) {
                SomeEntity current = serv.findById(entity);
                if (current == null)
                    return new TResult<>(TResult.TState.ERROR, "Record not found.");
                if ("CLOSED".equals(current.getStatus()))
                    return new TResult<>(TResult.TState.WARNING, "A closed record cannot be modified.");
            }
            return super.save(token, entity);
        } catch (Exception e) {
            return super.processException(token, entity, e);
        }
    }

    // Custom method delegating to the specialized service
    @Override
    public TResult<List<SomeEntity>> search(TAccessToken token, String name, String code) {
        try {
            List<SomeEntity> list = serv.search(name, code);
            return new TResult<>(TResult.TState.SUCCESS, list);
        } catch (Exception e) {
            return super.processException(token, null, e);
        }
    }
}
```

## 9. Real-World Examples

### Example 1 — Simple Controller (`JobEvidenceController`)

No custom logic needed. Wires the generic service via `@EJB`. The `@Stateless(name)` uses the interface name.

```java
@TSecurityInterceptor
@Stateless(name = "IJobEvidenceController")
@TBeanSecurity({@TBeanPolicie(id = DomainApp.EVIDENCE_MANAGER_FORM_ID,
    policie = {TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS})})
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class JobEvidenceController extends TSecureEjbController<JobEvidence>
        implements IJobEvidenceController, ITSecurity {

    @EJB
    private ItSupportToolsService<JobEvidence> serv;

    @EJB
    private ITSecurityController securityController;

    @Override
    public ITEjbService<JobEvidence> getService() { return serv; }

    @Override
    public ITSecurityController getSecurityController() { return securityController; }
}
```

**Its interface** (`-ejb-client`):

```java
@Remote
public interface IJobEvidenceController extends ITSecureEjbController<JobEvidence> {
    static final String JNDI_NAME = "IJobEvidenceControllerRemote";
}
```

---

### Example 2 — Controller with business rules (`GmudController`)

Overrides `save` and `remove` to enforce business rules (immutability of final-status records, status recalculation before persisting) before delegating to `super`.

```java
@TSecurityInterceptor
@Stateless(name = "IGmudController")
@TBeanSecurity({@TBeanPolicie(id = DomainApp.CHANGE_MANAGER_GMUD_EDIT_FORM_ID,
    policie = {TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS})})
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class GmudController extends TSecureEjbController<Gmud>
        implements IGmudController, ITSecurity {

    @EJB
    private GmudService serv;

    @EJB
    private ITSecurityController securityController;

    @Override
    public GmudService getService() { return serv; }

    @Override
    public ITSecurityController getSecurityController() { return securityController; }

    @Override
    public TResult<Gmud> save(TAccessToken token, Gmud entity) {
        try {
            if (!entity.isNew()) {
                Gmud currentDb = serv.findById(entity);

                if (currentDb == null)
                    return new TResult<>(TResult.TState.ERROR, "GMUD not found in the database.");

                // Block edits on records that have already reached a final status
                if (isFinalStatus(currentDb.getStatus()))
                    return new TResult<>(TResult.TState.WARNING,
                            "This GMUD has already been finalised (" + currentDb.getStatus() + ") and cannot be changed.");
            } else {
                if (entity.getStatus() == null)
                    entity.setStatus(GmudStatus.DRAFT.getDescription());
            }

            // Recalculate the GMUD status based on items/reviews before saving
            recalculateGmudStatus(entity);

            return super.save(token, entity);

        } catch (Exception e) {
            return super.processException(token, entity, e);
        }
    }

    @Override
    public TResult<Gmud> remove(TAccessToken token, Gmud entity) {
        try {
            Gmud gmud = serv.findById(entity);

            if (gmud == null)
                return new TResult<>(TResult.TState.ERROR, "Record not found.");

            if (GmudStatus.EXECUTING.getDescription().equals(gmud.getStatus()) ||
                GmudStatus.FINISHED.getDescription().equals(gmud.getStatus()))
                return new TResult<>(TResult.TState.WARNING,
                        "GMUD with status " + gmud.getStatus() + " cannot be deleted.");

            return super.remove(token, entity);

        } catch (Exception e) {
            return super.processException(token, entity, e);
        }
    }

    private void recalculateGmudStatus(Gmud gmud) {
        if (isExecutionPhase(gmud.getStatus()))
            updateStatusFromExecutionPlan(gmud);
        else
            updateStatusFromReviews(gmud);
    }

    private boolean isFinalStatus(String status) {
        return GmudStatus.FINISHED.getDescription().equals(status) ||
               GmudStatus.FAILED.getDescription().equals(status) ||
               GmudStatus.REJECTED.getDescription().equals(status);
    }

    private boolean isExecutionPhase(String status) {
        return GmudStatus.APPROVED.getDescription().equals(status) ||
               GmudStatus.EXECUTING.getDescription().equals(status) ||
               GmudStatus.FINISHED.getDescription().equals(status) ||
               GmudStatus.FAILED.getDescription().equals(status);
    }

    // ... updateStatusFromExecutionPlan() and updateStatusFromReviews() private helpers
}
```

**Its interface** (`-ejb-client`):

```java
@Remote
public interface IGmudController extends ITSecureEjbController<Gmud> {
    static final String JNDI_NAME = "IGmudControllerRemote";
}
```

> **Key points:**
> - `serv.findById(entity)` reads the **current database state** — never trust only the client-sent object for business-rule checks.
> - Return `TResult.TState.WARNING` (not `ERROR`) when a business rule blocks the operation — it is a user-facing message, not a system fault.
> - Always call `super.save(token, entity)` / `super.remove(token, entity)` after all checks pass.
> - Always call `super.processException(token, entity, e)` in every `catch` block.

---

### Example 3 — Controller with additional injected beans (`ProductivityActivityController`)

When the controller needs services beyond the standard EJB service (e.g., a MongoDB repository), inject them using `@EJB` and invoke them directly inside the custom methods.

```java
@TSecurityInterceptor
@Stateless(name = "IProductivityActivityController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class ProductivityActivityController extends TSecureEjbController<ProductivityActivityDTO>
        implements IProductivityActivityController, ITSecurity {

    @EJB
    private ItSupportToolsService<ProductivityActivityDTO> serv;

    @EJB
    private ProductivityActivityRepository repository; // additional EJB

    @EJB
    private ITSecurityController securityController;

    // Custom method using the additional repository
    public TResult<ProductivityActivityDTO> saveActivity(TAccessToken token,
            List<ProductivityActivityDTO> activities) {
        try {
            repository.saveAll(activities);
            return new TResult<>(TState.SUCCESS);
        } catch (Exception e) {
            return super.processException(token, null, e);
        }
    }
}
```

**Its interface** (`-ejb-client`):

```java
@Remote
public interface IProductivityActivityController extends ITSecureEjbController<ProductivityActivityDTO> {

    static final String JNDI_NAME = "IProductivityActivityControllerRemote";

    TResult<ProductivityActivityDTO> saveActivity(TAccessToken token, List<ProductivityActivityDTO> activities);

    TResult<List<ProductivityActivityDTO>> findUserIdAndDateRange(TAccessToken token, Long userId,
            LocalDateTime start, LocalDateTime end);

    TResult<List<ActivitySummaryDTO>> getWindowUsageSummary(TAccessToken token, Long userId,
            LocalDateTime start, LocalDateTime end);
}
```

## 10. Development Constraints

- **Modules:** Controller interface → `-ejb-client`. Controller implementation → `-ejb`.
- **`JNDI_NAME`:** Every interface **must** declare `static final String JNDI_NAME = "<InterfaceName>Remote"`. This is mandatory — the client uses it for JNDI lookup.
- **`@Stateless(name)`:** The name must match the interface name (e.g., `"IGmudController"`).
- **`ITSecurity`:** Every controller implementation must `implement ITSecurity`. Without it the interceptor throws `IllegalStateException`.
- **Return type:** Every controller method (including custom ones) must return `TResult<T>`. Never return raw entities or primitives.
- **Exception handling:** Always wrap custom method bodies in `try/catch` and call `super.processException(token, entity, e)` in the catch block. Never let exceptions propagate un-wrapped.
- **Business rules in `save`/`remove`:** Return `TResult.TState.WARNING` for user-facing rule violations; return `TResult.TState.ERROR` only for system/data errors.
- **No query logic:** The controller must **never** build queries or access the EAO directly. All data access must go through `getService()` or a specifically injected EJB.
- **Decision rule:** Start with the simple controller (Pattern A). Only create a specialized controller (Pattern B) when business-rule enforcement or custom client-facing methods are required.
