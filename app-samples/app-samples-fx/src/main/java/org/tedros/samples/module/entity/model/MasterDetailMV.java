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
import org.tedros.extension.module.doc.trigger.DocumentTrigger;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TAutoCompleteEntity;
import org.tedros.fx.annotation.control.TDetailField;
import org.tedros.fx.annotation.control.TDetailListField;
import org.tedros.fx.annotation.control.TEditEntityModal;
import org.tedros.fx.annotation.control.TFieldBox;
import org.tedros.fx.annotation.control.TGenericType;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TSelectImageField;
import org.tedros.fx.annotation.control.TTab;
import org.tedros.fx.annotation.control.TTabPane;
import org.tedros.fx.annotation.control.TTextField;
import org.tedros.fx.annotation.control.TTrigger;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.layout.TFlowPane;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TListViewPresenter;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.process.TEjbService;
import org.tedros.fx.annotation.query.TCondition;
import org.tedros.fx.annotation.query.TQuery;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.scene.layout.TRegion;
import org.tedros.fx.annotation.text.TText;
import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.control.TText.TTextStyle;
import org.tedros.fx.domain.TEnvironment;
import org.tedros.fx.model.TEntityModelView;
import org.tedros.person.ejb.controller.IPersonController;
import org.tedros.person.model.NaturalPerson;
import org.tedros.sample.domain.DomainApp;
import org.tedros.sample.ejb.controller.ISampleAController;
import org.tedros.sample.entity.SampleA;
import org.tedros.sample.entity.SampleB;
import org.tedros.sample.entity.SampleC;
import org.tedros.samples.SmplsKey;
import org.tedros.samples.module.entity.trigger.SampleBTrigger;
import org.tedros.samples.start.TConstant;
import org.tedros.server.model.ITFileBaseModel;
import org.tedros.server.query.TCompareOp;
import org.tedros.server.query.TLogicOp;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;

/**
 * @author Davis Gordon
 *
 */

@TForm(showBreadcrumBar=false, scroll=false)
@TEjbService(serviceName = ISampleAController.JNDI_NAME, model=SampleA.class)
@TListViewPresenter(
	presenter=@TPresenter(
		decorator = @TDecorator(viewTitle="Edit a master/detail entity")))
@TSecurity(id=DomainApp.SAMPLE_A_FORM_ID, appName = SmplsKey.APP_SAMPLES,
	moduleName = "Samples", viewName = "Edit a master/detail entity",
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class MasterDetailMV extends TEntityModelView<SampleA> {


	@TText(textStyle = TTextStyle.LARGE, text="Master/Detail")
	@TFieldBox(alignment=Pos.CENTER_LEFT,
	node=@TNode(id=TFieldBox.TITLE, parse = true))
	@TTabPane(tabs = { 
		@TTab(fields = { "header","name","text0","detail","text1","contacts"}, 
			text = "Detail field and modal"),
		@TTab(fields = { "details" }, text = "Detail list"),
		@TTab(fields = { "image" }, text = "Image")})
	private SimpleStringProperty header;
	
	@TLabel(text=TUsualKey.NAME)
	@TTextField(required=true, 
		node=@TNode(parse = true, requestFocus=true))
	@TFlowPane(hgap=20,
	pane=@TPane(children={"name","person"}))
	private SimpleStringProperty name;
	
	@TLabel(text=TUsualKey.NATURAL_PERSON)
	@TAutoCompleteEntity(
		showMaxItems=30,
		service = IPersonController.JNDI_NAME,
		query = @TQuery(entity = NaturalPerson.class, 
			condition = {
				@TCondition(field = "name", operator=TCompareOp.LIKE),
				@TCondition(logicOp=TLogicOp.OR, field = "lastName", 
				operator=TCompareOp.LIKE)}))
	private SimpleObjectProperty<NaturalPerson> person;
	
	@TText(textStyle = TTextStyle.LARGE, text="Edit an entity detail field")
	@TFieldBox(alignment=Pos.CENTER_LEFT,
	node=@TNode(id=TFieldBox.TITLE, parse = true))
	private SimpleStringProperty text0;
	
	@TLabel(text="Detail field", show=false)
	@TFieldBox(node=@TNode(id="field", parse = true))
	@TDetailField(model = SampleC.class, modelView = SampleCDetailMV.class)
	@TGenericType(model=SampleC.class, modelView=SampleCDetailMV.class)
	private SimpleObjectProperty<SampleCDetailMV> detail;
	
	@TLabel(text=TUsualKey.DETAILS, show=false)
	@TFieldBox(node=@TNode(id="list", parse = true))
	@TTrigger(type = SampleBTrigger.class)
	@TDetailListField(region=@TRegion(maxHeight=400, parse = true),
	modelView = SampleBDetailMV.class, entity = SampleB.class)
	@TGenericType(model=SampleB.class, modelView=SampleBDetailMV.class)
	private ITObservableList<SampleBDetailMV> details;
	
	@TText(textStyle = TTextStyle.LARGE, text="Edit entities using a modal")
	@TFieldBox(alignment=Pos.CENTER_LEFT,
	node=@TNode(id=TFieldBox.TITLE, parse = true))
	private SimpleStringProperty text1;
	
	@TLabel(text=TUsualKey.CONTACTS)
	@TEditEntityModal(height=70,
	modalHeight=400, modalWidth=600,
	model = Contact.class, modelView=ContactMV.class)
	@TGenericType(model = Contact.class, modelView=ContactMV.class)
	@TFlowPane(hgap=20,
	pane=@TPane(children={"contacts","documents"}))
	protected ITObservableList<ContactMV> contacts;
	
	@TLabel(text=TUsualKey.DOCUMENTS)
	@TEditEntityModal(height=70,
	modalHeight=490, modalWidth=700,
	model = Document.class, modelView=ModalDocumentMV.class)
	@TTrigger(type = DocumentTrigger.class)
	@TGenericType(model=Document.class, modelView=ModalDocumentMV.class)
	protected ITObservableList<ModalDocumentMV> documents;

	 @TSelectImageField(remoteOwner=TConstant.UUI,
		source=TEnvironment.LOCAL, 
		target=TEnvironment.REMOTE)
	@TFieldBox(node=@TNode(id="img", parse = true))
	private SimpleObjectProperty<ITFileBaseModel> image;
	
	public MasterDetailMV(SampleA entity) {
		super(entity);
		super.formatToString("%s", name);
	}

}
