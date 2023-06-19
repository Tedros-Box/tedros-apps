package org.tedros.extension.module.doc.converter;

import org.tedros.extension.model.DocType;
import org.tedros.fx.converter.TConverter;

public class DocTypeConverter extends TConverter<String, DocType> {

	@Override
	public DocType getOut() {
		for(DocType e : DocType.values())
			if(super.getIn()!=null && e.getValue().equals(super.getIn()))
				return e;
		return null;
	}

}