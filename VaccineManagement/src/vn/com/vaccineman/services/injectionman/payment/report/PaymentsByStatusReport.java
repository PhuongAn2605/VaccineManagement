package vn.com.vaccineman.services.injectionman.payment.report;

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
import vn.com.vaccineman.services.enums.PaymentStatus;
import vn.com.vaccineman.services.injectionman.injection.model.Injection;
import vn.com.vaccineman.services.injectionman.payment.model.Payment;
import vn.com.vaccineman.services.injectionman.payment.report.PaymentsByStatusReport;
import vn.com.vaccineman.services.injectionman.staff.model.Accountant;
import vn.com.vaccineman.services.injectionman.staff.model.Staff;

@DClass(schema="vaccineman", serialisable=false)
public class PaymentsByStatusReport {
	@DAttr(name = "id", id = true, auto = true, type = Type.Integer, length = 5, optional = false, mutable = false)
	  private int id;
	  private static int idCounter = 0;
	  
	  @DAttr(name = "status", type = Type.Domain, length=20, optional = false)
	  private PaymentStatus status;
	  
	//  Ex 6.13: Add Student.A_enrolments
	  /**output: students whose names match {@link #name} */
	  @DAttr(name="payments",type=Type.Collection,optional=false, mutable=false,
	      serialisable=false,filter=@Select(clazz=Payment.class, 
	      attributes={Payment.A_id, Payment.A_payPerson, Payment.A_registration, 
	    		  Payment.A_vaccineType, Payment.A_cost, Payment.A_inputMoney, Payment.A_returnMoney ,Payment.A_rptPaymentByStatus})
	      ,derivedFrom={"status"}
	      )
	  @DAssoc(ascName="payments-by-name-report-has-payments",role="report",
	      ascType=AssocType.One2Many,endType=AssocEndType.One,
	    associate=@Associate(type=Payment.class,cardMin=0,cardMax=MetaConstants.CARD_MORE
	    ))
	  @Output
	  private Collection<Payment> payments;

	  /**output: number of Injections found (if any), derived from {@link #Injections} */
	  @DAttr(name = "numPayments", type = Type.Integer, length = 20, auto=true, mutable=false)
	  @Output
	  private int numPayments;
	  
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
	  public PaymentsByStatusReport(@AttrRef("status") PaymentStatus status) throws NotPossibleException, DataSourceException {
	    this.id=++idCounter;
	    
	    this.status = status;
	    
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
	  @AttrRef(value="payments")
	  public void doReportQuery() throws NotPossibleException, DataSourceException {
	    // the query manager instance
	    
	    QRM qrm = QRM.getInstance();
	    
	    // create a query to look up Injection from the data source
	    // and then populate the output attribute (Injections) with the result
	    DSMBasic dsm = qrm.getDsm();
	    
	    
	    Query q = QueryToolKit.createSearchQuery(dsm, Payment.class, 
	            new String[] {Payment.A_status}, 
	            new Op[] {Op.MATCH}, 
	            new Object[] {status});
	        
	        Map<Oid, Payment> result = qrm.getDom().retrieveObjects(Payment.class, q);
	        
	        if (result != null) {
	          // update the main output data 
	          payments = result.values();
	          
	          // update other output (if any)
	          numPayments = payments.size();
	        } else {
	          // no data found: reset output
	          resetOutput();
	        }
	      }
	      
	  

	public PaymentStatus getStatus() {
		return status;
	}

	public void setStatus(PaymentStatus status) throws NotPossibleException, DataSourceException {
		this.status = status;
		
		doReportQuery();
	}

	/**
	   * @effects 
	   *  reset all output attributes to their initial values
	   */
	  private void resetOutput() {
	    payments = null;
	    numPayments = 0;
	  }

	  /**
	   * A link-adder method for {@link #Injections}, required for the object form to function.
	   * However, this method is empty because Injections have already be recorded in the attribute {@link #Injections}.
	   */
	  @DOpt(type=DOpt.Type.LinkAdder)
	  public boolean addPayment(Collection<Payment> payments) {
	    // do nothing
		
	    return false;
	  }
	  
	  /**
	   * @effects return Injections
	   */
	  public Collection<Payment> getPayments() {
	    return payments;
	  }
	  
	  /**
	   * @effects return numInjections
	   */
	  public int getNumPayments() {
	    return numPayments;
	  }

	  /**
	   * @effects return id
	   */
	  public int getId() {
	    return id;
	  }

	  
	  
	@Override
	public String toString() {
		return "PaymentsByStatusReport [paymentStatus=" + status + "]";
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
		PaymentsByStatusReport other = (PaymentsByStatusReport) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
