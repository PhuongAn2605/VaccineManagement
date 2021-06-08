package vn.com.vaccineman.it2.vaccineman.services.vaccineman.vaccineCenter.model;

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
import vn.com.vaccineman.it2.vaccineman.services.vaccineman.country.Country;
import vn.com.vaccineman.it2.vaccineman.services.vaccineman.storage.model.Storage;


@DClass(schema="vaccineman")
public class VaccineCenter {
	public static final String A_id = "id";
	public static final String A_name= "name";
	public static final String A_country = "country";
	public static final String A_address = "address";
	public static final String A_totalArea= "totalArea";
	public static final String A_capacity = "capacity";
	public static final String A_rptVaccineCenterCapacityGreaterThan = "rptVaccineCenterCapacityGreaterThan";
	
	@DAttr(name=A_id, id=true, type=Type.String, auto=true, length=6,
			mutable=false, optional=false)
	private String id;
	
	private static int idCounter = 0;
	
	@DAttr(name = A_name, type = Type.String, length = 30, optional = false, cid= true)
	  private String name;
	  
	 @DAttr(name = A_country, type = Type.Domain, length = 10)
	  @DAssoc(ascName = "country-has-vaccineCenters", role = "vaccineCenter", 
	    ascType = AssocType.One2Many, endType = AssocEndType.Many, 
	    associate = @Associate(type = Country.class, cardMin = 1, cardMax = 1), dependsOn = true)
	  private Country country;

//	  @DAttr(name = A_address, type = Type.Domain, length = 10, optional = false)
//	  private Address address;
	  
	  @DAttr(name = A_totalArea, type = Type.Double, length = 10, optional = false)
	  private double totalArea;
	  
	  @DAttr(name = A_capacity, type = Type.Double, length = 30, optional = false)
	  private double capacity;
	  
	  @DAttr(name="storages",type=Type.Collection,optional = true,
		      serialisable=false,filter=@Select(clazz=Storage.class))
		  @DAssoc(ascName="vaccineCenter-has-storages",role="vaccineCenter",
		      ascType=AssocType.One2Many,endType=AssocEndType.One,
		    associate=@Associate(type=Storage.class,cardMin=0,cardMax=100))
	  private Collection<Storage> storages;
	  
	  private int storagesCount;
	  
	
	  @DOpt(type=DOpt.Type.ObjectFormConstructor)
	  @DOpt(type=DOpt.Type.RequiredConstructor)
	  public VaccineCenter(@AttrRef("name") String name, 
//	      @AttrRef("country")Country country,
//	      @AttrRef("address") Address address,
	      @AttrRef("totalArea") Double totalArea,
	      @AttrRef("capacity") Double capacity) {
	  this(null,name,null,totalArea, capacity);
	  
	  }
	  
	  @DOpt(type=DOpt.Type.ObjectFormConstructor)
	  public VaccineCenter(@AttrRef("name") String name, 
	      @AttrRef("country")Country country,
//	      @AttrRef("address") Address address,
	      @AttrRef("totalArea") Double totalArea,
	      @AttrRef("capacity") Double capacity) {
	  this(null,name,country,totalArea, capacity);
	  
	  }
//	  @DOpt(type=DOpt.Type.ObjectFormConstructor)
//	  public VaccineCenter(@AttrRef("name") String name, 
//		@AttrRef("country") country country,
//	      @AttrRef("dob") Date dob,
//	      @AttrRef("address") String address,
//	      @AttrRef("phoneNumber") String phoneNumber ) {
//	    this(null, name, country,dob, address, phoneNumber);
//	  }
	  // a shared constructor that is invoked by other constructors
	  @DOpt(type=DOpt.Type.DataSourceConstructor)
	  public VaccineCenter(@AttrRef("id")String id, 
			  @AttrRef("name") String name, 
		      @AttrRef("country")Country country,
//		      @AttrRef("address") Address address,
		      @AttrRef("totalArea") Double totalArea,
		      @AttrRef("capacity") Double capacity) 
	  throws ConstraintViolationException {
	    // generate an id
	    this.id = nextID(id);

	    // assign other values
	    this.name = name;
	    this.country = country;
//	    this.address = address;
        this.totalArea = totalArea;
        this.capacity = capacity;
        
        storages = new ArrayList<>();
        storagesCount = 0;
	  }
	  
	  
	  public String getId() {
		return id;
	}

	
	public Country getCountry() {
		return country;
	}
	public void setCountry(Country country) {
		this.country = country;
	}
//	public Address getAddress() {
//		return address;
//	}
//	public void setAddress(Address address) {
//		this.address = address;
//	}
	public double getTotalArea() {
		return totalArea;
	}
	public void setTotalArea(double totalArea) {
		this.totalArea = totalArea;
	}
	public double getCapacity() {
		return capacity;
	}
	public void setCapacity(double capacity) {
		this.capacity = capacity;
	}
//	public void setId(String id) {
//		this.id = id;
//	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
		  @DOpt(type=DOpt.Type.LinkAdder)
		  public boolean addStorages(Storage i) {
			  if(!storages.contains(i))
				  storages.add(i);
			  return false;
		  }
		  
		  @DOpt(type=DOpt.Type.LinkAdderNew)
		  public boolean addNewStorages(Storage i) {
			  storages.add(i);
			  storagesCount ++;
			  return false;
		  }
		  
		  @DOpt(type=DOpt.Type.LinkAdder)
		  public boolean addStorages(Collection<Storage> is) {
			  boolean added = false;
			  for(Storage i : is) {
				  if(!storages.contains(i)) {
					  if(!added) added = true;
					  storages.add(i);
				  }
			  }
			  return false;
		  }
		  
		  @DOpt(type=DOpt.Type.LinkAdderNew)
		  public boolean addNewStorages(Collection<Storage> is) {
			  storages.addAll(is);
			  storagesCount += is.size();
			  return false;
		  }
		  
		  @DOpt(type=DOpt.Type.LinkRemover)
		  public boolean removeStorages(Storage i) {
			  boolean removed = storages.remove(i);
			  if(removed) {
				  storagesCount --;
			  }
			  return false;
		  }



		
		public void setStorages(Collection<Storage> storages) {
			this.storages = storages;
			storagesCount = storages.size();
		}


		public Collection<Storage> getStorages() {
		    return storages;
		  }

			@DOpt(type=DOpt.Type.LinkCountGetter)
			  public Integer getStoragesCount() {
			    return storagesCount;
			    //return enrolments.size();
			  }

			  @DOpt(type=DOpt.Type.LinkCountSetter)
			  public void setStoragesCount(int count) {
			    storagesCount = count;
			  }


	@Override
			public String toString() {
				return "VaccineCenter [id=" + id + ", name=" + name + ", country=" + country + ", totalArea="
						+ totalArea + ", capacity=" + capacity + "]";
			}
	private String nextID(String id) throws ConstraintViolationException {
	    if (id == null) { // generate a new id
	      if (idCounter == 0) {
	        idCounter = Calendar.getInstance().get(Calendar.YEAR);
	      } else {
	        idCounter++;
	      }
	      return "v" + idCounter;
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
