/**
 * 
 */
package org.tedros.redminetools.server.cdi.bo;

import java.util.List;

import org.tedros.redminetools.model.TProject;
import org.tedros.redminetools.server.gateway.RedmineApiGateway;
import org.tedros.server.cdi.bo.ITGenericBO;
import org.tedros.server.cdi.eao.ITGenericEAO;
import org.tedros.server.entity.ITEntity;
import org.tedros.server.query.TSelect;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.Dependent;

/**
 * The CDI business object 
 * 
 * @author Davis Dun
 *
 */
@Dependent
public class RedmineProjectBO implements ITGenericBO<TProject> {
	
	private RedmineApiGateway gateway;
    
	@PostConstruct
   	void init() {
   		String redmineURI = "https://redmine.detran.go.gov.br/";
   	    String apiAccessKey = "559147fe2183d824e7784c2862e6e0b070cd6804";
   		this.gateway = new RedmineApiGateway(redmineURI, apiAccessKey);
   	}
    
	@Override
	public List<TProject> search(TSelect<TProject> sel) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<TProject> search(TSelect<TProject> sel, int firstResult, int maxResult) {
		// TODO Auto-generated method stub
		return this.gateway.listAllProjects();
	}
	@Override
	public Long countSearch(TSelect<TProject> sel) {
		return this.gateway.countAllProjects();
	}
	@Override
	public ITGenericEAO<TProject> getEao() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public TProject findById(TProject entidade) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public TProject find(TProject entidade) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<TProject> findAll(TProject entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public TProject save(TProject entidade) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void remove(TProject entidade) throws Exception {
		// TODO Auto-generated method stub
		
	}
	@Override
	public List<TProject> listAll(Class<? extends ITEntity> entidade) throws Exception {
		return this.gateway.listAllProjects();
	}
	@Override
	public List<TProject> pageAll(TProject entidade, int firstResult, int maxResult, boolean orderByAsc)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<TProject> findAll(TProject entity, int firstResult, int maxResult, boolean orderByAsc,
			boolean containsAnyKeyWords) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Integer countFindAll(TProject entity, boolean containsAnyKeyWords) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Long countAll(Class<? extends ITEntity> entidade) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
}
