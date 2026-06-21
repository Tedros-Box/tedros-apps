package org.tedros.it.tools.module.governance.model;

import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TComboBoxField;
import org.tedros.fx.annotation.control.TDetailListField;
import org.tedros.fx.annotation.control.TFieldBox;
import org.tedros.fx.annotation.control.TGenericType;
import org.tedros.fx.annotation.control.TIntegerField;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TProcess;
import org.tedros.fx.annotation.control.TTextField;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.layout.TFlowPane;
import org.tedros.fx.annotation.layout.TPane;
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
import org.tedros.it.tools.ejb.controller.ICatalogServiceController;
import org.tedros.it.tools.ejb.controller.IServiceGroupController;
import org.tedros.it.tools.entity.CatalogService;
import org.tedros.it.tools.entity.ServiceGroup;
import org.tedros.it.tools.entity.ServiceVariant;
import org.tedros.server.query.TCompareOp;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * ModelView for CatalogService.
 * Allows creating and editing catalog services (number, name) together
 * with their variants inline via a master-detail list.
 * The 'group' association is set by the ServiceGroup context and is
 * not edited directly here.
 *
 * @author Davis Gordon
 */
@TForm(showBreadcrumBar = false, scroll = false)
@TEjbService(serviceName = ICatalogServiceController.JNDI_NAME, model = CatalogService.class)
@TListViewPresenter(
    page = @TPage(
        serviceName = ICatalogServiceController.JNDI_NAME,
        query = @TQuery(
            entity = CatalogService.class,
            condition = {
                @TCondition(field = "name",   operator = TCompareOp.LIKE,  label = TUsualKey.NAME),
                @TCondition(field = "number", operator = TCompareOp.EQUAL, label = TUsualKey.NUMBER)
            },
            orderBy = {
                @TOrder(label = TUsualKey.NUMBER, field = "number"),
                @TOrder(label = TUsualKey.NAME,   field = "name")
            }),
        showSearch = true,
        showOrderBy = true),
    presenter = @TPresenter(
        decorator = @TDecorator(
            viewTitle = ItToolsKey.TITLE_CATALOG_SERVICE,
            listTitle = ItToolsKey.TITLE_CATALOG_SERVICE_LIST),
        behavior = @TBehavior(
            runNewActionAfterSave = false,
            saveAllModels = false)))
@TSecurity(
    id = DomainApp.GOVERNANCE_CATALOG_SERVICE_FORM_ID,
    appName = ItToolsKey.APP_ITSUPPORT,
    moduleName = ItToolsKey.MODULE_GOVERNANCE,
    viewName = ItToolsKey.VIEW_CATALOG_SERVICE,
    allowedAccesses = {
        TAuthorizationType.VIEW_ACCESS,
        TAuthorizationType.EDIT,
        TAuthorizationType.SAVE,
        TAuthorizationType.DELETE,
        TAuthorizationType.NEW
    })
public class CatalogServiceMV extends TEntityModelView<CatalogService> {

    @TLabel(text = ItToolsKey.CATALOG_SERVICE_NUMBER)
    @TIntegerField
    @TFlowPane(hgap = 20, vgap = 12,
        pane = @TPane(children = {"number", "name", "group"}))
    private SimpleIntegerProperty number;

    @TLabel(text = ItToolsKey.CATALOG_SERVICE_NAME)
    @TTextField(maxLength = 300, required = true)
    private SimpleStringProperty name;
    
    @TLabel(text = ItToolsKey.CATALOG_SERVICE_GROUP)
    @TComboBoxField(
    		process=@TProcess(service = IServiceGroupController.JNDI_NAME, 
    		query = @TQuery(entity = ServiceGroup.class, orderBy=@TOrder(field="name"))))
    private SimpleObjectProperty<ServiceGroup> group;

    @TFieldBox(node = @TNode(id = "list", parse = true))
    @TDetailListField(
        region = @TRegion(maxHeight = 500, minHeight = 500, parse = true),
        modelView = ServiceVariantDetailMV.class,
        entity = ServiceVariant.class)
    @TGenericType(model = ServiceVariant.class, modelView = ServiceVariantDetailMV.class)
    private ITObservableList<ServiceVariantDetailMV> variants;

    public CatalogServiceMV(CatalogService entity) {
        super(entity);
        super.formatToString("[%s] %s", number, name);
    }
    
}
