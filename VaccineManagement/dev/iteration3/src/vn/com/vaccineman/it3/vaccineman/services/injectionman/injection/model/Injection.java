package vn.com.vaccineman.it3.vaccineman.services.injectionman.injection.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import domainapp.basics.exceptions.ConstraintViolationException;
import domainapp.basics.model.meta.AttrRef;
import domainapp.basics.model.meta.DAssoc;
import domainapp.basics.model.meta.DAttr;
import domainapp.basics.model.meta.DClass;
import domainapp.basics.model.meta.DOpt;
import domainapp.basics.model.meta.Select;
import domainapp.basics.model.meta.DAssoc.AssocEndType;
import domainapp.basics.model.meta.DAssoc.AssocType;
import domainapp.basics.model.meta.DAssoc.Associate;
import vn.com.exceptions.DExCode;
import vn.com.vaccineman.it3.vaccineman.services.injectionman.person.model.Person;
import vn.com.vaccineman.it3.vaccineman.services.injectionman.staff.model.Doctor;
import vn.com.vaccineman.services.enums.SideEffect;

import domainapp.basics.model.meta.DAttr.Format;
import domainapp.basics.model.meta.DAttr.Type;
import domainapp.basics.util.Tuple;

@DClass(schema="vaccineman")
public class Injection {
	public static final String A_id = "id";
	public static final String A_injectDate = "injectDate";
	public static final String A_doctor = "doctor";
	public static final String A_people = "people";
	public static final String A_numPeople = "numPeople";
//	public static final String A_sideEffect = "sideEffect";
	
	public static final String A_rptInjectionByDoctor = "rptInjectionByDoctor";
	public static final String A_rptInjectionByVaccineAndEffect = "rptInjectionByVaccineAndEffect";

	@DAttr(name=A_id, id=true, type=Type.String, auto=true, length=6,
			mutable=false, optional=false)
	private String id;
	
	private static int idCounter = 0;
	
	@DAttr(name = A_injectDate, type = Type.Date, format=Format.Date, length= 10, optional = false)
	private Date injectDate;
	
	@DAttr(name="doctor",  type=Type.Domain, length = 20, optional = false)
	@DAssoc(ascName="doctor-has-injections",role="injection",
	  ascType=AssocType.One2Many, endType=AssocEndType.Many,
	  associate=@Associate(type=Doctor.class,cardMin=1,cardMax=1))
	private Doctor doctor;
	
	@DAttr(name=A_people, type=Type.Collection, length=20, serialisable=false,
			filter=@Select(clazz=Person.class))
	@DAssoc(ascName="injection-has-people",role="injection",
	  ascType=AssocType.One2Many, endType=AssocEndType.One,
	  associate=@Associate(type=Person.class,cardMin=0,cardMax=100))
	private Collection<Person> people;
	
	private int peopleCount;
	
	
	@DAttr(name=A_numPeople,type=Type.Integer,length=20,mutable=false,
			auto=true, serialisable=true, optional=true)
	private int numPeople;
	

	@DOpt(type=DOpt.Type.ObjectFormConstructor)
	@DOpt(type=DOpt.Type.RequiredConstructor)
	public Injection(@AttrRef("injectDate")Date injectDate,
					@AttrRef("doctor")Doctor doctor) {
		this(null, injectDate, doctor, 0);
	}

	@DOpt(type=DOpt.Type.ObjectFormConstructor)
	public Injection(
					@AttrRef("injectDate")Date injectDate,
					@AttrRef("doctor")Doctor doctor,
					@AttrRef("numPeople")Integer numPeople) throws ConstraintViolationException{
		this(null, injectDate, doctor, numPeople);
	}
	
	@DOpt(type=DOpt.Type.ObjectFormConstructor)
	public Injection(@AttrRef("id")String id,
					@AttrRef("injectDate")Date injectDate,
					@AttrRef("doctor")Doctor doctor) throws ConstraintViolationException{
		this(id, injectDate, doctor, 0);
	}
	
	@DOpt(type=DOpt.Type.DataSourceConstructor)
	public Injection(@AttrRef("id")String id,
					@AttrRef("injectDate")Date injectDate,
					@AttrRef("doctor")Doctor doctor,
					@AttrRef("numPeople")Integer numPeople) throws ConstraintViolationException{
		this.id = nextID(id);
		this.injectDate = injectDate;
		this.doctor = doctor;
		this.numPeople = numPeople;
		
		people = new ArrayList<>();
		peopleCount = 0;
//		this.numPeople = people.size();
//		this.vaccineType = vaccineType;
//		this.sideEffect = sideEffect;
	}

	public Date getInjectDate() {
		return injectDate;
	}

	public void setInjectDate(Date injectDate) throws ConstraintViolationException{
//		if(injectDate.before(injectPerson.getRegistration().getRegisterDate())) {
//			throw new ConstraintViolationException(DExCode.INJECT_DATE_MUST_AFTER_REGISTER_DATE, injectDate);
//		}
		this.injectDate = injectDate;
	}


	
	//must have getId
	public String getId() {
		return id;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	@DOpt(type=DOpt.Type.LinkAdder)
	  public boolean addPerson(Person p) {
		  if(!people.contains(p)) {
			  people.add(p);
		  }
		  return false;
	  }
	  
	  @DOpt(type=DOpt.Type.LinkAdderNew)
	  public boolean addNewPerson(Person p) {
//		  people.add(p);
//		  if(p.getInjection() != null) {
			  people.add(p);
			  peopleCount ++;
			  updateNumPeople();
//			  System.out.println(numPeople);
//			  computePercent();
//		  }
		  return true;
	  }
	  
	  
	  @DOpt(type=DOpt.Type.LinkAdder)
	  public boolean addPeople(Collection<Person> rs) {
		  boolean added = false;
		  for(Person r : rs) {
			  if(!people.contains(r)) {
				  if(!added) added = true;
				  people.add(r);
//				  numPeople ++;
			  }
		  }
		  return false;
	  }
	  
	  @DOpt(type=DOpt.Type.LinkAdderNew)
	  public boolean addNewPeople(Collection<Person> ps) {
		  people.addAll(ps);
		  peopleCount += ps.size();
		  
		  updateNumPeople();
		  return true;
	  }
	  
	  @DOpt(type=DOpt.Type.LinkRemover)
	  public boolean removePerson(Person p) {
		  boolean removed = people.remove(p);
		  if(removed) {
//			  if(numPeople >0) {
			  peopleCount --;
			  updateNumPeople();
				  
//				  computePercent();
//			  }
//			  peopleCount --;
//			  
//			  computePercent();
		  }
		  return true;
	  }
	  
	  
	  public int getNumPeople() {
		return numPeople;
	}

	public Collection<Person> getPeople() {
		    return people;
		  }
	
	 

	@DOpt(type=DOpt.Type.LinkCountSetter)
	  public void setPeopleCount(int peopleCount) {
		this.peopleCount = peopleCount;
	}
	
	@DOpt(type=DOpt.Type.LinkCountGetter)
	public int getPeopleCount() {
		return peopleCount;
	}

//	  @DOpt(type = DOpt.Type.DerivedAttributeUpdater)
//	  @AttrRef(value = A_numPeople)
	public void updateNumPeople() {
		this.numPeople = this.peopleCount;
	}
//	public int getPeopleCount() {
//		return peopleCount;
//	}

	
	public void setPeople(Collection<Person> people) {
		this.people = people;
		peopleCount = people.size();
		updateNumPeople();
//		for(Person p : people) {
////			if(p.getInjection() != null) {
//				numPeople ++;
////			}
//		}
		
//		computePercent();
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
		Injection other = (Injection) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


	private String nextID(String id) throws ConstraintViolationException {
	    if (id == null) { // generate a new id
	      if (idCounter == 0) {
	        idCounter = Calendar.getInstance().get(Calendar.YEAR);
	      } else {
	        idCounter++;
	      }
	      return "I" + idCounter;
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

	  @Override
	public String toString() {
		return "Injection [id=" + id + ", injectDate=" + injectDate + ", doctor=" + doctor + "]";
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