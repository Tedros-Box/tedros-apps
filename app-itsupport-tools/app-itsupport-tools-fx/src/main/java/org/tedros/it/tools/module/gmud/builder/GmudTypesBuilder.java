package org.tedros.it.tools.module.gmud.builder;

import org.tedros.fx.builder.TGenericBuilder;
import org.tedros.it.tools.domain.GmudType;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@SuppressWarnings("rawtypes")
public class GmudTypesBuilder extends TGenericBuilder<ObservableList> {
	@Override
	public ObservableList<String> build() {
		ObservableList<String> list = FXCollections.observableArrayList();
		for (GmudType t : GmudType.values()) {
			list.add(t.getDescription());
		}
		
		return list;
	}
}
