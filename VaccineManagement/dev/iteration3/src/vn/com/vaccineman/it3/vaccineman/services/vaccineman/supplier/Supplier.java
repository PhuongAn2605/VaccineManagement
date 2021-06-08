package vn.com.vaccineman.it3.vaccineman.services.vaccineman.supplier;

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
import vn.com.vaccineman.it3.vaccineman.services.vaccineman.contract.Contract;
import vn.com.vaccineman.it3.vaccineman.services.vaccineman.country.Country;

@DClass(schema="vaccineman")
public class Supplier {
	public static final String A_id = "id";
	public static final String A_name= "name";
	public static final String A_organization = "organization";
	public static final String A_location = "location";
	public static final String A_contract= "contracts";
	@DAttr(name=A_id, id=true, type=Type.String, auto=true, length=6,
			mutable=false, optional=false)
	private String id;
	
	private static int idCounter = 0;
	
	
	@DAttr(name = A_name, type = Type.String, length = 20, optional = false, cid=true)
	  private String name;
	
	@DAttr(name = A_organization, type = Type.String, length = 20, optional = false)
	  private String organization;
	
	@DAttr(name=A_location, type= Type.Domain, length=10,optional= false)
	private Country location;
	
	@DAttr(name="contracts",type=Type.Collection,optional = true,
		      serialisable=false,filter=@Select(clazz=Contract.class))
		  @DAssoc(ascName="supplier-has-contracts",role="supplier",
		      ascType=AssocType.One2Many,endType=AssocEndType.One,
		    associate=@Associate(type=Contract.class,cardMin=0,cardMax=100))
	  private Collection<Contract> contracts; 
	
	private int contractCount;
	
	@DOpt(type=DOpt.Type.ObjectFormConstructor)
	@DOpt(type=DOpt.Type.RequiredConstructor)
	public Supplier(@AttrRef("name")String name,
			  @AttrRef("organization" )String organization,
			  @AttrRef("location") Country location) throws ConstraintViolationException {
		this(null, name,organization, location);
	
	
	}
		
		
		@DOpt(type=DOpt.Type.DataSourceConstructor)
		  public Supplier(@AttrRef("id")String id, 
				  @AttrRef("name")String name,
				  @AttrRef("organization" )String organization,
				  @AttrRef("location") Country location
				  )
						  throws ConstraintViolationException {
			    // generate an id
			    this.id = nextID(id);
			    this.name= name;
			    this.location = location;
			    this.organization= organization;
			
			    contractCount= 0;
			    contracts = new ArrayList<>();
			    

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
			Supplier.idCounter = idCounter;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getOrganization() {
			return organization;
		}


		public void setOrganization(String organization) {
			this.organization = organization;
		}


		public Country getLocation() {
			return location;
		}


		public void setLocation(Country location) {
			this.location = location;
		}


		//
		 @DOpt(type=DOpt.Type.LinkAdder)
		  public boolean addContract(Contract i) {
			  if(!contracts.contains(i))
				  contracts.add(i);
			  return false;
		  }
		  
		  @DOpt(type=DOpt.Type.LinkAdderNew)
		  public boolean addNewContract(Contract i) {
			  contracts.add(i);
			  contractCount ++;
			  return false;
		  }
		  
		  @DOpt(type=DOpt.Type.LinkAdder)
		  public boolean addContract(Collection<Contract> is) {
			  boolean added = false;
			  for(Contract i : is) {
				  if(!contracts.contains(i)) {
					  if(!added) added = true;
					  contracts.add(i);
				  }
			  }
			  return false;
		  }
		  
		  @DOpt(type=DOpt.Type.LinkAdderNew)
		  public boolean addNewContract(Collection<Contract> is) {
			  contracts.addAll(is);
			  contractCount += is.size();
			  return false;
		  }
		  
		  @DOpt(type=DOpt.Type.LinkRemover)
		  public boolean removeContract(Contract i) {
			  boolean removed = contracts.remove(i);
			  if(removed) {
				  contractCount --;
			  }
			  return false;
		  }



		
		public void setContracts(Collection<Contract> contracts) {
			this.contracts = contracts;
			contractCount = contracts.size();
		}


		public Collection<Contract> getContracts() {
		    return contracts;
		  }

			@DOpt(type=DOpt.Type.LinkCountGetter)
			  public Integer getcontractCount() {
			    return contractCount;
			    //return enrolments.size();
			  }

			  @DOpt(type=DOpt.Type.LinkCountSetter)
			  public void setcontractCount(int count) {
			    contractCount = count;
			  }
		

	

		@Override
			public String toString() {
				return "Supplier [id=" + id + ", name=" + name + ", organization=" + organization + ", location="
						+ location + "]";
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

