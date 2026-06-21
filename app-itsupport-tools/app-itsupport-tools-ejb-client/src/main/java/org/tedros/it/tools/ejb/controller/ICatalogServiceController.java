package org.tedros.it.tools.ejb.controller;

import java.util.List;

import org.tedros.it.tools.entity.CatalogService;
import org.tedros.server.controller.ITSecureEjbController;
import org.tedros.server.result.TResult;
import org.tedros.server.security.TAccessToken;

import jakarta.ejb.Remote;

/**
 * Remote interface for CatalogServiceController.
 * Provides standard CRUD plus a custom search method.
 *
 * @author Davis Gordon
 */
@Remote
public interface ICatalogServiceController extends ITSecureEjbController<CatalogService> {

    static final String JNDI_NAME = "ICatalogServiceControllerRemote";

    TResult<List<CatalogService>> search(TAccessToken token, String name, Integer number, Long groupId);
}
