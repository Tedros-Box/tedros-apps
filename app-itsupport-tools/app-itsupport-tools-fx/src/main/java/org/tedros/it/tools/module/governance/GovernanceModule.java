package org.tedros.it.tools.module.governance;

import org.tedros.core.TModule;
import org.tedros.core.annotation.TItem;
import org.tedros.core.annotation.TView;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.it.tools.ItToolsKey;
import org.tedros.it.tools.domain.DomainApp;
import org.tedros.it.tools.entity.CatalogService;
import org.tedros.it.tools.entity.ServiceCatalog;
import org.tedros.it.tools.model.ServiceCatalogReportModel;
import org.tedros.it.tools.module.governance.model.CatalogServiceMV;
import org.tedros.it.tools.module.governance.model.ServiceCatalogImportMV;
import org.tedros.it.tools.module.governance.model.ServiceCatalogMV;
import org.tedros.it.tools.module.governance.report.ServiceCatalogReportMV;

/**
 * Governança module.
 * Provides views for managing the Service Catalog with its groups,
 * the individual Catalog Services, and a JSON import tool.
 *
 * @author Davis Gordon
 */
@TView(items = {
    @TItem(
        title = ItToolsKey.VIEW_SERVICE_CATALOG,
        description = ItToolsKey.VIEW_SERVICE_CATALOG_DESC,
        modelView = ServiceCatalogMV.class,
        model = ServiceCatalog.class),
    @TItem(
        title = ItToolsKey.VIEW_CATALOG_SERVICE,
        description = ItToolsKey.VIEW_CATALOG_SERVICE_DESC,
        modelView = CatalogServiceMV.class,
        model = CatalogService.class),
    @TItem(
        title = ItToolsKey.VIEW_SERVICE_CATALOG_IMPORT,
        description = ItToolsKey.VIEW_SERVICE_CATALOG_IMPORT_DESC,
        modelView = ServiceCatalogImportMV.class,
        model = ServiceCatalog.class),
    @TItem(
            title = "Relatório de Catálogo de Serviços",
            description = "Relatório de Catálogo de Serviços",
            modelView = ServiceCatalogReportMV.class,
            model = ServiceCatalogReportModel.class)
})
@TSecurity(
    id = DomainApp.GOVERNANCE_MODULE_ID,
    appName = ItToolsKey.APP_ITSUPPORT,
    moduleName = ItToolsKey.MODULE_GOVERNANCE,
    allowedAccesses = TAuthorizationType.MODULE_ACCESS)
public class GovernanceModule extends TModule {

}
