package vn.com.vaccineman.services.vaccineman.country;

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
import vn.com.vaccineman.services.vaccineman.contract.Contract;
import vn.com.vaccineman.services.vaccineman.country.Country;
import vn.com.vaccineman.services.vaccineman.vaccineCenter.model.VaccineCenter;

@DClass(schema="vaccineman")
public class Country {
	public static final String A_id = "id";
	public static final String A_name= "name";
	public static final String A_population = "population";
	public static final String A_infectedPeopleNum = "infectedPeopleNum";
	public static final String A_vaccineCenter = "vaccineCenters";
	public static final String A_contract= "contracts";
	
	@DAttr(name=A_id, id=true, type=Type.String, auto=true, length=6,
			mutable=false, optional=false)
	private String id;
	
	private static int idCounter = 0;
	
	
	@DAttr(name = A_name, type = Type.String, length = 20, optional = false, cid=true)
	  private String name;
	
	@DAttr(name = A_population, type = Type.Long, length = 10, optional = false, min=100)
	  private long population;
	
	@DAttr(name = A_infectedPeopleNum, type = Type.Long, length = 10, optional = false, min=0)
	  private long infectedPeopleNum;
	
	@DAttr(name="contracts",type=Type.Collection,optional = true,
		      serialisable=false,filter=@Select(clazz=Contract.class))
		  @DAssoc(ascName="country-has-contracts",role="country",
		      ascType=AssocType.One2Many,endType=AssocEndType.One,
		    associate=@Associate(type=Contract.class,cardMin=0,cardMax=100))
	  private Collection<Contract> contracts; 
	
	private int contractCount;
	
	@DAttr(name=A_vaccineCenter,type=Type.Collection,optional = true,
		      serialisable=false,filter=@Select(clazz=VaccineCenter.class))
		  @DAssoc(ascName="country-has-vaccineCenters",role="country",
		      ascType=AssocType.One2Many,endType=AssocEndType.One,
		    associate=@Associate(type=VaccineCenter.class,cardMin=0,cardMax=100))
	  private Collection<VaccineCenter> vaccineCenters; 
	
	private int vaccineCenterCount;
	
	@DOpt(type=DOpt.Type.ObjectFormConstructor)
	@DOpt(type=DOpt.Type.RequiredConstructor)
	public Country(@AttrRef("name")String name,
			  @AttrRef("population" )Long population,
			  @AttrRef("infectedPeopleNum" )Long infectedPeopleNum) throws ConstraintViolationException {
		this(null, name,population, infectedPeopleNum);
	
	
	}
	
	@DOpt(type=DOpt.Type.ObjectFormConstructor)
	public Country(@AttrRef("name")String name) throws ConstraintViolationException {
		this(null, name, 0L, 0L);
	
	}
		
		
		@DOpt(type=DOpt.Type.DataSourceConstructor)
		  public Country(@AttrRef("id")String id, 
				  @AttrRef("name")String name,
				  @AttrRef("population" )Long population,
				  @AttrRef("infectedPeopleNum" )Long infectedPeopleNum)
						  throws ConstraintViolationException {
			    // generate an id
			    this.id = nextID(id);
			    this.name= name;
			    this.population= population;
//			    this.infectedPeopleNum = infectedPeopleNum;
			    setInfectedPeopleNum(infectedPeopleNum);
			
			    contractCount= 0;
			    contracts = new ArrayList<>();
			    
			    vaccineCenterCount= 0;
			    vaccineCenters = new ArrayList<>();
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
			Country.idCounter = idCounter;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public long getPopulation() {
			return population;
		}


		public void setPopulation(long population) {
			this.population = population;
		}


		public long getInfectedPeopleNum() {
			return infectedPeopleNum;
		}

		public void setInfectedPeopleNum(long infectedPeopleNum) throws ConstraintViolationException{
			if(infectedPeopleNum > population) {
				throw new ConstraintViolationException(DExCode.INVALID_INFECTED_PEOPLE_NUMBER, infectedPeopleNum);
			}
			this.infectedPeopleNum = infectedPeopleNum;
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
			  public Integer getContractCount() {
			    return contractCount;
			    //return enrolments.size();
			  }

			  @DOpt(type=DOpt.Type.LinkCountSetter)
			  public void setContractCount(int count) {
			    contractCount = count;
			  }
		

				 @DOpt(type=DOpt.Type.LinkAdder)
				  public boolean addVaccineCenter(VaccineCenter i) {
					  if(!vaccineCenters.contains(i))
						  vaccineCenters.add(i);
					  return false;
				  }
				  
				  @DOpt(type=DOpt.Type.LinkAdderNew)
				  public boolean addNewVaccineCenter(VaccineCenter i) {
					  vaccineCenters.add(i);
					  vaccineCenterCount ++;
					  return false;
				  }
				  
				  @DOpt(type=DOpt.Type.LinkAdder)
				  public boolean addVaccineCenter(Collection<VaccineCenter> is) {
					  boolean added = false;
					  for(VaccineCenter i : is) {
						  if(!vaccineCenters.contains(i)) {
							  if(!added) added = true;
							  vaccineCenters.add(i);
						  }
					  }
					  return false;
				  }
				  
				  @DOpt(type=DOpt.Type.LinkAdderNew)
				  public boolean addNewVaccineCenter(Collection<VaccineCenter> is) {
					  vaccineCenters.addAll(is);
					  vaccineCenterCount += is.size();
					  return false;
				  }
				  
				  @DOpt(type=DOpt.Type.LinkRemover)
				  public boolean removeVaccineCenter(VaccineCenter i) {
					  boolean removed = vaccineCenters.remove(i);
					  if(removed) {
						  vaccineCenterCount --;
					  }
					  return false;
				  }



				
				public void setVaccineCenters(Collection<VaccineCenter> VaccineCenters) {
					this.vaccineCenters = VaccineCenters;
					vaccineCenterCount = VaccineCenters.size();
				}


				public Collection<VaccineCenter> getVaccineCenters() {
				    return vaccineCenters;
				  }

					@DOpt(type=DOpt.Type.LinkCountGetter)
					  public Integer getVaccineCenterCount() {
					    return vaccineCenterCount;
					    //return enrolments.size();
					  }

					  @DOpt(type=DOpt.Type.LinkCountSetter)
					  public void setVaccineCenterCount(int count) {
					    vaccineCenterCount = count;
					  }

		@Override
		public String toString() {
			return "Country [id=" + id + ", name=" + name + ", population=" + population + "]";
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

