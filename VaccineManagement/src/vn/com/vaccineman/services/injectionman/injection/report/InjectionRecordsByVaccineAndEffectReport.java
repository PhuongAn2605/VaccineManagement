package vn.com.vaccineman.services.injectionman.injection.report;

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
import domainapp.basics.model.query.Expression;
import domainapp.basics.model.query.Query;
import domainapp.basics.model.query.QueryToolKit;
import domainapp.basics.model.query.Expression.Op;
import domainapp.basics.modules.report.model.meta.Output;
import vn.com.vaccineman.services.enums.SideEffect;
import vn.com.vaccineman.services.injectionman.injection.model.Injection;
import vn.com.vaccineman.services.injectionman.injection.record.InjectionRecord;
import vn.com.vaccineman.services.injectionman.injection.report.InjectionRecordsByVaccineAndEffectReport;
import vn.com.vaccineman.services.injectionman.person.model.Person;
import vn.com.vaccineman.services.vaccineman.vaccine.model.Vaccine;


@DClass(schema="vaccineman", serialisable=false)
public class InjectionRecordsByVaccineAndEffectReport {
	
	@DAttr(name = "id", id = true, auto = true, type = Type.Integer, length = 5, optional = false, mutable = false)
	  private int id;
	  private static int idCounter = 0;
	  
	  @DAttr(name = "vaccineType", type = Type.String, length=30, optional = true)
	  private String vaccineType;
	  
	  /**input: student name */
	  @DAttr(name = "sideEffect", type = Type.Domain, length=30, optional = false)
	  private SideEffect sideEffect;
	  
	//  Ex 6.13: Add Student.A_enrolments
	  /**output: students whose names match {@link #name} */
	  @DAttr(name="injectionRecords",type=Type.Collection,optional=false, mutable=false,
	      serialisable=false,filter=@Select(clazz=InjectionRecord.class, 
	      attributes={InjectionRecord.A_id, InjectionRecord.A_recordDate, InjectionRecord.A_vaccineType,
	    		  InjectionRecord.A_injectedPerson, InjectionRecord.A_rptInjectionRecordsByVaccineAndEffect})
	      ,derivedFrom={"vaccineType", "sideEffect"}
	      )
	  @DAssoc(ascName="injectionRecords-by-name-report-has-injectionRecords",role="report",
	      ascType=AssocType.One2Many,endType=AssocEndType.One,
	    associate=@Associate(type=Injection.class,cardMin=0,cardMax=MetaConstants.CARD_MORE))
	  @Output
	  private Collection<InjectionRecord> injectionRecords;

	  /**output: number of Injections found (if any), derived from {@link #Injections} */
	  @DAttr(name = "numInjections", type = Type.Integer, length = 20, auto=true, mutable=false)
	  @Output
	  private int numInjections;
	  
	  /**
	   * @effects 
	   *  initialise this with <tt>name</tt> and use {@link QRM} to retrieve from data source 
	   *  all {@link Injection} whose names match <tt>name</tt>.
	   *  initialise {@link #Injections} with the result if any.
	   *  
	   *  <p>throws NotPossibleException if failed to generate data source query; 
	   *  DataSourceException if fails to read from the data source
	   * 
	   */
	  @DOpt(type=DOpt.Type.ObjectFormConstructor)
	  @DOpt(type=DOpt.Type.RequiredConstructor)
	  public InjectionRecordsByVaccineAndEffectReport(
			  @AttrRef("sideEffect") SideEffect sideEffect) throws NotPossibleException, DataSourceException {
//	    this.id=++idCounter;
//	    
////	    this.vaccineType = vaccineType;
//	    this.sideEffect = sideEffect;
//	    
//	    doReportQuery();
		  this(null, sideEffect);
	  }
	  
	  @DOpt(type=DOpt.Type.ObjectFormConstructor)
	  public InjectionRecordsByVaccineAndEffectReport(@AttrRef("vaccineType") String vaccineType, 
			  @AttrRef("sideEffect") SideEffect sideEffect) throws NotPossibleException, DataSourceException {
	    this.id=++idCounter;
	    
	    this.vaccineType = vaccineType;
	    this.sideEffect = sideEffect;
	    
	    doReportQuery();
	  }
//	  
//	  @DOpt(type=DOpt.Type.Obj)
//	  public InjectionsProfileReport(@AttrRef("name"))throws NotPossibleException, DataSourceException{
//		  this(name, 0);
//	  }
	  

	  public String getVaccineType() {
		return vaccineType;
	}


	public void setVaccineType(String vaccineType)  throws NotPossibleException, DataSourceException{
		this.vaccineType = vaccineType;
	}


	public SideEffect getSideEffect() {
		return sideEffect;
	}


	public void setSideEffect(SideEffect sideEffect)  throws NotPossibleException, DataSourceException{
		this.sideEffect = sideEffect;
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
	  @AttrRef(value="injections")
	  public void doReportQuery() throws NotPossibleException, DataSourceException {
	    // the query manager instance
	    
	    QRM qrm = QRM.getInstance();
	    
	    DSMBasic dsm = qrm.getDsm();
	    
	    
	    Query qVaccine = QueryToolKit.createSimpleJoinQuery(dsm, InjectionRecord.class, Vaccine.class,
		           InjectionRecord.A_vaccineType,
		            Vaccine.A_name,
		            Op.MATCH,
		            "%" + vaccineType + "%");
       
	    Query qEffect = QueryToolKit.createSearchQuery(dsm, InjectionRecord.class,
	    		new String[] {InjectionRecord.A_sideEffect}, 
	            new Op[] {Op.MATCH}, 
	            new Object[] {sideEffect});
	    
//	        Map<Oid, InjectionRecord> result = qrm.getDom().retrieveObjects(InjectionRecord.class, q);
	        Map<Oid, InjectionRecord> resultEffect = qrm.getDom().retrieveObjects(InjectionRecord.class, qEffect);
	        Map<Oid, InjectionRecord> result = new HashMap<>();
	        
	        if(resultEffect != null && vaccineType != null) {
	        	
	        	Map<Oid, InjectionRecord> resultVaccine = qrm.getDom().retrieveObjects(InjectionRecord.class, qVaccine);
	        	if (resultVaccine != null) {
		        	result.putAll(resultEffect);
		        	result.putAll(resultVaccine);
		        	  
		          injectionRecords =result.values();
		          numInjections = injectionRecords.size();
		        
		        } else {
		        	resetOutput();
		        }
	        }else if(resultEffect != null && vaccineType == null) {
	        	result.putAll(resultEffect);
	        	injectionRecords =result.values();
		         numInjections = injectionRecords.size();

	        }else {
	        	resetOutput();
	        }
	        
//	        if (result != null && result1 != null) {
//	        	result2.putAll(result);
//	        	  result2.putAll(result1);
//	        	  
//	          injectionRecords =result2.values();
//	          numInjections = injectionRecords.size();
//	        
//	        } else {
//		          // no data found: reset output
//		          resetOutput();
//	        }
	      }
	      


	  /**
	   * @effects 
	   *  reset all output attributes to their initial values
	   */
	  private void resetOutput() {
	    injectionRecords = null;
	    numInjections = 0;
	  }

	  /**
	   * A link-adder method for {@link #Injections}, required for the object form to function.
	   * However, this method is empty because Injections have already be recorded in the attribute {@link #Injections}.
	   */
	  @DOpt(type=DOpt.Type.LinkAdder)
	  public boolean addInjection(Collection<Injection> injections) {
	    // do nothing
		
	    return false;
	  }
	  
	  /**
	   * @effects return Injections
	   */
	  public Collection<InjectionRecord> getInjectionRecords() {
	    return injectionRecords;
	  }
	  
	  /**
	   * @effects return numInjections
	   */
	  public int getNumInjections() {
	    return numInjections;
	  }

	  /**
	   * @effects return id
	   */
	  public int getId() {
	    return id;
	  }
	@Override
	public String toString() {
		return "InjectionsByVaccineAndEffectReport [id=" + id + ", vaccineType=" + vaccineType + ", sideEffect="
				+ sideEffect + "]";
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
		InjectionRecordsByVaccineAndEffectReport other = (InjectionRecordsByVaccineAndEffectReport) obj;
		if (id != other.id)
			return false;
		return true;
	}

	 
}
