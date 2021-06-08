package vn.com.vaccineman.it3.vaccineman.services.injectionman.person.model;

import java.util.Date;

import domainapp.basics.exceptions.ConstraintViolationException;
import domainapp.basics.model.meta.AttrRef;
import domainapp.basics.model.meta.DAssoc;
import domainapp.basics.model.meta.DAttr;
import domainapp.basics.model.meta.DAttr.Type;
import domainapp.basics.model.meta.DClass;
import domainapp.basics.model.meta.DOpt;
import vn.com.vaccineman.it3.vaccineman.services.enums.Gender;
import vn.com.vaccineman.it3.vaccineman.services.enums.HealthStatus;
import vn.com.vaccineman.it3.vaccineman.services.injectionman.injection.model.Injection;
import vn.com.vaccineman.it3.vaccineman.services.injectionman.region.InjectRegion;
import vn.com.vaccineman.it3.vaccineman.services.injectionman.registration.model.Registration;
import domainapp.basics.model.meta.DAssoc.AssocEndType;
import domainapp.basics.model.meta.DAssoc.AssocType;
import domainapp.basics.model.meta.DAssoc.Associate;

@DClass(schema="vaccineman")
public class PriorityPerson extends Person{
	
	@DAttr(name="job", type=Type.String, length=20, optional=false)
	private String job;
	
//	@DAttr(name =A_injectRegion, type = Type.Domain, length = 15, optional = false)
//	  @DAssoc(ascName="region-has-priorityPeople",role="priorityPerson",
//ascType=AssocType.One2Many, endType=AssocEndType.Many,
//associate=@Associate(type=InjectRegion.class,cardMin=1,cardMax=1), dependsOn=true)
	
//	  private InjectRegion injectRegion;
	
//	@DOpt(type=DOpt.Type.RequiredConstructor)
//	@DOpt(type=DOpt.Type.ObjectFormConstructor)
//	public PriorityPerson(@AttrRef("name")String name, 
//			@AttrRef("dob")Date dob, 
//			@AttrRef("injectRegion")String injectRegion, 
//			@AttrRef("phoneNumber")String phoneNumber,
//			@AttrRef("name")HealthStatus healthStatus, 
//			@AttrRef("doctor")Doctor doctor,
//			@AttrRef("registration")Registration registration) {
//		this(null, name, dob, null, injectRegion, phoneNumber,null,  healthStatus, doctor, registration);
//	}
	@DOpt(type=DOpt.Type.ObjectFormConstructor)
	@DOpt(type=DOpt.Type.RequiredConstructor)
	public PriorityPerson(@AttrRef("name")String name, 
			@AttrRef("dob")Date dob, 
			@AttrRef("phoneNumber")String phoneNumber,
			@AttrRef("healthStatus")HealthStatus healthStatus, 
			@AttrRef("job")String job) {
		this(null, name, dob, null, null, phoneNumber, healthStatus, null, null, job);
	}

	@DOpt(type=DOpt.Type.ObjectFormConstructor)
	public PriorityPerson(@AttrRef("name")String name, 
			@AttrRef("dob")Date dob, 
			@AttrRef("gender")Gender gender,
			@AttrRef("phoneNumber")String phoneNumber,
			@AttrRef("healthStatus")HealthStatus healthStatus, 
			@AttrRef("job")String job) {
		this(null, name, dob,gender, null, phoneNumber,healthStatus, null, null, job);
	}
	

	
	
	@DOpt(type=DOpt.Type.ObjectFormConstructor)
	public PriorityPerson(@AttrRef("name")String name, 
			@AttrRef("dob")Date dob, 
			@AttrRef("gender")Gender gender, 
			@AttrRef("injectRegion")InjectRegion injectRegion, 
			@AttrRef("phoneNumber")String phoneNumber,
			@AttrRef("healthStatus")HealthStatus healthStatus,
			@AttrRef("registration")Registration registration,
			@AttrRef("injection") Injection injection,
			@AttrRef("job")String job) {
		this(null, name, dob, gender, injectRegion, phoneNumber, healthStatus, registration, injection, job);
	}
	
	
	@DOpt(type=DOpt.Type.DataSourceConstructor)
	public PriorityPerson(
			@AttrRef("id")String id, 
			@AttrRef("name")String name, 
			@AttrRef("dob")Date dob, 
			@AttrRef("gender")Gender gender, 
			@AttrRef("injectRegion")InjectRegion injectRegion, 
			@AttrRef("phoneNumber")String phoneNumber,
			@AttrRef("healthStatus")HealthStatus healthStatus,
			@AttrRef("registration")Registration registration,
			@AttrRef("injection") Injection injection,
			@AttrRef("job")String job) {
		super(id, name, dob, gender, injectRegion, phoneNumber, healthStatus, registration, injection);
		this.job = job;
	}
	 
//	 public PriorityPerson(
//				@AttrRef("id")String id, 
//				@AttrRef("name")String name, 
//				@AttrRef("dob")Date dob, 
//				@AttrRef("gender")Gender gender, 
//				@AttrRef("injectRegion")InjectRegion injectRegion, 
//				@AttrRef("phoneNumber")String phoneNumber,
//				@AttrRef("healthStatus")HealthStatus healthStatus,
//				@AttrRef("registration")Registration registration,
//				@AttrRef("injection") Injection injection,
//				@AttrRef("job")String job) {
//			super(id, name, dob, gender, injectRegion, phoneNumber, healthStatus,registration, injection);
//			this.job = job;
//		}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}
	
//	@DOpt(type=DOpt.Type.LinkUpdater)
//	public boolean updateinjectRegion(InjectRegion r) {
//		setinjectRegion(r);
//		return true;
//
//	}
	

}

//package vn.com.vaccineman.services.injectionman.person.model;
//
//import java.util.Date;
//
//import domainapp.basics.exceptions.ConstraintViolationException;
//import domainapp.basics.model.meta.AttrRef;
//import domainapp.basics.model.meta.DAttr;
//import domainapp.basics.model.meta.DAttr.Type;
//import domainapp.basics.model.meta.DClass;
//import domainapp.basics.model.meta.DOpt;
//import vn.com.vaccineman.services.enums.Gender;
//import vn.com.vaccineman.services.enums.HealthStatus;
//import vn.com.vaccineman.services.injectionman.injection.model.Injection;
//import vn.com.vaccineman.services.injectionman.region.InjectRegion;
//import vn.com.vaccineman.services.injectionman.registration.Registration;
//
//@DClass(schema="vaccineman")
//public class PriorityPerson extends Person{
//	
//	@DAttr(name="job", type=Type.String, length=20, optional=false)
//	private String job;
//	
////	@DOpt(type=DOpt.Type.RequiredConstructor)
////	@DOpt(type=DOpt.Type.ObjectFormConstructor)
////	public PriorityPerson(@AttrRef("name")String name, 
////			@AttrRef("dob")Date dob, 
////			@AttrRef("injectRegion")String injectRegion, 
////			@AttrRef("phoneNumber")String phoneNumber,
////			@AttrRef("name")HealthStatus healthStatus, 
////			@AttrRef("doctor")Doctor doctor,
////			@AttrRef("registration")Registration registration) {
////		this(null, name, dob, null, injectRegion, phoneNumber,null,  healthStatus, doctor, registration);
////	}
//
//	@DOpt(type=DOpt.Type.ObjectFormConstructor)
//	public PriorityPerson(@AttrRef("name")String name, 
//			@AttrRef("dob")Date dob, 
//			@AttrRef("injectRegion")InjectRegion injectRegion, 
//			@AttrRef("phoneNumber")String phoneNumber,
//			@AttrRef("job")String job, 
//			@AttrRef("healthStatus")HealthStatus healthStatus, 
//			@AttrRef("registration")Registration registration,
//			@AttrRef("injection") Injection injection) {
//		this(null, name, dob, null, injectRegion, phoneNumber,job,  healthStatus, registration, injection);
//	}
//	
//	@DOpt(type=DOpt.Type.ObjectFormConstructor)
//	public PriorityPerson(@AttrRef("name")String name, 
//			@AttrRef("dob")Date dob, 
//			@AttrRef("gender")Gender gender,
//			@AttrRef("injectRegion")InjectRegion injectRegion, 
//			@AttrRef("phoneNumber")String phoneNumber,
//			@AttrRef("job")String job, 
//			@AttrRef("healthStatus")HealthStatus healthStatus) {
//		this(null, name, dob, gender, injectRegion, phoneNumber,job,  healthStatus, null, null);
//	}
//	
////	@DOpt(type=DOpt.Type.ObjectFormConstructor)
////	public PriorityPerson(@AttrRef("name")String name, 
////			@AttrRef("dob")Date dob, 
////			@AttrRef("gender")Gender gender, 
////			@AttrRef("injectRegion")String injectRegion, 
////			@AttrRef("phoneNumber")String phoneNumber,
////			@AttrRef("healthStatus")HealthStatus healthStatus,
////			@AttrRef("doctor")Doctor doctor,
////			@AttrRef("registration")Registration registration) {
////		this(null, name, dob, gender, injectRegion, phoneNumber,null,  healthStatus, doctor, registration);
////	}
////	
//	
//	@DOpt(type=DOpt.Type.ObjectFormConstructor)
//	public PriorityPerson(@AttrRef("name")String name, 
//			@AttrRef("dob")Date dob, 
//			@AttrRef("gender")Gender gender, 
//			@AttrRef("injectRegion")InjectRegion injectRegion, 
//			@AttrRef("phoneNumber")String phoneNumber,
//			@AttrRef("job")String job, 
//			@AttrRef("healthStatus")HealthStatus healthStatus,
//			@AttrRef("registration")Registration registration,
//			@AttrRef("injection") Injection injection) {
//		this(null, name, dob, gender, injectRegion, phoneNumber,job,  healthStatus, registration, injection);
//	}
//	
//	
//	 @DOpt(type=DOpt.Type.DataSourceConstructor)
//	public PriorityPerson(
//			@AttrRef("id")String id, 
//			@AttrRef("name")String name, 
//			@AttrRef("dob")Date dob, 
//			@AttrRef("gender")Gender gender, 
//			@AttrRef("injectRegion")InjectRegion injectRegion, 
//			@AttrRef("phoneNumber")String phoneNumber,
//			@AttrRef("job")String job,
//			@AttrRef("healthStatus")HealthStatus healthStatus,
//			@AttrRef("registration")Registration registration,
//			@AttrRef("injection") Injection injection) {
//		super(id, name, dob, gender, injectRegion, phoneNumber, healthStatus, registration, injection);
//		this.job = job;
//	}
//
//	public String getJob() {
//		return job;
//	}
//
//	public void setJob(String job) {
//		this.job = job;
//	}
//
//	 
//	
//
//}
