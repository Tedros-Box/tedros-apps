/**
 * 
 */
package org.tedros.redminetools.server.cdi.bo;

import java.util.List;

import org.tedros.redminetools.model.TProject;
import org.tedros.redminetools.server.cdi.eao.RedmineEAO;
import org.tedros.server.cdi.bo.ITGenericBO;
import org.tedros.server.cdi.eao.ITGenericEAO;
import org.tedros.server.entity.ITEntity;
import org.tedros.server.query.TSelect;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;

/**
 * The CDI business object 
 * 
 * @author Davis Dun
 *
 */
@Dependent
public class RedmineApiBO<E extends ITEntity> implements ITGenericBO<E> {

	
	@Override
	public ITGenericEAO<E> getEao() {
		return null;
	}

	@Override
	public List<E> search(TSelect<E> sel) {
		
		Class<E> type = sel.getType();
		
		if(type == TProject.class) {
			
		}
		
		
		return null;
	}

	@Override
	public List<E> search(TSelect<E> sel, int firstResult, int maxResult) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long countSearch(TSelect<E> sel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public E findById(E entidade) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public E find(E entidade) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<E> findAll(E entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public E save(E entidade) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove(E entidade) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<E> listAll(Class<? extends ITEntity> entidade) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<E> pageAll(E entidade, int firstResult, int maxResult, boolean orderByAsc) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<E> findAll(E entity, int firstResult, int maxResult, boolean orderByAsc, boolean containsAnyKeyWords)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer countFindAll(E entity, boolean containsAnyKeyWords) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long countAll(Class<? extends ITEntity> entidade) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
