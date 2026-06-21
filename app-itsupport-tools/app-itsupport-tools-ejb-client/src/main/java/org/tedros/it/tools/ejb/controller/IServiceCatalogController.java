package org.tedros.it.tools.ejb.controller;

import java.util.List;

import org.tedros.it.tools.entity.ServiceCatalog;
import org.tedros.server.controller.ITSecureEjbController;
import org.tedros.server.result.TResult;
import org.tedros.server.security.TAccessToken;

import jakarta.ejb.Remote;

/**
 * Remote interface for ServiceCatalogController.
 * Provides standard CRUD plus a custom search method.
 *
 * @author Davis Gordon
 */
@Remote
public interface IServiceCatalogController extends ITSecureEjbController<ServiceCatalog> {

    static final String JNDI_NAME = "IServiceCatalogControllerRemote";

    TResult<List<ServiceCatalog>> search(TAccessToken token, String name);
}
