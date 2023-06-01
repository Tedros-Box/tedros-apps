package org.tedros.extension.ejb.controller;

import javax.ejb.Remote;

import org.tedros.common.model.TFileEntity;
import org.tedros.server.controller.ITSecureEjbController;

@Remote
public interface ITFileEntityController extends ITSecureEjbController<TFileEntity>{

	static final String JNDI_NAME = "ITFileEntityControllerRemote";
}
