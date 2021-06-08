package vn.com.vaccinemane.it1.vaccineman.model.staff;

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
import vn.com.vaccinemane.it1.exceptions.DExCode;
import vn.com.vaccinemane.it1.utils.DToolkit;
import  vn.com.vaccinemane.it1.vaccineman.model.enums.Gender;
import  vn.com.vaccinemane.it1.vaccineman.model.registration.Registration;

@DClass(schema="vaccineman")
public class SupportStaff {
	public static final String A_id = "id";
	public static final String A_name = "name";
	public static final String A_dob = "dob";
	public static final String A_gender= "gender";
	public static final String A_address= "address";
	public static final String A_phoneNumber = "phoneNumber";
	
	@DAttr(name=A_id, id=true, type=Type.String, auto=true, length=5,
			mutable=false, optional=false)
	private String id;
	
	private static int idCounter = 0;
	
	@DAttr(name = A_name, type = Type.String, length = 20, optional = false)
	  private String name;
	  
	  @DAttr(name = A_dob, type = Type.Date, length = 10, optional = false)
	  private Date dob;
	  
	  @DAttr(name = A_gender, type = Type.Domain, length = 10, optional = true)
	  private Gender gender;
	  
	  @DAttr(name = A_address, type = Type.String, length = 15, optional = false)
	  private String address;
	  
	  @DAttr(name = A_phoneNumber, type = Type.String, length = 15, optional = false)
	  private String phoneNumber;
	
	  
//	  @DAttr(name = "registrations", type =Type.Collection, optional = true,
//			  serialisable=true,filter=@Select(clazz=Registration.class))
//	  @DAssoc(ascName = "staff-has-registrations", role = "supportStaff", 
//	    ascType = AssocType.One2Many, endType = AssocEndType.One, 
//	    associate = @Associate(type = Registration.class, cardMin = 1, cardMax = 100))
	  private Collection<Registration> registrations;
	  
	  private int registrationCount;

	  @DOpt(type=DOpt.Type.ObjectFormConstructor)
	  @DOpt(type=DOpt.Type.RequiredConstructor)
	public SupportStaff(@AttrRef("name")String name, 
			@AttrRef("dob")Date dob, 
			@AttrRef("address")String address, 
			@AttrRef("phoneNumber")String phoneNumber) {
		this(null, name, dob, null, address, phoneNumber);
	}
	  
	  @DOpt(type=DOpt.Type.ObjectFormConstructor)
	public SupportStaff(@AttrRef("name")String name, 
			@AttrRef("dob")Date dob,
			@AttrRef("gender")Gender gender,
			@AttrRef("address")String address, 
			@AttrRef("phoneNumber")String phoneNumber) {
		this(null, name, dob, gender, address, phoneNumber);
	}

	@DOpt(type=DOpt.Type.DataSourceConstructor)
	public SupportStaff(@AttrRef("id")String id, 
			@AttrRef("name")String name, 
			@AttrRef("dob")Date dob, 
			@AttrRef("gender")Gender gender, 
			@AttrRef("address")String address, 
			@AttrRef("phoneNumber")String phoneNumber) {
		this.id = nextID(id);
		this.name = name;
		this.dob = dob;
		this.gender = gender;
		this.address = address;
		this.phoneNumber = phoneNumber;
		
		registrationCount = 0;
		registrations = new ArrayList<>();
	}

	

	public String getId() {
		    return id;
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
	
	@Override
	public String toString() {
		return "Registration [id=" + id + ", name=" + name + ", dob=" + dob + ", gender=" + gender + ", address=" + address
				+ ", phoneNumber=" + phoneNumber +  "]";
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
		SupportStaff other = (SupportStaff) obj;
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
			return "S" + idCounter;
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
	  
//	  public int compareTo(Object o) {
//		    if (o == null || (!(o instanceof Registration)))
//		      return -1;
//
//		    Registration p = (Registration) o;
//
//		    return this.doctor.getId().compareTo(p.doctor.getId());
//		  }
	}
