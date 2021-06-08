package vn.com.vaccineman.it2.vaccineman.services.vaccineman.storage.report;

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
import vn.com.vaccineman.services.vaccineman.storage.model.Storage;

@DClass(schema="vaccineman", serialisable=false)
public class RemainVaccineQuantityReportLessThan {
	@DAttr(name = "id", id = true, auto = true, type = Type.Integer, length = 5, optional = false, mutable = false)
	  private int id;
	  private static int idCounter = 0;

	  /**input: person name */
	  @DAttr(name = "remainQuantity", type = Type.Long, length = 20, optional = false)
	  private Long remainQuantity;
	  
	  /**output: people whose names match {@link #name} */
	  @DAttr(name="storages",type=Type.Collection,optional=false, mutable=false,
	      serialisable=false,filter=@Select(clazz=Storage.class, 
	      attributes={Storage.A_id, Storage.A_name, Storage.A_capacity, Storage.A_vaccine,Storage.A_quantity, 
	    		  Storage.A_usedQuantity, Storage.A_rptRemainVaccineQuantityLessThan})
	      ,derivedFrom={"remainQuantity"}
	      )
	  @DAssoc(ascName="storages-by-remainQuantity-report-has-storages",role="report",
	      ascType=AssocType.One2Many,endType=AssocEndType.One,
	    associate=@Associate(type=Storage.class,cardMin=0,cardMax=MetaConstants.CARD_MORE
	    ))
	  @Output
	  private Collection<Storage> storages;

	  /**output: number of Storage found (if any), derived from {@link #Storage} */
	  @DAttr(name = "numStorages", type = Type.Integer, length = 20, auto=true, mutable=false)
	  @Output
	  private int numStorages;
	  
	  /**
	   * @effects 
	   *  initialise this with <tt>name</tt> and use {@link QRM} to retrieve from data source 
	   *  all {@link Storage} whose names match <tt>name</tt>.
	   *  initialise {@link #Storage} with the result if any.
	   *  
	   *  <p>throws NotPossibleException if failed to generate data source query; 
	   *  DataSourceException if fails to read from the data source
	   * 
	   */
	  @DOpt(type=DOpt.Type.ObjectFormConstructor)
	  @DOpt(type=DOpt.Type.RequiredConstructor)
	  public RemainVaccineQuantityReportLessThan(@AttrRef("remainQuantity") Long remainQuanity) throws NotPossibleException, DataSourceException {
	    this.id=++idCounter;
	    
	    this.remainQuantity = remainQuanity;
	    
	    doReportQuery();
	  }
	  
	 

	  public Long getRemainQuantity() {
		return remainQuantity;
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
	  public void setRemainQuantity(Long remainQuantity) throws NotPossibleException, DataSourceException {
//	    boolean doReportQuery = (name != null && !name.equals(this.name));
	    
	    this.remainQuantity = remainQuantity;
	    
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
	  @AttrRef(value="storages")
	  public void doReportQuery() throws NotPossibleException, DataSourceException {
	    // the query manager instance
	    
	    QRM qrm = QRM.getInstance();
	    
	    // create a query to look up Storage from the data source
	    // and then populate the output attribute (Storage) with the result
	    DSMBasic dsm = qrm.getDsm();
	    
	    //TODO: to conserve memory cache the query and only change the query parameter value(s)
	    Query q = QueryToolKit.createSearchQuery(dsm, Storage.class, 
	        new String[] {Storage.A_remainQuantity}, 
	        new Op[] {Op.LTEQ}, 
	        new Object[] {remainQuantity});
	    
	    Map<Oid, Storage> result = qrm.getDom().retrieveObjects(Storage.class, q);
	    
	    if (result != null) {
	      // update the main output data 
	      storages = result.values();
	      
	      // update other output (if any)
	      numStorages = storages.size();
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
	    storages = null;
	    numStorages = 0;
	  }

	  /**
	   * A link-adder method for {@link #Storage}, required for the object form to function.
	   * However, this method is empty because Storage have already be recorded in the attribute {@link #Storage}.
	   */
	  @DOpt(type=DOpt.Type.LinkAdder)
	  public boolean addStorage(Collection<Storage> storages) {
	    // do nothing
	    return false;
	  }
	  
	  /**
	   * @effects return Storage
	   */
	  public Collection<Storage> getStorages() {
	    return storages;
	  }
	  
	  /**
	   * @effects return numStorage
	   */
	  public int getNumStorages() {
	    return numStorages;
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
	    RemainVaccineQuantityReportLessThan other = (RemainVaccineQuantityReportLessThan) obj;
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
	    return "StoragesByNameReport (" + id + ", " + remainQuantity + ")";
	  }

	}
