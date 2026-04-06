package org.tedros.it.tools.module.governance.model;

import org.tedros.fx.annotation.control.TFieldBox;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.component.TComponent;
import org.tedros.fx.model.TModelView;
import org.tedros.fx.presenter.model.behavior.TViewBehavior;
import org.tedros.fx.presenter.model.decorator.TViewDecorator;
import org.tedros.it.tools.ItToolsKey;
import org.tedros.it.tools.module.governance.component.ServiceCatalogImportComponent;

import javafx.beans.property.SimpleObjectProperty;

/**
 * ModelView for importing a Service Catalog from a JSON file.
 * Hosts the {@link ServiceCatalogImportComponent} which handles the full
 * import workflow: file selection → validation → preview → import.
 *
 * @author Davis Gordon
 */
@TForm(showBreadcrumBar = false, scroll = false)
@TPresenter(model = ServiceCatalogImportModel.class,
        decorator = @TDecorator(type = TViewDecorator.class, viewTitle = ItToolsKey.TITLE_SERVICE_CATALOG_IMPORT),
        behavior = @TBehavior(type = TViewBehavior.class))
public class ServiceCatalogImportMV extends TModelView<ServiceCatalogImportModel> {

    @TFieldBox(node = @TNode(parse = true, id = "importComponent"))
    @TComponent(type = ServiceCatalogImportComponent.class)
    private SimpleObjectProperty<Object> importComponent;

    public ServiceCatalogImportMV(ServiceCatalogImportModel model) {
        super(model);
    }

    public SimpleObjectProperty<Object> getImportComponent() {
        return importComponent;
    }

    public void setImportComponent(SimpleObjectProperty<Object> importComponent) {
        this.importComponent = importComponent;
    }
}
