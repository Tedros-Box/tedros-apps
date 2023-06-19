/**
 * 
 */
package org.tedros.samples.module.entity.model;

import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.extension.model.Contact;
import org.tedros.extension.model.ContactMV;
import org.tedros.extension.model.Document;
import org.tedros.extension.model.ModalDocumentMV;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TDetailField;
import org.tedros.fx.annotation.control.TDetailListField;
import org.tedros.fx.annotation.control.TEditEntityModal;
import org.tedros.fx.annotation.control.TFieldBox;
import org.tedros.fx.annotation.control.TGenericType;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TTab;
import org.tedros.fx.annotation.control.TTabPane;
import org.tedros.fx.annotation.control.TTextField;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.layout.TFlowPane;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TListViewPresenter;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.process.TEjbService;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.scene.layout.TRegion;
import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.model.TEntityModelView;
import org.tedros.sample.domain.DomainApp;
import org.tedros.sample.ejb.controller.ISampleAController;
import org.tedros.sample.entity.SampleA;
import org.tedros.sample.entity.SampleB;
import org.tedros.sample.entity.SampleC;
import org.tedros.samples.SmplsKey;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Davis Gordon
 *
 */

@TForm(header = "", showBreadcrumBar=false, scroll=false)
@TEjbService(serviceName = ISampleAController.JNDI_NAME, model=SampleA.class)
@TListViewPresenter(
	presenter=@TPresenter(
		decorator = @TDecorator(viewTitle="Edit a master/detail entity")))
@TSecurity(id=DomainApp.SAMPLE_A_FORM_ID, appName = SmplsKey.APP_SAMPLES,
	moduleName = "Samples", viewName = "Edit a master/detail entity",
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class MasterDetailMV extends TEntityModelView<SampleA> {

	@TLabel(text=TUsualKey.NAME)
	@TTextField(required=true)
	@TTabPane(tabs = { 
			@TTab(fields = { "name","detail","contacts"}, text = "A Samples"),
			@TTab(fields = { "details" }, text = "B Samples")})
	private SimpleStringProperty name;

	@TLabel(text="Detail SampleC")
	@TDetailField(model = SampleC.class, modelView = SampleCDetailMV.class)
	@TGenericType(model=SampleC.class, modelView=SampleCDetailMV.class)
	private SimpleObjectProperty<SampleCDetailMV> detail;
	
	@TLabel(text=TUsualKey.DETAILS, show=false)
	@TFieldBox(node=@TNode(id="", parse = true))
	@TDetailListField(region=@TRegion(maxHeight=400, parse = true),
	modelView = SampleBDetailMV.class, entity = SampleB.class)
	@TGenericType(model=SampleB.class, modelView=SampleBDetailMV.class)
	private ITObservableList<SampleBDetailMV> details;

	@TLabel(text=TUsualKey.CONTACTS)
	@TEditEntityModal(modalHeight=400, modalWidth=600,
	model = Contact.class, modelView=ContactMV.class)
	@TGenericType(model = Contact.class, modelView=ContactMV.class)
	@TFlowPane(hgap=20,
	pane=@TPane(children={"contacts","documents"}))
	protected ITObservableList<ContactMV> contacts;
	
	@TLabel(text=TUsualKey.DOCUMENTS)
	@TEditEntityModal(modalHeight=490, modalWidth=700,
	model = Document.class, modelView=ModalDocumentMV.class)
	@TGenericType(model=Document.class, modelView=ModalDocumentMV.class)
	protected ITObservableList<ModalDocumentMV> documents;

	
	public MasterDetailMV(SampleA entity) {
		super(entity);
		super.formatToString("%s", name);
	}

}
