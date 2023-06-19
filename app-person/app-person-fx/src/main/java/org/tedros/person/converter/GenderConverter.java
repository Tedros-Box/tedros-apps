package org.tedros.person.converter;

import org.tedros.fx.converter.TConverter;
import org.tedros.person.domain.Gender;

public class GenderConverter extends TConverter<String, Gender> {

	@Override
	public Gender getOut() {
		for(Gender e : Gender.values())
			if(super.getIn()!=null && e.getValue().equals(super.getIn()))
				return e;
		return null;
	}

}
