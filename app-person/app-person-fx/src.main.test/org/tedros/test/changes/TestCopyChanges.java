package org.tedros.test.changes;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.tedros.extension.model.Contact;
import org.tedros.extension.model.ContactType;
import org.tedros.fx.util.JsonHelper;
import org.tedros.fx.util.TBeanUtil;
import org.tedros.person.domain.CivilStatus;
import org.tedros.person.model.Employee;
import org.tedros.person.model.LegalPerson;

public class TestCopyChanges {

	public TestCopyChanges() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		Contact c1 = new Contact();
		c1.setId(1L);
		c1.setVersionNum(22);
		c1.setInsertDate(new Date());
		c1.setName("Davis");
		c1.setType(ContactType.EMAIL);
		c1.setValue("davis.dun@gmail.com");
		Set<Contact> cts = new HashSet<>();
		cts.add(c1);
		LegalPerson emp = new LegalPerson();
		emp.setName("Tedros S.A");
		emp.setOtherName("Tedros Box");
		emp.setId(1L);
		emp.setVersionNum(22);
		emp.setInsertDate(new Date());
		emp.setContacts(cts);
		Employee src = new Employee();
		src.setEmployer(emp);
		src.setName("Nando");
		src.setLastName("Dun");
		src.setBirthDate(new Date());
		src.setCivilStatus(CivilStatus.SINGLE);
		
		String json = JsonHelper.write(src);
		System.out.println(json);
		Employee tgt = JsonHelper.read(json, Employee.class);
		tgt.setDescription("test");
		Contact c2 = new Contact();
		c2.setId(2L);
		c2.setVersionNum(22);
		c2.setInsertDate(new Date());
		c2.setName("Poly");
		c2.setType(ContactType.EMAIL);
		c2.setValue("poly.dun@gmail.com");
		tgt.getEmployer().getContacts().add(c2);
		
		TBeanUtil.copyChanges(tgt, src);
		System.out.println("_-------------");
		
		json =  JsonHelper.write(src);
		System.out.println(json);

	}

}
