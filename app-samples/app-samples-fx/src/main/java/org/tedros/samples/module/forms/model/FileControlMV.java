/**
 * 
 */
package org.tedros.samples.module.forms.model;

import java.io.File;

import org.tedros.fx.annotation.control.TDirectoryField;
import org.tedros.fx.annotation.control.TFieldBox;
import org.tedros.fx.annotation.control.TFileField;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TSelectImageField;
import org.tedros.fx.annotation.layout.TFlowPane;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.text.TText;
import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.control.TText.TTextStyle;
import org.tedros.fx.domain.TEnvironment;
import org.tedros.fx.domain.TFileModelType;
import org.tedros.fx.model.TModelView;
import org.tedros.fx.presenter.model.behavior.TViewBehavior;
import org.tedros.fx.presenter.model.decorator.TViewDecorator;
import org.tedros.fx.property.TSimpleFileProperty;
import org.tedros.server.model.ITFileBaseModel;
import org.tedros.server.model.TFileModel;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Davis Gordon
 *
 */
@TPresenter(decorator=@TDecorator(type=TViewDecorator.class, 
viewTitle="File components"),
behavior=@TBehavior(type=TViewBehavior.class))
public class FileControlMV extends TModelView<FieldModel> {
	
	@TText(textStyle = TTextStyle.LARGE, text="Samples of file components")
	@TFieldBox(node=@TNode(parse = true, id=TFieldBox.TITLE))
	private SimpleStringProperty header;
	
	@TLabel(text="Select folder")
	@TDirectoryField
	@TFlowPane(hgap=20,
	pane=@TPane(children={"dirField","fileField"}))
	private SimpleObjectProperty<File> dirField;

	@TLabel(text="Select file")
	@TFileField(propertyValueType=TFileModelType.MODEL)
	private TSimpleFileProperty<TFileModel> fileField;
	
	 @TSelectImageField(source=TEnvironment.LOCAL, 
		target=TEnvironment.LOCAL)
	private ITObservableList<ITFileBaseModel> selectImgField;

	public FileControlMV(FieldModel model) {
		super(model);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
