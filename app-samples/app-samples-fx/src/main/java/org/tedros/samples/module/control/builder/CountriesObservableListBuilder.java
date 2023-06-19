/**
 * 
 */
package org.tedros.samples.module.control.builder;

import java.util.Locale;

import org.tedros.core.TLanguage;
import org.tedros.fx.builder.TGenericBuilder;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public class CountriesObservableListBuilder extends TGenericBuilder<ObservableList> {

	@Override
	public ObservableList<String> build() {
		return TLanguage.getLocale().equals(new Locale("en")) 
				? FXCollections.observableArrayList("Albania", "Andorra", "Austria", "Belarus", "Belgium", 
					"Bosnia and Herzegovina", "Bulgaria", "Croatia", "Cyprus", "Czech Republic", 
					"Denmark", "Estonia", "Finland", "France", "Germany", "Greece", "Hungary", 
					"Iceland", "Ireland", "Italy", "Kosovo", "Latvia", "Liechtenstein", "Lithuania", 
					"Luxembourg", "Malta", "Moldova", "Monaco", "Montenegro", "Netherlands", 
					"North Macedonia", "Norway", "Poland", "Portugal", "Romania", "Russia",
					"San Marino", "Serbia", "Slovakia", "Slovenia", "Spain", "Sweden", 
					"Switzerland", "Ukraine", "United Kingdom", "Vatican City")
				
				:  FXCollections.observableArrayList("Albânia", "Andorra", "Áustria", "Bélgica", "Bielorrússia", 
						"Bósnia e Herzegovina", "Bulgária", "Croácia", "Chipre", "República Checa", 
						"Dinamarca", "Estônia", "Finlândia", "França", "Alemanha", "Grécia", 
						"Hungria", "Islândia", "Irlanda", "Itália", "Kosovo", "Letônia", "Liechtenstein", 
						"Lituânia", "Luxemburgo", "Malta", "Moldávia", "Mônaco", "Montenegro", 
						"Países Baixos", "Macedônia do Norte", "Noruega", "Polônia", "Portugal", 
						"Romênia", "Rússia", "San Marino", "Sérvia", "Eslováquia", "Eslovênia",
						"Espanha", "Suécia", "Suíça", "Ucrânia", "Reino Unido", "Cidade do Vaticano");
	}

}
