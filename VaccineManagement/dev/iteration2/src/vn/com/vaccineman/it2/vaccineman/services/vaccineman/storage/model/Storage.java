package vn.com.vaccineman.it2.vaccineman.services.vaccineman.storage.model;

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
import domainapp.basics.model.meta.DAttr.Format;
import domainapp.basics.model.meta.DAttr.Type;
import domainapp.basics.util.Tuple;
import vn.com.exceptions.DExCode;
import vn.com.vaccineman.it2.vaccineman.services.injectionman.registration.model.Registration;
import vn.com.vaccineman.it2.vaccineman.services.vaccineman.country.Country;
import vn.com.vaccineman.it2.vaccineman.services.vaccineman.storage.report.RemainVaccineQuantityReportLessThan;
import vn.com.vaccineman.it2.vaccineman.services.vaccineman.vaccine.model.Vaccine;
import vn.com.vaccineman.it2.vaccineman.services.vaccineman.vaccineCenter.model.VaccineCenter;

@DClass(schema="vaccineman")
public class Storage {
	public static final String A_id = "id";
	public static final String A_name= "name";
	public static final String A_vaccine = "vaccine";
	public static final String A_capacity = "capacity";
	public static final String A_quantity = "quantity";
	public static final String A_usedQuantity = "usedQuantity";
	public static final String A_remainQuantity = "remainQuantity";
	
	public static final String A_rptRemainVaccineQuantityLessThan = "rptRemainVaccineQuantityLessThan";
	
	@DAttr(name=A_id, id=true, type=Type.String, auto=true, length=6,
			mutable=false, optional=false)
	private String id;
	
	private static int idCounter = 0;
	
	
	@DAttr(name = A_name, type = Type.String, length = 20, optional = false, cid=true)
	  private String name;
	
	@DAttr(name = A_capacity, type = Type.Double, length = 10, optional = false)
	  private double capacity;
	
	@DAttr(name =A_vaccine, type = Type.Domain, length = 20, optional = false, cid=true)
	  private Vaccine vaccine;
	
	@DAttr(name = A_quantity, type = Type.Long, length = 10, optional = false)
	  private long quantity;
	
	@DAttr(name = A_usedQuantity, type = Type.Long, length = 20, mutable = false, auto = true, serialisable = true, optional = true)
	private long usedQuantity;
	
	@DAttr(name = A_remainQuantity, type = Type.Long, length = 20, optional = true, mutable = false, auto = true, serialisable = true, derivedFrom = {
			A_quantity, A_usedQuantity })
	private long remainQuantity;
	
	 @DAttr(name = "vaccineCenter", type = Type.Domain, length = 10, optional = false)
	  @DAssoc(ascName = "vaccineCenter-has-storages", role = "storage", 
	    ascType = AssocType.One2Many, endType = AssocEndType.Many, 
	    associate = @Associate(type = VaccineCenter.class, cardMin = 1, cardMax = 1), dependsOn = true)
	  private VaccineCenter vaccineCenter;
	 
	

	@DAttr(name="registrations",type=Type.Collection,serialisable=false,
			filter=@Select(clazz=Registration.class))
	  @DAssoc(ascName="storage-has-registrations",role="storage",
	  ascType=AssocType.One2Many, endType=AssocEndType.One,
	  associate=@Associate(type=Registration.class,cardMin=0,cardMax=100))
	private Collection<Registration> registrations;
	
	private int registrationCount;
	
	@DAttr(name=A_rptRemainVaccineQuantityLessThan,type=Type.Domain, serialisable=false, virtual=true)
	private RemainVaccineQuantityReportLessThan rptRemainVaccineQuantityLessThan;
	
	@DOpt(type=DOpt.Type.ObjectFormConstructor)
	@DOpt(type=DOpt.Type.RequiredConstructor)
	public Storage(@AttrRef("name")String name,
			  @AttrRef("capacity" )Double capacity,
			  @AttrRef("vaccine" )Vaccine vaccine,
			  @AttrRef("quantity" )Long quantity,
			  @AttrRef("center" )VaccineCenter vaccineCenter
			  ) throws ConstraintViolationException {
		this(null, name,capacity, vaccine, quantity, 0L, 0L, vaccineCenter);
	
	
	}
		
		
		@DOpt(type=DOpt.Type.DataSourceConstructor)
		  public Storage(@AttrRef("id")String id, 
				  @AttrRef("name")String name,
				  @AttrRef("capacity")Double capacity,
				  @AttrRef("vaccine" )Vaccine vaccine,
				  @AttrRef("quantity" )Long quantity,
				  @AttrRef("usedQuantity" )Long usedQuantity,
				  @AttrRef("remainQuantity" )Long remainQuantity,
				  @AttrRef("center" )VaccineCenter vaccineCenter)
						  throws ConstraintViolationException {
			    // generate an id
			    this.id = nextID(id);
			    this.name= name;
			    this.vaccine = vaccine;
//			    this.quantity = quantity;
			    setCapacity(capacity);
			    setQuantity(quantity);
			    this.usedQuantity = usedQuantity;
			    this.remainQuantity = remainQuantity;
			    this.vaccineCenter = vaccineCenter;
//			    vaccinesCount= 0;
//			    vaccines = new ArrayList<>();
			    registrations = new ArrayList<>();
			    registrationCount = 0;
			    
			    //not update in constructor
			    updateUsedQuantity();
}
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
			Storage.idCounter = idCounter;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
		public VaccineCenter getVaccineCenter() {
			return vaccineCenter;
		}


		public void setVaccineCenter(VaccineCenter vaccineCenter) {
			this.vaccineCenter = vaccineCenter;
		}



		@Override
		public String toString() {
			return "Storage [id=" + id + ", name=" + name + ", capacity=" + capacity + ", vaccine=" + vaccine
					+ ", quantity=" + quantity + ", vaccineCenter=" + vaccineCenter + "]";
		}


public double getCapacity() {
	return capacity;
}


public void setCapacity(double capacity) throws ConstraintViolationException{
	if(capacity < 1000) {
		throw new ConstraintViolationException(DExCode.INVALID_CAPACITY, capacity);
	}
	this.capacity = capacity;
}


public Vaccine getVaccine() {
	return vaccine;
}


public void setVaccine(Vaccine vaccine) {
	this.vaccine = vaccine;
}


public long getQuantity() {
	return quantity;
}


public void setQuantity(long quantity) throws ConstraintViolationException{
	if(quantity < 1000) {
		throw new ConstraintViolationException(DExCode.INVALID_QUANTITY_IN_STORAGE, quantity);
	}
	this.quantity = quantity;
}


public long getUsedQuantity() {
	return usedQuantity;
}

//public void setUsedQuantity(long usedQuantity) {
//	this.usedQuantity = usedQuantity;
//}

//@DOpt(type = DOpt.Type.DerivedAttributeUpdater)
//@AttrRef(value = A_usedQuantity)
public boolean updateUsedQuantity() {
	this.usedQuantity = this.registrationCount;
	updateRemainQuantity();
	
	return true;
}

public long getRemainQuantity() {
	return remainQuantity;
}


//public void setRemainQuantity(long remainQuantity) {
//	this.remainQuantity = remainQuantity;
//}

@DOpt(type = DOpt.Type.DerivedAttributeUpdater)
@AttrRef(value = A_remainQuantity)
public boolean updateRemainQuantity() {
	this.remainQuantity = quantity - usedQuantity;
	return true;
}

@DOpt(type=DOpt.Type.LinkAdder)
public boolean addRegistration(Registration e) {
  if (!registrations.contains(e)) {
    registrations.add(e);
//    registrationCount ++;
//    updateUsedQuantity();
  }
  return false; 
}

@DOpt(type=DOpt.Type.LinkAdderNew)
public boolean addNeRegistration(Registration e) {
  registrations.add(e);
  
  registrationCount ++;
  updateUsedQuantity();
  
  // no other attributes changed (average mark is not serialisable!!!)
  return true; 
}

@DOpt(type=DOpt.Type.LinkAdder)
//@MemberRef(name="enrolments")
public boolean addRegistration(Collection<Registration> regiss) {
  boolean added = false;
  for (Registration e : regiss) {
    if (!registrations.contains(e)) {
      if (!added) added = true;
      registrations.add(e);
//      registrationCount ++;
//	    updateUsedQuantity();
    }
  }
  // IMPORTANT: enrolment count must be updated separately by invoking setEnrolmentCount
  // otherwise computeAverageMark (below) can not be performed correctly
  // WHY? average mark is not serialisable
//  enrolmentCount += enrols.size();

//  if (added) {
//    // avg mark is not serialisable so we need to compute it here
//    computeAverageMark();
//  }

  // no other attributes changed
  return false; 
}

@DOpt(type=DOpt.Type.LinkAdderNew)
public boolean addNewRegistration(Collection<Registration> enrols) {
  registrations.addAll(enrols);
  registrationCount+=enrols.size();
  updateUsedQuantity();

  // no other attributes changed (average mark is not serialisable!!!)
  return true; 
}

@DOpt(type=DOpt.Type.LinkRemover)
//@MemberRef(name="enrolments")
public boolean removeRegistration(Registration e) {
  boolean removed = registrations.remove(e);
  
  if (removed) {
    registrationCount--;
    updateUsedQuantity();
   
  }
  // no other attributes changed
  return true; 
}

public Collection<Registration> getRegistrations() {
	    return registrations;
	  }

//public void setRegistrations(Collection<Registration> registrations) {
//		this.registrations = registrations;
//		registrationCount = registrations.size();
//		updateUsedQuantity();
//	}

public boolean setRegistration(Collection<Registration> registrations) {
	this.registrations = registrations;
	registrationCount = registrations.size();
	updateUsedQuantity();
	return true;
}

@DOpt(type=DOpt.Type.LinkCountGetter)
public Integer getRegistrationCount() {
  return registrationCount;
  //return enrolments.size();
}



		public RemainVaccineQuantityReportLessThan getRptRemainVaccineQuantity() {
	return rptRemainVaccineQuantityLessThan;
}


		private String nextID(String id) throws ConstraintViolationException {
		    if (id == null) { // generate a new id
		      if (idCounter == 0) {
		        idCounter = Calendar.getInstance().get(Calendar.YEAR);
		      } else {
		        idCounter++;
		      }
		      return "S" + idCounter;
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

