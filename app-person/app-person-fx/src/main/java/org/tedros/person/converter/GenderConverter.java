package com.tedros.person.converter;

import com.tedros.fxapi.form.TConverter;
import com.tedros.person.domain.Gender;

public class GenderConverter extends TConverter<String, Gender> {

	@Override
	public Gender getOut() {
		for(Gender e : Gender.values())
			if(super.getIn()!=null && e.getValue().equals(super.getIn()))
				return e;
		return null;
	}

}
