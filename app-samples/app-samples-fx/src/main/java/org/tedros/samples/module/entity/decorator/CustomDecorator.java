/**
 * 
 */
package org.tedros.samples.module.entity.decorator;

import org.tedros.api.presenter.view.ITDynaView;
import org.tedros.fx.annotation.presenter.TListViewPresenter;
import org.tedros.fx.presenter.decorator.TListViewHelper;
import org.tedros.fx.presenter.entity.decorator.TMasterCrudViewDecorator;
import org.tedros.samples.module.entity.model.CustomDecoratorMV;

/**
 * @author Davis Gordon
 *
 */
public class CustomDecorator extends TMasterCrudViewDecorator<CustomDecoratorMV> {
	
	protected void configListView() {
		String title = tPresenter!=null ? tPresenter.decorator().listTitle() : null;
		// get the list view settings
		TListViewPresenter tAnnotation = getPresenter().getModelViewClass().getAnnotation(TListViewPresenter.class);
		if(tAnnotation!=null)
			helper = new TListViewHelper<>(title, tAnnotation.listViewMaxWidth(), tAnnotation.listViewMinWidth(), 
					tAnnotation.page(), tAnnotation.aiAssistant());
		else
			helper = new TListViewHelper<>(title, 250, 250, null);
		
		addItemInTRightContent(helper.gettListViewPane());
	}
	

    /* (non-Javadoc)
	 * @see org.tedros.fx.presenter.entity.decorator.ITListViewDecorator#isListContentVisible()
	 */
    @Override
	public boolean isListContentVisible() {
    	final ITDynaView<CustomDecoratorMV> view = getView();
		return view.gettRightContent().getChildren().contains(helper.gettListViewPane());
    }
    
	/* (non-Javadoc)
	 * @see org.tedros.fx.presenter.entity.decorator.ITListViewDecorator#hideListContent()
	 */
	@Override
	public void hideListContent() {
		final ITDynaView<CustomDecoratorMV> view = getView();
		view.gettRightContent().getChildren().remove(helper.gettListViewPane());
	}
	
	/* (non-Javadoc)
	 * @see org.tedros.fx.presenter.entity.decorator.ITListViewDecorator#showListContent()
	 */
	@Override
	public void showListContent() {
		addItemInTRightContent(helper.gettListViewPane());
		
	}
}
