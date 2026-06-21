package org.tedros.it.tools.ejb.controller;

import java.util.List;

import org.tedros.it.tools.entity.ServiceVariant;
import org.tedros.server.controller.ITSecureEjbController;
import org.tedros.server.result.TResult;
import org.tedros.server.security.TAccessToken;

import jakarta.ejb.Remote;

/**
 * Remote interface for ServiceVariantController.
 * Provides standard CRUD plus a custom search method.
 *
 * @author Davis Gordon
 */
@Remote
public interface IServiceVariantController extends ITSecureEjbController<ServiceVariant> {

    static final String JNDI_NAME = "IServiceVariantControllerRemote";

    TResult<List<ServiceVariant>> search(TAccessToken token, String complexity, Long serviceId);
}
