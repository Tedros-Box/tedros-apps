/**
 * 
 */
package com.tedros.person.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tedros.person.domain.DomainSchema;
import com.tedros.person.domain.DomainTables;
import com.tedros.person.domain.Gender;
import com.tedros.person.domain.Sex;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.natural_person, schema = DomainSchema.schema)
@DiscriminatorValue("N")
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
	public String toString() {
		return (getName() != null ? "[#{label.natural.person}] "+getName() + " " : "")
				+ (lastName != null ? lastName : "") ;
	}
	
	
}
