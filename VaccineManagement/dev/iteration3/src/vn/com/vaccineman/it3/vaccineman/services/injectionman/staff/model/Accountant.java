package vn.com.vaccineman.it3.vaccineman.services.injectionman.staff.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import domainapp.basics.model.meta.AttrRef;
import domainapp.basics.model.meta.DAssoc;
import domainapp.basics.model.meta.DAttr;
import domainapp.basics.model.meta.DClass;
import domainapp.basics.model.meta.DOpt;
import domainapp.basics.model.meta.Select;
import vn.com.vaccineman.it3.vaccineman.services.enums.Gender;
import vn.com.vaccineman.it3.vaccineman.services.injectionman.payment.model.Payment;
import vn.com.vaccineman.it3.vaccineman.services.injectionman.staff.address.Address;
import domainapp.basics.model.meta.DAssoc.AssocEndType;
import domainapp.basics.model.meta.DAssoc.AssocType;
import domainapp.basics.model.meta.DAssoc.Associate;
import domainapp.basics.model.meta.DAttr.Type;


@DClass(schema="vaccineman")
public class Accountant extends Staff{
	
//	public static final String A_rptAccountantByName = "rptAccountantByName";
	public static final String A_payments = "payments";
	
	
	@DAttr(name=A_payments,type=Type.Collection,serialisable=false,
			filter=@Select(clazz=Payment.class))
	  @DAssoc(ascName="accountant-has-payments",role="accountant",
	  ascType=AssocType.One2Many, endType=AssocEndType.One,
	  associate=@Associate(type=Payment.class,cardMin=0,cardMax=100))
	private Collection<Payment> payments;
	
	private int paymentCount;
	
	
	@DOpt(type=DOpt.Type.ObjectFormConstructor)
	@DOpt(type=DOpt.Type.RequiredConstructor)
	public Accountant( 
			@AttrRef("name")String name, 
			@AttrRef("phoneNumber")String phoneNumber) {
		super(null, name, null, null, null, phoneNumber);
	}

	@DOpt(type=DOpt.Type.ObjectFormConstructor)
	public Accountant( 
			@AttrRef("name")String name, 
			@AttrRef("dob")Date dob, 
			@AttrRef("gender")Gender gender, 
			@AttrRef("address")Address address, 
			@AttrRef("phoneNumber")String phoneNumber) {
		super(null, name, dob, gender, address, phoneNumber);
	}
	
	
	@DOpt(type=DOpt.Type.DataSourceConstructor)
	public Accountant(@AttrRef("id")String id, 
			@AttrRef("name")String name, 
			@AttrRef("dob")Date dob, 
			@AttrRef("gender")Gender gender, 
			@AttrRef("address")Address address, 
			@AttrRef("phoneNumber")String phoneNumber) {
		super(id, name, dob, gender, address, phoneNumber);
		
		payments = new ArrayList<Payment>();
		paymentCount = 0;
		
	}

	@DOpt(type=DOpt.Type.LinkAdder)
	public boolean addPayment(Payment p) {
		if(!payments.contains(p)) {
			payments.add(p);
		}
		return false;

	}
	
	@DOpt(type=DOpt.Type.LinkAdderNew)
	  public boolean addNewPayment(Payment p) {
	    payments.add(p);
	    
	    paymentCount ++;
	    
	    return false; 
	  }
	
	@DOpt(type=DOpt.Type.LinkAdder)
	  //@MemberRef(name="enrolments")
	  public boolean addPayment(Collection<Payment> pays) {
	    boolean added = false;
	    for (Payment p : pays) {
	      if (!payments.contains(p)) {
	        if (!added) added = true;
	        payments.add(p);
	      }
	    }
	    return false;
	}
	
	@DOpt(type=DOpt.Type.LinkAdderNew)
	  public boolean addNewEnrolment(Collection<Payment> pays) {
	    payments.addAll(pays);
	    paymentCount += pays.size();
	    

	    // no other attributes changed (average mark is not serialisable!!!)
	    return false; 
	  }
	
	 @DOpt(type=DOpt.Type.LinkRemover)
	  //@MemberRef(name="enrolments")
	  public boolean removePayment(Payment p) {
	    boolean removed = payments.remove(p);
	    if(removed) {
	    	paymentCount --;
	    }
//	    
//	    if (removed) {
//	      enrolmentCount--;
//	      
//	      // v2.6.4.b
//	      computeAverageMark();
//	    }
	    // no other attributes changed
	    return false; 
	  }
	 
	public Collection<Payment> getPayments() {
		return payments;
	}

	public void setPayments(Collection<Payment> pays) {
		this.payments = pays;
		paymentCount = pays.size();
	}
	
	 @DOpt(type=DOpt.Type.LinkCountGetter)
	  public Integer getPaymentCount() {
	    return paymentCount;
	    //return enrolments.size();
	  }

	  @DOpt(type=DOpt.Type.LinkCountSetter)
	  public void sePaymentCount(int count) {
	    paymentCount = count;
	  }
}
