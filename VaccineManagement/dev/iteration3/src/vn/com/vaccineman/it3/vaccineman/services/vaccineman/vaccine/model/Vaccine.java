package vn.com.vaccineman.it3.vaccineman.services.vaccineman.vaccine.model;

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
import domainapp.basics.util.cache.StateHistory;
import vn.com.exceptions.DExCode;
import vn.com.utils.DToolkit;
import vn.com.vaccineman.it3.vaccineman.services.vaccineman.country.Country;
import vn.com.vaccineman.it3.vaccineman.services.vaccineman.trial.model.Trial;
import vn.com.vaccineman.it3.vaccineman.services.vaccineman.vaccine.report.VaccineByNameReport;


@DClass(schema = "vaccineman")
public abstract class Vaccine {
	public static final String A_id = "id";
	public static final String A_name = "name";
	public static final String A_producer = "producer";
	public static final String A_nature = "nature";
	public static final String A_injectionProtocol = "injectionProtocol";
	public static final String A_price = "price";
	public static final String A_averageTrialEfficacy = "averageTrialEfficacy";
	public static final String A_averageEvaluation = "averageEvaluation";
	
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

	@DAttr(name = A_price, type = Type.Double, length = 20, optional = false)
	private double price;


	@DAttr(name= A_averageTrialEfficacy,type=Type.Double, auto = true, serialisable = true, mutable = false,optional = true)
	private double averageTrialEfficacy;
	
//	@DAttr(name=A_averageEvaluation,type=Type.Domain, auto = true, serialisable = true, mutable = false,optional = true,
//			derivedFrom ={A_averageTrialEfficacy})
//	private Evaluation averageEvaluation;
	
	@DAttr(name="trials",type=Type.Collection,optional = true,
		      serialisable=false,filter=@Select(clazz=Trial.class))
		  @DAssoc(ascName="vaccine-has-trials",role="vaccine",
		      ascType=AssocType.One2Many,endType=AssocEndType.One,
		    associate=@Associate(type=Trial.class,cardMin=0,cardMax=100))
	  private Collection<Trial> trials; 
	
	private int trialsCount;

	
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
			@AttrRef("injectionProtocol")String injectionProtocol, 
			@AttrRef("price")Double price) {
		this(null, name, producer, nature, injectionProtocol, price, 0D);
	}

	@DOpt(type = DOpt.Type.DataSourceConstructor)
	public Vaccine(@AttrRef("id") String id, @AttrRef("name") String name,
			@AttrRef("producer")Country producer, 
			@AttrRef("nature") String nature,
			@AttrRef("injectionProtocol")String injectionProtocol, 
			@AttrRef("price")Double price,
			@AttrRef("averageTrialEfficacy")Double averageTrialEfficacy) {
		
		this.id = nextID(id);
		this.name = name;
		this.producer = producer;
		this.nature = nature;
		this.injectionProtocol = injectionProtocol;
		this.price = price;
		this.averageTrialEfficacy = averageTrialEfficacy;
//		this.evaluation = evaluation;
		
		trials = new ArrayList<>();
		trialsCount = 0;
		
//		updateAverageEvaluation();
		
//		computeAverageTrialEfficacy();
	}

//
//	public Evaluation getAverageEvaluation() {
//		return averageEvaluation;
//	}


	public String getId() {
		return id;
	}

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

	public double getAverageTrialEfficacy() {
		return averageTrialEfficacy;
	}

//	public StateHistory<String, Object> getStateHist() {
//		return stateHist;
//	}
//
//	public void setStateHist(StateHistory<String, Object> stateHist) {
//		this.stateHist = stateHist;
//	}

	public void setId(String id) {
		this.id = id;
	}

	public VaccineByNameReport getRptVaccineByName() {
		return rptVaccineByName;
	}

//	@DOpt(type = DOpt.Type.DerivedAttributeUpdater)
//    @AttrRef(value = A_averageEvaluation)
//	public void updateAverageEvaluation() throws ConstraintViolationException{
//		if(Trial.validateTrialEfficacy(averageTrialEfficacy)) {
//			if(averageTrialEfficacy >= 0 && averageTrialEfficacy < 50) {
//				this.averageEvaluation = Evaluation.Bad;
//			}else if(averageTrialEfficacy >= 50 && averageTrialEfficacy < 80) {
//				this.averageEvaluation = Evaluation.Medium;
//			}else if(averageTrialEfficacy >= 80 && averageTrialEfficacy < 95) {
//				this.averageEvaluation = Evaluation.Good;
//			}else {
//				this.averageEvaluation = Evaluation.Excellent;
//			}
//		}else {
//			throw new ConstraintViolationException(DExCode.INVALID_TRIAL_EFFICACY, averageTrialEfficacy);
//		}
//	}
	
	 @DOpt(type=DOpt.Type.LinkAdder)
	  //only need to do this for reflexive association: @MemberRef(name="enrolments")
	  public boolean addTrial(Trial e) {
	    if (!trials.contains(e)) {
	      trials.add(e);
//	      trialsCount ++;
//	      updateUsedQuantity();
	    }
	    return false; 
	  }

	  @DOpt(type=DOpt.Type.LinkAdderNew)
	  public boolean addNewTrial(Trial e) {
	    trials.add(e);
	    
	    trialsCount ++;
	    computeAverageTrialEfficacy();
	    
	    // no other attributes changed (average mark is not serialisable!!!)
	    return true; 
	  }
	  
	  @DOpt(type=DOpt.Type.LinkAdder)
	  public boolean addTrial(Collection<Trial> regiss) {
	    boolean added = false;
	    for (Trial e : regiss) {
	      if (!trials.contains(e)) {
	        if (!added) added = true;
	        trials.add(e);
//	        trialsCount ++;
//		    updateUsedQuantity();
	      }
	    }
	    // IMPORTANT: enrolment count must be updated separately by invoking setEnrolmentCount
	    // otherwise computeAverageMark (below) can not be performed correctly
	    // WHY? average mark is not serialisable
//	    enrolmentCount += enrols.size();

//	    if (added) {
//	      // avg mark is not serialisable so we need to compute it here
//	      computeAverageMark();
//	    }

	    // no other attributes changed
	    return false; 
	  }

	  @DOpt(type=DOpt.Type.LinkAdderNew)
	  public boolean addNewTrial(Collection<Trial> enrols) {
	    trials.addAll(enrols);
	    trialsCount+=enrols.size();
	    computeAverageTrialEfficacy();

	    // no other attributes changed (average mark is not serialisable!!!)
	    return true; 
	  }
	  
	  @DOpt(type=DOpt.Type.LinkRemover)
	  //@MemberRef(name="enrolments")
	  public boolean removeTrial(Trial e) {
	    boolean removed = trials.remove(e);
	    
	    if (removed) {
	      trialsCount--;
	      computeAverageTrialEfficacy();
	     
	    }
	    // no other attributes changed
	    return true; 
	  }
	  
	  public Collection<Trial> getTrials() {
		    return trials;
		  }
	  
	  public boolean setTrials(Collection<Trial> Trials) {
			this.trials = Trials;
			trialsCount = Trials.size();
			
			computeAverageTrialEfficacy();
			
			return true;
//			updateAverageEvaluation();
//			updateUsedQuantity();
		}
	  
	  @DOpt(type=DOpt.Type.LinkCountGetter)
	  public Integer getTrialsCount() {
	    return trialsCount;
	    //return enrolments.size();
	  }

	  @DOpt(type=DOpt.Type.LinkCountSetter)
	  public void setTrialsCount(int count) {
	    trialsCount = count;
	  }

	  private void computeAverageTrialEfficacy() {
		  if(trialsCount > 0) {
			  double totalPercent = 0d;
			  for(Trial t : trials) {
				  totalPercent += t.getTrialEfficacy();
			  }
			  averageTrialEfficacy = totalPercent / trialsCount;
		  }else {
			  averageTrialEfficacy = 0;
		  }
	  }

	  @DOpt(type=DOpt.Type.LinkUpdater)
	  public boolean updateTrial(Trial t) throws IllegalStateException{
		  double totalTrialEfficacy = averageTrialEfficacy * trialsCount;
		  
		  double oldTrialEfficacy = t.getTrialEfficacy(true);
		  double diff = t.getTrialEfficacy() - oldTrialEfficacy;
		  
		  totalTrialEfficacy += diff;
		  averageTrialEfficacy = totalTrialEfficacy / trialsCount;
		  
		  return true;
	  }

	
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
				+ ", injectionProtocol=" + injectionProtocol + ", price=" + price + ", averageTrialEfficacy="
				+ averageTrialEfficacy + "]";
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
