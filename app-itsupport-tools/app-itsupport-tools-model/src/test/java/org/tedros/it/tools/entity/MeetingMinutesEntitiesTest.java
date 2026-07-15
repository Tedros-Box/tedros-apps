package org.tedros.it.tools.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.tedros.common.model.TByteEntity;
import org.tedros.common.model.TFileEntity;

/**
 * Unit tests for meeting-minutes persistence model helpers.
 */
public class MeetingMinutesEntitiesTest {

	@Test
	public void meetingMinutesDefaultsActivityId() {
		MeetingMinutes m = new MeetingMinutes();
		assertEquals(Integer.valueOf(19), m.getActivityId());
	}

	@Test
	public void meetingMinutesHoldsTranscriptionText() {
		MeetingMinutes m = new MeetingMinutes();
		m.setTranscription("Fulano falou sobre o deploy...");
		assertEquals("Fulano falou sobre o deploy...", m.getTranscription());
	}

	@Test
	public void meetingMinutesEqualsByBusinessFields() {
		MeetingMinutes a = new MeetingMinutes();
		a.setMeetingPlace("Sala 1");
		a.setIssueNumber("123");
		MeetingMinutes b = new MeetingMinutes();
		b.setMeetingPlace("Sala 1");
		b.setIssueNumber("123");
		assertEquals(a, b);
		b.setIssueNumber("999");
		assertFalse(a.equals(b));
	}

	@Test
	public void agendaEqualsByIdWhenPresent() {
		MeetingAgenda a = new MeetingAgenda();
		a.setId(1L);
		a.setItem("1");
		a.setDescription("A");
		MeetingAgenda b = new MeetingAgenda();
		b.setId(1L);
		b.setItem("2");
		b.setDescription("B");
		assertEquals(a, b);
	}

	@Test
	public void referralEqualsByFieldsWithoutId() {
		MeetingReferral a = new MeetingReferral();
		a.setItem("1");
		a.setDescription("abrir chamado");
		a.setResponsable("Davis");
		a.setDeadline("2026-08-01");
		MeetingReferral b = new MeetingReferral();
		b.setItem("1");
		b.setDescription("abrir chamado");
		b.setResponsable("Davis");
		b.setDeadline("2026-08-01");
		assertEquals(a, b);
	}

	@Test
	public void evidenceInitAndCloseInputStream() throws IOException {
		MeetingEvidence e = new MeetingEvidence();
		e.setName("foto.png");
		TFileEntity file = new TFileEntity();
		TByteEntity bytes = new TByteEntity();
		bytes.setBytes(new byte[] { 1, 2, 3 });
		file.setByteEntity(bytes);
		e.setFile(file);

		assertNull(e.getEvidence());
		e.initInputStream();
		assertNotNull(e.getEvidence());
		assertEquals(1, e.getEvidence().read());
		e.closeInputStream();
		assertNull(e.getEvidence());
	}

	@Test
	public void evidenceInitWithoutBytesKeepsStreamNull() {
		MeetingEvidence e = new MeetingEvidence();
		e.setFile(new TFileEntity());
		e.initInputStream();
		assertNull(e.getEvidence());
	}

	@Test
	public void meetingMinutesHoldsChildrenCollections() {
		MeetingMinutes m = new MeetingMinutes();
		MeetingAgenda agenda = new MeetingAgenda();
		agenda.setItem("1");
		MeetingReferral referral = new MeetingReferral();
		referral.setItem("1");
		MeetingEvidence evidence = new MeetingEvidence();
		evidence.setName("e1");
		m.setMeetingAgenda(List.of(agenda));
		m.setMeetingReferrals(List.of(referral));
		m.setEvidences(List.of(evidence));
		assertEquals(1, m.getMeetingAgenda().size());
		assertEquals(1, m.getMeetingReferrals().size());
		assertEquals(1, m.getEvidences().size());
		assertTrue(m.getEvidences().get(0).getName().equals("e1"));
	}
}
