package vn.com.vaccinemane.it1.vaccineman.model.vaccine;
import java.util.Date;

import domainapp.basics.exceptions.ConstraintViolationException;
import domainapp.basics.model.meta.AttrRef;
import domainapp.basics.model.meta.DClass;
import domainapp.basics.model.meta.DOpt;
@DClass(schema="vaccineman")
public class FreeVaccine extends Vaccine {
	 @DOpt(type=DOpt.Type.ObjectFormConstructor)
	 @DOpt(type=DOpt.Type.RequiredConstructor)
	  public FreeVaccine(@AttrRef("name")String name,
			  @AttrRef("trialEfficacy")String trialEfficacy,
			  @AttrRef("quantity") Integer quantity,
			  @AttrRef("expiryDate")Date expiryDate) {
		  this(null, name, trialEfficacy ,quantity, expiryDate);

}
	  @DOpt(type=DOpt.Type.DataSourceConstructor)
		public FreeVaccine(String id, 
				String name,
				 String trialEfficacy,
				int quantity,
				Date expiryDate)
			throws ConstraintViolationException{
		super(id, name, trialEfficacy, quantity, expiryDate);
	  
		
	  }	
}
