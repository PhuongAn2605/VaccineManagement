package vn.com.vaccineman.it3.vaccineman.services.injectionman.payment.model;

import java.util.Calendar;
import java.util.Date;

import domainapp.basics.exceptions.ConstraintViolationException;
import domainapp.basics.model.meta.AttrRef;
import domainapp.basics.model.meta.DAssoc;
import domainapp.basics.model.meta.DAttr;
import domainapp.basics.model.meta.DClass;
import domainapp.basics.model.meta.DOpt;
import domainapp.basics.model.meta.DAttr.Format;
import domainapp.basics.model.meta.DAttr.Type;
import domainapp.basics.util.Tuple;
import domainapp.basics.model.meta.Select;
import domainapp.basics.model.meta.DAssoc.AssocEndType;
import domainapp.basics.model.meta.DAssoc.AssocType;
import domainapp.basics.model.meta.DAssoc.Associate;
import vn.com.exceptions.DExCode;
import vn.com.vaccineman.it3.vaccineman.services.enums.PaymentStatus;
import vn.com.vaccineman.it3.vaccineman.services.injectionman.payment.report.PaymentsByStatusReport;
import vn.com.vaccineman.it3.vaccineman.services.injectionman.person.model.Person;
import vn.com.vaccineman.it3.vaccineman.services.injectionman.registration.model.Registration;
import vn.com.vaccineman.it3.vaccineman.services.injectionman.staff.model.Accountant;
import vn.com.vaccineman.it3.vaccineman.services.vaccineman.vaccine.model.Vaccine;


@DClass(schema="vaccineman")
public class Payment {
	public static final String A_id = "id";
	public static final String A_payPerson = "payPerson";
	public static final String A_registration = "registration";
	public static final String A_vaccineType = "vaccineType";
	public static final String A_vaccinePrice= "vaccinePrice";
	public static final String A_cost = "cost";
	public static final String A_inputMoney = "inputMoney";
	public static final String A_returnMoney = "returnMoney";
	public static final String A_status = "status";
	public static final String A_accountant = "accountant";

	public static final String A_rptPaymentByStatus = "rptPaymentByStatus";
	
	@DAttr(name="id", id=true, type=Type.String, auto=true, length=5,
			mutable=false, optional=false)
	private String id;
	
	private static int idCounter = 0;
	
	@DAttr(name = "payDate", type = Type.Date, format=Format.Date, length= 10, optional = false)
	private Date payDate;
	
	@DAttr(name ="accountant", type = Type.Domain, length = 15, optional = true)
	  @DAssoc(ascName="accountant-has-payments",role="payment",
  ascType=AssocType.One2Many, endType=AssocEndType.Many,
associate=@Associate(type=Accountant.class,cardMin=1,cardMax=1))
	private Accountant accountant;
	
//	  @DAttr(name =A_registration, type = Type.Domain)
//	  @DAssoc(ascName="payment-has-registration",role="payment",
//	  ascType=AssocType.One2One, endType=AssocEndType.One,
//	  associate=@Associate(type=Registration.class,cardMin=1,cardMax=1))
	@DAttr(name = "registration", type = Type.Domain, length= 10, optional = false)
	private Registration registration;
	  
	  @DAttr(name="registeredPerson",type=Type.Domain,length=20,mutable=false,
				auto=true, serialisable=true, optional=true)
		private Person registeredPerson;
	
	@DAttr(name="vaccineType",type=Type.Domain,length=20,mutable=false,
			auto=true, serialisable=true, optional=true)
	private Vaccine vaccineType;
	
	@DAttr(name="quantity",type=Type.Integer,length=20,mutable=false,
			auto=true, serialisable=true, optional=true)
	private int quantity;
	
	@DAttr(name="vaccinePrice",type=Type.Double,length=20,mutable=false,
			auto=true, serialisable=true, optional=true)
	private double vaccinePrice;
	

	@DAttr(name = A_inputMoney, type = Type.Double, length = 5, optional = true, mutable = true)
	private double inputMoney;
	
	@DAttr(name= A_returnMoney,type=Type.Double, auto = true, serialisable = true, 
			mutable = false,optional = true, derivedFrom= {A_vaccinePrice, A_inputMoney})
	private double returnMoney;
	
	@DAttr(name=A_status,type=Type.Domain,length=20,optional=true, mutable=false,
			auto=true, serialisable=true,
			derivedFrom= {A_inputMoney, A_returnMoney})
	private PaymentStatus status;
	
	@DAttr(name=A_rptPaymentByStatus,type=Type.Domain, serialisable=false, virtual=true)
		  private PaymentsByStatusReport rptPaymentByStatus;
	
	@DOpt(type=DOpt.Type.ObjectFormConstructor)
	@DOpt(type=DOpt.Type.RequiredConstructor)
	public Payment(
			@AttrRef("payDate")Date payDate,
			@AttrRef("accountant")Accountant accountant,
			@AttrRef("registration")Registration registration) {
		this(null, payDate, accountant, registration, null, null, 0, 0D, 0D, 0D, null);
	}
	
	@DOpt(type=DOpt.Type.ObjectFormConstructor)
	public Payment(
			@AttrRef("payDate")Date payDate,
			@AttrRef("accountant")Accountant accountant,
			@AttrRef("registration")Registration registration,
			@AttrRef("inputMoney")Double inputMoney) {
		this(null, payDate, accountant, registration, null, null, 0, 0D, inputMoney, 0D, null);
	}
	
	@DOpt(type=DOpt.Type.ObjectFormConstructor)
	public Payment(
			@AttrRef("payPerson")Date payDate, 
			@AttrRef("accountant")Accountant accountant,
			@AttrRef("registration")Registration registration,
			@AttrRef("registeredPerson")Person registeredPerson,
			@AttrRef("vaccineType")Vaccine vaccineType,
			@AttrRef("quantity")Integer quantity,
			@AttrRef("vaccinePrice")Double vaccinePrice,
			@AttrRef("inputMoney")Double inputMoney,
			@AttrRef("returnMoney")Double returnMoney,
			@AttrRef("status")PaymentStatus status) {
		this(null, payDate, accountant, registration, registeredPerson, vaccineType, quantity, vaccinePrice, inputMoney, returnMoney, status);
	
	}
	
	@DOpt(type=DOpt.Type.DataSourceConstructor)
	public Payment(@AttrRef("id")String id, 
			@AttrRef("payDate")Date payDate, 
			@AttrRef("accountant")Accountant accountant,
			@AttrRef("registration")Registration registration,
			@AttrRef("registeredPerson")Person registeredPerson,
			@AttrRef("vaccineType")Vaccine vaccineType,
			@AttrRef("quantity")Integer quantity,
			@AttrRef("vaccinePrice")Double vaccinePrice,
			@AttrRef("inputMoney")Double inputMoney,
			@AttrRef("returnMoney")Double returnMoney,
			@AttrRef("status")PaymentStatus status) throws ConstraintViolationException{
		this.id = nextID(id);
		this.payDate = payDate;
		this.accountant = accountant;
		this.registration = registration;
		this.registeredPerson = setRegisteredPerson();
		this.vaccineType = setVaccineType();
		this.quantity = setQuantity();
		this.vaccinePrice = setVaccinePrice();
		if(inputMoney != null) {
			this.inputMoney = inputMoney;
			this.returnMoney = returnMoney;
			updateReturnMoney();
		}else {
			this.inputMoney = 0D;
			this.returnMoney = 0D;
		}
		
		setStatus();
//		this(id, payDate, accountant, null, registeredPerson, vaccineType, quantity, vaccinePrice, inputMoney, returnMoney, status);
	}
	
//	private Payment(@AttrRef("id")String id, 
//			@AttrRef("payDate")Date payDate, 
//			@AttrRef("accountant")Accountant accountant,
//			@AttrRef("registration")Registration registration,
//			@AttrRef("registeredPerson")Person registeredPerson,
//			@AttrRef("vaccineType")Vaccine vaccineType,
//			@AttrRef("quantity")Integer quantity,
//			@AttrRef("vaccinePrice")Double vaccinePrice,
//			@AttrRef("inputMoney")Double inputMoney,
//			@AttrRef("returnMoney")Double returnMoney,
//			@AttrRef("status")PaymentStatus status) throws ConstraintViolationException{
//		this.id = nextID(id);
//		this.payDate = payDate;
//		this.accountant = accountant;
//		this.registration = registration;
//		this.registeredPerson = setRegisteredPerson();
//		this.vaccineType = setVaccineType();
//		this.quantity = setQuantity();
//		this.vaccinePrice = setVaccinePrice();
//		if(inputMoney != null) {
//			this.inputMoney = inputMoney;
//			this.returnMoney = returnMoney;
//			updateReturnMoney();
//		}else {
//			this.inputMoney = 0D;
//			this.returnMoney = 0D;
//		}
//		
//		setStatus();
//	}
//	
	
	public PaymentsByStatusReport getRptPaymentByStatus() {
		return rptPaymentByStatus;
	}

	private Vaccine setVaccineType() {
		if(registration != null) {
			return registration.getVaccineType();
		}
		return null;
	}
	
	private Person setRegisteredPerson() {
		if(registration != null) {
			return registration.getRegisterPerson();
		}
		return null;
	}
	
	private double setVaccinePrice() {
		if(vaccineType != null) {
			return vaccineType.getPrice();
		}
		return 0D;
	}
	
	private int setQuantity() {
		if(registration != null) {
			return registration.getQuantity();
		}
		return 0;
	}
	
	public String getId() {
		return id;
	}

	public Registration getRegistration() {
		return registration;
	}

	public void setRegistration(Registration registration) {
		this.registration = registration;
	}
	
//	@DOpt(type=DOpt.Type.LinkAdderNew)
//	public void setNewRegistration(Registration registration) {
//		this.registration = registration;
//
//	}

	public Accountant getAccountant() {
		return accountant;
	}

	public void setAccountant(Accountant accountant) {
		this.accountant = accountant;
	}

	public Vaccine getVaccineType() {
		return vaccineType;
	}

	public void setVaccine(Vaccine vaccineType) {
		this.vaccineType = vaccineType;
	}


	public double getInputMoney() {
		return inputMoney;
	}

	
	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getVaccinePrice() {
		return vaccinePrice;
	}

	public void setVaccinePrice(double vaccinePrice) {
		this.vaccinePrice = vaccinePrice;
	}

	public Person getRegisteredPerson() {
		return registeredPerson;
	}

	public void setRegisteredPerson(Person registeredPerson) {
		this.registeredPerson = registeredPerson;
	}

	public void setVaccineType(Vaccine vaccineType) {
		this.vaccineType = vaccineType;
	}

	public void setReturnMoney(double returnMoney) {
		this.returnMoney = returnMoney;
	}

	public double getCost() {
		return vaccineType.getPrice();
	}
	public void setInputMoney(double inputMoney) throws ConstraintViolationException{
		if(inputMoney < 0) {
			throw new ConstraintViolationException(DExCode.INVALID_MONEY, inputMoney);
		}
		updateReturnMoney();
		this.inputMoney = inputMoney;
	}

	public double getReturnMoney() {
		return returnMoney;
	}

	@DOpt(type=DOpt.Type.DerivedAttributeUpdater)
	@AttrRef(value=A_returnMoney)
	private void updateReturnMoney() throws ConstraintViolationException{
			if(inputMoney < vaccinePrice) {
				throw new ConstraintViolationException(DExCode.INVALID_INPUT_MONEY, inputMoney);
			}else {
				returnMoney = inputMoney - vaccinePrice;
		}

	}

	public PaymentStatus getStatus() {
		return status;
	}

	@DOpt(type=DOpt.Type.DerivedAttributeUpdater)
	  @AttrRef(value=A_status)
	public void setStatus() {
		if(vaccinePrice != 0D && inputMoney != 0D || vaccinePrice == 0D) {
			this.status = PaymentStatus.Completed;
		}else {
			this.status = PaymentStatus.NotYet;
		}
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
			return "M" + idCounter;
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
	


	 @Override
	  public String toString() {
	    return toString(false);
	  }

	 public String toString(boolean full) {
		    if (full)
		      return "Payment(" + payDate + ")";
		    else
		      return "Payment(" + getId() + "," + 
		            ((vaccineType != null) ? vaccineType.getId() : "null") + "," +
		            ((registration != null) ? registration.getId() : "null") + ")";
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
