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
public class FreeVaccine extends Vaccine {
	
//	@DAttr(name=A_price,type=Type.Double,length=5, mutable=true,
//			auto=true, serialisable=true, optional=true)
//	  private double price ;
	
//	
//	@DAttr(name="healthOffice",  type=Type.Domain, length = 20, optional = false)
//	@DAssoc(ascName="healthOffice-has-freeVaccines",role="freeVaccine",
//	  ascType=AssocType.One2Many, endType=AssocEndType.Many,
//	  associate=@Associate(type=HealthOffice.class,cardMin=1,cardMax=1), dependsOn=true)
//	private HealthOffice healthOffice;
	
	

	 @DOpt(type=DOpt.Type.ObjectFormConstructor)
	 @DOpt(type=DOpt.Type.RequiredConstructor)
	  public FreeVaccine(@AttrRef("name") String name,
			  @AttrRef("producer")Country producer,
				@AttrRef("nature") String nature,
				@AttrRef("injectionProtocol")String injectionProtocol
			  ) {
		  this(null,  name, producer, nature, injectionProtocol, 0D, null, 0, 0D);

}
	 @DOpt(type=DOpt.Type.ObjectFormConstructor)
		public FreeVaccine(@AttrRef("name") String name,
				@AttrRef("producer")Country producer,
				@AttrRef("nature") String nature,
				@AttrRef("injectionProtocol")String injectionProtocol,
				@AttrRef("price")Double price,
				@AttrRef("unit")CurrencyUnit unit,
				@AttrRef("trialCount")Integer trialCount,
				@AttrRef("averageTrialEfficacy")Double averageTrialEfficacy)
						  throws ConstraintViolationException{
		  this(null,  name, producer, nature, injectionProtocol, price, unit, trialCount, averageTrialEfficacy);

	 }

	 @DOpt(type=DOpt.Type.ObjectFormConstructor)
		public FreeVaccine(@AttrRef("name") String name,
				@AttrRef("producer")Country producer,
				@AttrRef("nature") String nature,
				@AttrRef("injectionProtocol")String injectionProtocol,
				@AttrRef("price")Double price,
				@AttrRef("unit")CurrencyUnit unit)
						  throws ConstraintViolationException{
		  this(null,  name, producer, nature, injectionProtocol, price, unit,0, 0D);

	 }
	  @DOpt(type=DOpt.Type.DataSourceConstructor)
		public FreeVaccine(@AttrRef("id") String id, @AttrRef("name") String name,
				@AttrRef("producer")Country producer, 
				@AttrRef("nature") String nature,
				@AttrRef("injectionProtocol")String injectionProtocol, 
				@AttrRef("price")Double price,
				@AttrRef("unit")CurrencyUnit unit,
				@AttrRef("trialCount")Integer trialCount,
				@AttrRef("averageTrialEfficacy")Double averageTrialEfficacy)
						  throws ConstraintViolationException{
			super(id,  name, producer, nature, injectionProtocol, 0D, unit, trialCount, averageTrialEfficacy);
//			this.price = 0D;
	  }
//	  
//	  public FreeVacine(@AttrRef("id")String id, 
//				@AttrRef("name")String name, 
//				@AttrRef("trialEfficacy") String trialEfficacy,
//				@AttrRef("quantity")Integer quantity,
//				  @AttrRef("usedQuantity")Integer usedQuantity,
//				@AttrRef("remainQuantity")Integer remainQuantity,
//				  @AttrRef("expiryDate")Date expiryDate,
//				  @AttrRef ("price")Double price,
//				  @AttrRef("contract")Contract contract )throws ConstraintViolationException{
//		  super(id, name, trialEfficacy, quantity,usedQuantity ,, expiryDate, price, contract)
//	  }
	  
	  @Override
	public void setPrice(double price) {
		// TODO Auto-generated method stub
		super.setPrice(0D);
	}

}
