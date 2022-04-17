/**
 * 
 */
package com.tedros.location.module.adminArea.model;

import com.tedros.common.model.TFileEntity;
import com.tedros.fxapi.annotation.control.TFieldBox;
import com.tedros.fxapi.annotation.control.TFileField;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.effect.TDropShadow;
import com.tedros.fxapi.annotation.effect.TEffect;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.process.TEjbService;
import com.tedros.fxapi.annotation.reader.TReaderHtml;
import com.tedros.fxapi.annotation.reader.TTextReaderHtml;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.text.TText;
import com.tedros.fxapi.control.TText.TTextStyle;
import com.tedros.fxapi.domain.TFileExtension;
import com.tedros.fxapi.domain.TFileModelType;
import com.tedros.fxapi.domain.THtmlConstant;
import com.tedros.fxapi.domain.TStyleParameter;
import com.tedros.fxapi.presenter.modal.behavior.TImportFileModalBehavior;
import com.tedros.fxapi.presenter.modal.decorator.TImportFileModalDecorator;
import com.tedros.fxapi.presenter.model.TImportModelView;
import com.tedros.fxapi.property.TSimpleFileProperty;
import com.tedros.location.model.AdminArea;
import com.tedros.location.model.AdminAreaImport;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.text.TextAlignment;

/**
 * @author Davis Gordon
 *
 */
@TEjbService(serviceName = "IAdminAreaImportControllerRemote", model=AdminAreaImport.class)
@TPresenter(decorator = @TDecorator(type=TImportFileModalDecorator.class, viewTitle="#{view.admin.area.imp}"),
			behavior = @TBehavior(type=TImportFileModalBehavior.class, 
			importedEntityClass=AdminArea.class, importedModelViewClass=AdminAreaMV.class))
public class AdminAreaImportMV extends TImportModelView<AdminAreaImport> {

	@TTextReaderHtml(text="#{import.text}", 
			htmlTemplateForControlValue="<h2 id='"+THtmlConstant.ID+"' name='"+THtmlConstant.NAME+"' style='"+THtmlConstant.STYLE+"'>"+THtmlConstant.CONTENT+"</h2>",
			cssForControlValue="width:100%; padding:8px; background-color: "+TStyleParameter.PANEL_BACKGROUND_COLOR+";",
			cssForHtmlBox="", cssForContentValue="color:"+TStyleParameter.PANEL_TEXT_COLOR+";")
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-form", effect=@TEffect(dropShadow=@TDropShadow, parse=true), parse = true))
	@TText(text="#{import.up.file}", textAlignment=TextAlignment.LEFT, 
			textStyle = TTextStyle.LARGE)
	private SimpleStringProperty texto;
	
	@TReaderHtml
	private SimpleStringProperty rules;
	
	@TLabel(text="#{label.file}")
	@TFileField(propertyValueType=TFileModelType.ENTITY, 
	extensions= {TFileExtension.CSV}, moreExtensions= {"*.xls", "*.xlsx"},
	showFilePath=true, required=true)
	private TSimpleFileProperty<TFileEntity> file;
	
	public AdminAreaImportMV(AdminAreaImport model) {
		super(model);
	}

	@Override
	public void setId(SimpleLongProperty id) {
		
	}

	@Override
	public SimpleLongProperty getId() {
		return null;
	}

	@Override
	public SimpleStringProperty getDisplayProperty() {
		return null;
	}

	/**
	 * @return the file
	 */
	public TSimpleFileProperty<TFileEntity> getFile() {
		return file;
	}

	/**
	 * @param file the file to set
	 */
	public void setFile(TSimpleFileProperty<TFileEntity> file) {
		this.file = file;
	}

	/**
	 * @return the rules
	 */
	public SimpleStringProperty getRules() {
		return rules;
	}

	/**
	 * @param rules the rules to set
	 */
	public void setRules(SimpleStringProperty rules) {
		this.rules = rules;
	}

	/**
	 * @return the texto
	 */
	public SimpleStringProperty getTexto() {
		return texto;
	}

	/**
	 * @param texto the texto to set
	 */
	public void setTexto(SimpleStringProperty texto) {
		this.texto = texto;
	}

}
