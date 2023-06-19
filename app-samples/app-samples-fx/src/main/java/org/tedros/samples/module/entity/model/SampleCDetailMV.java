/**
 * 
 */
package org.tedros.samples.module.entity.model;

import java.util.Date;

import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TDatePickerField;
import org.tedros.fx.annotation.control.TIntegerField;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TTextField;
import org.tedros.fx.annotation.layout.TFlowPane;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.domain.TValidateNumber;
import org.tedros.fx.model.TEntityModelView;
import org.tedros.sample.entity.SampleC;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Davis Gordon
 *
 */
public class SampleCDetailMV extends TEntityModelView<SampleC> {

	@TLabel(text=TUsualKey.NAME)
	@TTextField
	@TFlowPane(hgap=20,
	pane=@TPane(children={"name","date","total"}))
	private SimpleStringProperty name;
	
	@TLabel(text=TUsualKey.DATE)
	@TDatePickerField
	private SimpleObjectProperty<Date> date;
	
	@TLabel(text=TUsualKey.AMOUNT)
	@TIntegerField(validate=TValidateNumber.GREATHER_THAN_ZERO)
	private SimpleIntegerProperty total;
	
	
	public SampleCDetailMV(SampleC entity) {
		super(entity);
		super.formatToString("%s", name);
	}

}
