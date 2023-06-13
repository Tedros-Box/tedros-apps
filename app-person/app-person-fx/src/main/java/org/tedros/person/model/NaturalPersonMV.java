/**
 * 
 */
package org.tedros.person.model;

import java.util.Date;
import java.util.Locale;

import org.tedros.core.TLanguage;
import org.tedros.core.controller.TUserController;
import org.tedros.core.security.model.TUser;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TAutoCompleteEntity;
import org.tedros.fx.annotation.control.TConverter;
import org.tedros.fx.annotation.control.TDatePickerField;
import org.tedros.fx.annotation.control.THRadioGroup;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TRadio;
import org.tedros.fx.annotation.control.TTextField;
import org.tedros.fx.annotation.control.TTrigger;
import org.tedros.fx.annotation.layout.THBox;
import org.tedros.fx.annotation.layout.THGrow;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.layout.TPriority;
import org.tedros.fx.annotation.query.TCondition;
import org.tedros.fx.annotation.query.TQuery;
import org.tedros.person.converter.CivilStatusConverter;
import org.tedros.person.converter.GenderConverter;
import org.tedros.person.converter.SexConverter;
import org.tedros.person.domain.CivilStatus;
import org.tedros.person.domain.Gender;
import org.tedros.person.domain.Sex;
import org.tedros.person.trigger.TedrosUserTrigger;
import org.tedros.server.query.TCompareOp;
import org.tedros.server.query.TLogicOp;

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
	@THBox(	pane=@TPane(children={"name", "lastName", "birthDate", "tedrosUser"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="name", priority=Priority.ALWAYS), 
			@TPriority(field="lastName", priority=Priority.ALWAYS), 
			@TPriority(field="birthDate", priority=Priority.SOMETIMES), 
			@TPriority(field="tedrosUser", priority=Priority.SOMETIMES)}))
	protected SimpleStringProperty lastName;
	
	@TLabel(text=TUsualKey.BIRTHDATE)
	@TDatePickerField
	protected SimpleObjectProperty<Date> birthDate;

	@TLabel(text=TUsualKey.USER + " Tedros")
	@TAutoCompleteEntity(
			service = TUserController.JNDI_NAME,
			query = @TQuery(entity = TUser.class, 
			condition = {
					@TCondition(field = "name", operator=TCompareOp.LIKE), 
					@TCondition(logicOp=TLogicOp.OR, field = "login", operator=TCompareOp.LIKE)}))
	@TTrigger(triggerClass = TedrosUserTrigger.class, runAfterFormBuild=true)
	private SimpleObjectProperty<TUser> tedrosUser;
	
	@TLabel(text=TUsualKey.SEX)
	@THRadioGroup(spacing= 10,
		converter=@TConverter(parse = true, type = SexConverter.class),
		radio = { @TRadio(text = TUsualKey.FEMININE, userData = TUsualKey.FEMININE ),
				@TRadio(text = TUsualKey.MASCULINE, userData = TUsualKey.MASCULINE )
		})
	@THBox(	pane=@TPane(children={"sex", "gender", "civilStatus"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="sex", priority=Priority.ALWAYS), 
			@TPriority(field="gender", priority=Priority.ALWAYS), 
			@TPriority(field="civilStatus", priority=Priority.ALWAYS)}))
	protected SimpleObjectProperty<Sex> sex;
	
	@TLabel(text=TUsualKey.GENDER)
	@THRadioGroup(spacing= 10,
		converter=@TConverter(parse = true, type = GenderConverter.class),
		radio = { @TRadio(text = TUsualKey.FEMININE, userData = TUsualKey.FEMININE),
				@TRadio(text = TUsualKey.MASCULINE, userData = TUsualKey.MASCULINE),
				@TRadio(text = TUsualKey.NEUTER, userData = TUsualKey.NEUTER),
				@TRadio(text = TUsualKey.COMMON, userData = TUsualKey.COMMON)
		})
	protected SimpleObjectProperty<Gender> gender;

	@TLabel(text=TUsualKey.CIVIL_STATUS)
	@THRadioGroup(spacing= 10,
		converter=@TConverter(parse = true, type = CivilStatusConverter.class),
		radio = { @TRadio(text = TUsualKey.SINGLE, userData = TUsualKey.SINGLE),
				@TRadio(text = TUsualKey.MARRIED, userData = TUsualKey.MARRIED),
				@TRadio(text = TUsualKey.SEPARATED, userData = TUsualKey.SEPARATED),
				@TRadio(text = TUsualKey.WIDOWED, userData = TUsualKey.WIDOWED)
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

	public SimpleObjectProperty<TUser> getTedrosUser() {
		return tedrosUser;
	}

	public void setTedrosUser(SimpleObjectProperty<TUser> tedrosUser) {
		this.tedrosUser = tedrosUser;
	}

}
