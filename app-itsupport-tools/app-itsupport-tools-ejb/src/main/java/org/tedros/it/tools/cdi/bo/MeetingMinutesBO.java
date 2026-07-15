package org.tedros.it.tools.cdi.bo;

import org.tedros.it.tools.cdi.eao.MeetingMinutesEao;
import org.tedros.it.tools.entity.MeetingMinutes;
import org.tedros.server.cdi.bo.TGenericBO;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class MeetingMinutesBO extends TGenericBO<MeetingMinutes> {

	@Inject
	private MeetingMinutesEao eao;

	@Override
	public MeetingMinutesEao getEao() {
		return eao;
	}
}
