package vn.com.vaccineman.it2.vaccineman.services.injectionman.staff.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import domainapp.basics.exceptions.ConstraintViolationException;
import domainapp.basics.model.meta.AttrRef;
import domainapp.basics.model.meta.DAssoc;
import domainapp.basics.model.meta.DAttr;
import domainapp.basics.model.meta.DOpt;
import domainapp.basics.model.meta.Select;
import domainapp.basics.model.meta.DAssoc.AssocEndType;
import domainapp.basics.model.meta.DAssoc.AssocType;
import domainapp.basics.model.meta.DAssoc.Associate;
import domainapp.basics.model.meta.DAttr.Type;
import domainapp.basics.model.meta.DClass;
import domainapp.basics.util.Tuple;
import vn.com.exceptions.DExCode;
import vn.com.utils.DToolkit;
import vn.com.vaccineman.services.enums.Gender;
import vn.com.vaccineman.it2.vaccineman.services.injectionman.registration.model.Registration;
import vn.com.vaccineman.it2.vaccineman.services.injectionman.staff.address.Address;


@DClass(schema="vaccineman")
public class SupportStaff extends Staff{

	  @DAttr(name = "registrations", type =Type.Collection, optional = false,
			  serialisable=false,filter=@Select(clazz=Registration.class))
	  @DAssoc(ascName = "staff-has-registrations", role = "supportStaff", 
	    ascType = AssocType.One2Many, endType = AssocEndType.One, 
	    associate = @Associate(type = Registration.class, cardMin = 1, cardMax = 100))
	  private Collection<Registration> registrations;
	  
	  private int registrationCount;

	  @DOpt(type=DOpt.Type.ObjectFormConstructor)
	  @DOpt(type=DOpt.Type.RequiredConstructor)
	public SupportStaff(@AttrRef("name")String name,
			@AttrRef("dob")Date dob, 
			@AttrRef("address")Address address, 
			@AttrRef("phoneNumber")String phoneNumber) {
		this(null, name, dob, null, address, phoneNumber);
	}
	  
	  @DOpt(type=DOpt.Type.ObjectFormConstructor)
	public SupportStaff(@AttrRef("name")String name, 
			@AttrRef("dob")Date dob,
			@AttrRef("gender")Gender gender,
			@AttrRef("address")Address address, 
			@AttrRef("phoneNumber")String phoneNumber) {
		this(null, name, dob, gender, address, phoneNumber);
	}

	@DOpt(type=DOpt.Type.DataSourceConstructor)
	public SupportStaff(@AttrRef("id")String id, 
			@AttrRef("name")String name, 
			@AttrRef("dob")Date dob, 
			@AttrRef("gender")Gender gender, 
			@AttrRef("address")Address address, 
			@AttrRef("phoneNumber")String phoneNumber) {
		super(id, name, dob, gender, address, phoneNumber);
		registrationCount = 0;
		registrations = new ArrayList<>();
	}

	
	@DOpt(type=DOpt.Type.LinkAdder)
	  public boolean addRegistration(Registration r) {
		  if(!registrations.contains(r))
			  registrations.add(r);
		  return false;
	  }
	  
	  @DOpt(type=DOpt.Type.LinkAdderNew)
	  public boolean addNewRegistration(Registration r) {
		  registrations.add(r);
		  registrationCount ++;
		  return false;
	  }
	  
	  @DOpt(type=DOpt.Type.LinkAdder)
	  public boolean addRegistrations(Collection<Registration> rs) {
		  boolean added = false;
		  for(Registration r : rs) {
			  if(!registrations.contains(r)) {
				  if(!added) added = true;
				  registrations.add(r);
			  }
		  }
		  return false;
	  }
	  
	  @DOpt(type=DOpt.Type.LinkAdderNew)
	  public boolean addNewRegistrations(Collection<Registration> ps) {
		  registrations.addAll(ps);
		  registrationCount += ps.size();
		  return false;
	  }
	  
	  @DOpt(type=DOpt.Type.LinkRemover)
	  public boolean removeRegistration(Registration e) {
		  boolean removed = registrations.remove(e);
		  if(removed) {
			  registrationCount --;
		  }
		  return false;
	  }

	  public Collection<Registration> getRegistrations() {
		    return registrations;
		  }

		  @DOpt(type=DOpt.Type.LinkCountGetter)
		  public Integer getRegistrationCount() {
		    return registrationCount;
		    //return enrolments.size();
		  }

		  @DOpt(type=DOpt.Type.LinkCountSetter)
		  public void setRegistrationCount(int count) {
		    registrationCount = count;
		  }
	
}