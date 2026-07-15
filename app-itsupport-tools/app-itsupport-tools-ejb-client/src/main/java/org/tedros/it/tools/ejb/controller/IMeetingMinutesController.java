package org.tedros.it.tools.ejb.controller;

import org.tedros.it.tools.entity.MeetingMinutes;
import org.tedros.server.controller.ITSecureEjbController;

import jakarta.ejb.Remote;

@Remote
public interface IMeetingMinutesController extends ITSecureEjbController<MeetingMinutes> {

	static final String JNDI_NAME = "IMeetingMinutesControllerRemote";

}
