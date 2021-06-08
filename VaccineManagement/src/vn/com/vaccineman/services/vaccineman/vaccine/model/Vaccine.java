package vn.com.vaccineman.services.vaccineman.vaccine.model;

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
import domainapp.basics.model.meta.DAttr.Type;
import domainapp.basics.util.Tuple;
import domainapp.basics.util.cache.StateHistory;
import vn.com.vaccineman.services.enums.CurrencyUnit;
import vn.com.vaccineman.services.vaccineman.country.Country;
import vn.com.vaccineman.services.vaccineman.trial.model.Trial;
import vn.com.vaccineman.services.vaccineman.vaccine.model.Vaccine;
import vn.com.vaccineman.services.vaccineman.vaccine.report.VaccineByNameReport;

@DClass(schema = "vaccineman")
public abstract class Vaccine {
	public static final String A_id = "id";
	public static final String A_name = "name";
	public static final String A_producer = "producer";
	public static final String A_nature = "nature";
	public static final String A_injectionProtocol = "injectionProtocol";
	public static final String A_price = "price";
	public static final String A_averageTrialEfficacy = "averageTrialEfficacy";
	public static final String A_unit = "unit";
	
	public static final String A_rptVaccineByName = "rptVaccineByName";
	@DAttr(name = A_id, id = true, type = Type.String, auto = true, length = 5, mutable = false, optional = false)
	private String id;

	private static int idCounter = 0;

	@DAttr(name = A_name, type = Type.String, length = 20, optional = false, cid = true)
	private String name;

	@DAttr(name = A_producer, type = Type.Domain, length = 20, optional = false)
	private Country producer;
	
	@DAttr(name = A_nature, type = Type.String, length = 20, optional = false)
	private String nature;

	@DAttr(name = A_injectionProtocol, type = Type.String, length = 20, optional = false)
	private String injectionProtocol;

	@DAttr(name = A_price, type = Type.Double, length = 10, min=0, optional = true)
	private double price;

	@DAttr(name = A_unit, type = Type.Domain, length = 10, optional = true)
	private CurrencyUnit unit;
	
//	@DAttr(name=A_averageEvaluation,type=Type.Domain, auto = true, serialisable = true, mutable = false,optional = true,
//			derivedFrom ={A_averageTrialEfficacy})
//	private Evaluation averageEvaluation;
	
	@DAttr(name="trials",type=Type.Collection,
		      serialisable=false,filter=@Select(clazz=Trial.class))
		  @DAssoc(ascName="vaccine-has-trials",role="vaccine",
		      ascType=AssocType.One2Many,endType=AssocEndType.One,
		    associate=@Associate(type=Trial.class,cardMin=0,cardMax=100))
	  private Collection<Trial> trials; 
	
	@DAttr(name="trialCount",type=Type.Integer,length=20,mutable=false,
			auto=true, serialisable=true, optional=true)
	private int trialCount;
	
	@DAttr(name= A_averageTrialEfficacy,type=Type.Double, auto = true, serialisable = true, mutable = false,optional = true)
	private double averageTrialEfficacy;

	
	public StateHistory<String, Object> stateHist;

	@DAttr(name = A_rptVaccineByName, type = Type.Domain, serialisable = false,
			// IMPORTANT: set virtual=true to exclude this attribute from the object state
			// (avoiding the view having to load this attribute's value from data source)
			virtual = true)
	private VaccineByNameReport rptVaccineByName;

	@DOpt(type = DOpt.Type.ObjectFormConstructor)
	@DOpt(type = DOpt.Type.RequiredConstructor)
	public Vaccine(
			@AttrRef("name") String name,
			@AttrRef("producer")Country producer,
			@AttrRef("nature") String nature,
			@AttrRef("injectionProtocol")String injectionProtocol) {
		this(null, name, producer, nature, injectionProtocol, 0D, null, 0, 0.0);
	}
	
	@DOpt(type = DOpt.Type.ObjectFormConstructor)
	public Vaccine(
			@AttrRef("name") String name,
			@AttrRef("producer")Country producer, 
			@AttrRef("nature") String nature,
			@AttrRef("injectionProtocol")String injectionProtocol, 
			@AttrRef("price")Double price,
			@AttrRef("unit")CurrencyUnit unit,
			@AttrRef("trialCount")Integer trialCount,
			@AttrRef("averageTrialEfficacy")Double averageTrialEfficacy) {
		this(null, name, producer, nature, injectionProtocol, price, unit, trialCount, averageTrialEfficacy);
	}
	
	@DOpt(type = DOpt.Type.ObjectFormConstructor)
	public Vaccine(
			@AttrRef("name") String name,
			@AttrRef("producer")Country producer, 
			@AttrRef("nature") String nature,
			@AttrRef("injectionProtocol")String injectionProtocol, 
			@AttrRef("price")Double price,
			@AttrRef("unit")CurrencyUnit unit) {
		this(null, name, producer, nature, injectionProtocol, price, unit, 0, 0D);
	}

	@DOpt(type = DOpt.Type.DataSourceConstructor)
	public Vaccine(@AttrRef("id") String id, @AttrRef("name") String name,
			@AttrRef("producer")Country producer, 
			@AttrRef("nature") String nature,
			@AttrRef("injectionProtocol")String injectionProtocol, 
			@AttrRef("price")Double price,
			@AttrRef("unit")CurrencyUnit unit,
			@AttrRef("trialCount")Integer trialCount,
			@AttrRef("averageTrialEfficacy")Double averageTrialEfficacy) {
		
		this.id = nextID(id);
		this.name = name;
		this.producer = producer;
		this.nature = nature;
		this.injectionProtocol = injectionProtocol;
		this.price = price;
		setUnit(unit);
		
		this.trialCount = trialCount;
//		this.averageTrialEfficacy = averageTrialEfficacy;
//		this.evaluation = evaluation;
		
		trials = new ArrayList<>();
//		trialCount = 0;
		
		this.averageTrialEfficacy = averageTrialEfficacy;

		
//		updateAverageEvaluation();
		
		stateHist = new StateHistory<>();
		
//		computeAverageTrialEfficacy();
	}



	public CurrencyUnit getUnit() {
		return unit;
	}

	public void setUnit(CurrencyUnit unit) {
		if(unit == null) {
			this.unit = CurrencyUnit.VND;
		}else {
			this.unit = unit;
		}
	}

	public String getId() {
		return id;
	}

//	public void setAverageTrialEfficacy(double averageTrialEfficacy) {
//		this.averageTrialEfficacy = averageTrialEfficacy;
//	}

	public int getIdCounter() {
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

	

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Country getProducer() {
		return producer;
	}

	public void setProducer(Country producer) {
		this.producer = producer;
	}

	public String getNature() {
		return nature;
	}

	public void setNature(String nature) {
		this.nature = nature;
	}

	public String getInjectionProtocol() {
		return injectionProtocol;
	}

	public void setInjectionProtocol(String injectionProtocol) {
		this.injectionProtocol = injectionProtocol;
	}



	public VaccineByNameReport getRptVaccineByName() {
		return rptVaccineByName;
	}

	
	 @DOpt(type=DOpt.Type.LinkAdder)
	  //only need to do this for reflexive association: @MemberRef(name="enrolments")
	  public boolean addTrial(Trial e) {
	    if (!trials.contains(e)) {
	      trials.add(e);
//	      trialCount ++;
//	      updateUsedQuantity();
	    }
	    return false; 
	  }

	 //create a new trial in subform
	  @DOpt(type=DOpt.Type.LinkAdderNew)
	  public boolean addNewTrial(Trial e) {
		  this.trials.add(e);
		  trialCount ++;
//		  trialCount =  5;
		  computeAverageTrialEfficacy();
		  
		  return true;
	  }
	  
	  @DOpt(type=DOpt.Type.LinkAdder)
	  public boolean addTrial(Collection<Trial> trs) {
	    boolean added = false;
	    for (Trial e : trs) {
	      if (!trials.contains(e)) {
	        if (!added) added = true;
	        this.trials.add(e);

	      }
	    }
	    return false; 
	  }

	  @DOpt(type=DOpt.Type.LinkAdderNew)
	  public boolean addNewTrial(Collection<Trial> trs) {
//	    trials.addAll(trs);
//	    trialCount += trs.size();
		  
		for (Trial t: trs) {
			this.trials.add(t);
			trialCount ++;
		}
	    computeAverageTrialEfficacy();

	    return true; 
	  }
	  
	  @DOpt(type=DOpt.Type.LinkRemover)
	  //@MemberRef(name="enrolments")
	  public boolean removeTrial(Trial e) {
	    boolean removed = trials.remove(e);
	    
	    if (removed) {
	      trialCount--;
	      computeAverageTrialEfficacy();
	     
	    }
	    // no other attributes changed
	    return true; 
	  }
	  
	  public Collection<Trial> getTrials() {
		    return trials;
		  }
	  
	  public boolean setTrials(Collection<Trial> trs) {
			this.trials = trs;
//			trialCount = trs.size();
			for(Trial t : trs) {
				trialCount ++;
			}
			
			computeAverageTrialEfficacy();
			return true;

//			updateAverageEvaluation();
//			updateUsedQuantity();
		}
	  
	  @DOpt(type=DOpt.Type.LinkCountGetter)
	  public Integer getTrialCount() {
	    return trialCount;
	    //return enrolments.size();
	  }

	  @DOpt(type = DOpt.Type.DerivedAttributeUpdater)
	  @AttrRef(value = "trialCount")
	  public void updateTrialCount() {
	    this.trialCount = this.trials.size();
	  }

	  private void computeAverageTrialEfficacy() {
		  if(trialCount > 0) {
			  double totalPercent = 0d;
			  for(Trial t : trials) {
				  totalPercent += t.getTrialEfficacy();
			  }
			  this.trialCount = this.trials.size();
			  averageTrialEfficacy = totalPercent / trialCount;
		  }else {
			  averageTrialEfficacy = 0;
		  }
		  
//		  stateHist.put(A_averageTrialEfficacy, averageTrialEfficacy);
	  }
	  
		public double getAverageTrialEfficacy() {
			return averageTrialEfficacy;
		}

//	  @DOpt(type=DOpt.Type.LinkUpdater)
//	  public boolean updateTrial(Trial t) throws IllegalStateException{
//		  double totalTrialEfficacy = averageTrialEfficacy * trialCount;
//		  
//		  double oldTrialEfficacy = t.getTrialEfficacy(true);
//		  double diff = t.getTrialEfficacy() - oldTrialEfficacy;
//		  
//		  totalTrialEfficacy += diff;
//		  averageTrialEfficacy = totalTrialEfficacy / trialCount;
//	
//		  computeAverageTrialEfficacy();
//		  return true;
//	  }


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	

	@Override
	public String toString() {
		return "Vaccine [id=" + id + ", name=" + name + ", producer=" + producer + ", nature=" + nature
				+ ", injectionProtocol=" + injectionProtocol + ", price=" + price + ", unit=" + unit + "]";
	}

	private String nextID(String id) throws ConstraintViolationException {
		if (id == null) {
			if (idCounter == 0) {
				// current year
				idCounter = Calendar.getInstance().get(Calendar.YEAR);
			} else {
				idCounter++;
			}
			// form: "P + current year"
			return "V" + idCounter;
		} else {
			int num;
			try {
				// take out the year in ID (exclude "P")
				num = Integer.parseInt(id.substring(1));
			} catch (RuntimeException e) {
				throw new ConstraintViolationException(ConstraintViolationException.Code.INVALID_VALUE, e,
						new Object[] { id });// create new anonymous array of object with one parameter id
			}
			if (num > idCounter) {
				idCounter = num;
			}
			return id;
		}
	}

	/**
	 * @requires minVal != null /\ maxVal != null
	 * @effects update the auto-generated value of attribute <tt>attrib</tt>,
	 *          specified for <tt>derivingValue</tt>, using <tt>minVal, maxVal</tt>
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
