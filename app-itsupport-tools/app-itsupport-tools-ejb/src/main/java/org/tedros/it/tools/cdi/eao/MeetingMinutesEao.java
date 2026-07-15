package org.tedros.it.tools.cdi.eao;

import org.tedros.it.tools.entity.MeetingMinutes;
import org.tedros.server.cdi.eao.TGenericEAO;

import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class MeetingMinutesEao extends TGenericEAO<MeetingMinutes> {

	@Override
	public void beforePersist(MeetingMinutes entity) throws Exception {
		relateMeetingChildren(entity);
	}

	@Override
	public void beforeMerge(MeetingMinutes entity) throws Exception {
		relateMeetingChildren(entity);
	}

	private void relateMeetingChildren(MeetingMinutes entity) {
		if (entity.getMeetingAgenda() != null) {
			entity.getMeetingAgenda().forEach(a -> a.setMeetingMinutes(entity));
		}
		if (entity.getMeetingReferrals() != null) {
			entity.getMeetingReferrals().forEach(r -> r.setMeetingMinutes(entity));
		}
		if (entity.getEvidences() != null) {
			entity.getEvidences().forEach(e -> e.setMeetingMinutes(entity));
		}
	}
}
