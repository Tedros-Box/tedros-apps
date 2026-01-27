package org.tedros.it.tools.module.gmud.converter;

import org.tedros.it.tools.domain.GmudStatus;

import javafx.util.StringConverter;

public class GmudStatusStringConverter extends StringConverter<GmudStatus> {

	@Override
	public String toString(GmudStatus object) {
		return object != null ? object.getDescription() : "";
	}

	@Override
	public GmudStatus fromString(String string) {
		for (GmudStatus Status : GmudStatus.values()) {
			if (Status.getDescription().equals(string)) {
				return Status;
			}
		}
		return null;
	}

}
