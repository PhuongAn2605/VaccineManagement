package vn.com.vaccinemane.it1.vaccineman.model.vaccine;

import java.util.Calendar;
import java.util.Date;

import domainapp.basics.exceptions.ConstraintViolationException;
import domainapp.basics.model.meta.AttrRef;
import domainapp.basics.model.meta.DAttr;
import domainapp.basics.model.meta.DClass;
import domainapp.basics.model.meta.DOpt;
import domainapp.basics.model.meta.DAttr.Type;
import domainapp.basics.util.Tuple;

@DClass(schema="vaccineman")
public abstract class Vaccine {
		public static final String A_id = "id";
		public static final String A_name = "name";
		public static final String A_trialEfficacy ="trialEfficacy";
		public static final String A_quantity ="quantity";
		public static final String A_expiryDate ="expiryDate";
		@DAttr(name=A_id, id=true, type=Type.String, auto=true, length=5,
				mutable=false, optional=false)
		private String id;

		private static int idCounter = 0;
		
		@DAttr(name = A_name, type = Type.String, length = 20, optional = false)
		  private String name;
		
		@DAttr(name = A_trialEfficacy, type = Type.String, length = 20, optional = false)
		  private String trialEfficacy;
	
		@DAttr(name = A_quantity, type = Type.Integer, length = 20, optional = false)
		  private int quantity;
		
		@DAttr(name = A_expiryDate, type = Type.Date, length = 20, optional = false)
		  private Date expiryDate ;
		
		@DOpt(type=DOpt.Type.ObjectFormConstructor)
		  @DOpt(type=DOpt.Type.RequiredConstructor)
		public Vaccine(@AttrRef("name")String name,
				@AttrRef("trialEfficacy")String trialEfficacy,
				@AttrRef("quantity")Integer quantity,
				@AttrRef("expiryDate")Date expiryDate) {
		this(null,name,trialEfficacy, quantity, expiryDate);}
		
		

		 @DOpt(type=DOpt.Type.DataSourceConstructor)
		 public Vaccine(@AttrRef("id")String id, 
					@AttrRef("name")String name,
					@AttrRef("trialEfficacy")String trialEfficacy,
					@AttrRef("quantity")Integer quantity,
					@AttrRef("expiryDate")Date expiryDate
					) {
			 this.id= nextID(id);
			 this.name= name;
			 this.trialEfficacy= trialEfficacy;
			 this.quantity= quantity;
			 this.expiryDate= expiryDate;
		 }

      public  String getId() {
    	  return id;
      }

		public  int getIdCounter() {
			return idCounter;
		}



		public static void setIdCounter(int idCounter) {
			Vaccine.idCounter = idCounter;
		}



		public String getName() {
			return name;
		}



		public void setName(String name) {
			this.name = name;
		}



		public String getTrialEfficacy() {
			return trialEfficacy;
		}



		public void setTrialEfficacy(String trialEfficacy) {
			this.trialEfficacy = trialEfficacy;
		}



		public int getQuantity() {
			return quantity;
		}



		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}



		public Date getExpiryDate() {
			return expiryDate;
		}



		public void setExpiryDate(Date expiryDate) {
			this.expiryDate = expiryDate;
		}



		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((expiryDate == null) ? 0 : expiryDate.hashCode());
			result = prime * result + ((id == null) ? 0 : id.hashCode());
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			result = prime * result + quantity;
			result = prime * result + ((trialEfficacy == null) ? 0 : trialEfficacy.hashCode());
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
			Vaccine other = (Vaccine) obj;
			if (expiryDate == null) {
				if (other.expiryDate != null)
					return false;
			} else if (!expiryDate.equals(other.expiryDate))
				return false;
			if (id == null) {
				if (other.id != null)
					return false;
			} else if (!id.equals(other.id))
				return false;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			if (quantity != other.quantity)
				return false;
			if (trialEfficacy == null) {
				if (other.trialEfficacy != null)
					return false;
			} else if (!trialEfficacy.equals(other.trialEfficacy))
				return false;
			return true;
		}



		@Override
		public String toString() {
			return "Vaccine [id=" + id + ", name=" + name + ", trialEfficacy=" + trialEfficacy + ", quantity="
					+ quantity + ", expiryDate=" + expiryDate + "]";
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
				return "V" + idCounter;
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

	


