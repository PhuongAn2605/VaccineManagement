package vn.com.vaccineman.it3.vaccineman.services.vaccineman.trial.model;

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
import vn.com.vaccineman.it3.vaccineman.services.enums.Evaluation;
import vn.com.vaccineman.it3.vaccineman.services.enums.PeopleGroup;
import vn.com.vaccineman.it3.vaccineman.services.vaccineman.supplier.Supplier;
import vn.com.vaccineman.it3.vaccineman.services.vaccineman.trial.report.TrialEfficacyGreaterThan;
import vn.com.vaccineman.it3.vaccineman.services.vaccineman.trial.report.TrialEfficacyLessThan;
import vn.com.vaccineman.it3.vaccineman.services.vaccineman.trial.report.TrialEvaluationAndPeoleGroupReport;
import vn.com.vaccineman.it3.vaccineman.services.vaccineman.vaccine.model.Vaccine;
import vn.com.vaccineman.it3.vaccineman.services.vaccineman.vaccineCenter.model.VaccineCenter;

@DClass(schema="vaccineman")
public class Trial {
	public static final String A_id = "id";
	public static final String A_name= "name";
	public static final String A_vaccineType = "vaccineType";
	public static final String A_peopleGroup = "peopleGroup";
	public static final String A_startDate = "startDate";
	public static final String A_endDate = "endDate";
	public static final String A_trackingDays = "trackingDays";
	public static final String A_details = "details";
	public static final String A_trialEfficacy = "trialEfficacy";
	public static final String A_evaluation = "evaluation";
	
	public static final String A_rptTrialEvaluationAndPeopleGroup = "rptTrialEvaluationAndPeopleGroup";
	public static final String A_rptTrialEfficacyLessThan = "rptTrialEfficacyLessThan";
	public static final String A_rptTrialEfficacyGreaterThan = "rptTrialEfficacyGreaterThan";
	@DAttr(name=A_id, id=true, type=Type.String, auto=true, length=6,
			mutable=false, optional=false)
	private String id;
	
	private static int idCounter = 0;
	
	
	@DAttr(name = A_name, type = Type.String, length = 20, optional = false, cid=true)
	  private String name;
	
	@DAttr(name = A_vaccineType, type = Type.Domain, length = 15, optional = false)
	  @DAssoc(ascName = "vaccine-has-trials", role = "trial", 
	    ascType = AssocType.One2Many, endType = AssocEndType.Many, 
	    associate = @Associate(type = Vaccine.class, cardMin = 1, cardMax = 1), dependsOn = true)
	  private Vaccine vaccineType;
	
	
	@DAttr(name = A_peopleGroup, type = Type.Domain, length = 10, optional = false)
	  private PeopleGroup peopleGroup;	
	
	@DAttr(name = A_startDate, type = Type.Date, format=Format.Date, length = 10, optional = false)
	  private Date startDate;
	
	@DAttr(name = A_endDate, type = Type.Date, format=Format.Date, length = 10, optional = false)
	  private Date endDate;
	
	@DAttr(name = A_trackingDays, type = Type.Integer, length = 5, optional = true, mutable = false, auto = true, serialisable = true, derivedFrom = {
			A_startDate, A_endDate })
	  private int trackingDays;
	
	@DAttr(name = A_details, type = Type.String, length = 25, optional = false)
	  private String details;
	
	@DAttr(name = A_trialEfficacy, type = Type.Double, length = 5, optional = false)
	  private double trialEfficacy;
	
	@DAttr(name =A_evaluation, type = Type.Domain, length = 10, optional = true, mutable = false, auto = true, serialisable = true, 
			derivedFrom = { A_trialEfficacy })
	private Evaluation evaluation;
	
	private StateHistory<String, Object> stateHist;
	
	@DAttr(name=A_rptTrialEvaluationAndPeopleGroup,type=Type.Domain, serialisable=false, virtual=true)
		  private TrialEvaluationAndPeoleGroupReport rptTrialEvaluationAndPeopleGroup;
	
	@DAttr(name=A_rptTrialEfficacyGreaterThan,type=Type.Domain, serialisable=false, virtual=true)
	  private TrialEfficacyGreaterThan rptTrialEfficacyGreaterThan;
	
	@DAttr(name=A_rptTrialEfficacyLessThan,type=Type.Domain, serialisable=false, virtual=true)
	  private TrialEfficacyLessThan rptTrialEfficacyLessThan;
	
	@DOpt(type=DOpt.Type.ObjectFormConstructor)
	@DOpt(type=DOpt.Type.RequiredConstructor)
	public Trial(
			  @AttrRef("name")String name,
			  @AttrRef("vaccineType" )Vaccine vaccineType,
			  @AttrRef("peopleGroup")PeopleGroup peopleGroup,
			  @AttrRef("startDate")Date startDate,
			  @AttrRef("endDate")Date endDate,
			  @AttrRef("trackingDays")Integer trackingDays,
			  @AttrRef("details")String details,
			  @AttrRef("trialEfficacy")Double trialEfficacy) throws ConstraintViolationException {
		this(null, name,vaccineType, peopleGroup, startDate, endDate, trackingDays, details, trialEfficacy, null);
	
	
	}
	
	@DOpt(type=DOpt.Type.ObjectFormConstructor)
	public Trial(
			  @AttrRef("name")String name,
			  @AttrRef("vaccineType" )Vaccine vaccineType,
			  @AttrRef("peopleGroup")PeopleGroup peopleGroup,
			  @AttrRef("startDate")Date startDate,
			  @AttrRef("endDate")Date endDate,
			  @AttrRef("details")String details,
			  @AttrRef("trialEfficacy")Double trialEfficacy) throws ConstraintViolationException {
		this(null, name,vaccineType, peopleGroup, startDate, endDate, 0, details, trialEfficacy, null);
	}
	
	@DOpt(type=DOpt.Type.ObjectFormConstructor)
	public Trial(
			  @AttrRef("name")String name,
			  @AttrRef("vaccineType" )Vaccine vaccineType,
			  @AttrRef("peopleGroup")PeopleGroup peopleGroup,
			  @AttrRef("startDate")Date startDate,
			  @AttrRef("endDate")Date endDate,
			  @AttrRef("trialEfficacy")Double trialEfficacy) throws ConstraintViolationException {
		this(null, name,vaccineType, peopleGroup, startDate, endDate, 0, null, trialEfficacy, null);
	
	
	}
		
		
		@DOpt(type=DOpt.Type.DataSourceConstructor)
		  public Trial(@AttrRef("id")String id, 
				  @AttrRef("name")String name,
				  @AttrRef("vaccineType" )Vaccine vaccineType,
				  @AttrRef("peopleGroup")PeopleGroup peopleGroup,
				  @AttrRef("startDate")Date startDate,
				  @AttrRef("endDate")Date endDate,
				  @AttrRef("trackingDays")Integer trackingDays,
				  @AttrRef("details")String details,
				  @AttrRef("trialEfficacy")Double trialEfficacy,
				  @AttrRef("evaluation")Evaluation evaluation)
						  throws ConstraintViolationException {
			    // generate an id
			    this.id = nextID(id);
			    this.name= name;
			    this.vaccineType = vaccineType;
			    this.peopleGroup= peopleGroup;
			    this.startDate = startDate;
			    this.endDate = endDate;
			    this.trackingDays = trackingDays;
			    this.details = details;
//			    this.trialEfficacy = trialEfficacy;
//			    this.evaluation = evaluation;
			   
			    updateTrackingDays();
//			    updateEvaluation();
			    
			    stateHist = new StateHistory<>();
			    setTrialEfficacy(trialEfficacy);
//			    stateHist.put(A_trialEfficacy, trialEfficacy);
}
		
		@DOpt(type = DOpt.Type.DerivedAttributeUpdater)
	    @AttrRef(value = A_trackingDays)
		public void updateTrackingDays() throws ConstraintViolationException{
			if(startDate.getTime() <= endDate.getTime()) {
				this.trackingDays = (int) ((endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24));
			}else {
				throw new ConstraintViolationException(DExCode.INVALID_END_DATE, endDate);
			}
		}
		
		@DOpt(type = DOpt.Type.DerivedAttributeUpdater)
	    @AttrRef(value = "evaluation")
		public void updateEvaluation() throws ConstraintViolationException{
			if(validateTrialEfficacy(trialEfficacy)) {
				if(trialEfficacy >= 0 && trialEfficacy < 50) {
					this.evaluation = Evaluation.Bad;
				}else if(trialEfficacy >= 50 && trialEfficacy < 80) {
					this.evaluation = Evaluation.Medium;
				}else if(trialEfficacy >= 80 && trialEfficacy < 95) {
					this.evaluation = Evaluation.Good;
				}else {
					this.evaluation = Evaluation.Excellent;
				}
			}else {
				throw new ConstraintViolationException(DExCode.INVALID_TRIAL_EFFICACY, trialEfficacy);
			}
		}
		
		public static boolean validateTrialEfficacy(double trialEfficacy) throws ConstraintViolationException{
			if(trialEfficacy < 0 || trialEfficacy > 100) {
				throw new ConstraintViolationException(DExCode.INVALID_TRIAL_EFFICACY, trialEfficacy);
			}else {
				return true;
			}
		}
		
		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}
		
		public Evaluation getEvaluation() {
			return evaluation;
		}


		public static int getIdCounter() {
			return idCounter;
		}

		public static void setIdCounter(int idCounter) {
			Trial.idCounter = idCounter;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}


		public Vaccine getVaccineType() {
			return vaccineType;
		}


		public void setVaccineType(Vaccine vaccineType) {
			this.vaccineType = vaccineType;
		}


		public PeopleGroup getPeopleGroup() {
			return peopleGroup;
		}


		public void setPeopleGroup(PeopleGroup peopleGroup) {
			this.peopleGroup = peopleGroup;
		}


		public Date getStartDate() {
			return startDate;
		}


		public void setStartDate(Date startDate) {
			this.startDate = startDate;
		}


		public Date getEndDate() {
			return endDate;
		}


		public void setEndDate(Date endDate) throws ConstraintViolationException{
			if(startDate != null && startDate.getTime() < endDate.getTime()) {
				this.endDate = endDate;
			}else {
				throw new ConstraintViolationException(DExCode.INVALID_END_DATE, endDate);
			}
		}


		public int getTrackingDays() {
			return trackingDays;
		}


//		public void setTrackingDays(int trackingDays) {
//			this.trackingDays = trackingDays;
//		}


		public String getDetails() {
			return details;
		}


		public void setDetails(String details) {
			this.details = details;
		}

//		public double getTrialEfficacy() {
//			return trialEfficacy;
//		}

		public double getTrialEfficacy() {
			return getTrialEfficacy(true);
		}


		public double getTrialEfficacy(boolean cached) throws IllegalStateException {
		    if (cached) {
		      Object val = stateHist.get(A_trialEfficacy);

		      if (val == null)
		        throw new IllegalStateException(
		            "Trial.getTrialEfficacy: cached value is null");

		      return (Double) val;
		    } else {
		      if (trialEfficacy != 0D)
		        return trialEfficacy;
		      else
		        return 0D;
		    }
		    }
		    
		public void setTrialEfficacy(double trialEfficacy) {
			this.trialEfficacy = trialEfficacy;
			stateHist.put(A_trialEfficacy, trialEfficacy);
			updateEvaluation();
		}


		public TrialEvaluationAndPeoleGroupReport getRptTrialEvaluationAndPeopleGroup() {
			return rptTrialEvaluationAndPeopleGroup;
		}

		
		public TrialEfficacyGreaterThan getRptTrialEfficacyGreaterThan() {
			return rptTrialEfficacyGreaterThan;
		}


		public TrialEfficacyLessThan getRptTrialEfficacyLessThan() {
			return rptTrialEfficacyLessThan;
		}



		

		@Override
		public String toString() {
			return "Trial [id=" + id + ", name=" + name + ", vaccineType=" + vaccineType + ", peopleGroup="
					+ peopleGroup + ", startDate=" + startDate + ", endDate=" + endDate + ", trackingDays="
					+ trackingDays + ", details=" + details + ", trialEfficacy=" + trialEfficacy + "]";
		}


		private String nextID(String id) throws ConstraintViolationException {
		    if (id == null) { // generate a new id
		      if (idCounter == 0) {
		        idCounter = Calendar.getInstance().get(Calendar.YEAR);
		      } else {
		        idCounter++;
		      }
		      return "T" + idCounter;
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

