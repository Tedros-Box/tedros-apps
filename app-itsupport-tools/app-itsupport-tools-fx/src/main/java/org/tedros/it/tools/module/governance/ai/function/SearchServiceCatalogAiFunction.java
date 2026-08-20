package org.tedros.it.tools.module.governance.ai.function;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.tedros.ai.function.TFunction;
import org.tedros.ai.function.ToolCallResult;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.service.remote.TEjbServiceLocator;
import org.tedros.it.tools.ejb.controller.IServiceCatalogController;
import org.tedros.it.tools.entity.CatalogService;
import org.tedros.it.tools.entity.ServiceCatalog;
import org.tedros.it.tools.entity.ServiceGroup;
import org.tedros.it.tools.entity.ServiceVariant;
import org.tedros.it.tools.module.governance.ai.model.CatalogServiceModel;
import org.tedros.it.tools.module.governance.ai.model.ServiceCatalogFilterModel;
import org.tedros.it.tools.module.governance.ai.model.ServiceCatalogModel;
import org.tedros.it.tools.module.governance.ai.model.ServiceGroupModel;
import org.tedros.it.tools.module.governance.ai.model.ServiceVariantModel;
import org.tedros.server.result.TResult;

public class SearchServiceCatalogAiFunction extends TFunction<ServiceCatalogFilterModel> {

    public static final String NAME = "search_service_catalog";
    public static final String DESCRIPTION = "Search and filter the Service Catalog (Catálogo de Serviços). "
            + "Filter by category/group name, service number, service name, variant ID, complexity (Baixa, Média, Alta, Única), "
            + "scope, estimated hours (HPA), deliverables/evidence, required profiles, or activities performed. "
            + "If no filter is provided, returns the full catalog structure.";

    public SearchServiceCatalogAiFunction() {
        super(NAME, DESCRIPTION, ServiceCatalogFilterModel.class, filter -> {
            try (TEjbServiceLocator locator = TEjbServiceLocator.getInstance()) {
                IServiceCatalogController controller = locator.lookup(IServiceCatalogController.JNDI_NAME);
                TResult<List<ServiceCatalog>> result = controller.search(TedrosContext.getLoggedUser().getAccessToken(), (String) null);

                if (result.isSuccess()) {
                    List<ServiceCatalogModel> models = filterAndConvert(result.getValue(), filter);

                    return ToolCallResult.builder()
                            .message("Service catalog retrieved successfully.")
                            .result(Map.of(
                                STATUS, SUCCESS,
                                ACTION, "service_catalog_retrieved",
                                SYSTEM_INSTRUCTION, "Service catalog retrieved successfully. "
                                        + "Do not retry again. Proceed with the user's request.",
                                "catalogs", models
                            ))
                            .build();
                } else {
                    String status = result.getState().name();
                    String message = StringUtils.isNoneBlank(result.getMessage())
                            ? result.getMessage()
                            : "Unknown error";

                    return ToolCallResult.builder()
                            .message(status + " while retrieving service catalog")
                            .result(Map.of(
                                STATUS, ERROR,
                                ACTION, "service_catalog_retrieval_" + status.toLowerCase(),
                                ERROR_MESSAGE, message
                            ))
                            .build();
                }
            } catch (Exception e) {
                return ToolCallResult.builder()
                        .message("Error retrieving service catalog: " + e.getMessage())
                        .result(Map.of(
                            STATUS, ERROR,
                            ACTION, "service_catalog_retrieval_error",
                            ERROR_MESSAGE, e.getMessage() != null ? e.getMessage() : "Unknown exception"
                        ))
                        .build();
            }
        });
    }

    public static List<ServiceCatalogModel> filterAndConvert(List<ServiceCatalog> catalogs, ServiceCatalogFilterModel filter) {
        if (catalogs == null || catalogs.isEmpty()) {
            return List.of();
        }
        boolean hasFilter = filter != null && filter.hasCriteria();
        if (!hasFilter) {
            return catalogs.stream().map(ServiceCatalogModel::new).toList();
        }

        List<ServiceCatalogModel> result = new ArrayList<>();
        for (ServiceCatalog catalog : catalogs) {
            if (catalog.getGroups() == null) {
                continue;
            }
            List<ServiceGroupModel> matchedGroups = new ArrayList<>();
            for (ServiceGroup group : catalog.getGroups()) {
                if (!matchGroup(group, filter)) {
                    continue;
                }
                if (group.getServices() == null) {
                    continue;
                }
                List<CatalogServiceModel> matchedServices = new ArrayList<>();
                for (CatalogService service : group.getServices()) {
                    if (!matchService(service, filter)) {
                        continue;
                    }
                    if (service.getVariants() == null) {
                        continue;
                    }
                    List<ServiceVariantModel> matchedVariants = service.getVariants().stream()
                            .filter(v -> matchVariant(v, filter))
                            .map(ServiceVariantModel::new)
                            .toList();

                    if (!matchedVariants.isEmpty()) {
                        matchedServices.add(new CatalogServiceModel(service, matchedVariants));
                    }
                }
                if (!matchedServices.isEmpty()) {
                    matchedGroups.add(new ServiceGroupModel(group, matchedServices));
                }
            }
            if (!matchedGroups.isEmpty()) {
                result.add(new ServiceCatalogModel(catalog, matchedGroups));
            }
        }
        return result;
    }

    private static boolean containsIgnoreCase(String source, String search) {
        if (source == null || search == null) {
            return false;
        }
        return source.toLowerCase().contains(search.toLowerCase());
    }

    private static boolean matchVariant(ServiceVariant v, ServiceCatalogFilterModel f) {
        if (v == null) {
            return false;
        }
        if (StringUtils.isNotBlank(f.getVariantId())
                && (v.getVariantId() == null || !v.getVariantId().trim().equalsIgnoreCase(f.getVariantId().trim()))) {
            return false;
        }
        if (StringUtils.isNotBlank(f.getComplexity())
                && (v.getComplexity() == null || !containsIgnoreCase(v.getComplexity(), f.getComplexity().trim()))) {
            return false;
        }
        if (StringUtils.isNotBlank(f.getScope())
                && !containsIgnoreCase(v.getScope(), f.getScope().trim())) {
            return false;
        }
        if (f.getEstimatedHours() != null) {
            if (v.getEstimatedHours() == null || Double.compare(v.getEstimatedHours(), f.getEstimatedHours()) != 0) {
                return false;
            }
        }
        if (f.getMinEstimatedHours() != null) {
            if (v.getEstimatedHours() == null || v.getEstimatedHours() < f.getMinEstimatedHours()) {
                return false;
            }
        }
        if (f.getMaxEstimatedHours() != null) {
            if (v.getEstimatedHours() == null || v.getEstimatedHours() > f.getMaxEstimatedHours()) {
                return false;
            }
        }
        if (StringUtils.isNotBlank(f.getDeliverables())
                && !containsIgnoreCase(v.getDeliverables(), f.getDeliverables().trim())) {
            return false;
        }
        if (StringUtils.isNotBlank(f.getRequiredProfiles())
                && !containsIgnoreCase(v.getRequiredProfiles(), f.getRequiredProfiles().trim())) {
            return false;
        }
        if (StringUtils.isNotBlank(f.getActivitiesPerformed())
                && !containsIgnoreCase(v.getActivitiesPerformed(), f.getActivitiesPerformed().trim())) {
            return false;
        }
        return true;
    }

    private static boolean matchService(CatalogService s, ServiceCatalogFilterModel f) {
        if (s == null) {
            return false;
        }
        if (f.getServiceNumber() != null
                && (s.getNumber() == null || !s.getNumber().equals(f.getServiceNumber()))) {
            return false;
        }
        if (StringUtils.isNotBlank(f.getServiceName())
                && !containsIgnoreCase(s.getName(), f.getServiceName().trim())) {
            return false;
        }
        return true;
    }

    private static boolean matchGroup(ServiceGroup g, ServiceCatalogFilterModel f) {
        if (g == null) {
            return false;
        }
        if (StringUtils.isNotBlank(f.getCategoryName())
                && !containsIgnoreCase(g.getName(), f.getCategoryName().trim())) {
            return false;
        }
        return true;
    }
}
