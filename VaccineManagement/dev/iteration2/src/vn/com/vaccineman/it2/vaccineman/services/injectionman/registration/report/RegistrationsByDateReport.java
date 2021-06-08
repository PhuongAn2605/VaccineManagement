package vn.com.vaccineman.it2.vaccineman.services.injectionman.registration.report;

import java.util.Collection;
import java.util.Date;
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
import domainapp.basics.model.meta.DAttr.Format;
import domainapp.basics.model.meta.DAttr.Type;
import domainapp.basics.model.query.Query;
import domainapp.basics.model.query.QueryToolKit;
import domainapp.basics.model.query.Expression.Op;
import domainapp.basics.modules.report.model.meta.Output;
import vn.com.vaccineman.services.injectionman.registration.model.Registration;

@DClass(schema="vaccineman", serialisable=false)
public class RegistrationsByDateReport {
	@DAttr(name = "id", id = true, auto = true, type = Type.Integer, length = 5, optional = false, mutable = false)
	  private int id;
	  private static int idCounter = 0;
	  
	  @DAttr(name="day", type=Type.Integer, length=3, min=1, max=31, optional=false)
		private int day;

		@DAttr(name="month", type=Type.Integer, length=3, min=1, max=12, optional=false)
		private int month;
		
		@DAttr(name="registerYear", type=Type.Integer, length=5, min=2019, max=3000, optional=false)
		private int registerYear;
	  
	//  Ex 6.13: Add Student.A_enrolments
	  /**output: students whose names match {@link #name} */
	  @DAttr(name="registrations",type=Type.Collection,optional=false, mutable=false,
	      serialisable=false,filter=@Select(clazz=Registration.class, 
	      attributes={Registration.A_id, Registration.A_registerPerson,
	    		  Registration.A_storage,
	    		  Registration.A_vaccineType,
	    		  Registration.A_quantity,
	    		  Registration.A_vaccinePrice,
	    		  Registration.A_supportStaff,
	    		  Registration.A_rptRegistrationByDate})
	      ,derivedFrom={Registration.A_day, Registration.A_month, Registration.A_registerYear}
	      )
	  @DAssoc(ascName="registrations-by-date-report-has-registrations",role="report",
	      ascType=AssocType.One2Many,endType=AssocEndType.One,
	    associate=@Associate(type=Registration.class,cardMin=0,cardMax=MetaConstants.CARD_MORE
	    ))
	  @Output
	  private Collection<Registration> registrations;

	  /**output: number of Registrations found (if any), derived from {@link #Registrations} */
	  @DAttr(name = "numRegistrations", type = Type.Integer, length = 20, auto=true, mutable=false)
	  @Output
	  private int numRegistrations;
	  
	  /**
	   * @effects 
	   *  initialise this with <tt>name</tt> and use {@link QRM} to retrieve from data source 
	   *  all {@link Registration} whose names match <tt>name</tt>.
	   *  initialise {@link #Registrations} with the result if any.
	   *  
	   *  <p>throws NotPossibleException if failed to generate data source query; 
	   *  DataSourceException if fails to read from the data source
	   * 
	   */
	  @DOpt(type=DOpt.Type.ObjectFormConstructor)
	  @DOpt(type=DOpt.Type.RequiredConstructor)
	  public RegistrationsByDateReport(@AttrRef("day")Integer day,
				@AttrRef("month")Integer month,
				@AttrRef("registerYear")Integer registerYear) throws NotPossibleException, DataSourceException {
	    this.id=++idCounter;
	    this.day = day;
	    this.registerYear = registerYear;
	    this.month = month;
	    
	    doReportQuery();
	  }
	  
	  

	  public int getDay() {
		return day;
	}



	public void setDay(int day) {
		this.day = day;
	}



	public int getMonth() {
		return month;
	}



	public void setMonth(int month) {
		this.month = month;
	}

	public int getRegisterYear() {
		return registerYear;
	}



	public void setRegisterYear(int registerYear) {
		this.registerYear = registerYear;
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
	  @AttrRef(value="registrations")
	  public void doReportQuery() throws NotPossibleException, DataSourceException {
	    // the query manager instance
	    
	    QRM qrm = QRM.getInstance();
	    
	    // create a query to look up Registration from the data source
	    // and then populate the output attribute (Registrations) with the result
	    DSMBasic dsm = qrm.getDsm();
	    
	    
	    Query qDay = QueryToolKit.createSearchQuery(dsm, Registration.class,
	            new String[] {Registration.A_day}, 
	            new Op[] {Op.EQ}, 
	            new Object[] {day});
	    
	    Query qMonth = QueryToolKit.createSearchQuery(dsm, Registration.class,
	            new String[] {Registration.A_month}, 
	            new Op[] {Op.EQ}, 
	            new Object[] {month});
	    
	    Query qYear = QueryToolKit.createSearchQuery(dsm, Registration.class,
	            new String[] {Registration.A_registerYear}, 
	            new Op[] {Op.EQ}, 
	            new Object[] {registerYear});
	        
	        Map<Oid, Registration> result1 = qrm.getDom().retrieveObjects(Registration.class, qDay);
	        Map<Oid, Registration> result2 = qrm.getDom().retrieveObjects(Registration.class, qMonth);
	        Map<Oid, Registration> result3 = qrm.getDom().retrieveObjects(Registration.class, qYear);
	        Map<Oid, Registration> result = new HashMap<>();
	        
	        if (result1 != null && result2 != null && result3 != null) {
	          result.putAll(result1);
	          result.putAll(result2);
	          result.putAll(result3);
	          
	          registrations = result.values();
	          numRegistrations = registrations.size();
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
	    registrations = null;
	    numRegistrations = 0;
	  }

	  /**
	   * A link-adder method for {@link #Registrations}, required for the object form to function.
	   * However, this method is empty because Registrations have already be recorded in the attribute {@link #Registrations}.
	   */
	  @DOpt(type=DOpt.Type.LinkAdder)
	  public boolean addRegistration(Collection<Registration> Registrations) {
	    // do nothing
		
	    return false;
	  }
	  
	  /**
	   * @effects return Registrations
	   */
	  public Collection<Registration> getRegistrations() {
	    return registrations;
	  }
	  
	  /**
	   * @effects return numRegistrations
	   */
	  public int getNumRegistrations() {
	    return numRegistrations;
	  }

	  /**
	   * @effects return id
	   */
	  public int getId() {
	    return id;
	  }

	@Override
	public String toString() {
		return "RegistrationsByDateReport [day=" + day + ", month=" + month + ", year=" + registerYear + "]";
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
		RegistrationsByDateReport other = (RegistrationsByDateReport) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
