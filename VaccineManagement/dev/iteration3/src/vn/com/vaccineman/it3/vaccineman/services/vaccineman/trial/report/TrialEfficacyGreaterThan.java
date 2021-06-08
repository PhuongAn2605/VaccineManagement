package vn.com.vaccineman.it3.vaccineman.services.vaccineman.trial.report;

import java.util.Collection;
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
import vn.com.vaccineman.it3.vaccineman.services.injectionman.staff.model.Staff;
import vn.com.vaccineman.it3.vaccineman.services.injectionman.staff.report.StaffByNameReport;
import vn.com.vaccineman.it3.vaccineman.services.vaccineman.trial.model.Trial;


@DClass(schema="vaccineman", serialisable=false)
public class TrialEfficacyGreaterThan {
	@DAttr(name = "id", id = true, auto = true, type = Type.Integer, length = 5, optional = false, mutable = false)
	  private int id;
	  private static int idCounter = 0;

	  /**input: person name */
	  @DAttr(name = "trialEfficacy", type = Type.Double, length = 20, optional = false)
	  private double trialEfficacy;
	  
	  /**output: people whose names match {@link #name} */
	  @DAttr(name="trials",type=Type.Collection,optional=false, mutable=false,
	      serialisable=false,filter=@Select(clazz=Trial.class, 
	      attributes={Trial.A_id, Trial.A_name, Trial.A_vaccineType, Trial.A_startDate, 
	    		  Trial.A_endDate,Trial.A_trackingDays, Trial.A_trialEfficacy, Trial.A_evaluation,Trial.A_rptTrialEfficacyGreaterThan})
	      ,derivedFrom={"trialEfficacy"}
	      )
	  @DAssoc(ascName="trials-by-efficacy-greaterThan-report-has-trials",role="report",
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
	  public TrialEfficacyGreaterThan(@AttrRef("trialEfficacy") Double trialEfficacy) throws NotPossibleException, DataSourceException {
	    this.id=++idCounter;
	    
	    this.trialEfficacy = this.trialEfficacy;
	    
	    doReportQuery();
	  }
	  
	public double getTrialEfficacy() {
		return trialEfficacy;
	}

	/**
	   * @effects <pre>
	   *  set this.name = name
	   *  if name is changed
	   *    invoke {@link #doReportQuery()} to update the output attribute value
	   *    throws NotPossibleException if failed to generate data source query; 
	   *    DataSourceException if fails to read from the data source.
	   *  </pre>
	   */
	  public void setTrialEfficacy(Double trialEfficacy) throws NotPossibleException, DataSourceException {
//	    boolean doReportQuery = (name != null && !name.equals(this.name));
	    
	    this.trialEfficacy = trialEfficacy;
	    
	    // DONOT invoke this here if there are > 1 input attributes!
	    doReportQuery();
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
	    
	    // create a query to look up Trial from the data source
	    // and then populate the output attribute (Trial) with the result
	    DSMBasic dsm = qrm.getDsm();
	    
	    //TODO: to conserve memory cache the query and only change the query parameter value(s)
	    Query q = QueryToolKit.createSearchQuery(dsm, Trial.class, 
	        new String[] {Trial.A_trialEfficacy}, 
	        new Op[] {Op.GTEQ}, 
	        new Object[] {trialEfficacy});
	    
	    Map<Oid, Trial> result = qrm.getDom().retrieveObjects(Trial.class, q);
	    
	    if (result != null) {
	      // update the main output data 
	      trials = result.values();
	      
	      // update other output (if any)
	      numTrials = trials.size();
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
	    TrialEfficacyGreaterThan other = (TrialEfficacyGreaterThan) obj;
	    if (id != other.id)
	      return false;
	    return true;
	  }

	  /* (non-Javadoc)
	   * @see java.lang.Object#toString()
	   */
	  /**
	   * @effects 
	   * 
	   * @version 
	   */
	  @Override
	  public String toString() {
	    return "TrialsByNameReport (" + id + ", " + trialEfficacy + ")";
	  }

	}
