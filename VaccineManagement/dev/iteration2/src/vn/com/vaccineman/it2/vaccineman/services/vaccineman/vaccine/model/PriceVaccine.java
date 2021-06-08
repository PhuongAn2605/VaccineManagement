package vn.com.vaccineman.it2.vaccineman.services.vaccineman.vaccine.model;

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
import vn.com.vaccineman.services.enums.Evaluation;
import vn.com.vaccineman.services.vaccineman.contract.Contract;
import vn.com.vaccineman.it2.vaccineman.services.vaccineman.country.Country;


@DClass(schema="vaccineman")
public class PriceVaccine extends Vaccine {
	//attribute
	//
//	@DAttr(name="healthOffice",  type=Type.Domain, length = 20, optional = false)
//	@DAssoc(ascName="healthOffice-has-priceVaccines",role="priceVaccine",
//	  ascType=AssocType.One2Many, endType=AssocEndType.Many,
//	  associate=@Associate(type=HealthOffice.class,cardMin=1,cardMax=1), dependsOn=true)
//	private HealthOffice healthOffice;
//	
	//objectFormConstructor
	
	 @DOpt(type=DOpt.Type.ObjectFormConstructor)
	 @DOpt(type=DOpt.Type.RequiredConstructor)	  
	  public PriceVaccine(@AttrRef("name") String name,
				@AttrRef("producer")Country producer, 
				@AttrRef("nature") String nature,
				@AttrRef("injectionProtocol")String injectionProtocol, 
				@AttrRef("price")Double price) {
		  this(null, name, producer, nature, injectionProtocol, price, 0D);

}
//	 @DOpt(type=DOpt.Type.ObjectFormConstructor)
//	  public PriceVaccine(@AttrRef("name")String name,
//			  @AttrRef("trialEfficacy")Double trialEfficacy,
//			  @AttrRef("quantity") Integer quantity,
//			  @AttrRef("expiryDate")Date expiryDate,
//			  @AttrRef("price")Double price,
//			  @AttrRef("contract")Contract contract) {
//		  this(null, name, trialEfficacy, quantity,0,0, expiryDate, price, contract);
//		  
//
//}
//	 @DOpt(type=DOpt.Type.ObjectFormConstructor)
//	  public PriceVaccine(@AttrRef("name")String name,
//			  @AttrRef("trialEfficacy")Double trialEfficacy,
//			  @AttrRef("quantity") Integer quantity,
//			  @AttrRef("usedQuantity")Integer usedQuantity,
//				@AttrRef("remainQuantity")Integer remainQuantity,
//			  @AttrRef("expiryDate")Date expiryDate,
//			  @AttrRef("price")Double price,
//			  @AttrRef("contract")Contract contract
//			  ) {
//		  this(null, name, trialEfficacy ,quantity,usedQuantity ,remainQuantity ,expiryDate, price, contract);}
//	 
//		  @DOpt(type=DOpt.Type.ObjectFormConstructor)
//		  public PriceVaccine(@AttrRef("name")String name,
//				  @AttrRef("trialEfficacy")Double trialEfficacy,
//				  @AttrRef("quantity") Integer quantity,
//				  @AttrRef("usedQuantity")Integer usedQuantity,
//					@AttrRef("remainQuantity")Integer remainQuantity,
//				  @AttrRef("expiryDate")Date expiryDate,
//				  @AttrRef("price")Double price
//				  ) {
//			  this(null, name, trialEfficacy ,quantity,usedQuantity ,remainQuantity ,expiryDate, price,null);
//			  
//		  }
//		  @DOpt(type=DOpt.Type.ObjectFormConstructor)
//			public PriceVaccine(@AttrRef("id")String id,@AttrRef("name")String name,
//					@AttrRef("trialEfficacy")Double trialEfficacy,
//					@AttrRef("quantity")Integer quantity,
//					@AttrRef("expiryDate")Date expiryDate,
//					@AttrRef("price")Double price
//				) {
//			this(id,name,trialEfficacy, quantity,0, 0, expiryDate,price,null);}
		  
	  @DOpt(type=DOpt.Type.DataSourceConstructor)
		public PriceVaccine(@AttrRef("id") String id, @AttrRef("name") String name,
				@AttrRef("producer")Country producer, 
				@AttrRef("nature") String nature,
				@AttrRef("injectionProtocol")String injectionProtocol, 
				@AttrRef("price")Double price,
				@AttrRef("averageTrialEfficacy")Double averageTrialEfficacy)
						throws ConstraintViolationException{
		super(id, name, producer, nature, injectionProtocol, price, averageTrialEfficacy);
		
	  }	

//	  public double getPrice() {
//		return price;
//	}
//
//	public void setPrice(double price) {
//		this.price = price;
//	}
}