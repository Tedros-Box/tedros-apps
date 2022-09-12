/**
 * 
 */
package org.tedros.person.model;

import java.util.Date;
import java.util.Locale;

import org.tedros.core.TLanguage;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TConverter;
import org.tedros.fx.annotation.control.TDatePickerField;
import org.tedros.fx.annotation.control.THorizontalRadioGroup;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TRadioButton;
import org.tedros.fx.annotation.control.TTextField;
import org.tedros.fx.annotation.layout.THBox;
import org.tedros.fx.annotation.layout.THGrow;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.layout.TPriority;
import org.tedros.person.converter.CivilStatusConverter;
import org.tedros.person.converter.GenderConverter;
import org.tedros.person.converter.SexConverter;
import org.tedros.person.domain.CivilStatus;
import org.tedros.person.domain.Gender;
import org.tedros.person.domain.Sex;
import org.tedros.person.model.NaturalPerson;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */
public class NaturalPersonMV<P extends NaturalPerson> extends PersonMV<P> {

	@TLabel(text=TUsualKey.LAST_NAME)
	@TTextField(maxLength=60)
	@THBox(	pane=@TPane(children={"name", "lastName", "birthDate"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="name", priority=Priority.ALWAYS), 
			@TPriority(field="lastName", priority=Priority.ALWAYS), 
			@TPriority(field="birthDate", priority=Priority.SOMETIMES)}))
	protected SimpleStringProperty lastName;
	
	@TLabel(text=TUsualKey.BIRTHDATE)
	@TDatePickerField
	protected SimpleObjectProperty<Date> birthDate;
	
	@TLabel(text=TUsualKey.SEX)
	@THorizontalRadioGroup(spacing= 10,
		converter=@TConverter(parse = true, type = SexConverter.class),
		radioButtons = { @TRadioButton(text = TUsualKey.FEMININE, userData = TUsualKey.FEMININE ),
				@TRadioButton(text = TUsualKey.MASCULINE, userData = TUsualKey.MASCULINE )
		})
	@THBox(	pane=@TPane(children={"sex", "gender", "civilStatus"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="sex", priority=Priority.ALWAYS), 
			@TPriority(field="gender", priority=Priority.ALWAYS), 
			@TPriority(field="civilStatus", priority=Priority.ALWAYS)}))
	protected SimpleObjectProperty<Sex> sex;
	
	@TLabel(text=TUsualKey.GENDER)
	@THorizontalRadioGroup(spacing= 10,
		converter=@TConverter(parse = true, type = GenderConverter.class),
		radioButtons = { @TRadioButton(text = TUsualKey.FEMININE, userData = TUsualKey.FEMININE),
				@TRadioButton(text = TUsualKey.MASCULINE, userData = TUsualKey.MASCULINE),
				@TRadioButton(text = TUsualKey.NEUTER, userData = TUsualKey.NEUTER),
				@TRadioButton(text = TUsualKey.COMMON, userData = TUsualKey.COMMON)
		})
	protected SimpleObjectProperty<Gender> gender;

	@TLabel(text=TUsualKey.CIVIL_STATUS)
	@THorizontalRadioGroup(spacing= 10,
		converter=@TConverter(parse = true, type = CivilStatusConverter.class),
		radioButtons = { @TRadioButton(text = TUsualKey.SINGLE, userData = TUsualKey.SINGLE),
				@TRadioButton(text = TUsualKey.MARRIED, userData = TUsualKey.MARRIED),
				@TRadioButton(text = TUsualKey.SEPARATED, userData = TUsualKey.SEPARATED),
				@TRadioButton(text = TUsualKey.WIDOWED, userData = TUsualKey.WIDOWED)
		})
	protected SimpleObjectProperty<CivilStatus> civilStatus;
	
	public NaturalPersonMV(P entity) {
		super(entity);
		if(TLanguage.getLocale().equals(Locale.ENGLISH))
			super.formatToString("%s, %s", lastName, name);
		else
			super.formatToString("%s %s", name, lastName);
	}

	public SimpleStringProperty getLastName() {
		return lastName;
	}

	public void setLastName(SimpleStringProperty lastName) {
		this.lastName = lastName;
	}

	public SimpleObjectProperty<Date> getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(SimpleObjectProperty<Date> birthDate) {
		this.birthDate = birthDate;
	}

	public SimpleObjectProperty<Sex> getSex() {
		return sex;
	}

	public void setSex(SimpleObjectProperty<Sex> sex) {
		this.sex = sex;
	}

	public SimpleObjectProperty<Gender> getGender() {
		return gender;
	}

	public void setGender(SimpleObjectProperty<Gender> gender) {
		this.gender = gender;
	}

	public SimpleObjectProperty<CivilStatus> getCivilStatus() {
		return civilStatus;
	}

	public void setCivilStatus(SimpleObjectProperty<CivilStatus> civilStatus) {
		this.civilStatus = civilStatus;
	}

}
