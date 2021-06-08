package vn.com.vaccinemane.it1.vaccineman.model.doctor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import domainapp.basics.exceptions.ConstraintViolationException;
import domainapp.basics.model.meta.AttrRef;
import domainapp.basics.model.meta.DAssoc;
import domainapp.basics.model.meta.DAttr;
import domainapp.basics.model.meta.DAttr.Format;
import domainapp.basics.model.meta.DAttr.Type;
import domainapp.basics.util.Tuple;
import  vn.com.vaccinemane.it1.vaccineman.model.enums.Gender;
import  vn.com.vaccinemane.it1.vaccineman.model.person.Person;
//import vn.com.vaccineman.it2.vaccineman.model.Student;
import domainapp.basics.model.meta.DClass;
import domainapp.basics.model.meta.DOpt;
import domainapp.basics.model.meta.Select;
import domainapp.basics.model.meta.DAssoc.AssocEndType;
import domainapp.basics.model.meta.DAssoc.AssocType;
import domainapp.basics.model.meta.DAssoc.Associate;

@DClass(schema="vaccineman")
public class Doctor {
	public static final String A_id = "id";
	public static final String A_name = "doctorName";
	public static final String A_dob = "dob";
	public static final String A_gender= "gender";
	public static final String A_address= "address";
	public static final String A_department = "department";
	public static final String A_phoneNumber = "phoneNumber";
	
	@DAttr(name=A_id, id=true, type=Type.String, auto=true, length=6,
			mutable=false, optional=false)
	private String id;
	
	private static int idCounter = 0;
	
	@DAttr(name = A_name, type = Type.String, length = 20, optional = false)
	  private String doctorName;
	  
	  @DAttr(name = A_dob, type = Type.Date, format=Format.Date, length = 15, optional = false)
	  private Date dob;
	  
	  @DAttr(name = A_gender, type = Type.Domain, length = 10, optional = true)
	  private Gender gender;
	  
	  @DAttr(name = A_address, type = Type.String, length = 15, optional = false)
	  private String address;

	  @DAttr(name = A_department, type = Type.String, length = 15, optional = false)
	  private String department;
	  
	  @DAttr(name = A_phoneNumber, type = Type.String, length = 12, optional = false)
	  private String phoneNumber;
	  
	  @DAttr(name="injectedPeople",type=Type.Collection,optional = true,
		      serialisable=false,filter=@Select(clazz=Person.class))
		  @DAssoc(ascName="doctor-has-people",role="doctor",
		      ascType=AssocType.One2Many,endType=AssocEndType.One,
		    associate=@Associate(type=Person.class,cardMin=0,cardMax=30))
	  private Collection<Person> injectedPeople; 
	  
	  private int personCount;

	  
	  @DOpt(type=DOpt.Type.ObjectFormConstructor)
	  @DOpt(type=DOpt.Type.RequiredConstructor)
	  public Doctor(@AttrRef("doctorName") String doctorName, 
	      @AttrRef("dob") Date dob,
	      @AttrRef("address") String address, 
	      @AttrRef("department") String department,
	      @AttrRef("phoneNumber") String phoneNumber ) {
	    this(null, doctorName, dob, null, address, department, phoneNumber);
	  }

	  @DOpt(type=DOpt.Type.ObjectFormConstructor)
	  public Doctor(@AttrRef("doctorName") String doctorName, 
	      @AttrRef("dob") Date dob,
	      @AttrRef("gender") Gender gender,
	      @AttrRef("address") String address, 
	      @AttrRef("department") String department,
	      @AttrRef("phoneNumber") String phoneNumber ) {
	    this(null, doctorName, dob, gender, address, department, phoneNumber);
	  }
	  
	  // a shared constructor that is invoked by other constructors
	  @DOpt(type=DOpt.Type.DataSourceConstructor)
	  public Doctor(@AttrRef("id")String id, 
			  @AttrRef("doctorName")String doctorName,
			  @AttrRef("dob")Date dob,
			  @AttrRef("gender")Gender gender, 
			  @AttrRef("address")String address, 
			  @AttrRef("department")String department, 
			  @AttrRef("phoneNumber")String phoneNumber) 
	  throws ConstraintViolationException {
	    // generate an id
	    this.id = nextID(id);

	    // assign other values
	    this.doctorName = doctorName;
	    this.dob = dob;
	    this.gender = gender;
	    this.address = address;
	    this.department = department;
	    this.phoneNumber = phoneNumber;
	    
	    personCount =0;
	    injectedPeople = new ArrayList<>();
	  }

	  // setter methods

	  

	  // v2.7.3
//	  public void setNewAddress(City address) {
//	    // change this invocation if need to perform other tasks (e.g. updating value of a derived attribtes)
//	    setAddress(address);
//	  }


	  public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public static int getIdCounter() {
		return idCounter;
	}

	public static void setIdCounter(int idCounter) {
		Doctor.idCounter = idCounter;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String name) {
		this.doctorName = name;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
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

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	  
	  
	  @DOpt(type=DOpt.Type.LinkAdder)
	  public boolean addInjectedPerson(Person p) {
		  if(!injectedPeople.contains(p))
			  injectedPeople.add(p);
		  return false;
	  }
	  
	  @DOpt(type=DOpt.Type.LinkAdderNew)
	  public boolean addNewInjectedPerson(Person p) {
		  injectedPeople.add(p);
		  personCount ++;
		  return false;
	  }
	  
	  @DOpt(type=DOpt.Type.LinkAdder)
	  public boolean addInjectedPeople(Collection<Person> ps) {
		  boolean added = false;
		  for(Person p : ps) {
			  if(!injectedPeople.contains(p)) {
				  if(!added) added = true;
				  injectedPeople.add(p);
			  }
		  }
		  return false;
	  }
	  
	  @DOpt(type=DOpt.Type.LinkAdderNew)
	  public boolean addNewInjectedPeople(Collection<Person> ps) {
		  injectedPeople.addAll(ps);
		  personCount += ps.size();
		  return false;
	  }
	  
	  @DOpt(type=DOpt.Type.LinkRemover)
	  public boolean removeInjectedPerson(Person e) {
		  boolean removed = injectedPeople.remove(e);
		  if(removed) {
			  personCount --;
		  }
		  return false;
	  }

	  public Collection<Person> getInjectedPeople() {
		    return injectedPeople;
		  }

		  @DOpt(type=DOpt.Type.LinkCountGetter)
		  public Integer getPersonCount() {
		    return personCount;
		    //return enrolments.size();
		  }

		  @DOpt(type=DOpt.Type.LinkCountSetter)
		  public void setPersonCount(int count) {
		    personCount = count;
		  }
		  
		public String toString(boolean full) {
			if(full)
				return "Doctor [id=" + id + ", name=" + doctorName + ", dob=" + dob + ", gender=" + gender + ", address="
				+ address + ", department=" + department + ", phoneNumber=" + phoneNumber + ", people=" + injectedPeople
				+ ", personCount=" + personCount + "]";
			else
				return "Doctor(" + id + ")";
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
		Doctor other = (Doctor) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	  
	  // automatically generate the next student id
	  private String nextID(String id) throws ConstraintViolationException {
	    if (id == null) { // generate a new id
	      if (idCounter == 0) {
	        idCounter = Calendar.getInstance().get(Calendar.YEAR);
	      } else {
	        idCounter++;
	      }
	      return "D" + idCounter;
	    } else {
	      // update id
	      int num;
	      try {
	        num = Integer.parseInt(id.substring(1));
	      } catch (RuntimeException e) {
	        throw new ConstraintViolationException(
	            ConstraintViolationException.Code.INVALID_VALUE, e, new Object[] { id });
	      }
	      
	      if (num > idCounter) {
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
}


