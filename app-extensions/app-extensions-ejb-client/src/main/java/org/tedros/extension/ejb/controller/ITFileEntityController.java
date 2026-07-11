package org.tedros.extension.ejb.controller;

import org.tedros.common.model.TFileEntity;
import org.tedros.server.controller.ITSecureEjbController;

import jakarta.ejb.Remote;

@Remote
public interface ITFileEntityController extends ITSecureEjbController<TFileEntity>{

	static final String JNDI_NAME = "ITFileEntityControllerRemote";
}
