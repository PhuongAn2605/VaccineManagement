package vn.com.vaccineman.it3.vaccineman.services.injectionman.person.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import domainapp.basics.exceptions.ConstraintViolationException;
import domainapp.basics.model.meta.AttrRef;
import domainapp.basics.model.meta.DAssoc;
import domainapp.basics.model.meta.DAttr;
import domainapp.basics.model.meta.DAttr.Format;
import domainapp.basics.model.meta.DAttr.Type;
import domainapp.basics.util.Tuple;
import vn.com.exceptions.DExCode;
import vn.com.utils.DToolkit;
import vn.com.vaccineman.it3.vaccineman.services.enums.Gender;
import vn.com.vaccineman.it3.vaccineman.services.enums.HealthStatus;
import vn.com.vaccineman.it3.vaccineman.services.injectionman.injection.model.Injection;
import vn.com.vaccineman.it3.vaccineman.services.injectionman.injection.record.InjectionRecord;
import vn.com.vaccineman.it3.vaccineman.services.injectionman.person.report.PeopleByNameReport;
import vn.com.vaccineman.it3.vaccineman.services.injectionman.region.InjectRegion;
import vn.com.vaccineman.it3.vaccineman.services.injectionman.registration.model.Registration;
import domainapp.basics.model.meta.DClass;
import domainapp.basics.model.meta.DOpt;
import domainapp.basics.model.meta.Select;
import domainapp.basics.model.meta.DAssoc.AssocEndType;
import domainapp.basics.model.meta.DAssoc.AssocType;
import domainapp.basics.model.meta.DAssoc.Associate;


@DClass(schema="vaccineman")
public abstract class Person {

	public static final String A_id = "id";
	public static final String A_name = "name";
	public static final String A_dob = "dob";
	public static final String A_gender= "gender";
	public static final String A_injectRegion= "injectRegion";
	public static final String A_department = "department";
	public static final String A_phoneNumber = "phoneNumber";
	public static final String A_injection= "injection";
	public static final String A_healthStatus = "healthStatus";
	public static final String A_registration = "registration";
	public static final String A_rptPersonByRegistration = "rptPersonByRegistration";
	public static final String A_rptPersonByName = "rptPersonByName";
	
	@DAttr(name=A_id, id=true, type=Type.String, auto=true, length=5,
			mutable=false, optional=false)
	private String id;
	
	private static int idCounter = 0;
	
	@DAttr(name = A_name, type = Type.String, length =20, optional = false, cid=true)
	  private String name;
	  
	  @DAttr(name = A_dob, type = Type.Date, format=Format.Date, length = 10, optional = false)
	  private Date dob;
	  
	  @DAttr(name = A_gender, type = Type.Domain, length=5, optional = true)
	  private Gender gender;
	  
	  @DAttr(name = A_injectRegion, type = Type.Domain, length=10)
	  @DAssoc(ascName="region-has-people",role="person",
      ascType=AssocType.One2Many, endType=AssocEndType.Many,
  associate=@Associate(type=InjectRegion.class,cardMin=1,cardMax=1))
	  private InjectRegion injectRegion;
	  
	  @DAttr(name = A_phoneNumber, type = Type.String, length = 12, optional = false)
	  private String phoneNumber;
	  
	  
	  @DAttr(name = A_healthStatus, type = Type.Domain, length = 10, optional = false)
	  private HealthStatus healthStatus;
	  
//	  @DAttr(name = "doctor", type = Type.Domain, length = 20, optional = true)
//	  @DAssoc(ascName = "doctor-has-people", role = "person", 
//	    ascType = AssocType.One2Many, endType = AssocEndType.Many, 
//	    associate = @Associate(type = Doctor.class, cardMin = 1, cardMax = 1), dependsOn = true)
//	  private Doctor doctor;
	  
	  //?add determinent=true -> input -> can not select, no person -> no registration
	  @DAttr(name =A_registration, type = Type.Domain)
	  @DAssoc(ascName="registration-has-person",role="person",
	  ascType=AssocType.One2One, endType=AssocEndType.One,
	  associate=@Associate(type=Registration.class,cardMin=1,cardMax=1))
	  private Registration registration;
	  
//	  @DAttr(name=A_registration, type=Type.Domain, length=10, optional = true)
//		private Registration registration;
		
	  
	  @DAttr(name = A_injection, type = Type.Domain, length=10)
	  @DAssoc(ascName="injection-has-people",role="person",
      ascType=AssocType.One2Many, endType=AssocEndType.Many,
  associate=@Associate(type=Injection.class,cardMin=1,cardMax=1))
	  private Injection injection;
	  
	  @DAttr(name="injectionRecords", type=Type.Collection, length=20, serialisable=false,
				filter=@Select(clazz=InjectionRecord.class))
		@DAssoc(ascName="person-has-injection-records",role="person",
		  ascType=AssocType.One2Many, endType=AssocEndType.One,
		  associate=@Associate(type=InjectionRecord.class,cardMin=0,cardMax=100))
		private Collection<InjectionRecord> injectionRecords;
	  
	  private int recordCount;
	  
	  @DAttr(name=A_rptPersonByName,type=Type.Domain, serialisable=false, virtual=true)
	  private PeopleByNameReport rptPersonByName;
	 

	  @DOpt(type=DOpt.Type.ObjectFormConstructor)
	  @DOpt(type=DOpt.Type.RequiredConstructor)
	public Person(@AttrRef("name")String name, 
			@AttrRef("dob")Date dob,  
			@AttrRef("phoneNumber")String phoneNumber,
			@AttrRef("healthStatus")HealthStatus healthStatus
			) {
		this(null, name, dob, null, null, phoneNumber,healthStatus, null,null);
	}
	  
//	  @DOpt(type=DOpt.Type.ObjectFormConstructor)
//	public Person(@AttrRef("name")String name, 
//			@AttrRef("dob")Date dob, 
//			@AttrRef("injectRegion")InjectRegion injectRegion, 
//			@AttrRef("phoneNumber")String phoneNumber,
//			@AttrRef("healthStatus")HealthStatus healthStatus
//			) {
//		this(null, name, dob, null, injectRegion, phoneNumber,healthStatus, null,null);
//	}
	  
	  @DOpt(type=DOpt.Type.ObjectFormConstructor)
		public Person(@AttrRef("name")String name, 
				@AttrRef("dob")Date dob,
				@AttrRef("gender")Gender gender,
				@AttrRef("injectRegion")InjectRegion injectRegion, 
				@AttrRef("phoneNumber")String phoneNumber,
				@AttrRef("healthStatus")HealthStatus healthStatus) {
			this(null, name, dob, gender, injectRegion, phoneNumber,healthStatus, null,null);
		}
	  
	  @DOpt(type=DOpt.Type.ObjectFormConstructor)
		public Person(@AttrRef("name")String name, 
				@AttrRef("dob")Date dob,
				@AttrRef("gender")Gender gender,
				@AttrRef("injectRegion")InjectRegion injectRegion, 
				@AttrRef("phoneNumber")String phoneNumber,
				@AttrRef("healthStatus")HealthStatus healthStatus,
				@AttrRef("injection") Injection injection) {
			this(null, name, dob, gender, injectRegion, phoneNumber,healthStatus, null, injection);
		}
	  
	  @DOpt(type=DOpt.Type.ObjectFormConstructor)
		public Person(@AttrRef("name")String name, 
				@AttrRef("dob")Date dob,
				@AttrRef("gender")Gender gender,
				@AttrRef("injectRegion")InjectRegion injectRegion, 
				@AttrRef("phoneNumber")String phoneNumber,
				@AttrRef("healthStatus")HealthStatus healthStatus,
				@AttrRef("registration")Registration registration) {
			this(null, name, dob, gender, injectRegion, phoneNumber,healthStatus, registration, null);
		}
	  
	  @DOpt(type=DOpt.Type.ObjectFormConstructor)
		public Person(@AttrRef("name")String name, 
				@AttrRef("dob")Date dob,
				@AttrRef("gender")Gender gender,
				@AttrRef("phoneNumber")String phoneNumber,
				@AttrRef("healthStatus")HealthStatus healthStatus) {
			this(null, name, dob,gender,null, phoneNumber,healthStatus,null, null);
		}
	  
	  @DOpt(type=DOpt.Type.ObjectFormConstructor)
		public Person(@AttrRef("name")String name, 
				@AttrRef("dob")Date dob,
				@AttrRef("gender")Gender gender,
				@AttrRef("injectRegion")InjectRegion injectRegion, 
				@AttrRef("phoneNumber")String phoneNumber,
				@AttrRef("healthStatus")HealthStatus healthStatus,
				@AttrRef("registration")Registration registration,
				@AttrRef("injection") Injection injection) {
			this(null, name, dob, gender, injectRegion, phoneNumber,healthStatus, registration, injection);
		}

	@DOpt(type=DOpt.Type.DataSourceConstructor)
	public Person(@AttrRef("id")String id, 
			@AttrRef("name")String name, 
			@AttrRef("dob")Date dob, 
			@AttrRef("gender")Gender gender, 
			@AttrRef("injectRegion")InjectRegion injectRegion, 
			@AttrRef("phoneNumber")String phoneNumber,
			@AttrRef("healthStatus")HealthStatus healthStatus,
			@AttrRef("registration")Registration registration,
			@AttrRef("injection") Injection injection) {
//		this(id, name, dob, gender, injectRegion, phoneNumber, healthStatus, null, injection);
		this.id = nextID(id);
		this.name = name;
		this.dob = dob;
		this.gender = gender;
		this.injectRegion = injectRegion;
//		this.phoneNumber = phoneNumber;
		setPhoneNumber(phoneNumber);
//		this.job = job;
		this.healthStatus = healthStatus;
//		this.doctor = doctor;
		this.registration = registration;
		this.injection = injection;
	}
	
	//base constructor
//	protected Person(@AttrRef("id")String id, 
//			@AttrRef("name")String name, 
//			@AttrRef("dob")Date dob, 
//			@AttrRef("gender")Gender gender, 
//			@AttrRef("injectRegion")InjectRegion injectRegion, 
//			@AttrRef("phoneNumber")String phoneNumber,
//			@AttrRef("healthStatus")HealthStatus healthStatus,
//			@AttrRef("registration")Registration registration,
//			@AttrRef("injection") Injection injection) {
//		this.id = nextID(id);
//		this.name = name;
//		this.dob = dob;
//		this.gender = gender;
//		this.injectRegion = injectRegion;
//		this.phoneNumber = phoneNumber;
//		this.healthStatus = healthStatus;
//
//		this.registration = registration;
//		this.injection = injection;
//		
//		recordCount = 0;
//		injectionRecords = new ArrayList<>();
//	}

	public String getId() {
		    return id;
		  }

	public String getName() {
		return name;
	}

	public InjectRegion getInjectRegion() {
		return injectRegion;
	}

	public void setInjectRegion(InjectRegion injectRegion) {
		this.injectRegion = injectRegion;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) throws ConstraintViolationException {
	    // additional validation on dob
	    if (dob.before(DToolkit.MIN_DOB)) {
	      throw new ConstraintViolationException(DExCode.INVALID_DOB, dob);
	    }
	    
	    this.dob = dob;
	  }

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}


//	@DOpt(type = DOpt.Type.LinkUpdater)
//	public void setinjectRegion(InjectRegion injectRegion) {
//		this.injectRegion = injectRegion;
////		int count = injectRegion.getInjectPeopleCount();
////		injectRegion.setInjectPeopleCount(count ++);
////		injectRegion.computePercent();
//	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) throws ConstraintViolationException{
		if(phoneNumber.length() < 10) {
			throw new ConstraintViolationException(DExCode.INVALID_PHONE_NUMBER, phoneNumber);
		  }
		  for(int i=0; i<phoneNumber.length(); i++) {
			  char ch = phoneNumber.charAt(i);
			  if(!Character.isDigit(ch)) {
				  throw new ConstraintViolationException(DExCode.INVALID_PHONE_NUMBER, phoneNumber);
			  }
		  }
		this.phoneNumber = phoneNumber;
	}
	

	public Injection getInjection() {
		return injection;
	}
	
//	@DOpt(type=DOpt.Type.LinkAdderNew)
//	public void setNewInjection(Injection injection) {
//		setInjection(injection);
//	}
	
//	@DOpt(type=DOpt.Type.LinkAdderNew)
//	public void setNewInjection(Injection injection) {
//		this.injection = injection;
//	}


	public void setInjection(Injection injection) throws ConstraintViolationException{
		if(registration != null) {
			this.injection = injection;
		}else {
			throw new ConstraintViolationException(DExCode.INJECTION_MUST_HAPPEN_AFTER_REGISTRATION, injection);
		}
	}

	public HealthStatus getHealthStatus() {
		return healthStatus;
	}

	public void setHealthStatus(HealthStatus healthStatus) {
		this.healthStatus = healthStatus;
	}

	
	public Registration getRegistration() {
		return registration;
	}
	
	
	@DOpt(type=DOpt.Type.LinkAdderNew)
	public void setNewRegistration(Registration registration) {
		this.registration = registration;
	}
	
	public void setRegistration(Registration registration) {
		this.registration = registration;
	}

//	@DOpt(type=DOpt.Type.LinkUpdater)
//	public boolean updateInjection(Injection injection) {
//		this.injection = injection;
//		return true;
//	}
//	
//	@DOpt(type=DOpt.Type.LinkUpdater)
//	public boolean updateRegistration(Registration r) {
//		this.registration = r;
//		return true;
//	}
	
	@DOpt(type=DOpt.Type.LinkAdder)
	  public boolean addInjectionRecord(InjectionRecord i) {
		  if(!injectionRecords.contains(i))
			  injectionRecords.add(i);
		  return false;
	  }
	  
	  @DOpt(type=DOpt.Type.LinkAdderNew)
	  public boolean addNewInjectionRecord(InjectionRecord i) {
		  injectionRecords.add(i);
		  recordCount ++;
		  return false;
	  }
	  
	  @DOpt(type=DOpt.Type.LinkAdder)
	  public boolean addInjectionRecords(Collection<InjectionRecord> is) {
		  boolean added = false;
		  for(InjectionRecord i : is) {
			  if(!injectionRecords.contains(i)) {
				  if(!added) added = true;
				  injectionRecords.add(i);
			  }
		  }
		  return false;
	  }
	  
	  @DOpt(type=DOpt.Type.LinkAdderNew)
	  public boolean addNewInjectionRecords(Collection<InjectionRecord> is) {
		  injectionRecords.addAll(is);
		  recordCount += is.size();
		  return false;
	  }
	  
	  @DOpt(type=DOpt.Type.LinkRemover)
	  public boolean removeInjectionRecord(InjectionRecord i) {
		  boolean removed = injectionRecords.remove(i);
		  if(removed) {
			  recordCount --;
		  }
		  return false;
	  }


	public void setInjectionRecords(Collection<InjectionRecord> injectionRecords) {
		this.injectionRecords = injectionRecords;
		recordCount = injectionRecords.size();
	}
	
	public Collection<InjectionRecord> getInjectionRecords() {
	    return injectionRecords;
	  }
	
	public PeopleByNameReport getRptPersonByName() {
		return rptPersonByName;
	}
	
	

//	@Override
//	public String toString() {
//		return "Person [id=" + id + ", name=" + name + ", dob=" + dob + ", gender=" + gender + ", injectRegion="
//				+ injectRegion + ", phoneNumber=" + phoneNumber + ", healthStatus=" + healthStatus + ", injection="
//				+ injection + "]";
//	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	

	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", dob=" + dob + ", gender=" + gender + ", injectRegion="
				+ injectRegion + ", phoneNumber=" + phoneNumber + ", healthStatus=" + healthStatus + ", injection="
				+ injection + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	  
	private String nextID(String id) throws ConstraintViolationException{
		if(id == null) {
			if(idCounter == 0) {
				//current year
				idCounter = Calendar.getInstance().get(Calendar.YEAR);
			}else {
				idCounter ++;
			}
			//form: "P + current year"
			return "P" + idCounter;
		}else {
			int num;
			try {
				//take out the year in ID (exclude "P")
				num = Integer.parseInt(id.substring(1));
			}catch(RuntimeException e) {
				throw new ConstraintViolationException(ConstraintViolationException.Code.INVALID_VALUE,
						e, new Object[] {id});//create new anonymous array of object with one parameter id
			}
			if(num > idCounter) {
				idCounter = num;
			}
			return id;
		}
	}
	
	 /**
	   * @requires 
	   *  minVal != null /\ maxVal != null
	   * @effects 
	   *  update the auto-generated value of attribute <tt>attrib</tt>, specified for <tt>derivingValue</tt>, using <tt>minVal, maxVal</tt>
	   */
	@DOpt(type=DOpt.Type.AutoAttributeValueSynchroniser)
	  public static void updateAutoGeneratedValue(
	      DAttr attrib,
	      Tuple derivingValue, 
	      Object minVal, 
	      Object maxVal) throws ConstraintViolationException {
	    
	    if (minVal != null && maxVal != null) {
	      //TODO: update this for the correct attribute if there are more than one auto attributes of this class 
	    	if (attrib.name().equals("id")) { 
			  String maxId = (String) maxVal;
			  
			  try {
			    int maxIdNum = Integer.parseInt(maxId.substring(1));
			    
			    if (maxIdNum > idCounter) // extra check
			      idCounter = maxIdNum;
			    
			  } catch (RuntimeException e) {
			    throw new ConstraintViolationException(
			        ConstraintViolationException.Code.INVALID_VALUE, e, new Object[] {maxId});
			  }
	    	}
	    }
	  }
	  
	}