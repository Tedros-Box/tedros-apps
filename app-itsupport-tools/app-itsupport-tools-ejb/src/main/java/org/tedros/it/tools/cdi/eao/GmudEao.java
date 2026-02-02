package org.tedros.it.tools.cdi.eao;

import org.tedros.it.tools.entity.Gmud;
import org.tedros.server.cdi.eao.TGenericEAO;

public class GmudEao extends TGenericEAO<Gmud> {
	
	@Override
	public void beforePersist(Gmud entity) throws Exception {
		relateGmudChilds(entity);
	}
	
	@Override
	public void beforeMerge(Gmud entity) throws Exception {
		relateGmudChilds(entity);
	}
	
	private void relateGmudChilds(Gmud entity) {
		if(entity.getExecutionPlan()!=null) {
			entity.getExecutionPlan().stream().forEach(ep -> ep.setGmud(entity));
		}
		
		if(entity.getReviews()!=null) {
			entity.getReviews().stream().forEach(r -> r.setGmud(entity));
		}
	}

}
