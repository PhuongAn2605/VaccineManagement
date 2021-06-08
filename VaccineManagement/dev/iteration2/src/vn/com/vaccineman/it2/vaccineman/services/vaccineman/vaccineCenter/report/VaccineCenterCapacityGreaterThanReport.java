package vn.com.vaccineman.it2.vaccineman.services.vaccineman.vaccineCenter.report;

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
import vn.com.vaccineman.services.injectionman.staff.model.Staff;
import vn.com.vaccineman.services.injectionman.staff.report.StaffByNameReport;
import vn.com.vaccineman.services.vaccineman.vaccineCenter.model.VaccineCenter;

@DClass(schema="vaccineman", serialisable=false)
public class VaccineCenterCapacityGreaterThanReport {
	@DAttr(name = "id", id = true, auto = true, type = Type.Integer, length = 5, optional = false, mutable = false)
	  private int id;
	  private static int idCounter = 0;

	  /**input: person name */
	  @DAttr(name = "capacity", type = Type.Double, length = 20, optional = false)
	  private double capacity;
	  
	  /**output: people whose names match {@link #name} */
	  @DAttr(name="vaccineCenters",type=Type.Collection,optional=false, mutable=false,
	      serialisable=false,filter=@Select(clazz=VaccineCenter.class, 
	      attributes={VaccineCenter.A_id, VaccineCenter.A_name, VaccineCenter.A_capacity, VaccineCenter.A_country,VaccineCenter.A_address, 
	    		  VaccineCenter.A_totalArea, VaccineCenter.A_rptVaccineCenterCapacityGreaterThan})
	      ,derivedFrom={"capacity"}
	      )
	  @DAssoc(ascName="vaccineCenters-by-capacity-report-has-vaccineCenters",role="report",
	      ascType=AssocType.One2Many,endType=AssocEndType.One,
	    associate=@Associate(type=VaccineCenter.class,cardMin=0,cardMax=MetaConstants.CARD_MORE
	    ))
	  @Output
	  private Collection<VaccineCenter> vaccineCenters;

	  /**output: number of VaccineCenter found (if any), derived from {@link #VaccineCenter} */
	  @DAttr(name = "numVaccineCenters", type = Type.Integer, length = 20, auto=true, mutable=false)
	  @Output
	  private int numVaccineCenters;
	  
	  /**
	   * @effects 
	   *  initialise this with <tt>name</tt> and use {@link QRM} to retrieve from data source 
	   *  all {@link VaccineCenter} whose names match <tt>name</tt>.
	   *  initialise {@link #VaccineCenter} with the result if any.
	   *  
	   *  <p>throws NotPossibleException if failed to generate data source query; 
	   *  DataSourceException if fails to read from the data source
	   * 
	   */
	  @DOpt(type=DOpt.Type.ObjectFormConstructor)
	  @DOpt(type=DOpt.Type.RequiredConstructor)
	  public VaccineCenterCapacityGreaterThanReport(@AttrRef("capacity")Double capacity) throws NotPossibleException, DataSourceException {
	    this.id=++idCounter;
	    
	    this.capacity = capacity;
	    
	    doReportQuery();
	  }
	  
	 

	


	public double getCapacity() {
		return capacity;
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
	  public void setCapacity(Double capacity) throws NotPossibleException, DataSourceException {
//	    boolean doReportQuery = (name != null && !name.equals(this.name));
	    
	    this.capacity = capacity;
	    
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
	  @AttrRef(value="vaccineCenters")
	  public void doReportQuery() throws NotPossibleException, DataSourceException {
	    // the query manager instance
	    
	    QRM qrm = QRM.getInstance();
	    
	    // create a query to look up VaccineCenter from the data source
	    // and then populate the output attribute (VaccineCenter) with the result
	    DSMBasic dsm = qrm.getDsm();
	    
	    //TODO: to conserve memory cache the query and only change the query parameter value(s)
	    Query q = QueryToolKit.createSearchQuery(dsm, VaccineCenter.class, 
	        new String[] {VaccineCenter.A_capacity}, 
	        new Op[] {Op.GTEQ}, 
	        new Object[] {capacity});
	    
	    Map<Oid, VaccineCenter> result = qrm.getDom().retrieveObjects(VaccineCenter.class, q);
	    
	    if (result != null) {
	      // update the main output data 
	      vaccineCenters = result.values();
	      
	      // update other output (if any)
	      numVaccineCenters = vaccineCenters.size();
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
	    vaccineCenters = null;
	    numVaccineCenters = 0;
	  }

	  /**
	   * A link-adder method for {@link #VaccineCenter}, required for the object form to function.
	   * However, this method is empty because VaccineCenter have already be recorded in the attribute {@link #VaccineCenter}.
	   */
	  @DOpt(type=DOpt.Type.LinkAdder)
	  public boolean addVaccineCenter(Collection<VaccineCenter> VaccineCenters) {
	    // do nothing
	    return false;
	  }
	  
	  /**
	   * @effects return VaccineCenter
	   */
	  public Collection<VaccineCenter> getVaccineCenters() {
	    return vaccineCenters;
	  }
	  
	  /**
	   * @effects return numVaccineCenter
	   */
	  public int getNumVaccineCenters() {
	    return numVaccineCenters;
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
	    VaccineCenterCapacityGreaterThanReport other = (VaccineCenterCapacityGreaterThanReport) obj;
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
	    return "VaccineCentersByNameReport (" + id + ", " + capacity + ")";
	  }

	}
