/**
 * 
 */
package org.tedros.person.module.category.trigger;

import java.util.HashSet;
import java.util.List;

import org.tedros.fx.control.trigger.TTrigger;
import org.tedros.fx.form.TFieldBox;
import org.tedros.person.model.Person;
import org.tedros.person.model.PersonCategory;
import org.tedros.person.module.category.model.CategoryMV;
import org.tedros.person.table.PersonTV;

/**
 * @author Davis Gordon
 *
 */
public class SetCategoryTrigger extends TTrigger<List<PersonTV>> {

	public SetCategoryTrigger(TFieldBox source, TFieldBox target) {
		super(source, target);
	}

	@Override
	public void run(TEvent event, List<PersonTV> lst, List<PersonTV> old) {
		CategoryMV mv = (CategoryMV) super.getForm().gettModelView();
		PersonCategory e = mv.getEntity();
		
		if(event!=null && event.equals(TEvent.ADDED) && lst!=null) {
			lst.forEach(m->{
				Person p = m.getEntity();
				if(p.getCategories()==null)
					p.setCategories(new HashSet<>());
				if(!p.getCategories().stream()
						.filter(c->e.isNew() && e==c || e.getId().equals(c.getId()))
						.findAny().isPresent())
					p.getCategories().add(e);
			});
		}
	}

}
