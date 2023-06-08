/**
 * 
 */
package org.tedros.samples.module.order.builder;

import org.tedros.extension.model.DocType;
import org.tedros.fx.builder.TGenericBuilder;

/**
 * @author Davis Gordon
 *
 */
public class DocTypeOtherBuilder extends TGenericBuilder<DocType> {
	@Override
	public DocType build() {
		return DocType.OTHER;
	}

}
