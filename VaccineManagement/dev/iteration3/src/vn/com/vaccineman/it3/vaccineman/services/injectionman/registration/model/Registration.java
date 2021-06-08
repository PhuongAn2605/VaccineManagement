package vn.com.vaccineman.it3.vaccineman.services.injectionman.registration.model;

import java.util.Calendar;
import java.util.Date;

//import org.graalvm.compiler.asm.aarch64.AArch64Assembler.DataCacheOperationType;

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
import vn.com.exceptions.DExCode;
import vn.com.utils.DToolkit;
import vn.com.vaccineman.it3.vaccineman.services.injectionman.person.model.Person;
import vn.com.vaccineman.it3.vaccineman.services.injectionman.registration.report.RegistrationsByDateReport;
import vn.com.vaccineman.it3.vaccineman.services.injectionman.staff.model.SupportStaff;
import vn.com.vaccineman.it3.vaccineman.services.vaccineman.storage.model.Storage;
import vn.com.vaccineman.it3.vaccineman.services.vaccineman.vaccine.model.Vaccine;

@DClass(schema="vaccineman")
public class Registration {
	public static final String A_id = "id";
	public static final String A_registerPerson = "registerPerson";
	public static final String A_day = "day";
	public static final String A_month = "month";
	public static final String A_registerYear = "registerYear";
	public static final String A_storage = "storage";
	public static final String A_vaccineType = "vaccineType";
	public static final String A_quantity = "quantity";
	public static final String A_vaccinePrice = "vaccinePrice";
	public static final String A_supportStaff = "supportStaff";
	
	public static final String A_rptRegistrationByDate = "rptRegistrationByDate";
	
	@DAttr(name=A_id, id=true, type=Type.String, auto=true, length=5, mutable=false, optional=false)
	private String id;
	
	private static int idCounter = 0;
	
//	@DAttr(name="registerPerson", type=Type.Domain, length=10, optional = false)
	@DAttr(name="registerPerson", type=Type.Domain,serialisable = false)
	@DAssoc(ascName="registration-has-person",role="registration",
	  ascType=AssocType.One2One, endType=AssocEndType.One,
	  associate=@Associate(type=Person.class,cardMin=1,cardMax=1, determinant = true))
	private Person registerPerson;
	
//	@DAttr(name=A_registerDate, type=Type.Date, format=Format.Date, length=10, optional=false)
//	private Date registerDate;
	
	@DAttr(name=A_day, type=Type.Integer, length=3, min=1, max=31, optional=false)
	private int day;

	@DAttr(name=A_month, type=Type.Integer, length=3, min=1, max=12, optional=false)
	private int month;
	
	@DAttr(name=A_registerYear, type=Type.Integer, length=5, min=2019, max=3000, optional=false)
	private int registerYear;
	
	@DAttr(name="storage",  type=Type.Domain, length = 5, optional = true)
	@DAssoc(ascName="storage-has-registrations",role="registration",
	  ascType=AssocType.One2Many, endType=AssocEndType.Many,
	  associate=@Associate(type=Storage.class,cardMin=1,cardMax=1), dependsOn=true)
	private Storage storage;
	
	@DAttr(name= "vaccineType",type=Type.Domain, auto = true, serialisable = true, mutable = false,optional = true)
	private Vaccine vaccineType;
	
	@DAttr(name= "quantity",type=Type.Integer, auto = true, serialisable = true, mutable = false,optional = true)
	private int quantity;
	
	@DAttr(name= "vaccinePrice",type=Type.Double, auto = true, serialisable = true, mutable = false,optional = true)
	private double vaccinePrice;
	
	@DAttr(name="supportStaff",  type=Type.Domain, length = 5, optional = true)
	@DAssoc(ascName="staff-has-registrations",role="registration",
	  ascType=AssocType.One2Many, endType=AssocEndType.Many,
	  associate=@Associate(type=SupportStaff.class,cardMin=1,cardMax=1), dependsOn=true)
	private SupportStaff supportStaff;
	
//	@DAttr(name="payment", type=Type.Domain, serialisable = false)
//	@DAssoc(ascName="payment-has-registration",role="registration",
//	  ascType=AssocType.One2One, endType=AssocEndType.One,
//	  associate=@Associate(type=Payment.class,cardMin=1,cardMax=1, determinant = true))
//	private Payment payment;


//	@DAttr(name="payment", type=Type.Domain, length=10, optional = true)
//	private Payment payment;
	
	@DAttr(name=A_rptRegistrationByDate,type=Type.Domain, serialisable=false, virtual=true)
	private RegistrationsByDateReport rptRegistrationByDate;
//	
	@DOpt(type=DOpt.Type.ObjectFormConstructor)
	@DOpt(type=DOpt.Type.RequiredConstructor)
	public Registration(
			@AttrRef("day")Integer day,
			@AttrRef("month")Integer month,
			@AttrRef("registerYear")Integer registerYear,
			@AttrRef("storage")Storage storage,
			@AttrRef("supportStaff") SupportStaff supportStaff) {
		this(null, null, day, month, registerYear, storage, null, 0, 0D, supportStaff);
	}

	@DOpt(type=DOpt.Type.ObjectFormConstructor)
	public Registration(@AttrRef("registerPerson")Person registerPerson,
			@AttrRef("day")Integer day,
			@AttrRef("month")Integer month,
			@AttrRef("registerYear")Integer registerYear,
			@AttrRef("storage")Storage storage,
			@AttrRef("vaccineType")Vaccine vaccineType,
			@AttrRef("quantity")Integer quantity,
			@AttrRef("vaccinePrice")Double vaccinePrice,
			@AttrRef("supportStaff") SupportStaff supportStaff) {
		this(null, registerPerson, day, month, registerYear, storage, vaccineType, quantity, vaccinePrice, null);
	}

//	@DOpt(type=DOpt.Type.ObjectFormConstructor)
//	public Registration(@AttrRef("registerPerson")Person registerPerson, 
//			@AttrRef("supportStaff") SupportStaff supportStaff,
//			@AttrRef("registerDate")Date registerDate) {
//		this(null, registerPerson, supportStaff, registerDate, null);
//	}
	
	
//	@DOpt(type=DOpt.Type.ObjectFormConstructor)
//	public Registration(@AttrRef("registerPerson")Person registerPerson,
//			@AttrRef("registerDate")Date registerDate,
//			@AttrRef("storage")Storage storage,
//			@AttrRef("vaccineType")Vaccine vaccineType,
//			@AttrRef("quantity")Integer quantity,
//			@AttrRef("vaccinePrice") Double vaccinePrice,
//			@AttrRef("supportStaff")SupportStaff supportStaff) {
//		this(null, registerPerson, registerDate, storage, vaccineType, quantity, vaccinePrice, supportStaff);
//	}

	@DOpt(type=DOpt.Type.DataSourceConstructor)
	public Registration(@AttrRef("id")String id,
			@AttrRef("day")Integer day,
			@AttrRef("month")Integer month,
			@AttrRef("registerYear")Integer registerYear,
			@AttrRef("storage")Storage storage,
			@AttrRef("vaccineType")Vaccine vaccineType,
			@AttrRef("qunatity")Integer quantity,
			@AttrRef("vaccinePrice")Double vaccinePrice,
			@AttrRef("supportStaff")SupportStaff supportStaff) throws ConstraintViolationException{

//		this.id = nextID(id);
//		this.registerPerson = registerPerson;
//		this.registerDate = registerDate;
//		this.storage = storage;
//		this.vaccineType = vaccineType;
//		this.quantity = quantity;
//		this.vaccinePrice = vaccinePrice;
////		this.payment = payment;
//		
//		setVaccineType();
//		setQuantity();
//		setVaccinePrice();
//		this.supportStaff = supportStaff;
		this(id, null, day, month, registerYear, storage, vaccineType, quantity, vaccinePrice, supportStaff);
	}
	
	private Registration(@AttrRef("id")String id,
			@AttrRef("registerPerson")Person registerPerson,
			@AttrRef("day")Integer day,
			@AttrRef("month")Integer month,
			@AttrRef("registerYear")Integer registerYear,
			@AttrRef("storage")Storage storage,
			@AttrRef("vaccineType")Vaccine vaccineType,
			@AttrRef("quantity")Integer quantity,
			@AttrRef("vaccinePrice")Double vaccinePrice,
			@AttrRef("supportStaff")SupportStaff supportStaff) throws ConstraintViolationException{
		this.id = nextID(id);
		this.registerPerson = registerPerson;
		this.day = day;
//		this.month = month;
		setMonth(month);
		this.registerYear= registerYear;
//		this.storage = storage;
		setStorage(storage);
		this.vaccineType = vaccineType;
		this.quantity = quantity;
		this.vaccinePrice = vaccinePrice;
//		this.payment = payment;
		
		setVaccineType();
		setQuantity();
		setVaccinePrice();
		this.supportStaff = supportStaff;
//		this.payment = payment;
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
	
	public void setVaccinePrice() {
		if(storage != null && storage.getVaccine() != null) {
			this.vaccinePrice = storage.getVaccine().getPrice();	
		}else {
			this.vaccinePrice = 0D;
		}
	}
	public void setVaccineType() {
		if(storage != null) {
			this.vaccineType = storage.getVaccine();	
		}else {
			this.vaccineType = null;
		}
	}

//	public Payment getPayment() {
//		return payment;
//	}
//
//	public void setPayment(Payment payment) {
//		this.payment = payment;
//	}
//	
//	@DOpt(type=DOpt.Type.LinkAdderNew)
//	public void setNewPayment(Payment payment) {
//		this.payment = payment;
//	}

	public String getId() {
		return id;
	}


	public Storage getStorage() {
		return storage;
	}

	public void setStorage(Storage storage) throws ConstraintViolationException{
		if(storage != null && storage.getRemainQuantity() == 0) {
			throw new ConstraintViolationException(DExCode.STORAGE_RUN_OUT_OF_VACCINE, storage);
		}
		
		this.storage = storage;
	}

	public void setId(String id) {
		this.id = id;
	}

	

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity() {
		this.quantity = 1;
	}

	public Person getRegisterPerson() {
		return registerPerson;
	}


	public void setRegisterPerson(Person registerPerson) {
		this.registerPerson = registerPerson;
	}
	
	public void setNewRegisterPerson(Person registerPerson) {
		setRegisterPerson(registerPerson);
	}
	
//	@DOpt(type=DOpt.Type.LinkAdderNew)
//	public void setNewRegisterPerson(Person registerPerson) {
////		this.registerPerson = registerPerson;
//		setRegisterPerson(registerPerson);
//	}


	public Vaccine getVaccineType() {
		return vaccineType;
	}

	public void setVaccineType(Vaccine vaccineType) {
		this.vaccineType = vaccineType;
	}

	public double getVaccinePrice() {
		if(vaccineType != null) {
			return vaccineType.getPrice();	
		}
		return 0D;
	}

//	public void setvaccinePrice(double vaccinePrice) {
//		this.vaccinePrice = vaccinePrice;
//	}

	public SupportStaff getSupportStaff() {
		return supportStaff;
	}


	public void setSupportStaff(SupportStaff supportStaff) {
		this.supportStaff = supportStaff;
	}



	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) throws ConstraintViolationException{
		if(day == 31 && (month == 4 || month == 6 || month ==9  || month == 11 )) {
			throw new ConstraintViolationException(DExCode.INVALID_MONTH, month);
		}else if(day > 29 && month == 2) {
			throw new ConstraintViolationException(DExCode.INVALID_DAY_IN_FEBRUARY, day);
		}
		this.month = month;
	}

	

	
	public int getRegisterYear() {
		return registerYear;
	}

	public void setRegisterYear(int registerYear) {
		this.registerYear = registerYear;
	}

	public RegistrationsByDateReport getRptRegistrationByDate() {
		return rptRegistrationByDate;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}


	@Override
	public String toString() {
		return "Registration [id=" + id + ", registerPerson=" + registerPerson 
				+ ", vaccineType=" + vaccineType + ", vaccinePrice=" + vaccinePrice + ", supportStaff=" + supportStaff + "]";
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
	@DOpt(type = DOpt.Type.AutoAttributeValueSynchroniser)
	public static void updateAutoGeneratedValue(DAttr attrib, Tuple derivingValue, Object minVal, Object maxVal)
			throws ConstraintViolationException {

		if (minVal != null && maxVal != null) {
			// TODO: update this for the correct attribute if there are more than one auto
			// attributes of this class
			if (attrib.name().equals("id")) {
				String maxId = (String) maxVal;

				try {
					int maxIdNum = Integer.parseInt(maxId.substring(1));

					if (maxIdNum > idCounter) // extra check
						idCounter = maxIdNum;

				} catch (RuntimeException e) {
					throw new ConstraintViolationException(ConstraintViolationException.Code.INVALID_VALUE, e,
							new Object[] { maxId });
				}
			}
		}
	}
	
	
	
}
