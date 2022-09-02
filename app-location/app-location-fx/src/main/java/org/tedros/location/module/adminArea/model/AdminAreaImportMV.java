/**
 * 
 */
package org.tedros.location.module.adminArea.model;

import org.tedros.common.model.TFileEntity;
import org.tedros.ejb.controller.IAdminAreaImportController;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TFieldBox;
import org.tedros.fx.annotation.control.TFileField;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.effect.TDropShadow;
import org.tedros.fx.annotation.effect.TEffect;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.process.TEjbService;
import org.tedros.fx.annotation.reader.TReaderHtml;
import org.tedros.fx.annotation.reader.TTextReaderHtml;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.text.TText;
import org.tedros.fx.control.TText.TTextStyle;
import org.tedros.fx.domain.TFileExtension;
import org.tedros.fx.domain.TFileModelType;
import org.tedros.fx.domain.THtmlConstant;
import org.tedros.fx.domain.TStyleParameter;
import org.tedros.fx.presenter.modal.behavior.TImportFileModalBehavior;
import org.tedros.fx.presenter.modal.decorator.TImportFileModalDecorator;
import org.tedros.fx.presenter.model.TImportModelView;
import org.tedros.fx.property.TSimpleFileProperty;
import org.tedros.location.LocatKey;
import org.tedros.location.model.AdminArea;
import org.tedros.location.model.AdminAreaImport;
import org.tedros.location.start.TConstant;

import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.text.TextAlignment;

/**
 * @author Davis Gordon
 *
 */
@TEjbService(serviceName = IAdminAreaImportController.JNDI_NAME, model=AdminAreaImport.class)
@TPresenter(decorator = @TDecorator(type=TImportFileModalDecorator.class, viewTitle=LocatKey.VIEW_ADMIN_AREA_IMP),
			behavior = @TBehavior(type=TImportFileModalBehavior.class, 
			importedEntityClass=AdminArea.class, importedModelViewClass=AdminAreaMV.class))
public class AdminAreaImportMV extends TImportModelView<AdminAreaImport> {

	@TTextReaderHtml(text=LocatKey.IMPORT_TEXT, 
		htmlTemplateForControlValue="<h2 id='"+THtmlConstant.ID+"' name='"+THtmlConstant.NAME+"' style='"+THtmlConstant.STYLE+"'>"+THtmlConstant.CONTENT+"</h2>",
		cssForControlValue="width:100%; padding:8px; background-color: "+TStyleParameter.PANEL_BACKGROUND_COLOR+";",
		cssForHtmlBox="", cssForContentValue="color:"+TStyleParameter.PANEL_TEXT_COLOR+";")
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-form", 
			effect=@TEffect(dropShadow=@TDropShadow, parse=true), parse = true))
	@TText(text=LocatKey.IMPORT_UP_FILE, textAlignment=TextAlignment.LEFT, 
			textStyle = TTextStyle.LARGE)
	private SimpleStringProperty texto;
	
	@TReaderHtml
	private SimpleStringProperty rules;
	
	@TLabel(text=TUsualKey.FILE)
	@TFileField(propertyValueType=TFileModelType.ENTITY, 
	initialDirectory=TFileField.TEDROS_MODULE+TConstant.UUI,
	extensions= {TFileExtension.CSV}, moreExtensions= {"*.xls", "*.xlsx"},
	showFilePath=true, required=true)
	private TSimpleFileProperty<TFileEntity> file;
	
	public AdminAreaImportMV(AdminAreaImport model) {
		super(model);
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