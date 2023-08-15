/**
 * 
 */
package org.tedros.person.model;

import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.tedros.person.domain.CivilStatus;
import org.tedros.person.domain.DomainSchema;
import org.tedros.person.domain.DomainTables;
import org.tedros.person.domain.Gender;
import org.tedros.person.domain.Sex;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Cacheable(false)
@Table(name = DomainTables.natural_person, schema = DomainSchema.schema)
@DiscriminatorValue("NTRL_PRSN")
@JsonClassDescription("A natural person")
public class NaturalPerson extends Person {

	private static final long serialVersionUID = -1790917959813402388L;

	@Column
	@JsonIgnore
	private Long tedrosUserId;
	
	@Column(length=60)
	private String lastName;
	
	@Column()
	@Temporal(TemporalType.DATE)
	private Date birthDate;
	
	@Column(length=12)
	@Enumerated(EnumType.STRING)
	private Sex sex;
	
	@Column(length=12)
	@Enumerated(EnumType.STRING)
	private Gender gender;
	
	@Column(length=25)
	@Enumerated(EnumType.STRING)
	private CivilStatus civilStatus;

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	@Override
	public String getDiscriminatorDesc() {
		return "#{label.natural.person}";
	}

	@Override
	public String toString() {
		return (getName() != null ? getName() + " " : "")
				+ (lastName != null ? lastName : "") ;
	}

	public CivilStatus getCivilStatus() {
		return civilStatus;
	}

	public void setCivilStatus(CivilStatus civilStatus) {
		this.civilStatus = civilStatus;
	}

	public Long getTedrosUserId() {
		return tedrosUserId;
	}

	public void setTedrosUserId(Long tedrosUserId) {
		this.tedrosUserId = tedrosUserId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((birthDate == null) ? 0 : birthDate.hashCode());
		result = prime * result + ((civilStatus == null) ? 0 : civilStatus.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((sex == null) ? 0 : sex.hashCode());
		result = prime * result + ((tedrosUserId == null) ? 0 : tedrosUserId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		NaturalPerson other = (NaturalPerson) obj;
		if (birthDate == null) {
			if (other.birthDate != null)
				return false;
		} else if (!birthDate.equals(other.birthDate))
			return false;
		if (civilStatus != other.civilStatus)
			return false;
		if (gender != other.gender)
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (sex != other.sex)
			return false;
		if (tedrosUserId == null) {
			if (other.tedrosUserId != null)
				return false;
		} else if (!tedrosUserId.equals(other.tedrosUserId))
			return false;
		return true;
	}

}
