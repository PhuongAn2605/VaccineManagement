package vn.com.vaccinemane.it1.vaccineman.model.person;

import java.util.Date;

import domainapp.basics.model.meta.AttrRef;
import domainapp.basics.model.meta.DClass;
import domainapp.basics.model.meta.DOpt;

import vn.com.vaccinemane.it1.vaccineman.model.doctor.Doctor;
import vn.com.vaccinemane.it1.vaccineman.model.enums.Gender;
import vn.com.vaccinemane.it1.vaccineman.model.enums.HealthStatus;
import  vn.com.vaccinemane.it1.vaccineman.model.registration.Registration;

@DClass(schema="vaccineman")
public class NormalPerson extends Person {

	@DOpt(type=DOpt.Type.RequiredConstructor)
	@DOpt(type=DOpt.Type.ObjectFormConstructor)
	public NormalPerson(@AttrRef("name")String name, 
			@AttrRef("dob")Date dob, 
			@AttrRef("address")String address, 
			@AttrRef("phoneNumber")String phoneNumber,
			@AttrRef("healthStatus")HealthStatus healthStatus, 
			@AttrRef("doctor")Doctor doctor,
			@AttrRef("registration")Registration registration) {
		this(null, name, dob, null, address, phoneNumber, healthStatus, doctor, registration);
	}
	
	
	@DOpt(type=DOpt.Type.ObjectFormConstructor)
	public NormalPerson(@AttrRef("name")String name, 
			@AttrRef("dob")Date dob, 
			@AttrRef("gender")Gender gender, 
			@AttrRef("address")String address, 
			@AttrRef("phoneNumber")String phoneNumber,
			@AttrRef("healthStatus")HealthStatus healthStatus, 
			@AttrRef("doctor")Doctor doctor,
			@AttrRef("registration")Registration registration) {
		this(null, name, dob, gender, address, phoneNumber,healthStatus, doctor, registration);
	}
	
//	@DOpt(type=DOpt.Type.ObjectFormConstructor)
//	public NormalPerson(@AttrRef("name")String name, 
//			@AttrRef("date")Date dob,
//			@AttrRef("gender")Gender gender, 
//			@AttrRef("address")String address, 
//			@AttrRef("phoneNumber")String phoneNumber,
//			HealthStatus healthStatus, Doctor doctor) {
//		this(null, name, dob, gender, address, phoneNumber,healthStatus, doctor);
//	}

	
	@DOpt(type=DOpt.Type.DataSourceConstructor)
	public NormalPerson(String id, String name, Date dob, Gender gender, String address, String phoneNumber,
			HealthStatus healthStatus, Doctor doctor, Registration registration) {
		super(id, name, dob, gender, address, phoneNumber,healthStatus, doctor, registration);
	}

}
