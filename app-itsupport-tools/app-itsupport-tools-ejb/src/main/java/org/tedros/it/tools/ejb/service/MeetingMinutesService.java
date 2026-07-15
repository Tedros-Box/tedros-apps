package org.tedros.it.tools.ejb.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.tedros.ai.ejb.client.IMeetingMinutesRagController;
import org.tedros.it.tools.cdi.bo.MeetingMinutesBO;
import org.tedros.it.tools.entity.MeetingMinutes;
import org.tedros.server.ejb.service.TEjbService;
import org.tedros.server.security.TAccessToken;
import org.tedros.server.service.TServiceLocator;
import org.tedros.util.TLoggerUtil;

import jakarta.ejb.Asynchronous;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;

@LocalBean
@Stateless(name = "MeetingMinutesService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class MeetingMinutesService extends TEjbService<MeetingMinutes> {

	private static final Logger LOGGER = TLoggerUtil.getLogger(MeetingMinutesService.class);

	@Inject
	private MeetingMinutesBO bo;

	@Override
	public MeetingMinutesBO getBussinesObject() {
		return bo;
	}

	/**
	 * Fire-and-forget RAG ingest of the meeting transcription (purge + re-embed inside
	 * {@link IMeetingMinutesRagController#ingest}).
	 */
	@Asynchronous
	public void createEmbedding(TAccessToken token, String meetingId, String transcription,
			Map<String, String> metadata) {
		if (StringUtils.isBlank(meetingId) || StringUtils.isBlank(transcription)) {
			return;
		}
		try {
			TServiceLocator serv = TServiceLocator.getInstance();
			try {
				IMeetingMinutesRagController rag = serv.lookupWithRetry(IMeetingMinutesRagController.JNDI_NAME);
				Map<String, String> meta = metadata != null ? new HashMap<>(metadata) : new HashMap<>();
				meta.putIfAbsent("meeting_id", meetingId);
				meta.putIfAbsent("source", "transcription");
				rag.ingest(token, meetingId, transcription, meta);
			} finally {
				serv.close();
			}
		} catch (Exception e) {
			LOGGER.error("Meeting minutes RAG ingest failed for {}: {}", meetingId, e.getMessage(), e);
		}
	}

	/** Fire-and-forget RAG purge for a meeting id. */
	@Asynchronous
	public void purgeEmbedding(TAccessToken token, String meetingId) {
		if (StringUtils.isBlank(meetingId)) {
			return;
		}
		try {
			TServiceLocator serv = TServiceLocator.getInstance();
			try {
				IMeetingMinutesRagController rag = serv.lookupWithRetry(IMeetingMinutesRagController.JNDI_NAME);
				rag.purge(token, meetingId);
			} finally {
				serv.close();
			}
		} catch (Exception e) {
			LOGGER.error("Meeting minutes RAG purge failed for {}: {}", meetingId, e.getMessage(), e);
		}
	}
}
