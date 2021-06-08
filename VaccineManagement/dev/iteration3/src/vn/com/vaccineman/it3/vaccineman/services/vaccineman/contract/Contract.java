package vn.com.vaccineman.it3.vaccineman.services.vaccineman.contract;

import java.util.Calendar;
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
import vn.com.vaccineman.it3.vaccineman.services.vaccineman.country.Country;
import vn.com.vaccineman.it3.vaccineman.services.vaccineman.supplier.Supplier;
import vn.com.vaccineman.it3.vaccineman.services.vaccineman.vaccine.model.Vaccine;


@DClass(schema= "vaccineman")
public class Contract {
	public static final String A_id = "id";
	public static final String A_date= "date";
	
	 @DAttr(name = "id", id = true, auto = true, type = Type.String, length = 5, optional = false, mutable = false)
	  private String id;
	  private static int idCounter = 0;
	  
	  @DAttr(name = A_date, type = Type.Date,format=Format.Date,  length = 15, optional = false)
	  private Date date;
	  
	  @DAttr(name = "supplier", type = Type.Domain, length = 15, optional = false)
	  @DAssoc(ascName = "supplier-has-contracts", role = "contract", 
	    ascType = AssocType.One2Many, endType = AssocEndType.Many, 
	    associate = @Associate(type = Supplier.class, cardMin = 1, cardMax = 1), dependsOn = true)
	  private Supplier supplier;
	  
	  @DAttr(name = "country", type = Type.Domain, length = 15, optional = false)
	  @DAssoc(ascName = "country-has-contracts", role = "contract", 
	    ascType = AssocType.One2Many, endType = AssocEndType.Many, 
	    associate = @Associate(type = Country.class, cardMin = 1, cardMax = 1), dependsOn = true)
	  private Country country;

	  @DAttr(name = "vaccineType", type = Type.Domain, length = 10, optional = false)
	  private Vaccine vaccineType;
		
		@DAttr(name="quantity", type= Type.Long, length=10,optional= false)
		private long quantity;
		
		 @DOpt(type=DOpt.Type.ObjectFormConstructor)
		 @DOpt(type=DOpt.Type.RequiredConstructor)
		 public Contract(
				  @AttrRef("date")Date date,
				  @AttrRef("supplier")Supplier supplier,
				  @AttrRef("country")Country country,
				 @AttrRef("vaccineType") Vaccine vaccineType,
				 @AttrRef("quantity")Long quantity)
			  throws ConstraintViolationException {
			 this(null, date, supplier, country, vaccineType, quantity);
		 }
//		 @DOpt(type=DOpt.Type.ObjectFormConstructor)
//		  @DOpt(type=DOpt.Type.RequiredConstructor)
//		 public Contract(@AttrRef("supplier")Supplier supplier,
//				 @AttrRef("country") country country,
//				 @AttrRef("date") Date date,
//				 @AttrRef("trader")Trader trader,
////				 @AttrRef("vaccine")Vaccine vaccine) {
//			 this(null, supplier, country, date, trader);
			 
			 
//}
		  // a shared constructor that is invoked by other constructors
		  @DOpt(type=DOpt.Type.DataSourceConstructor)
		  public Contract(@AttrRef("id")String id,
				  @AttrRef("date")Date date,
				  @AttrRef("supplier")Supplier supplier,
				  @AttrRef("country")Country country,
				 @AttrRef("vaccineType") Vaccine vaccineType,
				 @AttrRef("quantity")Long quantity)
			  throws ConstraintViolationException {
				    // generate an id
				    this.id = nextID(id);
				    this.supplier= supplier;
				    this.country= country;
				    this.date = date;
				    this.vaccineType = vaccineType;
				    this.quantity = quantity;
//				 
			 }
		  
		  
		  

//			public Vaccine getVaccine() {
//			return vaccine;
//		}
//		public void setVaccine(Vaccine vaccine) {
//			this.vaccine = vaccine;
	//	}
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
			Contract.idCounter = idCounter;
		}
		public Supplier getSupplier() {
			return supplier;
		}
		public void setSupplier(Supplier supplier) {
			this.supplier = supplier;
		}
	
		public Date getDate() {
			return date;
		}
		public void setDate(Date date) {
			this.date = date;
		}

			public Country getCountry() {
			return country;
		}


		public void setCountry(Country country) {
			this.country = country;
		}


		public Vaccine getVaccineType() {
			return vaccineType;
		}


		public void setVaccineType(Vaccine vaccineType) {
			this.vaccineType = vaccineType;
		}


		public long getQuantity() {
			return quantity;
		}


		public void setQuantity(long quantity) {
			this.quantity = quantity;
		}


			@Override
		public String toString() {
			return "Contract [id=" + id + ", date=" + date + ", supplier=" + supplier + ", country=" + country
					+ ", vaccineType=" + vaccineType + ", quantity=" + quantity + "]";
		}


			private String nextID(String id) throws ConstraintViolationException {
			    if (id == null) { // generate a new id
			      if (idCounter == 0) {
			        idCounter = Calendar.getInstance().get(Calendar.YEAR);
			      } else {
			        idCounter++;
			      }
			      return "C" + idCounter;
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
		  