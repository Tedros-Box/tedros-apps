package org.tedros.it.tools.ejb.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.tedros.it.tools.domain.DomainApp;
import org.tedros.it.tools.ejb.service.MeetingMinutesService;
import org.tedros.it.tools.entity.MeetingMinutes;
import org.tedros.server.ejb.controller.ITSecurityController;
import org.tedros.server.ejb.controller.TSecureEjbController;
import org.tedros.server.result.TResult;
import org.tedros.server.security.ITSecurity;
import org.tedros.server.security.TAccessPolicie;
import org.tedros.server.security.TAccessToken;
import org.tedros.server.security.TBeanPolicie;
import org.tedros.server.security.TBeanSecurity;
import org.tedros.server.security.TSecurityInterceptor;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;

@TSecurityInterceptor
@Stateless(name = "IMeetingMinutesController")
@TBeanSecurity({ @TBeanPolicie(id = DomainApp.MEETING_MINUTES_FORM_ID,
		policie = { TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS }) })
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class MeetingMinutesController extends TSecureEjbController<MeetingMinutes>
		implements IMeetingMinutesController, ITSecurity {

	@EJB
	private MeetingMinutesService serv;

	@EJB
	private ITSecurityController securityController;

	@Override
	public MeetingMinutesService getService() {
		return serv;
	}

	@Override
	public ITSecurityController getSecurityController() {
		return securityController;
	}

	@Override
	public TResult<MeetingMinutes> save(TAccessToken token, MeetingMinutes entity) {
		try {
			TResult<MeetingMinutes> result = super.save(token, entity);
			if (result.isSuccess() && result.getValue() != null && result.getValue().getId() != null) {
				MeetingMinutes saved = result.getValue();
				String meetingId = saved.getId().toString();
				String transcription = saved.getTranscription();
				if (StringUtils.isNotBlank(transcription)) {
					// ingest() purges existing vectors for meeting_id before re-embedding
					serv.createEmbedding(token, meetingId, transcription, buildRagMetadata(saved));
				} else {
					serv.purgeEmbedding(token, meetingId);
				}
			}
			return result;
		} catch (Exception e) {
			return super.processException(token, entity, e);
		}
	}

	@Override
	public TResult<MeetingMinutes> remove(TAccessToken token, MeetingMinutes entity) {
		String meetingId = entity != null && entity.getId() != null ? entity.getId().toString() : null;
		try {
			TResult<MeetingMinutes> result = super.remove(token, entity);
			if (result.isSuccess() && StringUtils.isNotBlank(meetingId)) {
				serv.purgeEmbedding(token, meetingId);
			}
			return result;
		} catch (Exception e) {
			return super.processException(token, entity, e);
		}
	}

	private static Map<String, String> buildRagMetadata(MeetingMinutes entity) {
		Map<String, String> meta = new HashMap<>();
		meta.put("source", "transcription");
		if (entity.getMeetingDate() != null) {
			meta.put("date", entity.getMeetingDate());
		}
		if (entity.getParticipants() != null) {
			meta.put("participants", entity.getParticipants());
		}
		if (entity.getIssueNumber() != null) {
			meta.put("project_context", entity.getIssueNumber());
		}
		if (entity.getMeetingTopic() != null) {
			meta.put("topic", entity.getMeetingTopic());
		}
		return meta;
	}
}
