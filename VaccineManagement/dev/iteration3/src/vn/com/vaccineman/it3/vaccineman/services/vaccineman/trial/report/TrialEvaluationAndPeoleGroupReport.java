package vn.com.vaccineman.it3.vaccineman.services.vaccineman.trial.report;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import domainapp.basics.core.dodm.dsm.DSMBasic;
import domainapp.basics.core.dodm.qrm.QRM;
import domainapp.basics.exceptions.DataSourceException;
import domainapp.basics.exceptions.NotPossibleException;
import domainapp.basics.model.Oid;
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
import domainapp.basics.model.query.Query;
import domainapp.basics.model.query.QueryToolKit;
import domainapp.basics.model.query.Expression.Op;
import domainapp.basics.modules.report.model.meta.Output;
import vn.com.vaccineman.it3.vaccineman.services.enums.Evaluation;
import vn.com.vaccineman.it3.vaccineman.services.enums.PeopleGroup;
import vn.com.vaccineman.it3.vaccineman.services.injectionman.injection.model.Injection;
import vn.com.vaccineman.it3.vaccineman.services.injectionman.staff.model.Staff;
import vn.com.vaccineman.it3.vaccineman.services.injectionman.staff.report.StaffByNameReport;
import vn.com.vaccineman.it3.vaccineman.services.vaccineman.trial.model.Trial;
import vn.com.vaccineman.it3.vaccineman.services.vaccineman.vaccine.model.Vaccine;


@DClass(schema="vaccineman", serialisable=false)
public class TrialEvaluationAndPeoleGroupReport {
	@DAttr(name = "id", id = true, auto = true, type = Type.Integer, length = 5, optional = false, mutable = false)
	  private int id;
	  private static int idCounter = 0;

	  @DAttr(name = "evaluation", type = Type.Domain, length = 20, optional = false)
	  private Evaluation evaluation;
	  
	  @DAttr(name = "peopleGroup", type = Type.Domain, length = 20, optional = false)
	  private PeopleGroup peopleGroup;
	  
	  /**output: people whose names match {@link #name} */
	  @DAttr(name="trials",type=Type.Collection,optional=false, mutable=false,
	      serialisable=false,filter=@Select(clazz=Trial.class, 
	      attributes={Trial.A_id, Trial.A_name, Trial.A_vaccineType, Trial.A_startDate, 
	    		  Trial.A_endDate,Trial.A_trackingDays, Trial.A_trialEfficacy, Trial.A_rptTrialEvaluationAndPeopleGroup})
	      ,derivedFrom={Trial.A_evaluation, Trial.A_peopleGroup}
	      )
	  @DAssoc(ascName="trials-by-remainQuantity-report-has-trials",role="report",
	      ascType=AssocType.One2Many,endType=AssocEndType.One,
	    associate=@Associate(type=Trial.class,cardMin=0,cardMax=MetaConstants.CARD_MORE
	    ))
	  @Output
	  private Collection<Trial> trials;

	  /**output: number of Trial found (if any), derived from {@link #Trial} */
	  @DAttr(name = "numTrials", type = Type.Integer, length = 20, auto=true, mutable=false)
	  @Output
	  private int numTrials;
	  
	  /**
	   * @effects 
	   *  initialise this with <tt>name</tt> and use {@link QRM} to retrieve from data source 
	   *  all {@link Trial} whose names match <tt>name</tt>.
	   *  initialise {@link #Trial} with the result if any.
	   *  
	   *  <p>throws NotPossibleException if failed to generate data source query; 
	   *  DataSourceException if fails to read from the data source
	   * 
	   */
	  @DOpt(type=DOpt.Type.ObjectFormConstructor)
	  @DOpt(type=DOpt.Type.RequiredConstructor)
	  public TrialEvaluationAndPeoleGroupReport(@AttrRef("evaluation") Evaluation evaluation,
			  @AttrRef("peopleGroup") PeopleGroup peopleGroup) throws NotPossibleException, DataSourceException {
	    this.id=++idCounter;
	    
	    this.evaluation = evaluation;
	    this.peopleGroup = peopleGroup;
	    
	    doReportQuery();
	  }
	  
	public Evaluation getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(Evaluation evaluation) {
		this.evaluation = evaluation;
	}

	public PeopleGroup getPeopleGroup() {
		return peopleGroup;
	}


	  public void setPeopleGroup(PeopleGroup peopleGroup) {
		this.peopleGroup = peopleGroup;
	}

	/**
	   * This method is invoked when the report input has be set by the user. 
	   * 
	   * @effects <pre>
	   *   formulate the object query
	   *   execute the query to retrieve from the data source the domain objects that satisfy it 
	   *   update the output attributes accordingly.
	   *  
	   *  <p>throws NotPossibleException if failed to generate data source query; 
	   *  DataSourceException if fails to read from the data source. </pre>
	   */
	  @DOpt(type=DOpt.Type.DerivedAttributeUpdater)
	  @AttrRef(value="trials")
	  public void doReportQuery() throws NotPossibleException, DataSourceException {
		    // the query manager instance
		    
		    QRM qrm = QRM.getInstance();
		    
		    // create a query to look up Injection from the data source
		    // and then populate the output attribute (Injections) with the result
		    DSMBasic dsm = qrm.getDsm();
		    
		    
//		    Ex 6.1: new Object[] {name + "%"});
//		    	Ex6.2: new Object[] {name + "%", "%" + name});
		    
		    //TODO: to conserve memory cache the query and only change the query parameter value(s)
		    Query q = QueryToolKit.createSearchQuery(dsm, Trial.class,
		    		new String[] {Trial.A_evaluation}, 
		            new Op[] {Op.MATCH}, 
		            new Object[] {evaluation});
		        //exercise 6.2
//		        Query q3 = QueryToolKit.createSearchQuery(dsm, Injection.class, 
//		                new String[] {Injection.A_vaccineType}, 
//		                new Expression.Op[] {Expression.Op.MATCH},
//		                new Object[] { "%"+vaccineType});
//		        
		    Query q1 = QueryToolKit.createSearchQuery(dsm, Trial.class,
		    		new String[] {Trial.A_peopleGroup}, 
		            new Op[] {Op.MATCH}, 
		            new Object[] {peopleGroup});
//		    
//		    Query q1 = QueryToolKit.createSearchQuery(dsm, SideEffect.class,
//		    		new String[] {Injection.A_sideEffect}, 
//		            new Op[] {Op.IN}, 
//		            new Object[] {SideEffect.Fatigue, SideEffect.Fever, SideEffect.Fine, SideEffect.Headache, SideEffect.NotCheck,
//		            		SideEffect.Others});
		    
		        Map<Oid, Trial> result = qrm.getDom().retrieveObjects(Trial.class, q);
		        Map<Oid, Trial> result1 = qrm.getDom().retrieveObjects(Trial.class, q1);
//		        Map<Oid, Injection> result3 = qrm.getDom().retrieveObjects(Injection.class, q2);
		        Map<Oid, Trial> result2 = new HashMap<>();
		        if (result != null) {
		        	result2.putAll(result);
		          // update the main output data 
		          if( result1 !=null) {
		        	  result2.putAll(result1);
		          }
		          if(!result2.isEmpty()) {
		        	  
		          trials =result2.values();
		          // update other output (if any)
		          numTrials = trials.size();
		        }
		        } else {
			          // no data found: reset output
			          resetOutput();
		        }
		      }

	  /**
	   * @effects 
	   *  reset all output attributes to their initial values
	   */
	  private void resetOutput() {
	    trials = null;
	    numTrials = 0;
	  }

	  /**
	   * A link-adder method for {@link #Trial}, required for the object form to function.
	   * However, this method is empty because Trial have already be recorded in the attribute {@link #Trial}.
	   */
	  @DOpt(type=DOpt.Type.LinkAdder)
	  public boolean addTrial(Collection<Trial> trials) {
	    // do nothing
	    return false;
	  }
	  
	  /**
	   * @effects return Trial
	   */
	  public Collection<Trial> getTrials() {
	    return trials;
	  }
	  
	  /**
	   * @effects return numTrial
	   */
	  public int getNumTrials() {
	    return numTrials;
	  }

	  /**
	   * @effects return id
	   */
	  public int getId() {
	    return id;
	  }

	  /* (non-Javadoc)
	   * @see java.lang.Object#hashCode()
	   */
	  /**
	   * @effects 
	   * 
	   * @version 
	   */
	  @Override
	  public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + id;
	    return result;
	  }

	  /* (non-Javadoc)
	   * @see java.lang.Object#equals(java.lang.Object)
	   */
	  /**
	   * @effects 
	   * 
	   * @version 
	   */
	  @Override
	  public boolean equals(Object obj) {
	    if (this == obj)
	      return true;
	    if (obj == null)
	      return false;
	    if (getClass() != obj.getClass())
	      return false;
	    TrialEvaluationAndPeoleGroupReport other = (TrialEvaluationAndPeoleGroupReport) obj;
	    if (id != other.id)
	      return false;
	    return true;
	  }

	@Override
	public String toString() {
		return "TrialByVaccineEvaluationAndPeoleGroupReport [id=" + id + ", evaluation=" + evaluation + ", peopleGroup="
				+ peopleGroup + ", numTrials=" + numTrials + "]";
	}

	}
