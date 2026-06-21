package org.tedros.it.tools.ejb.controller;

import java.util.List;

import org.tedros.it.tools.entity.ServiceGroup;
import org.tedros.server.controller.ITSecureEjbController;
import org.tedros.server.result.TResult;
import org.tedros.server.security.TAccessToken;

import jakarta.ejb.Remote;

/**
 * Remote interface for ServiceGroupController.
 * Provides standard CRUD plus a custom search method.
 *
 * @author Davis Gordon
 */
@Remote
public interface IServiceGroupController extends ITSecureEjbController<ServiceGroup> {

    static final String JNDI_NAME = "IServiceGroupControllerRemote";

    TResult<List<ServiceGroup>> search(TAccessToken token, String name, Long catalogId);
}
