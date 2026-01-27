package org.tedros.it.tools.module.gmud.builder;

import org.tedros.fx.builder.TGenericBuilder;
import org.tedros.it.tools.domain.GmudStatus;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@SuppressWarnings("rawtypes")
public class GmudStatusBuilder extends TGenericBuilder<ObservableList> {
	@Override
	public ObservableList<String> build() {
		ObservableList<String> list = FXCollections.observableArrayList();
		for (GmudStatus t : GmudStatus.values()) {
			list.add(t.getDescription());
		}
		
		return list;
	}
}
