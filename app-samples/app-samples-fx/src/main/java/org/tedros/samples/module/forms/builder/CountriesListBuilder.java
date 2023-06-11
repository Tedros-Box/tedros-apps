/**
 * 
 */
package org.tedros.samples.module.forms.builder;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.tedros.core.TLanguage;
import org.tedros.fx.builder.ITGenericBuilder;
import org.tedros.fx.builder.TGenericBuilder;

/**
 * @author Davis Gordon
 *
 */
public class CountriesListBuilder extends TGenericBuilder<List<String>> {

	@Override
	public List<String> build() {
		return TLanguage.getLocale().equals(new Locale("en")) 
				? Arrays.asList("Albania", "Andorra", "Austria", "Belarus", "Belgium", 
					"Bosnia and Herzegovina", "Bulgaria", "Croatia", "Cyprus", "Czech Republic", 
					"Denmark", "Estonia", "Finland", "France", "Germany", "Greece", "Hungary", 
					"Iceland", "Ireland", "Italy", "Kosovo", "Latvia", "Liechtenstein", "Lithuania", 
					"Luxembourg", "Malta", "Moldova", "Monaco", "Montenegro", "Netherlands", 
					"North Macedonia", "Norway", "Poland", "Portugal", "Romania", "Russia",
					"San Marino", "Serbia", "Slovakia", "Slovenia", "Spain", "Sweden", 
					"Switzerland", "Ukraine", "United Kingdom", "Vatican City")
				
				: Arrays.asList("Albânia", "Andorra", "Áustria", "Bélgica", "Bielorrússia", 
						"Bósnia e Herzegovina", "Bulgária", "Croácia", "Chipre", "República Checa", 
						"Dinamarca", "Estônia", "Finlândia", "França", "Alemanha", "Grécia", 
						"Hungria", "Islândia", "Irlanda", "Itália", "Kosovo", "Letônia", "Liechtenstein", 
						"Lituânia", "Luxemburgo", "Malta", "Moldávia", "Mônaco", "Montenegro", 
						"Países Baixos", "Macedônia do Norte", "Noruega", "Polônia", "Portugal", 
						"Romênia", "Rússia", "San Marino", "Sérvia", "Eslováquia", "Eslovênia",
						"Espanha", "Suécia", "Suíça", "Ucrânia", "Reino Unido", "Cidade do Vaticano");
	}

}
