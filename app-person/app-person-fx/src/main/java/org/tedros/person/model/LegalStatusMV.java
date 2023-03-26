/**
 * 
 */
package org.tedros.person.model;

/**
 * @author Davis Gordon
 *
 */
public class LegalStatusMV<E extends LegalStatus> extends PersonStatusMV<E> {

	public LegalStatusMV(E entity) {
		super(entity);
	}
}
