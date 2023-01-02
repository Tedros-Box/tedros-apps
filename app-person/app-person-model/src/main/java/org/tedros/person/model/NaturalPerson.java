/**
 * 
 */
package org.tedros.person.model;

import java.util.Date;

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

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.natural_person, schema = DomainSchema.schema)
@DiscriminatorValue("NTRL_PRSN")
public class NaturalPerson extends Person {

	private static final long serialVersionUID = -1790917959813402388L;

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
	
	
}
