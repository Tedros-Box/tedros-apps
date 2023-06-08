/**
 * 
 */
package org.tedros.samples.module.order.builder;

import java.util.function.Function;

import org.tedros.api.descriptor.ITComponentDescriptor;
import org.tedros.fx.builder.TFunctionEntityToStringBuilder;
import org.tedros.person.model.NaturalPerson;
import org.tedros.server.entity.TEntity;

/**
 * @author Davis Gordon
 *
 */
public class CustomerToStringBuilder extends TFunctionEntityToStringBuilder {

	/* (non-Javadoc)
	 * @see org.tedros.fx.builder.ITGenericBuilder#build()
	 */
	@Override
	public Function<TEntity, String> build() {
		return e -> {
			NaturalPerson p = (NaturalPerson) e;
			StringBuilder sb = new StringBuilder(p.toString());
			if(p.getDocuments()!=null) {
				p.getDocuments().forEach(c->{
					sb.append(", ");
					sb.append(c.getType().getName()).append(": ");
					sb.append(c.getValue());
				});
			}
			return sb.toString();
		};
	}

	/* (non-Javadoc)
	 * @see org.tedros.fx.builder.ITBuilder#getComponentDescriptor()
	 */
	@Override
	public ITComponentDescriptor getComponentDescriptor() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.tedros.fx.builder.ITBuilder#setComponentDescriptor(org.tedros.api.descriptor.ITComponentDescriptor)
	 */
	@Override
	public void setComponentDescriptor(ITComponentDescriptor componentDescriptor) {
		// TODO Auto-generated method stub

	}

}
