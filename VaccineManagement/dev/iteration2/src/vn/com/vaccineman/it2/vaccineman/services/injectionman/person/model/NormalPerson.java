package vn.com.vaccineman.it2.vaccineman.services.injectionman.person.model;

import java.util.Date;

import domainapp.basics.exceptions.ConstraintViolationException;
import domainapp.basics.model.meta.AttrRef;
import domainapp.basics.model.meta.DAssoc;
import domainapp.basics.model.meta.DAttr;
import domainapp.basics.model.meta.DAttr.Type;
import domainapp.basics.model.meta.DClass;
import domainapp.basics.model.meta.DOpt;
import domainapp.basics.model.meta.DAssoc.AssocEndType;
import domainapp.basics.model.meta.DAssoc.AssocType;
import domainapp.basics.model.meta.DAssoc.Associate;
import vn.com.vaccineman.it2.vaccineman.services.injectionman.injection.model.Injection;
import vn.com.vaccineman.it2.vaccineman.services.injectionman.region.InjectRegion;
import vn.com.vaccineman.it2.vaccineman.services.injectionman.registration.model.Registration;
import vn.com.vaccineman.services.enums.Gender;
import vn.com.vaccineman.services.enums.HealthStatus;

@DClass(schema="vaccineman")
public class NormalPerson extends Person {
	
//	@DAttr(name = A_injectRegion, type = Type.Domain, length = 15, optional = false)
//	  @DAssoc(ascName="region-has-normalPeople",role="normalPerson",
//  ascType=AssocType.One2Many, endType=AssocEndType.Many,
//associate=@Associate(type=InjectRegion.class,cardMin=1,cardMax=1), dependsOn=true)
//	  private InjectRegion injectRegion;

	@DOpt(type=DOpt.Type.RequiredConstructor)
	@DOpt(type=DOpt.Type.ObjectFormConstructor)
	public NormalPerson(@AttrRef("name")String name, 
			@AttrRef("dob")Date dob, 
			@AttrRef("phoneNumber")String phoneNumber,
			@AttrRef("healthStatus")HealthStatus healthStatus) {
		this(null, name, dob, null, null, phoneNumber, healthStatus, null, null);
	}
	
	@DOpt(type=DOpt.Type.ObjectFormConstructor)
	public NormalPerson(@AttrRef("name")String name, 
			@AttrRef("dob")Date dob, 
			@AttrRef("gender")Gender gender, 
			@AttrRef("phoneNumber")String phoneNumber,
			@AttrRef("healthStatus")HealthStatus healthStatus) {
		this(null, name, dob, gender, null, phoneNumber,healthStatus, null, null);
	}
	
	@DOpt(type=DOpt.Type.ObjectFormConstructor)
	public NormalPerson(@AttrRef("name")String name, 
			@AttrRef("dob")Date dob, 
			@AttrRef("gender")Gender gender, 
			@AttrRef("injectRegion")InjectRegion injectRegion, 
			@AttrRef("phoneNumber")String phoneNumber,
			@AttrRef("healthStatus")HealthStatus healthStatus, 
			@AttrRef("registration")Registration registration,
			@AttrRef("injection") Injection injection) {
		this(null, name, dob, gender, injectRegion, phoneNumber,healthStatus,registration, injection);
	}
	
//	@DOpt(type=DOpt.Type.ObjectFormConstructor)
//	public NormalPerson(@AttrRef("name")String name, 
//			@AttrRef("date")Date dob,
//			@AttrRef("gender")Gender gender, 
//			@AttrRef("injectRegion")InjectRegion injectRegion, 
//			@AttrRef("phoneNumber")String phoneNumber,
//			HealthStatus healthStatus,
//			@AttrRef("injection") Injection injection) {
//		this(null, name, dob, gender, injectRegion, phoneNumber, healthStatus, null, injection);
//	}

	
	@DOpt(type=DOpt.Type.DataSourceConstructor)
	public NormalPerson(@AttrRef("id")String id, 
			@AttrRef("name")String name, 
			@AttrRef("dob")Date dob, 
			@AttrRef("gender")Gender gender, 
			@AttrRef("injectRegion")InjectRegion injectRegion, 
			@AttrRef("phoneNumber")String phoneNumber,
			@AttrRef("healthStatus")HealthStatus healthStatus,
			@AttrRef("registration")Registration registration,
			@AttrRef("injection") Injection injection) throws ConstraintViolationException{
		super(id, name, dob, gender, injectRegion, phoneNumber, healthStatus, registration, injection);
	}

}
