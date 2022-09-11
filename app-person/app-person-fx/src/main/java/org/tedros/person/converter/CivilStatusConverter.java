package org.tedros.person.converter;

import org.tedros.fx.form.TConverter;
import org.tedros.person.domain.CivilStatus;

public class CivilStatusConverter extends TConverter<String, CivilStatus> {

	@Override
	public CivilStatus getOut() {
		for(CivilStatus e : CivilStatus.values())
			if(super.getIn()!=null && e.getValue().equals(super.getIn()))
				return e;
		return null;
	}

}
