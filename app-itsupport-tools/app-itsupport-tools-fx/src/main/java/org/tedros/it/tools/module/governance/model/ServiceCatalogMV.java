package org.tedros.it.tools.module.governance.model;

import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.fx.annotation.control.TDetailListField;
import org.tedros.fx.annotation.control.TFieldBox;
import org.tedros.fx.annotation.control.TGenericType;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TTextField;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.page.TPage;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TListViewPresenter;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.process.TEjbService;
import org.tedros.fx.annotation.query.TCondition;
import org.tedros.fx.annotation.query.TOrder;
import org.tedros.fx.annotation.query.TQuery;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.scene.layout.TRegion;
import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.model.TEntityModelView;
import org.tedros.it.tools.ItToolsKey;
import org.tedros.it.tools.domain.DomainApp;
import org.tedros.it.tools.ejb.controller.IServiceCatalogController;
import org.tedros.it.tools.entity.ServiceCatalog;
import org.tedros.it.tools.entity.ServiceGroup;
import org.tedros.it.tools.module.governance.action.ExportServiceCatalogAction;
import org.tedros.server.query.TCompareOp;

import javafx.beans.property.SimpleStringProperty;

/**
 * ModelView for ServiceCatalog.
 * Allows creating and editing a service catalog with its groups.
 * The 'name' field is a simple text input.
 * The 'groups' field renders as an inline editable list of ServiceGroupDetailMV records.
 *
 * @author Davis Gordon
 */
@TForm(showBreadcrumBar = false, scroll = false)
@TEjbService(serviceName = IServiceCatalogController.JNDI_NAME, model = ServiceCatalog.class)
@TListViewPresenter(
    page = @TPage(
        serviceName = IServiceCatalogController.JNDI_NAME,
        query = @TQuery(
            entity = ServiceCatalog.class,
            condition = {
                @TCondition(field = "name", operator = TCompareOp.LIKE, label = "Name")
            },
            orderBy = {
                @TOrder(label = "Name", field = "name")
            }),
        showSearch = true,
        showOrderBy = true),
    presenter = @TPresenter(
        decorator = @TDecorator(
        		buildPrintButton = true,
        		printButtonText = "JSON",
            viewTitle = ItToolsKey.TITLE_SERVICE_CATALOG,
            listTitle = ItToolsKey.TITLE_SERVICE_CATALOG_LIST),
        behavior = @TBehavior(
            runNewActionAfterSave = false,
            saveAllModels = false,            
            action = ExportServiceCatalogAction.class)))
@TSecurity(
    id = DomainApp.GOVERNANCE_SERVICE_CATALOG_FORM_ID,
    appName = ItToolsKey.APP_ITSUPPORT,
    moduleName = ItToolsKey.MODULE_GOVERNANCE,
    viewName = ItToolsKey.VIEW_SERVICE_CATALOG,
    allowedAccesses = {
        TAuthorizationType.VIEW_ACCESS,
        TAuthorizationType.EDIT,
        TAuthorizationType.SAVE,
        TAuthorizationType.DELETE,
        TAuthorizationType.NEW,
        TAuthorizationType.PRINT
    })
public class ServiceCatalogMV extends TEntityModelView<ServiceCatalog> {

    @TLabel(text = ItToolsKey.SERVICE_CATALOG_NAME)
    @TTextField(maxLength = 200, required = true)
    private SimpleStringProperty name;

    @TFieldBox(node = @TNode(id = "list", parse = true))
    @TDetailListField(
        region = @TRegion(maxHeight = 350, parse = true),
        modelView = ServiceGroupDetailMV.class,
        entity = ServiceGroup.class)
    @TGenericType(model = ServiceGroup.class, modelView = ServiceGroupDetailMV.class)
    private ITObservableList<ServiceGroupDetailMV> groups;

    public ServiceCatalogMV(ServiceCatalog entity) {
        super(entity);
        super.formatToString("%s", name);
    }

    // --- Getters and Setters ---

    public SimpleStringProperty getName() {
        return name;
    }

    public void setName(SimpleStringProperty name) {
        this.name = name;
    }

    public ITObservableList<ServiceGroupDetailMV> getGroups() {
        return groups;
    }

    public void setGroups(ITObservableList<ServiceGroupDetailMV> groups) {
        this.groups = groups;
    }
}
