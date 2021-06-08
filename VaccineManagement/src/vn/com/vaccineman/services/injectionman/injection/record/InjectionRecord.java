package vn.com.vaccineman.services.injectionman.injection.record;

import java.util.Date;

import domainapp.basics.exceptions.ConstraintViolationException;
import domainapp.basics.model.meta.AttrRef;
import domainapp.basics.model.meta.DAssoc;
import domainapp.basics.model.meta.DAttr;
import domainapp.basics.model.meta.DClass;
import domainapp.basics.model.meta.DOpt;
import domainapp.basics.model.meta.DAssoc.AssocEndType;
import domainapp.basics.model.meta.DAssoc.AssocType;
import domainapp.basics.model.meta.DAssoc.Associate;
import domainapp.basics.model.meta.DAttr.Format;
import domainapp.basics.model.meta.DAttr.Type;
import domainapp.basics.util.Tuple;
import vn.com.vaccineman.services.enums.SideEffect;
import vn.com.vaccineman.services.injectionman.injection.model.Injection;
import vn.com.vaccineman.services.injectionman.injection.report.InjectionRecordsByVaccineAndEffectReport;
import vn.com.vaccineman.services.injectionman.person.model.Person;
import vn.com.vaccineman.services.vaccineman.vaccine.model.Vaccine;


@DClass(schema="vaccineman")
public class InjectionRecord {
	public static final String A_id= "id";
	public static final String A_recordDate = "recordDate";
	public static final String A_injectedPerson = "injectedPerson";
	public static final String A_injection = "injection";
	public static final String A_sideEffect = "sideEffect";
	public static final String A_rptInjectionRecordsByVaccineAndEffect = "rptInjectionRecordsByVaccineAndEffect";
	
	public static final String A_vaccineType = "vaccineType";
	
	@DAttr(name="id",id=true,auto=true,length=3,mutable=false,optional=false,type= DAttr.Type.Integer)
    private int id;
    private static int idCounter;
    
    @DAttr(name=A_recordDate,type= DAttr.Type.Date,format= Format.Date, length=20,optional=false)
	private Date recordDate;

    @DAttr(name=A_injectedPerson,  type=Type.Domain, length = 20, optional = false)
	@DAssoc(ascName="person-has-injection-records",role="injectionRecord",
	  ascType=AssocType.One2Many, endType=AssocEndType.Many,
	  associate=@Associate(type=Person.class,cardMin=1,cardMax=1))
	private Person injectedPerson;
	
    @DAttr(name="injection",type=Type.Domain,length=10,mutable=false,
			auto=true, serialisable=true, optional=true)
	private Injection injection;
    
    @DAttr(name="injectDate",type=Type.Date, format=Format.Date, length=20,mutable=false,
			auto=true, serialisable=true, optional=true)
	private Date injectDate;
    
    @DAttr(name="vaccineType",type=Type.Domain,length=15,mutable=false,
			auto=true, serialisable=true, optional=true)
	private Vaccine vaccineType;
	
    @DAttr(name="sideEffect",type= DAttr.Type.Domain,length=20,optional=false)
	private SideEffect sideEffect;
    
    @DAttr(name=A_rptInjectionRecordsByVaccineAndEffect,type=Type.Domain, serialisable=false, virtual=true)
	private InjectionRecordsByVaccineAndEffectReport rptInjectionRecordsByVaccineAndEffect;
    
    @DOpt(type=DOpt.Type.ObjectFormConstructor)
    @DOpt(type=DOpt.Type.RequiredConstructor)
   	public InjectionRecord(
   			@AttrRef("recordDate")Date recordDate, 
   			@AttrRef("injectedPerson")Person injectedPerson, 
   			@AttrRef("sideEffect") SideEffect sideEffect) {
   		this(null, recordDate, injectedPerson, null,null, null, sideEffect);
   	}
    
    @DOpt(type=DOpt.Type.ObjectFormConstructor)
   	public InjectionRecord(
   			@AttrRef("recordDate")Date recordDate, 
   			@AttrRef("injectedPerson")Person injectedPerson, 
   			@AttrRef("injection")Injection injection, 
   			@AttrRef("injectDate")Date injectDate,
   			@AttrRef("vaccineType")Vaccine vaccineType,
   			@AttrRef("sideEffect") SideEffect sideEffect) {
   		this(null, recordDate, injectedPerson, injection, injectDate, vaccineType, sideEffect);
   	}

    @DOpt(type=DOpt.Type.DataSourceConstructor)
	public InjectionRecord(@AttrRef("id")Integer id, 
			@AttrRef("recordDate")Date recordDate, 
			@AttrRef("injectedPerson")Person injectedPerson, 
			@AttrRef("injection")Injection injection, 
			@AttrRef("injectDate")Date injectDate,
			@AttrRef("vaccineType")Vaccine vaccineType,
			@AttrRef("sideEffect") SideEffect sideEffect) {
		this.id = nextId(id);
		this.recordDate = recordDate;
		this.injectedPerson = injectedPerson;
		this.injection = injection;
		this.injectDate = injectDate;
		this.vaccineType = vaccineType;
		this.sideEffect = sideEffect;
		
		setInjection();
		setVaccineType();
		setInjectDate();
	}
	
	private static int nextId(Integer currID) {
	    if (currID == null) {
	      idCounter++;
	      return idCounter;
	    } else {
	      int num = currID.intValue();
	      if (num > idCounter)
	        idCounter = num;
	      
	      return currID;
	    }
	  }

	public int getId() {
		return id;
	}

	public Date getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	public Person getInjectedPerson() {
		return injectedPerson;
	}

	public void setInjectedPerson(Person injectedPerson) {
		this.injectedPerson = injectedPerson;
	}

	public Injection getInjection() {
		return injection;
	}

	public void setInjection(Injection injection) {
		this.injection = injection;
	}

	public Date getInjectDate() {
		return injectDate;
	}

//	public void setInjectDate(Date injectDate) {
//		this.injectDate = injectDate;
//	}

//	public void setInjectDate() {
//		if(injection != null) {
//			this.injectDate = injection.getInjectDate();
//		}
//	}
	
	public Vaccine getVaccineType() {
		return vaccineType;
	}

//	public void setVaccineType(Vaccine vaccineType) {
//		this.vaccineType = vaccineType;
//	}
	
	public void setInjection() {
		if(injectedPerson != null && injectedPerson.getInjection() != null) {
			this.injection = injectedPerson.getInjection();
		}
	}
	
	public void setInjectDate() {
		if(injectedPerson != null && injectedPerson.getInjection() != null) {
			this.injectDate = injectedPerson.getInjection().getInjectDate();
		}
	}
	
	public void setVaccineType() {
		if(injectedPerson != null && injectedPerson.getRegistration() != null) {
			this.vaccineType = injectedPerson.getRegistration().getVaccineType();
		}
	}

//	@DOpt(type=DOpt.Type.LinkUpdater)
//	public boolean updateVaccine(Vaccine v) throws IllegalStateException{
//		int oldRemainQuan = v.getRemainQuantity(true);
//		
//		v.stateHist.put(v.A_remainQuantity, oldRemainQuan - 1);
//		v.stateHist.put(v.A_usedQuantity, v.getUsedQuantity() + 1);
//		return true;
//		
//	}
	public SideEffect getSideEffect() {
		return sideEffect;
	}

	public void setSideEffect(SideEffect sideEffect) {
		this.sideEffect = sideEffect;
	}

	
	
	public InjectionRecordsByVaccineAndEffectReport getRptInjectionRecordsByVaccineAndEffect() {
		return rptInjectionRecordsByVaccineAndEffect;
	}

	@Override
	public String toString() {
		return "InjectionRecord [id=" + id + ", recordDate=" + recordDate + ", injectedPerson=" + injectedPerson
				+ ", injection=" + injection + ", sideEffect=" + sideEffect + "]";
	}

	@DOpt(type = DOpt.Type.AutoAttributeValueSynchroniser)
	  public static void updateAutoGeneratedValue(DAttr attrib,
	      Tuple derivingValue, Object minVal, Object maxVal)
	      throws ConstraintViolationException {
	    if (minVal != null && maxVal != null) {
	      // check the right attribute
	      if (attrib.name().equals("id")) {
	        int maxIdVal = (Integer) maxVal;
	        if (maxIdVal > idCounter)
	          idCounter = maxIdVal;
	      }
	      // TODO add support for other attributes here
	    }
	  }
    
	
}
