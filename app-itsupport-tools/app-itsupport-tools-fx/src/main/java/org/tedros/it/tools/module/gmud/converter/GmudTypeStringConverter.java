package org.tedros.it.tools.module.gmud.converter;

import org.tedros.it.tools.domain.GmudType;

import javafx.util.StringConverter;

public class GmudTypeStringConverter extends StringConverter<GmudType> {

	@Override
	public String toString(GmudType object) {
		return object != null ? object.getDescription() : "";
	}

	@Override
	public GmudType fromString(String string) {
		for (GmudType type : GmudType.values()) {
			if (type.getDescription().equals(string)) {
				return type;
			}
		}
		return null;
	}

}
