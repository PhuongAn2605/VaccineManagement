package vn.com.vaccinemane.it1.vaccineman.model.registration;

import java.util.Calendar;
import java.util.Date;

import domainapp.basics.exceptions.ConstraintViolationException;
import domainapp.basics.model.meta.AttrRef;
import domainapp.basics.model.meta.DAssoc;
import domainapp.basics.model.meta.DAttr;
import domainapp.basics.model.meta.DAttr.Format;
import domainapp.basics.model.meta.DAttr.Type;
import domainapp.basics.model.meta.DClass;
import domainapp.basics.util.Tuple;
import domainapp.basics.model.meta.DOpt;
import domainapp.basics.model.meta.DAssoc.AssocEndType;
import domainapp.basics.model.meta.DAssoc.AssocType;
import domainapp.basics.model.meta.DAssoc.Associate;
import vn.com.vaccinemane.it1.exceptions.DExCode;
import vn.com.vaccinemane.it1.utils.DToolkit;
import  vn.com.vaccinemane.it1.vaccineman.model.person.Person;
import  vn.com.vaccinemane.it1.vaccineman.model.staff.SupportStaff;

@DClass(schema="vaccineman")
public class Registration {
	public static final String A_id = "id";
	public static final String A_registerDate = "registerDate";
//	public static final String A_vaccineCost = "vaccineCost";
	
	@DAttr(name=A_id, id=true, type=Type.String, auto=true, length=5, mutable=false, optional=false)
	private String id;
	
	private static int idCounter = 0;
	
	@DAttr(name="registerPerson", type=Type.Domain, length=15, optional=false)
	@DAssoc(ascName="person-has-registration",role="registration",
	  ascType=AssocType.One2One, endType=AssocEndType.One,
	  associate=@Associate(type=Person.class,cardMin=1,cardMax=1))
	private Person registerPerson;
	
	@DAttr(name="supportStaff",  type=Type.Domain, length=15, optional=false)
	@DAssoc(ascName="staff-has-registrations",role="registration",
	  ascType=AssocType.One2Many, endType=AssocEndType.One,
	  associate=@Associate(type=SupportStaff.class,cardMin=1,cardMax=1))
	private SupportStaff supportStaff;
	
	
//	private String vaccineType;
	
	@DAttr(name=A_registerDate, type=Type.Date, format=Format.Date, length=10, optional=false)
	private Date registerDate;

	

	@DOpt(type=DOpt.Type.ObjectFormConstructor)
	@DOpt(type=DOpt.Type.RequiredConstructor)
	public Registration(@AttrRef("registerPerson")Person registerPerson, 
	
			@AttrRef("registerDate")Date registerDate) {
		this(null, registerPerson,null, registerDate);
	}

	@DOpt(type=DOpt.Type.ObjectFormConstructor)
	public Registration(@AttrRef("registerPerson")Person registerPerson, 
			@AttrRef("supportStaff") SupportStaff supportStaff,
			@AttrRef("registerDate")Date registerDate) {
		this(null, registerPerson, supportStaff, registerDate);
	}

	@DOpt(type=DOpt.Type.DataSourceConstructor)
	public Registration(@AttrRef("id")String id,
			@AttrRef("registerPerson")Person registerPerson,
			@AttrRef("supportStaff")SupportStaff supportStaff,
			@AttrRef("registerDate")Date registerDate) {

		this.id = nextID(id);
		this.registerPerson = registerPerson;
		this.supportStaff = supportStaff;
		this.registerDate = registerDate;
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
			return "R" + idCounter;
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


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public Person getRegisterPerson() {
		return registerPerson;
	}


	public void setRegisterPerson(Person registerPerson) {
		this.registerPerson = registerPerson;
	}


	public SupportStaff getSupportStaff() {
		return supportStaff;
	}


	public void setSupportStaff(SupportStaff supportStaff) {
		this.supportStaff = supportStaff;
	}


	public Date getRegisterDate() {
		return registerDate;
	}


	public void setRegisterDate(Date registerDate) throws ConstraintViolationException{
		if(registerDate.before(DToolkit.MIN_REGISTER_DATE)) {
			throw new ConstraintViolationException(DExCode.INVALID_REGISTER_DATE, registerDate);
		}
		
		this.registerDate = registerDate;
	}


	@Override
	public String toString() {
		return "Registration [id=" + id + ", registerPerson=" + registerPerson + ", supportStaff=" + supportStaff
				+ ", registerDate=" + registerDate + "]";
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
		Registration other = (Registration) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
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
	  
