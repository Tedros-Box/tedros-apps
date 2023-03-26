/**
 * 
 */
package org.tedros.person.model;

import java.util.Date;

import org.tedros.fx.TFxKey;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TCallbackFactory;
import org.tedros.fx.annotation.control.TCellFactory;
import org.tedros.fx.annotation.control.TCellValueFactory;
import org.tedros.fx.annotation.control.TComboBoxField;
import org.tedros.fx.annotation.control.TDatePickerField;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TModelViewType;
import org.tedros.fx.annotation.control.TOptionsList;
import org.tedros.fx.annotation.control.TTableColumn;
import org.tedros.fx.annotation.control.TTableView;
import org.tedros.fx.annotation.control.TTextField;
import org.tedros.fx.annotation.layout.THBox;
import org.tedros.fx.annotation.layout.THGrow;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.layout.TPriority;
import org.tedros.fx.annotation.scene.control.TControl;
import org.tedros.fx.builder.TEditModelRowFactoryCallBackBuilder;
import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.control.tablecell.TShortDateCallback;
import org.tedros.person.ejb.controller.IPersonTypeController;
import org.tedros.person.module.company.table.EmployeeITemMV;
import org.tedros.person.module.company.table.StaffTypeCellCallBack;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */
public class LegalPersonMV<E extends LegalPerson> extends PersonMV<E> {

	@TLabel(text=TUsualKey.TRADE_NAME)
	@TTextField(maxLength=60)
	@THBox(	pane=@TPane(children={"name", "otherName"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="name", priority=Priority.ALWAYS), 
			@TPriority(field="otherName", priority=Priority.ALWAYS)}))
	protected SimpleStringProperty otherName;
	
	@TLabel(text=TUsualKey.TYPE)
	@TComboBoxField(firstItemText=TUsualKey.SELECT,
	optionsList=@TOptionsList(serviceName = IPersonTypeController.JNDI_NAME, 
	optionModelViewClass=LegalTypeMV.class,
	entityClass=LegalType.class))
	@THBox(	 spacing=10, fillHeight=true,
	pane=@TPane(children={"type", "startActivities", "endActivities"}),
	hgrow=@THGrow(priority={@TPriority(field="type", priority=Priority.ALWAYS), 
			@TPriority(field="startActivities", priority=Priority.ALWAYS), 
			@TPriority(field="endActivities", priority=Priority.ALWAYS)}))
	protected SimpleObjectProperty<LegalType> type;
	
	@TLabel(text=TUsualKey.START_ACTIVITIES)
	@TDatePickerField
	protected SimpleObjectProperty<Date> startActivities;
	
	@TLabel(text=TUsualKey.END_ACTIVITIES)
	@TDatePickerField
	protected SimpleObjectProperty<Date> endActivities;
	
	@TTableView(editable=true, rowFactory=TEditModelRowFactoryCallBackBuilder.class,
		control=@TControl(tooltip=TFxKey.TABLE_MENU_TOOLTIP, parse = true),
		columns = { 
			@TTableColumn(cellValue="toStringProperty", text = TUsualKey.NAME, prefWidth=20, resizable=true), 
			@TTableColumn(cellValue="type", text = TUsualKey.OCCUPATION, resizable=true,
				cellValueFactory=@TCellValueFactory(parse=true, 
				value=@TCallbackFactory(parse=true, value=StaffTypeCellCallBack.class))), 
			@TTableColumn(cellValue="hiringDate", text = TUsualKey.HIRING_DATE, resizable=true,
				cellFactory=@TCellFactory(parse = true, 
					callBack=@TCallbackFactory(parse=true, value=TShortDateCallback.class))), 
			@TTableColumn(cellValue="resignationDate", text = TUsualKey.RESIGNATION_DATE, resizable=true,
				cellFactory=@TCellFactory(parse = true, 
					callBack=@TCallbackFactory(parse=true, value=TShortDateCallback.class))),
	})
	@TModelViewType(modelClass = Employee.class, modelViewClass=EmployeeITemMV.class)
	protected ITObservableList<EmployeeITemMV> staff;
	
	public LegalPersonMV(E entity) {
		super(entity);
	}
	
	public SimpleStringProperty getOtherName() {
		return otherName;
	}

	public void setOtherName(SimpleStringProperty otherName) {
		this.otherName = otherName;
	}

	public SimpleObjectProperty<LegalType> getType() {
		return type;
	}

	public void setType(SimpleObjectProperty<LegalType> type) {
		this.type = type;
	}

	public SimpleObjectProperty<Date> getStartActivities() {
		return startActivities;
	}

	public void setStartActivities(SimpleObjectProperty<Date> startActivities) {
		this.startActivities = startActivities;
	}

	public SimpleObjectProperty<Date> getEndActivities() {
		return endActivities;
	}

	public void setEndActivities(SimpleObjectProperty<Date> endActivities) {
		this.endActivities = endActivities;
	}

	public ITObservableList<EmployeeITemMV> getStaff() {
		return staff;
	}

	public void setStaff(ITObservableList<EmployeeITemMV> staff) {
		this.staff = staff;
	}
}
