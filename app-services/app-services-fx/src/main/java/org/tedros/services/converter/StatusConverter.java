package org.tedros.services.converter;

import org.tedros.fx.converter.TConverter;
import org.tedros.services.domain.Status;

public class StatusConverter extends TConverter<String, Status> {

	@Override
	public Status getOut() {
		for(Status e : Status.values())
			if(super.getIn()!=null && e.getValue().equals(super.getIn()))
				return e;
		return null;
	}

}
