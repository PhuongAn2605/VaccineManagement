package vn.com.vaccineman.services.vaccineman.vaccine.model;

import java.util.Date;

import domainapp.basics.exceptions.ConstraintViolationException;
import domainapp.basics.model.meta.AttrRef;
import domainapp.basics.model.meta.DAssoc;
import domainapp.basics.model.meta.DAttr;
import domainapp.basics.model.meta.DClass;
import domainapp.basics.model.meta.DOpt;
import domainapp.basics.model.meta.DAssoc.AssocEndType;
import domainapp.basics.model.meta.DAssoc.AssocType;
import domainapp.basics.model.meta.DAssoc.Associate;
import domainapp.basics.model.meta.DAttr.Type;
import vn.com.vaccineman.services.enums.CurrencyUnit;
import vn.com.vaccineman.services.enums.Evaluation;
import vn.com.vaccineman.services.vaccineman.contract.Contract;
import vn.com.vaccineman.services.vaccineman.country.Country;
import vn.com.vaccineman.services.vaccineman.vaccine.model.Vaccine;


@DClass(schema="vaccineman")
public class PriceVaccine extends Vaccine {
	
	@DAttr(name = A_price, type = Type.Double, length = 10, min=1000, optional = false)

	
	 @DOpt(type=DOpt.Type.ObjectFormConstructor)
	 @DOpt(type=DOpt.Type.RequiredConstructor)	  
	  public PriceVaccine(@AttrRef("name") String name,
			  @AttrRef("producer")Country producer,
				@AttrRef("nature") String nature,
				@AttrRef("injectionProtocol")String injectionProtocol, 
				@AttrRef("price")Double price) {
		  this(null, name, producer, nature, injectionProtocol, price, null, 0, 0D);

}

	@DOpt(type=DOpt.Type.ObjectFormConstructor)
	public PriceVaccine(@AttrRef("name") String name,
			@AttrRef("producer")Country producer, 
			@AttrRef("nature") String nature,
			@AttrRef("injectionProtocol")String injectionProtocol, 
			@AttrRef("price")Double price,
			@AttrRef("unit")CurrencyUnit unit,
			@AttrRef("trialCount")Integer trialCount,
			@AttrRef("averageTrialEfficacy")Double averageTrialEfficacy)
					throws ConstraintViolationException{
		  this(null, name, producer, nature, injectionProtocol, price, unit, trialCount, averageTrialEfficacy);

	}
	
	@DOpt(type=DOpt.Type.ObjectFormConstructor)
	public PriceVaccine(@AttrRef("name") String name,
			@AttrRef("producer")Country producer, 
			@AttrRef("nature") String nature,
			@AttrRef("injectionProtocol")String injectionProtocol, 
			@AttrRef("price")Double price,
			@AttrRef("unit")CurrencyUnit unit)
					throws ConstraintViolationException{
		  this(null, name, producer, nature, injectionProtocol, price, unit, 0, 0D);

	}
		  
	  @DOpt(type=DOpt.Type.DataSourceConstructor)
		public PriceVaccine(@AttrRef("id") String id, @AttrRef("name") String name,
				@AttrRef("producer")Country producer, 
				@AttrRef("nature") String nature,
				@AttrRef("injectionProtocol")String injectionProtocol, 
				@AttrRef("price")Double price,
				@AttrRef("unit")CurrencyUnit unit,
				@AttrRef("trialCount")Integer trialCount,
				@AttrRef("averageTrialEfficacy")Double averageTrialEfficacy)
						throws ConstraintViolationException{
		super(id, name, producer, nature, injectionProtocol, price, unit, trialCount, averageTrialEfficacy);
		
	  }	

//	  public double getPrice() {
//		return price;
//	}
//
//	public void setPrice(double price) {
//		this.price = price;
//	}
}