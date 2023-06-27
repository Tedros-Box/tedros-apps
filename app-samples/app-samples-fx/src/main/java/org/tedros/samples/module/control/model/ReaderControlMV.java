/**
 * 
 */
package org.tedros.samples.module.control.model;

import java.util.Date;

import org.tedros.fx.annotation.control.TFieldBox;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TShowField;
import org.tedros.fx.annotation.control.TShowField.TField;
import org.tedros.fx.annotation.layout.TFieldSet;
import org.tedros.fx.annotation.layout.TFlowPane;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.scene.layout.TRegion;
import org.tedros.fx.annotation.text.TText;
import org.tedros.fx.control.TText.TTextStyle;
import org.tedros.fx.domain.TDateStyle;
import org.tedros.fx.domain.TLayoutType;
import org.tedros.fx.domain.TTimeStyle;
import org.tedros.fx.model.TModelView;
import org.tedros.fx.presenter.model.behavior.TViewBehavior;
import org.tedros.fx.presenter.model.decorator.TViewDecorator;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Davis Gordon
 *
 */
@TPresenter(model=FieldModel.class,
	decorator=@TDecorator(type=TViewDecorator.class, viewTitle="Reader components"),
	behavior=@TBehavior(type=TViewBehavior.class))
public class ReaderControlMV extends TModelView<FieldModel> {
	
	@TText(textStyle = TTextStyle.LARGE, text="Samples of readers components")
	@TFieldBox(node=@TNode(parse = true, id=TFieldBox.TITLE))
	private SimpleStringProperty header;
	
	@TLabel(text="Shows string0 field")
	@TShowField(fields=@TField(format="--> %s <--"))
	@TFlowPane(hgap=20,
	pane=@TPane(children={"string0","integer0","long0",
			"double0","date0"}))
	private SimpleStringProperty string0;

	@TLabel(text="Shows integer0 field")
	@TShowField(fields=@TField(mask="9999-99"))
	private SimpleIntegerProperty integer0;
	
	@TLabel(text="Shows double0 field")
	@TShowField(fields=@TField(format=" $ %(,.2f "))
	private SimpleDoubleProperty double0;
	
	@TLabel(text="Shows long0 field")
	@TShowField()
	private SimpleLongProperty long0;

	@TLabel(text="Shows date0 field")
	@TShowField(fields=@TField(dateStyle=TDateStyle.NONE, 
	timeStyle=TTimeStyle.FULL))
	private SimpleObjectProperty<Date> date0;

	@TFieldSet(fields = { "model0" }, 
		legend = "Showing the MasterModel fields",
		region=@TRegion(parse = true, minWidth=480, maxWidth=480, prefWidth=480))
	@TShowField(layout=TLayoutType.FLOWPANE,
		fields= {
			@TField(name="string0", label="Shows string0 field"),
			@TField(name="integer0", label="Shows integer0 field"),
			@TField(name="long0", label="Shows long0 field"),
			@TField(name="double0", label="Shows double0 field"),
			@TField(name="date0", label="Shows date0 field", timeStyle=TTimeStyle.SHORT),

			@TField(name="detail0.string0", label="Shows detail0.string0 field"),
			@TField(name="detail0.integer0", label="Shows detail0.integer0 field"),
			@TField(name="detail0.long0", label="Shows detail0.long0 field"),
			@TField(name="detail0.double0", label="Shows detail0.double0 field"),
			@TField(name="detail0.date0", label="Shows detail0.date0 field", 
			dateStyle=TDateStyle.FULL, timeStyle=TTimeStyle.FULL)
	})
	private SimpleObjectProperty<MasterModel> model0;

	public ReaderControlMV(FieldModel model) {
		super(model);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
