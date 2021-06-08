package vn.com.vaccineman.it3.vaccineman.services.injectionman.staff.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import domainapp.basics.exceptions.ConstraintViolationException;
import domainapp.basics.model.meta.AttrRef;
import domainapp.basics.model.meta.DAssoc;
import domainapp.basics.model.meta.DAttr;
import domainapp.basics.model.meta.DAttr.Type;
import domainapp.basics.util.Tuple;

import vn.com.vaccineman.it3.vaccineman.services.enums.Gender;
import vn.com.vaccineman.it3.vaccineman.services.injectionman.injection.model.Injection;
import vn.com.vaccineman.it3.vaccineman.services.injectionman.staff.address.Address;
import vn.com.vaccineman.it3.vaccineman.services.injectionman.staff.report.StaffByNameReport;
//import vn.com.vaccineman.model.Student;
import domainapp.basics.model.meta.DClass;
import domainapp.basics.model.meta.DOpt;
import domainapp.basics.model.meta.Select;
import domainapp.basics.model.meta.DAssoc.AssocEndType;
import domainapp.basics.model.meta.DAssoc.AssocType;
import domainapp.basics.model.meta.DAssoc.Associate;

@DClass(schema="vaccineman")
public class Doctor extends Staff{
	public static final String A_department = "department";

	  @DAttr(name = A_department, type = Type.String, length = 30, optional = false)
	  private String department;
	  
	  
	  @DAttr(name="injections",type=Type.Collection,optional = false,
		      serialisable=false,filter=@Select(clazz=Injection.class))
		  @DAssoc(ascName="doctor-has-injections",role="doctor",
		      ascType=AssocType.One2Many,endType=AssocEndType.One,
		    associate=@Associate(type=Injection.class,cardMin=0,cardMax=100))
	  private Collection<Injection> injections; 
	  
	  private int injectionCount;

	  
	  @DOpt(type=DOpt.Type.ObjectFormConstructor)
	  @DOpt(type=DOpt.Type.RequiredConstructor)
	  public Doctor(@AttrRef("name") String name, 
	      @AttrRef("dob") Date dob,
	      @AttrRef("address") Address address, 
	      @AttrRef("phoneNumber") String phoneNumber, 
	      @AttrRef("department") String department) {
	    this(null, name, dob, null, address, phoneNumber, department);
	  }

	  @DOpt(type=DOpt.Type.ObjectFormConstructor)
	  public Doctor(@AttrRef("name") String name, 
	      @AttrRef("dob") Date dob,
	      @AttrRef("gender") Gender gender,
	      @AttrRef("address") Address address,
	      @AttrRef("phoneNumber") String phoneNumber,
	      @AttrRef("department")String department) {
	    this(null, name, dob, gender, address, phoneNumber, department);
	  }
	  
	  // a shared constructor that is invoked by other constructors
	  @DOpt(type=DOpt.Type.DataSourceConstructor)
	  public Doctor(@AttrRef("id")String id, 
			  @AttrRef("name")String name,
			  @AttrRef("dob")Date dob,
			  @AttrRef("gender")Gender gender, 
			  @AttrRef("address")Address address, 
			  @AttrRef("phoneNumber")String phoneNumber,
			  @AttrRef("department")String department) 
	  throws ConstraintViolationException {
	    super(id, name, dob, gender, address, phoneNumber);
	    
	    this.department = department;
	    
	    injectionCount =0;
	    injections = new ArrayList<>();
	  }

	  // setter methods

	  

	  // v2.7.3
//	  public void setNewAddress(City address) {
//	    setAddress(address);
//	  }


	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	  
	  @DOpt(type=DOpt.Type.LinkAdder)
	  public boolean addInjection(Injection i) {
		  if(!injections.contains(i))
			  injections.add(i);
		  return false;
	  }
	  
	  @DOpt(type=DOpt.Type.LinkAdderNew)
	  public boolean addNewInjection(Injection i) {
		  injections.add(i);
		  injectionCount ++;
		  return false;
	  }
	  
	  @DOpt(type=DOpt.Type.LinkAdder)
	  public boolean addInjection(Collection<Injection> is) {
		  boolean added = false;
		  for(Injection i : is) {
			  if(!injections.contains(i)) {
				  if(!added) added = true;
				  injections.add(i);
			  }
		  }
		  return false;
	  }
	  
	  @DOpt(type=DOpt.Type.LinkAdderNew)
	  public boolean addNewInjection(Collection<Injection> is) {
		  injections.addAll(is);
		  injectionCount += is.size();
		  return false;
	  }
	  
	  @DOpt(type=DOpt.Type.LinkRemover)
	  public boolean removeInjection(Injection i) {
		  boolean removed = injections.remove(i);
		  if(removed) {
			  injectionCount --;
		  }
		  return false;
	  }


	public void setInjections(Collection<Injection> injections) {
		this.injections = injections;
		injectionCount = injections.size();
	}
	
	public Collection<Injection> getInjections() {
	    return injections;
	  }

		@DOpt(type=DOpt.Type.LinkCountGetter)
		  public Integer getInjectionCount() {
		    return injectionCount;
		    //return enrolments.size();
		  }

		  @DOpt(type=DOpt.Type.LinkCountSetter)
		  public void setInjectionCount(int count) {
		    injectionCount = count;
		  }
}
