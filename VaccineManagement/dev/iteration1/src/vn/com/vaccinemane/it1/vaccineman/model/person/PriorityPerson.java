package vn.com.vaccinemane.it1.vaccineman.model.person;

import java.util.Date;

import domainapp.basics.exceptions.ConstraintViolationException;
import domainapp.basics.model.meta.AttrRef;
import domainapp.basics.model.meta.DAttr;
import domainapp.basics.model.meta.DAttr.Type;
import domainapp.basics.model.meta.DClass;
import domainapp.basics.model.meta.DOpt;
import vn.com.vaccinemane.it1.vaccineman.model.doctor.Doctor;
import vn.com.vaccinemane.it1.vaccineman.model.enums.Gender;
import vn.com.vaccinemane.it1.vaccineman.model.enums.HealthStatus;
import vn.com.vaccinemane.it1.vaccineman.model.registration.Registration;

@DClass(schema="vaccineman")
public class PriorityPerson extends Person{
	
	@DAttr(name="job", type=Type.String, length=20, optional=false)
	private String job;
	
	@DOpt(type=DOpt.Type.RequiredConstructor)
	@DOpt(type=DOpt.Type.ObjectFormConstructor)
	public PriorityPerson(@AttrRef("name")String name, 
			@AttrRef("dob")Date dob, 
			@AttrRef("address")String address, 
			@AttrRef("phoneNumber")String phoneNumber,
			@AttrRef("name")HealthStatus healthStatus, 
			@AttrRef("doctor")Doctor doctor,
			@AttrRef("registration")Registration registration) {
		this(null, name, dob, null, address, phoneNumber,null,  healthStatus, doctor, registration);
	}

	@DOpt(type=DOpt.Type.ObjectFormConstructor)
	public PriorityPerson(@AttrRef("name")String name, 
			@AttrRef("dob")Date dob, 
			@AttrRef("address")String address, 
			@AttrRef("phoneNumber")String phoneNumber,
			@AttrRef("job")String job, 
			@AttrRef("healthStatus")HealthStatus healthStatus, 
			@AttrRef("doctor")Doctor doctor,
			@AttrRef("registration")Registration registration) {
		this(null, name, dob, null, address, phoneNumber,job,  healthStatus, doctor, registration);
	}
	
	@DOpt(type=DOpt.Type.ObjectFormConstructor)
	public PriorityPerson(@AttrRef("name")String name, 
			@AttrRef("dob")Date dob, 
			@AttrRef("gender")Gender gender, 
			@AttrRef("address")String address, 
			@AttrRef("phoneNumber")String phoneNumber,
			@AttrRef("healthStatus")HealthStatus healthStatus,
			@AttrRef("doctor")Doctor doctor,
			@AttrRef("registration")Registration registration) {
		this(null, name, dob, gender, address, phoneNumber,null,  healthStatus, doctor, registration);
	}
	
	@DOpt(type=DOpt.Type.ObjectFormConstructor)
	public PriorityPerson(@AttrRef("name")String name, 
			@AttrRef("dob")Date dob, 
			@AttrRef("gender")Gender gender, 
			@AttrRef("address")String address, 
			@AttrRef("phoneNumber")String phoneNumber,
			@AttrRef("job")String job, 
			@AttrRef("healthStatus")HealthStatus healthStatus, 
			@AttrRef("doctor")Doctor doctor,
			@AttrRef("registration")Registration registration) {
		this(null, name, dob, gender, address, phoneNumber,job,  healthStatus, doctor, registration);
	}
	
	
	 @DOpt(type=DOpt.Type.DataSourceConstructor)
	public PriorityPerson(String id, String name, Date dob, Gender gender, String address, String phoneNumber,
			String job, HealthStatus healthStatus, Doctor doctor, Registration registration) {
		super(id, name, dob, gender, address, phoneNumber, healthStatus, doctor, registration);
		this.job = job;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	 
	

}
