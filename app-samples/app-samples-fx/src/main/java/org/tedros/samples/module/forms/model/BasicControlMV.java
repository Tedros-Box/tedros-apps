/**
 * 
 */
package org.tedros.samples.module.forms.model;

import java.awt.Color;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TBigDecimalField;
import org.tedros.fx.annotation.control.TBigIntegerField;
import org.tedros.fx.annotation.control.TCheckBoxField;
import org.tedros.fx.annotation.control.TColorPickerField;
import org.tedros.fx.annotation.control.TDatePickerField;
import org.tedros.fx.annotation.control.TDoubleField;
import org.tedros.fx.annotation.control.TFieldBox;
import org.tedros.fx.annotation.control.THorizontalRadioGroup;
import org.tedros.fx.annotation.control.TIntegerField;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TLongField;
import org.tedros.fx.annotation.control.TMaskField;
import org.tedros.fx.annotation.control.TNumberSpinnerField;
import org.tedros.fx.annotation.control.TPasswordField;
import org.tedros.fx.annotation.control.TRadioButton;
import org.tedros.fx.annotation.control.TSliderField;
import org.tedros.fx.annotation.control.TTextAreaField;
import org.tedros.fx.annotation.control.TTextField;
import org.tedros.fx.annotation.control.TVerticalRadioGroup;
import org.tedros.fx.annotation.layout.TFlowPane;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.scene.control.TLabeled;
import org.tedros.fx.annotation.text.TText;
import org.tedros.fx.builder.DateTimeFormatBuilder;
import org.tedros.fx.control.TText.TTextStyle;
import org.tedros.fx.domain.TLabelPosition;
import org.tedros.fx.domain.TValidateNumber;
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
@TPresenter(decorator=@TDecorator(type=TViewDecorator.class, 
viewTitle="Basic components"),
behavior=@TBehavior(type=TViewBehavior.class))
public class BasicControlMV extends TModelView<FieldModel> {
	
	@TText(textStyle = TTextStyle.LARGE, text="Samples of Field Components")
	@TFieldBox(node=@TNode(parse = true, id=TFieldBox.TITLE))
	private SimpleStringProperty header;
	
	@TLabel(text="Text Field")
	@TTextField
	@TFlowPane(hgap=20,
			pane=@TPane(children={"textField","maskField","passwordField",
			"integerField","longField","doubleField",
			"bigIntegerField", "bigDecimalField","numberSpinnerField"}))
	private SimpleStringProperty textField;
	
	@TLabel(text="Mask Field (Postal Code)")
	@TMaskField(mask = "9999-999")
	private SimpleStringProperty maskField;
	
	@TLabel(text="Password Field")
	@TPasswordField(required=true)
	private SimpleStringProperty passwordField;
	
	@TLabel(text="Integer Field")
	@TIntegerField(validate=TValidateNumber.GREATHER_THAN_ZERO)
	private SimpleIntegerProperty integerField;
	
	@TLabel(text="Long Field")
	@TLongField
	private SimpleLongProperty longField;
	
	@TLabel(text="Double Field")
	@TDoubleField
	private SimpleDoubleProperty doubleField;
	
	@TLabel(text="Big Integer Field")
	@TBigIntegerField
	private SimpleObjectProperty<BigInteger> bigIntegerField;
	
	@TLabel(text="Big Decimal Field")
	@TBigDecimalField
	private SimpleObjectProperty<BigDecimal> bigDecimalField;

	@TLabel(text="Number Spinner Field")
	@TNumberSpinnerField(maxValue = 12)
	private SimpleLongProperty numberSpinnerField;
	
	@TLabel(text="Slider Field")
	@TSliderField(max = 500, min = 0, 
	showTickLabels=true, showTickMarks=true, snapToTicks=true,
	blockIncrement=1, majorTickUnit=175, minorTickCount=25)
	private SimpleDoubleProperty sliderField;
	
	@TLabel(text="Textarea Field")
	@TTextAreaField(wrapText=true, prefRowCount=6)
	private SimpleStringProperty textAreaField;
	
	@TLabel(text="Date Picker Field")
	@TDatePickerField
	@TFlowPane(hgap=20,
	pane=@TPane(children={"dateField","dateTimeField","colorField",
	"horRadioButtonField","verRadioButtonField","checkBoxField"}))
	private SimpleObjectProperty<Date> dateField;
	
	@TLabel(text="Date Time Picker Field")
	@TDatePickerField(
		dateFormat=DateTimeFormatBuilder.class)
	private SimpleObjectProperty<Date> dateTimeField;
	

	@TLabel(text="Color Picker Field")
	@TColorPickerField
	private SimpleObjectProperty<Color> colorField;
	
	
	//private SimpleStringProperty htmlField;
	
	@TLabel(text="Horizontal Radio Group ("+TUsualKey.SEX+")")
	@THorizontalRadioGroup(radioButtons = { 
			@TRadioButton(text = TUsualKey.MASCULINE, userData = "male"),
			@TRadioButton(text = TUsualKey.FEMININE, userData = "female")
			})
	private SimpleStringProperty horRadioButtonField;
	
	@TLabel(text="Vertical Radio Group ("+TUsualKey.SEX+")")
	@TVerticalRadioGroup(radioButtons = { 
			@TRadioButton(text = TUsualKey.MASCULINE, userData = "male"),
			@TRadioButton(text = TUsualKey.FEMININE, userData = "female")
			})
	private SimpleStringProperty verRadioButtonField;
	
	@TLabel(text="Checkbox Field: ", position=TLabelPosition.LEFT)
	@TCheckBoxField(labeled = @TLabeled(parse = true, text=TUsualKey.ACTIVE))
	private SimpleObjectProperty<Boolean> checkBoxField;

	public BasicControlMV(FieldModel model) {
		super(model);
	}

}
