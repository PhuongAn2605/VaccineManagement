//package vn.com.vaccineman.services.injectionman.region;
//
//import java.util.ArrayList;
//import java.util.Collection;
//
//import domainapp.basics.exceptions.ConstraintViolationException;
//import domainapp.basics.model.meta.AttrRef;
//import domainapp.basics.model.meta.DAssoc;
//import domainapp.basics.model.meta.DAttr;
//import domainapp.basics.model.meta.DClass;
//import domainapp.basics.model.meta.DOpt;
//import domainapp.basics.model.meta.MetaConstants;
//import domainapp.basics.model.meta.Select;
//import domainapp.basics.model.meta.DAssoc.AssocEndType;
//import domainapp.basics.model.meta.DAssoc.AssocType;
//import domainapp.basics.model.meta.DAssoc.Associate;
//import domainapp.basics.model.meta.DAttr.Type;
//import domainapp.basics.util.Tuple;
//import domainapp.basics.util.cache.StateHistory;
//import vn.com.vaccineman.services.injectionman.person.model.NormalPerson;
//import vn.com.vaccineman.services.injectionman.person.model.PriorityPerson;
//
//@DClass(schema="vaccineman")
//public class InjectRegion {
//
//	public static final String A_name = "name";
//	public static final String A_population = "population";
//	public static final String A_injectedPeoplePercent = "injectedPeoplePercent";
//	public static final String A_injectPeopleCount = "injectPeopleCount";
//	
//	@DAttr(name="id", id=true,auto=true, length=3, mutable=false, optional=false, type=Type.Integer )
//	private int id;
//	private static int idCounter=0;
//	
//	@DAttr(name=A_name,type=Type.String,length=15,optional=false, cid=true)
//	private String name;
//	
//	
//	@DAttr(name=A_population,type=Type.Integer,length=20,optional=false)
//	private int population;
//	
//	//, attributes= {Person.A_id, Person.A_name,
//		//Person.A_phoneNumber, Person.A_registration}),
//		//derivedFrom= {"region_name"}
//	
//	@DAttr(name="injectPriorityPeople",type=Type.Collection,serialisable=false,
//			filter=@Select(clazz=PriorityPerson.class))
//	  @DAssoc(ascName="region-has-priorityPeople",role="region",
//	  ascType=AssocType.One2Many, endType=AssocEndType.One,
//	  associate=@Associate(type=PriorityPerson.class,cardMin=0,cardMax=100))
//	  private Collection<PriorityPerson> injectPriorityPeople;
//	
//		@DAttr(name="injectNormalPeople",type=Type.Collection,serialisable=false,
//				filter=@Select(clazz=NormalPerson.class))
//		  @DAssoc(ascName="region-has-normalPeople",role="region",
//		  ascType=AssocType.One2Many, endType=AssocEndType.One,
//		  associate=@Associate(type=NormalPerson.class,cardMin=0,cardMax=100))
//		  private Collection<NormalPerson> injectNormalPeople;
//		
////		@DAttr(name="injectPriorityPeople",type=Type.Collection,serialisable=false,
////				filter=@Select(clazz=NormalPerson.class))
////		  @DAssoc(ascName="region-has-people",role="region",
////		  ascType=AssocType.One2Many, endType=AssocEndType.One,
////		  associate=@Associate(type=Person.class,cardMin=0,cardMax=100))
////		  private Collection<PriorityPerson> injectPriorityPeople;
//	
//	@DAttr(name=A_injectPeopleCount,type=Type.Integer,length=5,mutable=false,
//			auto=true, serialisable=true, optional=true)
//	private int injectPeopleCount;
//	
//	//derivedFrom= {A_population, A_peopleCount}
//	@DAttr(name=A_injectedPeoplePercent,type=Type.Double,length=5,optional=true, mutable=false,
//			auto=true, serialisable=true,
//			derivedFrom= {A_population, A_injectPeopleCount})
//	private double injectedPeoplePercent;
//
////	private StateHistory<String, Object> stateHist;
//	
//	@DOpt(type=DOpt.Type.ObjectFormConstructor)
//	@DOpt(type=DOpt.Type.RequiredConstructor)
//	public InjectRegion(@AttrRef("name")String name) {
//		this(null, name, 0, 0, 0.0);
//	}
//	
//	@DOpt(type=DOpt.Type.ObjectFormConstructor)
//	public InjectRegion( 
//			@AttrRef("name")String name, 
//			@AttrRef("population")Integer population) {
//		this(null, name, population, 0, 0.0);
//	}
//	
////	@DOpt(type=DOpt.Type.ObjectFormConstructor)
////	public InjectRegion(
////			@AttrRef("name")String name, 
////			@AttrRef("population")Integer population) {
////		this(id, name, population, 0, 0.0);
////	}
//	
//	@DOpt(type=DOpt.Type.ObjectFormConstructor)
//	public InjectRegion(@AttrRef("name")String name,
//			@AttrRef("population")Integer population,
//			@AttrRef("injectPeopleCount")Integer injectPeopleCount,
//			@AttrRef("injectedPeoplePercent")Double injectedPeoplePercent) {
//		this(null, name, population, injectPeopleCount, injectedPeoplePercent);
//	}
//	
//	@DOpt(type=DOpt.Type.DataSourceConstructor)
//	public InjectRegion(@AttrRef("id")Integer id, 
//			@AttrRef("name")String name, 
//			@AttrRef("population")Integer population,
//			@AttrRef("injectPeopleCount")Integer injectPeopleCount,
//			@AttrRef("injectedPeoplePercent")Double injectedPeoplePercent) throws ConstraintViolationException{
//		this.id = nextId(id);
//		this.name = name;
//		this.population = population;
//		this.injectPeopleCount = injectPeopleCount;
//		this.injectedPeoplePercent = injectedPeoplePercent;
//		
////		stateHist = new StateHistory<>();
//		injectNormalPeople = new ArrayList<>();
//		injectPriorityPeople = new ArrayList<>();
////		computePercent();
//	}
//	
//	  private static int nextId(Integer currID) {
//		    if (currID == null) {
//		      idCounter++;
//		      return idCounter;
//		    } else {
//		      int num = currID.intValue();
//		      if (num > idCounter)
//		        idCounter = num;
//		      
//		      return currID;
//		    }
//		  }
//
//	public int getId() {
//		return id;
//	}
//
//	public String getName() {
//		return name;
//	}
//
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//
//	public int getPopulation() {
//		return population;
//	}
//
//
//	public void setPopulation(int population) {
//		this.population = population;
//	}
//
//	@DOpt(type=DOpt.Type.LinkAdder)
//	  public boolean addNormalPerson(NormalPerson p) {
//		  if(!injectNormalPeople.contains(p)) {
//			  injectNormalPeople.add(p);
////			  injectPeopleCount ++;
////			  computePercent();
////			  return true;
//		  }
//		  	
//		  return false;
//	  }
//	  
//	  @DOpt(type=DOpt.Type.LinkAdderNew)
//	  public boolean addNewNormalPerson(NormalPerson p) {
////		  injectPeople.add(p);
////		  if(p.getInjection() != null) {
//			  injectNormalPeople.add(p);
//			  injectPeopleCount ++;
////			  injectPeopleCount = 2;
//			  
//			  computePercent();
////			  return true;
////		  }
////		  peopleCount ++;
////		  
////		  computePercent();
//		  return true;
//	  }
//	  
//	  
//	  @DOpt(type=DOpt.Type.LinkAdder)
//	  public boolean addInjectNormalPeople(Collection<NormalPerson> ps) {
//		  boolean added = false;
//		  for(NormalPerson p : ps) {
//			  if(!injectNormalPeople.contains(p)) {
//				  if(!added) added = true;
//				  injectNormalPeople.add(p);
////				  injectPeopleCount ++;
//			  }
//		  }
////		  computePercent();
//		  return false;
//	  }
//	  
//	  @DOpt(type=DOpt.Type.LinkAdderNew)
//	  public boolean addNewInjectNormalPeople(Collection<NormalPerson> ps) {
////		  injectPeople.addAll(ps);
//		  for(NormalPerson p : ps) {
////			  if(p.getInjection() != null) {
//				  injectNormalPeople.add(p);
//				  injectPeopleCount ++;
////			  }
//		  }
////		  peopleCount += ps.size();
////		  
//		  computePercent();
//		  return true;
//	  }
//	  
//	  @DOpt(type=DOpt.Type.LinkRemover)
//	  public boolean removeNormalPerson(NormalPerson p) {
//		  boolean removed = injectNormalPeople.remove(p);
//		  if(removed) {
////			  if(p.getInjection() != null && peopleCount >0) {
//				  injectPeopleCount --;
//				  
//				  computePercent();
////				  return true;
////			  }
//		  }
//		  return true;
//	  }
//	  
//	  public Collection<NormalPerson> getInjectNormalPeople() {
//		    return injectNormalPeople;
//		  }
//	
//	public boolean setInjectNormalPeople(Collection<NormalPerson> np) {
//		this.injectNormalPeople = np;
//		injectPeopleCount = np.size();
////		for(NormalPerson p : np) {
//////			if(p.getInjection() != null) {
////				injectNormalPeople.add(p);
////				injectPeopleCount ++;
//////			}
////		}
//		
//		computePercent();
//		return true;
//	}
//	
//
//	  @DOpt(type=DOpt.Type.LinkCountGetter)
//	  public int getInjectPeopleCount() {
//	    return injectPeopleCount;
//	    //return enrolments.size();
//	  }
//
//	  @DOpt(type=DOpt.Type.LinkCountSetter)
//	  public void setInjectPeopleCount(int count) {
//		    injectPeopleCount = count;
//		  }
//
//	
//	public double getInjectedPeoplePercent() {
//	return injectedPeoplePercent;
//}
//
//	
//	//priority person
//	@DOpt(type=DOpt.Type.LinkAdder)
//	  public boolean addPriorityPerson(PriorityPerson p) {
//		  if(!injectPriorityPeople.contains(p)) {
//			  injectPriorityPeople.add(p);
////			  injectPeopleCount ++;
////			  computePercent();
////			  return true;
//		  }
//		  	
//		  return false;
//	  }
//	  
//	  @DOpt(type=DOpt.Type.LinkAdderNew)
//	  public boolean addNewPriorityPerson(PriorityPerson p) {
////		  injectPeople.add(p);
////		  if(p.getInjection() != null) {
//			  injectPriorityPeople.add(p);
//			  injectPeopleCount ++;
////			  peopleCount = 2;
//			  
//			  computePercent();
////			  return true;
////		  }
////		  peopleCount ++;
////		  
////		  computePercent();
//		  return true;
//	  }
//	  
//	  
//	  @DOpt(type=DOpt.Type.LinkAdder)
//	  public boolean addInjectPriorityPeople(Collection<PriorityPerson> ps) {
//		  boolean added = false;
//		  for(PriorityPerson p : ps) {
//			  if(!injectPriorityPeople.contains(p)) {
//				  if(!added) added = true;
//				  injectPriorityPeople.add(p);
////				  injectPeopleCount ++;
////				  return true;
//			  }
//		  }
////		  computePercent();
//		  return false;
//	  }
//	  
//	  @DOpt(type=DOpt.Type.LinkAdderNew)
//	  public boolean addNewInjectPriorityPeople(Collection<PriorityPerson> ps) {
////		  injectPeople.addAll(ps);
//		  for(PriorityPerson p : ps) {
////			  if(p.getInjection() != null) {
//				  injectPriorityPeople.add(p);
//				  injectPeopleCount ++;
////			  }
//		  }
////		  peopleCount += ps.size();
////		  
//		  computePercent();
//		  return true;
//	  }
//	  
//	  @DOpt(type=DOpt.Type.LinkRemover)
//	  public boolean removePerson(PriorityPerson p) {
//		  boolean removed = injectPriorityPeople.remove(p);
//		  if(removed) {
////			  if(p.getInjection() != null && peopleCount >0) {
//				  injectPeopleCount --;
//				  
//				  computePercent();
////				  return true;
////			  }
//		  }
//		  return true;
//	  }
//	  
//	  public Collection<PriorityPerson> getInjectPriorityPeople() {
//		    return injectPriorityPeople;
//		  }
//
//
//	
//	public boolean setInjectPriorityPeople(Collection<PriorityPerson> pp) {
//		this.injectPriorityPeople = pp;
//		injectPeopleCount = pp.size();
////		for(PriorityPerson p : pp) {
//////			if(p.getInjection() != null) {
////				injectPriorityPeople.add(p);
////				injectPeopleCount ++;
//////			}
////		}
//		
//		computePercent();
//		return true;
//	}
//	
////	public boolean updateInjectNormalPerson(NormalPerson p) throws IllegalStateException{
////		int count = this.injectPeopleCount;
////	}
////	public double getInjectedPeoplePercent() {
////		return getInjectedPeoplePercent(false);
////	}
////	
////	public double getInjectedPeoplePercent(boolean cached) throws IllegalStateException {
////	    if (cached) {
////	      Object val = stateHist.get(A_injectedPeoplePercent);
////
////	      if (val == null)
////	        throw new IllegalStateException(
////	            "InjectRegion.getInjectedPeoplePercent: cached value is null");
////
////	      return (Integer) val;
////	    } else {
////	      if (injectedPeoplePercent != 0)
////	        return injectedPeoplePercent;
////	      else
////	        return 0;
////	    }
////
////	  }
//	
////	@DOpt(type=DOpt.Type.DerivedAttributeUpdater)
////	@AttrRef(value=A_injectedPeoplePercent)
//	public void computePercent() {
////		if(peopleCount > 0) {
////			injectedPeoplePercent = ((peopleCount/population)*100)/100;
//			injectedPeoplePercent = (double) (injectPeopleCount*100)/population;
////			injectedPeoplePercent = 2;
////		}else {
////			injectedPeoplePercent = 0;
////		}
//	}
//	
//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + id;
//		return result;
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		InjectRegion other = (InjectRegion) obj;
//		if (id != other.id)
//			return false;
//		return true;
//	}
//	
//	@DOpt(type = DOpt.Type.AutoAttributeValueSynchroniser)
//	  public static void updateAutoGeneratedValue(DAttr attrib,
//	      Tuple derivingValue, Object minVal, Object maxVal)
//	      throws ConstraintViolationException {
//	    if (minVal != null && maxVal != null) {
//	      // check the right attribute
//	      if (attrib.name().equals("id")) {
//	        int maxIdVal = (Integer) maxVal;
//	        if (maxIdVal > idCounter)
//	          idCounter = maxIdVal;
//	      }
//	      // TODO add support for other attributes here
//	    }
//	  }
//
//	
//	}
//	
//	
//	

package vn.com.vaccineman.it2.vaccineman.services.injectionman.region;

import java.util.ArrayList;
import java.util.Collection;

import domainapp.basics.exceptions.ConstraintViolationException;
import domainapp.basics.model.meta.AttrRef;
import domainapp.basics.model.meta.DAssoc;
import domainapp.basics.model.meta.DAttr;
import domainapp.basics.model.meta.DClass;
import domainapp.basics.model.meta.DOpt;
import domainapp.basics.model.meta.MetaConstants;
import domainapp.basics.model.meta.Select;
import domainapp.basics.model.meta.DAssoc.AssocEndType;
import domainapp.basics.model.meta.DAssoc.AssocType;
import domainapp.basics.model.meta.DAssoc.Associate;
import domainapp.basics.model.meta.DAttr.Type;
import domainapp.basics.util.Tuple;
import domainapp.basics.util.cache.StateHistory;
import vn.com.vaccineman.it2.vaccineman.services.injectionman.person.model.Person;


@DClass(schema="vaccineman")
public class InjectRegion {

	public static final String A_name = "name";
	public static final String A_population = "population";
	public static final String A_injectedPeoplePercent = "injectedPeoplePercent";
	public static final String A_peopleCount = "peopleCount";

	
	@DAttr(name="id", id=true,auto=true, length=3, mutable=false, optional=false, type=Type.Integer )
	private int id;
	private static int idCounter;
	
	@DAttr(name=A_name,type=Type.String,length=20,optional=false, cid=true)
	private String name;
	//, attributes= {Person.A_id, Person.A_name,
	//Person.A_phoneNumber, Person.A_registration}),
	//derivedFrom= {"region_name"}
	@DAttr(name="injectPeople",type=Type.Collection,serialisable=false,
			filter=@Select(clazz=Person.class))
	  @DAssoc(ascName="region-has-people",role="region",
	  ascType=AssocType.One2Many, endType=AssocEndType.One,
	  associate=@Associate(type=Person.class,cardMin=0,cardMax=100))
	  private Collection<Person> injectPeople;
	
	
	@DAttr(name=A_population,type=Type.Integer,length=20,optional=false)
	private int population;
	
	@DAttr(name=A_peopleCount,type=Type.Integer,length=20,mutable=false,
			auto=true, serialisable=true, optional=true)
	private int peopleCount;
	
	//derivedFrom= {A_population, A_peopleCount}
	@DAttr(name=A_injectedPeoplePercent,type=Type.Double,length=20,optional=true, mutable=false,
			auto=true, serialisable=true,
			derivedFrom= {A_population, A_peopleCount})
	private double injectedPeoplePercent;

//	private StateHistory<String, Object> stateHist;
	@DOpt(type=DOpt.Type.ObjectFormConstructor)
	@DOpt(type=DOpt.Type.RequiredConstructor)
	public InjectRegion(@AttrRef("name")String name) {
		this(null, name, 0, 0, 0.0);
	}
	
	@DOpt(type=DOpt.Type.ObjectFormConstructor)
	public InjectRegion( 
			@AttrRef("name")String name, 
			@AttrRef("population")Integer population) {
		this(null, name, population, 0, 0.0);
	}
	
	@DOpt(type=DOpt.Type.ObjectFormConstructor)
	public InjectRegion(
			@AttrRef("id")Integer id,
			@AttrRef("name")String name, 
			@AttrRef("population")Integer population) {
		this(id, name, population, 0, 0.0);
	}
	
	@DOpt(type=DOpt.Type.ObjectFormConstructor)
	public InjectRegion(@AttrRef("name")String name,
			@AttrRef("population")Integer population,
			@AttrRef("peopleCount")Integer peopleCount,
			@AttrRef("injectedPeoplePercent")Double injectedPeoplePercent) {
		this(null, name, population, peopleCount, injectedPeoplePercent);
	}
	
//	@DOpt(type=DOpt.Type.ObjectFormConstructor)
//	public InjectRegion(@AttrRef("name")String name) {
//		this(0, name, 0, 0, 0.0);
//	}
	
	@DOpt(type=DOpt.Type.DataSourceConstructor)
	public InjectRegion(@AttrRef("id")Integer id, 
			@AttrRef("name")String name, 
			@AttrRef("population")Integer population,
			@AttrRef("peopleCount")Integer peopleCount,
			@AttrRef("injectedPeoplePercent")Double injectedPeoplePercent) throws ConstraintViolationException{
		this.id = nextId(id);
		this.name = name;
		this.population = population;
		this.peopleCount = peopleCount;
		this.injectedPeoplePercent = injectedPeoplePercent;
		
//		stateHist = new StateHistory<>();
		
		injectPeople = new ArrayList<Person>();
//		peopleCount = 0;
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

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getPopulation() {
		return population;
	}


	public void setPopulation(int population) {
		this.population = population;
	}

	@DOpt(type=DOpt.Type.LinkAdder)
	  public boolean addPerson(Person r) {
		  if(!injectPeople.contains(r))
			  injectPeople.add(r);
		  return false;
	  }
	  
	  @DOpt(type=DOpt.Type.LinkAdderNew)
	  public boolean addNewPerson(Person p) {
//		  injectPeople.add(p);
//		  if(p.getInjection() != null) {
			  injectPeople.add(p);
			  peopleCount ++;
			  updateInjectPeoplePercent();
//		  }
		  return true;
	  }
	  
	  
	  @DOpt(type=DOpt.Type.LinkAdder)
	  public boolean addInjectPeople(Collection<Person> rs) {
		  boolean added = false;
		  for(Person r : rs) {
			  if(!injectPeople.contains(r)) {
				  if(!added) added = true;
				  injectPeople.add(r);
			  }
		  }
		  return false;
	  }
	  
	  @DOpt(type=DOpt.Type.LinkAdderNew)
	  public boolean addNewInjectPeople(Collection<Person> ps) {
//		  injectPeople.addAll(ps);
		  for(Person p : ps) {
//			  if(p.getInjection() != null) {
				  injectPeople.add(p);
				  peopleCount ++;
//			  }
		  }
//		  peopleCount += ps.size();
//		  
		 updateInjectPeoplePercent();
		  return true;
	  }
	  
	  @DOpt(type=DOpt.Type.LinkRemover)
	  public boolean removePerson(Person p) {
		  boolean removed = injectPeople.remove(p);
		  if(removed) {
			  if(peopleCount >0) {
				  peopleCount --;
				  
				  updateInjectPeoplePercent();
			  }
//			  peopleCount --;
//			  
//			  computePercent();
		  }
		  return false;
	  }
	  
	  public Collection<Person> getInjectPeople() {
		    return injectPeople;
		  }

		  @DOpt(type=DOpt.Type.LinkCountGetter)
		  public Integer getPeopleCount() {
		    return peopleCount;
		    //return enrolments.size();
		  }

		  @DOpt(type = DOpt.Type.DerivedAttributeUpdater)
		  @AttrRef(value = A_peopleCount)
		  public void updatePeopleCount() {
		    this.peopleCount = injectPeople.size();
		  }
	
//	public int getPeopleCount() {
//		return peopleCount;
//	}

	
	public boolean setInjectPeople(Collection<Person> injectPeople) {
		this.injectPeople = injectPeople;
//		peopleCount = injectPeople.size();
		for(Person p : injectPeople) {
//			if(p.getInjection() != null) {
				peopleCount ++;
//			}
		}
		
		updateInjectPeoplePercent();
		return true;
	}
	
	public double getInjectedPeoplePercent() {
	return injectedPeoplePercent;
}

//	public double getInjectedPeoplePercent() {
//		return getInjectedPeoplePercent(false);
//	}
//	
//	public double getInjectedPeoplePercent(boolean cached) throws IllegalStateException {
//	    if (cached) {
//	      Object val = stateHist.get(A_injectedPeoplePercent);
//
//	      if (val == null)
//	        throw new IllegalStateException(
//	            "InjectRegion.getInjectedPeoplePercent: cached value is null");
//
//	      return (Integer) val;
//	    } else {
//	      if (injectedPeoplePercent != 0)
//	        return injectedPeoplePercent;
//	      else
//	        return 0;
//	    }
//
//	  }
	
	@DOpt(type=DOpt.Type.DerivedAttributeUpdater)
	@AttrRef(value=A_injectedPeoplePercent)
	private void updateInjectPeoplePercent() {
		if(peopleCount > 0) {
//			injectedPeoplePercent = ((peopleCount/population)*100)/100;
			injectedPeoplePercent = (double) (peopleCount*100)/population;
//			injectedPeoplePercent = 2;
		}else {
			injectedPeoplePercent = 0;
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		InjectRegion other = (InjectRegion) obj;
		if (id != other.id)
			return false;
		return true;
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
	
	
