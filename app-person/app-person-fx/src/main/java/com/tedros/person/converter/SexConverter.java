package com.tedros.person.converter;

import com.tedros.fxapi.form.TConverter;
import com.tedros.person.domain.Sex;

public class SexConverter extends TConverter<String, Sex> {

	@Override
	public Sex getOut() {
		for(Sex e : Sex.values())
			if(super.getIn()!=null && e.getValue().equals(super.getIn()))
				return e;
		return null;
	}

}