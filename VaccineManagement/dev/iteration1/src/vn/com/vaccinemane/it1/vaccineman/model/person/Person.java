package vn.com.vaccinemane.it1.vaccineman.model.person;

import java.util.Calendar;
import java.util.Date;

import domainapp.basics.exceptions.ConstraintViolationException;
import domainapp.basics.model.meta.AttrRef;
import domainapp.basics.model.meta.DAssoc;
import domainapp.basics.model.meta.DAttr;
import domainapp.basics.model.meta.DAttr.Format;
import domainapp.basics.model.meta.DAttr.Type;
import domainapp.basics.util.Tuple;
import vn.com.utils.DToolkit;
import vn.com.vaccinemane.it1.exceptions.DExCode;
import vn.com.vaccinemane.it1.vaccineman.model.doctor.Doctor;
import vn.com.vaccinemane.it1.vaccineman.model.enums.Gender;
import vn.com.vaccinemane.it1.vaccineman.model.enums.HealthStatus;
import vn.com.vaccinemane.it1.vaccineman.model.registration.Registration;
import domainapp.basics.model.meta.DClass;
import domainapp.basics.model.meta.DOpt;
import domainapp.basics.model.meta.DAssoc.AssocEndType;
import domainapp.basics.model.meta.DAssoc.AssocType;
import domainapp.basics.model.meta.DAssoc.Associate;

@DClass(schema="vaccineman")
public abstract class Person {

	public static final String A_id = "id";
	public static final String A_name = "name";
	public static final String A_dob = "dob";
	public static final String A_gender= "gender";
	public static final String A_address= "address";
	public static final String A_department = "department";
	public static final String A_phoneNumber = "phoneNumber";
//	public static final String A_job = "job";
	public static final String A_healthStatus = "healthStatus";
	
	@DAttr(name=A_id, id=true, type=Type.String, auto=true, length=5,
			mutable=false, optional=false)
	private String id;
	
	private static int idCounter = 0;
	
	@DAttr(name = A_name, type = Type.String, length = 20, optional = false)
	  private String name;
	  
	  @DAttr(name = A_dob, type = Type.Date, format=Format.Date, length = 10, optional = false)
	  private Date dob;
	  
	  @DAttr(name = A_gender, type = Type.Domain, length=10, optional = true)
	  private Gender gender;
	  
	  @DAttr(name = A_address, type = Type.String, length = 15, optional = false)
	  private String address;
	  
	  @DAttr(name = A_phoneNumber, type = Type.String, length = 15, optional = false)
	  private String phoneNumber;
	  
	  
	  @DAttr(name = A_healthStatus, type = Type.Domain, length = 10, optional = false)
	  private HealthStatus healthStatus;
	  
	  @DAttr(name = "doctor", type = Type.Domain, length = 10, optional = false)
	  @DAssoc(ascName = "doctor-has-people", role = "person", 
	    ascType = AssocType.One2Many, endType = AssocEndType.Many, 
	    associate = @Associate(type = Doctor.class, cardMin = 1, cardMax = 1), dependsOn = true)
	  private Doctor doctor;
	  
	  //?add determinent=true -> input -> can not select
	  @DAttr(name ="registration", type = Type.Domain, length = 10, optional = true)
	  @DAssoc(ascName="person-has-registration",role="person",
	  ascType=AssocType.One2One, endType=AssocEndType.One,
	  associate=@Associate(type=Registration.class,cardMin=1,cardMax=1))
	  private Registration registration;

	  @DOpt(type=DOpt.Type.ObjectFormConstructor)
	  @DOpt(type=DOpt.Type.RequiredConstructor)
	public Person(@AttrRef("name")String name, 
			@AttrRef("dob")Date dob, 
			@AttrRef("address")String address, 
			@AttrRef("phoneNumber")String phoneNumber,
			@AttrRef("healthStatus")HealthStatus healthStatus
			) {
		this(null, name, dob, null, address, phoneNumber,healthStatus, null, null);
	}
	  
	  @DOpt(type=DOpt.Type.ObjectFormConstructor)
	public Person(@AttrRef("name")String name, 
			@AttrRef("dob")Date dob,
			@AttrRef("gender")Gender gender,
			@AttrRef("address")String address, 
			@AttrRef("phoneNumber")String phoneNumber,
			@AttrRef("healthStatus")HealthStatus healthStatus,
			@AttrRef("doctor") Doctor doctor,
			@AttrRef("registration")Registration registration) {
		this(null, name, dob, gender, address, phoneNumber,healthStatus, doctor, registration);
	}
	  @DOpt(type=DOpt.Type.ObjectFormConstructor)
		public Person(@AttrRef("name")String name, 
				@AttrRef("dob")Date dob,
				@AttrRef("gender")Gender gender,
				@AttrRef("address")String address, 
				@AttrRef("phoneNumber")String phoneNumber,
				@AttrRef("healthStatus")HealthStatus healthStatus,
				@AttrRef("doctor") Doctor doctor) {
			this(null, name, dob, gender, address, phoneNumber,healthStatus, doctor, null);
		}
	  @DOpt(type=DOpt.Type.ObjectFormConstructor)
		public Person(@AttrRef("name")String name, 
				@AttrRef("dob")Date dob,
				@AttrRef("address")String address, 
				@AttrRef("phoneNumber")String phoneNumber,
				@AttrRef("job")String job,
				@AttrRef("healthStatus")HealthStatus healthStatus,
				@AttrRef("doctor") Doctor doctor,
				@AttrRef("registration")Registration registration) {
			this(null, name, dob, null, address, phoneNumber,healthStatus, doctor, registration);
		}
	  
	  @DOpt(type=DOpt.Type.ObjectFormConstructor)
		public Person(@AttrRef("name")String name, 
				@AttrRef("dob")Date dob,
				@AttrRef("gender")Gender gender,
				@AttrRef("address")String address, 
				@AttrRef("phoneNumber")String phoneNumber,
				@AttrRef("job")String job,
				@AttrRef("healthStatus")HealthStatus healthStatus,
				@AttrRef("doctor") Doctor doctor, 
				@AttrRef("registration")Registration registration) {
			this(null, name, dob, gender, address, phoneNumber,healthStatus, doctor, registration);
		}

	@DOpt(type=DOpt.Type.DataSourceConstructor)
	public Person(@AttrRef("id")String id, 
			@AttrRef("name")String name, 
			@AttrRef("dob")Date dob, 
			@AttrRef("gender")Gender gender, 
			@AttrRef("address")String address, 
			@AttrRef("phoneNumber")String phoneNumber,
			@AttrRef("healthStatus")HealthStatus healthStatus,
			@AttrRef("doctor") Doctor doctor,
			@AttrRef("registration")Registration registration) {
		this.id = nextID(id);
		this.name = name;
		this.dob = dob;
		this.gender = gender;
		this.address = address;
		this.phoneNumber = phoneNumber;
//		this.job = job;
		this.healthStatus = healthStatus;
		this.doctor = doctor;
		this.registration = registration;
	}

	  public String getId() {
		    return id;
		  }
	  
	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public String getName() {
		return name;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

//	public String getJob() {
//		return job;
//	}
//
//	public void setJob(String job) {
//		this.job = job;
//	}

	public HealthStatus getHealthStatus() {
		return healthStatus;
	}

	public void setHealthStatus(HealthStatus healthStatus) {
		this.healthStatus = healthStatus;
	}

	
	public Registration getRegistration() {
		return registration;
	}

	public void setRegistration(Registration registration) {
		this.registration = registration;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", dob=" + dob + ", gender=" + gender + ", address=" + address
				+ ", phoneNumber=" + phoneNumber + ", healthStatus=" + healthStatus + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
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
	  
	  public int compareTo(Object o) {
		    if (o == null || (!(o instanceof Person)))
		      return -1;

		    Person p = (Person) o;

		    return this.doctor.getId().compareTo(p.doctor.getId());
		  }
	}
