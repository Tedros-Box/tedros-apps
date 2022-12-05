package org.tedros.person.converter;

import org.tedros.fx.form.TConverter;
import org.tedros.person.domain.Sex;

public class SexConverter extends TConverter<String, Sex> {

	@Override
	public Sex getOut() {
		for(Sex e : Sex.values())
			if(super.getIn()!=null && e.getValue().equals(super.getIn()))
				return e;
		return null;
	}

}