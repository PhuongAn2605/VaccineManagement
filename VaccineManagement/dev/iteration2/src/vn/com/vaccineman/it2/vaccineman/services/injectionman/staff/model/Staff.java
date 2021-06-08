package vn.com.vaccineman.it2.vaccineman.services.injectionman.staff.model;

import java.util.Calendar;
import java.util.Date;

import domainapp.basics.exceptions.ConstraintViolationException;
import domainapp.basics.model.meta.AttrRef;
import domainapp.basics.model.meta.DAttr;
import domainapp.basics.model.meta.DOpt;
import domainapp.basics.model.meta.DAttr.Format;
import domainapp.basics.model.meta.DAttr.Type;
import domainapp.basics.model.meta.DClass;
import domainapp.basics.util.Tuple;
import vn.com.exceptions.DExCode;
import vn.com.utils.DToolkit;
import vn.com.vaccineman.services.enums.Gender;
import vn.com.vaccineman.it2.vaccineman.services.injectionman.staff.address.Address;
import vn.com.vaccineman.it2.vaccineman.services.injectionman.staff.report.StaffByNameReport;


@DClass(schema="vaccineman")
public abstract class Staff {
	
	public static final String A_id = "id";
	public static final String A_name = "name";
	public static final String A_dob = "dob";
	public static final String A_gender= "gender";
	public static final String A_address= "address";
	public static final String A_phoneNumber = "phoneNumber";
	public static final String A_rptStaffByName = "rptStaffByName";
	
	@DAttr(name=A_id, id=true, type=Type.String, auto=true, length=5,
			mutable=false, optional=false)
	private String id;
	
	private static int idCounter = 0;
	
	@DAttr(name = A_name, type = Type.String, length = 20, optional = false, cid=true)
	private String name;
	
	@DAttr(name = A_dob, type = Type.Date, format = Format.Date, length = 10, optional = true)
	private Date dob;
	
	@DAttr(name = A_gender, type = Type.Domain, length = 10, optional = true)
	private Gender gender;
	
	@DAttr(name = A_address, type = Type.Domain, length = 15, optional =false)
	private Address address;
	
	@DAttr(name = A_phoneNumber, type = Type.String, length = 15, optional = true)
	private String phoneNumber;

	@DAttr(name=A_rptStaffByName,type=Type.Domain, serialisable=false, 
		      // IMPORTANT: set virtual=true to exclude this attribute from the object state
		      // (avoiding the view having to load this attribute's value from data source)
		      virtual=true)
		  private StaffByNameReport rptStaffByName;
	
	@DOpt(type=DOpt.Type.ObjectFormConstructor)
	@DOpt(type=DOpt.Type.RequiredConstructor)
	public Staff(@AttrRef("name")String name, 
			@AttrRef("address")Address address) {
		this(null, name, null, null, address, null);
	}

	@DOpt(type=DOpt.Type.ObjectFormConstructor)
	public Staff(@AttrRef("name")String name, 
			@AttrRef("address")Address address, 
			@AttrRef("phoneNumber")String phoneNumber) {
		this(null, name, null, null, address, phoneNumber);
	}
	
	@DOpt(type=DOpt.Type.ObjectFormConstructor)
	public Staff(@AttrRef("name")String name, 
			@AttrRef("address")Address address,
			@AttrRef("dob")Date dob,
			@AttrRef("phoneNumber")String phoneNumber) {
		this(null, name, dob, null, address, phoneNumber);
	}
	
	@DOpt(type=DOpt.Type.ObjectFormConstructor)
	public Staff(@AttrRef("name")String name, 
			@AttrRef("address")Address address,
			@AttrRef("dob")Date dob,
			@AttrRef("gender")Gender gender, 
			@AttrRef("phoneNumber")String phoneNumber) {
		this(null, name, dob, gender, address, phoneNumber);
	}

	@DOpt(type=DOpt.Type.DataSourceConstructor)
	public Staff(@AttrRef("id")String id, 
			@AttrRef("name")String name, 
			@AttrRef("dob")Date dob, 
			@AttrRef("gender")Gender gender, 
			@AttrRef("address")Address address, 
			@AttrRef("phoneNumber")String phoneNumber) throws ConstraintViolationException{
		this.id = nextID(id);
		this.name = name;
		this.dob = dob;
		this.gender = gender;
		this.address = address;
//		this.phoneNumber = phoneNumber;
		setPhoneNumber(phoneNumber);
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

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

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
	
	@Override
	public String toString() {
		return "Staff [id=" + id + ", name=" + name + ", dob=" + dob + ", gender=" + gender + ", address=" + address
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
		Staff other = (Staff) obj;
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
}
